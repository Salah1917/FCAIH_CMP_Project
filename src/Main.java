import java.io.*;
import java.util.*;
public class Main {

    public static final String file = "C://Users//pc//Downloads//Directory/Compilers.txt";
    private static final Map<String, String> keywords = new HashMap<>();

    static {
        keywords.put("Program", "Start Statement");
        keywords.put("Division", "Class");
        keywords.put("InferedFrom","Inheritence");
        keywords.put("WhetherDo", "Condition");
        keywords.put("Ire", "Integer");
        keywords.put("Sire", "SInteger");
        keywords.put("Clo","Character");
        keywords.put("SetOfClo","String");
        keywords.put("FBU","Float");
        keywords.put("SFBU","SFloat");
        keywords.put("None","Void");
        keywords.put("Logical","Boolean");
        keywords.put("TerminateThis", "Break");
        keywords.put("Rotatewhen","Loop");
        keywords.put("Continuewhen","Loop");
        keywords.put("Replywith", "Return");
        keywords.put("Seop","Struct");
        keywords.put("Check","Switch");
        keywords.put("situationof","Switch");
        keywords.put("=","Assignment operator");
        keywords.put(".","Access Operator");
        keywords.put("Using","Inclusion");
        keywords.put("End", "End Statement");
    }
    public static void main(String[] args) throws IOException {
        List<Token> tokens = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        int lineCount = 1, errorCount = 0;

        while((line = reader.readLine()) != null) {
            String[] words = line.trim().split("\\s+|(?=[{}();=])|(?<=[{}();=])");

            for (String word : words) {
                String type;
                if (word.isBlank()) {
                    continue;
                } else {
                    type = identifyToken(word);
                }
                if (type.equals("Unknown")) {
                    System.out.println("Line: " + lineCount + " Error in Token text: " + word);
                    errorCount++;
                } else {
                    tokens.add(new Token(lineCount, word, type));
                }
            }
            lineCount++;
        }

            for(Token token : tokens){
                System.out.println(token);
            }
        System.out.println("Total number of errors: " + errorCount);

    }

    private static String identifyToken(String word){
        if(keywords.containsKey(word)){
            return keywords.get(word);
        } else if (word.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
            return "Identifier";
        } else if (word.matches("[0-9]*")) {
            return "constant";
        } else if ("{}();=,".contains(word)) {
            return "Braces";
        } else if (word.equals("+") || word.equals("-") || word.equals("*") || word.equals("/")) {
            return "Arithmetic Operation";
        } else if (word.equals("==") || word.equals("<") || word.equals(">")
                    || word.equals("!=") || word.equals("<=") || word.equals(">=")) {
            return "relational operators";
        } else if (word.equals("&&") || word.equals("||") || word.equals("~")) {
            return "Logical operators";
        } else {
            return "Unknown";
        }
    }
}