package jmaster.io.btvnproject3.DTO;

import lombok.Data;

import javax.persistence.Id;

@Data
public class MessageDTO {
    @Id
    private int id;
    private String to;
    private String toName;
    private String subject;
    private String content;
}
