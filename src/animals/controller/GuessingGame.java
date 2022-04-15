package animals.controller;
import animals.model.KnowledgeTree;
import animals.model.Node;
import animals.service.DataBaseService;
import animals.service.DialogueManager;
import animals.service.KnowledgeTreeService;

import java.util.Scanner;

public class GuessingGame {

    public static final String FILE_LOCATION_NAME = getFileLocationName();
    Scanner scanner = new Scanner(System.in);
    DialogueManager dm = new DialogueManager();
    DataBaseService dbs = new DataBaseService();
    KnowledgeTreeService kts = new KnowledgeTreeService();

    public void run(String type) {

        String FILE_NAME = FILE_LOCATION_NAME + type;
        dm.sayHello();
        var exist = dbs.checkFile(FILE_NAME);
        Node root;
        if (exist) {
            root = dbs.load(type, FILE_NAME);
        } else {
            root = new Node(dm.askFavoriteAnimal());
        }
        final var db = new KnowledgeTree(root);


        dm.sayWelcomeExpertSystem();

        while (true) {
            dm.getMenu();
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 0) {
                dm.sayGoodBye();
                System.exit(0);
            }
            switch (choice) {
                case 1:
                    playGame(type, FILE_NAME, db);
                    break;
                case 2:
                    kts.printAnimals(db.getRoot());
                    break;
                case 3:
                    kts.searchAnimal(db.getRoot());
                    break;
                case 4:
                    kts.printStatistics(db.getRoot());
                    break;
                case 5:
                    kts.printTree(db.getRoot());
                    break;
                default:
                    dm.printInvalidOperation();
                    break;
            }
        }

    }

    private void playGame(String type, String FILE_NAME, KnowledgeTree db) {
        do {
            dm.thinkOfAnAnimal();
            while (db.isStatement()) {
                System.out.println(db.getQuestion());
                db.next(dm.checkResponse());
            }

            System.out.println(dm.getAnimalQuestion(db.getQuestion()));
            final boolean isGuessedWrong = !dm.checkResponse();
            if (isGuessedWrong) {
                dm.printIGiveUp();
                final var animal = dm.getAnimal();
                final var guessedAnimal = db.getData();
                dm.printSpecifyAFact(db.getData(), animal);
                final var statement = dm.getStatement();
                dm.printIsTheStatementCorrect(animal);
                final var isCorrect = dm.checkResponse();
                var questionStatement = dm.distinguish(guessedAnimal, animal, statement, isCorrect);
                questionStatement = dm.capitalizeFirstLetter(questionStatement);
                db.addAnimal(animal, questionStatement, isCorrect);
            }
            db.reset();
            if (isGuessedWrong) {
                dbs.save(db.getRoot(), type, FILE_NAME);
            }
            dm.printPlayAgain();
        } while (dm.checkResponse());
    }


    private static String getFileLocationName() {
        String local = System.getProperty("user.language");
        return local.equals("eo") ? "animals_eo." : "animals.";
    }
}