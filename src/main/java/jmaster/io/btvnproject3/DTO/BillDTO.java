package jmaster.io.btvnproject3.DTO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jmaster.io.btvnproject3.entity.BillItem;
import jmaster.io.btvnproject3.entity.User;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class BillDTO {
    private Integer id;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date buyDate;
    private UserDTO user;

    @JsonBackReference
    private List<BillItemsDTO> billItems;
}
