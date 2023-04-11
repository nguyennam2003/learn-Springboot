package jmaster.io.btvnproject3.service;

import jmaster.io.btvnproject3.DTO.CategoryDTO;
import jmaster.io.btvnproject3.DTO.PageDTO;
import jmaster.io.btvnproject3.entity.Category;
import jmaster.io.btvnproject3.repo.CategoryRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    CategoryRepo categoryRepo;

    @Transactional
    @CacheEvict(cacheNames = "category-search", allEntries = true)
    public void create(CategoryDTO categoryDTO) {
        Category category = new ModelMapper().map(categoryDTO, Category.class);
        categoryRepo.save(category);
    }

    @Transactional
    @Cacheable(cacheNames = "categories", key = "#id")
    public void update(CategoryDTO categoryDTO) {
        Category category = categoryRepo.findById(categoryDTO.getId()).orElseThrow(NoResultException::new);

        category.setName(categoryDTO.getName());

        categoryRepo.save(category);
    }

    @Transactional
    @Caching(evict = {@CacheEvict(cacheNames = "categories", key = "#id"),
                        @CacheEvict(cacheNames = "category-search", allEntries = true)
    })
    public void delete(int id) {
        categoryRepo.deleteById(id);
    }

    @Transactional
    public PageDTO<CategoryDTO> searchByName(String name, Pageable pageable) {

        Page<Category> pageRS = categoryRepo.findByNameLike("%" + name + "%", pageable);

        PageDTO<CategoryDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPage(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<CategoryDTO> categoryDTOs = pageRS.get().map(category -> new ModelMapper().
                map(category, CategoryDTO.class)).collect(Collectors.toList());

        pageDTO.setContents(categoryDTOs);
        return pageDTO;
    }

    @Cacheable(cacheNames = "categories", key = "#id", unless = "#result == null")
    public CategoryDTO getById(int id) {
        Category category = categoryRepo.findById(id).orElseThrow(NoResultException::new);
        return new ModelMapper().map(category, CategoryDTO.class);
    }

}
