package controller;

import exception.*;
import model.User;
import model.UserRole;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    public ModelAndView login(@RequestParam(name = "login") String login, @RequestParam(name = "pass") String pass, HttpServletRequest request) throws UserBlockedException, WrongEmailOrPasswordException {
        //get user and check if exists
        User user = userService.findUser(login, pass);
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


    @RequestMapping(value = "/changeLang")
    public ModelAndView changeLanguage(@RequestParam(name = "lang") String lang, HttpServletRequest request) throws EmptyLanguageException {
        LOG.info("Changing language");
        request.getSession().removeAttribute("language");
        request.getSession().setAttribute("language", lang);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @RequestMapping(value = "/doRegistration")
    public ModelAndView registration(@RequestParam(name = "email") String email,
                                     @RequestParam(name = "pass1") String pass1,
                                     @RequestParam(name = "pass2") String pass2,
                                     @RequestParam(name = "idn") String idn,
                                     @RequestParam(name = "name") String english_name,
                                     @RequestParam(name = "surname") String english_surname,
                                     @RequestParam(name = "patronymic") String english_patronymic,
                                     @RequestParam(name = "city") String english_city,
                                     @RequestParam(name = "region") String english_region,
                                     @RequestParam(name = "school_name") String english_school_name,
                                     @RequestParam(name = "average_certificate_point") String average_certificate_point,
                                     @RequestParam(name = "name_ua") String ukrainian_name,
                                     @RequestParam(name = "surname_ua") String ukrainian_surname,
                                     @RequestParam(name = "patronymic_ua") String ukrainian_patronymic,
                                     @RequestParam(name = "city_ua") String ukrainian_city,
                                     @RequestParam(name = "region_ua") String ukrainian_region,
                                     @RequestParam(name = "school_name_ua") String ukrainian_school_name, HttpServletRequest request) throws EmptyParametersException, SuchEmailExistException, SuchIdnExistException, PasswordDontMatchException {
        ModelAndView modelAndView = new ModelAndView();


        userService.doRegistration(email, pass1, pass2, idn, english_name, english_surname, english_patronymic, english_city, english_region, english_school_name, average_certificate_point, ukrainian_name,
                ukrainian_surname, ukrainian_patronymic, ukrainian_city, ukrainian_region, ukrainian_school_name);
        modelAndView.setViewName("redirect:start");
        return modelAndView;
    }

    @RequestMapping(value = "/app/logout")
    public ModelAndView logout(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        LOG.info(request.getSession().getAttribute("email") + " success logout");
        HttpSession session = request.getSession();
        //kill session
        session.invalidate();
        modelAndView.setViewName("redirect:/start");
        return modelAndView;
    }

    @RequestMapping(value = "/app/home", method = RequestMethod.GET)
    public ModelAndView goHome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("app/home");
        return modelAndView;
    }

}
