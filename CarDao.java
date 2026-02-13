package carsharing;

import java.util.List;

public interface CarDao {
    void add(Car car);
    List<Car> findCarsByCompany(int companyId);
    Car findById(int id);
}
