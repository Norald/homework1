package filter;

import model.UserRole;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Access admin filter. Check if user can go to admin pages.
 *
 * @author Vladislav Prokopenko
 */
@WebFilter(filterName = "AdminAccessFilter", urlPatterns = "/app/admin/*")
public class AdminAccessFilter implements Filter {
    private static final Logger LOG = LogManager.getLogger(AdminAccessFilter.class.getName());

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        if (accessAllowed(req)) {
            chain.doFilter(req, resp);
        } else {
            LOG.warn("No rights! Only for admin role");
            req.setAttribute("errorMessage", "You have no rights to do it!");
            req.getRequestDispatcher("/error.jsp")
                    .forward(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }


    private boolean accessAllowed(ServletRequest request) {
        HttpSession session = ((HttpServletRequest) request).getSession();
        UserRole role = (UserRole) session.getAttribute("role");
        if (role == null) {
            return false;
        } else if (!role.getName().equals("admin")) {
            return false;
        } else {
            return true;
        }

    }
}
