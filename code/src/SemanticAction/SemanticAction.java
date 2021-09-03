package SemanticAction;

import StateTransitionMatrix.StructureUtilities;

public interface SemanticAction {
    void execute(StructureUtilities su, char c);
}
