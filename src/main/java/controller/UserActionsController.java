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
import pdf.StatementWorker;
import service.FacultyService;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.util.*;

@Controller
public class UserActionsController {
    private static final Logger LOG = LogManager.getLogger(UserActionsController.class.getName());

    private UserService userService;
    private FacultyService facultyService;
    private StatementWorker statementWorker;

    @Autowired
    public UserActionsController(UserService userService, FacultyService facultyService, StatementWorker statementWorker) {
        this.userService = userService;
        this.facultyService = facultyService;
        this.statementWorker = statementWorker;
    }

    @RequestMapping(value = "/app/faculties")
    public ModelAndView showListOfFaculties(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        String sort;

        //Logic of working with sorting
        if (request.getSession().getAttribute("sort") == null) {
            //default sorting - by alphabet
            sort = "sortAZ";
        } else if (request.getParameterMap().containsKey("sort")) {
            sort = request.getParameter("sort");
        } else {
            sort = (String) request.getSession().getAttribute("sort");
        }


        //getting locale
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

//        FacultyDao facultyDao = new FacultyDao();
        int rows = facultyService.getCountOfFaculties();

        int nOfPages = rows / facultyCountOnPage;

        if (nOfPages % facultyCountOnPage > 0) {

            nOfPages++;
        }
        if (rows % facultyCountOnPage == 0) {
            nOfPages--;
        }

        List<Faculty> faculties = null;
        if (sort.equals("sortAZ")) {
            faculties = facultyService.getFacultiesWithLimitOrderAZ(startValue, facultyCountOnPage, locale);
        } else if (sort.equals("sortZA")) {
            faculties = facultyService.getFacultiesWithLimitOrderZA(startValue, facultyCountOnPage, locale);
        } else if (sort.equals("sortBudget")) {
            faculties = facultyService.getFacultiesWithLimitOrderBugdet(startValue, facultyCountOnPage, locale);
        } else {
            faculties = facultyService.getFacultiesWithLimitOrderTotal(startValue, facultyCountOnPage, locale);
        }
        if (!faculties.isEmpty()) {
            LOG.info("Getting faculties successful");
            request.setAttribute("facultiesList", faculties);
            request.setAttribute("noOfPages", nOfPages);
            request.setAttribute("pageFaculty", pageFaculty);
            //update sort attribute in session
            request.getSession().removeAttribute("sort");
            request.getSession().setAttribute("sort", sort);
            modelAndView.setViewName("app/faculties");
            return modelAndView;
        } else {
            LOG.warn("Can`t get faculties");
            request.setAttribute("error", rb.getString("error.cant.get.faculties"));
            modelAndView.setViewName("error");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/app/add_mark")
    public ModelAndView addMark(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        //getting locale
        String locale = (String) request.getSession().getAttribute("language");
        //getting locale for errors
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale(locale));
        if (request.getParameter("mark") != null || request.getParameter("marksSelect") != null) {
            String mark = request.getParameter("mark");
            String examId = request.getParameter("marksSelect");
            String userEmail = (String) request.getSession().getAttribute("email");


            //find user
            User user = userService.findUser(userEmail);

            //adding mark for user
            userService.addUserMark(user.getId(), Integer.parseInt(examId), Integer.parseInt(mark));
            modelAndView.setViewName("redirect:/app/marks");
//            response.sendRedirect("/app/marks");
            return modelAndView;


        } else {
            LOG.warn("Empty parameters");
            request.setAttribute("error", rb.getString("error.registration.empty.parameters"));
            modelAndView.setViewName("error");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/app/marks")
    public ModelAndView showListOfMarks(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        //getting locale
        String locale = (String) request.getSession().getAttribute("language");

        //getting user exams
        List<UserResult> results = userService.findUserResults((String) request.getSession().getAttribute("email"), locale);
        //getting all exams
        List<SubjectExam> exams = facultyService.getAllSubjectExamsForFaculty(locale);


        //we delete the user's choice so that he cannot re-select the same subject for which he has already contributed points
        List<SubjectExam> usersExams = new ArrayList<SubjectExam>();
        for (int i = 0; i < results.size(); i++) {
            usersExams.add(results.get(i).getSubjectExam());
        }
        exams.removeAll(usersExams);

        request.setAttribute("results", results);
        request.setAttribute("exams", exams);
        modelAndView.setViewName("app/marks");
        return modelAndView;
    }

    @RequestMapping(value = "/app/mark_del")
    public ModelAndView deleteMark(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        String userid = request.getParameter("subjectid");
        String locale = (String) request.getSession().getAttribute("language");
        //getting locale for errors
        Locale current = new Locale(locale);
        //getting resource bundle
        ResourceBundle rb = ResourceBundle.getBundle("resource", current);

        //check if empty
        if (userid.isEmpty() || userid.equals("")) {
            LOG.warn("Wrong id or subject exam");
            request.setAttribute("error", rb.getString("error.wrong.id.or.subject.exam"));
            modelAndView.setViewName("error");
            return modelAndView;
        } else {
            String userEmail = (String) request.getSession().getAttribute("email");
            User user = userService.findUser(userEmail);

            userService.removeUserResult(user.getId(), Integer.parseInt(userid));

            //if user delete his marks - all his admissions will be deleted
            userService.removeUserAdmissions(user.getId());
            request.getSession().removeAttribute("admissions map");
            modelAndView.setViewName("redirect:/app/marks");
            return modelAndView;

        }
    }

    @RequestMapping(value = "/app/admissions")
    public ModelAndView showListOfAdmissions(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        //getting locale
        String locale = (String) request.getSession().getAttribute("language");


        User user = userService.findUser((String) request.getSession().getAttribute("email"));

        //getting all user admissions
        Map<String, Date> mapOfAdmissions = userService.findUserAdmissions(user, locale);

        HttpSession session = request.getSession();

        session.setAttribute("admissions map", mapOfAdmissions);

        modelAndView.setViewName("app/admissions");
        return modelAndView;
    }

    @RequestMapping(value = "/app/admission_del")
    public ModelAndView deleteAdmission(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String faculty_Name = request.getParameter("faculty_name");
        //getting locale
        String locale = (String) request.getSession().getAttribute("language");
        //getting locale for errors
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale(locale));

        //check if faculty name is empty
        if (!faculty_Name.isEmpty()) {
            User user = userService.findUser((String) request.getSession().getAttribute("email"));
            //deleting admission
            userService.deleteUserAdmission(user.getId(), faculty_Name, locale);

            //update all user admissions, user admissions contains in session
            Map<String, Date> mapOfAdmissions = userService.findUserAdmissions(user, locale);
            request.getSession().removeAttribute("admissions map");

            request.getSession().setAttribute("admissions map", mapOfAdmissions);
            modelAndView.setViewName("redirect:/app/admissions");
            return modelAndView;
        } else {
            LOG.warn("Wrong faculty");
            request.setAttribute("error", rb.getString("error.wrong.faculty"));
            modelAndView.setViewName("error");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/app/faculty")
    public ModelAndView facultyAdmission(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        //getting locale
        String locale = (String) request.getSession().getAttribute("language");
        //getting locale for errors
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale(locale));

        String id = request.getParameter("id");
        if (id.isEmpty() || id.equals("")) {
            LOG.warn("Wrong id faculty");
            request.setAttribute("error", rb.getString("error.wrong.id.of.faculty"));
            modelAndView.setViewName("error");
            return modelAndView;
        } else {
            Faculty faculty = facultyService.findFacultyById(id, locale);
            if (faculty == null) {
                LOG.warn("No such faculty");
                request.setAttribute("error", rb.getString("error.no.such.faculty"));
                modelAndView.setViewName("error");
                return modelAndView;
            } else {
                Set<Integer> facultyDemends = facultyService.getFacultyDemends(id);

                Set<Integer> userSubjects = userService.getUserSubjects((String) request.getSession().getAttribute("email"));
                faculty = facultyService.findFacultyById(id, locale);

                request.setAttribute("faculty", faculty);
                //user can pass more than 3 exams, check if user exams is good for faculty demends
                if (userSubjects.containsAll(facultyDemends)) {
                    request.setAttribute("able to apply", true);
                    modelAndView.setViewName("app/faculty");
                    return modelAndView;
                } else {
                    request.setAttribute("able to apply", false);
                    modelAndView.setViewName("app/faculty");
                    return modelAndView;
                }

            }
        }
    }

    @RequestMapping(value = "/app/participate")
    public ModelAndView sendAdmission(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String faculty_id = request.getParameter("faculty_id");
        String locale = (String) request.getSession().getAttribute("language");
        //getting locale for errors
        Locale current = new Locale(locale);
        ResourceBundle rb = ResourceBundle.getBundle("resource", current);

        //checking if values are empty
        if (faculty_id.equals("") || faculty_id.isEmpty()) {
            LOG.warn("faculty_id is empty");
            request.setAttribute("error", "faculty_id is empty");
            modelAndView.setViewName("error");
            return modelAndView;
        } else {

            User user = userService.findUser((String) request.getSession().getAttribute("email"));

            List<Admission> listAdmission = userService.getUserAdmissionForFaculty(user.getId(), Integer.parseInt(faculty_id));
            Set<Integer> list = facultyService.getFacultyDemends(faculty_id);


            //If faculty have no demends, user can`t apply
            if (list.size() < 3) {
                LOG.warn("Faculty have no demends");
                request.setAttribute("error", rb.getString("error.cant.apply"));
                modelAndView.setViewName("error");
                return modelAndView;

            } else if (listAdmission.isEmpty()) {

                userService.addUserAdmissionToFaculty(user.getId(), Integer.parseInt(faculty_id));
                Map<String, Date> mapOfAdmissions = userService.findUserAdmissions(user, locale);
                //update map of admissions in session
                request.getSession().removeAttribute("admissions map");
                request.getSession().setAttribute("admissions map", mapOfAdmissions);
                modelAndView.setViewName("redirect:/app/admissions");
                return modelAndView;

            } else {//if user already send admission
                LOG.warn(request.getSession().getAttribute("email") + " Already send admission");
                request.setAttribute("error", rb.getString("error.already.send.admission"));
                modelAndView.setViewName("error");
                return modelAndView;
            }
        }
    }

    @RequestMapping(value = "/app/personalInfo")
    public ModelAndView showPersonalInfo(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();


        //getting locale
        String locale = (String) request.getSession().getAttribute("language");
        //getting locale for errors
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale(locale));

        User user = userService.findUser((String) request.getSession().getAttribute("email"));

        //getting details for both locales: uk and en
        //we show all user details
        UserDetails userDetails1 = userService.findUserDetails(user, "uk");
        UserDetails userDetails2 = userService.findUserDetails(user, "en");

        //check if we have such user
        if (userDetails1 != null && userDetails2 != null) {
            request.setAttribute("user", user);
            request.setAttribute("details1", userDetails1);
            request.setAttribute("details2", userDetails2);
            modelAndView.setViewName("app/info");
            return modelAndView;
        } else {
            LOG.warn("No such user");
            request.setAttribute("error", rb.getString("error.there.are.not.such.user"));
            modelAndView.setViewName("error");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/app/changeinfo")
    public ModelAndView changePersonalInfo(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();


        //getting parameters
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

        //getting locale
        String locale = (String) request.getSession().getAttribute("language");
        //getting locale for errors
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale(locale));


        //check if empty
        if (email.isEmpty() || pass1.isEmpty() || idn.isEmpty() ||
                name.isEmpty() || surname.isEmpty() || patronymic.isEmpty() || city.isEmpty() ||
                region.isEmpty() || school_name.isEmpty() || average_certificate_point.isEmpty() || name_ua.isEmpty() ||
                surname_ua.isEmpty() || patronymic_ua.isEmpty() || city_ua.isEmpty() || region_ua.isEmpty() ||
                school_name_ua.isEmpty()) {
            LOG.warn("Empty parameters");
            request.setAttribute("error", rb.getString("error.registration.empty.parameters"));
            modelAndView.setViewName("error");
            return modelAndView;
        } else {
            //find user by email, show us if such email exists
            User user = userService.findUser(email);

            //get user from session
            User sessionUser = userService.findUser((String) request.getSession().getAttribute("email"));

            //if email exists
            if (user != null && !user.getEmail().equals(sessionUser.getEmail())) {
                LOG.warn("Email already exists");
                request.setAttribute("error", rb.getString("error.such.email.exists"));
                modelAndView.setViewName("error");
                return modelAndView;
            }
            user = userService.findUserByIdn(idn);

            //if identification number exists
            if (user != null && user.getIdn() != sessionUser.getIdn()) {
                LOG.warn("IDN already exists");
                request.setAttribute("error", rb.getString("error.such.idn.exists"));
                modelAndView.setViewName("error");
                return modelAndView;
            } else {


                //if user changed email, then change email in session
                request.getSession().removeAttribute("email");
                request.getSession().setAttribute("email", email);


                //updating user and user details
                userService.updateUser(email, Long.parseLong(idn), pass1, sessionUser.getId());
                userService.updateDetails(name, surname, patronymic, city, region, school_name, Integer.parseInt(average_certificate_point), name_ua, surname_ua, patronymic_ua, city_ua, region_ua, school_name_ua, sessionUser.getId());
                //if user changed his details - all user admission will delete
                userService.removeUserAdmissions(sessionUser.getId());
                modelAndView.setViewName("redirect:/app/personalInfo");
                return modelAndView;
            }

        }
    }

    @RequestMapping(value = "/app/statements")
    public ModelAndView showListOfStatements(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        request.setAttribute("files", statementWorker.getListOfStatements(PathApp.STATEMENTS_FOLDER));
        modelAndView.setViewName("app/all_statements");
        return modelAndView;
    }

    @RequestMapping(value = "/app/download_statement")
    public void downloadStatement(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelAndView modelAndView = new ModelAndView();

        //getting locale
        String locale = (String) request.getSession().getAttribute("language");
        //getting locale for errors
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale(locale));


        String filename = request.getParameter("name");

        //find file with this name and return it to user
        OutputStream out = response.getOutputStream();
        response.setContentType("application/pdf");

        statementWorker.downloadStatement(PathApp.STATEMENTS_FOLDER + "/" + filename, out);
    }
}
