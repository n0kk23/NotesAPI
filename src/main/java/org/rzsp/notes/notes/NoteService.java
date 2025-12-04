package org.rzsp.notes.notes;

import lombok.extern.log4j.Log4j2;
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
    private final NoteMapper noteMapper;

    public NoteService(
            NoteRepository noteRepository,
            NoteMapper noteMapper
    ) {
        this.noteRepository = noteRepository;
        this.noteMapper = noteMapper;
    }

    public List<NoteResponse> getAllNotesByDate(LocalDate date) {
        log.debug("Start getting all notes by date: {}", date);

        return noteRepository.findAllByDate(date).stream()
                .map(noteMapper::toResponse)
                .toList();
    }

    public void createNote(
            NoteCreateRequest request
    ) {
        log.debug("Creating note by request: {}", request);

        NoteEntity newNote = noteMapper.toEntity(request);
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
