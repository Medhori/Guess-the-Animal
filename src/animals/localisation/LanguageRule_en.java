package animals.localisation;

import java.util.Map;
import java.util.Scanner;


import static animals.localisation.SentenceGenerator.generateSentence;


public class LanguageRule_en implements LanguageRule {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String VOWEL_ARTICLE = "an ";
    private static final String CONSONANT_ARTICLE = "a ";
    private static final String ENGLISH_VOWELS = "aeiou";


    public String getStringAnimal(String input) {
        String name = input.toLowerCase().strip();
        if (name.startsWith(VOWEL_ARTICLE) || name.startsWith(CONSONANT_ARTICLE)) {
            return name;
        } else {
            final var isStartVowel = ENGLISH_VOWELS.indexOf(name.charAt(0)) > -1;
            final var article = isStartVowel ? VOWEL_ARTICLE : CONSONANT_ARTICLE;
            return article + name;
        }
    }

    @Override
    public String getName(String str) {
        return str.replaceFirst("an? (.+)", "$1");
    }

    @Override
    public String getAnimalQuestion(String animal) {
        return String.format("Is it %s?", animal);
    }

    @Override
    public String getStatement() {
        while (true) {
            System.out.println("The sentence should be of the format: 'It can/has/is ...'.");
            final var statement = scanner.nextLine().toLowerCase();
            if (statement.matches("it (can|has|is) .+")) {
                return statement;
            }
            System.out.println("The examples of a statement: \n" +
                    "It can fly \n" +
                    "It has horns \n" +
                    "It is a mammal ");
        }
    }

    @Override
    public String getStatementQuestion(String data) {
        return generateSentence(data, Map.of(
                "it can (.*)", "Can it $1?",
                "it has (.*)", "Does it have $1?",
                "it is (.*)", "Is it $1?"
        ));
    }

    @Override
    public void getFact(String firstAnimal, String secondAnimal, String input, boolean posResp) {
        String response;
        if (input.startsWith("it can ")) {
            response = (input.replaceFirst("it can ", "")).trim();
            if (posResp) {
                System.out.printf("- The %s can't %s.\n", firstAnimal, response);
                System.out.printf("- The %s can %s.\n", secondAnimal, response);
            } else {
                System.out.printf("- The %s can %s.\n", firstAnimal, response);
                System.out.printf("- The %s can't %s.\n", secondAnimal, response);
            }
        } else if (input.startsWith("it has ")) {
            response = (input.replaceFirst("it has ",
                    "")).trim();
            if (posResp) {
                System.out.printf("- The %s doesn't have %s.\n", firstAnimal, response);
                System.out.printf("- The %s has %s.\n", secondAnimal, response);
            } else {
                System.out.printf("- The %s has %s.\n", firstAnimal, response);
                System.out.printf("- The %s doesn't have %s.\n", secondAnimal, response);
            }

        } else {
            response = (input.replaceFirst("it is ",
                    "")).trim();
            if (posResp) {
                System.out.printf("- The %s isn't %s.\n", firstAnimal, response);
                System.out.printf("- The %s is %s.\n", secondAnimal, response);
            } else {
                System.out.printf("- The %s is %s.\n", firstAnimal, response);
                System.out.printf("- The %s isn't %s.\n", secondAnimal, response);
            }
        }
    }


    public String positiveFact(String str) {

        if (str.startsWith("Does it have")) {
            str = str.replaceFirst("Does it have", "It has");
        } else if (str.startsWith("Can it")) {
            str = str.replaceFirst("Can it", "It can");
        } else {
            str = str.replaceFirst("Is it", "It is");
        }
        return str.substring(0, str.length() - 1) + ".";
    }

    public String negativeFact(String str) {
        if (str.startsWith("Does it have")) {
            str = str.replaceFirst("Does it have", "It doesn't have");
        } else if (str.startsWith("Can it")) {
            str = str.replaceFirst("Can it", "It can't");
        } else {
            str = str.replaceFirst("Is it", "It isn't");
        }
        return str.substring(0, str.length() - 1) + ".";
    }

}
