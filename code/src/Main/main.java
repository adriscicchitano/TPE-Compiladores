package Main;


import StateTransitionMatrix.LexicAnalyzer;
import StateTransitionMatrix.Parser;
import StateTransitionMatrix.StructureUtilities;
import Text.Text;

import java.io.File;

public class main {
    public static void main(String[] args){
        File f = new File("C:\\Users\\User\\Desktop\\hola.txt");
        Text text = new Text(f);
        StructureUtilities su = new StructureUtilities(text);

        LexicAnalyzer la = new LexicAnalyzer(text,su);
        while(true){
            String s = la.requestToken();
            if(s!=null)
                System.out.println(s);
            else
                break;
        }

        text = new Text(f);
        Parser p = new Parser(text,true);
        p.run();



        //CharSet cs = new CharSet('a','b','c');
        //System.out.println(cs.contains('a'));
        //System.out.println(cs.contains('z'));

    }
}
