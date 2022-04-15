package animals.localisation;

import java.util.ListResourceBundle;

public class App extends ListResourceBundle {
    String getMenu() {
        return "What do you want to do:\n" +
                "\n" +
                "1. Play the guessing game\n" +
                "2. List of all animals\n" +
                "3. Search for an animal\n" +
                "4. Calculate statistics\n" +
                "5. Print the Knowledge Tree\n" +
                "0. Exit\n";
    }


    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"menu", getMenu()}
        };
    }
}
