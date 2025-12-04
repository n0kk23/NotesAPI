package org.rzsp.notes.notes;

import lombok.extern.log4j.Log4j2;
import org.rzsp.notes.days.DayService;
import org.rzsp.notes.days.dto.DayNotesResponse;
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

    public NoteService(
            NoteRepository noteRepository,
            DayService dayService,
            NoteMapper noteMapper
    ) {
        this.noteRepository = noteRepository;
        this.dayService = dayService;
        this.noteMapper = noteMapper;
    }

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

    public void deleteNoteById(
            Long id
    ) {
        log.debug("Start deleting note by id: {}", id);

        noteRepository.deleteById(id);

        log.debug("Deleting by id is ended");
    }

    @Transactional
    public void deleteAllNotesByDate(
            LocalDate date
    ) {
        log.debug("Start deleting all notes by date: {}", date);

        noteRepository.deleteAllByDate(date);

        log.debug("Deleting by date is ended");
    }

}
