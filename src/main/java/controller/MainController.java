package controller;

import dto.ListResponse;
import dto.Response;
import dto.UserDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

/**
 * @author y.khagel
 */
@RestController
@AllArgsConstructor
@ApiResponses({@ApiResponse(code = 400, message = "Bad Request")})
@RequestMapping(path = "/api", produces = APPLICATION_XML_VALUE)
public class MainController {
  private final UserService userService;

  @ApiOperation("Зарегистрировать абонента")
  @PostMapping("/register")
  public Response<UserDto> register(Model model, String login, String password) {
    if (userService.getUser(login) != null) {
      model.addAttribute("message", "Login have exists. Please enter again!!!");
      return register(model, login, password);
    }
    return new Response(userService.register(login, password));
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
  public Response<UserDto> setBalance(@PathVariable Double amount, @PathVariable String login) {
    return new Response(userService.addMoney(amount, login));
  }

  @ApiOperation("Найти абонента по логину")
  @GetMapping("/find_customer")
  public ListResponse<UserDto> getUser(final String login) {
    return new ListResponse(userService.getUser(login));
  }

}