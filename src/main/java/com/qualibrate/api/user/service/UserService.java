package com.qualibrate.api.user.service;

import java.util.ArrayList;
import java.util.List;

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
import com.qualibrate.api.exceptions.ResourceNotFoundException;
import com.qualibrate.api.project.repository.ProjectDTO;
import com.qualibrate.api.project.repository.ProjectRecord;
import com.qualibrate.api.project.service.ProjectService;
import com.qualibrate.api.user.repository.User;
import com.qualibrate.api.user.repository.UserDTO;
import com.qualibrate.api.user.repository.UserRecord;
import com.qualibrate.api.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="mailto:chetan.shirke1688@gmail.com">Chetan Shirke</a>
 *
 * Service Layer for User Entity
 *
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private EntityToDtoTransformer<UserRecord, UserDTO> userEntityToDtoConverter;

    @Autowired
    private EntityToDtoTransformer<ProjectRecord, ProjectDTO> projectEntityToDtoConverter;

    public Page<UserDTO> getUser(Pageable pageable) {
        return userRepository.findAll(pageable).map(userEntityToDtoConverter);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO getUser(Long id) {
        UserRecord user = userRepository.getOne(id);
        if (user == null)
            throw new ResourceNotFoundException("User not found");
        return userEntityToDtoConverter.apply(user);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public User createUser(User user) {
        UserRecord saved;
        try {
            saved = userRepository.save(new UserRecord(user));
        } catch (DataIntegrityViolationException e) {
            log.error("user with email {} already exists", user.getEmail());
            throw new InvalidRequestException("Integrity violation. "
                + "user with email " + user.getEmail() + " already exists.",
                ErrorCodes.UserEntityAPIErrors.USERENTITY_ALREADY_EXISTS);
        }
        return userEntityToDtoConverter.apply(saved);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void deleteUser(Long id) {
        if (userRepository.existsById(id))
            userRepository.deleteById(id);
        else
            throw new ResourceNotFoundException("User not found", ErrorCodes.NOT_FOUND_EXCEPTION);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO updateUser(Long id, UserDTO userDto) {
        if (userDto.getId() != null && !id.equals(userDto.getId()))
            throw new InvalidRequestException("unexpected userId in request body : " + userDto.getId());
        UserRecord userRecord = userRepository.getOne(id);
        if (userRecord == null)
            throw new ResourceNotFoundException("User not found");
        //nonNullCopyBeanUtils.copyNonNullProperties(userRecord, userDto);
        System.out.println(userRecord);
        return userEntityToDtoConverter.apply(userRepository.save(userRecord));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<ProjectDTO> getProjectFor(Long id) {
        List<ProjectDTO> list = new ArrayList<ProjectDTO>();
        if (getUser(id).getProjects() != null) {
            getUser(id).getProjects().forEach(x -> list.add(projectEntityToDtoConverter.apply(x)));
        } else {
            throw new ResourceNotFoundException("Project details does"
                  + " not exists for User: " + id,
                  ErrorCodes.UserEntityAPIErrors.USERENTITY_VALUE_RESOURCE_NOT_FOUND);
        }
        return list;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ProjectDTO assignProject(Long userId, Long projectId) {
        if (!userRepository.existsById(userId))
            throw new ResourceNotFoundException("User not found");
        return projectService.assignToUser(userId, projectId);
    }
}
