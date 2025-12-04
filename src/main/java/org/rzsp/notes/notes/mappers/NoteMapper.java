package org.rzsp.notes.notes.mappers;

import org.rzsp.notes.notes.NoteEntity;
import org.rzsp.notes.notes.dto.NoteCreateRequest;
import org.rzsp.notes.notes.dto.NoteResponse;
import org.springframework.stereotype.Component;

@Component
public class NoteMapper {

    public NoteEntity toEntity(NoteCreateRequest request) {
        return NoteEntity.builder()
                .description(request.description())
                .date(request.date())
                .build();
    }

    public NoteResponse toResponse(NoteEntity entity) {
        return new NoteResponse(
                entity.getNumber(),
                entity.getId(),
                entity.getDescription()
        );
    }

}
