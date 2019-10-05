package tests;

import entity.GenericEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import repository.GenericEntityRepository;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SpringBootJPAIntegrationTest {

  @Autowired
  private GenericEntityRepository genericEntityRepository;

  @Test
  public void givenGenericEntityRepository_whenSaveAndRetreiveEntity_thenOK() {
    GenericEntity genericEntity = genericEntityRepository
            .save(new GenericEntity("7995999999", "password"));
    Optional<GenericEntity> foundEntity = genericEntityRepository
            .findById(Long.parseLong(genericEntity.getLogin()));
    assertTrue(foundEntity.isPresent());
    assertEquals(genericEntity.getLogin(), foundEntity.get().getLogin());
  }
}
