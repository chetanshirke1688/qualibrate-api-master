package com.qualibrate.api.file.repository;

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
public class File {

    @ApiModelProperty(name = "id", dataType = "String", required = false,
            notes = "Unique identifier", example = "1")
    private Long id;

    @NotNull
    @Size(min = 1, max = 20)
    @ApiModelProperty(name = "name", dataType = "String", required = true,
        notes = "File name", example = "readme.txt")
    private String name;

    @Size(min = 1, max = 100)
    @ApiModelProperty(name = "uuid", dataType = "String", required = false,
        notes = "unique identifier to identify files (Unique filename)", example = "ASASDASDasdasdarar.txt")
    private String uuid;

    @Size(min = 0, max = 500)
    @ApiModelProperty(name = "path", dataType = "String", required = false,
        notes = "File path", example = "s3://test-bucket/uploads/images/test.png")
    private String path;

    @ApiModelProperty(name = "mime", dataType = "String", required = false,
            notes = "mime type of file", example = "application/pdf")
    private String mime;

    @ApiModelProperty(name = "user_id", dataType = "String", required = false,
            notes = "user id", example = "application/pdf")
    private Long userId;
}
