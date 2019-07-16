package com.qualibrate.api.exceptions.model;

import lombok.Data;

/**
 * @author <a href="mailto:chetan.shirke1688@gmail.com">Chetan Shirke</a>
 */
@Data
public class Error {
    private String message;
    private String code;
}
