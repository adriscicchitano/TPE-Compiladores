package Structures;

public class SymbolTableValue {
    private String type;
    private String use;
    private String parameterType;

    public SymbolTableValue(String type, String use){
        this.type = type;
        this.use= use;
    }

    public SymbolTableValue(String type, String use, String parameterType){
        this.type = type;
        this.use= use;
        this.parameterType = parameterType;
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

    public void setParameterType(String parameter){
        this.parameterType = parameter;
    }

    @Override
    public String toString() {
        return "["+type + "-" + use + "]";
    }
}
