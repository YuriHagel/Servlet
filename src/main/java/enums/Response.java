package enums;

import lombok.Getter;


public enum Response {
  OK(0, "все хорошо"),
  DUPLICATE_USER(1, "такой агент уже зарегистрирован"),
  WRONG_LOGIN(2, "неверный формат телефона"),
  BAD_PASSWORD(3, "плохой пароль"),
  OTHER(5, "другая ошибка повторите позже"),
  USER_NOT_EXITS(6, "Пользователь с данным логином не существует");

  @Getter
  private final int code;
  @Getter
  private final String description;

  Response(int code, String description) {
    this.code = code;
    this.description = description;
  }

  @Override
  public String toString() {
    return code + ": " + description;
  }

}