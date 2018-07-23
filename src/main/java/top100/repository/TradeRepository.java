package top100.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import top100.models.Trade;
import top100.models.Transaction;
import top100.models.User;

import java.util.List;

/**
 * Created by Jack on 17/11/2017.
 */
@Transactional
public interface TradeRepository extends JpaRepository<Trade, Long> {

    List<Trade> findAll();

    List<Trade> findAllByBuyingPlayer(User user);

    List<Trade> findAllBySellingPlayer(User user);

    List<Trade> findAllByTransaction(Transaction transaction);

    Trade findById(int it);

}