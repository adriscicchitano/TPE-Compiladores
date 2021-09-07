package SemanticAction;

import StateTransitionMatrix.StructureUtilities;

public class AddTokenFromBuffer implements SemanticAction{
    String token;

    public AddTokenFromBuffer(String token){
        this.token = token;
    }

    @Override
    public void execute(StructureUtilities su, char c) {
        su.addToken(this.token);
    }
}
