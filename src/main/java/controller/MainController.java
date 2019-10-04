package controller;

import command.AddMoney;
import command.WithdrawMoneyCommand;
import entity.UserEntity;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
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
  private final CommandGateway commandGateway;

  @ApiOperation("Зарегистрировать абонента")
  @PostMapping("/register")
  public UserEntity register(String login, String password) {
    return register(login, password);
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
  @PostMapping("{login}/addmoney/{amount}")
  public void addMoney(@PathVariable String login, @PathVariable Double amount){
    commandGateway.send(
            new AddMoney(
                    login,
                    amount
            ));
  }

  @PostMapping("{login}/withdraw/{amount}")
  public void withdrawMoney(@PathVariable String login,@PathVariable Double amount){
    withdrawMoney(login, amount);
    commandGateway.send(
            new WithdrawMoneyCommand(
                    login,
                    amount
            )
    );
  }
}