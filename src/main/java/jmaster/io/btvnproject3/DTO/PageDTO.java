package jmaster.io.btvnproject3.DTO;

import lombok.Data;

import java.util.List;

@Data
public class PageDTO<T> {
    private int totalPage;
    private long totalElements;
    private List<T> contents;
}
