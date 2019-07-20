package com.qualibrate.api.user.repository;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.qualibrate.api.project.repository.ProjectRecord;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author <a href="mailto:chetan.shirke1688@gmail.com">Chetan Shirke</a>
 *
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class UserDTO extends User implements com.qualibrate.api.commons.transformer.Dto {

    protected Long id;

    private boolean active;

    private String uid;

    private String provider;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date activatedAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date loginAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date logoutAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date timestamp;

    @JsonIgnore
    private Set<ProjectRecord> projects;

}
