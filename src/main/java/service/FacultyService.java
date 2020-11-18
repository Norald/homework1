package service;

import controller.AdminActionsController;
import db.dao.FacultyDao;
import db.dao.UserDao;
import exception.*;
import model.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import path.PathApp;
import pdf.CreateStatement;
import pdf.StatementWorker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static controller.AdminActionsController.getDateFromString;

@Component
public class FacultyService {

    private static final Logger LOG = LogManager.getLogger(FacultyService.class);

    private FacultyDao facultyDao;
    private UserDao userDao;

    @Autowired
    public FacultyService(FacultyDao facultyDao, UserDao userDao) {
        this.facultyDao = facultyDao;
        this.userDao = userDao;
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

    public List<Faculty> getFacultiesWithSorting(String sort, int startValue, int facultyCountOnPage, String locale){
        List<Faculty> faculties = null;
        if (sort.equals("sortAZ")) {
            faculties = getFacultiesWithLimitOrderAZ(startValue, facultyCountOnPage, locale);
        } else if (sort.equals("sortZA")) {
            faculties = getFacultiesWithLimitOrderZA(startValue, facultyCountOnPage, locale);
        } else if (sort.equals("sortBudget")) {
            faculties = getFacultiesWithLimitOrderBugdet(startValue, facultyCountOnPage, locale);
        } else {
            faculties = getFacultiesWithLimitOrderTotal(startValue, facultyCountOnPage, locale);
        }
        isFacultiesListEmpty(faculties);
        return faculties;
    }

    public boolean isFacultiesListEmpty(List<Faculty> faculties){
        if (!faculties.isEmpty()){
            return true;
        }else{
            throw new CantGetFacultiesException();
        }
    }

    public List<SubjectExam> getUserAvailableSubjects(HttpServletRequest request, String locale, List<UserResult> results){
         //getting all exams
        List<SubjectExam> exams = facultyDao.getAllSubjectExams(locale);

        //we delete the user's choice so that he cannot re-select the same subject for which he has already contributed points
        List<SubjectExam> usersExams = new ArrayList<SubjectExam>();
        for (int i = 0; i < results.size(); i++) {
            usersExams.add(results.get(i).getSubjectExam());
        }
        exams.removeAll(usersExams);
        return exams;
    }

    public Faculty findFacultyByIdAndLocale(HttpServletRequest request, String locale){
        Faculty faculty = null;
        try{
            String id = request.getParameter("id");
            if (id.isEmpty() || id.equals("")) {
                throw new WrongIdOfFacultyException();
            } else{
                faculty = findFacultyById(id, locale);
                if (faculty == null) {
                    throw new NoSuchFacultyException();
                }
                return faculty;
            }
        }catch (Exception e){
            throw new EmptyParametersException();
        }
    }

    public void addFaculty(HttpServletRequest request){
        try{
            String name = request.getParameter("name");
            String name_ua = request.getParameter("name_ua");
            String description = request.getParameter("description");
            String description_ua = request.getParameter("description_ua");
            String budget_amount = request.getParameter("budget_amount");
            String total_amount = request.getParameter("total_amount");
            if (request.getParameter("name") == null || request.getParameter("name_ua") == null ||
                    request.getParameter("description") == null || request.getParameter("description_ua") == null ||
                    request.getParameter("budget_amount") == null || request.getParameter("total_amount") == null) {
                throw new EmptyParametersException();
            } else {
                addFaculty(name, Integer.parseInt(budget_amount), Integer.parseInt(total_amount), description, name_ua, description_ua);
            }
        }catch (Exception e){
            throw new EmptyParametersException();
        }
    }

    public void deleteFaculty(HttpServletRequest request){
        try{
            String id = request.getParameter("id");
            if (request.getParameter("id") == null) {
                throw new EmptyFacultyIdException();
            } else {
                //DELETE ALL ADMISSIONS AND DEMENDS IF FACULTY DELETED!
                deleteFacultyById(Integer.parseInt(id));
                deleteExamDemndsFacultyById(Integer.parseInt(id));
                deleteAllFacultyAdmissions(Integer.parseInt(id));
            }
        }catch (Exception e){
            throw new EmptyParametersException();
        }
    }

    public List<SubjectExam> getSubjectExamsWithLimitImp(int startValue, int facultyCountOnPage, String locale){
        //getting all exams
        List<SubjectExam> subjectExams = getSubjectExamsWithLimit(startValue, facultyCountOnPage, locale);

        if (!subjectExams.isEmpty()) {
            return subjectExams;
        } else {
            throw new CantGetSubjectExamException();
        }
    }

    public void addSubjectExam(HttpServletRequest request){
        try{
            String name = request.getParameter("name");
            String name_ua = request.getParameter("name_ua");
            String description = request.getParameter("description");
            String description_ua = request.getParameter("description_ua");
            if (request.getParameter("name") == null || request.getParameter("name_ua") == null ||
                    request.getParameter("description") == null || request.getParameter("description_ua") == null) {
                throw new EmptyParametersException();
            } else {
                //add exam
                addSubjectExam(name, name_ua, description, description_ua);
            }
        }catch (Exception e){
            throw new EmptyParametersException();
        }
    }

    public void deleteSubjectExam(HttpServletRequest request){
        try{
            String id = request.getParameter("id");
            if (request.getParameter("id") == null) {
                throw new EmptyParametersException();
            } else {
                //DELETE admissions if subject exam deleted
                deleteSubjectExamById(Integer.parseInt(id));
                userDao.deleteAllResultsBySubjectExamId(Integer.parseInt(id));
                Set<Integer> facultiesId = getFacultyDemends(id);
                for (Integer i : facultiesId) {
                    deleteAllFacultyAdmissions(i);
                }
            }
        }catch (Exception e){
            throw new EmptyParametersException();
        }
    }

    public void addFacultyDemend(HttpServletRequest request, String locale){
        try{
            String examId = request.getParameter("demendSelect");
            String facultyId = request.getParameter("idFaculty");
            if (request.getParameter("idFaculty") == null && request.getParameter("idFaculty") == null) {
                throw new EmptyParametersException();
            } else {
                //add exam demend for faculty
                addExamDemendForFaculty(Integer.parseInt(examId), Integer.parseInt(facultyId));
                //if demends changed - all admissions should be deleted
                deleteAllFacultyAdmissions(Integer.parseInt(facultyId));
                Faculty faculty = findFacultyById(facultyId, locale);
                List<SubjectExam> examList = getFacultyDemendsWithName(facultyId, locale);
                List<SubjectExam> examFullList = getAllSubjectExams(locale);
                examFullList.removeAll(examList);
                request.getSession().setAttribute("id", facultyId);
            }
        }catch (Exception e){
            throw new EmptyParametersException();
        }
    }

    public void deleteFacultyDemend(HttpServletRequest request, String locale){
        try{
            String idFaculty = request.getParameter("idFaculty");
            String idExam = request.getParameter("idExam");
            if (request.getParameter("idFaculty") == null || request.getParameter("idExam") == null) {
                throw new EmptyParametersException();
            } else {

                //getting faculty
                Faculty faculty = findFacultyById(idFaculty, locale);
                //if deleting demend, all admissions will be deleted too
                deleteExamDemendForFaculty(Integer.parseInt(idExam), Integer.parseInt(idFaculty));
                deleteAllFacultyAdmissions(Integer.parseInt(idFaculty));
                //remove values from dropdown list
                List<SubjectExam> examFullList = getAllSubjectExams(locale);
                List<SubjectExam> examList = getFacultyDemendsWithName(idFaculty, locale);
                examFullList.removeAll(examList);
                request.setAttribute("faculty", faculty);
                request.setAttribute("examAvailableList", examFullList);
                request.setAttribute("examList", examList);
            }
        }catch (Exception e){
            throw new EmptyParametersException();
        }
    }

    public void generateFacultiesListForStatement(HttpServletRequest request, String locale){
        try{
            String idFaculty = request.getParameter("id");
            String date = request.getParameter("date");
            if (request.getParameter("id") == null || request.getParameter("date") == null) {
                throw new EmptyParametersException();
            } else {
                //find faculty
                Faculty faculty = findFacultyById(idFaculty, locale);
                //find all admissions for faculty
                List<Admission> admissionList = userDao.getAllUsersAdmissionsForFacultyWithDate(Integer.parseInt(idFaculty), getDateFromString(date));
                ArrayList<UserFinalStatementResult> results = new ArrayList<>();
                //getting final results for users
                for (int i = 0; i < admissionList.size(); i++) {
                    results.add(userDao.getFinalStatementResultForFaculty(admissionList.get(i).getUserId(), faculty.getId(), locale));
                }
                //sort list by total result
                Collections.sort(results, new TotalResultComparator());
                //set values to session
                request.getSession().setAttribute("date", date);
                request.getSession().setAttribute("results", results);
                request.getSession().setAttribute("facultyStatement", faculty);
            }
        }catch (Exception e){
            throw new EmptyParametersException();
        }
    }

    public void generateEarlyStatement(HttpServletRequest request, String locale, CreateStatement statement){
        try{
            //getting values from session
            Faculty faculty = (Faculty) request.getSession().getAttribute("facultyStatement");
            ArrayList<UserFinalStatementResult> results = (ArrayList<UserFinalStatementResult>) request.getSession().getAttribute("results");

            String name = faculty.getName();
            //change name, delete spaces
            name = name.replaceAll(" ", "_");
            //creating name of file
            String fileName = "Generate_Early_Report_" + name + "_" + new java.sql.Date(System.currentTimeMillis()) + "_" + System.currentTimeMillis() + ".pdf";


            //generating pdf statement
            try {
                statement.createPDF("C:/Users/Влад/Desktop/Selectioncommittee/out/statements/" + fileName, faculty, results, locale);
            } catch (Exception e1) {
                LOG.error(e1.getMessage(), e1);
            }
        }catch (Exception e){
            throw new EmptyParametersException();
        }
    }

    public void generateLateStatement(HttpServletRequest request, String locale, CreateStatement statement){
        try{
            Faculty faculty = (Faculty) request.getSession().getAttribute("facultyStatement");

            String name = faculty.getName();
            name = name.replaceAll(" ", "_");
            String fileName = "Generate_Late_Report_" + name + "_" + new java.sql.Date(System.currentTimeMillis()) + "_" + System.currentTimeMillis() + ".pdf";


            String date = (String) request.getSession().getAttribute("date");

            //find all applied admissions for faculty
            List<Admission> admissionList = userDao.getAllAppliedUsersAdmissionsForFacultyWithDate(faculty.getId(), getDateFromString(date));
            ArrayList<UserFinalStatementResult> results = new ArrayList<>();
            //getting final results for applied user statements
            for (int i = 0; i < admissionList.size(); i++) {
                results.add(userDao.getFinalStatementResultForFaculty(admissionList.get(i).getUserId(), faculty.getId(), locale));
            }
            //sort list by total result
            Collections.sort(results, new TotalResultComparator());


            try {
                statement.createPDF("C:/Users/Влад/Desktop/Selectioncommittee/out/statements/" + fileName, faculty, results, locale);
            } catch (Exception e1) {
                LOG.warn(e1.getMessage(), e1);
            }
        }catch (Exception e){
            throw new EmptyParametersException();
        }
    }

    public void confirmUserForStatement(HttpServletRequest request, String locale){
        try{
            String idAdmission = request.getParameter("idAdmission");
            String operation = request.getParameter("operation");
            if (request.getParameter("idAdmission") == null || request.getParameter("operation") == null) {
                throw new EmptyParametersException();
            } else {
                if (operation.equals("approve")) {
                    approveAdmission(Integer.parseInt(idAdmission));
                } else {
                    disapproveAdmission(Integer.parseInt(idAdmission));
                }

                String date = (String) request.getSession().getAttribute("date");

                Faculty faculty = (Faculty) request.getSession().getAttribute("facultyStatement");
                //find faculty
                //find all admissions for faculty
                List<Admission> admissionList = userDao.getAllUsersAdmissionsForFacultyWithDate(faculty.getId(), getDateFromString(date));
                ArrayList<UserFinalStatementResult> results = new ArrayList<>();
                //getting final results for users
                for (int i = 0; i < admissionList.size(); i++) {
                    results.add(userDao.getFinalStatementResultForFaculty(admissionList.get(i).getUserId(), faculty.getId(), locale));
                }
                //sort list by total result
                Collections.sort(results, new TotalResultComparator());
                //set values to session
                request.getSession().setAttribute("date", date);
                request.getSession().setAttribute("results", results);
                request.getSession().setAttribute("facultyStatement", faculty);
            }
        }catch (Exception e){
            throw new EmptyParametersException();
        }
    }

    public void deleteStatement(HttpServletRequest request, StatementWorker worker){
        try{
            //get name from request
            String filename = request.getParameter("name");
            if (request.getParameter("name") == null) {
                throw new EmptyParametersException();
            } else {
                //delete file
                worker.deleteStatement(PathApp.STATEMENTS_FOLDER + "/" + filename);
            }
        } catch (Exception e){
            throw new EmptyParametersException();
        }
    }

}
