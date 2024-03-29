package com.qualibrate.api.project.repository;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="mailto:chetan.shirke1688@gmail.com">Chetan Shirke</a>
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @ApiModelProperty(name = "id", dataType = "String", required = false,
            notes = "Unique identifier", example = "1")
    private Long id;

    @NotNull
    @Size(min = 1, max = 20)
    @ApiModelProperty(name = "name", dataType = "String", required = true,
        notes = "Project name", example = "CRM Project")
    private String name;

    @Size(min = 1, max = 100)
    @ApiModelProperty(name = "description", dataType = "String", required = false,
        notes = "Details about project content", example = "Automation suite for release 101-B")
    private String description;

    @Size(min = 0, max = 20)
    @ApiModelProperty(name = "code", dataType = "String", required = false,
        notes = "Generic identifier", example = "PRJ-001")
    private String code;


    @ApiModelProperty(name = "active", dataType = "String", required = false,
            notes = "In archive?", example = "true/false")
    private boolean active;

}
