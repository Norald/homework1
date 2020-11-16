package listener;

import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Context listener.
 *
 * @author Vladislav Prokopenko
 */
public class ContextListener implements ServletContextListener {
    public ContextListener() {
    }

    public void contextDestroyed(ServletContextEvent paramServletContextEvent) {
    }

    public void contextInitialized(ServletContextEvent servletContext) {
        PropertyConfigurator.configure(servletContext.getServletContext().getRealPath(
                "WEB-INF/log4j.properties"));
    }
}