package carsharing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private final String DB_URL;

    public Database(String url) {
        DB_URL = url;
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

    public Company selectCompany(String query) {
        List<Company> companies = selectCompanyList(query);
        if (companies.size() == 1) {
            return companies.getFirst();
        } else if (companies.isEmpty()) {
            return null;
        } else {
            throw new IllegalStateException("Query returned more than one object");
        }
    }

    public List<Company> selectCompanyList(String query) {
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

    public List<Car> selectCarList(String query) {
        List<Car> cars = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(DB_URL);
             Statement statement = con.createStatement();
             ResultSet resultSetItem = statement.executeQuery(query)
        ) {
            while (resultSetItem.next()) {
                int id = resultSetItem.getInt("id");
                String name = resultSetItem.getString("name");
                int company_id = resultSetItem.getInt("company_id");
                cars.add(new Car(id, name, company_id));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return cars;
    }

}
