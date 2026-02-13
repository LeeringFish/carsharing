package carsharing;

public class Customer {
    private String name;
    private int rentedCarId;

    public Customer(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
