package jmaster.io.btvnproject3.entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * BillItem (id, Bill , Product , quantity, buyPrice) - mỗi billitem thuộc về 1 bill và 1 product
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class BillItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Bill bill;

    @ManyToOne
    private Product product;
    private int quantity;
    protected double price;
}
