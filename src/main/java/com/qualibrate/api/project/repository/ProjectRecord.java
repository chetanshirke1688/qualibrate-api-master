package com.qualibrate.api.project.repository;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="mailto:chetan.shirke1688@gmail.com">chetan.shirke1688@gmail.com</a>
 *
 * Project Entity represents Database table "project"
 */
@Data
@NoArgsConstructor
@Entity(name = "project")
public class ProjectRecord implements com.qualibrate.api.commons.transformer.Entity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private String description;

    private String code;

    private String icon;

    private boolean active;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date timestamp;

    // due to hibernate convention set db column name on foreign key
    @Column(name = "user_id")
    private Long userId;

    public ProjectRecord(Project project) {
        this.name = project.getName();
        this.description = project.getDescription();
        this.timestamp = new Date();
        this.active = project.isActive();
        this.code = project.getCode();
        this.id = project.getId();
    }

    public ProjectRecord(ProjectDTO project) {
        this.name = project.getName();
        this.description = project.getDescription();
        this.timestamp = new Date();
        this.active = project.isActive();
        this.code = project.getCode();
        this.id = project.getId();
    }
}
