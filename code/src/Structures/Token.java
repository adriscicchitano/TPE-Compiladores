package Structures;

public class Token {
    //HACER UN METODO QUE DEVUELVA EL TOKEN COMO UN ENTERO (VALOR ASCII O VALOR OTORGADO POR EL PARSER)
    private String token;
    private String symbolsTableReference;

    public Token(){}

    public void setSymbolsTableReference(String reference){
        this.symbolsTableReference = reference;
    }

    public void setToken(String token){
        this.token =token;
    }

    public void setToken(char c){
        this.token = "";
        this.token += c;
    }

    public String getToken(){
        return this.token;
    }

    public String getSymbolsTableReference(){
        return this.symbolsTableReference;
    }

}
