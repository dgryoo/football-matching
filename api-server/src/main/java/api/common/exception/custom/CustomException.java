package api.common.exception.custom;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class CustomException extends RuntimeException {

    protected HttpStatus httpStatus;
    protected String title;
    protected String message;
    protected Map<String, String> errors = new HashMap<>();

    public CustomException(HttpStatus httpStatus, String title, String message) {
        super();
        this.httpStatus = httpStatus;
        this.title = title;
        this.message = message;
    }

    public CustomErrorResponse toCustomErrorResponse() {
        CustomError error = new CustomError(title, message, errors);
        CustomErrorResponse response = new CustomErrorResponse(
                httpStatus.value(), error);
        return response;
    }

    public void addErrors(String k, String v) {
        errors.put(k, v);
    }
}
