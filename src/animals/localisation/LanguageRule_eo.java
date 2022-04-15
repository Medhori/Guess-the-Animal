package animals.localisation;

import java.util.Scanner;


public class LanguageRule_eo implements LanguageRule {
    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public String getStringAnimal(String input) {
        return input.toLowerCase().strip();
    }

    @Override
    public String getName(String str) {
        return str;
    }

    @Override
    public String getAnimalQuestion(String animal) {
        return String.format("Ĉu ĝi estas %s?", animal);
    }

    @Override
    public String getStatement() {
        while (true) {
            System.out.println("La frazo devas esti de la formato: 'Ĝi <povas/havas/estas/...> ...'.");
            final var statement = scanner.nextLine();
            if (statement.startsWith("Ĝi ") || statement.startsWith("ĝi ")) {
                return statement;
            }
            System.out.println("La ekzemploj de aserto:\nĜi povas flugi\nĜi havas kornojn\nĜi estas mamulo");
        }
    }

    @Override
    public String getStatementQuestion(String data) {
        return String.format("Ĉu %s?", data.toLowerCase());
    }

    @Override
    public void getFact(String firstAnimal, String secondAnimal, String input, boolean posResp) {

        if (posResp) {
            System.out.println(input.replaceFirst("Ĝi", "La " + firstAnimal + " ne") + ".");
            System.out.println(input.replaceFirst("Ĝi", "La " + secondAnimal) + ".");

        } else {
            System.out.println(input.replaceFirst("Ĝi", "La " + firstAnimal) + ".");
            System.out.println(input.replaceFirst("Ĝi", "La " + secondAnimal + " ne") + ".");
        }


    }

    @Override
    public String positiveFact(String str) {
        if (str.startsWith("Ĉu ĝi"))
            return str.replaceFirst("Ĉu ĝi", "Ĝi");
        else {
            return str.replaceFirst("Ĉu ", "Ĝi");
        }
    }

    @Override
    public String negativeFact(String str) {
        if (str.startsWith("Ĉu ĝi"))
            return str.replaceFirst("Ĉu ĝi", "Ĝi ne");
        else {
            return str.replaceFirst("Ĉu ", "Ĝi ne");
        }
    }
}
