package top100.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Created by chohab on 20/07/2018.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "company")
public class Company {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "companyName")
    String companyName;

    @Column(name = "country")
    String country;

    @Column(name = "revenue")
    double revenue;

    @Column(name = "employees")
    int employees;

    @Column(name = "shares")
    int sharesAvailable;

    @Column(name = "advantage")
    Boolean competitiveAdvantage;

    @JsonIgnore
    @OneToMany(mappedBy = "company")
    List<Transaction> transactions;

}

