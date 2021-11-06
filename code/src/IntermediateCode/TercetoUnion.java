package IntermediateCode;

public class TercetoUnion {
    public String tableSymbolReference;
    public int indexToTerceto;

    public TercetoUnion(String tableSymbolReference){
        this.tableSymbolReference = tableSymbolReference;
    }

    public TercetoUnion(int indexToTerceto){
        this.indexToTerceto = indexToTerceto;
    }

    @Override
    public String toString() {
        if(tableSymbolReference != null)
            return tableSymbolReference;
        else
            return "["+indexToTerceto+"]";
    }
}
