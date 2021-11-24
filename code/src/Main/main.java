package Main;

import IntermediateCode.Terceto;
import StateTransitionMatrix.Parser;
import StateTransitionMatrix.StructureUtilities;
import TercetosTranslation.Translator;
import Text.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args){
        //File f = new File(args[0]);

        File f = new File("C:\\Users\\Tomas\\Desktop\\compi\\Casos de Prueba\\caso_6.txt");

        Text text = new Text(f);
        Parser p = new Parser(text,true);
        p.compile();


        /*
        List<Terceto> ter = new ArrayList<>();
        //ter.add(new Terceto("+", "[1]", "3",null));
        ter.add(new Terceto("var", "aa:abc", "UINT", null));
        ter.add(new Terceto(":=", "aa:abc", "15", null));
        ter.add(new Terceto("utod", "aa:abc", "-", null));
        ter.add(new Terceto(":=", "aa:abc", "[3]", null));
        ter.add(new Terceto("+", "1", "8",null));
        ter.add(new Terceto("-", "aa:abc", "[2]",null));
        ter.add(new Terceto("/", "[2]", "[3]",null));
        ter.add(new Terceto("*", "aa:abc", "[4]",null));
        ter.add(new Terceto(":=", "aa:abc", "[5]",null));
        ter.add(new Terceto(":=", "aa:abc", "645",null));
        */
        Translator t = new Translator(p.tercetos);


        System.out.println(t.getFinalCode());
        System.out.println();
    }
}
