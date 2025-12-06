package org.rzsp.notes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rzsp.notes.days.DayService;
import org.rzsp.notes.days.dto.DayNotesResponse;
import org.rzsp.notes.exceptions.DateNotHaveNoteException;
import org.rzsp.notes.exceptions.NoteNotFoundException;
import org.rzsp.notes.notes.NoteEntity;
import org.rzsp.notes.notes.NoteRepository;
import org.rzsp.notes.notes.NoteService;
import org.rzsp.notes.notes.dto.NoteCreateRequest;
import org.rzsp.notes.notes.dto.NoteResponse;
import org.rzsp.notes.notes.mappers.NoteMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private DayService dayService;

    @Mock
    private NoteMapper noteMapper;

    @InjectMocks
    private NoteService noteService;

    private LocalDate date;
    private NoteEntity noteEntity;
    private NoteResponse noteResponse;

    @BeforeEach
    void setUp() {
        date = LocalDate.of(2025, 12, 6);
        noteEntity = new NoteEntity();
        noteEntity.setId(1L);
        noteEntity.setNumber(1L);
        noteEntity.setDescription("Test note");
        noteEntity.setDate(date);

        noteResponse = new NoteResponse(1L, 1L, "Test note");
    }

    @Test
    void testCorrectGetNotesByDay() {
        when(noteRepository.findAllByDateOrderByNumberAsc(date)).thenReturn(List.of(noteEntity));
        when(noteMapper.toResponse(noteEntity)).thenReturn(noteResponse);
        when(dayService.getInformationAboutHoliday(date)).thenReturn(DayNotesResponse.builder()
                .isHoliday(false)
                .holidayName(null)
                .notes(List.of())
                .build());

        DayNotesResponse response = noteService.getNotesByDate(date);

        assertThat(response).isNotNull();
        assertThat(response.notes()).hasSize(1);
        verify(noteRepository).findAllByDateOrderByNumberAsc(date);
        verify(noteMapper).toResponse(noteEntity);
        verify(dayService).getInformationAboutHoliday(date);
    }

    @Test
    void testCorrectCreationNoteAndCheckThatItCorrectlyGetIncrement() {
        NoteCreateRequest request = new NoteCreateRequest(date, "New Note");

        when(noteRepository.findTopByDateOrderByNumberDesc(date)).thenReturn(Optional.of(noteEntity));
        when(noteMapper.toEntity(request)).thenReturn(new NoteEntity());

        noteService.createNote(request);

        ArgumentCaptor<NoteEntity> captor = ArgumentCaptor.forClass(NoteEntity.class);
        verify(noteRepository).save(captor.capture());

        NoteEntity saved = captor.getValue();
        assertThat(saved.getNumber()).isEqualTo(noteEntity.getNumber() + 1);
        verify(noteMapper).toEntity(request);
    }

    @Test
    void testExceptionWhenNoteWithThisIdIsNotExist() {
        Long id = 1L;
        when(noteRepository.existsById(id)).thenReturn(false);

        assertThrows(NoteNotFoundException.class, () -> noteService.deleteNoteById(id));
        verify(noteRepository, never()).deleteById(any());
    }

    @Test
    void testCorrectDeletingNoteById() {
        Long id = 1L;
        when(noteRepository.existsById(id)).thenReturn(true);

        noteService.deleteNoteById(id);

        verify(noteRepository).deleteById(id);
    }

    @Test
    void testExceptionWhenDateDoesntHaveAnyNotes() {
        when(noteRepository.existsByDate(date)).thenReturn(false);

        assertThrows(DateNotHaveNoteException.class, () -> noteService.deleteAllNotesByDate(date));
        verify(noteRepository, never()).deleteAllByDate(any());
    }

    @Test
    void testCorrectDeletingAllNotesByDate() {
        when(noteRepository.existsByDate(date)).thenReturn(true);

        noteService.deleteAllNotesByDate(date);

        verify(noteRepository).deleteAllByDate(date);
    }

}
