package StateTransitionMatrix;

import Structures.Buffer;
import Structures.SymbolsTable;
import Structures.Token;
import Text.Text;
import Text.TokenizedText;
import Utils.utils;

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
            result.setToken(s,this.text.getCurrentLine());
            result.setSymbolsTableReference(buffer.toString());
        }
        else{
            result.setToken(buffer.toString(),this.text.getCurrentLine());
            result.setSymbolsTableReference(null);
        }
    }

    public void addToken(Character c,Token result) {
        result.setToken(c.toString(),this.text.getCurrentLine());
        result.setSymbolsTableReference(null);
    }

    public void removeLastCharFromBuffer() {
        buffer.removeLastChar();
    }

    public void addError(String error) {
        errors.addToken("Linea " + text.getCurrentLine() + ": " + error);
        System.err.println("Linea " + text.getCurrentLine() + ": " + error);
    }

    public void addWarning(String warning){
        warnings.addToken("Linea " + text.getCurrentLine() + ": " + warning);
    }


    public void searchInSymbolsTable(Token result) {
        if (symbolsTable.isReservedWord(buffer.toString().toUpperCase())) {
            result.setToken(buffer.toString().toUpperCase(),this.text.getCurrentLine());
            result.setSymbolsTableReference(null);
        }
        else {
            result.setToken("ID",this.text.getCurrentLine());
            if (buffer.bufferSize() > 22) {
                addWarning("Se trunca el nombre del ID, la longitud maxima es 22");
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
            if (!utils.checkUINTRange(buffer.toString())) {
                addError("Constante UINT fuera de rango");
            } else {
                addSymbolToTable(buffer.toString(), "UINT");
                token.setToken("CTE_UINT", this.text.getCurrentLine());
                token.setSymbolsTableReference(buffer.toString());
            }
        }
        if (type.equals("DOUBLE")) {
            if (!utils.checkDOUBLERange(buffer.toString())) {
                addError("Constante DOUBLE fuera de rango");
            } else {
                addSymbolToTable(buffer.toString(), "DOUBLE");
                token.setToken("CTE_DOUBLE", this.text.getCurrentLine());
                token.setSymbolsTableReference(buffer.toString());
            }
        }
    }

    public void setToNegative(String constant){

        if(utils.checkDOUBLERange("-"+constant)){
            this.symbolsTable.setToNegative(constant);
        }
        else{
            this.addError("La constante UINT se va de rango al cambiarse a negativo");
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

