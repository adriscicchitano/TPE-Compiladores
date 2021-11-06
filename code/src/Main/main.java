package Main;

import StateTransitionMatrix.Parser;
import StateTransitionMatrix.StructureUtilities;
import Text.Text;

import java.io.File;

public class main {
    public static void main(String[] args){
        //File f = new File(args[0]);

        File f = new File("X:\\ADRIAN\\4-CUARTO AÑO\\Diseño de Compiladores\\TPE\\code\\test_cases\\codes\\test_2.txt");
        Text text = new Text(f);
        Parser p = new Parser(text,true);
        p.compile();
    }
}
