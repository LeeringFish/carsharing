package carsharing;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String fileName;

        if (args.length == 2 && "-databaseFileName".equals(args[0])) {
            fileName = args[1];
        } else {
            fileName = "test";
        }

        Scanner scan = new Scanner(System.in);
        CompanyDao companyDao = new DbCompanyDao(fileName);
        UserInterface ui = new UserInterface(scan, companyDao);
        ui.run();
    }
}