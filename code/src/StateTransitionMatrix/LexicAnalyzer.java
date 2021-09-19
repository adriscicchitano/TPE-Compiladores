package StateTransitionMatrix;

import SemanticAction.SemanticAction;
import Structures.Buffer;
import Structures.CharSet;
import Structures.SymbolsTable;
import Structures.Token;
import Text.Text;
import Text.TokenizedText;

public class LexicAnalyzer {
    private final int FINAL_STATE = 19;
    private final int START_STATE = 0;

    private CharSet[] charSet;
    private int[][] states;
    private SemanticAction[][] semanticActions;
    private StructureUtilities su;
    private Text code;

    public LexicAnalyzer(int[][] states, CharSet[] charSet, SemanticAction[][] semanticActions, Text code){
        this.states = states;
        this.semanticActions = semanticActions;
        this.charSet = charSet;
        this.code = code;
        this.su = new StructureUtilities(code);
    }

    private int searchColumnIndex(char c){
        for(int i = 0; i < charSet.length; i++){
            if(charSet[i].contains(c))
                return i;
        }
        return charSet.length - 1;
    }

    /*
    private void analyze(){
        int currentState = START_STATE;
        char character = 0;

        while(character != '$' && !code.isEmpty()){
            if(currentState != FINAL_STATE) {
                character = code.getNextChar();
                int columnIndex = searchColumnIndex(character);
                if (semanticActions[currentState][columnIndex] != null)
                    semanticActions[currentState][columnIndex].execute(su, character);
                currentState = states[currentState][columnIndex];
            }else currentState = START_STATE;
        }


        System.out.println(su.showTokens());
        System.out.println("---------------------------------------");
        System.out.println(su.showErrors());
        System.out.println("---------------------------------------");
        System.out.println(su.showWarnings());
        System.out.println("---------------------------------------");
        System.out.println(su.showSymbolsTable());


    }
    */

    public String requestToken(){
        int currentState = START_STATE;
        char character = 0;
        Token token = new Token();

        if(!code.isEmpty()) {
            while (currentState != FINAL_STATE) {
                character = code.getNextChar();
                int columnIndex = searchColumnIndex(character);
                if (semanticActions[currentState][columnIndex] != null)
                    semanticActions[currentState][columnIndex].execute(su, character,token);
                currentState = states[currentState][columnIndex];
            }
        }else{
            return null;
        }
        return token.getToken();

    }

}
