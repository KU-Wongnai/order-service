package ku.cs.kuwongnai.user;

import java.time.LocalDateTime;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class UserRequest {
  @Id
  private Long id;
  private String name;
  private String email;
  private LocalDateTime emailVerifiedAt;
  private String avatar;
}
