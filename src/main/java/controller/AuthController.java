package controller;

import exception.*;
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
    public ModelAndView login(HttpServletRequest request) throws UserBlockedException, WrongEmailOrPasswordException {
        //get user and check if exists
        User user = userService.findUser(request);
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
    public ModelAndView changeLanguage(HttpServletRequest request) throws EmptyLanguageException {
        String language = userService.getLanguage(request);
        LOG.info("Changing language");
        request.getSession().removeAttribute("language");
        request.getSession().setAttribute("language", language);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @RequestMapping(value = "/doRegistration")
    public ModelAndView registration(HttpServletRequest request) throws EmptyParametersException, SuchEmailExistException, SuchIdnExistException, PasswordDontMatchException {
        ModelAndView modelAndView = new ModelAndView();
        userService.doRegistration(request);
        modelAndView.setViewName("redirect:start");
        return modelAndView;
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
        modelAndView.setViewName("app/home");
        return modelAndView;
    }

}
