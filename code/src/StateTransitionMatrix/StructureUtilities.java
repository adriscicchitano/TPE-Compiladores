package StateTransitionMatrix;

import Structures.Buffer;
import Structures.SymbolsTable;
import Structures.Token;
import Text.Text;
import Utils.utils;

import java.util.ArrayList;
import java.util.List;

public class StructureUtilities {

    private Buffer buffer;
    private SymbolsTable symbolsTable;
    private List<Token> tokenizedText;
    private List<String> codeStructures;
    private List<String> errors;
    private List<String> warnings;
    private Text text;

    public StructureUtilities(Text text) {
        this.buffer = new Buffer();
        this.symbolsTable = new SymbolsTable();
        this.tokenizedText = new ArrayList<>();
        this.codeStructures = new ArrayList<>();
        this.errors = new ArrayList<>();
        this.warnings = new ArrayList<>();
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
        this.tokenizedText.add(result.copy());
    }

    public void addToken(Character c,Token result) {
        result.setToken(c.toString(),this.text.getCurrentLine());
        result.setSymbolsTableReference(null);
        this.tokenizedText.add(result.copy());
    }

    public void removeLastCharFromBuffer() {
        buffer.removeLastChar();
    }

    public void addError(String error) {
        errors.add("Linea " + getCurrentLine() + ": " + error);
    }

    public void addWarning(String warning){
        warnings.add("Linea " + getCurrentLine() + ": " + warning);
    }

    public void addCodeStructure(String s){
        this.codeStructures.add("Linea " + getCurrentLine() + ": " + s);
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
        this.tokenizedText.add(result.copy());
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
        this.tokenizedText.add(token.copy());
    }

    public void setToNegative(String constant){

        if(utils.checkDOUBLERange("-"+constant)){
            this.symbolsTable.addSymbols("-"+constant,"DOUBLE");
        }
        else{
            this.addError("La constante UINT se va de rango al cambiarse a negativo");
        }

    }

    private int getCurrentLine(){
        int line = this.text.getCurrentLine();
        if(this.tokenizedText.size() != 0) {
            if (this.tokenizedText.get(this.tokenizedText.size()-1).isDetectedInNewLine())
                return line - 1;
        }
        return line;
    }

    public String showTokens() {
        String result = "";
        for(Token t :  this.tokenizedText){
            result += t.getToken() + '\n';
        }
        return result;
    }

    public String showCodeStructures() {
        return utils.formattedList(codeStructures);
    }

    public String showErrors() {
        return utils.formattedList(errors);
    }

    public String showWarnings(){
        return utils.formattedList(warnings);
    }

    public String showSymbolsTable() {
        return symbolsTable.toString();
    }

}

