package jmaster.io.btvnproject3.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Bill(id, buyDate, User user)
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CreatedDate
    @Column(updatable = false)
    private Date buyDate;
    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL,
                orphanRemoval = true)
    private List<BillItem> billItems;
}
