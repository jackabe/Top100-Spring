package top100.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import top100.models.Company;
import top100.models.Transaction;
import top100.models.User;

import java.util.List;

/**
 * Created by Jack on 17/11/2017.
 */
@Transactional
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAll();

    List<Transaction> findAllByUser(User user);

    List<Transaction> findAllByCompany(Company company);

    Transaction findById(int id);

    void deleteTransactionById(int id);

    Transaction findByUserAndCompany(User user, Company company);

}
