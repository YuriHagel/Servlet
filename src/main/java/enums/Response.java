package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Response {
  OK(0, "все хорошо"),
  DUPLICATE_USER(1, "такой агент уже зарегистрирован"),
  WRONG_LOGIN(2, "неверный формат телефона"),
  BAD_PASSWORD(3, "плохой пароль"),
  OTHER(5, "другая ошибка повторите позже"),
  USER_NOT_EXITS(6, "Пользователь с данным логином не существует");

  private final int code;
  private final String description;
}