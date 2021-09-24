package StateTransitionMatrix;

import Structures.Buffer;
import Structures.SymbolsTable;
import Structures.Token;
import Text.Text;
import Text.TokenizedText;

public class StructureUtilities {
    private final int UINT_MAX = 65535;
    private final double DOUBLE_MIN = 2.2250738585072014E-308;
    private final double DOUBLE_MAX = 1.7976931348623157E+308;

    private Buffer buffer;
    private SymbolsTable symbolsTable;
    private TokenizedText tokenizedText;
    private TokenizedText errors;
    private TokenizedText warnings;
    private Text text;

    public StructureUtilities(Text text) {
        this.buffer = new Buffer();
        this.symbolsTable = new SymbolsTable();
        this.tokenizedText = new TokenizedText();
        this.errors = new TokenizedText();
        this.warnings = new TokenizedText();
        this.text = text;
    }

    public void addCharToBuffer(char c) {
        buffer.addCharacter(c);
    }

    public void emptyBuffer() {
        buffer.emptyBuffer();
    }

    public void returnCharToText() {
        text.returnChar();
    }

    public void addToken(String s,Token result) {
        if(!s.equals("")) {
            addSymbolToTable(buffer.toString(), "STRING");
            result.setToken(s);
            result.setSymbolsTableReference(buffer.toString());
        }
        else{
            result.setToken(buffer.toString());
            result.setSymbolsTableReference(null);
        }
    }

    public void addToken(Character c,Token result) {
        result.setToken(c.toString());
        result.setSymbolsTableReference(null);
    }

    public void removeLastCharFromBuffer() {
        buffer.removeLastChar();
    }

    public void addError(String error) {
        errors.addToken("Linea " + text.getCurrentLine() + ": " + error);
    }

    public void searchInSymbolsTable(Token result) {
        if (symbolsTable.isReservedWord(buffer.toString().toUpperCase())) {
            result.setToken(buffer.toString().toUpperCase());
            result.setSymbolsTableReference(null);
        }
        else {
            result.setToken("ID");
            if (buffer.bufferSize() > 22) {
                warnings.addToken("Linea " + text.getCurrentLine() + ": Se trunca el nombre del ID, la longitud maxima es 22");
                addSymbolToTable(buffer.toString().substring(0, 22), "ID");
                result.setSymbolsTableReference(buffer.toString().substring(0, 22));
            } else {
                addSymbolToTable(buffer.toString(), "ID");
                result.setSymbolsTableReference(buffer.toString());
            }
        }

    }

    private void addSymbolToTable(String key, String value){
        this.symbolsTable.addSymbols(key,value);
    }

    public void CheckRangeAndAddToken(String type,Token token) {
        if (type.equals("UINT")) {
            Integer aux = Integer.parseInt(buffer.toString());
            if (aux > UINT_MAX) {
                errors.addToken("Linea " + text.getCurrentLine() + ": Constante UINT fuera de rango");
            } else {
                addSymbolToTable(aux.toString(), "UINT");
                token.setToken("CTE_UINT");
                token.setSymbolsTableReference(aux.toString());
            }
        }
        if(type.equals("DOUBLE")){
            Double aux = Double.parseDouble(buffer.toString());
            if(aux < DOUBLE_MIN || aux > DOUBLE_MAX){
                errors.addToken("Linea " + text.getCurrentLine() + ": Constante DOUBLE fuera de rango");
            }else{
                addSymbolToTable(aux.toString(), "DOUBLE");
                token.setToken("CTE_DOUBLE");
                token.setSymbolsTableReference(aux.toString());
            }
        }
    }

    public String showTokens() {
        return tokenizedText.toString();
    }

    public String showErrors() {
        return errors.toString();
    }

    public String showWarnings(){
        return warnings.toString();
    }

    public String showSymbolsTable() {
        return symbolsTable.toString();
    }

}

