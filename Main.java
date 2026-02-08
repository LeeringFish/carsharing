package carsharing;

public class Main {

    public static void main(String[] args) {
        String fileName;

        if (args.length == 2 && "-databaseFileName".equals(args[0])) {
            fileName = args[1];
        } else {
            fileName = "test";
        }

        Database db = new Database(fileName);
        db.createTable();
    }
}