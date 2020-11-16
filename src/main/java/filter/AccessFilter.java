package filter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Access filter. Check if user need authorisation. Opened only for start and registration pages
 *
 * @author Vladislav Prokopenko
 */
@WebFilter(filterName = "AccessFilter", urlPatterns = "/app/*")
public class AccessFilter implements Filter {
    private static final Logger LOG = LogManager.getLogger(AccessFilter.class.getName());

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        if (accessAllowed(req)) {
            chain.doFilter(req, resp);
        } else {
            LOG.warn("Need authorization");
            req.setAttribute("error", "You need authorization");
            req.getRequestDispatcher("/error.jsp")
                    .forward(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

    private boolean accessAllowed(ServletRequest request) {
        HttpSession session = ((HttpServletRequest) request).getSession();
        String commandName = (String) session.getAttribute("auth");
        if (commandName == null || commandName.isEmpty()) {
            return false;
        } else if (!commandName.equals("authorised")) {
            return false;
        } else if (commandName.equals("authorised")) {
            return true;
        } else {
            return false;
        }

    }

}
