package db.dao;

import model.Faculty;
import model.SubjectExam;
import db.DBManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Data access object for faculties entities.
 *
 * @author Vladislav Prokopenko
 */

@Repository
public class FacultyDao {

    private static final Logger LOG = LogManager.getLogger(FacultyDao.class.getName());

    public FacultyDao() {
    }

    /**
     * Set admission status DISAPPROVED
     *
     * @param admissionId admission id
     */
    public void disapproveAdmission(int admissionId) {
        PreparedStatement pstmt = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(DaoFacultyRequests.SET_ADMISSION_STATUS_DISAPPROVED);
            pstmt.setInt(1, admissionId);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    }


    /**
     * Set admission status APPROVED
     *
     * @param admissionId admission id
     */
    public void approveAdmission(int admissionId) {
        PreparedStatement pstmt = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(DaoFacultyRequests.SET_ADMISSION_STATUS_APPROVED);
            pstmt.setInt(1, admissionId);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    }

    /**
     * Getting all subject exams with limit.
     *
     * @param startValue   value to start
     * @param amountOnPage amount to get from db
     * @param locale       locale
     * @return list of Subject Exams
     */
    public List<SubjectExam> getSubjectExamsWithLimit(int startValue, int amountOnPage, String locale) {
        String request;
        String fieldName;
        String fieldDescription;
        if (locale.equals("uk")) {
            request = DaoFacultyRequests.GET_ALL_SUBJECT_EXAMS_WITH_LIMIT_UA;
            fieldName = DBFields.SUBJECT__NAME_UA;
            fieldDescription = DBFields.SUBJECT__DESCRIPTION_UA;
        } else {
            request = DaoFacultyRequests.GET_ALL_SUBJECT_EXAMS_WITH_LIMIT;
            fieldName = DBFields.SUBJECT__NAME;
            fieldDescription = DBFields.SUBJECT__DESCRIPTION;
        }
        ArrayList<SubjectExam> examsList = new ArrayList<SubjectExam>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            FacultyMapper facultyMapper = new FacultyMapper();
            pstmt = con.prepareStatement(request);
            pstmt.setInt(1, startValue);
            pstmt.setInt(2, amountOnPage);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SubjectExam subjectExam = new SubjectExam();
                subjectExam.setId(rs.getInt(DBFields.ENTITY__ID));
                subjectExam.setName(rs.getString(fieldName));
                subjectExam.setDescription(rs.getString(fieldDescription));
                examsList.add(subjectExam);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return examsList;
    }


    /**
     * Getting total count of exams.
     * Need for pagination.
     *
     * @return count of exams.
     */
    public int getTotalCountOfSubjectExams() {
        int result = 0;
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            FacultyMapper facultyMapper = new FacultyMapper();
            stmt = con.createStatement();
            rs = stmt.executeQuery(DaoFacultyRequests.GET_TOTAL_COUNT_OF_SUBJECT_EXAMS);
            if (rs.next()) {
                result = rs.getInt(1);
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return result;
    }

    /**
     * Getting total count of faculties.
     * Need for pagination.
     *
     * @return count of faculties.
     */
    public int getTotalCountOfFaculty() {
        int result = 0;
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            FacultyMapper facultyMapper = new FacultyMapper();
            stmt = con.createStatement();
            rs = stmt.executeQuery(DaoFacultyRequests.GET_TOTAL_COUNT_OF_FACULTIES);
            if (rs.next()) {
                result = rs.getInt(1);
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return result;
    }

    /**
     * Get list of all faculties with limit.
     * Order by budget amount.
     *
     * @param startValue   value to start
     * @param amountOnPage amount to get from db
     * @param locale       locale
     * @return sorted list of faculties
     */
    public List<Faculty> getFacultiesWithLimitOrderBugdet(int startValue, int amountOnPage, String locale) {
        ArrayList<Faculty> facultiesList = new ArrayList<Faculty>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            FacultyMapper facultyMapper = new FacultyMapper();
            pstmt = con.prepareStatement(DaoFacultyRequests.GET_ALL_FACULTIES_LIMIT_ORDER_BY_BUDGET_AMOUNT);
            pstmt.setInt(1, startValue);
            pstmt.setInt(2, amountOnPage);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Faculty faculty;
                faculty = facultyMapper.mapRow(rs, locale);
                facultiesList.add(faculty);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return facultiesList;
    }

    /**
     * Get list of all faculties with limit.
     * Order by total amount.
     *
     * @param startValue   value to start
     * @param amountOnPage amount to get from db
     * @param locale       locale
     * @return sorted list of faculties
     */
    public List<Faculty> getFacultiesWithLimitOrderTotal(int startValue, int amountOnPage, String locale) {
        ArrayList<Faculty> facultiesList = new ArrayList<Faculty>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            FacultyMapper facultyMapper = new FacultyMapper();
            pstmt = con.prepareStatement(DaoFacultyRequests.GET_ALL_FACULTIES_LIMIT_ORDER_BY_TOTAL_AMOUNT);
            pstmt.setInt(1, startValue);
            pstmt.setInt(2, amountOnPage);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Faculty faculty;
                faculty = facultyMapper.mapRow(rs, locale);
                facultiesList.add(faculty);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return facultiesList;
    }

    /**
     * Get list of all faculties with limit.
     * Order by alphabet.
     *
     * @param startValue   value to start
     * @param amountOnPage amount to get from db
     * @param locale       locale
     * @return sorted list of faculties
     */
    public List<Faculty> getFacultiesWithLimitOrderAZ(int startValue, int amountOnPage, String locale) {
        String request;
        if (locale.equals("uk")) {
            request = DaoFacultyRequests.GET_ALL_FACULTIES_LIMIT_ORDER_BY_AZ_UA;
        } else {
            request = DaoFacultyRequests.GET_ALL_FACULTIES_LIMIT_ORDER_BY_AZ;
        }
        ArrayList<Faculty> facultiesList = new ArrayList<Faculty>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            FacultyMapper facultyMapper = new FacultyMapper();
            pstmt = con.prepareStatement(request);
            pstmt.setInt(1, startValue);
            pstmt.setInt(2, amountOnPage);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Faculty faculty;
                faculty = facultyMapper.mapRow(rs, locale);
                facultiesList.add(faculty);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return facultiesList;
    }

    /**
     * Get list of all faculties with limit.
     * Order by reverse alphabet.
     *
     * @param startValue   value to start
     * @param amountOnPage amount to get from db
     * @param locale       locale
     * @return sorted list of faculties
     */
    public List<Faculty> getFacultiesWithLimitOrderZA(int startValue, int amountOnPage, String locale) {
        String request;
        if (locale.equals("uk")) {
            request = DaoFacultyRequests.GET_ALL_FACULTIES_LIMIT_ORDER_BY_ZA_UA;
        } else {
            request = DaoFacultyRequests.GET_ALL_FACULTIES_LIMIT_ORDER_BY_ZA;
        }
        ArrayList<Faculty> facultiesList = new ArrayList<Faculty>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            FacultyMapper facultyMapper = new FacultyMapper();
            pstmt = con.prepareStatement(request);
            pstmt.setInt(1, startValue);
            pstmt.setInt(2, amountOnPage);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Faculty faculty;
                faculty = facultyMapper.mapRow(rs, locale);
                facultiesList.add(faculty);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return facultiesList;
    }

//    public List<Faculty> getAllFacultiesNoOffset(String locale){
//        ArrayList<Faculty> facultiesList = new ArrayList<Faculty>();
//        Statement stmt = null;
//        ResultSet rs = null;
//        Connection con = null;
//        try {
//            con = DBManager.getInstance().getConnection();
//            FacultyMapper facultyMapper = new FacultyMapper();
//            stmt = con.createStatement();
//            rs = stmt.executeQuery(DaoFacultyRequests.GET_ALL_FACULTIES);
//            while (rs.next()) {
//                Faculty faculty = facultyMapper.mapRow(rs, locale);
//                facultiesList.add(faculty);
//            }
//            rs.close();
//            stmt.close();
//        } catch (SQLException ex) {
////            DBManager.getInstance().rollbackAndClose(con);
//            LOG.error(ex.getMessage(),ex);
//        } finally {
//            DBManager.getInstance().commitAndClose(con);
//        }
//        return facultiesList;
//    }

    /**
     * Get faculty by id.
     *
     * @param id     id of faculty
     * @param locale locale
     * @return faculty
     */
    public Faculty findFacultyById(String id, String locale) {
        Faculty faculty = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            FacultyMapper facultyMapper = new FacultyMapper();
            pstmt = con.prepareStatement(DaoFacultyRequests.GET_FACULTY_BY_ID);
            pstmt.setInt(1, Integer.parseInt(id));
            rs = pstmt.executeQuery();
            if (rs.next())
                faculty = facultyMapper.mapRow(rs, locale);
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return faculty;
    }

    /**
     * Get Set of faculty demends;
     *
     * @param id id of faculty
     * @return Set of faculties
     */
    public Set<Integer> getFacultyDemends(String id) {
        Set<Integer> demendsList = new TreeSet<Integer>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            FacultyMapper facultyMapper = new FacultyMapper();
            pstmt = con.prepareStatement(DaoFacultyRequests.GET_FACULTY_EXAM_DEMENDS_BY_ID);
            pstmt.setInt(1, Integer.parseInt(id));
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int idDemend = rs.getInt(DBFields.ENTITY__ID);
                demendsList.add(idDemend);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return demendsList;
    }

//    public Set<Integer> getFacultiesByDemends(String idSubjectExam){
//        Set<Integer> demendsList = new TreeSet<Integer>();
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        Connection con = null;
//        try {
//            con = DBManager.getInstance().getConnection();
//            FacultyMapper facultyMapper = new FacultyMapper();
//            pstmt = con.prepareStatement(DaoFacultyRequests.GET_FACULTIES_BY_SUBJECT_EXAM_DEMENDS);
//            pstmt.setInt(1, Integer.parseInt(idSubjectExam));
//            rs = pstmt.executeQuery();
//            while (rs.next()) {
//                int idDemend = rs.getInt("faculty_id");
//                demendsList.add(idDemend);
//            }
//            rs.close();
//            pstmt.close();
//        } catch (SQLException ex) {
////            DBManager.getInstance().rollbackAndClose(con);
//            LOG.error(ex.getMessage(),ex);
//        } finally {
//            DBManager.getInstance().commitAndClose(con);
//        }
//        return demendsList;
//    }

    /**
     * Get a list of exams that are required by the faculty
     *
     * @param id     faculty id
     * @param locale locale
     * @return list of exams
     */
    public List<SubjectExam> getFacultyDemendsWithName(String id, String locale) {
        List<SubjectExam> examList = new ArrayList<>();
        String request;

        if (locale.equals("en")) {
            request = DaoFacultyRequests.GET_FACULTY_EXAM_DEMENDS_WITH_FACULTY_NAME_BY_ID;
        } else {
            request = DaoFacultyRequests.GET_FACULTY_EXAM_DEMENDS_WITH_FACULTY_NAME_BY_ID_UA;
        }
        Set<Integer> demendsList = new TreeSet<Integer>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            FacultyMapper facultyMapper = new FacultyMapper();
            pstmt = con.prepareStatement(request);
            pstmt.setInt(1, Integer.parseInt(id));
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SubjectExam subjectExam = new SubjectExam();
                subjectExam.setId(rs.getInt(1));
                subjectExam.setName(rs.getString(2));
                subjectExam.setDescription(rs.getString(3));
                examList.add(subjectExam);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return examList;
    }

    /**
     * Delete all faculty demend by exam amd faculty
     *
     * @param examId    id of exam
     * @param facultyId id of faculty
     */
    public void deleteExamDemendForFaculty(int examId, int facultyId) {
        PreparedStatement pstmt = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(DaoFacultyRequests.DELETE_EXAM_DEMENDS);
            pstmt.setInt(1, examId);
            pstmt.setInt(2, facultyId);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    }

    /**
     * Delete all faculty admissions
     *
     * @param facultyId faculty id
     */
    public void deleteAllFacultyAdmissions(int facultyId) {
        PreparedStatement pstmt = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(DaoFacultyRequests.DELETE_ALL_FACULTY_ADMISSION_BY_ID);
            pstmt.setInt(1, facultyId);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    }


    /**
     * Get list of all subject exams
     *
     * @param locale locale
     * @return list of subject exams
     */
    public List<SubjectExam> getAllSubjectExams(String locale) {
        String fieldName = "";
        String fieldDescription = "";
        String request = "";
        if (locale.equals("en")) {
            request = DaoFacultyRequests.GET_ALL_SUBJECTS;
            fieldName = DBFields.SUBJECT__NAME;
            fieldDescription = DBFields.SUBJECT__DESCRIPTION;
        } else {
            request = DaoFacultyRequests.GET_ALL_SUBJECTS;
            fieldName = DBFields.SUBJECT__NAME_UA;
            fieldDescription = DBFields.SUBJECT__DESCRIPTION_UA;
        }
        List<SubjectExam> subjectsList = new ArrayList<SubjectExam>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            FacultyMapper facultyMapper = new FacultyMapper();
            pstmt = con.prepareStatement(DaoFacultyRequests.GET_ALL_SUBJECTS);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SubjectExam subjectExam = new SubjectExam();
                subjectExam.setId(rs.getInt(DBFields.ENTITY__ID));
                subjectExam.setName(rs.getString(fieldName));
                subjectExam.setDescription(rs.getString(fieldDescription));
                subjectsList.add(subjectExam);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return subjectsList;
    }


    /**
     * Delete faculty
     *
     * @param facultyId faculty id
     */
    public void deleteFacultyById(int facultyId) {
        PreparedStatement pstmt = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(DaoFacultyRequests.DELETE_FACULTY_BY_ID);
            pstmt.setInt(1, facultyId);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    }

    /**
     * Delete all exam demends for faculty
     *
     * @param facultyId faculty id
     */
    public void deleteExamDemndsFacultyFacultyById(int facultyId) {
        PreparedStatement pstmt = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(DaoFacultyRequests.DELETE_FACULTY_EXAM_DEMENDS_BY_FACULTY_ID);
            pstmt.setInt(1, facultyId);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    }

    /**
     * Delete subject exam
     *
     * @param subjectExamId subject exam id
     */
    public void deleteSubjectExamById(int subjectExamId) {
        PreparedStatement pstmt = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(DaoFacultyRequests.DELETE_SUBJECT_BY_ID);
            pstmt.setInt(1, subjectExamId);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    }

    /**
     * Add exam demends for faculty
     *
     * @param idExam    exam id
     * @param idFaculty faculty id
     */
    public void addExamDemendForFaculty(int idExam, int idFaculty) {
        PreparedStatement pstmt = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(DaoFacultyRequests.ADD_EXAM_DEMENDS_BY_FACULTY_ID);
            pstmt.setInt(1, idExam);
            pstmt.setInt(2, idFaculty);

            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    }

    /**
     * Adding faculty
     *
     * @param name           faculty name
     * @param budget_amount  budget amount of places in faculty
     * @param total_amount   total amount of places in faculty
     * @param description    faculty description
     * @param name_ua        faculty name in ukrainian
     * @param description_ua faculty description in ukrainian
     */
    public void addFaculty(String name, int budget_amount, int total_amount, String description, String name_ua, String description_ua) {
        PreparedStatement pstmt = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(DaoFacultyRequests.ADD_FACULTY);
            pstmt.setString(1, name);
            pstmt.setInt(2, budget_amount);
            pstmt.setInt(3, total_amount);
            pstmt.setString(4, description);
            pstmt.setString(5, name_ua);
            pstmt.setString(6, description_ua);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    }

    /**
     * Adding subject exam
     *
     * @param name           name of subject exam
     * @param description    description of subject exam
     * @param name_ua        name of subject exam in ukrainian
     * @param description_ua description of subject exam in ukrainian
     */
    public void addSubjectExam(String name, String description, String name_ua, String description_ua) {
        PreparedStatement pstmt = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(DaoFacultyRequests.ADD_SUBJECT);
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setString(3, name_ua);
            pstmt.setString(4, description_ua);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
            LOG.error(ex.getMessage(), ex);
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    }


    /**
     * Static class for mapping faculty
     */
    private static class FacultyMapper {

        public Faculty mapRow(ResultSet rs, String locale) {
            try {
                String fieldName = "";
                String fieldDescription = "";
                if (locale.equals("en")) {
                    fieldName = DBFields.FACULTY__NAME;
                    fieldDescription = DBFields.FACULTY__DESCRIPTION;
                } else {
                    fieldName = DBFields.FACULTY__NAME_UA;
                    fieldDescription = DBFields.FACULTY__DESCRIPTION_UA;
                }
                Faculty faculty = new Faculty();
                faculty.setId(rs.getInt(DBFields.ENTITY__ID));
                faculty.setName(rs.getString(fieldName));
                faculty.setBudgetAmount(rs.getInt(DBFields.FACULTY_BUDGET__AMOUNT));
                faculty.setTotalAmount(rs.getInt(DBFields.FACULTY_TOTAL__AMOUNT));
                faculty.setDescription(rs.getString(fieldDescription));
                faculty.setLogoUrl(rs.getString(DBFields.FACULTY_LOGO__URL));
                return faculty;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
