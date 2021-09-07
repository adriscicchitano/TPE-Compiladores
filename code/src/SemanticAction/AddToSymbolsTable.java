package SemanticAction;

import StateTransitionMatrix.StructureUtilities;

public class AddToSymbolsTable implements SemanticAction{
    @Override
    public void execute(StructureUtilities su, char c) {
        su.searchInSymbolsTable();
    }
}
