package model;

import org.springframework.stereotype.Component;

/**
 * Class that describes user details.
 * Like name, surname etc.
 * @author Vladislav Prokopenko.
 */
@Component
public class UserDetails {
    /**
     * User ID
     */
    private int userId;

    /**
     * User Name
     */
    private String name;

    /**
     * User Surname
     */
    private String surname;

    /**
     * User patronymic
     */
    private String patronymic;

    /**
     * User city
     */
    private String city;

    /**
     * User region
     */
    private String region;

    /**
     * User school name
     */
    private String school_name;

    /**
     * User documents url
     */
    private String document_url;

    /**
     * User certificate point
     */
    private int average_certificate;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public String getDocument_url() {
        return document_url;
    }

    public void setDocument_url(String document_url) {
        this.document_url = document_url;
    }

    public int getAverage_certificate() {
        return average_certificate;
    }

    public void setAverage_certificate(int average_certificate) {
        this.average_certificate = average_certificate;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", school_name='" + school_name + '\'' +
                ", document_url='" + document_url + '\'' +
                ", average_certificate=" + average_certificate +
                '}';
    }
}
