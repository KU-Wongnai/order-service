package ku.cs.kuwongnai.user;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import ku.cs.kuwongnai.order.Bill;
import ku.cs.kuwongnai.order.PurchaseOrder;
import lombok.Data;

@Entity
@Data
public class User {

  @Id
  private Long id;
  private String name;
  private String email;
  private LocalDateTime emailVerifiedAt;
  private String avatar;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<Bill> bills;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<PurchaseOrder> orders;
}
