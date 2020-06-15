package flashcards;
 
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
 
public class Main {
 
    //input the action
    public static void input_action(Map cards) {
        Scanner input_action_scanner = new Scanner(System.in);
 
        System.out.println("Input the action (add, remove, import, export, ask, exit):");
        System.out.print("> ");
        String action_string = input_action_scanner.next();
        if (action_string.equals("add")) {
            add(cards);
        } else if (action_string.equals("remove")) {
            remove(cards);
        } else if (action_string.equals("import")) {
            import_cards(cards);
        } else if (action_string.equals("export")) {
            export_cards(cards);
        } else if (action_string.equals("ask")) {
            ask(cards);
        } else if (action_string.equals("exit")) {
            exit();
        }
    }
 
    //add
    public static void add (Map cards) {
        Scanner add_name_scanner = new Scanner(System.in);
        Scanner add_def_scanner = new Scanner(System.in);
 
        System.out.println("The card:");
        System.out.print("> ");
        String card_name = add_name_scanner.nextLine();
 
        if (cards.containsKey(card_name)) {
            System.out.println("The card \"" + card_name + "\" already exists.");
            input_action (cards);
 
        } else {
            System.out.println("The definition of the card:");
            System.out.print("> ");
        }
 
        String card_def = add_def_scanner.nextLine();
 
        if (cards.containsValue(card_def)) {
            System.out.println("The definition \"" + card_def + "\" already exists.");
            input_action (cards);
 
        } else {
 
            cards.put(card_name, card_def);
            System.out.println("The pair (\"" + card_name + "\":\"" + card_def + "\") has been added.");
            input_action (cards);
        }
 
    }
 
    //remove
    public static void remove (Map cards) {
        Scanner remove_scanner = new Scanner(System.in);
 
        System.out.println("The card:");
        System.out.print("> ");
        String card_name = remove_scanner.nextLine();
        if (cards.containsKey(card_name)) {
            cards.remove(card_name);
            System.out.println("The card has been removed.");
        } else {
            System.out.println("Can't remove \"" + card_name + "\": there is no such card.");
        }
 
        input_action(cards);
    }
 
    //import
    public static void import_cards (Map cards) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("File name:");
        System.out.print("> ");
        String import_file = scanner.nextLine();
        int counter = 0;
        try {
            Scanner import_scanner = new Scanner(new File(import_file));
            while (import_scanner.hasNextLine()) {
                cards.put(import_scanner.nextLine(), import_scanner.nextLine());
                counter++;
            }
            System.out.println(counter + " cards have been loaded.");
            input_action(cards);
 
        } catch (IOException e) {
            System.out.println("File not found.");
            input_action(cards);
        }
    }
 
    //export
    public static void export_cards (Map cards) {
        Scanner export_scanner = new Scanner(System.in);
 
        System.out.println("File name:");
        System.out.print("> ");
        String export_file = export_scanner.nextLine();
        File file = new File(export_file);
        int counter = 0;
        try (PrintWriter printWriter = new PrintWriter(file)) {
            Iterator exportIterator = cards.entrySet().iterator();
 
            while (exportIterator.hasNext()) {
                Map.Entry mapElement = (Map.Entry) exportIterator.next();
                printWriter.println(mapElement.getKey() + "\n" + mapElement.getValue());
                counter++;
            }
        } catch (Exception e) {
            input_action(cards);
        }
        System.out.println(counter + " cards have been saved.");
        input_action(cards);
    }
 
    //ask
    public static void ask (Map cards) {
 
        Scanner ask_scanner = new Scanner(System.in);
        Set<String> keySet = cards.keySet();
        List<String> keyList = new ArrayList<>(keySet);
 
        int size = keyList.size();
        int randIdx = new Random().nextInt(size);
 
        String randomKey = keyList.get(randIdx);
        String randomValue = (String) cards.get(randomKey);
 
        System.out.println("How many times to ask?");
        System.out.print("> ");
        int number = ask_scanner.nextInt();
        ask_scanner.nextLine();
 
        for (int i = 1; i <= number; i++) {
 
            System.out.println("Print the definition of \"" + randomKey + "\":");
            System.out.print("> ");
            String def = ask_scanner.nextLine();
 
            if (def.equals(randomValue)) {
                System.out.println("Correct answer.");
            } else if (cards.containsValue(def)) {
                System.out.println("Wrong answer. The correct one is \"" + randomValue + "\", you've just written the definition of \"" + getKeyFromValue(cards, def) + "\".");
            } else {
                System.out.println("Wrong answer. The correct one is \"" + randomValue + "\".");
            }
        }
        input_action (cards);
    }
 
    //exit
    public static void exit() {
        System.out.println("Bye bye!");
        System.exit(0);
    }
 
    //get key from value
    public static Object getKeyFromValue(Map cards, Object value) {
        for (Object o : cards.keySet()) {
            if (cards.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }
 
    public static void main(String[] args) {
        Map<String, String> cards = new LinkedHashMap<>();
        input_action (cards);
    }
}
