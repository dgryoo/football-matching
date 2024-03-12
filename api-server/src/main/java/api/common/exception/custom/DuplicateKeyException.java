package api.common.exception.custom;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DuplicateKeyException extends CustomException {

    public DuplicateKeyException() {
        super(HttpStatus.CONFLICT, "중복된 리소스 입니다.", "이미 등록된 정보가 있습니다.");
    }

    public DuplicateKeyException(String title, String message) {
        super(HttpStatus.CONFLICT, title, message);
    }
}
