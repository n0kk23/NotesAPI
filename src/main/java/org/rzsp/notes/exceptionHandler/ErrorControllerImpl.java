package org.rzsp.notes.exceptionHandler;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.rzsp.notes.exceptions.dto.ErrorResponseToHandler;
import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorControllerImpl implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<ErrorResponseToHandler> handleError(
            HttpServletRequest request
    ) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        int statusCode = status != null ? Integer.parseInt(status.toString()) : 500;
        HttpStatus httpStatus = HttpStatus.valueOf(statusCode);

        String message = switch (httpStatus) {
            case NOT_FOUND -> "Path is not found";
            case BAD_REQUEST -> "Bad request";
            case METHOD_NOT_ALLOWED -> "You can't enter to this path";
            default -> "Something wrong with server, please try again later";
        };

        return ResponseEntity.status(httpStatus)
                .body(new ErrorResponseToHandler(
                        httpStatus.value(),
                        httpStatus.name(),
                        message
                ));
    }

}
