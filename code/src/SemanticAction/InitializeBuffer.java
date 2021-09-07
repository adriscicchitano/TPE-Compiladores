package SemanticAction;

import StateTransitionMatrix.StructureUtilities;

public class InitializeBuffer implements SemanticAction{
    @Override
    public void execute(StructureUtilities su, char c) {
        su.emptyBuffer();
    }
}
