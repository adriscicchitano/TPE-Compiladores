package Text;

public class TokenizedText {
    private String tokens = "";

    public TokenizedText(){}

    public void addToken(String s){
        tokens += s + '\n';
    }

    public String toString(){
        return this.tokens;
    }
}
