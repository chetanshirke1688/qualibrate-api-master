package com.qualibrate.api.commons.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.qualibrate.api.commons.transformer.EntityToDtoTransformer;
import com.qualibrate.api.file.repository.FileDTO;
import com.qualibrate.api.file.repository.FileRecord;
import com.qualibrate.api.project.repository.ProjectDTO;
import com.qualibrate.api.project.repository.ProjectRecord;
import com.qualibrate.api.user.repository.UserDTO;
import com.qualibrate.api.user.repository.UserRecord;

/**
 * Entity transformers are used to convert entities to data objects.
 *
 * @author <a href="mailto:chetan.shirke1688@gmail.com">Chetan Shirke</a>
 *
 */
@Configuration
public class EntityConverterConfig {

    @Bean(name = "userEntityToDtoConverter")
    public EntityToDtoTransformer<UserRecord, UserDTO> userEntityToDtoConverter() {
        return new EntityToDtoTransformer<UserRecord, UserDTO>(new UserDTO());
    }

    @Bean(name = "projectEntityToDtoConverter")
    public EntityToDtoTransformer<ProjectRecord, ProjectDTO> projectEntityToDtoConverter() {
        return new EntityToDtoTransformer<ProjectRecord, ProjectDTO>(new ProjectDTO());
    }

    @Bean(name = "fileEntityToDtoConverter")
    public EntityToDtoTransformer<FileRecord, FileDTO> fileEntityToDtoConverter() {
        return new EntityToDtoTransformer<FileRecord, FileDTO>(new FileDTO());
    }
}
