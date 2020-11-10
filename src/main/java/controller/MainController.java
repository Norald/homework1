package controller;

import db.dao.UserDao;
import model.User;
import model.UserRole;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.ResourceBundle;

@Controller
public class MainController {
    private static final Logger LOG = LogManager.getLogger(UserDao.class.getName());


    private UserDao userDao;

    @Autowired
    public MainController(UserDao userDao) {
        this.userDao = userDao;
    }

    /*First method on start application*/
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView main() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("start");
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest request) {

        String login = request.getParameter("login");
        String password = request.getParameter("pass");



        //getting locale
        String locale = (String)request.getSession().getAttribute("language");
        //getting locale for errors
        Locale current = new Locale(locale);
        ResourceBundle rb = ResourceBundle.getBundle("resource", current);

        //get user and check if exists
        //UserDao userDao = new UserDao();
        User user;

        user = userDao.findUser(login, password);


        if(user!=null){

            //check if user blocked
            if(user.isBlocked()){
                LOG.warn(login+" is blocked. Can`t login");

                ModelAndView modelAndView = new ModelAndView();
                modelAndView.setViewName("error");
                return modelAndView;
//                request.setAttribute("error", rb.getString("error.blocked"));
//                request.getRequestDispatcher("/error.jsp")
//                        .forward(request, response);
            }else {

                LOG.info("Success login");

                HttpSession session = request.getSession();

                //set to session attributes: email, auth and role
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.setViewName("home");
                session.setAttribute("email", user.getEmail());

                session.setAttribute("auth", "authorised");

                session.setAttribute("role", UserRole.getRole(user));


                return modelAndView;
            }
        }else{//if wrong email or password
            LOG.warn(login+" wrong email or password");
            request.setAttribute("error", rb.getString("error.wrong.email.or.password"));
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("error");
            return modelAndView;
        }

//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("start");
//        return modelAndView;
    }







    /*как только на start.jsp подтвердится форма
    <spring:form method="post"  modelAttribute="userJSP" action="check-user">,
    то попадем вот сюда
     */
//    @RequestMapping(value = "/check-user")
//    public ModelAndView checkUser(@ModelAttribute("userJSP") User user) {
//        ModelAndView modelAndView = new ModelAndView();
//
//        //имя представления, куда нужно будет перейти
//        modelAndView.setViewName("secondPage");
//
//        //записываем в атрибут userJSP (используется на странице *.jsp объект user
//        modelAndView.addObject("userJSP", user);
//
//        return modelAndView; //после уйдем на представление, указанное чуть выше, если оно будет найдено.
//    }
//    @RequestMapping(value = "/123")
//    public ModelAndView check123() {
//        ModelAndView modelAndView = new ModelAndView();
//
//        //имя представления, куда нужно будет перейти
//        modelAndView.setViewName("123");
//
//
//
//        return modelAndView; //после уйдем на представление, указанное чуть выше, если оно будет найдено.
//    }
}
