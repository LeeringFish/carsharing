package carsharing;

import java.util.ArrayList;
import java.util.List;

public class DbCarDao implements CarDao {
    private static final String CREATE_DB = "CREATE TABLE IF NOT EXISTS CAR(" +
            "ID INT PRIMARY KEY AUTO_INCREMENT," +
            "NAME VARCHAR UNIQUE NOT NULL," +
            "COMPANY_ID INT NOT NULL," +
            "CONSTRAINT FK_COMPANY FOREIGN KEY (COMPANY_ID) " +
            "REFERENCES COMPANY(ID))";

    private static final String SELECT_BY_COMPANY = "SELECT * FROM CAR WHERE company_id = %d";
    private static final String SELECT_BY_ID = "SELECT * FROM CAR WHERE id = %d";
    private static final String INSERT_DATA = "INSERT INTO CAR (name, company_id) VALUES ('%s', %d)";

    private final Database db;

    public DbCarDao(Database db) {
        this.db = db;
        db.run(CREATE_DB);
    }

    public void add(Car car) {
        db.run(String.format(INSERT_DATA, car.getName(), car.getCompanyId()));
    }

    public List<Car> findCarsByCompany(int companyId) {
        return db.selectCarList(String.format(SELECT_BY_COMPANY, companyId));
    }

    public Car findById(int id) {
        return db.selectCar(String.format(SELECT_BY_ID, id));
    }

}
