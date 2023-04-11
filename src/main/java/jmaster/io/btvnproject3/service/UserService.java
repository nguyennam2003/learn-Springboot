package jmaster.io.btvnproject3.service;

import jmaster.io.btvnproject3.DTO.PageDTO;
import jmaster.io.btvnproject3.DTO.UserDTO;
import jmaster.io.btvnproject3.entity.User;
import jmaster.io.btvnproject3.repo.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {

    @Autowired
    UserRepo userRepo;


    @Transactional
    public void create(UserDTO userDTO) {
        User user = new ModelMapper().map(userDTO, User.class);
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        userRepo.save(user);
        userDTO.setId(user.getId());
        userDTO.setCreateAt(user.getCreateAt());
    }

    @Transactional
    @Cacheable(cacheNames = "users", key = "#id")
    public void update(UserDTO userDTO) {
        User user = userRepo.findById(userDTO.getId()).orElseThrow(RuntimeException::new);

        user.setEmail(userDTO.getEmail());
        user.setBirthdate(userDTO.getBirthdate());
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());
        user.setRoles(userDTO.getRoles());

        if (user.getAvatar() != null)
            user.setAvatar(userDTO.getAvatar());

        userRepo.save(user);
    }

    @Transactional
    @Cacheable(cacheNames = "users", key = "#id")
    public void update1(UserDTO userDTO) {
        User user = userRepo.findById(userDTO.getId()).orElseThrow(RuntimeException::new);

        ModelMapper mapper = new ModelMapper();
        mapper.createTypeMap(UserDTO.class, User.class).addMappings(map -> {map.skip(User::setPassword);
                    if (user.getAvatar() != null)
                        map.skip(User::setAvatar);
                })
                .setProvider(p -> user);

        User saveUser = mapper.map(userDTO, User.class);


        userRepo.save(saveUser);
    }

    @Transactional
    public void updatePassword(UserDTO userDTO) {
        User user = userRepo.findById(userDTO.getId()).orElseThrow(NoResultException::new);
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
//        user.setPassword(userDTO.getPassword());
        userRepo.save(user);
    }

    @Transactional
    @Cacheable(cacheNames = "users", key = "#id", unless = "#result == null")
    public UserDTO findById(int id) {
        User user = userRepo.findById(id).orElseThrow(NoResultException::new);

        return new ModelMapper().map(user, UserDTO.class);
    }

    @Transactional
    public PageDTO<UserDTO> getAll() {
        List<UserDTO> list = new ArrayList<>();
        PageDTO<UserDTO> pageDTO = new PageDTO<>();
        User user = new User();

        for (User u : userRepo.findAll()
        ) {
            ModelMapper mapper = new ModelMapper();
            UserDTO userDTO = mapper.map(u, UserDTO.class);
            list.add(userDTO);
        }
        pageDTO.setContents(list);
        return pageDTO;
    }

    @Caching(evict = {@CacheEvict(cacheNames = "users", key = "#id"),
            @CacheEvict(cacheNames = "user-search", allEntries = true)
    })
    public void delete(int id) {
        userRepo.deleteById(id);
    }

    @Transactional
    public PageDTO<UserDTO> searchByName(String name, Pageable pageable) {
        Page<User> pageRS = userRepo.findByNameLike("%" + name + "%", pageable);

        PageDTO<UserDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPage(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<UserDTO> userDTOs = pageRS.get().map(user -> new ModelMapper().
                map(user, UserDTO.class)).collect(Collectors.toList());

        pageDTO.setContents(userDTOs);
        return pageDTO;
    }

    @Transactional
    public List<UserDTO> searchByBirthdate(Date birthdate) {
        List<User> pageRS = userRepo.searchByBirthdate(birthdate);

        List<UserDTO> list = new ArrayList<>();
        for (User user : pageRS) {
            list.add(new ModelMapper().map(user, UserDTO.class));
        }
        return list;
    }
}
