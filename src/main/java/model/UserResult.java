package model;

import java.sql.Date;

/**
 * Class that describes user result.
 * Like exam and mark, etc.
 *
 * @author Vladislav Prokopenko.
 */
public class UserResult {

    private int userId;


    private SubjectExam subjectExam;


    private int result;


    private Date dateOfExam;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public SubjectExam getSubjectExam() {
        return subjectExam;
    }

    public void setSubjectExam(SubjectExam subjectExam) {
        this.subjectExam = subjectExam;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Date getDateOfExam() {
        return dateOfExam;
    }

    public void setDateOfExam(Date dateOfExam) {
        this.dateOfExam = dateOfExam;
    }

    @Override
    public String toString() {
        return "UserResult{" +
                "userId=" + userId +
                ", subject_exam=" + subjectExam +
                ", result=" + result +
                ", dateOfExam=" + dateOfExam +
                '}';
    }
}
