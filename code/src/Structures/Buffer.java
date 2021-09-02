package Structures;

import java.util.ArrayList;
import java.util.List;

public class Buffer {
    private List<Character> buffer;

    public Buffer(){
        this.buffer = new ArrayList<>();
    }

    public void addCharacter(Character c){
        buffer.add(c);
    }

    public int bufferSize(){
        return this.buffer.size();
    }

    public void emptyBuffer(){
        this.buffer.clear();
    }

}
