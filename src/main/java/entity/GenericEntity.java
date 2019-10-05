package entity;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Data
@Entity
@AllArgsConstructor
public class GenericEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private String login;
  private String password;
}
