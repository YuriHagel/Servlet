package entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "offers_seq")
  @SequenceGenerator(name = "offers_seq", sequenceName = "offers_seq", allocationSize = 1)
  private Long id;
  private String login;
  private String password;
  @CreationTimestamp
  @Column(name = "dt_created")
  private Date dtCreated;
  @Column(name = "balance")
  private Double balance;

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final UserEntity other = (UserEntity) obj;
    return Objects.equals(this.login, other.login);
  }
}

