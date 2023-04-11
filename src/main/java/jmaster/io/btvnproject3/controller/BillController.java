package jmaster.io.btvnproject3.controller;

import jmaster.io.btvnproject3.DTO.BillDTO;
import jmaster.io.btvnproject3.DTO.BillStatisticDTO;
import jmaster.io.btvnproject3.DTO.PageDTO;
import jmaster.io.btvnproject3.DTO.ResponseDTO;
import jmaster.io.btvnproject3.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/bill")
public class BillController {
    @Autowired
    BillService billService;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<BillDTO> add(@RequestBody @Valid BillDTO billDTO) {
        billService.create(billDTO);
        return ResponseDTO.<BillDTO>builder().status(200).data(billDTO).build();
    }

    @GetMapping("/{id}")
    public ResponseDTO<BillDTO> get(@PathVariable("id") int id) {
        return ResponseDTO.<BillDTO>builder().status(200).data(billService.getById(id)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Void> delete(@PathVariable("id") int id) {
        billService.delete(id);
        return ResponseDTO.<Void>builder().status(200).build();
    }

    @PutMapping("/")
    public ResponseDTO<BillDTO> update(@ModelAttribute @Valid BillDTO billDTO) {
        billService.update(billDTO);
        return ResponseDTO.<BillDTO>builder().status(200).data(billDTO).build();
    }

    @GetMapping("/search")//?name
    public ResponseDTO<PageDTO<BillDTO>> search(
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "page", required = false) Integer page) {

        size = size == null ? 10 : size;
        page = page == null ? 0 : page;
        PageDTO<BillDTO> pageRS = billService.findAll(page, size);

        return ResponseDTO.<PageDTO<BillDTO>>builder().status(200).data(pageRS).build();
    }

//    @GetMapping("/statistic")
//    public ResponseDTO<PageDTO<BillStatisticDTO>> get() {
//        PageDTO<BillStatisticDTO> pageRS = billService.statistic();
//
//        return ResponseDTO.<PageDTO<BillStatisticDTO>>builder().data(pageRS).status(200).build();
//    }
}
