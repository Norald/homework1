package model;

import org.springframework.stereotype.Component;

import java.sql.Date;

/**
 * Class that describes user result.
 * Like exam and mark, etc.
 * @author Vladislav Prokopenko.
 */
@Component
public class UserResult {
    /**
     * User id;
     */
    private int userId;

    /**
     * Subject exam;
     */
    private SubjectExam subject_exam;

    /**
     * Result for exam;
     */
    private int result;

    /**
     * Date of exam;
     */
    private Date dateOfExam;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public SubjectExam getSubject_exam() {
        return subject_exam;
    }

    public void setSubject_exam(SubjectExam subject_exam) {
        this.subject_exam = subject_exam;
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
                ", subject_exam=" + subject_exam +
                ", result=" + result +
                ", dateOfExam=" + dateOfExam +
                '}';
    }
}
