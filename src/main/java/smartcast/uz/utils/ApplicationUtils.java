package smartcast.uz.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import smartcast.uz.domain.User;

public class ApplicationUtils {
    public static Long getUserId() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

    public static String maskedPan(String pan){
        return new StringBuilder(pan)
                .replace(6, 12, "******")
                .toString();
    }
}
