package org.rzsp.notes.exceptionhandler;

import lombok.extern.log4j.Log4j2;
import org.rzsp.notes.exceptions.DateNotHaveAnyNote;
import org.rzsp.notes.exceptions.NoteNotFoundException;
import org.rzsp.notes.exceptions.dto.ErrorResponseToHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.*;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final static String NOTE_NOT_FOUND = "Note with this ID is not exist";
    private final static String INVALID_DATE_FORMAT = "Invalid date request. Must be yyyy-MM-dd format and had correct dates";
    private final static String NOT_FOUND_PATH = "Requested path is not found";
    private final static String DATE_NOT_HAVE_ANY_NOTE = "This date doesn't have any note";

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponseToHandler> handleMethodNotAllowed(
            HttpRequestMethodNotSupportedException e
    ) {
        return build(HttpStatus.METHOD_NOT_ALLOWED, NOT_FOUND_PATH);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseToHandler> handleMismatchException(
            MethodArgumentTypeMismatchException e
    ) {
        return build(HttpStatus.BAD_REQUEST, INVALID_DATE_FORMAT);
    }

    @ExceptionHandler(DateNotHaveAnyNote.class)
    public ResponseEntity<ErrorResponseToHandler> handleDateNotHaveAnyNoteException(
            DateNotHaveAnyNote e
    ) {
        return build(HttpStatus.NOT_FOUND, DATE_NOT_HAVE_ANY_NOTE);
    }

    @ExceptionHandler(NoteNotFoundException.class)
    public ResponseEntity<ErrorResponseToHandler> handleNoteNotFoundException(
            NoteNotFoundException e
    ) {
        return build(HttpStatus.NOT_FOUND, NOTE_NOT_FOUND);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponseToHandler> handleNoHandlerFoundException(
            NoHandlerFoundException e
    ) {
        return build(HttpStatus.NOT_FOUND, NOT_FOUND_PATH);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseToHandler> handleNotValidArgumentException(
            MethodArgumentNotValidException e
    ) {
        return build(HttpStatus.BAD_REQUEST, getExceptionMessage(e));
    }

    /**
     * Выдает сообщение исключения.
     * Нужен для раскрытия сообщения получаемого через валидацию Jakarta
     *
     * @param e исключение
     * @return String - сообщение, которое передает исключение
     */
    private String getExceptionMessage(MethodArgumentNotValidException e) {
        return e.getBindingResult()
                .getFieldErrors().stream()
                .findFirst()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .orElse("Validation error");
    }

    /**
     * Строит ResponseEntity с ответом исключения.
     *
     * @param status статус исключения
     * @param message сообщение исключения
     * @return {@link ErrorResponseToHandler} - ответ исключения
     */
    private ResponseEntity<ErrorResponseToHandler> build(
            HttpStatus status,
            String message
    ) {
        return ResponseEntity
                .status(status)
                .body(new ErrorResponseToHandler(
                        status.value(),
                        status.name(),
                        message
                ));
    }

}
