package org.rzsp.notes.notes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface NoteRepository extends JpaRepository<NoteEntity, Long> {
    List<NoteEntity> findAllByDate(LocalDate date);
    void deleteAllByDate(LocalDate date);
}
