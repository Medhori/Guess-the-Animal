package animals.service;

import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

public class BundleService {
    static Scanner scanner = new Scanner(System.in);

    static ResourceBundle resourceGreetings = ResourceBundle.getBundle("animals.localisation.Greetings");
    static ResourceBundle resourceApp = ResourceBundle.getBundle("animals.localisation.App");
    static ResourceBundle resourceBundle = ResourceBundle.getBundle("application");

    public static String getEnter() {
        return resourceApp.getString("enter");
    }

    public static String getHello() {
        return resourceGreetings.getString("hi");
    }

    public static String getWelcome() {
        return resourceGreetings.getString("welcome");
    }

    public static String getBye() {
        return resourceGreetings.getString("bye");
    }

    public static String getLocalMenu() {
        return resourceApp.getString("menu");
    }


    public static String getLocalString(String str) {
        return resourceBundle.getString(str);
    }

    public static String[] getLocalArrayString(String str) {
        return resourceBundle.getString(str).split("\f");
    }

    private static String pickMessage(final String[] messages) {
        return messages[new Random().nextInt(messages.length)];
    }

    public static boolean checkResponse() {
        final var bundle = ResourceBundle.getBundle("application");
        while (true) {
            final var respond = scanner.nextLine().toLowerCase().trim();
            if (respond.matches(bundle.getString("pattern.yes"))) {
                return true;
            }
            if (respond.matches(bundle.getString("pattern.no"))) {
                return false;
            }
            System.out.println(pickMessage(bundle.getString("ask.again").split("\f")));
        }
    }
}
