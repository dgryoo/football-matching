package api.common.exception.custom;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SourceNotFoundException extends CustomException {

    public SourceNotFoundException() {
        super(HttpStatus.NOT_FOUND, "리소스를 찾을 수 없습니다.", "정보를 등록한 후 다시 이용해주세요.");
    }

    public SourceNotFoundException(String title, String message) {
        super(HttpStatus.NOT_FOUND, title, message);
    }
}
