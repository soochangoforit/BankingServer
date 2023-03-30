package transfer.banking.server.domain.trasaction.adapter.out.persistence.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import transfer.banking.server.domain.account.adapter.out.persistence.entity.Account;
import transfer.banking.server.domain.trasaction.adapter.out.persistence.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  @Query("SELECT t FROM Transaction t JOIN FETCH t.sender JOIN FETCH t.receiver WHERE t.sender = :myAccount OR t.receiver = :myAccount ORDER BY t.createdAt DESC")
  List<Transaction> findAllBySenderOrReceiver(@Param("myAccount") Account myAccount);
}
