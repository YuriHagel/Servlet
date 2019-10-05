package tests;

import entity.UserEntity;
import lombok.AllArgsConstructor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import repository.UserRepository;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SaveUser {
  @Autowired
 private UserRepository repository;

  @Test
  public void testUserSave() {
    //given
    UserEntity user = new UserEntity();
    //when
    repository.save(user);
    //then
    long id = user.getId();
    Optional<UserEntity> newUser = repository.findById(id);
    Assert.assertTrue(newUser.isPresent());
    //cleanUp
    repository.deleteById(id);
  }
}
