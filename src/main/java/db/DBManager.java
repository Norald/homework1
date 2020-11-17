package db;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Database manager class.
 *
 * @author Prokopenko Vladislav
 * @since 25.09.2020
 */
@Repository
public class DBManager {
    private static final Logger LOG = LogManager.getLogger(DBManager.class.getName());

    private static DBManager instance;


    public DBManager() {
    }



    public Connection getConnection() throws SQLException {
        Connection con = null;
        try {

            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/board");
            con = ds.getConnection();
        } catch (NamingException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return con;
    }



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


    public void rollbackAndClose(Connection con) {
        try {
            con.rollback();
            con.close();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }
}
