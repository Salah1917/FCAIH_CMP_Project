import java.io.*;
import java.util.*;

public class Main {
    private static final String file = "C://Users//pc//Downloads//Directory/Compilers.txt";

    private static final Map<String, String> keywords = new HashMap<>();

    static {
        keywords.put("Program", "Start Statement");
        keywords.put("Division", "Class");
        keywords.put("InferedFrom", "Inheritence");
        keywords.put("WhetherDo", "Condition");
        keywords.put("Ire", "Integer");
        keywords.put("Sire", "SInteger");
        keywords.put("Clo", "Character");
        keywords.put("SetOfClo", "String");
        keywords.put("FBU", "Float");
        keywords.put("SFBU", "SFloat");
        keywords.put("None", "Void");
        keywords.put("Logical", "Boolean");
        keywords.put("TerminateThis", "Break");
        keywords.put("Rotatewhen", "Loop");
        keywords.put("Continuewhen", "Loop");
        keywords.put("Replywith", "Return");
        keywords.put("Seop", "Struct");
        keywords.put("Check", "Switch");
        keywords.put("situationof", "Switch");
        keywords.put("Using", "Inclusion");
        keywords.put("End", "End Statement");
    }

    private static final Map<String, String> sentences = new HashMap<>();

    static {
        sentences.put("Start Statement", "Program"); //Program
        sentences.put("Class Identifier OpenBrace", "ClassDeclaration");// className x {
        sentences.put("Integer Identifier AssignmentOperator Constant Semicolon", "VarDeclaration");//int x = 3;
        sentences.put("Loop OpenParen Identifier RelationalOperator Identifier Semicolon Identifier Semicolon Constant CloseParen OpenBrace", "ContinueWhenStatement");//for (i < x; x; 5){

    }

    public static void main(String[] args) throws IOException {
        List<Token> tokens = new ArrayList<>();
        List<List<String>> allTokenTypesPerLine = new ArrayList<>();
        List<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line;
        int lineCount = 1, tokenErrorCount = 0, grammarErrorCount = 0;

        // Tokenization: Lexical Analysis
        while ((line = reader.readLine()) != null) {
            lines.add(line);
            String[] words = line.trim().split("\\s+|(?=[{}();=,+\\-*/])|(?<=[{}();=,+\\-*/])");
            List<String> tokenTypesForLine = new ArrayList<>();

            for (String word : words) {
                String type;
                if (word.isBlank()) {
                    continue;
                } else {
                    type = identifyToken(word);
                }

                if (type.equals("Unknown")) {
                    System.out.println("Line: " + lineCount + " Error in Token text: " + word);
                    tokenErrorCount++;
                } else {
                    tokens.add(new Token(lineCount, word, type));
                    tokenTypesForLine.add(type);
                }
            }

            allTokenTypesPerLine.add(tokenTypesForLine);
            lineCount++;
        }

        // Print all tokens using toString method(override)
        for (Token token : tokens) {
            System.out.println(token);
        }

        // Parser: Grammar Analysis
        for (int i = 0; i < allTokenTypesPerLine.size(); i++) {
            List<String> tokenTypes = allTokenTypesPerLine.get(i);
            if (!tokenTypes.isEmpty()) {
                String sentenceKey = String.join(" ", tokenTypes);
                System.out.println("Line " + (i + 1) + ": Sentence Analysis: " + sentenceKey);

                if (sentences.containsKey(sentenceKey)) {
                    System.out.println("Grammar Match: " + sentences.get(sentenceKey));
                } else {
                    System.out.println("Grammar Match: [No matching rule found]");
                    grammarErrorCount++;
                }
            }
        }

        //print final error counts
        System.out.println("Total number of token errors: " + tokenErrorCount);
        System.out.println("Total number of grammar errors: " + grammarErrorCount);
    }

    //use the hashmap and then the other stuff, if it doesn't match then unknown
    private static String identifyToken(String word) {
        if (keywords.containsKey(word)) {
            return keywords.get(word);
        } else if (word.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
            return "Identifier";
        } else if (word.matches("[0-9]+")) {
            return "Constant";
        } else {
            switch (word) {
                case "{": return "OpenBrace";
                case "}": return "CloseBrace";
                case "(": return "OpenParen";
                case ")": return "CloseParen";
                case ";": return "Semicolon";
                case ",": return "Comma";
                case "=": return "AssignmentOperator";
                case "+": case "-": return "AddOp";
                case "*": case "/": return "MulOp";
                case "==": case "!=": case ">": case "<": case ">=": case "<=":
                    return "RelationalOperator";
                case "&&": case "||": case "~":
                    return "LogicalOperator";
                case ".": return "Dot";
                case "txt": return "txt";
                default: return "Unknown";
            }
        }
    }
}

