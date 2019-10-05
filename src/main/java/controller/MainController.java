package controller;

import entity.UserEntity;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author y.khagel
 */
@RestController
@AllArgsConstructor
@RequestMapping(path = "/api")
public class MainController {
  private final UserService userService;

  @ApiOperation("Зарегистрировать абонента")
  @PostMapping("/register")
  public UserEntity register(String login, String password) {
    return userService.register(login, password);
  }

  @ApiOperation("Выйти")
  @GetMapping("/logout")
  public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      new SecurityContextLogoutHandler().logout(request, response, auth);
    }
    return ResponseEntity.status(200).body("You are logout");
  }

  //Пополнить баланс
  @ApiOperation("Пополнить баланс")
  @PostMapping("/setBalance")
  public void setBalance(@PathVariable Double amount, @PathVariable String login) {
    userService.addMoney(amount, login);
  }

  @ApiOperation("Уменьшить баланс")
  @PostMapping("/drawBalance")
  public void withdrawBalance(@PathVariable String login, @PathVariable Double amount) {
    userService.withdrawMoney(amount, login);
  }

  @ApiOperation("Удалить абонента")
  @DeleteMapping("/delete")
  public void deleteUser(@PathVariable String login) {
    userService.deleteUser(login);
  }

  @ApiOperation("Найти абонента по логину")
  @GetMapping("/find_customer")
  public void findLoginId(@PathVariable String login) {
    userService.getUser(login);
  }

  @ApiOperation("Верификация юзера")
  @PostMapping("/verify")
  public void verifyUser(@PathVariable String login, @PathVariable String password) {
    userService.verifyUser(login, password);
  }

}