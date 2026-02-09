package carsharing;

import java.util.List;

public class DbCompanyDao implements CompanyDao {
    private static final String CONNECTION_URL = "jdbc:h2:~/IdeaProjects/Car Sharing/Car Sharing/task/src/carsharing/db/";
    private static final String CREATE_DB = "CREATE TABLE IF NOT EXISTS COMPANY(" +
            "ID INT PRIMARY KEY AUTO_INCREMENT," +
            "NAME VARCHAR UNIQUE NOT NULL)";
    private static final String SELECT_ALL = "SELECT * FROM COMPANY";
    private static final String SELECT = "SELECT * FROM COMPANY WHERE id = %d";
    private static final String INSERT_DATA = "INSERT INTO COMPANY (name) VALUES ('%s')";
    private static final String UPDATE_DATA = "UPDATE COMPANY SET name " +
            "= '%s' WHERE id = %d";
    private static final String DELETE_DATA = "DELETE FROM COMPANY WHERE id = %d";

    private final Database db;

    public DbCompanyDao(String fileName) {
        db = new Database(CONNECTION_URL + fileName);
        db.run(CREATE_DB);
    }

    @Override
    public void add(Company company) {
        db.run(String.format(INSERT_DATA, company.getName()));
    }

    @Override
    public List<Company> findAll() {
        return db.selectForList(SELECT_ALL);
    }

    @Override
    public Company findById(int id) {
        return db.select(String.format(SELECT, id));
    }

    @Override
    public void update(Company company) {
        db.run(String.format(UPDATE_DATA, company.getName(), company.getId()));
    }

    @Override
    public void deleteById(int id) {
        db.run(String.format(DELETE_DATA, id));
    }

}
