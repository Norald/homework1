package db.dao;
/**
 * Class with faculties SQL requests
 * @author Vladislav Prokopenko
 */
public class DaoFacultyRequests {
    public static final String GET_ALL_FACULTIES = "SELECT * FROM faculty";
    public static final String GET_FACULTY_BY_ID = " SELECT * FROM faculty WHERE id = (?)";
    public static final String GET_FACULTY_EXAM_DEMENDS_BY_ID = " SELECT se.id FROM subject_exam se INNER JOIN faculty_exam_demends fed ON se.id = fed.subject_exam_id INNER JOIN faculty f ON f.id = fed.faculty_id WHERE f.id = (?)";


    public static final String GET_FACULTY_EXAM_DEMENDS_WITH_FACULTY_NAME_BY_ID = " SELECT se.id, se.name, se.description FROM subject_exam se INNER JOIN faculty_exam_demends fed ON se.id = fed.subject_exam_id INNER JOIN faculty f ON f.id = fed.faculty_id WHERE f.id = (?)";
    public static final String GET_FACULTY_EXAM_DEMENDS_WITH_FACULTY_NAME_BY_ID_UA = " SELECT se.id, se.name_ua, se.description_ua FROM subject_exam se INNER JOIN faculty_exam_demends fed ON se.id = fed.subject_exam_id INNER JOIN faculty f ON f.id = fed.faculty_id WHERE f.id = (?)";

    public static final String ADD_FACULTY = "INSERT INTO faculty (name, budget_amount, total_amount, description, name_ua, description_ua) VALUES(?,?,?,?,?,?)";
    public static final String ADD_EXAM_DEMENDS_BY_FACULTY_ID = "INSERT INTO faculty_exam_demends (subject_exam_id, faculty_id) VALUES(?,?)";
    public static final String DELETE_EXAM_DEMENDS = "DELETE FROM faculty_exam_demends WHERE subject_exam_id = (?) AND faculty_id = (?)";
    public static final String GET_ALL_SUBJECTS = "SELECT * FROM subject_exam";
    public static final String ADD_SUBJECT = "INSERT INTO subject_exam(name,description, name_ua, description_ua) VALUES (?, ?, ?, ?)";
    public static final String DELETE_SUBJECT_BY_ID = "DELETE FROM subject_exam WHERE id = (?)";

    public static final String DELETE_FACULTY_BY_ID = "DELETE FROM faculty WHERE id = (?)";
    public static final String DELETE_FACULTY_EXAM_DEMENDS_BY_FACULTY_ID = "DELETE FROM faculty_exam_demends WHERE faculty_id = (?)";


    public static final String GET_ALL_FACULTIES_LIMIT_ORDER_BY_ZA = "SELECT * FROM faculty ORDER BY name DESC LIMIT ?,?";
    public static final String GET_ALL_FACULTIES_LIMIT_ORDER_BY_AZ = "SELECT * FROM faculty ORDER BY name ASC LIMIT ?,?";
    public static final String GET_ALL_FACULTIES_LIMIT_ORDER_BY_AZ_UA = "SELECT * FROM faculty ORDER BY name_ua ASC LIMIT ?,?";
    public static final String GET_ALL_FACULTIES_LIMIT_ORDER_BY_ZA_UA = "SELECT * FROM faculty ORDER BY name_ua DESC LIMIT ?,?";
    public static final String GET_ALL_FACULTIES_LIMIT_ORDER_BY_BUDGET_AMOUNT = "SELECT * FROM faculty ORDER BY budget_amount DESC LIMIT ?,? ";
    public static final String GET_ALL_FACULTIES_LIMIT_ORDER_BY_TOTAL_AMOUNT = "SELECT * FROM faculty ORDER BY total_amount DESC LIMIT ?,? ";

    public static final String GET_TOTAL_COUNT_OF_FACULTIES = "SELECT COUNT(*) FROM faculty";
    public static final String ADD_USER_ADMISSION = "INSERT INTO admission(user_id, faculty_id, date, is_approved) VALUES (?, ?, ?, ?)";


    public static final String DELETE_ALL_FACULTY_ADMISSION_BY_ID = "DELETE FROM admission WHERE faculty_id = (?)";

    public static final String GET_TOTAL_COUNT_OF_SUBJECT_EXAMS = "SELECT COUNT(*) FROM subject_exam";

    public static final String GET_ALL_SUBJECT_EXAMS_WITH_LIMIT_UA = "SELECT id, name_ua, description_ua FROM subject_exam LIMIT ?,?";
    public static final String GET_ALL_SUBJECT_EXAMS_WITH_LIMIT = "SELECT id, name, description FROM subject_exam LIMIT ?,?";

    public static final String GET_FACULTIES_BY_SUBJECT_EXAM_DEMENDS = "SELECT faculty_id FROM faculty_exam_demends WHERE subject_exam_id = (?)";
    public static final String SET_ADMISSION_STATUS_APPROVED = "UPDATE admission SET is_approved = 1 WHERE id = (?)";
    public static final String SET_ADMISSION_STATUS_DISAPPROVED = "UPDATE admission SET is_approved = 0 WHERE id = (?)";

}
