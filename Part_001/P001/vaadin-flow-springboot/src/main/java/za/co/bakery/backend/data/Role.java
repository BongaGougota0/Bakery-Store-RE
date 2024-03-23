package za.co.bakery.backend.data;

public class Role {
    public static final String WAITER = "Waiter";
    public static final String ADMIN = "Admin";
    public static final String BAKER = "Baker";

    private Role(){}

    public static String[] getAllRoles(){return new String[]{BAKER,BAKER, ADMIN};}
}
