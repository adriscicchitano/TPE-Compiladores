package Structures;

public class SymbolTableValue {
    private String type;
    private String use;
    private String parameter;

    public SymbolTableValue(String type, String use){
        this.type = type;
        this.use= use;
    }

    public SymbolTableValue(String type, String use, String parameter){
        this.type = type;
        this.use= use;
        this.parameter = parameter;
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

    public void setParameter(String parameter){
        this.parameter = parameter;
    }


    public String getParameter(){return this.parameter;}
    /*
    public void setCallable(String callableFunction){
        this.callableFunction = callableFunction;
    }

    public String getCallable(){
        return this.callableFunction;
    }
    */
    @Override
    public String toString() {
        String result = "["+type + "-" + use;
        /*
        if(this.callableFunction != null)
            result += "-" + callableFunction;
        */
        if(this.parameter != null)
            result += "-" + parameter;
        return  result+ "]";
    }
}
