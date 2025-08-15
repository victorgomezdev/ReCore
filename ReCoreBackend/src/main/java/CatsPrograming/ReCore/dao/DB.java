package CatsPrograming.ReCore.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {

    private static final String DRIVER = "org.h2.Driver";

    private static final String URL = "jdbc:h2:./dc";

    private static final String USER = "sa";

    private static final String PASSWORD = "sa";

    private static final String SQL_DROP_CREATE_ADDRESS = "DROP TABLE IF EXISTS " +
            " ADDRESSES; CREATE TABLE ADDRESSES (ID INT AUTO_INCREMENT PRIMARY KEY, " +
            " STREET VARCHAR(100) NOT NULL, " +
            " NUMBER INT NOT NULL, " +
            " LOCATION VARCHAR(100) NOT NULL, " +
            " PROVINCE VARCHAR(100) NOT NULL)";

    private static final String SQL_DROP_CREATE_USERS = "DROP ABLE IF EXISTS " +
            " USERS; CREATE TABLE USERS (ID INT AUTO_INCREMENT PRIMARY KEY, " +
            " NAME VARCHAR (100) NOT NULL, " +
            " EMAIL VARCHAR (100) NOT NULL, " +
            " PASSWORD VARCHAR(100) NOT NULL, " +
            " DNI VARCHAR(25) NOT NULL, " +
            " JOIN_DATE DATE NOT NULL, " +
            " LAST_LOGIN DATE NULL, " +
            " ADDRESS_ID INT NOT NULL)";

    private static final String SQL_INSERT = "INSERT INTO ADDRESSES (STREET, NUMBER, LOCATION, PROVINCE)" +
            " VALUES('Dorrego', 1680, 'Olavarria', 'Buenos Aires'); " +
            " INSERT INTO USERS (NAME, EMAIL, PASSWORD, DNI, JOIN_DATE, ADDRESS_ID) "+
            " VALUES ('Victor Manuel Gomez', 'Victorgomezdev87@gmail.com', '15553389Aa*', '40373553', '2025-08-08', 1)";

    public static Connection getConnection() throws Exception {
        Class.forName(DRIVER);
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void createTables() {
        Connection connection = null;

        try {
            connection = getConnection();
            Statement statement = connection.createStatement();
            statement.execute(SQL_DROP_CREATE_ADDRESS);
            statement.execute(SQL_DROP_CREATE_USERS);
            statement.execute(SQL_INSERT);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
