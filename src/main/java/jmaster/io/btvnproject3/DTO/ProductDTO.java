package jmaster.io.btvnproject3.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jmaster.io.btvnproject3.entity.Category;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class ProductDTO {
    private Integer id;
    @NotBlank
    private String name;
    private String image;
    @Min(0)
    private double price;
    private String description;
    @JsonIgnore
    private MultipartFile file;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date createAt;
    private Category category;
}
