package za.co.bakery.backend.data;

public class Role {
    private static final String WAITER = "Waiter";
    private static final String ADMIN = "Admin";
    private static final String BAKER = "Baker";

    private Role(){}

    public static String[] getAllRoles(){return new String[]{BAKER,BAKER, ADMIN};}
}
