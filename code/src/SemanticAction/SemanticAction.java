package SemanticAction;

import Structures.Buffer;
import Structures.SymbolsTable;
import Text.TokenizedText;

public interface SemanticAction {
    String execute(Buffer buffer, char c, SymbolsTable symbolsTable, TokenizedText errors, int line);
}
