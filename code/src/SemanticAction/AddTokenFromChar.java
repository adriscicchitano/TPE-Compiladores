package SemanticAction;

import StateTransitionMatrix.StructureUtilities;
import Structures.Token;

public class AddTokenFromChar implements SemanticAction{
    @Override
    public void execute(StructureUtilities su, char c, Token token) {
        su.addToken(c);
        token.setToken(c);
    }
}
