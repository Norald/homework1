package service;

import controller.AuthController;
import db.dao.FacultyDao;
import db.dao.UserDao;
import exception.*;
import model.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class UserService {
    private static final Logger LOG = LogManager.getLogger(UserService.class);


    private UserDao userDao;
    private FacultyDao facultyDao;
    @Autowired
    public UserService(UserDao userDao, FacultyDao facultyDao) {
        this.userDao = userDao;
        this.facultyDao = facultyDao;
    }

    public UserService() {
    }

    public User findUser(HttpServletRequest request) {
        try {
            String log = request.getParameter("login");
            String pass = request.getParameter("pass");
            User user = userDao.findUser(log, pass);

            if (user != null) {
                //check if user blocked
                if (user.isBlocked()) {
                    throw new UserBlockedException();

                } else {
                    return user;
                }
            } else {
                throw new WrongEmailOrPasswordException();
            }
        } catch (Exception e) {
            throw new EmptyParametersException();
        }
    }

    public String getLanguage(HttpServletRequest request) {
        try {
            String language = request.getParameter("lang");
            if (!language.isEmpty()) {
                return language;
            } else {
                throw new EmptyLanguageException();
            }
        } catch (Exception e) {
            throw new EmptyLanguageException();
        }
    }

    public void doRegistration(HttpServletRequest request) {
        try {
            String email = request.getParameter("email");
            String pass1 = request.getParameter("pass1");
            String pass2 = request.getParameter("pass2");
            String idn = request.getParameter("idn");
            String english_name = request.getParameter("name");
            String english_surname = request.getParameter("surname");
            String english_patronymic = request.getParameter("patronymic");
            String english_city = request.getParameter("city");
            String english_region = request.getParameter("region");
            String english_school_name = request.getParameter("school_name");
            String average_certificate_point = request.getParameter("average_certificate_point");
            String ukrainian_name = request.getParameter("name_ua");
            String ukrainian_surname = request.getParameter("surname_ua");
            String ukrainian_patronymic = request.getParameter("patronymic_ua");
            String ukrainian_city = request.getParameter("city_ua");
            String ukrainian_region = request.getParameter("region_ua");
            String ukrainian_school_name = request.getParameter("school_name_ua");

            if (email.isEmpty() || pass1.isEmpty() || pass2.isEmpty() || idn.isEmpty() ||
                    english_name.isEmpty() || english_surname.isEmpty() || english_patronymic.isEmpty() || english_city.isEmpty() ||
                    english_region.isEmpty() || english_school_name.isEmpty() || average_certificate_point.isEmpty() || ukrainian_name.isEmpty() ||
                    ukrainian_surname.isEmpty() || ukrainian_patronymic.isEmpty() || ukrainian_city.isEmpty() || ukrainian_region.isEmpty() ||
                    ukrainian_school_name.isEmpty()) {
                throw new EmptyParametersException();
            } else {
                User user = findUser(email);
                //check if email already exists
                if (user != null) {
                    throw new SuchEmailExistException();
                }
                user = findUserByIdn(idn);
                //check if identification number already exists
                if (user != null) {
                    throw new SuchIdnExistException();
                } else if (!pass1.equals(pass2)) { //check if passwords match
                    throw new PasswordDontMatchException();
                } else {
                    LOG.info("Registration with email is successfull " + email);
                    addUser(email, Long.parseLong(idn), pass1);
                    user = findUser(email);
                    addUserDetails(user.getId(), english_name, english_surname, english_patronymic, english_city, english_region, english_school_name, Integer.parseInt(average_certificate_point), ukrainian_name, ukrainian_surname, ukrainian_patronymic, ukrainian_city, ukrainian_region, ukrainian_school_name);
                }
            }
        } catch (Exception e) {
            throw new EmptyParametersException();
        }
    }

    public User findUser(String email) {
        return userDao.findUser(email);
    }

    public User findUserByIdn(String idn) {
        return userDao.findUserByIdn(idn);
    }

    public void addUser(String email, Long idn, String password) {
        userDao.addUser(email, idn, password);
    }

    public void addUserDetails(int userId, String name, String surname, String patronymic, String city, String region, String schoolName, int certificatePoint, String nameUa, String surnameUa, String patronymicUa, String cityUa, String regionUa, String schoolNameUa) {
        userDao.addUserDetails(userId, name, surname, patronymic, city, region, schoolName, certificatePoint, nameUa, surnameUa, patronymicUa, cityUa, regionUa, schoolNameUa);
    }

    public void addUserMark(int userId, int idSubjectExam, int result) {
        userDao.addUserMark(userId, idSubjectExam, result);
    }

    public List<UserResult> findUserResults(String email, String locale) {
        return userDao.findUserResult(email, locale);
    }

    public void removeUserResult(int userId, int idSubjectExam) {
        userDao.removeUserResults(userId, idSubjectExam);
    }

    public void removeUserAdmissions(int userId) {
        userDao.removeUserAdmissions(userId);
    }

    public Map<String, Date> findUserAdmissions(User user, String locale) {
        return userDao.findUserAdmissions(user, locale);
    }

    public void deleteUserAdmission(int userId, String facultyName, String locale) {
        userDao.deleteUserAdmission(userId, facultyName, locale);
    }

    public Set<Integer> getUserSubjects(String email) {
        return userDao.getUserSubjects(email);
    }

    public List<Admission> getUserAdmissionForFaculty(int userId, int facultyId) {
        return userDao.getUserAdmissionForFaculty(userId, facultyId);
    }

    public void addUserAdmissionToFaculty(int userId, int facultyId) {
        userDao.addUserAdmissionToFaculty(userId, facultyId);
    }

    public UserDetails findUserDetails(User user, String locale) {
        return userDao.findUserDetails(user, locale);
    }

    public void updateUser(String email, long idn, String password, int idUser) {
        userDao.updateUser(email, idn, password, idUser);
    }

    public void updateDetails(String name, String surname, String patronymic, String city, String region, String schoolName, int averageCertificatePoint, String nameUa, String surnameUa, String patronymicUa, String cityUa, String regionUa, String schoolNameUa, int idUser) {
        userDao.updateDetails(name, surname, patronymic, city, region, schoolName, averageCertificatePoint, nameUa, surnameUa, patronymicUa, cityUa, regionUa, schoolNameUa, idUser);
    }

    public void deleteAllResultsBySubjectExamId(int subjectExamId) {
        userDao.deleteAllResultsBySubjectExamId(subjectExamId);
    }

    public int getTotalCountOfUsers() {
        return userDao.getTotalCountOfUsers();
    }

    public List<User> findAllUsersWithLimit(int startValue, int amountOnPage) {
        return userDao.findAllUsersWithLimit(startValue, amountOnPage);
    }

    public void blockUserById(int userId) {
        userDao.blockUserById(userId);
    }

    public void unblockUserById(int userId) {
        userDao.unblockUserById(userId);
    }

    public void makeUserAdmin(int userId) {
        userDao.makeUserAdmin(userId);
    }

    public void makeAdminUser(int userId) {
        userDao.makeAdminUser(userId);
    }


    public List<Admission> getAllUsersAdmissionsForFacultyWithDate(int faculty_id, Date date) {
        return userDao.getAllUsersAdmissionsForFacultyWithDate(faculty_id, date);
    }

    public List<Admission> getAllAppliedUsersAdmissionsForFacultyWithDate(int faculty_id, Date date) {
        return userDao.getAllAppliedUsersAdmissionsForFacultyWithDate(faculty_id, date);
    }

    public UserFinalStatementResult getFinalStatementResultForFaculty(int userId, int facultyId, String locale) {
        return userDao.getFinalStatementResultForFaculty(userId, facultyId, locale);
    }

    public UserFinalStatementResult getAllAppliedUsersAdmissionsForFacultyWithDate(int userId, int facultyId, String locale) {
        return userDao.getLateFinalStatementResultForFaculty(userId, facultyId, locale);
    }

    public void addUserMark(HttpServletRequest request) {
        try {
            String mark = request.getParameter("mark");
            String examId = request.getParameter("marksSelect");
            String userEmail = (String) request.getSession().getAttribute("email");
            if (request.getParameter("mark") != null || request.getParameter("marksSelect") != null) {
                //find user
                User user = findUser(userEmail);
                //adding mark for user
                addUserMark(user.getId(), Integer.parseInt(examId), Integer.parseInt(mark));
            } else{
                throw new EmptyParametersException();
            }
        } catch (Exception e) {
            throw new EmptyParametersException();
        }
    }

    public void deleteUserMark(HttpServletRequest request) {
        try {
            String userid = request.getParameter("subjectid");
            if (userid.isEmpty() || userid.equals("")) {
                throw new WrongIdOfSubjectExamException();
            } else {
                String userEmail = (String) request.getSession().getAttribute("email");
                User user = findUser(userEmail);

                removeUserResult(user.getId(), Integer.parseInt(userid));

                //if user delete his marks - all his admissions will be deleted
                removeUserAdmissions(user.getId());

            }
        } catch (Exception e) {
            throw new EmptyParametersException();
        }
    }

    public Map<String, Date> deleteUserAdmission(HttpServletRequest request, String locale) {
        try {
            String faculty_Name = request.getParameter("faculty_name");
            if (!faculty_Name.isEmpty()) {
                User user = findUser((String) request.getSession().getAttribute("email"));
                //deleting admission
                deleteUserAdmission(user.getId(), faculty_Name, locale);
                Map<String, Date> mapOfAdmissions = findUserAdmissions(user, locale);
                return mapOfAdmissions;
            } else {
                throw new WrongFacultyException();
            }
        } catch (Exception e) {
            throw new EmptyParametersException();
        }
    }

    public Map<String, Date> participateUser(HttpServletRequest request, String locale){
        try{
            String faculty_id = request.getParameter("faculty_id");
            //checking if values are empty
            if (faculty_id.equals("") || faculty_id.isEmpty()) {
                throw new EmptyFacultyIdException();
            } else {

                User user = findUser((String) request.getSession().getAttribute("email"));

                List<Admission> listAdmission = getUserAdmissionForFaculty(user.getId(), Integer.parseInt(faculty_id));
                Set<Integer> list = facultyDao.getFacultyDemends(faculty_id);


                //If faculty have no demends, user can`t apply
                if (list.size() < 3) {
                    throw new FacultyHaveNoDemendsException();
                } else if (listAdmission.isEmpty()) {

                   addUserAdmissionToFaculty(user.getId(), Integer.parseInt(faculty_id));
                    Map<String, Date> mapOfAdmissions = findUserAdmissions(user, locale);
                    return mapOfAdmissions;

                } else {//if user already send admission
                    throw new AlreadySendedAmdissionException();
                }
            }
        }catch (Exception e){
            throw new EmptyParametersException();
        }
    }

    public void changeUserInfo(HttpServletRequest request, String locale){
        try{
            String email = request.getParameter("email");
            String pass1 = request.getParameter("pass1");
            String idn = request.getParameter("idn");
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            String patronymic = request.getParameter("patronymic");
            String city = request.getParameter("city");
            String region = request.getParameter("region");
            String school_name = request.getParameter("school_name");
            String average_certificate_point = request.getParameter("average_certificate_point");
            String name_ua = request.getParameter("name_ua");
            String surname_ua = request.getParameter("surname_ua");
            String patronymic_ua = request.getParameter("patronymic_ua");
            String city_ua = request.getParameter("city_ua");
            String region_ua = request.getParameter("region_ua");
            String school_name_ua = request.getParameter("school_name_ua");

            if (email.isEmpty() || pass1.isEmpty() || idn.isEmpty() ||
                    name.isEmpty() || surname.isEmpty() || patronymic.isEmpty() || city.isEmpty() ||
                    region.isEmpty() || school_name.isEmpty() || average_certificate_point.isEmpty() || name_ua.isEmpty() ||
                    surname_ua.isEmpty() || patronymic_ua.isEmpty() || city_ua.isEmpty() || region_ua.isEmpty() ||
                    school_name_ua.isEmpty()) {

                throw new EmptyParametersException();
            } else {
                //find user by email, show us if such email exists
                User user = findUser(email);

                //get user from session
                User sessionUser = findUser((String) request.getSession().getAttribute("email"));

                //if email exists
                if (user != null && !user.getEmail().equals(sessionUser.getEmail())) {
                    throw new SuchEmailExistException();
                }
                user = findUserByIdn(idn);

                //if identification number exists
                if (user != null && user.getIdn() != sessionUser.getIdn()) {
                    throw new SuchIdnExistException();
                } else {


                    //if user changed email, then change email in session
                    request.getSession().removeAttribute("email");
                    request.getSession().setAttribute("email", email);


                    //updating user and user details
                    updateUser(email, Long.parseLong(idn), pass1, sessionUser.getId());
                    updateDetails(name, surname, patronymic, city, region, school_name, Integer.parseInt(average_certificate_point), name_ua, surname_ua, patronymic_ua, city_ua, region_ua, school_name_ua, sessionUser.getId());
                    //if user changed his details - all user admission will delete
                    removeUserAdmissions(sessionUser.getId());
                }

            }
        }catch (Exception e){
            throw new EmptyParametersException();
        }
    }

    public List<User> findAllUsersWithLimitImp(int startValue, int facultyCountOnPage){
        List<User> users = findAllUsersWithLimit(startValue, facultyCountOnPage);
        if (!users.isEmpty()) {
            return users;
        } else {
            throw new CantGetUsersException();
        }
    }

    public boolean blockUnblockUser(HttpServletRequest request){
        boolean blockCheck = false;
        try{
            String operation = request.getParameter("operation");
            String userId = request.getParameter("id");

            if (request.getParameter("operation") == null || request.getParameter("id") == null) {
                throw new EmptyParametersException();
            } else if (request.getParameter("operation").equals("block") || request.getParameter("operation").equals("unblock")) {
                //if operation 'block' - then block user
                if (operation.equals("block")) {
                    blockUserById(Integer.parseInt(userId));
                    blockCheck = true;
                    return blockCheck;

                } else {//else - unblock user
                    unblockUserById(Integer.parseInt(userId));
                    blockCheck = false;
                    return blockCheck;
                }
            } else {
                throw new WrongOperationException();
            }
        }catch (Exception e){
            throw new EmptyParametersException();
        }
    }

    public void setUnsetUserRole(HttpServletRequest request){
        try{
            String operation = request.getParameter("operation");
            String userId = request.getParameter("id");
            //check if parameters not null
            if (request.getParameter("operation") == null || request.getParameter("id") == null) {
                throw new EmptyParametersException();
            } else if (request.getParameter("operation").equals("makeUser") || request.getParameter("operation").equals("makeAdmin")) {
                //if operation - makeAdmin, set to user Admin role
                if (operation.equals("makeAdmin")) {
                    makeUserAdmin(Integer.parseInt(userId));
                } else {//set to admin User role
                    makeAdminUser(Integer.parseInt(userId));
                }
            } else {
                throw new WrongOperationException();
            }
        }catch (Exception e){
            throw new EmptyParametersException();
        }
    }


}
