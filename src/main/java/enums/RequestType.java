package enums;

import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum RequestType {
  REGISTER_CUSTOMER(1),
  GET_BALANCE(2),
  SET_BALANCE(3),
  MULTI_REQUEST(4);
  private final int code;

  public static RequestType ofCode(int code) {
    return Arrays.asList(RequestType.values())
            .stream()
            .filter(type -> type.code == code)
            .findFirst()
            .orElse(null);
  }
}