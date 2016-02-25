package by.pvt.khudnitsky.payments.utils;

import by.pvt.khudnitsky.payments.enums.Parameters;

/**
 * Created by: khudnitsky
 * Date: 23.02.2016
 * Time: 15:53
 */
public class FilterUtil {
    private static final String ORDER_BY_DATE = "ORDER BY operationDate";
    private static final String ORDER_BY_DESCRIPTION = "ORDER BY description";
    private static final String ORDER_BY_AMOUNT = "ORDER BY amount";
    private static final String ORDER_BY_CLIENT = "ORDER BY userLastName";
    private static final String ORDER_BY_ACCOUNT = "ORDER BY accountNumber";
    private static final String ORDER_ASC = "ASC";
    private static final String ORDER_DESC = "DESC";


    public static PaginationFilter defineParameters(String ordering, String orderingType, int currentPage, int recordsPerPage){
        PaginationFilter filter = new PaginationFilter();
        switch (ordering){
            case "description":
                filter.setOrdering(ORDER_BY_DESCRIPTION);
                break;
            case "amount":
                filter.setOrdering(ORDER_BY_AMOUNT);
                break;
            case "client":
                filter.setOrdering(ORDER_BY_CLIENT);
                break;
            case "account":
                filter.setOrdering(ORDER_BY_ACCOUNT);
                break;
            default:
                filter.setOrdering(ORDER_BY_DATE);
        }

        if(recordsPerPage == 0){
            filter.setRecordsPerPage(3);
        }
        if(currentPage == 0) {
            filter.setCurrentPage(1);
        }
        return filter;
    }
}
