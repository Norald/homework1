package model;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

/**
 * Class that describes admission to faculty;
 * @author Vladislav Prokopenko;
 */
@Component
public class Admission {
    /**
     * ID of admission
     */
    private int id;

    /**
     * ID of user
     */
    private int user_id;

    /**
     * ID of faculty
     */
    private int faculty_id;

    /**
     * Date of admission
     */
    private Date date;

    /**
     * Is approved admission
     */
    private boolean is_approved;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getFaculty_id() {
        return faculty_id;
    }

    public void setFaculty_id(int faculty_id) {
        this.faculty_id = faculty_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isIs_approved() {
        return is_approved;
    }

    public void setIs_approved(boolean is_approved) {
        this.is_approved = is_approved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admission admission = (Admission) o;
        return id == admission.id &&
                user_id == admission.user_id &&
                faculty_id == admission.faculty_id &&
                is_approved == admission.is_approved &&
                Objects.equals(date, admission.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_id, faculty_id, date, is_approved);
    }

    @Override
    public String toString() {
        return "Admission{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", faculty_id=" + faculty_id +
                ", date=" + date +
                ", is_approved=" + is_approved +
                '}';
    }
}
