package jmaster.io.btvnproject3.service;

import jmaster.io.btvnproject3.DTO.*;
import jmaster.io.btvnproject3.entity.Bill;
import jmaster.io.btvnproject3.entity.BillItem;
import jmaster.io.btvnproject3.entity.User;
import jmaster.io.btvnproject3.repo.BillRepo;
import jmaster.io.btvnproject3.repo.ProductRepo;
import jmaster.io.btvnproject3.repo.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillService {

    @Autowired
    BillRepo billRepo;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ProductRepo productRepo;

    // nho dung ham nay o 1 class moi trong lop repo
    @SuppressWarnings("unchecked")
    public List<Bill> searchByDate(@Param("x") Date s) {
        String jpql = "SELECT b FROM Bill b WHERE b.buyDate >= :x";

        return entityManager.createQuery(jpql)
                .setParameter("x", s)
                .setMaxResults(10)
                .setFirstResult(0)
                .getResultList();
    }


    @Transactional
    public void create(BillDTO billDTO) {
        User user = userRepo.findById(billDTO.getUser().getId()).orElseThrow(NoResultException::new);

        Bill bill = new Bill();
        bill.setUser(user);

        List<BillItem> billItems = new ArrayList<>();

        for (BillItemsDTO billItemDTO : billDTO.getBillItems()) {
            BillItem billItem = new BillItem();
            billItem.setBill(bill);
            billItem.setProduct(
                    productRepo.findById(billItemDTO.getProduct().getId()).orElseThrow(NoResultException::new));

            billItem.setPrice(billItemDTO.getPrice());
            billItem.setQuantity(billItemDTO.getQuantity());

            billItems.add(billItem);
        }

        bill.setBillItems(billItems);
        billRepo.save(bill);
    }

    @Transactional
    public BillDTO getById(int id) {
        Bill bill = billRepo.findById(id).orElseThrow(NoResultException::new);

        return new ModelMapper().map(bill, BillDTO.class);
    }

    @Transactional
    public void update(BillDTO billDTO) {
        User user = userRepo.findById(billDTO.getUser().getId()).orElseThrow(NoResultException::new);
        Bill bill = billRepo.findById(billDTO.getId()).orElseThrow(NoResultException::new);

//        bill.getBillItems().clear(); // xoa het billitem trong db
//        for (BillItemsDTO billItemsDTO : billDTO.getBillItems()) {
//            BillItem billItem = new BillItem();
//            billItem.setBill(bill);
//            billItem.setProduct(productRepo.findById(billItemsDTO.getProduct().getId())
//                    .orElseThrow(NoResultException::new));
//            billItem.setPrice(billItemsDTO.getBuyPrice());
//            billItem.setQuantity(billItemsDTO.getQuantity());
//
//            bill.getBillItems().add(billItem);
//        }

        bill.setUser(user);

        billRepo.save(bill);
    }

    @Transactional
    public void delete(int id) {
        billRepo.deleteById(id);
    }

    @Transactional
    public PageDTO<BillDTO> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Bill> pageRS = billRepo.findAll(pageable);

        PageDTO<BillDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPage(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<BillDTO> billDTOs = pageRS.get().map(bill -> new ModelMapper().
                map(bill, BillDTO.class)).collect(Collectors.toList());

        pageDTO.setContents(billDTOs);
        return pageDTO;
    }

//    public PageDTO<BillStatisticDTO> statistic() {
//        List<Object[]> list = billRepo.thongKeBill();
//
//        PageDTO<BillStatisticDTO> pageDTO = new PageDTO<>();
//        pageDTO.setTotalElements(1);
//        pageDTO.setTotalElements(list.size());
//
//        List<BillStatisticDTO> billStatisticDTOs = new ArrayList<>();
//
//        for (Object[] arr : list) {
//            BillStatisticDTO billStatisticDTO = new BillStatisticDTO((long) (arr[0]),
//                    String.valueOf(arr[1]) + "/"+ String.valueOf(arr[2]));
//            billStatisticDTOs.add(billStatisticDTO);
//        }
//        pageDTO.setContents(billStatisticDTOs);
//        return pageDTO;
//    }
}
