package Structures;

import Utils.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class SymbolsTable {
    private HashMap<String,String> symbolsTable;
    private final String[] reservedWords = {"IF", "THEN", "ELSE", "ENDIF", "PRINT",
                                            "FUNC", "RETURN", "BEGIN", "END", "BREAK",
                                            "UINT", "DOUBLE", "STRING", "WHILE", "DO", "PRE"};

    public SymbolsTable(){
        this.symbolsTable = new HashMap<>();
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

    public void setToNegative(String key){
        String previousValue = this.symbolsTable.get(key);
        this.symbolsTable.remove(key);
        this.symbolsTable.put("-"+key,previousValue);
    }

    public String toString(){
        return this.symbolsTable.toString();
    }
}
