package SemanticAction;

import StateTransitionMatrix.StructureUtilities;

public class AddCharacterToBuffer implements SemanticAction{
    @Override
    public void execute(StructureUtilities su, char c) {
        su.addCharToBuffer(c);
    }
}
