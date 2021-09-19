package SemanticAction;

import StateTransitionMatrix.StructureUtilities;
import Structures.Token;

public class CompoundActions implements SemanticAction{
    SemanticAction sa1;
    SemanticAction sa2;

    public CompoundActions(SemanticAction sa1, SemanticAction sa2){
        this.sa1=sa1;
        this.sa2=sa2;
    }

    @Override
    public void execute(StructureUtilities su, char c, Token token) {
        sa1.execute(su,c,token);
        sa2.execute(su,c,token);
    }
}
