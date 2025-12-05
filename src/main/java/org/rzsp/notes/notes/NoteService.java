package org.rzsp.notes.notes;

import lombok.extern.log4j.Log4j2;
import org.rzsp.notes.days.DayService;
import org.rzsp.notes.days.dto.DayNotesResponse;
import org.rzsp.notes.exceptions.NoteNotFoundException;
import org.rzsp.notes.notes.dto.NoteCreateRequest;
import org.rzsp.notes.notes.dto.NoteResponse;
import org.rzsp.notes.notes.mappers.NoteMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Log4j2
@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final DayService dayService;
    private final NoteMapper noteMapper;

    /**
     * Конструктор сервиса для работы с заметками.
     *
     * @param noteRepository репозиторий для доступа к данным заметок
     * @param dayService сервис для работы с днями
     * @param noteMapper преобразователь для сущностей и DTO заметок
     */
    public NoteService(
            NoteRepository noteRepository,
            DayService dayService,
            NoteMapper noteMapper
    ) {
        this.noteRepository = noteRepository;
        this.dayService = dayService;
        this.noteMapper = noteMapper;
    }

    /**
     * Получает заметки по указанному в аргументе дате.
     *
     * @param date дата, заметки которой необходимо вернуть
     * @return {@link DayNotesResponse} ответ, который включает в себя информацию о дне и заметках
     */
    public DayNotesResponse getNotesByDate(
            LocalDate date
    ) {
        log.debug("Start getting all notes by date: {}", date);

        List<NoteResponse> notes = noteRepository.findAllByDateOrderByNumberAsc(date).stream()
                .map(noteMapper::toResponse)
                .toList();

        DayNotesResponse day = dayService.getInformationAboutHoliday(date);

        return DayNotesResponse.builder()
                .isHoliday(day.isHoliday())
                .holidayName(day.holidayName())
                .notes(notes)
                .build();
    }

    /**
     * Создает сущность заметки на основании запрооса.
     *
     * @param request DTO запрос на создание сущности заметки
     */
    @Transactional
    public void createNote(
            NoteCreateRequest request
    ) {
        log.debug("Creating note by request: {}", request);

        Long numberForNote = noteRepository
                .findTopByDateOrderByNumberDesc(request.date())
                .map(NoteEntity::getNumber)
                .orElse(0L) + 1;

        NoteEntity newNote = noteMapper.toEntity(request);
        newNote.setNumber(numberForNote);

        noteRepository.save(newNote);

        log.debug("Creating by request is ended");
    }

    /**
     * Удаляет заметку на основании указанного в аргументе индентификатора заметки.
     *
     * @param id уникальный индентификатор, по которому удаляется заметка
     * @throws NoteNotFoundException исключение, выбрасывается в случае если по указанному ID заметки не существует-
     */
    public void deleteNoteById(
            Long id
    ) {
        log.debug("Start deleting note by id: {}", id);

        if (!noteRepository.existsById(id)) {
            throw new NoteNotFoundException(id);
        }

        noteRepository.deleteById(id);

        log.debug("Deleting by id is ended");
    }

    /**
     * Удаляет все заметки на указанную дату.
     *
     * @param date дата, заметки которой необходимо удалить
     */
    @Transactional
    public void deleteAllNotesByDate(
            LocalDate date
    ) {
        log.debug("Start deleting all notes by date: {}", date);

        boolean hasNotes = noteRepository.existsByDate(date);

        if (!hasNotes) {
            throw new NoteNotFoundException(date);
        }

        noteRepository.deleteAllByDate(date);

        log.debug("Deleting by date is ended");
    }

}
