package SemanticAction;

import StateTransitionMatrix.StructureUtilities;

public class AddError implements SemanticAction{
    String error;

    public AddError(String error){
        this.error = error;
    }

    @Override
    public void execute(StructureUtilities su, char c) {
        su.addError(this.error);
    }
}
