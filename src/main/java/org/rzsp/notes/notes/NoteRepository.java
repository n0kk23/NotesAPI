package org.rzsp.notes.notes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Репозиторий заметок.
 */
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {
    /**
     * Возвращает сущность заметок в порядке их номера от меньшего к большему.
     *
     * @param date дата, заметки которой необходимо вывести
     * @return {@link NoteEntity} - заметки в порядке номера от меньшего к большому
     */
    List<NoteEntity> findAllByDateOrderByNumberAsc(LocalDate date);

    /**
     * Удаляет все заметки из репозитория, которые соотвествуют принимаемой в аргументе дате.
     *
     * @param date дата, заметки которой необходимо удалить
     */
    void deleteAllByDate(LocalDate date);

    /**
     * Находит сущность заметки с самым большим номером.
     * Необходим для реализации автоинкремента номеров у заметок определенной даты
     *
     * @param date дата, у которой мы найти заметку с самым большим номером
     * @return {@link NoteEntity} - сущность заметки с самым большим номером
     */
    Optional<NoteEntity> findTopByDateOrderByNumberDesc(LocalDate date);

    /**
     * Проверяет существуют ли у указанной даты заметки и возвращает булевый результат.
     *
     * @param date дата, у которой мы хотим узнать есть ли заметки или нет
     * @return boolean - существуют ли заметки с указанной датой
     */
    boolean existsByDate(LocalDate date);
}
