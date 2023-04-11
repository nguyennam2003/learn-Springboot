package jmaster.io.btvnproject3.repo;

import jmaster.io.btvnproject3.entity.BillItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillItemsRepo extends JpaRepository<BillItem, Integer> {

}
