package com.qualibrate.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when request input is not valid or missing
 * <a href="mailto:chetan.shirke1688@pb.com">Chetan Shirke</a>
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRequestException extends QualibrateServiceException {

    public static final long serialVersionUID = 1L;

    public InvalidRequestException(String message) {
        super(message);
    }

    public InvalidRequestException(String message, String code) {
        super(message, code);
    }

    public InvalidRequestException(Throwable t, String message, String code) {
        super(t, message, code);
    }
}
