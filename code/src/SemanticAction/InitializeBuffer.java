package SemanticAction;

import StateTransitionMatrix.StructureUtilities;
import Structures.Token;

public class InitializeBuffer implements SemanticAction{
    @Override
    public void execute(StructureUtilities su, char c, Token token) {
        su.emptyBuffer();
    }
}
