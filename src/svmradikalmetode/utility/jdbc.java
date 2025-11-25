package svmradikalmetode.utility;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author cvgs
 */
public class jdbc {

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setUrl("jdbc:mysql://localhost/db_askrm");
            dataSource.setUser("root");
            dataSource.setPassword("");

            connection = dataSource.getConnection();
        }
        return connection;
    }
    
}
