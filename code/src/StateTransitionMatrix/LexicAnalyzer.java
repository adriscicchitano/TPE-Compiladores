package StateTransitionMatrix;

import SemanticAction.SemanticAction;
import Structures.*;
import Text.Text;
import SemanticAction.SemanticAction;
import SemanticAction.AddCharacterToBuffer;
import SemanticAction.AddError;
import SemanticAction.AddToSymbolsTable;
import SemanticAction.AddTokenFromBuffer;
import SemanticAction.AddTokenFromChar;
import SemanticAction.CompoundActions;
import SemanticAction.InitializeBuffer;
import SemanticAction.RemoveLastCharFromBuffer;
import SemanticAction.ReturnCharToText;
import SemanticAction.CheckRange;
import Text.TokenizedText;

public class LexicAnalyzer {
    private final int FINAL_STATE = 19;
    private final int START_STATE = 0;

    private SemanticAction[][] semanticActionsInitializer() {
        SemanticAction init = new InitializeBuffer();
        SemanticAction addToBuffer = new AddCharacterToBuffer();
        SemanticAction addTokenChar = new AddTokenFromChar();
        SemanticAction removeLastCharFromBuffer = new RemoveLastCharFromBuffer();
        SemanticAction returnCharToText = new ReturnCharToText();
        SemanticAction searchInSymbolsTable = new AddToSymbolsTable();

        SemanticAction as_0 = new CompoundActions(init, addToBuffer);
        SemanticAction as_1 = new CompoundActions(as_0, addTokenChar);
        SemanticAction as_2 = new CompoundActions(returnCharToText, new AddTokenFromBuffer(""));
        SemanticAction as_3 = new CompoundActions(addToBuffer, new AddTokenFromBuffer(""));
        SemanticAction as_4 = returnCharToText; //CUANDO SE USE as_4 en la matriz, se debe hacer: new CompoundActions(as,4,new AddError("este es el error"));
        SemanticAction as_4_1 = new CompoundActions(as_4, new AddError("El caracter . debe ser acompañado de un numero para formar un double"));
        SemanticAction as_4_2 = new CompoundActions(as_4, new AddError("El caracter E debe ser acompañado de un numero, un + o un - para formar un double"));
        SemanticAction as_4_3 = new CompoundActions(as_4, new AddError("El exponente con signo debe ser acompañado de un numero"));
        SemanticAction as_4_4 = new CompoundActions(as_4, new AddError("El caracter & debe ser acompañado de otro caracter &"));
        SemanticAction as_4_5 = new CompoundActions(as_4, new AddError("El caracter | debe ser acompañado de otro caracter |"));
        SemanticAction as_4_6 = new CompoundActions(as_4, new AddError("No puede haber un salto de linea en la cadena de caracteres sin un + anteriormente"));
        SemanticAction as_4_7 = new CompoundActions(as_4, new AddError("No puede terminar el archivo sin cerrar la cadena de caracteres"));
        SemanticAction as_4_8 = new CompoundActions(as_4, new AddError("Luego de un salto de linea no puede venir otro caracter que no sea un + u otro salto de linea"));
        SemanticAction as_4_9 = new CompoundActions(as_4, new AddError("El caracter = debe ser acompañado de otro caracter = "));
        SemanticAction as_5 = addToBuffer;
        SemanticAction as_6 = removeLastCharFromBuffer;
        SemanticAction as_7 = new CompoundActions(returnCharToText, searchInSymbolsTable);
        SemanticAction as_8 = new CompoundActions(returnCharToText, new CheckRange("UINT"));
        SemanticAction as_9 = new CompoundActions(returnCharToText, new CheckRange("DOUBLE"));
        SemanticAction as_10 = init;
        SemanticAction as_11 = new AddTokenFromBuffer("STRING");

        SemanticAction[][] result = {
                {as_0, as_0, as_0, as_0, as_1, as_0, as_0, as_0, as_0, as_0, as_0, as_0, as_10, as_1, as_1, null, null, as_0, null, null},                                                          //0
                {as_5, as_5, as_5, as_7, as_7, as_7, as_7, as_7, as_7, as_7, as_7, as_7, as_7, as_7, as_7, as_7, as_7, as_5, as_7, as_7},                                                          //1
                {as_8, as_5, as_8, as_5, as_8, as_8, as_8, as_8, as_8, as_8, as_8, as_8, as_8, as_8, as_8, as_8, as_8, as_8, as_8, as_8},                                                          //2
                {as_4_1, as_5, as_4_1, as_4_1, as_4_1, as_4_1, as_4_1, as_4_1, as_4_1, as_4_1, as_4_1, as_4_1, as_4_1, as_4_1, as_4_1, as_4_1, as_4_1, as_4_1, as_4_1, as_4_1},                    //3
                {as_9, as_5, as_9, as_9, as_9, as_9, as_9, as_9, as_9, as_9, as_9, as_9, as_9, as_9, as_9, as_9, as_9, as_5, as_9, as_9},                                                          //4
                {as_4_2, as_5, as_4_2, as_4_2, as_4_2, as_4_2, as_4_2, as_4_2, as_4_2, as_4_2, as_4_2, as_4_2, as_4_2, as_5, as_5, as_4_2, as_4_2, as_4_2, as_4_2, as_4_2},                        //5
                {as_4_3, as_5, as_4_3, as_4_3, as_4_3, as_4_3, as_4_3, as_4_3, as_4_3, as_4_3, as_4_3, as_4_3, as_4_3, as_4_3, as_4_3, as_4_3, as_4_3, as_4_3, as_4_3, as_4_3},                    //6
                {as_9, as_5, as_9, as_9, as_9, as_9, as_9, as_9, as_9, as_9, as_9, as_9, as_9, as_9, as_9, as_9, as_9, as_9, as_9, as_9},                                                          //7
                {as_2, as_2, as_2, as_2, as_2, null, as_2, as_2, as_2, as_2, as_2, as_2, as_2, as_2, as_2, as_2, as_2, as_2, as_2, as_2},                                                          //8
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},                                                          //9
                {as_2, as_2, as_2, as_2, as_2, as_2, as_2, as_3, as_3, as_2, as_2, as_2, as_2, as_2, as_2, as_2, as_2, as_2, as_2, as_2},                                                          //10
                {as_2, as_2, as_2, as_2, as_2, as_2, as_2, as_2, as_3, as_2, as_2, as_2, as_2, as_2, as_2, as_2, as_2, as_2, as_2, as_2},                                                          //11
                {as_4_4, as_4_4, as_4_4, as_4_4, as_4_4, as_4_4, as_4_4, as_4_4, as_4_4, as_4_4, as_3, as_4_4, as_4_4, as_4_4, as_4_4, as_4_4, as_4_4, as_4_4, as_4_4, as_4_4},                    //12
                {as_4_5, as_4_5, as_4_5, as_4_5, as_4_5, as_4_5, as_4_5, as_4_5, as_4_5, as_4_5, as_4_5, as_3, as_4_5, as_4_5, as_4_5, as_4_5, as_4_5, as_4_5, as_4_5, as_4_5},                    //13
                {as_5, as_5, as_5, as_5, as_5, as_5, as_5, as_5, as_5, as_5, as_5, as_5, as_11, as_5, as_5, as_4_6, as_5, as_5, as_5, as_4_7},                                                     //14
                {as_5, as_5, as_5, as_5, as_5, as_5, as_5, as_5, as_5, as_5, as_5, as_5, as_3, as_5, as_5, as_6, as_5, as_5, as_5, as_4_7},                                                        //15
                {as_4_8, as_4_8, as_4_8, as_4_8, as_4_8, as_4_8, as_4_8, as_4_8, as_4_8, as_4_8, as_4_8, as_4_8, as_4_8, null, as_4_8, null, null, as_4_8, as_4_8, as_4_8},                        //16
                {as_2, as_2, as_2, as_2, as_2, as_2, as_2, as_2, as_3, as_2, as_2, as_2, as_2, as_2, as_2, as_2, as_2, as_2, as_2, as_2},                                                          //17
                {as_4_9, as_4_9, as_4_9, as_4_9, as_4_9, as_4_9, as_4_9, as_4_9, as_3, as_4_9, as_4_9, as_4_9, as_4_9, as_4_9, as_4_9, as_4_9, as_4_9, as_4_9, as_4_9, as_4_9},                    //18
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}                                                           //F
        };

        return result;
    }

    private CharSet[] charSet = {
            new CharSet('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
                    'A','B','C','D','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'),
            new CharSet('0','1','2','3','4','5','6','7','8','9'),
            new CharSet('_'),
            new CharSet('.'),
            new CharSet('/','(',')',',',';'),
            new CharSet('*'),
            new CharSet('<'),
            new CharSet('>'),
            new CharSet('='),
            new CharSet(':'),
            new CharSet('&'),
            new CharSet('|'),
            new CharSet('%'),
            new CharSet('+'),
            new CharSet('-'),
            new CharSet('\n'),
            new CharSet('\t',' '),
            new CharSet('E'),
            new CharSet('$')
    };
    private int[][] states = {
            {1,2,1,3,19,8,10,17,18,11,12,13,14,19,19,0,0,1,19,19},          //0
            {1,1,1,19,19,19,19,19,19,19,19,19,19,19,19,19,19,1,19,19},     //1
            {19,2,19,4,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19},    //2
            {19,4,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19},   //3
            {19,4,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,5,19,19},    //4
            {19,7,19,19,19,19,19,19,19,19,19,19,19,6,6,19,19,19,19,19},     //5
            {19,7,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19},   //6
            {19,7,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19},   //7
            {19,19,19,19,19,9,19,19,19,19,19,19,19,19,19,19,19,19,19,19},   //8
            {9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,0,9,9,9,19},                     //9
            {19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19},  //10
            {19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19},  //11
            {19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19},  //12
            {19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19},  //13
            {14,14,14,14,14,14,14,14,14,14,14,14,19,15,14,19,14,14,14,19},  //14
            {14,14,14,14,14,14,14,14,14,14,14,14,14,15,14,16,14,14,14,19},  //15
            {19,19,19,19,19,19,19,19,19,19,19,19,19,14,19,16,16,19,19,19},  //16
            {19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19},  //17
            {19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19},  //18
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,19}                      //F
    };
    private SemanticAction[][] semanticActions;
    private StructureUtilities su;
    private Text code;
    private Token currentToken = new Token();

    public LexicAnalyzer(Text code){
        this.code = code;
        this.su = new StructureUtilities(code);
        this.semanticActions = semanticActionsInitializer();
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
        if(character == '$')
            return null;
        else {
            return token.getToken() + token.getSymbolsTableReference();
        }

    }

    public int yylex(){
        int currentState = START_STATE;
        char character = 0;

        if(!code.isEmpty()) {
            while (currentState != FINAL_STATE) {
                character = code.getNextChar();
                int columnIndex = searchColumnIndex(character);
                if (semanticActions[currentState][columnIndex] != null)
                    semanticActions[currentState][columnIndex].execute(su, character,currentToken);
                currentState = states[currentState][columnIndex];
            }
        }else{
            return 0;
        }
        if(character == '$')
            return 0;
        else
            return convertToCode(currentToken.getToken());

    }

    private int convertToCode(String token) {
        if(token.length() == 1)
            return token.charAt(0);
        else
            return TerminalsCodes.getCode(token);
    }

    public int getCurrentLine(){
        return this.code.getCurrentLine();
    }

    public String getSymbolsTableReference(){
        return this.currentToken.getSymbolsTableReference();
    }
}
