package transfer.banking.server.domain.trasaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import transfer.banking.server.domain.trasaction.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
