package Main;

import Text.Text;

import java.io.File;

public class main {
    public static void main(String[] args){
        File f = new File("C:\\Users\\User\\Desktop\\hola.txt");
        Text text = new Text(f);

        String s = "aa";
        char b = 'r';
        System.out.println(s+b);

        //CharSet cs = new CharSet('a','b','c');
        //System.out.println(cs.contains('a'));
        //System.out.println(cs.contains('z'));

    }
}
