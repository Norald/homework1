package controller;

import db.dao.FacultyDao;
import db.dao.UserDao;
import model.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import path.PathApp;
import pdf.CreateStatement;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AdminActionsController {
    private UserDao userDao;
    private FacultyDao facultyDao;
    private CreateStatement statement;
    private static final Logger LOG = LogManager.getLogger(AdminActionsController.class.getName());


    @Autowired
    public AdminActionsController(UserDao userDao, FacultyDao facultyDao, CreateStatement statement) {
        this.userDao = userDao;
        this.facultyDao = facultyDao;
        this.statement = statement;
    }


    @RequestMapping(value = "/app/admin/home")
    public ModelAndView main() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("app/admin/admin_home");
        return modelAndView;
    }

    @RequestMapping(value = "/app/admin/all_faculties")
    public ModelAndView showAllFacultiesAdmin(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String locale = (String) request.getSession().getAttribute("language");
        //getting locale for errors
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale(locale));
        //realisation of pagination
        int pageFaculty;
        int facultyCountOnPage = 5;
        int startValue;
        if (!request.getParameterMap().containsKey("page")) {
            pageFaculty = 1;
            startValue = 0;
        } else {
            pageFaculty = Integer.parseInt(request.getParameter("page"));
            startValue = (pageFaculty - 1) * facultyCountOnPage;
        }


        int rows = facultyDao.getTotalCountOfFaculty();
        int nOfPages = rows / facultyCountOnPage;

        if (nOfPages % facultyCountOnPage > 0) {
            nOfPages++;
        }
        if (rows % facultyCountOnPage == 0) {
            nOfPages--;
        }

        List<Faculty> faculties = null;
        faculties = facultyDao.getFacultiesWithLimitOrderAZ(startValue, facultyCountOnPage, locale);

        if (!faculties.isEmpty()) {
            //attributes for pagination
            request.setAttribute("facultiesList", faculties);
            request.setAttribute("noOfPages", nOfPages);
            request.setAttribute("pageFaculty", pageFaculty);
            modelAndView.setViewName("app/admin/all_faculties");
            return modelAndView;
        } else {
            LOG.warn("Cant get faculties");
            request.setAttribute("error", rb.getString("error.cant.get.faculties"));
            modelAndView.setViewName("error");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/app/admin/add_faculty")
    public ModelAndView addFaculty(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        //getting locale
        String locale = (String) request.getSession().getAttribute("language");
        //getting locale for errors
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale(locale));
        if (request.getParameter("name") == null || request.getParameter("name_ua") == null ||
                request.getParameter("description") == null || request.getParameter("description_ua") == null ||
                request.getParameter("budget_amount") == null || request.getParameter("total_amount") == null) {
            LOG.warn("Empty parameter");
            request.setAttribute("error", rb.getString("error.empty.parameter"));
            modelAndView.setViewName("error");
            return modelAndView;
        } else {
            String name = request.getParameter("name");
            String name_ua = request.getParameter("name_ua");
            String description = request.getParameter("description");
            String description_ua = request.getParameter("description_ua");
            String budget_amount = request.getParameter("budget_amount");
            String total_amount = request.getParameter("total_amount");
            //add new faculty
            facultyDao.addFaculty(name, Integer.parseInt(budget_amount), Integer.parseInt(total_amount), description, name_ua, description_ua);
            modelAndView.setViewName("redirect:/app/admin/all_faculties");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/app/admin/delete_faculty")
    public ModelAndView deleteFaculty(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String locale = (String) request.getSession().getAttribute("language");
        //getting locale for errors
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale(locale));
        if (request.getParameter("id") == null) {
            LOG.warn("Empty id");
            request.setAttribute("error", rb.getString("error.empty.id"));
            modelAndView.setViewName("error");
            return modelAndView;
        } else {
            String id = request.getParameter("id");
            //DELETE ALL ADMISSIONS AND DEMENDS IF FACULTY DELETED!
            facultyDao.deleteFacultyById(Integer.parseInt(id));
            facultyDao.deleteExamDemndsFacultyFacultyById(Integer.parseInt(id));
            facultyDao.deleteAllFacultyAdmissions(Integer.parseInt(id));
            modelAndView.setViewName("redirect:/app/admin/all_faculties");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/app/admin/subject_exams")
    public ModelAndView showListOfSubjects(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        //getting locale
        String locale = (String) request.getSession().getAttribute("language");
        //getting locale for errors
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale(locale));

        //realisation of pagination
        int pageSubjectExam;
        int facultyCountOnPage = 5;
        int startValue;
        if (!request.getParameterMap().containsKey("page")) {
            pageSubjectExam = 1;
            startValue = 0;
        } else {
            pageSubjectExam = Integer.parseInt(request.getParameter("page"));
            startValue = (pageSubjectExam - 1) * facultyCountOnPage;
        }

        int rows = facultyDao.getTotalCountOfSubjectExams();
        int nOfPages = rows / facultyCountOnPage;

        if (nOfPages % facultyCountOnPage > 0) {
            nOfPages++;
        }
        if (rows % facultyCountOnPage == 0) {
            nOfPages--;
        }

        List<SubjectExam> subjectExams = null;
        subjectExams = facultyDao.getSubjectExamsWithLimit(startValue, facultyCountOnPage, locale);

        if (!subjectExams.isEmpty()) {
            request.setAttribute("subjectExams", subjectExams);
            request.setAttribute("noOfPages", nOfPages);
            request.setAttribute("pageSubjectExam", pageSubjectExam);
            modelAndView.setViewName("app/admin/subject_exams");
            return modelAndView;
        } else {
            LOG.warn("Cant get subject exam");
            request.setAttribute("error", rb.getString("error.cant.get.subject.exam"));
            modelAndView.setViewName("error");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/app/admin/add_subject_exam")
    public ModelAndView addSubject(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        //getting locale
        String locale = (String) request.getSession().getAttribute("language");
        //getting locale for errors
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale(locale));
        if (request.getParameter("name") == null || request.getParameter("name_ua") == null ||
                request.getParameter("description") == null || request.getParameter("description_ua") == null) {
            LOG.warn("Empty parameter");
            request.setAttribute("error", rb.getString("error.empty.parameter"));
            modelAndView.setViewName("error");
            return modelAndView;
        } else {
            String name = request.getParameter("name");
            String name_ua = request.getParameter("name_ua");
            String description = request.getParameter("description");
            String description_ua = request.getParameter("description_ua");
            //add exam
            facultyDao.addSubjectExam(name, name_ua, description, description_ua);
            modelAndView.setViewName("redirect:/app/admin/subject_exams");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/app/admin/delete_subject_exam")
    public ModelAndView deleteSubject(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String locale = (String) request.getSession().getAttribute("language");
        //getting locale for errors
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale(locale));
        if (request.getParameter("id") == null) {
            LOG.warn("Empty id");
            request.setAttribute("error", rb.getString("error.empty.id"));
            modelAndView.setViewName("error");
            return modelAndView;
        } else {
            String id = request.getParameter("id");
            FacultyDao facultyDao = new FacultyDao();
            //DELETE admissions if subject exam deleted
            facultyDao.deleteSubjectExamById(Integer.parseInt(id));
            UserDao userDao = new UserDao();
            userDao.deleteAllResultsBySubjectExamId(Integer.parseInt(id));
            Set<Integer> facultiesId = facultyDao.getFacultyDemends(id);
            for (Integer i : facultiesId) {
                facultyDao.deleteAllFacultyAdmissions(i);
            }
            modelAndView.setViewName("redirect:/app/admin/subject_exams");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/app/admin/users")
    public ModelAndView showAllUsers(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        //getting locale
        String locale = (String) request.getSession().getAttribute("language");
        //getting locale for errors
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale(locale));

        //realistaion of pagionation
        int pageFaculty;
        int facultyCountOnPage = 10;
        int startValue;
        if (!request.getParameterMap().containsKey("page")) {
            pageFaculty = 1;
            startValue = 0;
        } else {
            pageFaculty = Integer.parseInt(request.getParameter("page"));
            startValue = (pageFaculty - 1) * facultyCountOnPage;
        }

        int rows = userDao.getTotalCountOfUsers();
        int nOfPages = rows / facultyCountOnPage;

        if (nOfPages % facultyCountOnPage > 0) {
            nOfPages++;
        }
        if (rows % facultyCountOnPage == 0) {
            nOfPages--;
        }

        List<User> users = null;
        users = userDao.findAllUsersWithLimit(startValue, facultyCountOnPage);

        //System.out.println(Arrays.asList(faculties));
        if (!users.isEmpty()) {
            request.setAttribute("usersList", users);
            request.setAttribute("noOfPages", nOfPages);
            request.setAttribute("pageFaculty", pageFaculty);
            modelAndView.setViewName("app/admin/all_users");
            return modelAndView;
        } else {
            LOG.warn("Cant get faculties");
            request.setAttribute("error", rb.getString("error.cant.get.users"));
            modelAndView.setViewName("error");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/app/admin/block_user")
    public ModelAndView blockUser(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        //getting locale
        String locale = (String) request.getSession().getAttribute("language");
        //getting locale for errors
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale(locale));
        if (request.getParameter("operation") == null || request.getParameter("id") == null) {
            LOG.warn("Error operation or user id null");
            request.setAttribute("error", rb.getString("error.operation.or.user.id.null"));
            modelAndView.setViewName("error");
            return modelAndView;
        } else if (request.getParameter("operation").equals("block") || request.getParameter("operation").equals("unblock")) {
            String operation = request.getParameter("operation");
            String userId = request.getParameter("id");
            UserDao userDao = new UserDao();
            //if operation 'block' - then block user
            if (operation.equals("block")) {
                userDao.blockUserById(Integer.parseInt(userId));
                modelAndView.setViewName("redirect:/app/admin/users");
                return modelAndView;

            } else {//else - unblock user
                userDao.unblockUserById(Integer.parseInt(userId));
                modelAndView.setViewName("redirect:/app/admin/users");
                return modelAndView;
            }
        } else {
            LOG.warn("Wrong operation");
            request.setAttribute("error", "Wrong operation");
            modelAndView.setViewName("error");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/app/admin/user_set_role")
    public ModelAndView changeUserRole(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        //getting locale
        String locale = (String) request.getSession().getAttribute("language");
        //getting locale for errors
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale(locale));
        //check if parameters not null
        if (request.getParameter("operation") == null || request.getParameter("id") == null) {
            LOG.warn("Error operation or user id null");
            request.setAttribute("error", rb.getString("error.operation.or.user.id.null"));
            modelAndView.setViewName("error");
            return modelAndView;
        } else if (request.getParameter("operation").equals("makeUser") || request.getParameter("operation").equals("makeAdmin")) {
            String operation = request.getParameter("operation");
            String userId = request.getParameter("id");
            UserDao userDao = new UserDao();
            //if operation - makeAdmin, set to user Admin role
            if (operation.equals("makeAdmin")) {
                userDao.makeUserAdmin(Integer.parseInt(userId));
                modelAndView.setViewName("redirect:/app/admin/users");
                return modelAndView;

            } else {//set to admin User role
                userDao.makeAdminUser(Integer.parseInt(userId));
                modelAndView.setViewName("redirect:/app/admin/users");
                return modelAndView;
            }
        } else {
            LOG.warn("Wrong operation");
            request.setAttribute("error", rb.getString("error.wrong.operation"));
            modelAndView.setViewName("error");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/app/admin/faculty_admissions")
    public ModelAndView facultyDemends(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        //getting locale
        String locale = (String) request.getSession().getAttribute("language");
        //getting locale for errors
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale(locale));


        if (request.getSession().getAttribute("id") != null || request.getParameter("id") != null) {
            String idFaculty;
            if (request.getSession().getAttribute("id") != null) {
                idFaculty = (String) (request.getSession().getAttribute("id"));
            } else {
                idFaculty = request.getParameter("id");
            }
            List<SubjectExam> examList = facultyDao.getFacultyDemendsWithName(idFaculty, locale);
            List<SubjectExam> examFullList = facultyDao.getAllSubjectExams(locale);
            Faculty faculty = facultyDao.findFacultyById(idFaculty, locale);
            examFullList.removeAll(examList);
            request.getSession().removeAttribute("id");

            request.setAttribute("faculty", faculty);
            request.setAttribute("examList", examList);
            request.setAttribute("examAvailableList", examFullList);

            modelAndView.setViewName("app/admin/faculty_demends");
            return modelAndView;
        } else {
            LOG.warn("Empty id");
            request.setAttribute("error", rb.getString("error.empty.id"));
            modelAndView.setViewName("error");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/app/admin/faculty_demend")
    public ModelAndView addFacultyDemend(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        //getting locale
        String locale = (String) request.getSession().getAttribute("language");
        //getting locale for errors
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale(locale));

        if (request.getParameter("idFaculty") == null && request.getParameter("idFaculty") == null) {
            LOG.warn("Empty faculty id or exam id");
            request.setAttribute("error", rb.getString("error.empty.faculty.or.exam.id"));
            modelAndView.setViewName("error");
            return modelAndView;
        } else {
            String examId = request.getParameter("demendSelect");
            String facultyId = request.getParameter("idFaculty");
            //add exam demend for faculty
            facultyDao.addExamDemendForFaculty(Integer.parseInt(examId), Integer.parseInt(facultyId));
            //if demends changed - all admissions should be deleted
            facultyDao.deleteAllFacultyAdmissions(Integer.parseInt(facultyId));
            Faculty faculty = facultyDao.findFacultyById(facultyId, locale);
            List<SubjectExam> examList = facultyDao.getFacultyDemendsWithName(facultyId, locale);
            List<SubjectExam> examFullList = facultyDao.getAllSubjectExams(locale);
            examFullList.removeAll(examList);
            System.out.println(facultyId);
            request.getSession().setAttribute("id", facultyId);
            modelAndView.setViewName("redirect:/app/admin/faculty_admissions");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/app/admin/delete_demend")
    public ModelAndView deleteFacultyDemend(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        //getting locale
        String locale = (String) request.getSession().getAttribute("language");
        //getting locale for errors
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale(locale));


        if (request.getParameter("idFaculty") == null || request.getParameter("idExam") == null) {
            LOG.warn("Empty faculty id or exam id");
            request.setAttribute("error", rb.getString("error.empty.faculty.or.exam.id"));
            modelAndView.setViewName("error");
            return modelAndView;
        } else {
            String idFaculty = request.getParameter("idFaculty");
            String idExam = request.getParameter("idExam");

            //getting faculty
            Faculty faculty = facultyDao.findFacultyById(idFaculty, locale);
            //if deleting demend, all admissions will be deleted too
            facultyDao.deleteExamDemendForFaculty(Integer.parseInt(idExam), Integer.parseInt(idFaculty));
            facultyDao.deleteAllFacultyAdmissions(Integer.parseInt(idFaculty));
            //remove values from dropdown list
            List<SubjectExam> examFullList = facultyDao.getAllSubjectExams(locale);
            List<SubjectExam> examList = facultyDao.getFacultyDemendsWithName(idFaculty, locale);
            examFullList.removeAll(examList);
            request.setAttribute("faculty", faculty);
            request.setAttribute("examAvailableList", examFullList);
            request.setAttribute("examList", examList);

            modelAndView.setViewName("app/admin/faculty_demends");
            return modelAndView;
        }
    }


    @RequestMapping(value = "/app/admin/generation_statements")
    public ModelAndView showListOfFacultiesStatements(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String locale = (String) request.getSession().getAttribute("language");
        //getting locale for errors
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale(locale));

        //realisation of pagination
        int pageFaculty;
        int facultyCountOnPage = 5;
        int startValue;
        if (!request.getParameterMap().containsKey("page")) {
            pageFaculty = 1;
            startValue = 0;
        } else {
            pageFaculty = Integer.parseInt(request.getParameter("page"));
            startValue = (pageFaculty - 1) * facultyCountOnPage;
        }

        int rows = facultyDao.getTotalCountOfFaculty();
        int nOfPages = rows / facultyCountOnPage;

        if (nOfPages % facultyCountOnPage > 0) {
            nOfPages++;
        }
        if (rows % facultyCountOnPage == 0) {
            nOfPages--;
        }

        List<Faculty> faculties = null;
        faculties = facultyDao.getFacultiesWithLimitOrderAZ(startValue, facultyCountOnPage, locale);

        if (!faculties.isEmpty()) {
            request.setAttribute("facultiesList", faculties);
            request.setAttribute("noOfPages", nOfPages);
            request.setAttribute("pageFaculty", pageFaculty);
            modelAndView.setViewName("app/admin/faculties_list_statements");
            return modelAndView;
        } else {
            LOG.warn("Cant get faculties");
            request.setAttribute("error", rb.getString("error.cant.get.faculties"));
            modelAndView.setViewName("error");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/app/admin/generate_statement")
    public ModelAndView generatePageStatement(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        //getting locale
        String locale = (String) request.getSession().getAttribute("language");
        //getting locale for errors
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale(locale));

        if (request.getParameter("id") == null || request.getParameter("date") == null) {
            LOG.warn("Error id or date null");
            request.setAttribute("error", rb.getString("error.id.or.date.null"));
            modelAndView.setViewName("error");
            return modelAndView;
        } else {
            String idFaculty = request.getParameter("id");
            String date = request.getParameter("date");


            //find faculty
            Faculty faculty = facultyDao.findFacultyById(idFaculty, locale);
            UserDao userDao = new UserDao();
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
            modelAndView.setViewName("app/admin/statement");
            return modelAndView;
        }
    }

    public static java.sql.Date getDateFromString(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsed = null;
        try {
            parsed = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Date date = new java.sql.Date(parsed.getTime());
        return date;
    }

    @RequestMapping(value = "/app/admin/generate_early_statement")
    public ModelAndView generateEarlyStatement(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        String locale = (String) request.getSession().getAttribute("language");
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

        modelAndView.setViewName("app/admin/admin_home");
        return modelAndView;
    }

    @RequestMapping(value = "/app/admin/generate_late_statement")
    public ModelAndView generateLateStatement(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        String locale = (String) request.getSession().getAttribute("language");
        Faculty faculty = (Faculty) request.getSession().getAttribute("facultyStatement");

        String name = faculty.getName();
        name = name.replaceAll(" ", "_");
        String fileName = "Generate_Late_Report_" + name + "_" + new java.sql.Date(System.currentTimeMillis()) + "_" + System.currentTimeMillis() + ".pdf";


        String date = (String) request.getSession().getAttribute("date");

        UserDao userDao = new UserDao();
        //find all applied admissions for faculty
        List<Admission> admissionList = userDao.getAllAppliedUsersAdmissionsForFacultyWithDate(faculty.getId(), getDateFromString(date));
        ArrayList<UserFinalStatementResult> results = new ArrayList<>();
        //getting final results for applied user statements
        for (int i = 0; i < admissionList.size(); i++) {
            results.add(userDao.getLateFinalStatementResultForFaculty(admissionList.get(i).getUserId(), faculty.getId(), locale));
        }
        //sort list by total result
        Collections.sort(results, new TotalResultComparator());


        try {
            statement.createPDF("C:/Users/Влад/Desktop/Selectioncommittee/out/statements/" + fileName, faculty, results, locale);
        } catch (Exception e1) {
            LOG.warn(e1.getMessage(), e1);
        }

        modelAndView.setViewName("app/admin/admin_home");
        return modelAndView;
    }

    @RequestMapping(value = "/app/admin/confirm_user_for_statement")
    public ModelAndView confirmUserForStatement(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        //getting locale
        String locale = (String) request.getSession().getAttribute("language");
        //getting locale for errors
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale(locale));
        if (request.getParameter("idAdmission") == null || request.getParameter("operation") == null) {
            LOG.warn("Empty value");
            request.setAttribute("error", rb.getString("error.empty.parameter"));
            modelAndView.setViewName("error");
            return modelAndView;
        } else {
            String idAdmission = request.getParameter("idAdmission");
            String operation = request.getParameter("operation");
            if (operation.equals("approve")) {
                facultyDao.approveAdmission(Integer.parseInt(idAdmission));
            } else {
                facultyDao.disapproveAdmission(Integer.parseInt(idAdmission));
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
            modelAndView.setViewName("app/admin/statement");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/app/admin/delete_statement")
    public ModelAndView deleteStatement(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        //getting locale

        String locale = (String) request.getSession().getAttribute("language");
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale(locale));

        if (request.getParameter("name") == null) {
            LOG.warn("Empty parameter");
            request.setAttribute("error", rb.getString("error.empty.parameter"));
            modelAndView.setViewName("error");
            return modelAndView;
        } else {
            //get name from request
            String filename = request.getParameter("name");

            //delete file
            File file = new File(PathApp.STATEMENTS_FOLDER + "/" + filename);
            file.delete();
            modelAndView.setViewName("app/all_statements");
            return modelAndView;
        }
    }
}
