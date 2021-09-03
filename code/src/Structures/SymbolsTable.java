package Structures;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class SymbolsTable {
    private HashMap<String,String> symbolsTable;
    private final String[] reservedWords = {"IF", "THEN", "ELSE", "ENDIF", "PRINT",
                                            "FUNC", "RETURN", "BEGIN", "END", "BREAK",
                                            "UINT", "DOUBLE", "WHILE", "DO"};

    public SymbolsTable(){
        this.symbolsTable = new HashMap<String,String>();
    }

    public void addSymbols(String key, String value){
        if(!this.symbolsTable.containsKey(key))
            this.symbolsTable.put(key, value);
    }

    public boolean contains(String key){
        return this.symbolsTable.containsKey(key);
    }

    public boolean isReservedWord(String word){
        for(int i = 0; i < reservedWords.length; i++){
            if(reservedWords[i].equals(word))
                return true;
        }
        return false;
    }

    public String toString(){
        return this.symbolsTable.toString();
    }
}
