package jmaster.io.btvnproject3.repo;

import jmaster.io.btvnproject3.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface BillRepo extends JpaRepository<Bill, Integer> {

    @Query("select b from Bill b where b.buyDate >= :x")
    List<Bill> searchByDate(@Param("x") Date date);


//    @Query("select count(b.id), month(b.buyDate), year(b.buydate)" +
//            " from Bill  b group by month(b.buyDate), year(b.buyDate)")
//    List<Object[]> thongKeBill();
}
