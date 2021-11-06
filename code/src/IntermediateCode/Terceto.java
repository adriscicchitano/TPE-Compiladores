package IntermediateCode;

public class Terceto {
    private String action;
    public TercetoUnion v1;
    public TercetoUnion v2;
    public String type;

    public Terceto(String action, String v1, String v2, String type){
        this.action = action;
        this.v1 = new TercetoUnion(v1);
        this.v2 = new TercetoUnion(v2);
        if(type != null)
            this.type = type;
    }

    public Terceto(String action, String v1, int v2, String type){
        this.action = action;
        this.v1 = new TercetoUnion(v1);
        this.v2 = new TercetoUnion(v2);
        if(type != null)
            this.type = type;
    }

    public Terceto(String action, int v1, String v2, String type){
        this.action = action;
        this.v1 = new TercetoUnion(v1);
        this.v2 = new TercetoUnion(v2);
        if(type != null)
            this.type = type;
    }

    public Terceto(String action, int v1, int v2, String type){
        this.action = action;
        this.v1 = new TercetoUnion(v1);
        this.v2 = new TercetoUnion(v2);
        if(type != null)
            this.type = type;
    }

    public String getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return "("+action+", "+v1+", "+v2+")" + ((type != null) ? type : "");
    }
}
