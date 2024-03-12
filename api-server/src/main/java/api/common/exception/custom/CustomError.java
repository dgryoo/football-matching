package api.common.exception.custom;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
public class CustomError {
    private final String title;

    private final String message;

    private final Map<String,String> errors;

}
