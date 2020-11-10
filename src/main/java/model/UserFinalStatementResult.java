package model;

import org.springframework.stereotype.Component;

/**
 * Class that describes user results which will be described in statement.
 * @author Vladislav Prokopenko.
 */
@Component
public class UserFinalStatementResult {
    /**
     * ID of statement;
     */
    private int id;

    /**
     * Full username;
     */
    private String fullname;

    /**
     * Identification number of user;
     */
    private long idn;

    /**
     * Total user exam result;
     */
    private int totalExamResult;

    /**
     * User certificate point;
     */
    private int certificatePoint;

    /**
     * Is admission approved;
     */
    private boolean is_approved;

    /**
     * Total result. Sum of totalExamResult and certificate point;
     */
    private int totalResult;


    /**
     * Get total result
     * @return Sum of totalExamResult and certificate point
     */
    public int getTotalResult() {
        this.totalResult = totalExamResult+ certificatePoint;
        return this.totalResult;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public long getIdn() {
        return idn;
    }

    public void setIdn(long idn) {
        this.idn = idn;
    }

    public int getTotalExamResult() {
        return totalExamResult;
    }

    public void setTotalExamResult(int totalExamResult) {
        this.totalExamResult = totalExamResult;
    }

    public int getCertificatePoint() {
        return certificatePoint;
    }

    public void setCertificatePoint(int certificatePoint) {
        this.certificatePoint = certificatePoint;
    }

    public boolean isIs_approved() {
        return is_approved;
    }

    public void setIs_approved(boolean is_approved) {
        this.is_approved = is_approved;
    }


}
