package pl.edu.pwr.healthycar.api.utilities;

public class Endpoints {

    public static final String REPORTS = "/reports";
    public static final String REPORTS_ID = "/reports/{id}";
    public static final String REPORTS_CAR_CARID = "/reports/car/{carId}";
    public static final String REPORTS_SAVE = "/reports/save";
    public static final String REPORTS_DELETE_ID = "/reports/delete/{id}";

    public static final String RIDES = "/rides";
    public static final String RIDES_ID = "/rides/{id}";
    public static final String RIDES_USER_USERID = "/rides/user/{userId}";
    public static final String RIDES_LATEST_CARID = "/rides/latest/{carId}";
    public static final String RIDES_SAVE = "/rides/save";
    public static final String RIDES_DELETE_ID = "/rides/delete/{id}";

    public static final String CARS = "/cars";
    public static final String CARS_ID = "/cars/{id}";
    public static final String CARS_OWNER_OWNERID = "/cars/owner/{ownerId}";
    public static final String CARS_SAVE = "/cars/save";
    public static final String CARS_DELETE_ID = "/cars/delete/{id}";

    public static final String USERS = "/users";
    public static final String USERS_ID = "/users/{id}";
    public static final String USERS_SAVE = "/users/save";
    public static final String USERS_DELETE_ID = "/users/delete/{id}";
    public static final String USERS_LOGIN = "/users/login";
    public static final String USERS_RESET = "/users/reset";


    public static String buildRequestLog(String endpoint) {
        return "REQ " + endpoint + " => ";
    }

    public static String buildResponseLog(String endpoint) {
        return "RES " + endpoint + " => ";
    }
}
