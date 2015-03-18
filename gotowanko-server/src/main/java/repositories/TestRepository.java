package repositories;

import entities.TestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by michal on 17.03.15.
 */
public interface TestRepository extends JpaRepository<TestEntity, Long>, TestRepositoryCustom {

        @Query("SELECT tr FROM TestEntity tr WHERE tr.key = ?1")
         public TestEntity getByIdUsingQueryAnnotation(Long id);
}