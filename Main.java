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

        String url = "jdbc:h2:~/IdeaProjects/Car Sharing/Car Sharing/task/src/carsharing/db/" + fileName;
        Database db = new Database(url);

        Scanner scan = new Scanner(System.in);
        CompanyDao companyDao = new DbCompanyDao(db);
        CarDao carDao = new DbCarDao(db);
        UserInterface ui = new UserInterface(scan, companyDao, carDao);
        ui.run();
    }
}