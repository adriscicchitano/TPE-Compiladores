package Text;

import java.io.File;
import java.util.Scanner;

public class Text {
    private char[] text;
    private int currentLine = 1;
    private int actualCharIndex = 0;

    public Text(File file){
        this.text = fileToString(file).toCharArray();
    }

    private String fileToString(File file){
        String text = "";
        try {
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()) {
                text += reader.nextLine() + '\n';
            }
        }catch(Exception e){
            System.err.println("El archivo no es valido");
            return null;
        }
        return text;
    }

    public char getNextChar(){
        char result = text[actualCharIndex];
        if(result == '\n')
            currentLine++;
        actualCharIndex++;
        return result;
    }

    public void returnChar(){
        actualCharIndex--;
    }

    public int getCurrentLine(){
        return currentLine;
    }
}
