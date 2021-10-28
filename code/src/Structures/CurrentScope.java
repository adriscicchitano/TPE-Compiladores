package Structures;

import java.util.ArrayList;
import java.util.List;

public class CurrentScope {
    private static List<String> currentScope = new ArrayList<>();


    public static void addScope(String scope){
        currentScope.add(scope);
    }

    public static void deleteScope(){
        currentScope.remove(currentScope.size() - 1);
    }

    public static String getScope() {
        String scope = "";
        int i = 0;
        while(i < currentScope.size() - 1) {
            scope += currentScope.get(i) + ".";
            i++;
        }
        scope += currentScope.get(i);
        return scope;
    }
}

