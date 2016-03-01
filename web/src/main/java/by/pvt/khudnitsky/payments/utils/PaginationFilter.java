package by.pvt.khudnitsky.payments.utils;

/**
 * Created by: khudnitsky
 * Date: 23.02.2016
 * Time: 15:55
 */
public class PaginationFilter {
    private String ordering;
    private String direction;
    private Integer currentPage;
    private Integer recordsPerPage;

    public PaginationFilter() {}

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public String getOrdering() {
        return ordering;
    }

    public void setOrdering(String ordering) {
       this.ordering = ordering;
    }

    public Integer getRecordsPerPage() {
        return recordsPerPage;
    }

    public void setRecordsPerPage(Integer recordsPerPage) {
        this.recordsPerPage = recordsPerPage;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
