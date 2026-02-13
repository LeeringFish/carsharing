package carsharing;

import java.util.List;

public class DbCustomerDao implements CustomerDao {
    private static final String CREATE_DB = "CREATE TABLE IF NOT EXISTS CUSTOMER(" +
            "ID INT PRIMARY KEY AUTO_INCREMENT," +
            "NAME VARCHAR UNIQUE NOT NULL," +
            "RENTED_CAR_ID INT," +
            "CONSTRAINT FK_CUSTOMER FOREIGN KEY (RENTED_CAR_ID) " +
            "REFERENCES CAR(ID))";
    private static final String INSERT_DATA = "INSERT INTO CUSTOMER (name) VALUES ('%s')";
    private static final String SELECT_ALL = "SELECT * FROM CUSTOMER";
    private static final String SELECT_RENTED = "SELECT rented_car_id FROM CUSTOMER " +
                                                "WHERE rented_car_id IS NOT NULL";
    private static final String RENT_CAR = "UPDATE CUSTOMER SET rented_car_id = '%s' " +
                                         "WHERE id = %s";
    private static final String RETURN_CAR = "UPDATE CUSTOMER SET rented_car_id = NULL " +
                                         "WHERE id = %s";

    private final Database db;


    public DbCustomerDao(Database db) {
        this.db = db;
        db.run(CREATE_DB);
    }

    public void add(Customer customer) {
        db.run(String.format(INSERT_DATA, customer));
    }

    public List<Customer> findAll() {
        return db.selectCustomerList(SELECT_ALL);
    }

    public List<Integer> getRentedCarIds() {
        return db.selectRentedIds(SELECT_RENTED);
    }

    public void rentCar(Customer customer, int carId) {
        db.run(String.format(RENT_CAR, carId, customer.getId()));
        customer.setRentedCarId(carId);
    }

    public void returnCar(Customer customer) {
        db.run(String.format(RETURN_CAR, customer.getId()));
        customer.setRentedCarId(0);
    }

}
