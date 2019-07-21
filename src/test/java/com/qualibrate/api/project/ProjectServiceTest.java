package com.qualibrate.api.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;

import com.qualibrate.ApplicationTests;
import com.qualibrate.api.project.repository.Project;
import com.qualibrate.api.project.repository.ProjectDTO;
import com.qualibrate.api.project.service.ProjectService;

/**
 * @author <a href="mailto:chetan.shirke1688@gmail.com">Chetan Shirke</a>
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DirtiesContext
public class ProjectServiceTest extends ApplicationTests {

    @Autowired
    private ProjectService projectService;

    private Project p = new Project(Long.valueOf(100), "PROJ-NAME-01", "PROJ-01-Description", "PROJ-CODE-01", false);

    @Test
    @DirtiesContext
    public void aTestSaveMethod() {
        ProjectDTO savedDto = projectService.createProject(p);
        assertNotNull(savedDto);
        assertEquals(savedDto.getCode(), p.getCode());
        assertNotNull(savedDto.getTimestamp());
    }

    @Test
    public void cTestGetPaginatedData() {
        Page<ProjectDTO> result = projectService.getProject(new PageRequest(0, 10));
        assertEquals(10, result.getContent().size());

        result = projectService.getProject(new PageRequest(1, 200));
        assertEquals(0, result.getContent().size());

    }
}
