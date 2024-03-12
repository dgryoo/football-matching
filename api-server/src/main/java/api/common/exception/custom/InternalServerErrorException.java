package api.common.exception.custom;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InternalServerErrorException extends CustomException {

    public InternalServerErrorException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류입니다.", "관리자에게 문의하세요.");
    }

    public InternalServerErrorException(String title, String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, title, message);
    }
}
