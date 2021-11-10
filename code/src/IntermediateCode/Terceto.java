package IntermediateCode;

import java.util.Objects;

public class Terceto {
    private String action;
    private String v1;
    private boolean v1IsTerceto;
    private String v2;
    private boolean v2IsTerceto;
    private String type;

    public Terceto(String action, String v1, String v2, String type){
        this.action = action;
        this.v1 = v1;
        this.v1IsTerceto = v1.startsWith("[") && v1.endsWith("]");
        this.v2 = v2;
        this.v2IsTerceto = v2.startsWith("[") && v2.endsWith("]");
        if(type != null)
            this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public String getAction(){
        return this.action;
    }

    public boolean isV1IsTerceto(){
        return this.v1IsTerceto;
    }

    public boolean isV2IsTerceto(){
        return this.v2IsTerceto;
    }

    public String getV1(){
        return this.v1;
    }

    public String getV2() {
        return this.v2;
    }

    public void setV2(String v2){
        this.v2 = v2;
    }

    @Override
    public String toString() {
        return "("+action+", "+v1+", "+v2+")" + ((type != null) ? type : "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Terceto terceto = (Terceto) o;
        return Objects.equals(action, terceto.action) && Objects.equals(v1, terceto.v1) && Objects.equals(v2, terceto.v2);
    }

}
