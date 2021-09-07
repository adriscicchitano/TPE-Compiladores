package Text;

import java.io.File;
import java.util.Scanner;

public class Text {
    private char[] text;
    private boolean[] visited;
    private int currentLine = 1;
    private int currentCharIndex = 0;
    private boolean isEmpty;

    public Text(File file){
        this.text = fileToString(file).toCharArray();
        this.visited = new boolean[this.text.length];
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
        this.isEmpty = text.length() == 0;
        return text;
    }

    public char getNextChar(){
        char result = text[currentCharIndex];
        if(result == '\n' && !visited[currentCharIndex])
            currentLine++;
        currentCharIndex++;
        visited[currentCharIndex-1] = true;
        return result;
    }

    public void returnChar(){
        currentCharIndex--;
    }

    public boolean isEmpty(){
        return this.isEmpty;
    }

    public int getCurrentLine(){
        return currentLine;
    }
}
