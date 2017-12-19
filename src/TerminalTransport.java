import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TerminalTransport {
    public static void main(String[] args) {
        Scanner choose = new Scanner(System.in);
        int choiceInt;
        String choice, from, to;
        if (args.length != 0) {
            from = args[0];
            to = args[1];
        } else {
            System.out.print("From: ");
            from = choose.nextLine();
            System.out.print("To:   ");
            to = choose.nextLine();
        }
        choice = "";
        if (args.length != 0) {
            from = args[0];
            to = args[1];
        }
        System.out.println("Calculating...");
        ArrayList<Connection> connections = JsonParser.buildConnection(from, to);


        printMenu(connections, false);
        choice = choose.next();
        boolean invalid = false;

        while (!"q".equals(choice)) {
            choiceInt = Integer.parseInt(choice);

            if (choiceInt < connections.size() && choiceInt >= 0) {
                SystemOut.clearScreen();
                connections.get(choiceInt).print();

                System.out.println(ConsoleColors.BLUE + "Press any key to continue..." + ConsoleColors.RESET);
                try {
                    System.in.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                invalid = true;
            }
            printMenu(connections, invalid);
            choice = choose.next();
            invalid = false;
        }

        choose.close();
    }

    private static void printMenu(ArrayList<Connection> connections, boolean invalid) {
        SystemOut.clearScreen();
        for (int i = 0; i < connections.size(); i++) {
            SystemOut.centerPrint("Connection " + i, 42);
            connections.get(i).printSummary();
        }
        SystemOut.centerPrint("Select one Connection:", 42);
        if (invalid)
            System.out.println(ConsoleColors.RED + "Invalid connection (Options: 0 to " + connections.size() + ")" + ConsoleColors.RESET);
        System.out.print("> ");
    }
}
