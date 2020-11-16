package service;

import db.dao.FacultyDao;
import model.Faculty;
import model.SubjectExam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Set;

@Component
public class FacultyService {

    private FacultyDao facultyDao;

    @Autowired
    public FacultyService(FacultyDao facultyDao) {
        this.facultyDao = facultyDao;
    }

    public FacultyService() {
    }

    public int getCountOfFaculties() {
        return facultyDao.getTotalCountOfFaculty();
    }

    public List<Faculty> getFacultiesWithLimitOrderAZ(int startValue, int amountOnPage, String locale) {
        return facultyDao.getFacultiesWithLimitOrderAZ(startValue, amountOnPage, locale);
    }

    public List<Faculty> getFacultiesWithLimitOrderZA(int startValue, int amountOnPage, String locale) {
        return facultyDao.getFacultiesWithLimitOrderZA(startValue, amountOnPage, locale);
    }

    public List<Faculty> getFacultiesWithLimitOrderBugdet(int startValue, int amountOnPage, String locale) {
        return facultyDao.getFacultiesWithLimitOrderBugdet(startValue, amountOnPage, locale);
    }

    public List<Faculty> getFacultiesWithLimitOrderTotal(int startValue, int amountOnPage, String locale) {
        return facultyDao.getFacultiesWithLimitOrderTotal(startValue, amountOnPage, locale);
    }

    public List<SubjectExam> getAllSubjectExamsForFaculty(String locale) {
        return facultyDao.getAllSubjectExams(locale);
    }

    public Faculty findFacultyById(String id, String locale) {
        return facultyDao.findFacultyById(id, locale);
    }

    public Set<Integer> getFacultyDemends(String id) {
        return facultyDao.getFacultyDemends(id);
    }

    public void addFaculty(String name, int budgetAmount, int totalAmount, String description, String nameUa, String descriptionUa) {
        facultyDao.addFaculty(name, budgetAmount, totalAmount, description, nameUa, descriptionUa);
    }

    public void deleteFacultyById(int idFaculty) {
        facultyDao.deleteFacultyById(idFaculty);
    }

    public void deleteExamDemndsFacultyById(int idFaculty) {
        facultyDao.deleteExamDemndsFacultyFacultyById(idFaculty);
    }

    public void deleteAllFacultyAdmissions(int idFaculty) {
        facultyDao.deleteAllFacultyAdmissions(idFaculty);
    }

    public List<SubjectExam> getSubjectExamsWithLimit(int startValue, int amountOnPage, String locale){
        return facultyDao.getSubjectExamsWithLimit(startValue, amountOnPage, locale);
    }

    public int getTotalCountOfSubjectExams() {
        return facultyDao.getTotalCountOfSubjectExams();
    }

    public void addSubjectExam(String name, String description, String nameUa, String descriptionUa) {
        facultyDao.addSubjectExam(name, nameUa, description, descriptionUa);
    }

    public void deleteSubjectExamById(int subjectExamId) {
        facultyDao.deleteSubjectExamById(subjectExamId);
    }

    public List<SubjectExam> getFacultyDemendsWithName (String id, String locale){
        return facultyDao.getFacultyDemendsWithName(id, locale);
    }

    public List<SubjectExam> getAllSubjectExams (String locale){
        return facultyDao.getAllSubjectExams(locale);
    }

    public void addExamDemendForFaculty(int idExam, int idFaculty) {
        facultyDao.addExamDemendForFaculty(idExam, idFaculty);
    }

    public void deleteExamDemendForFaculty(int examId, int facultyId) {
        facultyDao.deleteExamDemendForFaculty(examId, facultyId);
    }

    public void approveAdmission(int admissionId) {
        facultyDao.approveAdmission(admissionId);
    }

    public void disapproveAdmission(int admissionId) {
        facultyDao.disapproveAdmission(admissionId);
    }

}
