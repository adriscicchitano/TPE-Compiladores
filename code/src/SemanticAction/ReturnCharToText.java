package SemanticAction;

import StateTransitionMatrix.StructureUtilities;
import Structures.Token;

public class ReturnCharToText implements SemanticAction{
    @Override
    public void execute(StructureUtilities su, char c, Token token) {
        su.returnCharToText();
    }
}
