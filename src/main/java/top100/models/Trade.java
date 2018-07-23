package top100.models;

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

    @ManyToOne
    @JoinColumn(name = "buyingPlayer")
    User buyingPlayer;

    @ManyToOne
    @JoinColumn(name = "sellingPlayer")
    User sellingPlayer;

    @ManyToOne
    @JoinColumn(name = "transaction")
    Transaction transaction;

}


