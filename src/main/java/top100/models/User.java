package top100.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * Created by Jack on 20/07/2018.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")

public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "username")
    String username;

    @Column(name = "password")
    String password;

    @Column(name = "email")
    String email;

    @Column(name = "address")
    String address;

    @Column(name = "createDate")
    Timestamp createDate;

    @Column(name = "bank")
    double bank;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    List<Transaction> transactions;

    @JsonIgnore
    @OneToMany(mappedBy = "buyingPlayer")
    List<Trade> buyingTrades;

    @JsonIgnore
    @OneToMany(mappedBy = "sellingPlayer")
    List<Trade> sellingTrades;


}
