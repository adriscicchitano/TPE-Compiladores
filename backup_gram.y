%{
package StateTransitionMatrix;
import Text.Text;
import Utils.utils;
import java.io.IOException;
import Structures.SymbolTableValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import IntermediateCode.CurrentScope;
import IntermediateCode.Terceto;
%}

%token ASIG ID CTE_UINT CTE_DOUBLE UINT DOUBLE STRING PRINT MAYOR_IGUAL MENOR_IGUAL IGUAL DISTINTO OR AND IF THEN ELSE ENDIF BREAK BEGIN END WHILE DO FUNC RETURN PRE WRONG_STRING CTE_STRING
%%
programa						:	nombre_programa bloque_sentencias_declarativas
									{
										this.tercetos.add(new Terceto("main", "-", "-", null));
									}
									BEGIN sentencias_ejecutables END';'
									{	
										showResults();
										int i = 1;
										for(Terceto t : this.tercetos) {
											System.out.println(i + "\t->\t" + t);
											i++;
									  }
									}
								;
nombre_programa					: 	ID {CurrentScope.addScope($1.sval);}
								;
bloque_sentencias_declarativas	:	sentencias_declarativas
								;
sentencias_declarativas			:	sentencias_declarativas sentencia_declarativa |
									sentencia_declarativa
								;
sentencia_declarativa			:	tipo lista_de_variables ';' 
									{
										su.addCodeStructure("Declaracion de variables " + $1.sval);
										su.changeSTValues(idList,$1.sval,"VARIABLE");
										su.changeSTKeys(idList,concatenateScope(idList,CurrentScope.getScope()));
										for(String var : idList){
											this.tercetos.add(new Terceto(
												"var",
												su.searchForKey(var, CurrentScope.getScope()), 
												$1.sval,
												$1.sval
											));
										}
									}|
									funcion |
									FUNC lista_de_variables';' {su.addCodeStructure("Declaracion de variables de tipo FUNC");su.changeSTValues(idList,"FUNC","VARIABLE");su.changeSTKeys(idList,concatenateScope(idList,CurrentScope.getScope()));} |
									tipo lista_de_variables {su.addError(" falta ; luego de la sentencia", "Sintáctica");} |
									FUNC lista_de_variables {su.addError(" falta ; luego de la sentencia", "Sintáctica");} |
									lista_de_variables ';'{su.addError(" la/las variables no tienen tipo", "Sintáctica");}
								;
lista_de_variables				:	lista_de_variables ',' ID {idList.add($3.sval);}|
									ID {idList = new ArrayList<>(); idList.add($1.sval);}
								;
header_funcion					:	
									tipo FUNC ID '('parametro')' 
									{
										$$.sval = $3.sval;
										$$.type = $1.sval;
										su.addCodeStructure("Declaracion de la funcion " + $3.sval);
										su.changeSTValues($3.sval,$1.sval,"FUNC", $5.sval + ":" + CurrentScope.getScope() + ":" + $3.sval);
										su.changeSTKey($3.sval,$3.sval + ":" + CurrentScope.getScope());
										
										this.tercetos.add(new Terceto(
												"var",
												"param_"+$3.sval+":"+CurrentScope.getScope(), 
												$5.type,
												$5.type
											));
										
										su.addSymbolToTable("param_"+$3.sval+":"+CurrentScope.getScope(), "");
										su.changeSTValues("param_"+$3.sval+":"+CurrentScope.getScope(), $5.type, "VARIABLE");
										
										CurrentScope.addScope($3.sval);
										su.changeSTKey($5.sval,$5.sval + ":" + CurrentScope.getScope());
										this.tercetosFuncs.push(new Terceto(
											"tag",
											su.searchForKey($3.sval, CurrentScope.getScope()),
											$5.sval + ":" + CurrentScope.getScope(),
											su.getSymbolsTableValue(su.searchForKey($3.sval, CurrentScope.getScope())).getType() + "-" +
											$5.type.toLowerCase()
											));
											
									}
								;
cuerpo_funcion					:	
									BEGIN sentencias_ejecutables RETURN '('retorno')'';' END';' 
									{
										$$.sval = $5.sval;
										$$.type = $5.type;
									}|
									BEGIN pre_condicion sentencias_ejecutables RETURN '('retorno')'';' END';' 
									{
										$$.sval = $6.sval;
										$$.type = $6.type;
									}|
									BEGIN sentencias_ejecutables RETURN '('retorno')'';' END {su.addError(" falta ; luego del END", "Sintáctica");} |
									BEGIN pre_condicion sentencias_ejecutables RETURN '('retorno')'';' END {su.addError(" falta ; luego del END", "Sintáctica");}
								;
funcion							:	header_funcion bloque_sentencias_declarativas {this.tercetos.add(this.tercetosFuncs.pop());} cuerpo_funcion 
									{	
										ParserVal l = new ParserVal($1.sval);
										l.type = $1.type;
										addTercetos(":=", l, $4, $$);
										this.tercetos.add(new Terceto("ret", "-", "-", l.type));
										CurrentScope.deleteScope();
									} |
									
									header_funcion {this.tercetos.add(this.tercetosFuncs.pop());} cuerpo_funcion 
									{	
										ParserVal l = new ParserVal($1.sval);
										l.type = $1.type;
										addTercetos(":=", l, $3, $$);
										this.tercetos.add(new Terceto("ret", "-", "-", l.type));
										CurrentScope.deleteScope();
									}
									
								;
pre_condicion					:	PRE':' condicion 
									{
										this.t = this.tercetosjump.remove(this.tercetosjump.size() - 1);
										this.t.setAction("BT");
										this.tercetosjump.add(this.t);
									}
									',' CTE_STRING ';'
									{
										this.t = this.tercetosjump.remove(this.tercetosjump.size() - 1);
										this.tercetos.add(new Terceto (
																"Pre",
																$6.sval,
																"-",
																null
																));
										this.tercetos.add(new Terceto (
																"Label"+(this.tercetos.size()+1),
																"",
																"",
																null
																));
										this.t.setV2(String.valueOf("Label"+this.tercetos.size()));
									}|
									PRE error ';' {su.addError(" Se encontro un error no considerado por la gramatica luego del PRE", "Sintáctica");}
								;
retorno							:	expresion 
									{
										$$.sval = $1.sval;
										$$.type = $1.type;
									}
								;
parametro						:	tipo ID 
									{
										su.changeSTValues($2.sval,$1.sval,"PARAM");
										$$.sval = $2.sval;
										$$.type = $1.sval;
									}
								;
sentencias_ejecutables			: 	sentencias_ejecutables sentencia_ejecutable |
									sentencia_ejecutable 
								;

sentencia_ejecutable			:	asignacion {su.addCodeStructure("ASIGNACION");} |
									clausula_seleccion {su.addCodeStructure("SENTENCIA IF");}|
									sentencia_print {su.addCodeStructure("SENTENCIA PRINT");}|
									while {su.addCodeStructure("WHILE");}
								;
clausula_seleccion				:
									IF condicion bloque_then
															{
																this.t = this.tercetosjump.remove(this.tercetosjump.size() - 1);
																this.t2 = new Terceto(
																		"BI",
																		"",
																		"",
																		null
																		);															
																this.tercetos.add(this.t2);
																this.tercetosjump.add(this.t2);
																this.tercetos.add(new Terceto (
																						"Label"+(this.tercetos.size()+1),
																						"",
																						"",
																						null
																						));
																this.t.setV2(String.valueOf("Label"+this.tercetos.size()));
															}
									bloque_else ENDIF ';' 
															{
																this.tercetos.add(new Terceto (
																						"Label"+(this.tercetos.size()+1),
																						"",
																						"",
																						null
																						));
																this.t = this.tercetosjump.remove(this.tercetosjump.size() - 1);															
																this.t.setV1(String.valueOf("Label"+this.tercetos.size()));
															}|
									IF condicion bloque_then ENDIF ';' 
															{
																this.tercetos.add(new Terceto (
																						"Label"+(this.tercetos.size()+1),
																						"",
																						"",
																						null
																						));
																this.t = this.tercetosjump.remove(this.tercetosjump.size() - 1);
																this.t.setV2(String.valueOf("Label"+this.tercetos.size()));	
															}|
									IF error ';'{su.addError(" Se encontro un error no considerado por la gramatica luego del IF", "Sintáctica");}
bloque_then						:
									THEN BEGIN sentencias_ejecutables END';' |
									THEN sentencia_ejecutable |
									THEN error ';' {su.addError(" Se encontro un error no considerado por la gramatica luego del THEN", "Sintáctica");}
								;
bloque_else						:
									ELSE BEGIN sentencias_ejecutables END';' |
									ELSE sentencia_ejecutable |
									ELSE error ';' {su.addError(" Se encontro un error no considerado por la gramatica luego del ELSE", "Sintáctica");}
								;
while							:	
									WHILE 
									{
										this.t = new Terceto (
																"Label"+(this.tercetos.size()+1),
																"",
																"",
																null
																);
										this.tercetos.add(this.t);
										this.tercetosjump.add(this.t);
									}
									condicion bloque_while
													{	
														if (this.tercetosjump.get(this.tercetosjump.size() - 1).getAction().equals("BREAK"))  {
															this.t3 = this.tercetosjump.remove(this.tercetosjump.size() - 1);
															this.t3.setV1(String.valueOf("Label"+(this.tercetos.size()+2)));
															this.t3.setAction("BI");
														}
														this.t = this.tercetosjump.remove(this.tercetosjump.size() - 1);
														this.t2 = this.tercetosjump.remove(this.tercetosjump.size() - 1);
														this.tercetos.add(new Terceto(
				      											"BI",
				      											String.valueOf("Label"+(this.tercetos.indexOf(this.t2)+1)),
				      											"",
				      											null
			      												));
														this.tercetos.add(new Terceto (
																						"Label"+(this.tercetos.size()+1),
																						"",
																						"",
																						null
																						));
														this.t.setV2(String.valueOf("Label"+this.tercetos.size()));
														
													}|
									WHILE error ';' {su.addError(" Se encontro un error no considerado por la gramatica luego del WHILE", "Sintáctica");}
								;
bloque_while					:
									DO BEGIN sentencias_ejecutables_while END ';' |
									DO sentencia_ejecutable_while |
									DO error ';' {su.addError(" Se encontro un error no considerado por la gramatica luego del DO", "Sintáctica");}
								;
sentencias_ejecutables_while	: 	sentencias_ejecutables_while sentencia_ejecutable_while | 
									sentencia_ejecutable_while
								;
sentencia_ejecutable_while		: 	sentencia_ejecutable |
									BREAK';'
									{
										this.t = new Terceto(
												"BREAK",
												"["+this.tercetos.size()+"]",
												"",
												null
											);
										this.tercetos.add(this.t);
										this.tercetosjump.add(this.t);
									}
								;
								
llamado_funcion					:	
									ID '('factor')' 
									{
										su.addCodeStructure("LLamado a funcion " + $1.sval);
										String correctFunc = su.searchForKey($1.sval,CurrentScope.getScope());
										if(correctFunc == null){
											su.addError("No se puede realizar el llamado a la funcion", "Semantica");
											break;
										}
										SymbolTableValue v = su.getSymbolsTableValue(correctFunc);
										if(v.getUse().equals("FUNC")){
											$$.type = v.getType();
											ParserVal l = new ParserVal("param_"+$1.sval+":"+CurrentScope.getScope());
											l.type = su.getSymbolsTableValue("param_"+$1.sval+":"+CurrentScope.getScope()).getType();
											if(l.type == null){
												l.type = su.getSymbolsTableValue(su.searchForKey("param_"+$1.sval,CurrentScope.getScope())).getType();
											}
											
											addTercetos(":=", l, $3, $$);
											
											this.tercetos.add(new Terceto(
												"call",
												su.searchForKey($1.sval,CurrentScope.getScope()),
												"-",
												v.getType()
											));
										}else{
											su.addError("No se puede llamar a otra cosa que no sea una funcion", "Semantica");
										}
									}
								;

condicion						:	'('condicion_AND')' 
									{
											this.t = new Terceto(
													"BF",
													"["+this.tercetos.size()+"]",
													"",
													null
												);
											this.tercetos.add(this.t);
											this.tercetosjump.add(this.t);
									}|
									'('condicion_AND {su.addError(" falta parentesis de cierre en la condicion", "Sintáctica");}  |
									condicion_AND')'{su.addError(" falta parentesis de apertura en la condicion", "Sintáctica");}
								;
condicion_AND					:	condicion_AND AND condicion_OR 
									{
										this.tercetos.add(getArithTerceto("&&",$1.sval,$3.sval,$$.type));
										$$.sval = "["+this.tercetos.size()+"]";
									}|
									condicion_OR
									{
										$$.sval = $1.sval;
										$$.type = $1.type;
									}
								;
condicion_OR					:	condicion_OR OR condicion_simple 
									{
										this.tercetos.add(getArithTerceto("||",$1.sval,$3.sval,$$.type));
										$$.sval = "["+this.tercetos.size()+"]";
									}|
									condicion_simple
									{
										$$.sval = $1.sval;
										$$.type = $1.type;
									}
								;
condicion_simple				:	condicion_simple '>' expresion 
									{
										addTercetos(">", $1, $3, $$);
									}|
									condicion_simple '<' expresion 
									{
										addTercetos("<", $1, $3, $$);
									}|
									condicion_simple MAYOR_IGUAL expresion 
									{
										addTercetos(">=", $1, $3, $$);
									}|
									condicion_simple MENOR_IGUAL expresion 
									{
										addTercetos("<=", $1, $3, $$);
									}|
									condicion_simple IGUAL expresion 
									{
										addTercetos("==", $1, $3, $$);
									}|
									condicion_simple DISTINTO expresion 
									{
										addTercetos("<>", $1, $3, $$);
									}|
									expresion 
									{
										$$.sval = $1.sval;
										$$.type = $1.type;
									}|
									condicion_simple '=' expresion {su.addError("No se reconoce el comparador", "Sintáctica");} |
									condicion_simple ':' expresion {su.addError("No se reconoce el comparador", "Sintáctica");}

								;
sentencia_print					:	PRINT to_print ';'
									{
										this.tercetos.add(new Terceto(
											"print",
											$2.sval,
											"-",
											null
										));
									}	
								;
to_print						:	'('CTE_STRING')' 
									{
										$$.sval = $2.sval;
										System.err.println($$.sval);
									}|
									'('CTE_STRING {su.addError(" falta parentesis de cierre para la sentencia PRINT", "Sintáctica");} |
									CTE_STRING')' {su.addError(" falta parentesis de apertura para la sentencia PRINT", "Sintáctica");} |
									'('WRONG_STRING {su.addError("STRING mal escrito", "Sintáctica");}
									
								;
asignacion						:	ID ASIG expresion ';'
									{
										String correctKey = su.searchForKey($1.sval.split(":")[0], CurrentScope.getScope());
										if(su.getSymbolsTableValue(correctKey).getType().toLowerCase().contains("func")){
											String correctFunc = su.searchForKey(val_peek(1).sval.split(":")[0], CurrentScope.getScope());
											List<String> aux = Arrays.asList(correctFunc.split(":"));
                                            String paramType = su.getSymbolsTableValue(su.getSymbolsTableValue(correctFunc).getParameter()).getType();
											if(su.getSymbolsTableValue(correctFunc).getUse().toLowerCase().contains("func")){
												ParserVal l = new ParserVal(correctKey);l.setType("func");
												ParserVal r = new ParserVal(correctFunc);r.setType(su.getSymbolsTableValue(correctFunc).getType().toLowerCase());
												addTercetos("func_assig", l, r, $$);
												
												Terceto auxT = this.tercetos.remove(this.tercetos.size()-1);
												auxT.setType(r.type);
												this.tercetos.add(auxT);
												
												su.changeSTValues(correctKey, r.type, "FUNC", su.getSymbolsTableValue(correctFunc).getParameter());
												this.tercetos.add(new Terceto(
													"var",
													"param_"+$1.sval+":"+CurrentScope.getScope(), 
													paramType,
													paramType
												));
												su.addSymbolToTable("param_"+$1.sval+":"+CurrentScope.getScope(), "");
												su.changeSTValues("param_"+$1.sval+":"+CurrentScope.getScope(), paramType, "VARIABLE");
											}else{
												su.addError("No se puede asignar algo que no sea una funcion cuando el lado izquierdo es de tipo FUNC", "Semantica");
											}
										}else{
											String leftKey = su.searchForKey($1.sval, CurrentScope.getScope());
											if(leftKey == null || $3.type == null){
												su.addError("No se puede realizar la asignacion","Semantica");
												break;
											}
											char conv = whoConverts(su.getSymbolsTableValue(leftKey).getType().toLowerCase(), $3.type);
											if(conv == 'L'){
												su.addError("No se puede realizar la asignacion por que el tipo del lado izquierdo no es convertible","Semantica");
											}
											else{
												if(conv == 'R'){
													this.tercetos.add(new Terceto("utod", $3.sval, "-", "double"));
													$3.sval = "["+this.tercetos.size()+"]";
												}
											}
											this.tercetos.add(getArithTerceto(":=",correctKey,$3.sval,su.getSymbolsTableValue(correctKey).getType().toLowerCase()));
										}
									}
								;
expresion						:	expresion '+' termino 
									{
										addTercetos("+", $1, $3, $$);

									}|
									expresion '-' termino 
									{
										addTercetos("-", $1, $3, $$);
									}|
									termino 
									{
										$$.sval = $1.sval;
										$$.type = $1.type;
									}
								;
termino							:	termino '*' factor 
									{										
										addTercetos("*", $1, $3, $$);
									}|
									termino '/' factor 
									{
										addTercetos("/", $1, $3, $$);
									}|
									factor 
									{
										$$.sval = $1.sval;
										$$.type = $1.type;
									}
								;
factor							:	ID 
									{
										$$.sval = $1.sval+":"+CurrentScope.getScope(); 
										String correctKey = su.searchForKey($1.sval, CurrentScope.getScope());
										if(correctKey != null)
											$$.type = su.getSymbolsTableValue(correctKey).getType().toLowerCase();
										else
											su.addError("La variable no fue declarada", "Semantica");
									}| 
									uint_factor {$$.sval = $1.sval;$$.type = "uint";}|
									double_factor {$$.sval = $1.sval;$$.type = "double";} |
									llamado_funcion {$$.type = $1.type; $$.sval = "["+this.tercetos.size()+"]";}
								;
uint_factor						:	CTE_UINT {$$.sval = $1.sval;} |
									'-' CTE_UINT {su.addError("UINT no puede ser negativo", "Sintáctica");}
								;
double_factor					:	CTE_DOUBLE {$$.sval = $1.sval;} |
									'-' CTE_DOUBLE {$$.sval = "-"+$2.sval; setToNegative($2.sval);}
								;
tipo							: 	UINT {$$.sval = "uint";}|
									DOUBLE {$$.sval = "double";} 
								;
%%
  private Text text;
  private LexicAnalyzer lexicAnalyzer;
  private StructureUtilities su;
  private List<String> idList;
  public List<Terceto> tercetos = new ArrayList<>();
  private List<Terceto> tercetosjump = new ArrayList<>();
  private Stack<Terceto> tercetosFuncs = new Stack<>();
  private Terceto t; 
  private Terceto t2;
  private Terceto t3; 
  boolean isFunction = false;

  
  public Parser(Text text, boolean debug){
    this.text = text;
	this.su = new StructureUtilities(text);
    this.lexicAnalyzer = new LexicAnalyzer(text,su);
    yydebug=debug;
  }

  public Parser(Text text){
    this.text = text;
	this.su = new StructureUtilities(text);
    this.lexicAnalyzer = new LexicAnalyzer(text,su);
  }

  public void compile(){
	this.run();
	if(!su.hasErrors()){
		System.out.println("La compilacion fue correcta");
	}else{
      System.out.println("Se encontraron errores en la compilacion");
		try {
			utils.exportErrors(su);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
  }

  private void showResults(){
	try {
      utils.exportResults(su, this.tercetos);
    } catch (IOException e) {
      e.printStackTrace();
    }
    //System.out.println("Tokens: \n" + su.showTokens());
    //System.out.println("Errores: \n" + su.showErrors());
    //System.out.println("Warnings: \n" + su.showWarnings());
    //System.out.println("Simbolos: \n" + su.showSymbolsTable());
  }

  private void setToNegative(String constant){
    this.su.setToNegative(constant);
  } 
  
  private void addTercetos(String operator, ParserVal left, ParserVal right, ParserVal result){
	if(!(left.sval.startsWith("[") && left.sval.endsWith("]"))){
		String correctLeftKey = su.searchForKey(left.sval.split(":")[0], CurrentScope.getScope());
		if(correctLeftKey == null){
			su.addError("La variable no esta al alcance", "Semantica");
			return;
		}else
			left.sval = correctLeftKey;
	}
	
	if(!(right.sval.startsWith("[") && right.sval.endsWith("]"))){
		String correctRightKey = su.searchForKey(right.sval.split(":")[0], CurrentScope.getScope());
		if(correctRightKey == null){
			su.addError("La variable no esta al alcance", "Semantica");
			return;
		}else
			right.sval = correctRightKey;
	}
	
	if(left.type == null || right.type == null){
		su.addError("La variable no tiene tipo", "Semantica");
		return;
	}
	
	char conv = whoConverts(left.type, right.type);
	if(conv == 'L'){
		this.tercetos.add(new Terceto("utod", left.sval, "-", "double"));
		left.sval = "["+this.tercetos.size()+"]";
	}
	else{
		if(conv == 'R'){
			this.tercetos.add(new Terceto("utod", right.sval, "-", "double"));
			right.sval = "["+this.tercetos.size()+"]";
		}
	}
	result.type = left.type.equals("func") ? "func" : conv != 'N' ? "double" : left.type;
	this.tercetos.add(getArithTerceto(operator,left.sval,right.sval,result.type));
	result.sval = "["+this.tercetos.size()+"]";
  }
  
  private char whoConverts(String leftType, String rightType) { 
	  
    if(!leftType.equals(rightType) && !leftType.contains("func")){
      if(leftType.toLowerCase().contains("double") && rightType.toLowerCase().contains("uint"))
        return 'R';
      else
        if(leftType.toLowerCase().contains("uint") && rightType.toLowerCase().contains("double"))
          return 'L';
        else
          su.addError("No es posible realizar la conversion", "Semantica");
      return 'X';
    }else
      return 'N';
  }

  private Terceto getArithTerceto(String operation, String leftValue, String rightValue, String type){
    return new Terceto(operation, leftValue, rightValue, type);
  }  
  
  private List<String> concatenateScope(List<String> list, String scope){
	  List<String> result = new ArrayList<>();
	  for(String s : list)
		  result.add(s + ":" + scope);
	  return result;
  }	  

  private int yylex(){
	int code = this.lexicAnalyzer.yylex();
	String reference = this.lexicAnalyzer.getSymbolsTableReference();
	if(reference != null) {
              ParserVal v = new ParserVal(reference);
              v.setType(this.su.getSymbolsTableValue(reference).getType());
              yylval = new ParserVal(reference);
    }
    return code;
  }

  private void yyerror(String err){
    //su.addError(err);
  }