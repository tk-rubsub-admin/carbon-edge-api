package th.co.carbonedge.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class Pageable<T> {
    private Pagination pagination;
    private List<T> records;
}
