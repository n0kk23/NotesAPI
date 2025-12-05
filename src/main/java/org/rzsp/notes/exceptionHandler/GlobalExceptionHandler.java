package org.rzsp.notes.exceptionHandler;

import lombok.extern.log4j.Log4j2;
import org.rzsp.notes.exceptions.NoteNotFoundException;
import org.rzsp.notes.exceptions.dto.ErrorResponseToHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final static String INTERNAL_SERVER_ERROR = "Something wrong with server, please try again later";
    private final static String NOTE_NOT_FOUND = "Note is not found. Please, check that note ID is correct";
    private final static String INVALID_DATE_FORMAT = "Invalid date format. Must be yyyy-MM-dd";
    private final static String NOT_FOUND_PATH = "Requested path is not found";

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseToHandler> handleMismatchException(
            MethodArgumentTypeMismatchException e
    ) {
        return build(HttpStatus.BAD_REQUEST, INVALID_DATE_FORMAT);
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

    private String getExceptionMessage(MethodArgumentNotValidException e) {
        return e.getBindingResult()
                .getFieldErrors().stream()
                .findFirst()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .orElse("Validation error");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseToHandler> handleException(
            Exception e
    ) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR);
    }

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
