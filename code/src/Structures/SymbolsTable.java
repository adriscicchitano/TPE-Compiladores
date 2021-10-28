package Structures;

import Utils.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class SymbolsTable {
    private HashMap<String,SymbolTableValue> symbolsTable;
    private final String[] reservedWords = {"IF", "THEN", "ELSE", "ENDIF", "PRINT",
                                            "FUNC", "RETURN", "BEGIN", "END", "BREAK",
                                            "UINT", "DOUBLE", "STRING", "WHILE", "DO", "PRE"};

    public SymbolsTable(){
        this.symbolsTable = new HashMap<>();
    }

    public void addSymbols(String key, String type){
        if(!this.symbolsTable.containsKey(key))
            this.symbolsTable.put(key, new SymbolTableValue(type, null));
    }

    public void addSymbols(String key, SymbolTableValue value){
        if(!this.symbolsTable.containsKey(key))
            this.symbolsTable.put(key, value);
    }

    public void changeValue(String key, String type, String use){
        SymbolTableValue v = null;
        if(this.symbolsTable.containsKey(key)) {
            v = this.symbolsTable.remove(key);
            v.setType(type);
            v.setUse(use);
            this.symbolsTable.put(key,v);
        }else{
            System.err.println("NO EXISTE LA CLAVE EN LA TABLA DE SIMBOLOS");
        }

    }

    public SymbolTableValue remove(String key){
        return this.symbolsTable.remove(key);
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
