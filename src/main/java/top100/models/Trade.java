package top100.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by chohab on 20/07/2018.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trade")
public class Trade {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "buyingPlayer")
    User buyingPlayer;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "sellingPlayer")
    User sellingPlayer;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "transactionId")
    Transaction transaction;

    @Column(name = "status")
    String status;

    @Column(name = "type")
    String type;

    @Column(name = "sharePrice")
    double sharePrice;

    @Column(name = "amount")
    double amount;

}


