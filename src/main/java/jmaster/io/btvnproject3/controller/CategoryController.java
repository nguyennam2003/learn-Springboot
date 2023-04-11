package jmaster.io.btvnproject3.controller;

import jmaster.io.btvnproject3.DTO.CategoryDTO;
import jmaster.io.btvnproject3.DTO.PageDTO;
import jmaster.io.btvnproject3.DTO.ResponseDTO;
import jmaster.io.btvnproject3.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/category")
public class CategoryController {


    @Autowired
    CategoryService categoryService;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<CategoryDTO> add(@ModelAttribute CategoryDTO categoryDTO) {
        categoryService.create(categoryDTO);
        return ResponseDTO.<CategoryDTO>builder().status(200).data(categoryDTO).build();
    }

    @GetMapping("/{id}")
    public ResponseDTO<CategoryDTO> get(@PathVariable("id") int id) {
        return ResponseDTO.<CategoryDTO>builder().status(200).data(categoryService.getById(id)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Void> delete(@PathVariable("id") int id) {
        categoryService.delete(id);
        return ResponseDTO.<Void>builder().status(200).build();
    }

    @PutMapping("/")
    public ResponseDTO<CategoryDTO> update(@ModelAttribute CategoryDTO categoryDTO) {
        categoryService.update(categoryDTO);
        return ResponseDTO.<CategoryDTO>builder().status(200).data(categoryDTO).build();
    }

    @GetMapping("/search")//?name
    public ResponseDTO<PageDTO<CategoryDTO>> search(
                                                @RequestParam(name = "name", required = false) String name,
                                                @RequestParam(name = "size", required = false) Integer size,
                                                @RequestParam(name = "page", required = false) Integer page) {

        size = size == null ? 10 : size;
        page = page == null ? 0 : page;
        name = name == null ? "" : name;

        Pageable pageable = PageRequest.of(page, size);

        PageDTO<CategoryDTO> pageRS = categoryService.searchByName(name, pageable);

        return ResponseDTO.<PageDTO<CategoryDTO>>builder().status(200).data(pageRS).build();
    }
}
