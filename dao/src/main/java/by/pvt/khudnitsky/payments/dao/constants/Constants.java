package by.pvt.khudnitsky.payments.dao.constants;

/**
 * Utility class
 * Stores different constants
 * Created by: khudnitsky
 * Date: 03.03.2016
 * Time: 8:46
 */
public class Constants {
    public static final String ERROR_DAO = "Error was thrown in DAO: ";
    public static final String ERROR_ACCESS_LEVEL_TYPE = "Unable to return access level type. Error was thrown in DAO: ";
    public static final String ERROR_ACCOUNT_STATUS = "Unable to check account status. Error was thrown in DAO: ";
    public static final String ERROR_BLOCKED_ACCOUNT_LIST = "Unable to return list of blocked accounts. Error was thrown in DAO: ";
    public static final String ERROR_USER_BY_LOGIN = "Unable to return user by login. Error was thrown in DAO: ";
    public static final String ERROR_CURRENCY_TYPE = "Unable to return currency type. Error was thrown in DAO: ";
    public static final String ERROR_OPERATION_DELETE = "Unable to delete the operation. Error was thrown in DAO: ";
    public static final String ERROR_OPERATIONS_LIST = "Unable to get list of operations. Error was thrown in DAO: ";
    public static final String ERROR_USERS_LIST = "Unable to return list of clients. Error was thrown in DAO: ";

    public static final String PARAMETER_ACCESS_LEVEL_TYPE = "accessLevelType";
    public static final String PARAMETER_ACCOUNT_STATUS = "accountStatus";
    public static final String PARAMETER_ACCOUNT_NUMBER = "accountNumber";
    public static final String PARAMETER_ID = "id";
    public static final String PARAMETER_CURRENCY_TYPE = "currencyType";
    public static final String PARAMETER_ACCOUNT_ID = "accountId";
    public static final String PARAMETER_OPERATION_DATE = "operationDate";
    public static final String PARAMETER_OPERATION_DESCRIPTION = "description";
    public static final String PARAMETER_OPERATION_AMOUNT = "amount";
    public static final String PARAMETER_USER_LAST_NAME = "userLastName";
    public static final String PARAMETER_USER_LOGIN = "login";

    public static final String HQL_GET_BY_ACCESS_LEVEL = "from AccessLevel where accessLevelType = :accessLevelType";
    public static final String HQL_GET_BY_CURRENCY_TYPE = "from Currency where currencyType = :currencyType";
    public static final String HQL_DELETE_BY_ACCOUNT_ID = "delete from Operation as o where o.account.id = :accountId";
    public static final String SQL_GET_OPERATIONS = "select o.F_DATE as operationDate, " +
                                                    "o.F_DESCRIPTION as description, " +
                                                    "o.F_AMOUNT as amount, " +
                                                    "u.F_LASTNAME as userLastName, " +
                                                    "a.F_ACCOUNTNUMBER as accountNumber " +
                                                    "from t_operation as o " +
                                                    "inner join t_user as u " +
                                                    "on o.F_USER_ID=u.F_ID " +
                                                    "inner join t_account as a " +
                                                    "on o.F_ACCOUNT_ID=a.F_ID ";
    public static final String HQL_GET_ALL_CLIENTS = " from User" /* user  join user.accessLevels level where level.accessLevelType = :accessLevelType"*/;
    public static final String HQL_GET_BY_LOGIN = "from User where login = :login";


    private Constants(){}
}
