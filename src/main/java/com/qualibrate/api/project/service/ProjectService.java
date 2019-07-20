package com.qualibrate.api.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qualibrate.api.commons.transformer.EntityToDtoTransformer;
import com.qualibrate.api.exceptions.ErrorCodes;
import com.qualibrate.api.exceptions.InvalidRequestException;
import com.qualibrate.api.project.repository.Project;
import com.qualibrate.api.project.repository.ProjectDTO;
import com.qualibrate.api.project.repository.ProjectRecord;
import com.qualibrate.api.project.repository.ProjectRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="mailto:chetan.shirke1688@gmail.com">Chetan Shirke</a>
 *
 * Service Layer for Project
 */
@Service
@Slf4j
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private EntityToDtoTransformer<ProjectRecord, ProjectDTO> projectEntityToDtoConverter;

    public Page<ProjectDTO> getProject(Pageable pageable) {
        return projectRepo.findAll(pageable).map(projectEntityToDtoConverter);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ProjectDTO createProject(Project project) {
        ProjectRecord saved;
        try {
            saved = projectRepo.save(new ProjectRecord(project));
        } catch (DataIntegrityViolationException e) {
            log.error("project with code {} already exists", project.getCode());
            throw new InvalidRequestException("error creating new project. code must be unique",
                ErrorCodes.ProjectEntityAPIErrors.PROJECTENTITY_ALREADY_EXISTS);
        }
        return projectEntityToDtoConverter.apply(saved);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ProjectDTO assignToUser(Long userId, Long projectId) {
        ProjectRecord p = findOne(projectId);
        p.setUserId(userId);
        return projectEntityToDtoConverter.apply(projectRepo.save(p));
    }

    private ProjectRecord findOne(Long projectId) {
        ProjectRecord p = projectRepo.getOne(projectId);
        return p;
    }
}
