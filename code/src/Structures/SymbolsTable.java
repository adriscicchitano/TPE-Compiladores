package Structures;

import java.util.HashMap;

public class SymbolsTable {
    private HashMap<String,String> symbolsTable;

    public SymbolsTable(){
        this.symbolsTable = new HashMap<String,String>();
    }

    public void addSymbolsTable(String key){
        if(!this.symbolsTable.containsKey(key))
            this.symbolsTable.put(key, null);
    }

    public boolean contains(String key){
        return this.symbolsTable.containsKey(key);
    }

    public String toString(){
        return this.symbolsTable.toString();
    }
}
