package com.qualibrate.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when request input is not valid or missing
 * @author <a href="mailto:chetan.shirke1688@pb.com">Chetan Shirke</a>
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnAuthorizedException extends QualibrateServiceException {

    public static final long serialVersionUID = 1L;

    public UnAuthorizedException(String message) {
        super(message);
    }

    public UnAuthorizedException(String message, String code) {
        super(message, code);
    }

    public UnAuthorizedException(Throwable t, String message, String code) {
        super(t, message, code);
    }
}
