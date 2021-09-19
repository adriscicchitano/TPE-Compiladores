package SemanticAction;

import StateTransitionMatrix.StructureUtilities;
import Structures.Token;

public interface SemanticAction {
    void execute(StructureUtilities su, char c, Token token);
}
