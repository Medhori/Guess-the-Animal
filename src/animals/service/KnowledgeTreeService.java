package animals.service;

import animals.model.Node;
import animals.localisation.LanguageRule;
import animals.localisation.LanguageRule_en;
import animals.localisation.LanguageRule_eo;

import static animals.service.BundleService.*;

import java.text.MessageFormat;
import java.util.*;

import static java.lang.System.out;

public class KnowledgeTreeService {
    public static final String THE_KNOWLEDGE_TREE_STATS = getLocalString("title");
    public static final String ROOT_NODE = getLocalString("root");
    public static final String TOTAL_NUMBER_OF_NODES = getLocalString("nodes");
    public static final String TOTAL_NUMBER_OF_ANIMALS = getLocalString("animals");
    public static final String TOTAL_NUMBER_OF_STATEMENTS = getLocalString("statements");
    public static final String HEIGHT_OF_THE_TREE = getLocalString("height");
    public static final String MINIMUM_DEPTH = getLocalString("minimum");
    public static final String AVERAGE_DEPTH = getLocalString("average");
    LanguageRule lr = getLanguageRule();

    private LanguageRule getLanguageRule() {
        return System.getProperty("user.language").equals("eo") ? new LanguageRule_eo() : new LanguageRule_en();
    }

    Scanner scanner = new Scanner(System.in);
    private final Map<String, List<String>> animals = new HashMap<>();

    public void printAnimals(Node root) {
        out.println(getLocalString("list.animals"));
        getAnimals(root).keySet().stream()
                .sorted()
                .map(name -> " - " + name)
                .forEach(out::println);
    }

    public void printTree(Node root) {
        out.println();
        printNode(root, false, " ");
    }

    public void printNode(Node node, boolean isYes, String prefix) {
        final String symbol;
        symbol = isYes ? "├" : "└";
        if (node.isLeaf()) {
            out.printf("%s%s %s%n", prefix, symbol, node.getData());
            return;
        }
        out.printf("%s%s %s%n", prefix, symbol, node.getData());
        prefix += isYes ? "│" : " ";
        printNode(node.getYes(), true, prefix);
        printNode(node.getNo(), false, prefix);
    }


    public void printStatistics(Node root) {
        final var stats = getAnimals(root).values().stream()
                .mapToInt(List::size).summaryStatistics();

        out.printf("%-30s %n%n", THE_KNOWLEDGE_TREE_STATS);
        out.printf("%-30s %s%n", ROOT_NODE, lr.positiveFact(root.getData()));
        out.printf("%-30s %d%n", TOTAL_NUMBER_OF_NODES, stats.getCount() * 2 - 1);
        out.printf("%-30s %d%n", TOTAL_NUMBER_OF_ANIMALS, stats.getCount());
        out.printf("%-30s %d%n", TOTAL_NUMBER_OF_STATEMENTS, stats.getCount() - 1);
        out.printf("%-30s %d%n", HEIGHT_OF_THE_TREE, stats.getMax());
        out.printf("%-30s %d%n", MINIMUM_DEPTH, stats.getMin());
        out.printf("%-30s %.1f%n", AVERAGE_DEPTH, stats.getAverage());
    }


    public Map<String, List<String>> getAnimals(Node root) {
        animals.clear();
        collectAnimals(root, new LinkedList<>());
        return animals;
    }


    public void searchAnimal(Node root) {
        out.println(getLocalString("search.animal"));
        final var animal = lr.getName(scanner.nextLine());
        final var animals = getAnimals(root);
        if (animals.containsKey(animal)) {
            printFactAbout(animal);
            final var facts = animals.get(animal);
            facts.forEach(fact -> out.println(" - " + fact));
        } else {
            out.println(getLocalString("search.not_found"));
        }
    }


    private void printFactAbout(String animal) {
        var result = MessageFormat
                .format(getLocalString("search.facts"), animal);
        out.println(result);
    }

    private void collectAnimals(final Node node, final Deque<String> facts) {
        if (node.isLeaf()) {
            animals.put(lr.getName(node.getData()), List.copyOf(facts));
            return;
        }
        final var statement = node.getData();
        facts.add(lr.positiveFact(statement));
        collectAnimals(node.getYes(), facts);
        facts.removeLast();
        facts.add(lr.negativeFact(statement));
        collectAnimals(node.getNo(), facts);
        facts.removeLast();
    }
}