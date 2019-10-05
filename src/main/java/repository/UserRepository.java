package repository;


import entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
  //Найти абонента по логину и паролю.
  UserEntity findByLoginAndPassword(String login, String password);

  //Найти абонента по логину.
  UserEntity findByLogin(String login);

  void deleteById(Long id);

  @Query(value = "select t from UsersEntity t where t.login = :login", nativeQuery = true)
  void editUser(@Param("balance") Double balance,
                @Param("login") String login);

}