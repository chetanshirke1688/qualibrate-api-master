package com.qualibrate.api.file.repository;

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
@Entity(name = "files")
public class FileRecord implements com.qualibrate.api.commons.transformer.Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String uuid;

    private String name;

    private String mime;

    private String path;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date timestamp;

    // due to hibernate convention set db column name on foreign key
    @Column(name = "user_id")
    private Long userId;

    public FileRecord(File file) {
        this.id = file.getId();
        this.mime = file.getMime();
        this.name = file.getName();
        this.timestamp = new Date();
        this.uuid = file.getUuid();
        this.path = file.getPath();
        this.userId = file.getUserId();
    }
}
