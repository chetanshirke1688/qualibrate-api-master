package com.qualibrate.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown if underlying resource is not available
 * @author <a href="mailto:chetan.shirke1688@pb.com">Chetan Shirke</a>
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ResourceNotAvailableException extends QualibrateServiceException {

    public static final long serialVersionUID = 1L;

    public ResourceNotAvailableException(String message) {
        super(message);
    }

    public ResourceNotAvailableException(String message, String code) {
        super(message, code);
    }

    public ResourceNotAvailableException(Throwable t, String message, String code) {
        super(t, message, code);
    }
}
