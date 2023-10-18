package ku.cs.kuwongnai.order;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, UUID> {

  public List<Bill> findByUserId(Long userId);
}
