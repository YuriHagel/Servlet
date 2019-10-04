package repository;


import entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
  UserEntity findByLoginAndPassword(String login, String password);

  UserEntity findByLogin(String login);

  @Query(value = "select t from UsersEntity t where t.login = :login", nativeQuery = true)
  void editUser(@Param("balance") Double balance,
                @Param("login") String login);

}