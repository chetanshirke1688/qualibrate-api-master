package com.qualibrate.api.exceptions.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

/**
 * Error response
 * @author <a href="mailto:chetan.shirke1688@pb.com">Chetan Shirke</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    @Singular
    private List<Error> errors = new ArrayList<>();
}
