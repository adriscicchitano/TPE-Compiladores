package SemanticAction;

import StateTransitionMatrix.StructureUtilities;
import Structures.Token;

public class CheckRange implements SemanticAction{
    String type;

    public CheckRange(String type){
        this.type = type;
    }

    @Override
    public void execute(StructureUtilities su, char c, Token token) {
        su.CheckRangeAndAddToken(this.type,token);
    }
}
