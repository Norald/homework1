package model;

import java.util.Date;
import java.util.Objects;

/**
 * Class that describes admission to faculty;
 *
 * @author Vladislav Prokopenko;
 */

public class Admission {

    private int id;


    private int userId;


    private int facultyId;

    /**
     * Date of admission
     */
    private Date date;


    private boolean isApproved;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        this.isApproved = approved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admission admission = (Admission) o;
        return id == admission.id &&
                userId == admission.userId &&
                facultyId == admission.facultyId &&
                isApproved == admission.isApproved &&
                Objects.equals(date, admission.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, facultyId, date, isApproved);
    }

    @Override
    public String toString() {
        return "Admission{" +
                "id=" + id +
                ", user_id=" + userId +
                ", faculty_id=" + facultyId +
                ", date=" + date +
                ", is_approved=" + isApproved +
                '}';
    }
}
