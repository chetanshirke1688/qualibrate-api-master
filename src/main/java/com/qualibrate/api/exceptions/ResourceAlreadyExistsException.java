package com.qualibrate.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown if the resource being created already exists
 * @author <a href="mailto:chetan.shirke1688@pb.com">Chetan Shirke</a>
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceAlreadyExistsException extends QualibrateServiceException {

    public static final long serialVersionUID = 1L;

    public ResourceAlreadyExistsException(String message) {
        super(message);
    }

    public ResourceAlreadyExistsException(String message, String code) {
        super(message, code);
    }

    public ResourceAlreadyExistsException(Throwable t, String message, String code) {
        super(t, message, code);
    }
}
