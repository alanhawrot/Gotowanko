package repositories;

import entities.TestEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by michal on 17.03.15.
 */

@Repository
@Transactional
public class TestRepository /*extends SimpleJpaRepository<TestEntity, Long> */{

    @PersistenceContext
    EntityManager entityManager;

    public List<TestEntity> all() {
        return entityManager.createQuery("FROM TestEntity").getResultList();
    }

    public void create(TestEntity testEntity) {
        entityManager.persist(testEntity);
    }
}
