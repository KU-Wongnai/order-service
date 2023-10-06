package ku.cs.kuwongnai.order;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BillRepository extends JpaRepository<Bill, UUID> {

  public List<Bill> findByUserId(Long userId);
}
