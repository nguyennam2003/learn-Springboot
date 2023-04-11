package jmaster.io.btvnproject3.service;

import jmaster.io.btvnproject3.repo.BillItemsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillItemsService {

    @Autowired
    BillItemsRepo billItemsRepo;
}
