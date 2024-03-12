package api.common.exception.custom;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomErrorResponse {

    private final Integer statusCode;

    private final CustomError error;

}
