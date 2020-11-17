package controller;

import exception.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    private static final Logger LOG = LogManager.getLogger(GlobalControllerExceptionHandler.class);


    @ExceptionHandler(UserBlockedException.class)
    public ModelAndView blockUserHandler(HttpServletRequest request) {
        LOG.warn("Blocked. Can`t login");
        request.setAttribute("error", "You are blocked.");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error");
        return mav;
    }

    @ExceptionHandler(WrongEmailOrPasswordException.class)
    public ModelAndView wrongEmailOrPasswordHandler(HttpServletRequest request)  {
        LOG.warn("Wrong email or password");
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale((String) request.getSession().getAttribute("language")));
        request.setAttribute("error", rb.getString("error.wrong.email.or.password"));
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error");
        return mav;
    }

    @ExceptionHandler(EmptyLanguageException.class)
    public ModelAndView emptyLanguageHandler(HttpServletRequest request)  {
        LOG.warn("Language empty");
        request.setAttribute("error", "error");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ExceptionHandler(EmptyParametersException.class)
    public ModelAndView emptyParametersHandler(HttpServletRequest request)  {
        ModelAndView modelAndView = new ModelAndView();
        LOG.warn("Empty parameters");
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale((String) request.getSession().getAttribute("language")));
        request.setAttribute("error", rb.getString("error.registration.empty.parameters"));
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ExceptionHandler(SuchEmailExistException.class)
    public ModelAndView suchEmailExistHandler(HttpServletRequest request)  {
        ModelAndView modelAndView = new ModelAndView();
        LOG.warn("Such email exists");
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale((String) request.getSession().getAttribute("language")));
        request.setAttribute("error", rb.getString("error.such.email.exists"));
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ExceptionHandler(SuchIdnExistException.class)
    public ModelAndView suchIdnExistHandler(HttpServletRequest request)  {
        ModelAndView modelAndView = new ModelAndView();
        LOG.warn("Such idn exists");
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale((String) request.getSession().getAttribute("language")));
        request.setAttribute("error", rb.getString("error.such.idn.exists"));
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ExceptionHandler(PasswordDontMatchException.class)
    public ModelAndView passwordDontMatchHandler(HttpServletRequest request)  {
        ModelAndView modelAndView = new ModelAndView();
        LOG.warn("Passwords dont match");
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale((String) request.getSession().getAttribute("language")));
        request.setAttribute("error", rb.getString("error.passwords.should.match"));
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ExceptionHandler(CantGetFacultiesException.class)
    public ModelAndView cantGetFacultiesHandler(HttpServletRequest request)  {
        ModelAndView modelAndView = new ModelAndView();
        LOG.warn("Can`t get faculties");
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale((String) request.getSession().getAttribute("language")));
        request.setAttribute("error", rb.getString("error.cant.get.faculties"));
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ExceptionHandler(WrongIdOfSubjectExamException.class)
    public ModelAndView wrongIdOfSubjectExamHandler(HttpServletRequest request)  {
        ModelAndView modelAndView = new ModelAndView();
        LOG.warn("Wrong id or subject exam");
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale((String) request.getSession().getAttribute("language")));
        request.setAttribute("error", rb.getString("error.wrong.id.or.subject.exam"));
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ExceptionHandler(WrongFacultyException.class)
    public ModelAndView wrongFacultyHandler(HttpServletRequest request)  {
        ModelAndView modelAndView = new ModelAndView();
        LOG.warn("Wrong faculty");
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale((String) request.getSession().getAttribute("language")));
        request.setAttribute("error", rb.getString("error.wrong.faculty"));
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ExceptionHandler(WrongIdOfFacultyException.class)
    public ModelAndView wrongIdOfFacultyHandler(HttpServletRequest request)  {
        ModelAndView modelAndView = new ModelAndView();
        LOG.warn("Wrong id faculty");
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale((String) request.getSession().getAttribute("language")));
        request.setAttribute("error", rb.getString("error.wrong.id.of.faculty"));
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ExceptionHandler(NoSuchFacultyException.class)
    public ModelAndView noSuchFacultyHandler(HttpServletRequest request)  {
        ModelAndView modelAndView = new ModelAndView();
        LOG.warn("No such faculty");
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale((String) request.getSession().getAttribute("language")));
        request.setAttribute("error", rb.getString("error.no.such.faculty"));
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ExceptionHandler(EmptyFacultyIdException.class)
    public ModelAndView emptyFacultyIdHandler(HttpServletRequest request)  {
        ModelAndView modelAndView = new ModelAndView();
        LOG.warn("faculty_id is empty");
        request.setAttribute("error", "faculty_id is empty");
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ExceptionHandler(FacultyHaveNoDemendsException.class)
    public ModelAndView facultyHaveNoDemendsHandler(HttpServletRequest request)  {
        ModelAndView modelAndView = new ModelAndView();
        LOG.warn("Faculty have no demends");
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale((String) request.getSession().getAttribute("language")));
        request.setAttribute("error", rb.getString("error.cant.apply"));
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ExceptionHandler(AlreadySendedAmdissionException.class)
    public ModelAndView alreadySendedAdmissionHandler(HttpServletRequest request)  {
        ModelAndView modelAndView = new ModelAndView();
        LOG.warn(request.getSession().getAttribute("email") + " Already send admission");
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale((String) request.getSession().getAttribute("language")));
        request.setAttribute("error", rb.getString("error.already.send.admission"));
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ExceptionHandler(NoSuchUserException.class)
    public ModelAndView noSuchUserHandler(HttpServletRequest request)  {
        ModelAndView modelAndView = new ModelAndView();
        LOG.warn("No such user");
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale((String) request.getSession().getAttribute("language")));
        request.setAttribute("error", rb.getString("error.there.are.not.such.user"));
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ExceptionHandler(CantGetSubjectExamException.class)
    public ModelAndView cantGetSubjectExamHandler(HttpServletRequest request)  {
        ModelAndView modelAndView = new ModelAndView();
        LOG.warn("Cant get subject exam");
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale((String) request.getSession().getAttribute("language")));
        request.setAttribute("error", rb.getString("error.cant.get.subject.exam"));
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ExceptionHandler(CantGetUsersException.class)
    public ModelAndView cantGetUsersHandler(HttpServletRequest request)  {
        ModelAndView modelAndView = new ModelAndView();
        LOG.warn("Cant get users");
        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale((String) request.getSession().getAttribute("language")));
        request.setAttribute("error", rb.getString("error.cant.get.users"));
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ExceptionHandler(WrongOperationException.class)
    public ModelAndView wrongOperationHandler(HttpServletRequest request)  {
        ModelAndView modelAndView = new ModelAndView();
        LOG.warn("Wrong operation");
        request.setAttribute("error", "Wrong operation");
        modelAndView.setViewName("error");
        return modelAndView;
    }

    /**
     * 404 error
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView default404ErrorHandler(HttpServletRequest request) {
        request.setAttribute("error", "404. No such resource.");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error");
        return mav;
    }

    @ExceptionHandler(Throwable.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest request)  {
        request.setAttribute("error", "Error happened.");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error");
        return mav;
    }
}
