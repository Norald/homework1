package model;

/**
 * Enum that describes user role.
 * USER/ADMIN.
 *
 * @author Vladislav Prokopenko.
 */
public enum UserRole {
    USER, ADMIN;


    public static UserRole getRole(User user) {
        int roleId = user.getUserRoleId() - 1;
        return UserRole.values()[roleId];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
