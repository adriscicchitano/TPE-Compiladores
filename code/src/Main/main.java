package Main;

import StateTransitionMatrix.Parser;
import Text.Text;

import java.io.File;

public class main {
    public static void main(String[] args){
        //File f = new File(args[0]);
        File f = new File("C:\\Users\\User\\Desktop\\hola.txt");
        Text text = new Text(f);
        Parser p = new Parser(text,true);
        p.run();

    }
}
