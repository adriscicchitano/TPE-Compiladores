package SemanticAction;

import StateTransitionMatrix.StructureUtilities;

public class AddTokenFromChar implements SemanticAction{
    @Override
    public void execute(StructureUtilities su, char c) {
        su.addToken(c);
    }
}
