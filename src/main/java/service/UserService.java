package service;

import dto.UserDto;
import entity.UserEntity;
import enums.Response;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
  private final UserDto userDto;

  public UserEntity register(String login, String password) {
    UserEntity usersEntity = new UserEntity();
    usersEntity.setLogin(login);
    usersEntity.setPassword(hash(password));
    userRepository.save(usersEntity);
    if (usersEntity.equals(validate(usersEntity))) {
      return usersEntity;
    } else {
      System.exit(0);
    }
    return register(login, password);
  }

  //Найти всех абонентов.
  public UserEntity getUser(final String login) {
    return userRepository.findByLogin(login);
  }

  //Установить баланс

  public UserEntity addMoney(Double ammount, String login) {
    UserEntity user = userRepository.findByLogin(login);
    user.setBalance(user.getBalance() + ammount);
    userRepository.save(user);
    return user;
  }


  private String hash(String password) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(password.getBytes());
      byte[] bytes = md.digest(password.getBytes());
      StringBuilder stringBuffer = new StringBuilder();
      for (byte aByte : bytes)
        stringBuffer
                .append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));

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

  protected Response validate(UserEntity user) {
    if (isExistAgent(user))
      return Response.DUPLICATE_USER;
    else if (!isValidLogin(user.getLogin()))
      return Response.WRONG_LOGIN;
    else if (isBadPassword(user.getPassword()))
      return Response.BAD_PASSWORD;

    return null;
  }

  private boolean isExistAgent(UserEntity userEntity) {
    return userRepository.findByLogin(userEntity.toString()) != null;
  }

  private boolean isBadPassword(String password) {
    return StringUtils.length(password) < 8;
  }
}




