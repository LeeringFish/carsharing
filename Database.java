package carsharing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private final String DB_URL;

    public Database(String fileName) {
        DB_URL = "jdbc:h2:~/IdeaProjects/Car Sharing/Car Sharing/task/src/carsharing/db/"
                    + fileName;
    }

    public void run(String str) {
        try (Connection con = DriverManager.getConnection(DB_URL)) {
            con.setAutoCommit(true);
            try (Statement statement = con.createStatement()) {
                statement.executeUpdate(str);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Company select(String query) {
        List<Company> companies = selectForList(query);
        if (companies.size() == 1) {
            return companies.getFirst();
        } else if (companies.isEmpty()) {
            return null;
        } else {
            throw new IllegalStateException("Query returned more than one object");
        }
    }

    public List<Company> selectForList(String query) {
        List<Company> companies = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(DB_URL);
             Statement statement = con.createStatement();
             ResultSet resultSetItem = statement.executeQuery(query)
        ) {
            while (resultSetItem.next()) {
                int id = resultSetItem.getInt("id");
                String name = resultSetItem.getString("name");
                Company company = new Company(id, name);
                companies.add(company);
            }

            return companies;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return companies;
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
