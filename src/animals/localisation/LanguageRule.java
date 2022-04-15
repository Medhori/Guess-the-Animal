package animals.localisation;

public interface LanguageRule {
    String getStringAnimal(String input);

    String getName(String str);

    String getAnimalQuestion(String animal);

    String getStatement();

    String getStatementQuestion(String data);

    void getFact(String firstAnimal, String secondAnimal, String input, boolean posResp);

    String positiveFact(String str);

    String negativeFact(String str);
}
