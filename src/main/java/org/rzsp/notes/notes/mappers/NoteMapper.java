package org.rzsp.notes.notes.mappers;

import lombok.extern.log4j.Log4j2;
import org.rzsp.notes.notes.NoteEntity;
import org.rzsp.notes.notes.dto.NoteCreateRequest;
import org.rzsp.notes.notes.dto.NoteResponse;
import org.springframework.stereotype.Component;

/**
 * Вспомогательный класс для преобразования заметок.
 */
@Log4j2
@Component
public class NoteMapper {

    /**
     * Преобразует заметку в сущность на основе запроса на создание.
     *
     * @param request запрос с данными, необходимыми для создания заметки
     * @return {@link NoteEntity} - сущность заметки
     */
    public NoteEntity toEntity(NoteCreateRequest request) {
        log.debug("Start mapping {} into NoteEntity", request.toString());

        return NoteEntity.builder()
                .description(request.description())
                .date(request.date())
                .build();
    }

    /**
     * Преобразует заметку в ответ на основе сущности.
     *
     * @param entity сущность, которая преобразуется в ответ клиенту
     * @return {@link NoteResponse} - ответ заметки для клиента
     */
    public NoteResponse toResponse(NoteEntity entity) {
        log.debug("Start mapping {} into NoteResponse", entity.toString());

        return new NoteResponse(
                entity.getNumber(),
                entity.getId(),
                entity.getDescription()
        );
    }

}
