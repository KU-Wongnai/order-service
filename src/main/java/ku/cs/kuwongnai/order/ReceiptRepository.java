package ku.cs.kuwongnai.order;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepository extends JpaRepository<Receipt, UUID> {

}
