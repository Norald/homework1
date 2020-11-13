package service;

import db.dao.UserDao;
import model.Admission;
import model.User;
import model.UserDetails;
import model.UserResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class UserService {


    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserService() {
    }

    public User findUser(String login, String password) {
        return userDao.findUser(login, password);
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
}
