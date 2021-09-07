package SemanticAction;

import StateTransitionMatrix.StructureUtilities;

public class RemoveLastCharFromBuffer implements SemanticAction{
    @Override
    public void execute(StructureUtilities su, char c) {
        su.removeLastCharFromBuffer();
    }
}
