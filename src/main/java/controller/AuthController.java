package controller;

import model.User;
import model.UserRole;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.ResourceBundle;

@Controller
public class AuthController {
    private static final Logger LOG = LogManager.getLogger(AuthController.class);


    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /*First method on start application*/
    @GetMapping(value = "/")
    public ModelAndView main() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("start");
        return modelAndView;
    }

    @RequestMapping(value = "/login")
    public ModelAndView login(HttpServletRequest request) {

        String login = request.getParameter("login");
        String password = request.getParameter("pass");


        //getting locale
        String locale = (String) request.getSession().getAttribute("language");
        //getting locale for errors
        Locale current = new Locale(locale);
        ResourceBundle rb = ResourceBundle.getBundle("resource", current);

        //get user and check if exists
        //UserDao userDao = new UserDao();
        User user;

        user = userService.findUser(login, password);


        if (user != null) {

            //check if user blocked
            if (user.isBlocked()) {
                LOG.warn(login + " is blocked. Can`t login");

                ModelAndView modelAndView = new ModelAndView();
                modelAndView.setViewName("error");
                return modelAndView;

            } else {

                LOG.info("Success login");

                HttpSession session = request.getSession();

                //set to session attributes: email, auth and role
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.setViewName("redirect:app/home");
                session.setAttribute("email", user.getEmail());

                session.setAttribute("auth", "authorised");

                session.setAttribute("role", UserRole.getRole(user));


                return modelAndView;
            }
        } else {//if wrong email or password
            LOG.warn(login + " wrong email or password");
            request.setAttribute("error", rb.getString("error.wrong.email.or.password"));
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("error");
            return modelAndView;
        }

    }


    @RequestMapping(value = "/changeLang")
    public ModelAndView changeLanguage(HttpServletRequest request) {

        String language = request.getParameter("lang");

        if (!language.isEmpty()) {
            LOG.info("Changing language");
            request.getSession().removeAttribute("language");
            request.getSession().setAttribute("language", language);

            return new ModelAndView("redirect:" + request.getHeader("referer"));
        } else {
            LOG.warn("Language empty");
            request.setAttribute("error", "error");
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("error");
            return modelAndView;
        }
    }

    @PostMapping(value = "/doRegistration")
    public ModelAndView registration(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
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


        ResourceBundle rb = ResourceBundle.getBundle("resource", new Locale((String) request.getSession().getAttribute("language")));


        //check if we got empty parameters
        if (email.isEmpty() || pass1.isEmpty() || pass2.isEmpty() || idn.isEmpty() ||
                english_name.isEmpty() || english_surname.isEmpty() || english_patronymic.isEmpty() || english_city.isEmpty() ||
                english_region.isEmpty() || english_school_name.isEmpty() || average_certificate_point.isEmpty() || ukrainian_name.isEmpty() ||
                ukrainian_surname.isEmpty() || ukrainian_patronymic.isEmpty() || ukrainian_city.isEmpty() || ukrainian_region.isEmpty() ||
                ukrainian_school_name.isEmpty()) {

            LOG.warn("Empty parameters in registration");
            request.setAttribute("error", rb.getString("error.registration.empty.parameters"));
            modelAndView.setViewName("error");
            return modelAndView;
        } else {
            User user = userService.findUser(email);
            //check if email already exists
            if (user != null) {
                LOG.warn("Such email exists");
                request.setAttribute("error", rb.getString("error.such.email.exists"));
                modelAndView.setViewName("error");
                return modelAndView;
            }
            user = userService.findUserByIdn(idn);
            //check if identification number already exists
            if (user != null) {
                LOG.warn("Such idn exists");
                request.setAttribute("error", rb.getString("error.such.idn.exists"));
                modelAndView.setViewName("error");
                return modelAndView;
            } else if (!pass1.equals(pass2)) { //check if passwords match
                LOG.warn("Passwords dont match");
                request.setAttribute("error", rb.getString("error.passwords.should.match"));
                modelAndView.setViewName("error");
                return modelAndView;
            } else {
                LOG.info("Registration with email is successfull " + email);
                userService.addUser(email, Long.parseLong(idn), pass1);
                user = userService.findUser(email);
                userService.addUserDetails(user.getId(), english_name, english_surname, english_patronymic, english_city, english_region, english_school_name, Integer.parseInt(average_certificate_point), ukrainian_name, ukrainian_surname, ukrainian_patronymic, ukrainian_city, ukrainian_region, ukrainian_school_name);
                modelAndView.setViewName("redirect:start");
                return modelAndView;
            }
        }
    }

    @RequestMapping(value = "/app/logout")
    public ModelAndView logout(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String locale = (String) request.getSession().getAttribute("language");
//        response.setContentType("text/html;charset=UTF-8");

        LOG.info(request.getSession().getAttribute("email") + " success logout");
        HttpSession session = request.getSession();
        //kill session
        session.invalidate();
        modelAndView.setViewName("redirect:/start");
        return modelAndView;
    }

    @RequestMapping(value = "/app/home", method = RequestMethod.GET)
    public ModelAndView goHome(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        System.out.println(request.getContextPath());
        modelAndView.setViewName("app/home");
        return modelAndView;
    }

}
