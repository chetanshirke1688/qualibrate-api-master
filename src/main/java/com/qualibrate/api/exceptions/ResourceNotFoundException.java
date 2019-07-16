package com.qualibrate.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when resource could not be found
 * @author <a href="mailto:chetan.shirke1688@pb.com">Chetan Shirke</a>
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends QualibrateServiceException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, String code) {
        super(message, code);
    }


    public ResourceNotFoundException(Throwable t, String message) {
        super(t, message);
    }

    public ResourceNotFoundException(Throwable t, String message, String code) {
        super(t, message, code);
    }
}
