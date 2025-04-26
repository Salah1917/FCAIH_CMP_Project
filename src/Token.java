public class Token {
    int lineCount;
    String text;
    String type;

    public Token(int lineCount, String text, String type) {
        this.lineCount = lineCount;
        this.text = text;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Line: " + lineCount + " Token Text: " + text + "\t Token Type: " + type;
    }
}