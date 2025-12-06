package org.rzsp.notes.exceptions.dto;

import lombok.Builder;

/**
 * Класс преобразователь ошибки в ответ
 * @param status статус ошибки
 * @param error название ошибки
 * @param message сообщение ошибки
 */
@Builder
public record ErrorResponseToHandler(
        int status,
        String error,
        String message
) {
}
