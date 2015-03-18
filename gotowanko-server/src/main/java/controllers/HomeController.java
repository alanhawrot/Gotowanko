package controllers;

import entities.TestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repositories.TestRepository;
import repositories.TestRepositoryImpl;

/**
 * Created by alanhawrot on 17.03.15.
 */
@RestController
public class HomeController {

    @Autowired
    TestRepository testRepository;

    @RequestMapping(value = {"/", "/home"})
    public String home() {
        TestEntity testEntity = new TestEntity();
        testRepository.save(testEntity);
        return "OK";
    }

    @RequestMapping(value = "/test")
    public TestEntity getFirstTestEntity() {
        return testRepository.getOne((long) 1);
    }
    @RequestMapping(value = "/byid/{id}")
    public String getFirstTestEntity(@PathVariable("id") Long id) {
        return testRepository.getByIdUsingQueryAnnotation(id).toString();
    }
}
