package api.common.exception.custom;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BadRequestException extends CustomException {
    public BadRequestException() {
        super(HttpStatus.BAD_REQUEST, "올바르지 않은 형식의 요청입니다.", "요청을 확인해 주세요.");
    }

    public BadRequestException(String title, String message) {
        super(HttpStatus.BAD_REQUEST, title, message);
    }
}
