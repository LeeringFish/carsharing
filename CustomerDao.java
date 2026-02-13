package carsharing;

import java.util.List;

public interface CustomerDao {
    void add(Customer customer);
    List<Customer> findAll();
    List<Integer> getRentedCarIds();
    void rentCar(Customer customer, int carId);
    void returnCar(Customer customer);
}
