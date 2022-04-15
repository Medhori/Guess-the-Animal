package animals.service;

import animals.localisation.LanguageRule;
import animals.localisation.LanguageRule_en;
import animals.localisation.LanguageRule_eo;

import java.text.MessageFormat;
import java.util.*;

import static animals.service.BundleService.*;

public class DialogueManager {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();
    LanguageRule lr = getLanguageRule();

    private LanguageRule getLanguageRule() {
        return System.getProperty("user.language").equals("eo") ? new LanguageRule_eo() : new LanguageRule_en();
    }

    public void sayHello() {
        System.out.println(getHello());
        System.out.println();
    }

    public void sayWelcomeExpertSystem() {
        System.out.println();
        System.out.println(getWelcome());

    }

    public void sayGoodBye() {
        System.out.println();
        System.out.println(getBye());
    }

    public boolean checkResponse() {
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

    String readLine() {
        return scanner.nextLine().trim().toLowerCase();
    }

    private String pickMessage(final String[] messages) {
        return messages[random.nextInt(messages.length)];
    }

    public String getAnimalQuestion(String animal) {
        return lr.getAnimalQuestion(animal);
    }

    public String getAnimal() {
        return lr.getStringAnimal(readLine());
    }

    public String askFavoriteAnimal() {
        System.out.println(getLocalString("ask.favorite.animal"));
        return lr.getStringAnimal(readLine());
    }

    public void thinkOfAnAnimal() {
        System.out.println(getLocalString("think"));
        System.out.println(getLocalString("enter"));
        scanner.nextLine();
    }

    public String getStatement() {
        return lr.getStatement();
    }

    public void printIGiveUp() {
        System.out.println(getLocalString("give.up"));
    }

    public void printSpecifyAFact(String firstAnimal, String secondAnimal) {
        var result = MessageFormat
                .format(getLocalString("specify.fact"), firstAnimal, secondAnimal);
        System.out.println(result);
    }

    public void printIsTheStatementCorrect(String animal) {
        var result = MessageFormat
                .format(getLocalString("is.correct"), animal);
        System.out.println(result);
    }

    public void printPlayAgain() {
        var result = pickMessage(getLocalArrayString("again"));
        System.out.println(result);
    }

    public void getMenu() {
        System.out.println();
        System.out.println(getLocalMenu());
    }

    public void printYourChoice() {
        System.out.println(getLocalString("choice"));
    }

    public void printInvalidOperation() {
        var result = MessageFormat
                .format(getLocalString("error"), "5");
        System.out.println(result);
    }

    private String getString(String response) {
        List<Character> chars = List.of('.', '?', '!', ',');
        if (chars.contains(response.charAt(response.length() - 1))) {
            response = response.substring(0, response.length() - 1);
        }
        return response;
    }

    public String capitalizeFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public String distinguish(String firstAnimal, String secondAnimal, String input, boolean posResp) {
        System.out.println(getLocalString("learned"));
        input = getString(input);
        firstAnimal = lr.getName(firstAnimal);
        secondAnimal = lr.getName(secondAnimal);
        String response;
        String newStatement = lr.getStatementQuestion(input);
        lr.getFact(firstAnimal, secondAnimal, input, posResp);
        System.out.println(getLocalString("distinguish"));
        System.out.printf("- %s\n", newStatement);
        System.out.println(pickMessage(getLocalArrayString("thanks")));
        System.out.println();
        return newStatement;
    }


}