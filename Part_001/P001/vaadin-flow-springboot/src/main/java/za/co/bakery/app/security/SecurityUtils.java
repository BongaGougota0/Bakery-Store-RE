package za.co.bakery.app.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public final class SecurityUtils {
    private SecurityUtils(){}

    public static String getUsername(){
        SecurityContext context = SecurityContextHolder.getContext();
        if(context != null && context.getAuthentication() != null){
            Object principal = context.getAuthentication().getPrincipal();
            if(principal instanceof UserDetails){
                UserDetails userDetails = (UserDetails) context.getAuthentication().getPrincipal();
                return userDetails.getUsername();
            }
        }
        //In the case the user not logged in or Anonymous.
        return null;
    }

    public static boolean isUserLoggedIn(){
        return isUserLoggedIn(SecurityContextHolder.getContext().getAuthentication());
    }

    private static boolean isUserLoggedIn(Authentication authentication){
        return authentication != null && !(authentication instanceof AnonymousAuthenticationToken);
    }
}
