package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private final String DB_URL;

    public Database(String fileName) {
        DB_URL = "jdbc:h2:~/IdeaProjects/Car Sharing/Car Sharing/task/src/carsharing/db/"
                    + fileName;
    }

    public void createTable() {

        try (Connection con = DriverManager.getConnection(DB_URL)) {
            con.setAutoCommit(true);
            try (Statement statement = con.createStatement()) {

                statement.executeUpdate("CREATE TABLE IF NOT EXISTS COMPANY(" +
                        "ID INT," +
                        "NAME VARCHAR)");

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
