package jmaster.io.btvnproject3.controller;

import jmaster.io.btvnproject3.DTO.ProductDTO;
import jmaster.io.btvnproject3.DTO.ResponseDTO;
import jmaster.io.btvnproject3.repo.ProductRepo;
import jmaster.io.btvnproject3.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepo productRepo;


    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<ProductDTO> add(@ModelAttribute @Valid ProductDTO productDTO) throws IllegalAccessError, IOException {
        if (productDTO.getFile() != null && !productDTO.getFile().isEmpty()) {
            final String UPLOAD_FOLDER = "E:/WorkSpace/file/";

            if (!(new File(UPLOAD_FOLDER).exists())) {
                new File(UPLOAD_FOLDER).mkdirs();
            }
            String filename = productDTO.getFile().getOriginalFilename();
            String extension = filename.substring(filename.lastIndexOf("."));
            String newFileName = UUID.randomUUID().toString() + extension;
            File file = new File(UPLOAD_FOLDER + newFileName);
            productDTO.getFile().transferTo(file);

            productDTO.setImage(newFileName);
        }
        productService.create(productDTO);
        return ResponseDTO.<ProductDTO>builder().status(200).data(productDTO).build();
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Void> delete (@PathVariable("id") int id) {
        productService.delete(id);
        return ResponseDTO.<Void>builder().status(200).build();
    }

    @GetMapping("/get/{id}")
    public ResponseDTO<ProductDTO> getById(@PathVariable("id") int id) {
        ProductDTO productDTO = productService.findById(id);
        return ResponseDTO.<ProductDTO>builder().status(200).data(productDTO).build();
    }
}
