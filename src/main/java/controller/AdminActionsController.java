package controller;


import exception.*;
import model.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import path.PathApp;
import pdf.CreateStatement;
import pdf.StatementWorker;
import service.FacultyService;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AdminActionsController {
    private static final Logger LOG = LogManager.getLogger(AdminActionsController.class);

    private UserService userService;
    private FacultyService facultyService;
    private CreateStatement statement;
    private StatementWorker worker;


    @Autowired
    public AdminActionsController(UserService userService, FacultyService facultyService, CreateStatement statement, StatementWorker worker) {
        this.userService = userService;
        this.facultyService = facultyService;
        this.statement = statement;
        this.worker = worker;
    }


    @RequestMapping(value = "/app/admin/home")
    public ModelAndView main() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("app/admin/admin_home");
        return modelAndView;
    }

    @RequestMapping(value = "/app/admin/all_faculties")
    public ModelAndView showAllFacultiesAdmin(HttpServletRequest request) throws CantGetFacultiesException {
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


        int rows = facultyService.getCountOfFaculties();
        int nOfPages = rows / facultyCountOnPage;

        if (nOfPages % facultyCountOnPage > 0) {
            nOfPages++;
        }
        if (rows % facultyCountOnPage == 0) {
            nOfPages--;
        }

        List<Faculty> faculties = facultyService.getFacultiesWithSorting("sortAZ", startValue, facultyCountOnPage, locale);

        request.setAttribute("facultiesList", faculties);
        request.setAttribute("noOfPages", nOfPages);
        request.setAttribute("pageFaculty", pageFaculty);
        modelAndView.setViewName("app/admin/all_faculties");
        return modelAndView;
    }

    @RequestMapping(value = "/app/admin/add_faculty")
    public ModelAndView addFaculty(HttpServletRequest request) throws EmptyParametersException {
        ModelAndView modelAndView = new ModelAndView();
        //getting locale
        String locale = (String) request.getSession().getAttribute("language");
        //getting locale for errors
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale(locale));
        //add new faculty
        facultyService.addFaculty(request);
        modelAndView.setViewName("redirect:/app/admin/all_faculties");
        return modelAndView;
    }

    @RequestMapping(value = "/app/admin/delete_faculty")
    public ModelAndView deleteFaculty(HttpServletRequest request) throws EmptyFacultyIdException {
        ModelAndView modelAndView = new ModelAndView();
        facultyService.deleteFaculty(request);
        modelAndView.setViewName("redirect:/app/admin/all_faculties");
        return modelAndView;
    }

    @RequestMapping(value = "/app/admin/subject_exams")
    public ModelAndView showListOfSubjects(HttpServletRequest request) throws CantGetSubjectExamException {
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

        int rows = facultyService.getTotalCountOfSubjectExams();
        int nOfPages = rows / facultyCountOnPage;

        if (nOfPages % facultyCountOnPage > 0) {
            nOfPages++;
        }
        if (rows % facultyCountOnPage == 0) {
            nOfPages--;
        }

        List<SubjectExam> subjectExams = facultyService.getSubjectExamsWithLimitImp(startValue, facultyCountOnPage, locale);

        request.setAttribute("subjectExams", subjectExams);
        request.setAttribute("noOfPages", nOfPages);
        request.setAttribute("pageSubjectExam", pageSubjectExam);
        modelAndView.setViewName("app/admin/subject_exams");
        return modelAndView;

    }

    @RequestMapping(value = "/app/admin/add_subject_exam")
    public ModelAndView addSubject(HttpServletRequest request) throws EmptyParametersException {
        ModelAndView modelAndView = new ModelAndView();
        facultyService.addSubjectExam(request);
        modelAndView.setViewName("redirect:/app/admin/subject_exams");
        return modelAndView;
    }

    @RequestMapping(value = "/app/admin/delete_subject_exam")
    public ModelAndView deleteSubject(HttpServletRequest request) throws EmptyParametersException {
        ModelAndView modelAndView = new ModelAndView();
        facultyService.deleteSubjectExam(request);
        modelAndView.setViewName("redirect:/app/admin/subject_exams");
        return modelAndView;
    }

    @RequestMapping(value = "/app/admin/users")
    public ModelAndView showAllUsers(HttpServletRequest request) throws CantGetUsersException {
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

        int rows = userService.getTotalCountOfUsers();
        int nOfPages = rows / facultyCountOnPage;

        if (nOfPages % facultyCountOnPage > 0) {
            nOfPages++;
        }
        if (rows % facultyCountOnPage == 0) {
            nOfPages--;
        }

        List<User> users = userService.findAllUsersWithLimitImp(startValue, facultyCountOnPage);
        request.setAttribute("usersList", users);
        request.setAttribute("noOfPages", nOfPages);
        request.setAttribute("pageFaculty", pageFaculty);
        modelAndView.setViewName("app/admin/all_users");
        return modelAndView;

    }

    @RequestMapping(value = "/app/admin/block_user")
    public ModelAndView blockUser(HttpServletRequest request) throws EmptyParametersException, WrongOperationException {
        ModelAndView modelAndView = new ModelAndView();
        userService.blockUnblockUser(request);
        modelAndView.setViewName("redirect:/app/admin/users");
        return modelAndView;

    }

    @RequestMapping(value = "/app/admin/user_set_role")
    public ModelAndView changeUserRole(HttpServletRequest request) throws EmptyParametersException, WrongOperationException {
        ModelAndView modelAndView = new ModelAndView();
        userService.setUnsetUserRole(request);
        modelAndView.setViewName("redirect:/app/admin/users");
        return modelAndView;
    }

    @RequestMapping(value = "/app/admin/faculty_admissions")
    public ModelAndView facultyDemends(HttpServletRequest request) throws EmptyParametersException {
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
            List<SubjectExam> examList = facultyService.getFacultyDemendsWithName(idFaculty, locale);
            List<SubjectExam> examFullList = facultyService.getAllSubjectExams(locale);
            Faculty faculty = facultyService.findFacultyById(idFaculty, locale);
            examFullList.removeAll(examList);
            request.getSession().removeAttribute("id");

            request.setAttribute("faculty", faculty);
            request.setAttribute("examList", examList);
            request.setAttribute("examAvailableList", examFullList);

            modelAndView.setViewName("app/admin/faculty_demends");
            return modelAndView;
        } else {
            throw new EmptyParametersException();
        }
    }

    @RequestMapping(value = "/app/admin/faculty_demend")
    public ModelAndView addFacultyDemend(HttpServletRequest request) throws EmptyParametersException {

        ModelAndView modelAndView = new ModelAndView();
        //getting locale
        String locale = (String) request.getSession().getAttribute("language");
        //getting locale for errors

        facultyService.addFacultyDemend(request, locale);
        modelAndView.setViewName("redirect:/app/admin/faculty_admissions");
        return modelAndView;
    }

    @RequestMapping(value = "/app/admin/delete_demend")
    public ModelAndView deleteFacultyDemend(HttpServletRequest request) throws EmptyParametersException {
        ModelAndView modelAndView = new ModelAndView();
        //getting locale
        String locale = (String) request.getSession().getAttribute("language");
        //getting locale for errors
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale(locale));
        facultyService.deleteFacultyDemend(request, locale);
        modelAndView.setViewName("app/admin/faculty_demends");
        return modelAndView;
    }


    @RequestMapping(value = "/app/admin/generation_statements")
    public ModelAndView showListOfFacultiesStatements(HttpServletRequest request) throws CantGetFacultiesException {
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

        int rows = facultyService.getCountOfFaculties();
        int nOfPages = rows / facultyCountOnPage;

        if (nOfPages % facultyCountOnPage > 0) {
            nOfPages++;
        }
        if (rows % facultyCountOnPage == 0) {
            nOfPages--;
        }

        List<Faculty> faculties = facultyService.getFacultiesWithSorting("sortAZ", startValue, facultyCountOnPage, locale);

        request.setAttribute("facultiesList", faculties);
        request.setAttribute("noOfPages", nOfPages);
        request.setAttribute("pageFaculty", pageFaculty);
        modelAndView.setViewName("app/admin/faculties_list_statements");
        return modelAndView;
    }

    @RequestMapping(value = "/app/admin/generate_statement")
    public ModelAndView generatePageStatement(HttpServletRequest request) throws EmptyParametersException {
        ModelAndView modelAndView = new ModelAndView();

        //getting locale
        String locale = (String) request.getSession().getAttribute("language");
        //getting locale for errors
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale(locale));

        facultyService.generateFacultiesListForStatement(request, locale);
        modelAndView.setViewName("app/admin/statement");
        return modelAndView;
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
        facultyService.generateEarlyStatement(request, locale, statement);

        modelAndView.setViewName("app/admin/admin_home");
        return modelAndView;
    }

    @RequestMapping(value = "/app/admin/generate_late_statement")
    public ModelAndView generateLateStatement(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String locale = (String) request.getSession().getAttribute("language");
        facultyService.generateLateStatement(request, locale, statement);
        modelAndView.setViewName("app/admin/admin_home");
        return modelAndView;
    }

    @RequestMapping(value = "/app/admin/confirm_user_for_statement")
    public ModelAndView confirmUserForStatement(HttpServletRequest request) throws EmptyParametersException {
        ModelAndView modelAndView = new ModelAndView();
        //getting locale
        String locale = (String) request.getSession().getAttribute("language");
        //getting locale for errors
        facultyService.confirmUserForStatement(request, locale);
        modelAndView.setViewName("app/admin/statement");
        return modelAndView;
    }

    @RequestMapping(value = "/app/admin/delete_statement")
    public ModelAndView deleteStatement(HttpServletRequest request) throws IOException, EmptyParametersException {
        ModelAndView modelAndView = new ModelAndView();

        //getting locale

        String locale = (String) request.getSession().getAttribute("language");
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale(locale));

        facultyService.deleteStatement(request, worker);
        modelAndView.setViewName("app/all_statements");
        return modelAndView;

    }
}
