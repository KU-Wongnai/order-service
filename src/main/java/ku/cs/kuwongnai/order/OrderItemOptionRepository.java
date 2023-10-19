package ku.cs.kuwongnai.order;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemOptionRepository extends JpaRepository<OrderItemOption, UUID> {

}
