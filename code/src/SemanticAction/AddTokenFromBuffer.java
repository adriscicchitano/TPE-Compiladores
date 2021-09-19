package SemanticAction;

import StateTransitionMatrix.StructureUtilities;
import Structures.Token;

public class AddTokenFromBuffer implements SemanticAction{
    String token;

    public AddTokenFromBuffer(String token){
        this.token = token;
    }

    @Override
    public void execute(StructureUtilities su, char c, Token token) {
        su.addToken(this.token,token);
    }
}
