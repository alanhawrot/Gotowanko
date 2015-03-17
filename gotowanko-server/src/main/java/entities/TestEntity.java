package entities;

import javax.annotation.Generated;
import javax.persistence.*;

/**
 * Created by michal on 17.03.15.
 */
@Entity
public class TestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long key;

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }


    @Override
    public String toString() {
        return "TestEntity{key='" + key + "\'}";
    }
}
