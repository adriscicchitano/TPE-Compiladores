package TercetosTranslation;

import IntermediateCode.Terceto;
import Utils.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Translator {
    private static final String r1 = "eax";
    private static final String r1_2B = "ax";
    private static final String r2 = "ebx";
    private static final String r3 = "ecx";
    private static final String r3_2B = "cx"; //LOWEST 2 BYTES OF R3
    private static final String r3_1B = "cl"; //LOWEST BYTE OF R3
    private static final String r4 = "edx";
    private static final String r4_2B = "dx"; //LOWEST 2 BYTES OF R4
    private static final String r4_1B = "dl"; //LOWEST BYTE OF R4
    private static final String conversionVar = "@conversion";
    private static final String sys_aux = "sys_aux";
    private static final String UINT_type = "DD";
    private static final String DOUB_type = "real4";
    private static final String start =
            "start:\n" +
            "mov "+r1+",0\n" +
            "mov "+r2+",0\n" +
            "mov "+r3+",0\n" +
            "mov "+r4+",0\n" +
            "finit\n"+
            "jmp main\n";
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
    private static final String exceptRecursion =
                    "error_func_recursion:\n" +
                    "\tinvoke StdOut, addr func_recursion\n" +
                    "\tjmp ending";
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
                    "\toverflow_mult DB \"overflow en multiplicacion\",0\n"+
                    "\tfunc_recursion DB \"recursion en funcion\",0\n"+
                    "\t"+conversionVar+" "+UINT_type+" 0\n"+
                    "\t"+sys_aux+" DW 0\n";
    private String codeSection = ".code\n";
    private String functions = "";

    private List<Terceto> tercetos;
    private String code = "";
    private int auxVarCount = 0;
    private int auxVarCountF = 0;
    private int doub_consts = 0;
    private String currentFunc;
    private String currentFuncType;
    private int currentFuncStartIndex = 0;

    public Translator(List<Terceto> tercetos){
        this.tercetos = tercetos;
        this.code += this.generateCode();
        this.code = start + "\n" + this.functions + this.code;
        this.generateAuxiliarVars();
        this.code = execptOFMult + "\n" + this.code;
        this.code = exceptDiv0 + "\n" + this.code;
        this.code = exceptRecursion + "\n" + this.code;
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
            this.dataSection += "\t@Faux"+(i+1)+" "+DOUB_type+" 0.0\n";
        }
    }

    private String generateCode(){
        String code = "";
        int i = 0;
        for(Terceto t : this.tercetos){
            if(t.getType() == null || !t.getType().contains("double")) {
                switch (t.getAction()) {
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
                    case "BI":
                        code += this.translateBI(t);
                        break;
                    case "Pre":
                        code += this.translatePRE(t);
                        break;
                    case "func_assig":
                        this.functions += translateFunctionAssignment(t);
                        break;
                }
            }else{
                switch(t.getAction()){
                    case "utod":
                        code += this.translateConversion(t);
                        break;
                    case "+":
                    case "*":
                    case "-":
                    case "/":
                        code += this.translateArithF(t);
                        break;
                    case ":=":
                        code += this.translateAssignmentF(t);
                        break;
                    case "<":
                    case "<=":
                    case ">":
                    case ">=":
                    case "==":
                    case "<>":
                        code += this.translateComparisonF(t);
                        break;
                    case "&&":
                    case "||":
                        code += this.translateLogicOperation(t);
                        break;
                    case "call":
                        code += this.translateFunctionCall(t);
                        break;
                    case "ret":
                        code += this.translateFunctionRet(t);
                        break;
                    case "tag":
                        code += this.translateFunctionDec(t);
                        break;
                    case "var":
                        this.dataSection += translateVarDec(t);
                        break;
                    case "func_assig":
                        this.functions += translateFunctionAssignment(t);
                        break;
                }
            }
            if (t.getAction().startsWith("Label")){
                code += this.translateTag(t);
            }
            i++;
        }
        return code;
    }

    public String getFinalCode(){
        String endStart = "finit\nend start";
        return this.code + endStart;
    }


    public String translateTag(Terceto t){
        String translation = t.getAction()+":\n";
        return translation;
    }

    public String translatePRE(Terceto t){
        String translation = translateScreenPrint(t);
        return translation+"ret\n";
    }

    public String translateBI(Terceto t){
        String translation = "jmp "+t.getV1()+"\n";
        return translation;
    }

    public String translateBF(Terceto t){
        String translation = "pop "+r1_2B+"\ncmp "+r1_2B+", 0\nje "+t.getV2()+"\n";
        return translation;
    }

    public String translateScreenPrint(Terceto t){
        String translation = "";
        String toPrint = t.getV1();
        this.dataSection += "\t"+toPrint.replaceAll(" ", "_")+" DB "+"\""+toPrint+"\", 0\n";
        translation += "invoke StdOut, addr "+toPrint.replaceAll(" ", "_")+"\n";
        return translation;
    }

    public String translateFunctionAssignment(Terceto t){
        String translation = t.getV1().replaceAll(":","_")+":\n";
        this.dataSection += "\t_"+t.getV1().replaceAll(":","_")+ " real4 0.0\n";

        // ->PARA PROBAR<- FALTA HACER QUE SE HAGA ALGO DEPENDIENDO
        // EL TIPO DEL PARAMETRO
        translation +=
                "fld _param_"+t.getV1().replaceAll(":","_")+"\n" +
                "fstp _param_"+t.getV2().replaceAll(":","_")+"\n";

        translation += "call "+t.getV2().replaceAll(":", "_") +"\n";

        // ->PARA PROBAR<- FALTA HACER QUE SE HAGA ALGO DEPENDIENDO
        // EL TIPO DE LA FUNCION
        if(t.getType().equals("uint")) {
            translation +=
                    "mov "+r1+", _" + t.getV2().replaceAll(":", "_") + "\n" +
                    "mov _" + t.getV1().replaceAll(":", "_") + ", "+r1+"\n";
        }else {
            translation +=
                    "fld _" + t.getV2().replaceAll(":", "_") + "\n" +
                    "fstp _" + t.getV1().replaceAll(":", "_") + "\n";
        }
        return translation +"ret \n";


    }

    public String translateFunctionDec(Terceto t){
        String translation = t.getV1().replaceAll(":", "_")+":\n";
        translation += this.translateComparison(new Terceto("<>", t.getV1().replaceAll(":", "_")+"_count", "0", "uint"));
        translation += "pop "+r4_2B+"\ncmp "+r4_2B+", 1"+"\nje error_func_recursion\n";
        translation += this.translateArith(new Terceto("+", t.getV1().replaceAll(":", "_")+"_count", "1", "uint"));
        translation += "mov "+"_"+t.getV1().replaceAll(":", "_")+"_count, "+r1+"\n";

        String funcType = t.getType().split("-")[0];
        String paramType = t.getType().split("-")[1];
        this.currentFunc = t.getV1().replaceAll(":", "_");
        this.currentFuncType = funcType;

        this.dataSection +=
                "\t"+"_"+t.getV2().replaceAll(":", "_")+" " + (paramType.equals("uint") ? UINT_type+" 0" : DOUB_type+ " 0.0") +"\n" +
                //"\t"+"_param_"+t.getV1().replaceAll(":", "_")+ (paramType.equals("uint") ? UINT_type+" 0" : DOUB_type+ " 0.0") +"\n"+
                "\t"+"_"+t.getV1().replaceAll(":", "_")+" "+(funcType.equals("uint") ? UINT_type+" 0" : DOUB_type+ " 0.0")+"\n"+
                "\t"+"_"+t.getV1().replaceAll(":", "_")+"_count DD 0\n";

        if(paramType.equals("uint"))
            translation += this.translateAssignment(new Terceto(":=",t.getV2().replaceAll(":","_"),"param_"+this.currentFunc,null));
        else
            translation += this.translateAssignmentF(new Terceto(":=",t.getV2().replaceAll(":","_"),"param_"+this.currentFunc,null));

        return translation;
    }

    public String translateFunctionRet(Terceto t){
        String translation = "";
        String leftOperand;
        /*
        if (currentFuncType.contains("uint")) {
            if (t.isV1IsTerceto()) {
                int tercetoIndex = Integer.parseInt(t.getV1().replace("[", "").replace("]", ""));
                leftOperand = this.tercetos.get(tercetoIndex - 1).getAux();
                translation += "mov " + r1 + ", " + leftOperand + "\nmov _" + this.currentFunc + ", " + r1 + "\n";
            } else
                translation += "mov " + r1 + ", " + (utils.isNumber(t.getV1()) ? t.getV1() : "_" + t.getV1().replaceAll(":", "_")) + "\nmov _" + this.currentFunc + ", " + r1 + "\n";
        }else{
            if (t.isV1IsTerceto()) {
                int tercetoIndex = Integer.parseInt(t.getV1().replace("[", "").replace("]", ""));
                leftOperand = this.tercetos.get(tercetoIndex - 1).getAux();
                translation += "fld " + leftOperand +"\nfstp _" + this.currentFunc + "\n";
            } else
                if(utils.isNumber(t.getV1())) {
                    this.dataSection += "\t@doub_const"+(++doub_consts)+" "+DOUB_type+" "+t.getV1()+"\n";
                    translation += "fld @doub_const"+doub_consts+"\n"+ "fstp _"+this.currentFunc+"\n";;
                }
                else
                    translation += "fld " + "_" + t.getV1().replaceAll(":", "_") + "\nfstp _" + this.currentFunc +"\n";
        }


        if(t.getType().equals("uint")){
            translation += "mov ";
        }
        */

        translation += this.translateAssignment(new Terceto(":=", this.currentFunc+"_count","0", "uint"));

        this.currentFunc = null;
        this.currentFuncType = null;
        return translation+"ret\n";
    }

    public String translateFunctionCall(Terceto t){
        String translation = "";
        String param;
        /*
        if(t.isV2IsTerceto()) {
            int tercetoIndex = Integer.parseInt(t.getV2().replace("[", "").replace("]", ""));
            param = this.tercetos.get(tercetoIndex - 1).getAux();

            if(param.startsWith("@F")) {
                translation += "fld "+param+"\n" + "fstp _"+this.currentFunc+"_param\n";
            }
            else
                translation += "mov "+r1+", "+param+"\nmov _"+this.currentFunc+"_param, "+param+"\n";
        }
        else {
            if(t.getType().contains("double")) {
                if(utils.isNumber(t.getV2())){
                    this.dataSection += "\t@doub_const"+(++doub_consts)+" "+DOUB_type+" "+t.getV2()+"\n";
                    translation += "fld @doub_const"+doub_consts+"\n"+ "fstp _"+t.getV1().replaceAll(":","_")+"_param\n";;
                }else
                    translation += "fld "+"_" + t.getV2().replaceAll(":", "_")+"\n" + "fstp _"+t.getV1().replaceAll(":","_")+"_param\n";
            }
            else
                translation += "mov " + r1 + ", " + (utils.isNumber(t.getV2()) ? t.getV2() : "_" + t.getV2().replaceAll(":", "_")) + "\nmov _" + t.getV1().replaceAll(":", "_") + "_param, " + r1 + "\n";
        }

         */

        translation += "call "+t.getV1().replaceAll(":", "_")+"\n";

        if(t.getType().split("-")[0].contains("double")) {
            t.setAux("@Faux"+(++auxVarCountF));
            translation += "fld _"+t.getV1().replaceAll(":","_")+"\n"+"fstp @Faux"+auxVarCountF+"\n";
        }else{
            t.setAux("@aux"+(++auxVarCount));
            translation += "mov "+r1+", _"+t.getV1().replaceAll(":","_")+"\n"+"mov @aux"+auxVarCount+", "+r1+"\n";
        }

        return translation;
    }

    public String translateLogicOperation(Terceto t){
        String translation = "pop "+r3_2B+"\n"+"pop "+r4_2B+"\n";

        switch(t.getAction()){
            case "&&":
                translation += "and "+r3_2B+", "+r4_2B+"\n";
                break;
            case "||":
                translation += "or "+r3_2B+", "+r4_2B+"\n";
                break;
        }

        translation += "push "+r3_2B+"\n";
        return translation;
    }

    public String translateComparisonF(Terceto t){
        String translation = "";
        String leftOperand;
        String rightOperand;

        if(t.isV2IsTerceto()) {
            int tercetoIndex = Integer.parseInt(t.getV2().replace("[","").replace("]",""));
            rightOperand = this.tercetos.get(tercetoIndex-1).getAux();
        }else {
            if(utils.isNumber(t.getV2())) {
                this.dataSection += "\t@doub_const"+(++doub_consts)+" "+DOUB_type+" "+t.getV2()+"\n";
                rightOperand = "@doub_const" + doub_consts;
            }
            else{
                rightOperand = "_"+t.getV2().replaceAll(":","_");
            }
        }
        translation += "fld "+rightOperand+"\n";

        if(t.isV1IsTerceto()) {
            int tercetoIndex = Integer.parseInt(t.getV1().replace("[", "").replace("]", ""));
            leftOperand = this.tercetos.get(tercetoIndex - 1).getAux();
            translation += "fld "+leftOperand+"\n";
        }
        else {
            if(utils.isNumber(t.getV1())) {
                this.dataSection += "\t@doub_const"+(++doub_consts)+" "+DOUB_type+" "+t.getV1()+"\n";
                translation += "fld @doub_const" + doub_consts +"\n"/*+ ", _" + t.getV1()+ "\nfld @Faux" + auxVarCountF + "\n"*/;
            }
            else{
                translation += /*"mov @Faux" + (++auxVarCountF) + ", _" + t.getV1().replaceAll(":", "_") + */"fld _"+ t.getV1().replaceAll(":","_") +"\n";
            }
        }

        translation += "fcom\nfstsw "+sys_aux+"\nmov "+r1_2B+", "+sys_aux+"\nsahf\nmov "+r4_2B+", 0\nmov "+r3_2B+", 0\n";

        switch(t.getAction()){
            case "<":
                translation += "setb "+r4_1B+"\npush "+r4_2B+"\n";
                break;
            case "<=":
                translation += "setb "+r4_1B+"\nsete "+r3_1B+"\nor "+r3_2B+", "+r4_2B+"\npush "+r3_2B+"\n";
                break;
            case ">":
                translation += "seta "+r4_1B+"\npush "+r4_2B+"\n";
                break;
            case ">=":
                translation += "seta "+r4_1B+"\nsete "+r3_1B+"\nor "+r3_2B+", "+r4_2B+"\npush "+r3_2B+"\n";
                break;
            case "==":
                translation += "sete "+r4_1B+"\npush "+r4_2B+"\n";
                break;
            case "<>":
                translation += "setne "+r4_1B+"\npush "+r4_2B+"\n";
                break;
        }

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

        translation += "cmp "+r1+", "+rightOperand+"\nmov "+r4_2B+", 0\n";

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
        }else {
            if(utils.isNumber(t.getV1())) {
                translation += "mov "+conversionVar+", "+t.getV1()+"\n";
                operand = conversionVar;
            }
            else
                operand = "_"+t.getV1().replaceAll(":", "_");
        }
        t.setAux("@Faux"+(++auxVarCountF));
        translation += "fild "+operand+"\n" + "fstp @Faux"+auxVarCountF+"\n";
        return translation;
    }

    public String translateVarDec(Terceto t){
        String translation = "";
        switch(t.getV2()){
            case "uint":
                translation += "\t_"+t.getV1().replaceAll(":","_")+" "+UINT_type+" 0\n";
                break;
            case "double":
                translation += "\t_"+t.getV1().replaceAll(":","_")+" "+DOUB_type+" 0.0\n";
                break;
        }
        return translation;
    }

    public String translateArithF(Terceto t){
        String translation = "";
        String leftOperand;
        String rightOperand;

        if(t.isV2IsTerceto()) {
            int tercetoIndex = Integer.parseInt(t.getV2().replace("[","").replace("]",""));
            rightOperand = this.tercetos.get(tercetoIndex-1).getAux();
        }else {
            if(utils.isNumber(t.getV2())) {
                this.dataSection += "\t@doub_const"+(++doub_consts)+" "+DOUB_type+" "+t.getV2()+"\n";
                rightOperand = "@doub_const" + doub_consts /*+ ", _" + t.getV1()+ "\nfld @Faux" + auxVarCountF + "\n"*/;
            }
            else{
                rightOperand = /*"mov @Faux" + (++auxVarCountF) + ", _" + t.getV1().replaceAll(":", "_") + */"_"+t.getV2().replaceAll(":", "_");
            }
        }

        if(t.isV1IsTerceto()) {
            int tercetoIndex = Integer.parseInt(t.getV1().replace("[", "").replace("]", ""));
            leftOperand = this.tercetos.get(tercetoIndex - 1).getAux();
            translation += "fld "+leftOperand+"\n";
        }
        else {
            if(utils.isNumber(t.getV1())) {
                this.dataSection += "\t@doub_const"+(++doub_consts)+" "+DOUB_type+" "+t.getV1()+"\n";
                translation += "fld @doub_const" + doub_consts +"\n";
            }
            else{
                translation += "fld _"+ t.getV1().replaceAll(":","_") +"\n";
            }
        }

        translation += "fld "+rightOperand+"\n";

        switch(t.getAction())
        {
            case "+":
                translation += "fadd \n";
                break;
            case "-":
                translation += "fsub \n";
                break;
            case "*":
                translation += "fmul \n";
                break;
            case "/":
                translation += "ftst\n" +
                        "fstsw "+sys_aux+"\n" +
                        "mov "+r1_2B+", "+sys_aux+"\n" +
                        "sahf\nje error_divisor_0\nfdiv \n";
                break;
        }
        t.setAux("@Faux"+(++auxVarCountF));
        translation += "fstp @Faux"+auxVarCountF+"\n";
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
                translation +=  "mul "+r3+"\n"+
                                "cmp "+r1+", "+utils.UINT_MAX+"\n"+
                                "jg error_overflow_mult\n"+
                                "cmp "+r1+", "+utils.UINT_MIN+"\n"+
                                "jl error_overflow_mult\n";
                break;
            case "/":
                translation += "mov "+r4+", 0\ncmp "+r3+", 0\nje error_divisor_0\ndiv "+r3+"\n";
                break;
        }
        t.setAux("@aux"+(++auxVarCount));
        translation += "mov @aux"+auxVarCount+", "+r1+"\n";
        return translation;
    }

    public String translateAssignmentF(Terceto t){
        String translation = "";
        String leftOperand;
        String rightOperand;

        if(t.isV2IsTerceto()) {
            int tercetoIndex = Integer.parseInt(t.getV2().replace("[","").replace("]",""));
            rightOperand = this.tercetos.get(tercetoIndex-1).getAux();
        }else {
            if(utils.isNumber(t.getV2())) {
                this.dataSection += "\t@doub_const"+(++doub_consts)+" "+DOUB_type+" "+t.getV2()+"\n";
                rightOperand = "@doub_const" + doub_consts;
            }
            else{
                rightOperand = "_"+ t.getV2().replaceAll(":","_");
            }
        }
        translation += "fld "+rightOperand+"\n";
        leftOperand = utils.isNumber(t.getV1()) ? t.getV1() : "_"+t.getV1().replaceAll(":", "_");
        translation += "fstp "+leftOperand+"\n";
        return translation;
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
