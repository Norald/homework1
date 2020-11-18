package db.dao;

import db.DBManager;
import model.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.*;
import java.util.*;

/**
 * Data access object for user entities.
 *
 * @author Vladislav Prokopenko
 */
@Repository
public class UserDao {
    private static final Logger LOG = LogManager.getLogger(UserDao.class.getName());

    @Autowired
    private DBManager dbManager;

//    @Autowired
//    public UserDao(DBManager dbManager) {
//        this.dbManager = dbManager;
//    }


    /**
     * Adding user
     *
     * @param email    user email
     * @param idn      user identification number
     * @param password user password
     */
    public void addUser(String email, long idn, String password) {
        PreparedStatement pstmt = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(DaoUserRequest.ADD_USER);
            pstmt.setString(1, email);
            pstmt.setLong(2, idn);
            pstmt.setString(3, password);

            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            dbManager.commitAndClose(con);
        }
    }

    /**
     * Add user details
     *
     * @param userId            user id
     * @param name              user name
     * @param surname           user surname
     * @param patronymic        user patronymic
     * @param city              user cirt
     * @param region            user region
     * @param school_name       user school name
     * @param certificate_point user average certificate point
     * @param name_ua           user name ukrainian
     * @param surname_ua        user surname ukrainian
     * @param patronymic_ua     user patronymic ukrainian
     * @param city_ua           user city ukrainian
     * @param region_ua         user region ukrainian
     * @param school_name_ua    user school name ukrainian
     */
    public void addUserDetails(int userId, String name, String surname, String patronymic, String city, String region, String school_name, int certificate_point, String name_ua, String surname_ua, String patronymic_ua, String city_ua, String region_ua, String school_name_ua) {
        PreparedStatement pstmt = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(DaoUserRequest.ADD_USER_DETAILS);
            pstmt.setInt(1, userId);
            pstmt.setString(2, name);
            pstmt.setString(3, surname);
            pstmt.setString(4, patronymic);
            pstmt.setString(5, city);
            pstmt.setString(6, region);
            pstmt.setString(7, school_name);
            pstmt.setInt(8, certificate_point);
            pstmt.setString(9, name_ua);
            pstmt.setString(10, surname_ua);
            pstmt.setString(11, patronymic_ua);
            pstmt.setString(12, city_ua);
            pstmt.setString(13, region_ua);
            pstmt.setString(14, school_name_ua);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            dbManager.commitAndClose(con);
        }
    }

    /**
     * Get user details
     *
     * @param user   user
     * @param locale locale
     * @return user details
     */
    public UserDetails findUserDetails(User user, String locale) {

        String request;
        String name;
        String surname;
        String patronymic;
        String city;
        String region;
        String school_name;
        if (locale.equals("en")) {
            request = DaoUserRequest.GET_USER_DETAILS_BY_ID;
            name = DBFields.USER__NAME;
            surname = DBFields.USER__SURNAME;
            patronymic = DBFields.USER__PATRONYMIC;
            city = DBFields.USER__CITY;
            region = DBFields.USER__REGION;
            school_name = DBFields.USER__SCHOOL_NAME;
        } else {
            request = DaoUserRequest.GET_USER_DETAILS_BY_ID_UA;
            name = DBFields.USER__NAME_UA;
            surname = DBFields.USER__SURNAME_UA;
            patronymic = DBFields.USER__PATRONYMIC_UA;
            city = DBFields.USER__CITY_UA;
            region = DBFields.USER__REGION_UA;
            school_name = DBFields.USER__SCHOOL_NAME_UA;
        }

        UserDetails details = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            UserMapper mapper = new UserMapper();
            pstmt = con.prepareStatement(request);
            pstmt.setInt(1, user.getId());
            rs = pstmt.executeQuery();
            if (rs.next())
                details = new UserDetails();
            details.setUserId(rs.getInt(DBFields.USER__ID));
            details.setName(rs.getString(name));
            details.setSurname(rs.getString(surname));
            details.setPatronymic(rs.getString(patronymic));
            details.setCity(rs.getString(city));
            details.setRegion(rs.getString(region));
            details.setSchoolName(rs.getString(school_name));
            details.setAverage_certificate(rs.getInt(DBFields.USER__AVERAGE_CERTIFICATE_POINT));
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            dbManager.commitAndClose(con);
        }
        return details;
    }


    /**
     * Get Map of user admissions
     *
     * @param user   user
     * @param locale locale
     * @return Map of user admissions
     */
    public Map<String, Date> findUserAdmissions(User user, String locale) {
        String fieldName = "";
        String request = "";
        if (locale.equals("en")) {
            request = DaoUserRequest.GET_ALL_USER_ADMISSION;
            fieldName = DBFields.FACULTY__NAME;
        } else {
            request = DaoUserRequest.GET_ALL_USER_ADMISSION_UA;
            fieldName = DBFields.FACULTY__NAME_UA;

        }

        Map<String, Date> mapAdmissionList = new HashMap<String, Date>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(request);
            pstmt.setString(1, user.getEmail());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Date date = rs.getDate(DBFields.ADMISSION__DATE);
                String facultyName = rs.getString(fieldName);
                mapAdmissionList.put(facultyName, date);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            dbManager.commitAndClose(con);
        }
        return mapAdmissionList;
    }


//    public User findUser(int id) {
//        User user = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        Connection con = null;
//        try {
//            con = DBManager.getInstance().getConnection();
//            UserMapper mapper = new UserMapper();
//            pstmt = con.prepareStatement(DaoUserRequest.GET_USER_BY_ID);
//            pstmt.setInt(1, id);
//            rs = pstmt.executeQuery();
//            if (rs.next())
//                user = mapper.mapRow(rs);
//            rs.close();
//            pstmt.close();
//        } catch (SQLException ex) {
////            DBManager.getInstance().rollbackAndClose(con);
//            LOG.error(ex.getMessage(),ex);
//        } finally {
//            DBManager.getInstance().commitAndClose(con);
//        }
//        return user;
//    }

    /**
     * Find user by email
     *
     * @param email user email
     * @return user
     */
    public User findUser(String email) {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            UserMapper mapper = new UserMapper();
            pstmt = con.prepareStatement(DaoUserRequest.GET_USER_BY_EMAIL);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            if (rs.next())
                user = mapper.mapRow(rs);
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            dbManager.commitAndClose(con);
        }
        return user;
    }

    /**
     * Find user by identification number
     *
     * @param idn identification number
     * @return user
     */
    public User findUserByIdn(String idn) {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            UserMapper mapper = new UserMapper();
            pstmt = con.prepareStatement(DaoUserRequest.GET_USER_BY_IDN);
            pstmt.setLong(1, Long.parseLong(idn));
            rs = pstmt.executeQuery();
            if (rs.next())
                user = mapper.mapRow(rs);
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            dbManager.commitAndClose(con);
        }
        return user;
    }


    /**
     * Find user by email and password.
     * Method need for authorisation.
     *
     * @param email
     * @param password
     * @return user
     */
    public User findUser(String email, String password) {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            UserMapper mapper = new UserMapper();
            pstmt = con.prepareStatement(DaoUserRequest.GET_USER_BY_EMAIL_AND_PASSWORD);
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            if (rs.next())
                user = mapper.mapRow(rs);
            con.commit();
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            if (con != null) {
                dbManager.commitAndClose(con);
            }
        }
        return user;
    }


    /**
     * Get Set of user subjects by email
     *
     * @param email user email
     * @return Set of user subjects
     */
    public Set<Integer> getUserSubjects(String email) {
        Set<Integer> userSubjects = new TreeSet<Integer>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(DaoUserRequest.GET_ALL_USER_SUBJECTS_BY_EMAIL);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int idSubject = rs.getInt(DBFields.SUBJECT__EXAM_ID);
                userSubjects.add(idSubject);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            dbManager.commitAndClose(con);
        }
        return userSubjects;
    }


    /**
     * Get all user results by email
     *
     * @param email  user email
     * @param locale locale
     * @return list of all user results
     */
    public List<UserResult> findUserResult(String email, String locale) {

        List<UserResult> resultList = new ArrayList<UserResult>();
        UserResult result = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
//            UserResultMapper mapper = new UserResultMapper();
            pstmt = con.prepareStatement(DaoUserRequest.GET_ALL_USER_MARKS_BY_EMAIL);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                result = new UserResult();
                result.setUserId(rs.getInt(DBFields.USER__ID));
                result.setSubjectExam(findSubjectExam(rs.getInt(DBFields.SUBJECT__EXAM_ID), locale));
                result.setResult(rs.getInt(DBFields.RESULT));
                result.setDateOfExam(rs.getDate(DBFields.DATE_OF_EXAM));
                resultList.add(result);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            //dbManager.commitAndClose(con);
        }
        return resultList;
    }

    /**
     * Find subject exam by id
     *
     * @param id     subject id
     * @param locale locale
     * @return subject exam
     */
    public SubjectExam findSubjectExam(int id, String locale) {
        String fieldDescription = "";
        String fieldName = "";
        String request = "";
        if (locale.equals("en")) {
            request = DaoUserRequest.GET_SUBJECT_EXAM_BY_ID;
            fieldName = DBFields.SUBJECT__NAME;
            fieldDescription = DBFields.SUBJECT__DESCRIPTION;
        } else {
            request = DaoUserRequest.GET_SUBJECT_EXAM_BY_ID;
            fieldName = DBFields.SUBJECT__NAME_UA;
            fieldDescription = DBFields.SUBJECT__DESCRIPTION_UA;
        }
        SubjectExam subjectExam = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            UserMapper mapper = new UserMapper();
            pstmt = con.prepareStatement(request);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next())
                subjectExam = new SubjectExam();
            subjectExam.setId(rs.getInt(DBFields.ENTITY__ID));
            subjectExam.setName(rs.getString(fieldName));
            subjectExam.setDescription(rs.getString(fieldDescription));
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            dbManager.commitAndClose(con);
        }
        return subjectExam;
    }


    /**
     * Add user mark
     *
     * @param userId        user id
     * @param idSubjectExam exam id
     * @param result        user result
     */
    public void addUserMark(int userId, int idSubjectExam, int result) {
        PreparedStatement pstmt = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(DaoUserRequest.INSERT_NEW_USER_MARK);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, idSubjectExam);
            pstmt.setInt(3, result);
            pstmt.setDate(4, new Date(System.currentTimeMillis()));
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            dbManager.commitAndClose(con);
        }
    }


    /**
     * Add user admission to faculty
     *
     * @param userId     user id
     * @param faculty_id faculty id
     */
    public void addUserAdmissionToFaculty(int userId, int faculty_id) {
        PreparedStatement pstmt = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(DaoFacultyRequests.ADD_USER_ADMISSION);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, faculty_id);
            pstmt.setDate(3, new Date(System.currentTimeMillis()));
            pstmt.setBoolean(4, false);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            dbManager.commitAndClose(con);
        }
    }

    /**
     * Get user admission for faculty
     *
     * @param user_id    user id
     * @param faculty_id faculty id
     * @return list of admissions
     */
    public List<Admission> getUserAdmissionForFaculty(int user_id, int faculty_id) {
        List<Admission> userAdmission = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            AdmissionMapper admissionMapper = new AdmissionMapper();
            pstmt = con.prepareStatement(DaoUserRequest.SELECT_USER_ADMISSIONS_FOR_FACULTY);
            pstmt.setInt(1, user_id);
            pstmt.setInt(2, faculty_id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Admission admission = admissionMapper.mapRow(rs);
                userAdmission.add(admission);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            dbManager.commitAndClose(con);
        }
        return userAdmission;
    }

    /**
     * Delete user results
     *
     * @param userId        user id
     * @param idSubjectExam exam id
     */
    public void removeUserResults(int userId, int idSubjectExam) {
        PreparedStatement pstmt = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(DaoUserRequest.DELETE_ALL_USER_MARKS);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, idSubjectExam);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            dbManager.commitAndClose(con);
        }
    }

    /**
     * Delete all user admissions
     *
     * @param userId user id
     */
    public void removeUserAdmissions(int userId) {
        PreparedStatement pstmt = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(DaoUserRequest.DELETE_ALL_USER_ADMISSION);
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            dbManager.commitAndClose(con);
        }
    }


    /**
     * Update user.
     *
     * @param email    user email
     * @param idn      user idn.
     * @param password user password.
     * @param idUser   user id.
     */
    public void updateUser(String email, long idn, String password, int idUser) {
        PreparedStatement pstmt = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(DaoUserRequest.UPDATE_USER_BY_ID);
            pstmt.setString(1, email);
            pstmt.setLong(2, idn);
            pstmt.setString(3, password);
            pstmt.setInt(4, idUser);

            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            dbManager.commitAndClose(con);
        }
    }

    /**
     * Update user details.
     *
     * @param name                      user name
     * @param surname                   user surname
     * @param patronymic                user patronymic
     * @param city                      user city
     * @param region                    user region
     * @param school_name               user school name
     * @param average_certificate_point user average certificate point
     * @param name_ua                   user name ukrainian
     * @param surname_ua                user surname ukrainian
     * @param patronymic_ua             user patronymic ukrainian
     * @param city_ua                   user city ukrainian
     * @param region_ua                 user region ukrainian
     * @param school_name_ua            user school name ukrainian
     * @param idUser                    user id
     */
    public void updateDetails(String name, String surname, String patronymic, String city, String region, String school_name, int average_certificate_point, String name_ua, String surname_ua, String patronymic_ua, String city_ua, String region_ua, String school_name_ua, int idUser) {
        PreparedStatement pstmt = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(DaoUserRequest.UPDATE_USER_DETAILS_BY_ID);
            pstmt.setString(1, name);
            pstmt.setString(2, surname);
            pstmt.setString(3, patronymic);
            pstmt.setString(4, city);
            pstmt.setString(5, region);
            pstmt.setString(6, school_name);
            pstmt.setInt(7, average_certificate_point);
            pstmt.setString(8, name_ua);
            pstmt.setString(9, surname_ua);
            pstmt.setString(10, patronymic_ua);
            pstmt.setString(11, city_ua);
            pstmt.setString(12, region_ua);
            pstmt.setString(13, school_name_ua);
            pstmt.setInt(14, idUser);


            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            dbManager.commitAndClose(con);
        }
    }

    /**
     * Delete user admission for faculty
     *
     * @param userId       user id
     * @param faculty_name faculty name
     * @param locale       locale
     */
    public void deleteUserAdmission(int userId, String faculty_name, String locale) {
        String fieldName = "";
        String request = "";
        if (locale.equals("en")) {
            request = DaoUserRequest.DELETE_USER_ADMISSION;
            fieldName = DBFields.FACULTY__NAME;
        } else {
            request = DaoUserRequest.DELETE_USER_ADMISSION_UA;
            fieldName = DBFields.FACULTY__NAME_UA;
        }

        PreparedStatement pstmt = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(request);
            pstmt.setInt(1, userId);
            pstmt.setString(2, faculty_name);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            dbManager.commitAndClose(con);
        }
    }

    /**
     * Get all user with limit
     *
     * @param startValue   start value of search
     * @param amountOnPage amount per request
     * @return List of users
     */
    public List<User> findAllUsersWithLimit(int startValue, int amountOnPage) {
        List<User> userList = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            UserMapper mapper = new UserMapper();
            pstmt = con.prepareStatement(DaoUserRequest.GET_ALL_USERS_WITH_LIMIT);
            pstmt.setInt(1, startValue);
            pstmt.setInt(2, amountOnPage);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                User user = mapper.mapRow(rs);
                userList.add(user);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            dbManager.commitAndClose(con);
        }
        return userList;
    }


    /**
     * Get total count of users
     *
     * @return count of users
     */
    public int getTotalCountOfUsers() {
        int result = 0;
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(DaoUserRequest.GET_TOTAL_COUNT_OF_USERS);
            if (rs.next()) {
                result = rs.getInt(1);
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            dbManager.commitAndClose(con);
        }
        return result;
    }


    /**
     * Block user by id
     *
     * @param userId user id
     */
    public void blockUserById(int userId) {
        PreparedStatement pstmt = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(DaoUserRequest.BLOCK_USER_BY_ID);
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            dbManager.commitAndClose(con);
        }
    }

    /**
     * Unblock user by id
     *
     * @param userId user id
     */
    public void unblockUserById(int userId) {
        PreparedStatement pstmt = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(DaoUserRequest.UNBLOCK_USER_BY_ID);
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            dbManager.commitAndClose(con);
        }
    }


    /**
     * Make user admin
     *
     * @param userId user id
     */
    public void makeUserAdmin(int userId) {
        PreparedStatement pstmt = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(DaoUserRequest.SET_TO_USER_ADMIN_ROLE_BY_ID);
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            dbManager.commitAndClose(con);
        }
    }

    /**
     * Set to admin user role
     *
     * @param userId user id
     */
    public void makeAdminUser(int userId) {
        PreparedStatement pstmt = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(DaoUserRequest.SET_TO_USER_USER_ROLE_BY_ID);
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            dbManager.commitAndClose(con);
        }
    }

    /**
     * Delete all results by exam
     *
     * @param subjectExamId exam id
     */
    public void deleteAllResultsBySubjectExamId(int subjectExamId) {
        PreparedStatement pstmt = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            pstmt = con.prepareStatement(DaoUserRequest.DELETE_ALL_USER_RESULS_BY_SUBJECT_EXAM_ID);
            pstmt.setInt(1, subjectExamId);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            dbManager.commitAndClose(con);
        }
    }

    /**
     * Get all users admissions for faculty with date.
     *
     * @param faculty_id faculty id
     * @param date       date
     * @return List of admissions
     */
    public List<Admission> getAllUsersAdmissionsForFacultyWithDate(int faculty_id, Date date) {
        List<Admission> userAdmission = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            AdmissionMapper admissionMapper = new AdmissionMapper();
            pstmt = con.prepareStatement(DaoUserRequest.SELECT_ALL_USERS_ADMISSIONS_FOR_FACULTY);
            pstmt.setInt(1, faculty_id);

            pstmt.setDate(2, date);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                Admission admission = admissionMapper.mapRow(rs);
                userAdmission.add(admission);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            dbManager.commitAndClose(con);
        }
        return userAdmission;
    }

    /**
     * Get all applied users admissions for faculty with date.
     *
     * @param faculty_id faculty id
     * @param date       date
     * @return List of admissions
     */
    public List<Admission> getAllAppliedUsersAdmissionsForFacultyWithDate(int faculty_id, Date date) {
        List<Admission> userAdmission = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = dbManager.getConnection();
            AdmissionMapper admissionMapper = new AdmissionMapper();
            pstmt = con.prepareStatement(DaoUserRequest.SELECT_ALL_APPLIED_USERS_ADMISSIONS_FOR_FACULTY);
            pstmt.setInt(1, faculty_id);

            pstmt.setDate(2, date);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                Admission admission = admissionMapper.mapRow(rs);
                userAdmission.add(admission);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            dbManager.commitAndClose(con);
        }
        return userAdmission;
    }


    /**
     * Get late final statement for user.
     *
     * @param userId    user id
     * @param facultyId faculty id
     * @param locale    locale
     * @return UserFinalStatementResult user final results
     */
    public UserFinalStatementResult getLateFinalStatementResultForFaculty(int userId, int facultyId, String locale) {
        UserFinalStatementResult userFinalStatementResults = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String request;

        if (locale.equals("uk")) {
            request = DaoUserRequest.GET_USER_ADMISSION_RESULTS_BY_ID_FACULTY_ID_APPROVED_UA;
        } else {
            request = DaoUserRequest.GET_USER_ADMISSION_RESULTS_BY_ID_FACULTY_ID_APPROVED;
        }
        try {
            con = dbManager.getConnection();
            UserMapper mapper = new UserMapper();
            pstmt = con.prepareStatement(request);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, facultyId);
            pstmt.setInt(3, userId);
            pstmt.setInt(4, userId);
            pstmt.setInt(5, userId);
            pstmt.setInt(6, userId);
            pstmt.setInt(7, facultyId);
            pstmt.setInt(8, userId);
            pstmt.setInt(9, facultyId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                userFinalStatementResults = new UserFinalStatementResult();
                userFinalStatementResults.setId(rs.getInt(DBFields.ENTITY__ID));
                userFinalStatementResults.setFullname(rs.getString(DBFields.STATEMENT__FULLNAME));
                userFinalStatementResults.setIdn(rs.getLong(DBFields.USER__IDN));
                userFinalStatementResults.setTotalExamResult(rs.getInt(DBFields.STATEMENT__TOTAL_RESULT));
                userFinalStatementResults.setCertificatePoint(rs.getInt(DBFields.STATEMENT__CERTIFICATE_POINT));
                userFinalStatementResults.setApproved(rs.getBoolean(DBFields.STATEMENT__IS_APPROVED));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            dbManager.commitAndClose(con);
        }
        return userFinalStatementResults;
    }


    /**
     * Get early final statement for user.
     *
     * @param userId    user id
     * @param facultyId faculty id
     * @param locale    locale
     * @return UserFinalStatementResult user final results
     */
    public UserFinalStatementResult getFinalStatementResultForFaculty(int userId, int facultyId, String locale) {
        UserFinalStatementResult userFinalStatementResults = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String request;

        if (locale.equals("uk")) {
            request = DaoUserRequest.GET_USER_ADMISSION_RESULTS_BY_ID_FACULTY_ID_UA;
        } else {
            request = DaoUserRequest.GET_USER_ADMISSION_RESULTS_BY_ID_FACULTY_ID;
        }
        try {
            con = dbManager.getConnection();
            UserMapper mapper = new UserMapper();
            pstmt = con.prepareStatement(request);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, facultyId);
            pstmt.setInt(3, userId);
            pstmt.setInt(4, userId);
            pstmt.setInt(5, userId);
            pstmt.setInt(6, userId);
            pstmt.setInt(7, facultyId);
            pstmt.setInt(8, userId);
            pstmt.setInt(9, facultyId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                userFinalStatementResults = new UserFinalStatementResult();
                userFinalStatementResults.setId(rs.getInt(DBFields.ENTITY__ID));
                userFinalStatementResults.setFullname(rs.getString(DBFields.STATEMENT__FULLNAME));
                userFinalStatementResults.setIdn(rs.getLong(DBFields.USER__IDN));
                userFinalStatementResults.setTotalExamResult(rs.getInt(DBFields.STATEMENT__TOTAL_RESULT));
                userFinalStatementResults.setCertificatePoint(rs.getInt(DBFields.STATEMENT__CERTIFICATE_POINT));
                userFinalStatementResults.setApproved(rs.getBoolean(DBFields.STATEMENT__IS_APPROVED));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            dbManager.commitAndClose(con);
        }
        return userFinalStatementResults;
    }


    /**
     * Static class for mapping User
     */
    public static class UserMapper {

        public UserMapper() {
        }

        public User mapRow(ResultSet rs) {
            try {
                User user = new User();
                user.setId(rs.getInt(DBFields.ENTITY__ID));
                user.setEmail(rs.getString(DBFields.USER__EMAIL));
                user.setIdn(rs.getLong(DBFields.USER__IDN));
                user.setBlocked(rs.getBoolean(DBFields.USER__BLOCK));
                user.setUserRoleId(rs.getInt(DBFields.USER__ROLE));
                user.setPassword(rs.getString(DBFields.USER__PASSWORD));
                return user;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /**
     * Static class for mapping UserResult
     */
    public static class UserResultMapper {
        private UserDao userDao;

        @Autowired
        public UserResultMapper(UserDao userDao) {
            this.userDao = userDao;
        }

        public UserResultMapper() {
        }

        public UserResult mapRow(ResultSet rs, String locale) {
            try {
                UserResult result = new UserResult();
                result.setUserId(rs.getInt(DBFields.USER__ID));
                result.setSubjectExam(userDao.findSubjectExam(rs.getInt(DBFields.SUBJECT__EXAM_ID), locale));
                result.setResult(rs.getInt(DBFields.RESULT));
                result.setDateOfExam(rs.getDate(DBFields.DATE_OF_EXAM));
                return result;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /**
     * Static class for mapping Admission
     */
    public static class AdmissionMapper {

        public AdmissionMapper() {
        }

        public Admission mapRow(ResultSet rs) {
            try {
                Admission admission = new Admission();
                admission.setId(rs.getInt(DBFields.ENTITY__ID));
                admission.setUserId(rs.getInt(DBFields.USER__ID));
                admission.setFacultyId(rs.getInt(DBFields.FACULTY__ID));
                admission.setDate(rs.getDate(DBFields.ADMISSION__DATE));
                admission.setApproved(rs.getBoolean(DBFields.STATEMENT__IS_APPROVED));
                return admission;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
