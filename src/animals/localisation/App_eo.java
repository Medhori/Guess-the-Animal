package animals.localisation;

import java.util.ListResourceBundle;

public class App_eo extends ListResourceBundle {
    String getMenu() {
        return "Kion vi volas fari:\n" +
                "\n" +
                "1. Ludi la divenludon\n" +
                "2. Listo de ĉiuj bestoj\n" +
                "3. Serĉi beston\n" +
                "4. Kalkuli\n" +
                "5. Printi la Sciarbon\n" +
                "0. Eliri\n";
    }


    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"menu", getMenu()}
        };
    }
}
