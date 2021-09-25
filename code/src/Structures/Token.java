package Structures;

public class Token {
    //HACER UN METODO QUE DEVUELVA EL TOKEN COMO UN ENTERO (VALOR ASCII O VALOR OTORGADO POR EL PARSER)
    private String token;
    private String symbolsTableReference;
    private int detectedInLine = 0;
    private boolean isNewLine;

    public Token(){}

    public void setSymbolsTableReference(String reference){
        this.symbolsTableReference = reference;
    }

    public void setToken(String token, int line){
        this.token =token;
        if(this.detectedInLine != line)
            isNewLine = true;
        else
            isNewLine = false;
        this.detectedInLine = line;
    }

    public int getLineDetected(){
        return this.detectedInLine;
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

    public boolean isDetectedInNewLine() {
        return isNewLine;
    }
}
