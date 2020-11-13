package service;

import db.dao.FacultyDao;
import model.Faculty;
import model.SubjectExam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
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
}
