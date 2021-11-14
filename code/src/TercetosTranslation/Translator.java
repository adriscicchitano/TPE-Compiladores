package TercetosTranslation;

import IntermediateCode.Terceto;
import Utils.utils;

import java.util.ArrayList;
import java.util.List;

public class Translator {
    private static final String r1 = "eax";
    private static final String r1_2B = "ax";
    private static final String r2 = "ebx";
    private static final String r3 = "ecx";
    private static final String r4 = "edx";
    private static final String r4_2B = "dx"; //LOWEST 2 BYTES OF R4
    private static final String r4_1B = "dl"; //LOWEST BYTE OF R4
    private static final String UINT_type = "DD";
    private static final String DOUB_type = "real4";
    private static final String start = "start:\nmov "+r4+",0\njmp main\n";
    private static final String endingTag =
            "ending:\n" +
            "\tinvoke ExitProcess, 0";
    private static final String exceptDiv0 =
                    "error_divisor_0:\n" +
                    "\tinvoke StdOut, addr divisor_zero\n" +
                    "\tjmp ending";
    private static final String execptOFMult =
                    "error_overflow_mult:\n" +
                    "\tinvoke StdOut, addr overflow_mult\n" +
                    "\tjmp ending\n";
    private static final String header =
            ".386\n" +
            ".model flat, stdcall\n" +
            "option casemap :none\n" +
            "include \\masm32\\include\\windows.inc\n" +
            "include \\masm32\\include\\kernel32.inc\n" +
            "include \\masm32\\include\\masm32.inc\n" +
            "includelib \\masm32\\lib\\kernel32.lib\n" +
            "includelib \\masm32\\lib\\masm32.lib";

    private String dataSection =
                    ".data\n" +
                    "\tdivisor_zero DB \"division por 0\",0\n"+
                    "\toverflow_mult DB \"overflow en multiplicacion\",0\n";
    private String codeSection = ".code\n";

    private List<Terceto> tercetos;
    private String code = "";
    private int auxVarCount = 0;
    private int auxVarCountF = 0;
    private String currentFunc;

    public Translator(List<Terceto> tercetos){
        this.tercetos = tercetos;
        this.code += start + "\n";
        this.code += this.generateCode();
        this.generateAuxiliarVars();
        this.code = execptOFMult + "\n" + this.code;
        this.code = exceptDiv0 + "\n" + this.code;
        this.code = endingTag + "\n" + this.code;
        this.code = codeSection + "\n" + this.code;
        this.code = dataSection + "\n" + this.code;
        this.code = header + "\n" + this.code;
    }

    private void generateAuxiliarVars(){
        for(int i = 0; i < auxVarCount; i++ ){
            this.dataSection += "\t@aux"+(i+1)+" "+UINT_type+" 0\n";
        }
        for(int i = 0; i < auxVarCountF; i++ ){
            this.dataSection += "\t@Faux"+(i+1)+" "+DOUB_type+" 0\n";
        }
    }

    private String generateCode(){
        String code = "";
        for(Terceto t : this.tercetos){
            switch(t.getAction()){
                case "main":
                    code += "main:\n";
                    break;
                case "var":
                    this.dataSection += translateVarDec(t);
                    break;
                case "+":
                case "*":
                case "-":
                case "/":
                    code += this.translateArith(t);
                    break;
                case ":=":
                    code += this.translateAssignment(t);
                    break;
                case "utod":
                    code += this.translateConversion(t);
                    break;
                case "<":
                case "<=":
                case ">":
                case ">=":
                case "==":
                case "<>":
                    code += this.translateComparison(t);
                    break;
                case "&&":
                case "||":
                    code += this.translateLogicOperation(t);
                    break;
                case "tag":
                    code += this.translateFunctionDec(t);
                    break;
                case "ret":
                    code += this.translateFunctionRet(t);
                    break;
                case "call":
                    code += this.translateFunctionCall(t);
                    break;
                case "print":
                    code += this.translateScreenPrint(t);
                    break;
                case "BF":
                    code += this.translateBF(t);
                    break;
            }
        }
        return code;
    }

    public String getFinalCode(){
        String endStart = "end start";
        return this.code + endStart;
    }

    public String translateBF(Terceto t){
        String translation = "pop "+r1_2B+"\ncmp "+r1_2B+", 1\njne \n";
        return translation;
    }

    public String translateScreenPrint(Terceto t){
        String translation = "";
        String toPrint = t.getV1();
        this.dataSection += "\t"+toPrint+" DB "+"\""+toPrint+"\", 0\n";
        translation += "invoke StdOut, addr "+toPrint+"\n";
        return translation;
    }

    public String translateFunctionDec(Terceto t){
        String translation = t.getV1().replaceAll(":", "_")+":\n";
        this.currentFunc = t.getV1().replaceAll(":", "_");
        this.dataSection += "\t"+"_"+t.getV1().replaceAll(":", "_")+"_param\n"+
                            "\t"+"_"+t.getV1().replaceAll(":", "_")+"\n";
        translation += this.translateAssignment(new Terceto(":=",t.getV2().replaceAll(":","_"),this.currentFunc+"_param",null));
        return translation;
    }

    public String translateFunctionRet(Terceto t){
        String translation = "";
        String leftOperand;

        if(t.isV1IsTerceto()) {
            int tercetoIndex = Integer.parseInt(t.getV1().replace("[", "").replace("]", ""));
            leftOperand = this.tercetos.get(tercetoIndex - 1).getAux();
            translation += "mov "+r1+", "+leftOperand+"\nmov _"+this.currentFunc+", "+r1+"\n";
        }
        else
            translation += "mov "+r1+", "+(utils.isNumber(t.getV1()) ? t.getV1() : "_"+t.getV1().replaceAll(":", "_"))+"\nmov _"+this.currentFunc+", "+r1+"\n";

        this.currentFunc = null;
        return translation+"ret\n";
    }

    public String translateFunctionCall(Terceto t){
        String translation = "";
        String param;

        if(t.isV2IsTerceto()) {
            int tercetoIndex = Integer.parseInt(t.getV1().replace("[", "").replace("]", ""));
            param = this.tercetos.get(tercetoIndex - 1).getAux();
            translation += "mov "+r1+", "+param+"\nmov _"+this.currentFunc+"_param, "+param+"\n";
        }
        else
            translation += "mov "+r1+", "+(utils.isNumber(t.getV2()) ? t.getV2() : "_"+t.getV2().replaceAll(":", "_"))+"\nmov _"+t.getV1().replaceAll(":", "_")+"_param, "+ r1 +"\n";

        translation += "call "+t.getV1().replaceAll(":", "_")+"\n";

        return translation;
    }

    public String translateLogicOperation(Terceto t){
        String translation = "pop "+r1+"\n"+"pop "+r2+"\n";

        switch(t.getAction()){
            case "&&":
                translation += "and "+r1+", "+r2+"\n";
                break;
            case "||":
                translation += "or "+r1+", "+r2+"\n";
                break;
        }

        translation += "push "+r1+"\n";
        return translation;
    }

    public String translateComparison(Terceto t){
        String translation = "";
        String leftOperand;
        String rightOperand;

        if(t.isV2IsTerceto()) {
            int tercetoIndex = Integer.parseInt(t.getV2().replace("[","").replace("]",""));
            rightOperand = this.tercetos.get(tercetoIndex-1).getAux();
        }else
            rightOperand = utils.isNumber(t.getV2()) ? t.getV2() : "_"+t.getV2().replaceAll(":", "_");

        if(t.isV1IsTerceto()) {
            int tercetoIndex = Integer.parseInt(t.getV1().replace("[", "").replace("]", ""));
            leftOperand = this.tercetos.get(tercetoIndex - 1).getAux();
            translation += "mov " + r1 + ", "+leftOperand+"\n";
        }
        else
            translation += "mov "+r1+", "+ (utils.isNumber(t.getV1()) ? t.getV1() : "_"+t.getV1().replaceAll(":", "_")) +"\n";

        translation += "cmp "+r1+", "+rightOperand+"\n";

        switch(t.getAction()){
            case "<":
                translation += "setl "+r4_1B+"\n";
                break;
            case "<=":
                translation += "setle "+r4_1B+"\n";
                break;
            case ">":
                translation += "setg "+r4_1B+"\n";
                break;
            case ">=":
                translation += "setge "+r4_1B+"\n";
                break;
            case "==":
                translation += "sete "+r4_1B+"\n";
                break;
            case "<>":
                translation += "setne "+r4_1B+"\n";
                break;
        }

        translation += "push "+r4_2B+"\n";

        return translation;
    }

    public String translateConversion(Terceto t){
        String translation = "";
        String operand;
        if(t.isV1IsTerceto()) {
            int tercetoIndex = Integer.parseInt(t.getV1().replace("[","").replace("]",""));
            operand = this.tercetos.get(tercetoIndex-1).getAux();
        }else
            operand = t.getV1().replaceAll(":", "_");
        t.setAux("@Faux"+(++auxVarCountF));
        translation += "fild "+operand+"\n" + "fst ";
        return translation;
    }

    public String translateVarDec(Terceto t){
        String translation = "";
        switch(t.getV2()){
            case "UINT":
                translation += "\t_"+t.getV1().replaceAll(":","_")+" "+UINT_type+" 0\n";
                break;
            case "DOUBLE":
                translation += "\t_"+t.getV1().replaceAll(":","_")+" "+DOUB_type+" 0\n";
                break;
        }
        return translation;
    }

    public String translateArith(Terceto t){
        String translation = "";
        String leftOperand;
        String rightOperand;

        if(t.isV2IsTerceto()) {
            int tercetoIndex = Integer.parseInt(t.getV2().replace("[","").replace("]",""));
            rightOperand = this.tercetos.get(tercetoIndex-1).getAux();
        }else {
            rightOperand = utils.isNumber(t.getV2()) ? t.getV2() : "_"+t.getV2().replaceAll(":", "_");
        }

        translation += "mov "+r3+", "+rightOperand+"\n";

        if(t.isV1IsTerceto()) {
            int tercetoIndex = Integer.parseInt(t.getV1().replace("[", "").replace("]", ""));
            leftOperand = this.tercetos.get(tercetoIndex - 1).getAux();
            translation += "mov " + r1 + ", "+leftOperand+"\n";
        }
        else
            translation += "mov "+r1+", "+ (utils.isNumber(t.getV1()) ? t.getV1() : "_"+t.getV1().replaceAll(":", "_")) +"\n";
        switch(t.getAction())
        {
            case "+":
                translation += "add "+r1+", "+ r3+"\n";
                break;
            case "-":
                translation += "sub "+r1+", "+ r3+"\n";
                break;
            case "*":
                translation += "mul "+r3+"\n"+
                        "mov "+r1+", "+r1+"\n" +
                        "mov "+r2+", "+"0\n" +
                        "mov bx, ax\n" +
                        "cmp "+r1+", "+r2+"\n" +
                        "jne error_overflow_mult\n"+
                        "mov cx, ax\n";
                break;
            case "/":
                translation += "mov "+r4+", 0\ncmp "+r3+", 0\nje error_divisor_0\ndiv "+r3+"\n";
                break;
        }
        t.setAux("@aux"+(++auxVarCount));
        translation += "mov @aux"+auxVarCount+", "+r1+"\n";
        return translation;
        //System.out.println(translation);
    }

    public String translateAssignment(Terceto t){
        String translation = "";
        String leftOperand;
        String rightOperand;

        if(t.isV2IsTerceto()) {
            int tercetoIndex = Integer.parseInt(t.getV2().replace("[","").replace("]",""));
            rightOperand = this.tercetos.get(tercetoIndex-1).getAux();
        }else
            rightOperand = utils.isNumber(t.getV2()) ? t.getV2() : "_"+t.getV2().replaceAll(":", "_");
        translation += "mov "+r1+", "+rightOperand+"\n";
        leftOperand = utils.isNumber(t.getV1()) ? t.getV1() : "_"+t.getV1().replaceAll(":", "_");
        translation += "mov "+leftOperand+", "+r1+"\n";
        return translation;
    }
}
