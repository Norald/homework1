package db;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Database manager class. Works with Mysql.
 * Using data pool connection.
 *
 * @author Prokopenko Vladislav
 * @since 25.09.2020
 */
public class DBManager {
    private static final Logger LOG = LogManager.getLogger(DBManager.class.getName());

    private static DBManager instance;


    private DBManager() {
    }

    /**
     * Connection is singleton;
     */
    public static synchronized DBManager getInstance() {
        if (instance == null)
            instance = new DBManager();
        return instance;
    }


    public Connection getConnection() throws SQLException {
        Connection con = null;
        try {

            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");

            // iinspection_board - the name of data source
            DataSource ds = (DataSource) envContext.lookup("jdbc/board");
            con = ds.getConnection();
        } catch (NamingException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return con;
    }


    /**
     * Commits and close the given connection.
     *
     * @param con Connection to be committed and closed.
     */
    public void commitAndClose(Connection con) {
        try {
            if (con != null) {
                con.commit();
                con.close();
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    /**
     * Rollbacks and close the given connection.
     *
     * @param con Connection to be rollbacked and closed.
     */
    public void rollbackAndClose(Connection con) {
        try {
            con.rollback();
            con.close();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }
}
