package com.qualibrate.api.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.stream.IntStream;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.qualibrate.ControllerTest;
import com.qualibrate.api.project.repository.Project;
import com.qualibrate.api.project.repository.ProjectDTO;
import com.qualibrate.api.project.service.ProjectService;

/**
 * @author <a href="mailto:chetan.shirke1688@gmail.com">Chetan Shirke</a>
 * Functional Test Cases for Project Entity
 */

@SuppressWarnings("unchecked")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DirtiesContext
public class ProjectControllerTest extends ControllerTest {

    @Autowired
    ProjectService projectService;

    /**
     * Test POST and GET for project posts 10 parallel requests to run test suit
     * quicker
     */
    @Test
    public void aVerifyProjectPost() {
        int requestNumbers = 10;
        IntStream.range(0, requestNumbers).parallel().forEach(x -> {
                Project p = new Project(Long.valueOf(x), "PROJ-NAME-"
                    + x, "PROJ-Description-" + x, "PROJ-CODE-" + x, false);
                HttpEntity<Project> entity = new HttpEntity<Project>(p, getHeaders());
                ResponseEntity<ProjectDTO> response = post("/api/v1/project", entity, ProjectDTO.class);
                assertNotNull(response);
                assertEquals(HttpStatus.CREATED, response.getStatusCode());
            });
        // check if 10 entities are created or not.
        Page<ProjectDTO> projects = projectService.getProject(new PageRequest(0, 10));
        assertNotNull(projects);
        assertEquals(10, projects.getContent().size());
    }

    /**
     * verify invalid input causes 400 (Bad Request) missing "project name" in
     * request
     */
    @Test
    public void bVerifyProjectRequestValidations() {
        Project p = new Project(Long.valueOf(100), null, "PROJ-Description-100", "PROJ-CODE-100", false);
        // missing project name in request
        HttpEntity<Project> entity = new HttpEntity<Project>(p, getHeaders());
        ResponseEntity<ProjectDTO> response = post("/api/v1/project", entity, ProjectDTO.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
