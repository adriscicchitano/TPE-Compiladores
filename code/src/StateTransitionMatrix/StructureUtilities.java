package StateTransitionMatrix;

import Structures.Buffer;
import Structures.SymbolTableValue;
import Structures.SymbolsTable;
import Structures.Token;
import Text.Text;
import Utils.utils;

import java.util.ArrayList;
import java.util.Arrays;
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
            if(s.equals("CTE_STRING"))
                addSymbolToTable(buffer.toString(), "CTE_STRING");
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

    public void addError(String error, String stage) {
        errors.add(stage + " - Linea " + getCurrentLine() + ": " + error);
    }

    public void addWarning(String warning){
        warnings.add("Linea " + getCurrentLine() + ": " + warning);
    }

    public void addCodeStructure(String s){
        this.codeStructures.add("Linea " + getCurrentLine() + ": " + s);
    }

    public void searchInSymbolsTable(Token result) {
        if (symbolsTable.isReservedWord(buffer.toString())) {
            result.setToken(buffer.toString(),this.text.getCurrentLine());
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
                addError("Constante UINT fuera de rango", "Lexico");
            } else {
                addSymbolToTable(buffer.toString(), "CTE_UINT");
                token.setToken("CTE_UINT", this.text.getCurrentLine());
                token.setSymbolsTableReference(buffer.toString());
            }
        }
        if (type.equals("DOUBLE")) {
            if (!utils.checkDOUBLERange(buffer.toString())) {
                addError("Constante DOUBLE fuera de rango", "Lexico");
            } else {
                addSymbolToTable(buffer.toString(), "CTE_DOUBLE");
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
            this.addError("La constante UINT se va de rango al cambiarse a negativo", "Sintactico");
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

    public boolean hasErrors(){
        return this.errors.size() > 0;
    }

    public void changeSTValues(String key, String type, String use){
        this.symbolsTable.changeValue(key, type, use);
    }

    public void changeSTValues(String key, String type, String use, String parameterType){
        this.symbolsTable.changeValue(key, type, use, parameterType);
    }

    public void changeSTValues(List<String> keys, String type, String use){
        for(String key : keys)
            this.symbolsTable.changeValue(key, type, use);
    }

    public void changeSTValues(List<String> keys, String type, String use, String parameterType){
        for(String key : keys)
            this.symbolsTable.changeValue(key, type, use, parameterType);
    }

    public void changeSTKey(String formerKey, String newKey){
        SymbolTableValue v = this.symbolsTable.remove(formerKey);
        if(!this.symbolsTable.contains(newKey))
            this.symbolsTable.addSymbols(newKey,v);
        else
            this.addError("Redeclaracion de variable " + formerKey,"Semantica");
    }

    public void changeSTKeys(List<String> formerKeys, List<String> newKeys){
        if(formerKeys.size() != newKeys.size()) {
            System.err.println("changeSTKeys - No hay la misma cantidad de claves");
            return;
        }
        int i = 0;
        while (i < formerKeys.size()) {
            changeSTKey(formerKeys.get(i), newKeys.get(i));
            i++;
        }
    }

    public SymbolTableValue getSymbolsTableValue(String key){
        //String[] aux = key.split(":");
        //System.out.println(Arrays.toString(aux));
        SymbolTableValue value = this.symbolsTable.getValue(key);
        if(value != null)
            return value;
        else {
            System.err.println("La clave " + key + " no existe en la tabla de simbolos");
            return null;
        }
    }

    public String searchForKey(String key, String scope){
        //Retorna la clave que existe en la tabla de simbolos dependiendo el ambito
        String[] scopes = scope.split(":");
        for(int i = scopes.length; i > 0; i--){
            String newKey = key+":"+String.join(":",Arrays.copyOfRange(scopes,0,i));
            SymbolTableValue result = getSymbolsTableValue(newKey);
            if(result != null)
                return newKey;
        }
        return null;
    }
}

