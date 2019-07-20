package com.qualibrate.api.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Base exception
 * @author <a href="mailto:chetan.shirke1688@pb.com">Chetan Shirke</a>
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QualibrateServiceException extends RuntimeException {

    public static final long serialVersionUID = 1L;
    private String code;

    public QualibrateServiceException(String message) {
        super(message);
    }

    public QualibrateServiceException(String message, String code) {
        super(message);
        this.code = code;
    }

    public QualibrateServiceException(Throwable t, String message) {
        super(message, t);
    }

    public QualibrateServiceException(Throwable t, String message, String code) {
        super(message, t);
        this.code = code;
    }
}
