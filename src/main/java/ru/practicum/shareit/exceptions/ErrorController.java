package ru.practicum.shareit.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorController {
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({ValidationException.class})
    public ErrorResponse handleValidationExceptions(final Exception ex) {
        log.error("Данная сущность уже существует: {}", ex.getMessage(), ex);
        return new ErrorResponse("Данная сущность уже существует: " + ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorResponse handleArgumentNotValidExceptions(final Exception ex) {
        log.error("Ошибка некорректного запроса: {}", ex.getMessage(), ex);
        return new ErrorResponse("Некорректные параметры: " + ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class})
    public ErrorResponse handleNotFoundExceptions(final Exception ex) {
        log.error("Не найдено: {}", ex.getMessage(), ex);
        return new ErrorResponse("Не найдено: " + ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(final Exception ex) {
        log.error("Произошла непредвиденная ошибка: {}", ex.getMessage(), ex);
        return new ErrorResponse("Произошла непредвиденная ошибка. Пожалуйста, попробуйте позже.");
    }
}
