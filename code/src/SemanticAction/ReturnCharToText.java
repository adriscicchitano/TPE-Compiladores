package SemanticAction;

import StateTransitionMatrix.StructureUtilities;

public class ReturnCharToText implements SemanticAction{
    @Override
    public void execute(StructureUtilities su, char c) {
        su.returnCharToText();
    }
}
