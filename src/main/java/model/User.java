package model;

import org.springframework.stereotype.Component;

/**
 * Class that describes user.
 * @author Vladislav Prokopenko.
 */
@Component
public class User {
    /**
     * ID of user;
     */
    private int id;

    /**
     * EMAIL of user;
     */
    private String email;

    /**
     * Identification number of user;
     */
    private long idn;

    /**
     * Check if user blocked;
     */
    private boolean blocked;

    /**
     * User role id;
     */
    private int user_role_id;

    /**
     * User password;
     */
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getIdn() {
        return idn;
    }

    public void setIdn(long idn) {
        this.idn = idn;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public int getUser_role_id() {
        return user_role_id;
    }

    public void setUser_role_id(int user_role_id) {
        this.user_role_id = user_role_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", idn=" + idn +
                ", blocked=" + blocked +
                ", user_role_id=" + user_role_id +
                ", password='" + "**********" + '\'' +
                '}';
    }
}
