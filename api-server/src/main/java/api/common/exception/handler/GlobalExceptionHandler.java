package api.common.exception.handler;

import api.common.exception.custom.BadRequestException;
import api.common.exception.custom.CustomErrorResponse;
import api.common.exception.custom.CustomException;
import api.common.exception.custom.InternalServerErrorException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // Custom Exception
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomErrorResponse> handleException(CustomException e, HttpServletRequest httpServletRequest) {

        // ResponseEntity body에 CustomErrorResponse로 변경 후 리턴
        return ResponseEntity
                .status(e.getHttpStatus().value())
                .body(e.toCustomErrorResponse());
    }

    // Spring Validation Exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse> handleException(MethodArgumentNotValidException e) {
        // Custom Exception 생성
        BadRequestException badRequestException = new BadRequestException();

        // MethodArgumentNotValidException의 에러 원인 주입
        e.getBindingResult()
                .getFieldErrors()
                .forEach(error -> badRequestException.addErrors(error.getField(), error.getDefaultMessage()));

        // ResponseEntity body에 CustomErrorResponse로 변경 후 리턴
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(badRequestException.toCustomErrorResponse());
    }


    // Unknown Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handleException(Exception e, HttpServletRequest httpServletRequest) {
        // 알려지지 않은 예외의 호출 정보 출력
        logger.debug("Method : {}", httpServletRequest.getMethod());
        logger.debug("Path : {}", httpServletRequest.getRequestURI());

        // ResponseEntity body에 CustomErrorResponse로 변경 후 리턴
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new InternalServerErrorException().toCustomErrorResponse());
    }

}
