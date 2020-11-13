package model;

/**
 * Class that describes user details.
 * Like name, surname etc.
 *
 * @author Vladislav Prokopenko.
 */
public class UserDetails {

    private int userId;

    private String name;


    private String surname;


    private String patronymic;


    private String city;


    private String region;


    private String schoolName;

    /**
     * User documents url
     */
    private String documentUrl;

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

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
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
                ", school_name='" + schoolName + '\'' +
                ", document_url='" + documentUrl + '\'' +
                ", average_certificate=" + average_certificate +
                '}';
    }
}
