package db.dao;

/**
 * Class with users SQL requests
 * @author Vladislav Prokopenko
 */
public class DaoUserRequest {

    public static final String GET_ALL_USER_ADMISSION = "SELECT a.date, f.name FROM admission a INNER JOIN faculty f ON a.faculty_id = f.id INNER JOIN user u ON u.id = a.user_id WHERE u.email = (?)";
    public static final String GET_ALL_USER_ADMISSION_UA = "SELECT a.date, f.name_ua FROM admission a INNER JOIN faculty f ON a.faculty_id = f.id INNER JOIN user u ON u.id = a.user_id WHERE u.email = (?)";

    public static final String GET_USER_BY_EMAIL = "SELECT * FROM user WHERE email = (?)";
    public static final String GET_USER_BY_EMAIL_AND_PASSWORD = "SELECT * FROM user WHERE email = (?) AND password = (?)";
    public static final String GET_USER_BY_ID = "SELECT * FROM user WHERE id = (?)";
    public static final String GET_USER_BY_IDN = "SELECT * FROM user WHERE idn = (?)";

    public static final String ADD_USER = "INSERT INTO user (email, idn , block, user_role_id, password) VALUES (?,?, 0, 1 , ?)";
    public static final String ADD_USER_DETAILS = "INSERT INTO user_details (user_id, name, surname, patronymic, city, region, school_name, average_certificate_point, name_ua, surname_ua, patronymic_ua, city_ua, region_ua, school_name_ua) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static final String GET_USER_DETAILS_BY_ID = "SELECT user_id, name, surname, patronymic, city, region, school_name, average_certificate_point FROM user_details WHERE user_id = (?)";
    public static final String GET_USER_DETAILS_BY_ID_UA = "SELECT user_id, name_ua, surname_ua, patronymic_ua, city_ua, region_ua, school_name_ua, average_certificate_point FROM user_details WHERE user_id = (?)";



    public static final String GET_ALL_USER_SUBJECTS_BY_EMAIL = "SELECT ur.subject_exam_id FROM users_results ur INNER JOIN user u ON u.id =  ur.user_id WHERE u.email = (?)";
    public static final String GET_ALL_USER_MARKS_BY_EMAIL = "SELECT ur.user_id, ur.subject_exam_id, ur.result, ur.date_of_exam FROM users_results ur INNER JOIN user u ON u.id =  ur.user_id WHERE u.email = (?)";
    public static final String GET_SUBJECT_EXAM_BY_ID = "SELECT * FROM subject_exam WHERE id = (?)";
    public static final String INSERT_NEW_USER_MARK = "INSERT INTO users_results(user_id, subject_exam_id, result, date_of_exam) VALUES (?,?,?,?)";

    public static final String DELETE_ALL_USER_MARKS = "DELETE FROM users_results WHERE user_id =(?) and subject_exam_id = (?)";
    public static final String DELETE_ALL_USER_ADMISSION = "DELETE FROM admission WHERE user_id=(?)";
    public static final String SELECT_USER_ADMISSIONS_FOR_FACULTY = "SELECT * FROM admission WHERE user_id = (?) AND faculty_id = (?)";
    public static final String DELETE_USER_ADMISSION = "DELETE a FROM admission a INNER JOIN faculty f ON a.faculty_id = f.id INNER JOIN user u ON u.id = a.user_id WHERE u.id = (?) AND f.name = (?)";
    public static final String DELETE_USER_ADMISSION_UA = "DELETE a FROM admission a INNER JOIN faculty f ON a.faculty_id = f.id INNER JOIN user u ON u.id = a.user_id WHERE u.id = (?) AND f.name_ua = (?)";

    public static final String UPDATE_USER_BY_ID = "UPDATE user SET email=(?), idn = (?), password = (?) WHERE id = (?)";
    public static final String UPDATE_USER_DETAILS_BY_ID = "UPDATE user_details SET name = (?), surname = (?), patronymic = (?), city = (?), region = (?), school_name = (?), average_certificate_point = (?), name_ua = (?), surname_ua = (?), patronymic_ua = (?), city_ua = (?), region_ua = (?), school_name_ua = (?) WHERE user_id = (?)";



    public static final String GET_ALL_USERS_WITH_LIMIT = "SELECT * FROM user ORDER BY email LIMIT ?,?";
    public static final String GET_TOTAL_COUNT_OF_USERS = "SELECT COUNT(*) FROM user";

    public static final String BLOCK_USER_BY_ID = "UPDATE user SET block = 1 WHERE id = (?)";
    public static final String UNBLOCK_USER_BY_ID = "UPDATE user SET block = 0 WHERE id = (?)";

    public static final String SET_TO_USER_ADMIN_ROLE_BY_ID = "UPDATE user SET user_role_id = 2 WHERE id = (?)";
    public static final String SET_TO_USER_USER_ROLE_BY_ID = "UPDATE user SET user_role_id = 1 WHERE id = (?)";

    public static final String DELETE_ALL_USER_RESULS_BY_SUBJECT_EXAM_ID = "DELETE FROM users_results WHERE subject_exam_id = (?)";



    public static final String SELECT_ALL_USERS_ADMISSIONS_FOR_FACULTY = "SELECT * FROM admission WHERE faculty_id = (?) AND date BETWEEN (?) AND CURDATE();";
    public static final String SELECT_ALL_APPLIED_USERS_ADMISSIONS_FOR_FACULTY = "SELECT * FROM admission WHERE faculty_id = (?) AND is_approved = '1' AND date BETWEEN (?) AND CURDATE();";

    public static final String GET_USER_ADMISSION_RESULTS_BY_ID_FACULTY_ID = "SELECT id, fullName, idn, totalResult, certificate_point, is_approved FROM" +
            "((SELECT id as id FROM admission WHERE user_id =(?) AND faculty_id = (?) )AS id" +
            "," +
            "(SELECT CONCAT(surname,' ', name, ' ', patronymic) as fullName FROM user_details WHERE user_id = (?) )AS fullName" +
            "," +
            "(SELECT idn as idn FROM user WHERE id =(?) ) AS idn" +
            "," +
            "(SELECT average_certificate_point as certificate_point FROM user_details WHERE user_id =(?) )AS certificate_point" +
            "," +
            "(SELECT SUM(result) as totalResult FROM users_results WHERE user_id =(?) AND subject_exam_id IN(SELECT subject_exam_id FROM faculty_exam_demends WHERE faculty_id = (?))) AS totalResult" +
            "," +
            "(SELECT is_approved AS is_approved FROM admission WHERE user_id =(?) AND faculty_id = (?)) AS is_approved)";

    public static final String GET_USER_ADMISSION_RESULTS_BY_ID_FACULTY_ID_UA = "SELECT id, fullName, idn, totalResult, certificate_point, is_approved FROM" +
            "((SELECT id as id FROM admission WHERE user_id =(?) AND faculty_id = (?) )AS id" +
            "," +
            "(SELECT CONCAT(surname_ua,' ', name_ua, ' ', patronymic_ua) as fullName FROM user_details WHERE user_id = (?) )AS fullName" +
            "," +
            "(SELECT idn as idn FROM user WHERE id =(?) ) AS idn" +
            "," +
            "(SELECT average_certificate_point as certificate_point FROM user_details WHERE user_id =(?) )AS certificate_point" +
            "," +
            "(SELECT SUM(result) as totalResult FROM users_results WHERE user_id =(?) AND subject_exam_id IN(SELECT subject_exam_id FROM faculty_exam_demends WHERE faculty_id = (?))) AS totalResult" +
            "," +
            "(SELECT is_approved AS is_approved FROM admission WHERE user_id =(?) AND faculty_id = (?)) AS is_approved)";

    public static final String GET_USER_ADMISSION_RESULTS_BY_ID_FACULTY_ID_APPROVED = "SELECT id, fullName, idn, totalResult, certificate_point, is_approved FROM" +
            "((SELECT id as id FROM admission WHERE user_id =(?) AND faculty_id = (?) )AS id" +
            "," +
            "(SELECT CONCAT(surname,' ', name, ' ', patronymic) as fullName FROM user_details WHERE user_id = (?) )AS fullName" +
            "," +
            "(SELECT idn as idn FROM user WHERE id =(?) ) AS idn" +
            "," +
            "(SELECT average_certificate_point as certificate_point FROM user_details WHERE user_id =(?) )AS certificate_point" +
            "," +
            "(SELECT SUM(result) as totalResult FROM users_results WHERE user_id =(?) AND subject_exam_id IN(SELECT subject_exam_id FROM faculty_exam_demends WHERE faculty_id = (?))) AS totalResult" +
            "," +
            "(SELECT is_approved AS is_approved FROM admission WHERE user_id =(?) AND faculty_id = (?)) AS is_approved) WHERE is_approved=1";

    public static final String GET_USER_ADMISSION_RESULTS_BY_ID_FACULTY_ID_APPROVED_UA = "SELECT id, fullName, idn, totalResult, certificate_point, is_approved FROM" +
            "((SELECT id as id FROM admission WHERE user_id =(?) AND faculty_id = (?) )AS id" +
            "," +
            "(SELECT CONCAT(surname_ua,' ', name_ua, ' ', patronymic_ua) as fullName FROM user_details WHERE user_id = (?) )AS fullName" +
            "," +
            "(SELECT idn as idn FROM user WHERE id =(?) ) AS idn" +
            "," +
            "(SELECT average_certificate_point as certificate_point FROM user_details WHERE user_id =(?) )AS certificate_point" +
            "," +
            "(SELECT SUM(result) as totalResult FROM users_results WHERE user_id =(?) AND subject_exam_id IN(SELECT subject_exam_id FROM faculty_exam_demends WHERE faculty_id = (?))) AS totalResult" +
            "," +
            "(SELECT is_approved AS is_approved FROM admission WHERE user_id =(?) AND faculty_id = (?)) AS is_approved)  WHERE is_approved=1";

}
