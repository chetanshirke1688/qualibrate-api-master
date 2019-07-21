package com.qualibrate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.qualibrate.api.Application;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest
    .WebEnvironment.DEFINED_PORT)
public class ApplicationTests {

    @Test
    public void contextLoads() {
    }

}
