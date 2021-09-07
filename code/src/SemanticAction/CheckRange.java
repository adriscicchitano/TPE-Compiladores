package SemanticAction;

import StateTransitionMatrix.StructureUtilities;

public class CheckRange implements SemanticAction{
    String type;

    public CheckRange(String type){
        this.type = type;
    }

    @Override
    public void execute(StructureUtilities su, char c) {
        su.CheckRangeAndAddToken(this.type);
    }
}
