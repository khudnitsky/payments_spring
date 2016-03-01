package by.pvt.khudnitsky.payments.utils;

/**
 * Created by: khudnitsky
 * Date: 23.02.2016
 * Time: 15:53
 */
public class PaginationFilterUtil {
    public static PaginationFilter defineParameters(String ordering, String direction, Integer currentPage, Integer recordsPerPage){
        PaginationFilter filter = new PaginationFilter();
        if(ordering != null) {
           filter.setOrdering(ordering);
        }
        else {
            filter.setOrdering("date");
        }

        if(recordsPerPage == null){
            filter.setRecordsPerPage(3);
        }
        else{
            filter.setRecordsPerPage(recordsPerPage);
        }
        if(currentPage == null) {
            filter.setCurrentPage(1);
        }
        else {
            filter.setCurrentPage(currentPage);
        }
        if(direction == null){
            filter.setDirection("asc");
        }
        else{
            filter.setDirection(direction);
        }

        return filter;
    }
}
