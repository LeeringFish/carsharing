package carsharing;

import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private final CompanyDao companyDao;
    private final CarDao carDao;
    private final CustomerDao customerDao;
    private final Scanner scan;

    public UserInterface(Scanner scan, CompanyDao companyDao, CarDao carDao, CustomerDao customerDao) {
        this.companyDao = companyDao;
        this.carDao = carDao;
        this.customerDao = customerDao;
        this.scan = scan;
    }

    public void run() {
        boolean running = true;
        int userInput;

        while (running) {
            printMainMenu();
            userInput = Integer.parseInt(scan.nextLine());
            System.out.println();

            switch (userInput) {
                case 1 -> logIn();
                case 2 -> customerLogIn();
                case 3 -> createCustomer();
                case 0 -> running = false;
                default -> System.out.println("Invalid selection");
            }

            System.out.println();
        }

    }

    private void logIn() {
        boolean loggedIn = true;
        int userInput;

        while (loggedIn) {
            printLoggedInMenu();
            userInput = Integer.parseInt(scan.nextLine());
            System.out.println();

            switch (userInput) {
                case 1 -> {
                    List<Company> companies = companyDao.findAll();
                    if (companies.isEmpty()) {
                        System.out.println("The company list is empty!");
                    } else {
                        companyMenu(companies);
                    }
                }
                case 2 -> createCompany();
                case 0 -> loggedIn = false;
                default -> System.out.println("Invalid selection");
            }

            System.out.println();
        }

    }

    private void customerLogIn() {
        List<Customer> customers = customerDao.findAll();
        if (customers.isEmpty()) {
            System.out.println("The customer list is empty!");
            return;
        }

        printCustomers(customers);

        int userChoice = Integer.parseInt(scan.nextLine());
        System.out.println();

        if (userChoice == 0) {
            return;
        }

        Customer customer = customers.get(userChoice - 1);
        logIntoCustomer(customer);
    }

    private void companyMenu(List<Company> companies) {

        System.out.println("Choose the company:");
        for (Company company: companies) {
            System.out.printf("%d. %s\n", company.getId(), company.getName());
        }
        System.out.println("0. Back");

        int userChoice = Integer.parseInt(scan.nextLine());
        System.out.println();

        if (userChoice == 0) {
            return;
        }

        Company company = companies.get(userChoice - 1);
        logIntoCompany(company);
    }

    private void printCustomers(List<Customer> customers) {

        System.out.println("Customer list:");
        for (Customer customer: customers) {
            System.out.printf("%d. %s\n", customer.getId(), customer.getName());
        }
        System.out.println("0. Back");
    }

    private void logIntoCompany(Company company) {
        boolean loggedIn = true;
        int userInput;
        System.out.printf("'%s' company\n", company.getName());

        while (loggedIn) {
            printLoggedIntoCompanyMenu();
            userInput = Integer.parseInt(scan.nextLine());
            System.out.println();

            switch (userInput) {
                case 1 -> {
                    List<Car> cars = carDao.findCarsByCompany(company.getId());
                    if (cars.isEmpty()) {
                        System.out.println("The car list is empty!");
                    } else {
                        System.out.println("Car list:");
                        for (int i = 0; i < cars.size(); i++) {
                            System.out.printf("%d. %s\n", i + 1, cars.get(i));
                        }
                    }

                    System.out.println();
                }
                case 2 -> createCar(company.getId());
                case 0 -> loggedIn = false;
                default -> System.out.println("Invalid selection");
            }

        }

    }

    private void logIntoCustomer(Customer customer) {
        boolean loggedIn = true;
        int userInput;

        while (loggedIn) {
            printLoggedIntoCustomerMenu();
            userInput = Integer.parseInt(scan.nextLine());
            System.out.println();

            switch (userInput) {
                case 1 -> rentCar(customer);
                case 2 -> returnCar(customer);
                case 3 -> printRentedCar(customer);
                case 0 -> loggedIn = false;
            }

            System.out.println();
        }
    }

    private void createCompany() {
        String userInput;

        System.out.println("Enter the company name:");
        userInput = scan.nextLine();
        companyDao.add(new Company(userInput));
        System.out.println("The company was created!");
    }

    private void createCustomer() {
        String userInput;

        System.out.println("Enter the customer name:");
        userInput = scan.nextLine();
        customerDao.add(new Customer(userInput));
        System.out.println("The customer was added!");
    }

    private void rentCar(Customer customer) {

        if (customer.getRentedCarId() != 0) {
            System.out.println("You've already rented a car!");
            return;
        }

        List<Company> companies = companyDao.findAll();

        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
            return;
        }

        System.out.println("Choose a company:");
        for (Company company: companies) {
            System.out.printf("%d. %s\n", company.getId(), company.getName());
        }
        System.out.println("0. Back");

        int userChoice = Integer.parseInt(scan.nextLine());
        System.out.println();

        if (userChoice == 0) {
            return;
        }

        Company company = companies.get(userChoice - 1);
        List<Car> cars = carDao.findCarsByCompany(company.getId());
        List<Integer> rentedCarIds = customerDao.getRentedCarIds();
        List<Car> availableCars = cars.stream().filter(car -> !rentedCarIds.contains(car.getId())).toList();

        if (availableCars.isEmpty()) {
            System.out.printf("No available cars in the '%s' company", company.getName());
            return;
        }

        System.out.println("Choose a car:");
        for (int i = 0; i < availableCars.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, availableCars.get(i));
        }
        System.out.println("0. Back");

        userChoice = Integer.parseInt(scan.nextLine());
        System.out.println();

        if (userChoice == 0) {
            return;
        }

        Car carToRent = availableCars.get(userChoice - 1);
        customerDao.rentCar(customer, carToRent.getId());
        System.out.printf("You rented '%s'\n", carToRent.getName());
    }

    private void returnCar(Customer customer) {
        if (customer.getRentedCarId() == 0) {
            System.out.println("You didn't rent a car!");
            return;
        }

        customerDao.returnCar(customer);
        System.out.println("You've returned a rented car!");
    }

    private void createCar(int companyId) {
        String userInput;

        System.out.println("Enter the car name:");
        userInput = scan.nextLine();
        carDao.add(new Car(userInput, companyId));
        System.out.println("The car was added!\n");
    }

    private void printRentedCar(Customer customer) {
        if (customer.getRentedCarId() == 0) {
            System.out.println("You didn't rent a car!");
            return;
        }

        Car rentedCar = carDao.findById(customer.getRentedCarId());
        Company carCompany = companyDao.findById(rentedCar.getCompanyId());

        System.out.println("Your rented car:");
        System.out.println(rentedCar.getName());
        System.out.println("Company:");
        System.out.println(carCompany.getName());
    }

    private void printMainMenu() {
        System.out.println("1. Log in as a manager");
        System.out.println("2. Log in as a customer");
        System.out.println("3. Create a customer");
        System.out.println("0. Exit");
    }

    private void printLoggedInMenu() {
        System.out.println("1. Company list");
        System.out.println("2. Create a company");
        System.out.println("0. Back");
    }

    private void printLoggedIntoCompanyMenu() {
        System.out.println("1. Car list");
        System.out.println("2. Create a car");
        System.out.println("0. Back");
    }

    private void printLoggedIntoCustomerMenu() {
        System.out.println("1. Rent a car");
        System.out.println("2. Return a rented car");
        System.out.println("3. My rented car");
        System.out.println("0. Back");
    }

}
