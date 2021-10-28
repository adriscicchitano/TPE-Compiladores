package Main;

import StateTransitionMatrix.Parser;
import Structures.CurrentScope;
import Text.Text;

import java.io.File;

public class main {
    public static void main(String[] args){
        //File f = new File(args[0]);
        File f = new File("X:\\ADRIAN\\4-CUARTO AÑO\\Diseño de Compiladores\\TPE\\code\\test_cases\\codes\\valid_code.txt");
        Text text = new Text(f);
        Parser p = new Parser(text);
        p.compile();

    }
}
