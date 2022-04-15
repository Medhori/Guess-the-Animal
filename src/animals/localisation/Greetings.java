package animals.localisation;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ListResourceBundle;
import java.util.Random;

public class Greetings extends ListResourceBundle {
    static Random random = new Random();

    private String greetings() {
        final var messages = new ArrayList<String>();
        final var time = LocalTime.now();
        if (time.isAfter(LocalTime.NOON) && time.isBefore(LocalTime.of(18, 0))) {
            messages.add("Good afternoon!");
        } else if (time.isAfter(LocalTime.of(5, 0)) && time.isBefore(LocalTime.NOON)) {
            messages.add("Good morning!");
        } else if (time.isAfter(LocalTime.of(18, 0))) {
            messages.add("Good evening!");
        } else if (time.isAfter(LocalTime.MIDNIGHT) && time.isBefore(LocalTime.of(4, 0))) {
            messages.add("Hi Night Owl!");
        } else if (time.isAfter(LocalTime.of(4, 0))) {
            messages.add("Hi Early Bird!");
        }
        messages.add("Hello!");
        return pickMessage(messages.toArray(String[]::new));
    }

    private String pickMessage(final String[] messages) {
        return messages[random.nextInt(messages.length)];
    }

    public String sayBye() {
        return pickMessage(new String[]{
                "Bye!",
                "Bye, bye!",
                "See you later!",
                "See you soon!",
                "Talk to you later!",
                "I’m off!",
                "It was nice seeing you!",
                "See ya!",
                "See you later, alligator!",
                "In a while, crocodile!",
                "Have a nice one!"
        });
    }


    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"hi", greetings()},
                {"welcome", "Welcome to the animals expert system"},
                {"bye", sayBye()}};
    }
}