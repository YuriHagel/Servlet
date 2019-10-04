package command;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class WithdrawMoneyCommand {
  @TargetAggregateIdentifier
  private final String login;
  private final Double balance;
}
