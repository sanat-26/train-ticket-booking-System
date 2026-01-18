package ticketBooking.util;
import org.mindrot.jbcrypt.BCrypt;

public class userServiceUtil {

    // Hash a plain-text password
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
        // 12 = work factor (recommended default)
    }
        // Verify a plain password against a stored hash
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}