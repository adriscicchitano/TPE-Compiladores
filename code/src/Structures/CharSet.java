package Structures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CharSet {
    private List<Character> characters = new ArrayList<>();

    public CharSet(Character... characters){
        this.characters.addAll(Arrays.asList(characters));
    }

    public boolean contains(char c){
        return this.characters.contains(c);
    }
}
