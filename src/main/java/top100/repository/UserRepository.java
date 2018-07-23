package top100.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import top100.models.User;

import java.util.List;

/**
 * Created by Jack on 17/11/2017.
 */
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    List<User> findAll();





}
