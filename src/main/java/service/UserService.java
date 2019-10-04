package service;

import entity.UserEntity;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import repository.UserRepository;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@AllArgsConstructor
public class UserService {
  private static final Logger logger = LoggerFactory.getLogger(UserService.class);
  private UserRepository userRepository;


  public UserEntity register(String login, String password) {
    UserEntity usersEntity = new UserEntity();
    usersEntity.setLogin(login);
    usersEntity.setPassword(hash(password));
    userRepository.save(usersEntity);
    return usersEntity;
  }

  //Установить баланс

  public void addMoney(Double ammount, String login) {
    UserEntity user = userRepository.findByLogin(login);
    user.setBalance(user.getBalance() + ammount);
    userRepository.save(user);
  }

  //Удалить пользователя

  public void deleteUser(String login) {
    UserEntity user = userRepository.findByLogin(login);
    userRepository.deleteById(user.getId());
  }

  //Верификация юзера.
  public UserEntity verifyUser(String login, String password) {
    return userRepository.findByLoginAndPassword(login, password);
  }

  //Уменьшить балан юзера.
  public void withdrawMoney(Double ammount, String login) {
    UserEntity user = userRepository.findByLogin(login);
    user.setBalance(user.getBalance() - ammount);
    userRepository.save(user);
  }


  public String hash(String password) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(password.getBytes());
      byte[] bytes = md.digest(password.getBytes());
      StringBuilder stringBuffer = new StringBuilder();
      for (int i = 0; i < bytes.length; i++)
        stringBuffer.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));

      return stringBuffer.toString();
    } catch (NoSuchAlgorithmException e) {
      logger.error("NoSuchAlgorithmException", e);
      throw new RuntimeException(e);
    }
  }


  private boolean isValidLogin(String number) {
    Pattern pattern = Pattern.compile("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$");
    Matcher matcher = pattern.matcher(number);
    return matcher.matches();
  }
}




