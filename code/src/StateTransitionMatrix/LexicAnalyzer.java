package StateTransitionMatrix;

import SemanticAction.SemanticAction;
import Structures.Buffer;
import Structures.CharSet;
import Structures.SymbolsTable;
import Text.Text;
import Text.TokenizedText;

public class LexicAnalyzer {
    private CharSet[] charSet;
    private int[][] states;
    private SemanticAction[][] semanticActions;
    private SymbolsTable symbolsTable;
    private TokenizedText tokenizedText;

    public LexicAnalyzer(int[][] states, CharSet[] charSet, SemanticAction[][] semanticActions){
        this.states = states;
        this.semanticActions = semanticActions;
        this.charSet = charSet;
        this.symbolsTable = new SymbolsTable();
        this.tokenizedText = new TokenizedText();
    }

    private int searchColumnIndex(char c){
        for(int i = 0; i < charSet.length; i++){
            if(charSet[i].contains(c))
                return i;
        }
        return charSet.length - 1;
    }

    public void analyze(Text text){
        int currentState = 0;
        char character = 0;
        Buffer buffer = new Buffer();
        TokenizedText errors = new TokenizedText();

        while(character != '$'){
            character = text.getNextChar();
            int columnIndex = searchColumnIndex(character);
            String token = semanticActions[currentState][columnIndex].execute(buffer,character,this.symbolsTable, errors, text.getCurrentLine());
            if(token != null){
                this.tokenizedText.addToken(token);
            }
            currentState = states[currentState][columnIndex];
        }

        //---------------------------------
        System.out.println(tokenizedText);
        System.out.println(symbolsTable);
        System.out.println(errors);
        //---------------------------------

    }

}
