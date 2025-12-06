package org.rzsp.notes.notes.dto;

/**
 * Ответ клиенту в виде DTO объекта заметки
 * @param number номер заметки
 * @param id уникальный идентификатор заметки
 * @param description сообщение заметки
 */
public record NoteResponse(
        Long number,
        Long id,
        String description
) {
}
