package jmaster.io.btvnproject3.controller;

import jmaster.io.btvnproject3.DTO.PageDTO;
import jmaster.io.btvnproject3.DTO.ResponseDTO;
import jmaster.io.btvnproject3.DTO.UserDTO;
import jmaster.io.btvnproject3.repo.UserRepo;
import jmaster.io.btvnproject3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepo userRepo;

    @PostMapping("/new")
    public ResponseDTO<UserDTO> add(@ModelAttribute UserDTO userDTO) throws IllegalAccessError, IOException {
        if (userDTO.getFile() != null && !userDTO.getFile().isEmpty()) {
            final String UPLOAD_FOLDER = "E:/WorkSpace/file/";

            String filename = userDTO.getFile().getOriginalFilename();

            //lay dinh dang file
            String extension = filename.substring(filename.lastIndexOf("."));
            // tao ten moi
            String newFileName = UUID.randomUUID().toString() + extension;

            File file = new File(UPLOAD_FOLDER + newFileName);
            userDTO.getFile().transferTo(file);

            userDTO.setAvatar(newFileName); //save to db
        }
        userService.create(userDTO);
        return ResponseDTO.<UserDTO>builder().status(200).data(userDTO).build();
    }

    @GetMapping("/download/{fileName}")
    public ResponseDTO<Void> download(@PathVariable("fileName") String fileName,
                                      HttpServletResponse response) throws IOException {
        final String UPLOAD_FOLDER = "E:/WorkSpace/file";
        File file = new File(UPLOAD_FOLDER + fileName);

        // java.nio.file.Files
        Files.copy(file.toPath(), response.getOutputStream());
        return ResponseDTO.<Void>builder().status(200).build();
    }

    @PutMapping("/edit")
    public ResponseDTO<UserDTO> edit(@RequestBody UserDTO userDTO) {
        userService.update(userDTO);
        return ResponseDTO.<UserDTO>builder().status(200).data(userDTO).build();
    }

    @PutMapping("/password")
    public ResponseDTO<Void> updatePassword(@RequestBody @Valid UserDTO userDTO) {
        userService.updatePassword(userDTO);
        return ResponseDTO.<Void>builder().status(200).build();
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") int id) {
        userService.delete(id);
    }

    @GetMapping("/get/{id}")
    public ResponseDTO<UserDTO> getById(@PathVariable("id") int id) {
        return ResponseDTO.<UserDTO>builder().status(200).data(userService.findById(id)).build();
    }

    @GetMapping("/search")
//    @Secured({"USER_ADMIN"})
//    @PreAuthorize("hasAuthority('USER_ADMIN')")
//    @PostAuthorize("returnObject != null || #id != null")
    public ResponseDTO<PageDTO<UserDTO>> searchByName(@RequestParam(name = "name", required = false) String name,
                                                      @RequestParam(name = "page", required = false) Integer page,
                                                      @RequestParam(name = "size", required = false) Integer size) {
        size = size == null ? 10 : size;
        page = page == null ? 0 : page;
        name = name == null ? "" : name;

        Pageable pageable = PageRequest.of(page, size);

        PageDTO<UserDTO> pageRS = userService.searchByName(name, pageable);
        return ResponseDTO.<PageDTO<UserDTO>>builder().status(200).data(pageRS).build();
    }

    @GetMapping("/search-byBd")
    public ResponseDTO<List<UserDTO>> searchByBirthdate(@RequestParam(name = "birthdate",
            required = false) Date birthdate) {
        List<UserDTO> list = userService.searchByBirthdate(birthdate);
        return ResponseDTO.<List<UserDTO>>builder().status(200).data(list).build();
    }

}
