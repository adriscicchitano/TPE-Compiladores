package Structures;

import java.util.HashMap;

public class TerminalsCodes {
    private static HashMap<String,Integer> codes = new HashMap<String,Integer>(){{
        put(":=",257);
        put("ID",258);
        put("CTE_UINT",259);
        put("CTE_DOUBLE",260);
        put("UINT",261);
        put("DOUBLE",262);
        put("STRING",263);
        put("PRINT",264);
        put(">=",265);
        put("<=",266);
        put("==",267);
        put("<>",268);
        put("||",269);
        put("&&",270);
        put("IF",271);
        put("THEN",272);
        put("ELSE",273);
        put("ENDIF",274);
        put("BREAK",275);
        put("BEGIN",276);
        put("END",277);
        put("WHILE",278);
        put("DO",279);
        put("FUNC",280);
        put("RETURN",281);
        put("PRE",282);
    }};

    public static int getCode(String code){
        return codes.getOrDefault(code, -1);
    }
}
