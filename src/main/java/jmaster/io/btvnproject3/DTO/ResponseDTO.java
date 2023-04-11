package jmaster.io.btvnproject3.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDTO<T> {
    private int status;
    private String error;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
}
