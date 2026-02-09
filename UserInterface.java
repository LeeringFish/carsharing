package carsharing;

import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private CompanyDao companyDao;
    private Scanner scan;

    public UserInterface(Scanner scan, CompanyDao companyDao) {
        this.companyDao = companyDao;
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
                case 1 -> printCompanyList();
                case 2 -> createCompany();
                case 0 -> loggedIn = false;
                default -> System.out.println("Invalid selection");
            }

            System.out.println();
        }

    }

    private void printCompanyList() {
        List<Company> companies = companyDao.findAll();

        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
        } else {
            System.out.println("Company list:");
            for (Company company: companies) {
                System.out.printf("%d. %s\n", company.getId(), company.getName());
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

    private void printMainMenu() {
        System.out.println("1. Log in as a manager");
        System.out.println("0. Exit");
    }

    private void printLoggedInMenu() {
        System.out.println("1. Company list");
        System.out.println("2. Create a company");
        System.out.println("0. Back");
    }

}
