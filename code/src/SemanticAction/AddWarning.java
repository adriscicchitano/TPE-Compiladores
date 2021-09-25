package SemanticAction;

import StateTransitionMatrix.StructureUtilities;
import Structures.Token;

public class AddWarning implements SemanticAction{
    @Override
    public void execute(StructureUtilities su, char c, Token token) {
        su.addWarning("No se reconoce el caracter " + c + " por lo tanto es omitido del codigo");
    }
}
