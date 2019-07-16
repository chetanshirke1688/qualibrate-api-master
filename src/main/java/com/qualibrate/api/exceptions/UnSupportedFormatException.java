package com.qualibrate.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown if the format is not supported.
 * @author <a href="mailto:chetan.shirke1688@pb.com">Chetan Shirke</a>
 */
@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
public class UnSupportedFormatException extends QualibrateServiceException {

    public static final long serialVersionUID = 1L;

    public UnSupportedFormatException(String message) {
        super(message);
    }

    public UnSupportedFormatException(String message, String code) {
        super(message, code);
    }

    public UnSupportedFormatException(Throwable t, String message, String code) {
        super(t, message, code);
    }
}
