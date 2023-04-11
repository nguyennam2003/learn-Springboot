package jmaster.io.btvnproject3.service;

import jmaster.io.btvnproject3.DTO.CategoryDTO;
import jmaster.io.btvnproject3.DTO.ProductDTO;
import jmaster.io.btvnproject3.DTO.UserDTO;
import jmaster.io.btvnproject3.entity.Category;
import jmaster.io.btvnproject3.entity.Product;
import jmaster.io.btvnproject3.entity.User;
import jmaster.io.btvnproject3.repo.CategoryRepo;
import jmaster.io.btvnproject3.repo.ProductRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    @Autowired
    CategoryRepo categoryRepo;

    @Transactional
    public ProductDTO create(ProductDTO productDTO) {
        Product product = new ModelMapper().map(productDTO, Product.class);

        productRepo.save(product);

        //tra ve id sau khi tao
        productDTO.setId(product.getId());
        productDTO.setCreateAt(product.getCreateAt());
        return productDTO;
    }

    public void delete(int id) {
        productRepo.deleteById(id);
    }

    @Transactional
    public ProductDTO findById(int id) {
        Product product = productRepo.findById(id).orElseThrow(NoResultException::new);

        ProductDTO productDTO = new ModelMapper().map(product, ProductDTO.class);
        return productDTO;
    }

}
