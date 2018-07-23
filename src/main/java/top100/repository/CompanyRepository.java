package top100.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.transaction.annotation.Transactional;
import top100.models.Company;
import top100.models.User;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Jack on 17/11/2017.
 */
@Transactional
public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findAll();

    Company findById(int id);





}
