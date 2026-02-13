package carsharing;

import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private CompanyDao companyDao;
    private CarDao carDao;
    private Scanner scan;

    public UserInterface(Scanner scan, CompanyDao companyDao, CarDao carDao) {
        this.companyDao = companyDao;
        this.carDao = carDao;
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

    private void createCompany() {
        String userInput;

        System.out.println("Enter the company name:");
        userInput = scan.nextLine();
        companyDao.add(new Company(userInput));
        System.out.println("The company was created!");
    }

    private void createCar(int companyId) {
        String userInput;

        System.out.println("Enter the car name:");
        userInput = scan.nextLine();
        carDao.add(new Car(userInput, companyId));
        System.out.println("The car was added!\n");
    }

    private void printMainMenu() {
        System.out.println("1. Log in as a manager");
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

}
