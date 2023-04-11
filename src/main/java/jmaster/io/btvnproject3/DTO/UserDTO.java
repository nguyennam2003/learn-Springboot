package jmaster.io.btvnproject3.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;


@Data
public class UserDTO {
    private Integer id;

    @NotBlank
    private String name;
    private String avatar;
    private String username;
    private String password;
    private String email;

    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthdate;

    @JsonIgnore
    private MultipartFile file;

    @LastModifiedDate
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date createAt;
    private List<String> roles;
}
