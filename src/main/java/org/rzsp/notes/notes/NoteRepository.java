package org.rzsp.notes.notes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<NoteEntity, Long> {
    List<NoteEntity> findAllByDateOrderByNumberAsc(LocalDate date);
    void deleteAllByDate(LocalDate date);
    Optional<NoteEntity> findTopByDateOrderByNumberDesc(LocalDate date); // Необходим для генерации номера
}
