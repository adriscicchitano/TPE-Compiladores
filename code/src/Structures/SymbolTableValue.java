package Structures;

public class SymbolTableValue {
    private String type;
    private String use;

    public SymbolTableValue(String type, String use){
        this.type = type;
        this.use= use;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    @Override
    public String toString() {
        return "["+type + "-" + use + "]";
    }
}
