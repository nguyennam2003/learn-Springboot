package jmaster.io.btvnproject3.DTO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.validation.constraints.Min;


@Data
public class BillItemsDTO {
    private Integer id;

    @JsonBackReference
    private BillDTO billDTO;

    private ProductDTO product;

    @Min(0)
    private int quantity;

    @Min(0)
    protected double price;
}
