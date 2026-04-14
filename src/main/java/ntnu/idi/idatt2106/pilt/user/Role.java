package ntnu.idi.idatt2106.pilt.user;

/**
 * Enum representing the different roles a user can have in the system.
 * Each role maps to a Spring Security authority string (e.g. "ROLE_TEACHER").
 */
public enum Role {
    TEACHER("ROLE_TEACHER"),
    STUDENT("ROLE_STUDENT");

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }
}
