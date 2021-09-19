package Text;

import java.util.ArrayList;
import java.util.List;

public class TokenizedText {
    private List<String> tokens;
    private int index = 0;

    public TokenizedText(){
        this.tokens = new ArrayList<>();
    }

    public void addToken(String s) {
        tokens.add(s);
    }

    public String toString(){
        String result = "";
        for(String s : this.tokens)
            result += s + '\n';
        return result;
    }

    public String nextToken(){
        String aux = tokens.get(index);
        index++;
        return aux;
    }

    public boolean hasMoreTokens(){
        return index < this.tokens.size();
    }
}
