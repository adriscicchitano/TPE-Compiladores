package SemanticAction;

import StateTransitionMatrix.StructureUtilities;
import Structures.Token;

public class AddError implements SemanticAction{
    String error;

    public AddError(String error){
        this.error = error;
    }

    @Override
    public void execute(StructureUtilities su, char c, Token token) {
        su.addError(this.error);
    }
}
