package by.pvt.khudnitsky.payments.utils;

/**
 * Created by: khudnitsky
 * Date: 27.02.2016
 * Time: 0:14
 */
public class OrderingUtil {
    private static final String ORDER_BY_DATE = "ORDER BY operationDate";
    private static final String ORDER_BY_DESCRIPTION = "ORDER BY description";
    private static final String ORDER_BY_AMOUNT = "ORDER BY amount";
    private static final String ORDER_BY_CLIENT = "ORDER BY userLastName";
    private static final String ORDER_BY_ACCOUNT = "ORDER BY accountNumber";
    private static final String ORDER_ASC = " ASC";
    private static final String ORDER_DESC = " DESC";

    private OrderingUtil(){}

    public static String defineOrderingType(String ordering){
        String oderBy;
        switch (ordering) {
            case "description":
                oderBy = ORDER_BY_DESCRIPTION;
                break;
            case "amount":
                oderBy = ORDER_BY_AMOUNT;
                break;
            case "client":
                oderBy = ORDER_BY_CLIENT;
                break;
            case "account":
                oderBy = ORDER_BY_ACCOUNT;
                break;
            default:
                oderBy = ORDER_BY_DATE;
        }
        return oderBy;
    }

    public static String defineOrderingDirection(String direction){
        String oderDirection;
        switch (direction) {
            case "asc":
                oderDirection = ORDER_ASC;
                break;
            case "desc":
                oderDirection = ORDER_DESC;
                break;
            default:
                oderDirection = ORDER_ASC;
        }
        return oderDirection;
    }
}
