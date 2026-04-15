package th.co.carbonedge.api.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import th.co.carbonedge.api.dto.Pagination;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaginationUtils {

    public static Pagination createPagination(Page<?> page) {
        Pagination pagination = new Pagination();
        pagination.setPage(page.getPageable().getPageNumber());
        pagination.setSize(page.getSize());
        pagination.setTotalPage(page.getTotalPages());
        pagination.setTotalRecords(page.getTotalElements());

        return pagination;
    }

}
