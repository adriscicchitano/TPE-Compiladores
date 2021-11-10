%{
package StateTransitionMatrix;
import Text.Text;
import Utils.utils;
import java.io.IOException;
import Structures.SymbolTableValue;
import java.util.ArrayList;
import java.util.List;
import IntermediateCode.CurrentScope;
import IntermediateCode.Terceto;
%}

%token ASIG ID CTE_UINT CTE_DOUBLE UINT DOUBLE STRING PRINT MAYOR_IGUAL MENOR_IGUAL IGUAL DISTINTO OR AND IF THEN ELSE ENDIF BREAK BEGIN END WHILE DO FUNC RETURN PRE WRONG_STRING CTE_STRING
%%
programa						:	nombre_programa bloque_sentencias_declarativas bloque_sentencias_ejecutables 
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
sentencia_erronea				:	error ';' {su.addError("Se encontro un error no considerado por la gramatica", "Sintáctica");}
								;
bloque_sentencias_declarativas	:	sentencias_declarativas
								;
sentencias_declarativas			:	sentencias_declarativas sentencia_declarativa |
									sentencia_declarativa
								;
sentencia_declarativa			:	tipo lista_de_variables ';' {su.addCodeStructure("Declaracion de variables " + $1.sval);su.changeSTValues(idList,$1.sval,"VARIABLE");su.changeSTKeys(idList,concatenateScope(idList,CurrentScope.getScope()));}|
									funcion';' |
									FUNC lista_de_variables';' {su.addCodeStructure("Declaracion de variables de tipo FUNC");su.changeSTValues(idList,"FUNC","VARIABLE");su.changeSTKeys(idList,concatenateScope(idList,CurrentScope.getScope()));} |
									tipo lista_de_variables {su.addError(" falta ; luego de la sentencia", "Sintáctica");} |
									funcion {su.addError(" falta ; luego de la sentencia", "Sintáctica");} |
									FUNC lista_de_variables {su.addError(" falta ; luego de la sentencia", "Sintáctica");} |
									lista_de_variables ';'{su.addError(" la/las variables no tienen tipo", "Sintáctica");} |
									sentencia_erronea
								;
lista_de_variables				:	lista_de_variables ',' ID {idList.add($3.sval);}|
									ID {idList = new ArrayList<>(); idList.add($1.sval);}
								;
header_funcion					:	tipo FUNC ID '('parametro')' {su.addCodeStructure("Declaracion de la funcion " + $3.sval);su.changeSTValues($3.sval,$1.sval,"FUNC");CurrentScope.addScope($3.sval);su.changeSTKey($5.sval,$5.sval + "." + CurrentScope.getScope());}
								;
funcion							:	header_funcion bloque_sentencias_declarativas BEGIN sentencias_ejecutables RETURN '('retorno')'';' END {CurrentScope.deleteScope();}|
									header_funcion bloque_sentencias_declarativas BEGIN pre_condicion sentencias_ejecutables RETURN '('retorno')'';' END {CurrentScope.deleteScope();} 
								;

pre_condicion					:	PRE':' condicion ',' CTE_STRING ';'|
									PRE':' condicion ',' CTE_STRING {su.addError(" falta ; luego de PRE", "Sintáctica");}|
									PRE condicion ',' CTE_STRING ';' {su.addError(" falta : luego de PRE", "Sintáctica");}|
									PRE':' condicion CTE_STRING ';'{su.addError(" falta , luego de la condicion en PRE", "Sintáctica");} |
									PRE':' ',' CTE_STRING ';'{su.addError(" falta falta la condicion para la sentencia PRE", "Sintáctica");} |
									PRE':' condicion ',' ';'{su.addError(" falta la cadena de caracteres luego de la ,", "Sintáctica");} |
									PRE':' condicion ';'{su.addError(" falta , y la cadena de caracteres luego de la condicion", "Sintáctica");} |
									PRE':' condicion ',' WRONG_STRING {su.addError("El STRING es incorrecto", "Sintáctica");}
								;
retorno							:	expresion
								;
parametro						:	tipo ID {su.changeSTValues($2.sval,$1.sval,"PARAM");$$.sval = $2.sval;}
								;

bloque_sentencias_ejecutables	:	BEGIN sentencias_ejecutables END';' |
									BEGIN END ';'	
								;
sentencias_ejecutables			: 	sentencias_ejecutables sentencia_ejecutable |
									sentencia_ejecutable 
								;

sentencia_ejecutable			:	asignacion';' {su.addCodeStructure("ASIGNACION");} |
									clausula_seleccion';' {su.addCodeStructure("SENTENCIA IF");}|
									sentencia_print';' {su.addCodeStructure("SENTENCIA PRINT");}|
									while';' {su.addCodeStructure("WHILE");} |
									asignacion {su.addError(" falta ; luego de la sentencia", "Sintáctica");} |
									clausula_seleccion {su.addError(" falta ; luego de la sentencia", "Sintáctica");}|
									sentencia_print {su.addError(" falta ; luego de la sentencia", "Sintáctica");}|
									while {su.addError(" falta ; luego de la sentencia", "Sintáctica");} |
									sentencia_erronea
								;

while							:	WHILE condicion DO bloque_sentencias_while 
													{
														t = this.tercetosjump.get(this.tercetos.size() - 1);
														t.set_v2(String.valueOf((Integer)tercetos.size()));
														t2 = new Terceto(
				      											"BI",
				      											"["+this.tercetos.size()+"]",
				      											String.valueOf((Integer)(this.tercetos.indexOf(t) - 1)),
				      											null
			      												);
			      											this.tercetos.add(t2)															
														
													}|
									WHILE DO bloque_sentencias_while {su.addError(" falta la condicion de corte del WHILE", "Sintáctica");}|
									WHILE condicion bloque_sentencias_while {su.addError(" falta DO en la sentencia WHILE", "Sintáctica");}
								;
bloque_sentencias_while			:	BEGIN sentencias_ejecutables_while END |
									BEGIN END
								;
sentencias_ejecutables_while	: 	sentencias_ejecutables_while sentencia_ejecutable_while | 
									sentencia_ejecutable_while
								;
sentencia_ejecutable_while		: 	sentencia_ejecutable |
									BREAK';' |
									BREAK {su.addError(" falta ; luego de la sentencia", "Sintáctica");}
								;

llamado_funcion					:	ID '('factor')' {su.addCodeStructure("LLamado a funcion " + $1.sval);}
								;

clausula_seleccion				:	IF condicion THEN bloque_sentencias_ejecutables ELSE	{
															t = this.tercetosjump.get(this.tercetos.size() - 1);
															t.set_v2(String.valueOf((Integer)tercetos.size()));
															t2 = new Terceto(
					      											"BI",
					      											"["+this.tercetos.size()+"]",
					      											"",
					      											null
				      												);															
				      										        this.tercetos.add(t2);
				      										        this.tercetosjump.add(t2);															
														} bloque_sentencias_ejecutables ENDIF
														{
															t = this.tercetosjump.get(this.tercetos.size() - 1);															t = this.tercetosjump.get(this.tercetos.size() - 1);
															t.set_v2(String.valueOf((Integer)tercetos.size()));
														} |
									IF condicion THEN bloque_sentencias_ejecutables ENDIF{
																	t = this.tercetosjump.get(this.tercetos.size() - 1);
																	t.set_v2(String.valueOf((Integer)tercetos.size()));	
																} |
									IF condicion bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables ENDIF {su.addError(" falta THEN en la sentencia IF", "Sintáctica");} |
									IF condicion THEN bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables {su.addError(" falta ENDIF en la sentencia IF", "Sintáctica");} |
									IF condicion bloque_sentencias_ejecutables ENDIF {su.addError(" falta THEN en la sentencia IF", "Sintáctica");} |
									IF condicion THEN bloque_sentencias_ejecutables {su.addError(" falta ENDIF en la sentencia IF", "Sintáctica");} |
									IF THEN bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables ENDIF {su.addError(" falta la condicion en la sentencia IF", "Sintáctica");}|
									IF THEN bloque_sentencias_ejecutables ENDIF {su.addError(" falta la condicion en la sentencia IF", "Sintáctica");} |
									IF condicion THEN ELSE bloque_sentencias_ejecutables ENDIF {su.addError(" falta bloque de sentencias luego del THEN", "Sintáctica");}|
									IF condicion THEN ENDIF {su.addError(" falta bloque de sentencias luego del THEN", "Sintáctica");}|
									IF condicion THEN bloque_sentencias_ejecutables ELSE ENDIF  {su.addError(" falta bloque de sentencias luego del ELSE", "Sintáctica");}
								;
condicion						:	'('condicion_AND')' 
									{
										        t = new Terceto(
              											"BF",
              											"["+this.tercetos.size()+"]",
              											"",
              											null
      												);
      										        this.tercetos.add(t);
      										        this.tercetosjump.add(t);
      										        
									}|
									'('condicion_AND {su.addError(" falta parentesis de cierre en la condicion", "Sintáctica");}  |
									condicion_AND')'{su.addError(" falta parentesis de apertura en la condicion", "Sintáctica");}
								;
condicion_AND					:	condicion_AND AND condicion_OR 
									{
										su.addCodeStructure("Se detecta una condicion AND");
										this.tercetos.addAll(this.getTercetos(
											"&&",
											leftTercetoIndexForAND != null && (!leftTercetoIndexForAND.equals("0")) ? leftTercetoIndexForAND : $1.sval,
											itsSingleNumber ? $3.sval : String.valueOf((Integer)tercetos.size()),
											leftTercetoIndexForAND != null && (!leftTercetoIndexForAND.equals("0")), 
											true
										));
										leftTercetoIndexForAND = String.valueOf((Integer)tercetos.size()); 
									}|
									condicion_OR
									{
										leftTercetoIndexForAND = String.valueOf((Integer)tercetos.size()); 
									}
								;
condicion_OR					:	condicion_OR OR condicion_simple 
									{
										su.addCodeStructure("Se detecta una condicion OR");
										this.tercetos.addAll(this.getTercetos(
											"||",
											leftTercetoIndexForOR != null && (!leftTercetoIndexForOR.equals("0")) ? leftTercetoIndexForOR : $1.sval,
											itsSingleNumber ? $3.sval : String.valueOf((Integer)tercetos.size()),
											leftTercetoIndexForOR != null && (!leftTercetoIndexForOR.equals("0")), 
											true
										));
										leftTercetoIndexForOR = String.valueOf((Integer)tercetos.size()); 
									}|
									condicion_simple
									{
										leftTercetoIndexForOR = String.valueOf((Integer)tercetos.size()); 
									}
								;
condicion_simple				:	condicion_simple '>' expresion 
									{
										su.addCodeStructure("Se detecta una condicion >");
										this.tercetos.addAll(this.getTercetos(
											">",
											leftTercetoIndexForComparison != null && (!leftTercetoIndexForComparison.equals("0")) ? leftTercetoIndexForComparison : $1.sval,
											itsSingleNumber ? $3.sval : String.valueOf((Integer)tercetos.size()),
											leftTercetoIndexForComparison != null && (!leftTercetoIndexForComparison.equals("0")), 
											!itsSingleNumber
										));
										leftTercetoIndex = String.valueOf((Integer)tercetos.size());
										itsSingleNumber = false;
									}|
									condicion_simple '<' expresion 
									{
										su.addCodeStructure("Se detecta una condicion <");
										this.tercetos.addAll(this.getTercetos(
											"<",
											leftTercetoIndexForComparison != null && (!leftTercetoIndexForComparison.equals("0")) ? leftTercetoIndexForComparison : $1.sval,
											itsSingleNumber ? $3.sval : String.valueOf((Integer)tercetos.size()),
											leftTercetoIndexForComparison != null && (!leftTercetoIndexForComparison.equals("0")), 
											!itsSingleNumber
										));
										leftTercetoIndex = String.valueOf((Integer)tercetos.size());
										itsSingleNumber = false;
									}|
									condicion_simple MAYOR_IGUAL expresion 
									{
										su.addCodeStructure("Se detecta una condicion >=");
										this.tercetos.addAll(this.getTercetos(
											">=",
											leftTercetoIndexForComparison != null && (!leftTercetoIndexForComparison.equals("0")) ? leftTercetoIndexForComparison : $1.sval,
											itsSingleNumber ? $3.sval : String.valueOf((Integer)tercetos.size()),
											leftTercetoIndexForComparison != null && (!leftTercetoIndexForComparison.equals("0")), 
											!itsSingleNumber
										));
										leftTercetoIndex = String.valueOf((Integer)tercetos.size());
										itsSingleNumber = false;
									}|
									condicion_simple MENOR_IGUAL expresion 
									{
										su.addCodeStructure("Se detecta una condicion <=");
										this.tercetos.addAll(this.getTercetos(
											"<=",
											leftTercetoIndexForComparison != null && (!leftTercetoIndexForComparison.equals("0")) ? leftTercetoIndexForComparison : $1.sval,
											itsSingleNumber ? $3.sval : String.valueOf((Integer)tercetos.size()),
											leftTercetoIndexForComparison != null && (!leftTercetoIndexForComparison.equals("0")), 
											!itsSingleNumber
										));
										leftTercetoIndex = String.valueOf((Integer)tercetos.size());
										itsSingleNumber = false;
									}|
									condicion_simple IGUAL expresion 
									{
										su.addCodeStructure("Se detecta una condicion ==");
										this.tercetos.addAll(this.getTercetos(
											"==",
											leftTercetoIndexForComparison != null && (!leftTercetoIndexForComparison.equals("0")) ? leftTercetoIndexForComparison : $1.sval,
											itsSingleNumber ? $3.sval : String.valueOf((Integer)tercetos.size()),
											leftTercetoIndexForComparison != null && (!leftTercetoIndexForComparison.equals("0")), 
											!itsSingleNumber
										));
										leftTercetoIndex = String.valueOf((Integer)tercetos.size());
										itsSingleNumber = false;
									}|
									condicion_simple DISTINTO expresion 
									{
										su.addCodeStructure("Se detecta una condicion <>");
										this.tercetos.addAll(this.getTercetos(
											"<>",
											leftTercetoIndexForComparison != null && (!leftTercetoIndexForComparison.equals("0")) ? leftTercetoIndexForComparison : $1.sval,
											itsSingleNumber ? $3.sval : String.valueOf((Integer)tercetos.size()),
											leftTercetoIndexForComparison != null && (!leftTercetoIndexForComparison.equals("0")), 
											!itsSingleNumber
										));
										leftTercetoIndex = String.valueOf((Integer)tercetos.size());
										itsSingleNumber = false;
									}|
									expresion 
									{
										comparisonDetected = true;
										leftTercetoIndexForComparison = leftIsTerceto ? String.valueOf((Integer)tercetos.size()) : null; 
										leftIsTerceto = false;
									}|
									condicion_simple '=' expresion {su.addError("No se reconoce el comparador", "Sintáctica");} |
									condicion_simple ':' expresion {su.addError("No se reconoce el comparador", "Sintáctica");}

								;
sentencia_print					:	PRINT to_print 
								;
to_print						:	'('CTE_STRING')' |
									'('CTE_STRING {su.addError(" falta parentesis de cierre para la sentencia PRINT", "Sintáctica");} |
									CTE_STRING')' {su.addError(" falta parentesis de apertura para la sentencia PRINT", "Sintáctica");} |
									'('WRONG_STRING {su.addError("STRING mal escrito", "Sintáctica");}
									
								;
asignacion						:	ID ASIG expresion 
									{
										addAssignmentTercetos($1.sval,$3.sval);
										leftIsTerceto = false;
										rightIsTerceto = false;
										sumDetected = false;
										leftTercetoIndex = null;
										itsSingleNumber = false;
									}
								;
expresion						:	expresion '+' termino 
									{
										su.addCodeStructure("Se detecta una suma");
										this.tercetos.addAll(this.getTercetos(
											"+",
											leftTercetoIndex != null && (!leftTercetoIndex.equals("0")) ? leftTercetoIndex : $1.sval,
											rightIsTerceto ? String.valueOf((Integer)tercetos.size()) : $3.sval,
											leftTercetoIndex != null && (!leftTercetoIndex.equals("0")), 
											rightIsTerceto
										));
										if (comparisonDetected)
											leftIsTerceto = true;
										leftTercetoIndex = String.valueOf((Integer)tercetos.size());
										itsSingleNumber = false;
									}|
									expresion '-' termino 
									{
										su.addCodeStructure("Se detecta una resta");
										this.tercetos.addAll(this.getTercetos(
											"-",
											leftTercetoIndex != null && (!leftTercetoIndex.equals("0")) ? leftTercetoIndex : $1.sval,
											rightIsTerceto ? String.valueOf((Integer)tercetos.size()) :$3.sval,
											leftTercetoIndex != null && (!leftTercetoIndex.equals("0")),
											rightIsTerceto
										));
										if (comparisonDetected)
											leftIsTerceto = true;
										leftTercetoIndex = String.valueOf((Integer)tercetos.size());
										itsSingleNumber = false;
									}|
									termino 
									{
										$$.sval = leftIsTerceto ? String.valueOf((Integer)tercetos.size()) : $1.sval;
										rightIsTerceto = false;
										sumDetected = true; 
										leftTercetoIndex = (leftIsTerceto || comparisonDetected && !itsSingleNumber) ? String.valueOf((Integer)tercetos.size()) : null;
										//itsSingleNumber = !leftIsTerceto;
										if(comparisonDetected && !itsSingleNumber)
											leftIsTerceto = true;
										else 
											leftIsTerceto = false;
										itsSingleNumber = true;
									}
								;
termino							:	termino '*' factor 
									{
										su.addCodeStructure("Se detecta una multiplicacion");
										this.tercetos.addAll(this.getTercetos(
											"*",
											leftIsTerceto ? String.valueOf((Integer)tercetos.size()) : $1.sval,
											$3.sval,
											leftIsTerceto,
											false
										));
										leftIsTerceto = true;
										if (sumDetected)
											rightIsTerceto = true;
										itsSingleNumber = false;
									}|
									termino '/' factor 
									{
										su.addCodeStructure("Se detecta una division");
										this.tercetos.addAll(this.getTercetos(
											"/",
											leftIsTerceto ? String.valueOf((Integer)tercetos.size()) : $1.sval,
											$3.sval,
											leftIsTerceto,
											false
										));
										leftIsTerceto = true;
										if (sumDetected)
											rightIsTerceto = true;
										itsSingleNumber = false;
									}|
									factor 
									{
										$$.sval = $1.sval;
										leftIsTerceto = false;
										rightIsTerceto = false;
										itsSingleNumber = true;
									}
								;
factor							:	ID {$$.sval = $1.sval;}| 
									uint_factor |
									double_factor {$$.sval = $1.sval;} |
									llamado_funcion
								;
uint_factor						:	CTE_UINT {$$.sval = $1.sval;} |
									'-' CTE_UINT {su.addError("UINT no puede ser negativo", "Sintáctica");}
								;
double_factor					:	CTE_DOUBLE {$$.sval = $1.sval;} |
									'-' CTE_DOUBLE {$$.sval = $2.sval; setToNegative($2.sval);}
								;
tipo							: 	UINT {$$.sval = "UINT";}|
									DOUBLE {$$.sval = "DOUBLE";} 
								;
%%
  private Text text;
  private LexicAnalyzer lexicAnalyzer;
  private StructureUtilities su;
  private List<String> idList;
  private List<Terceto> tercetos = new ArrayList<>();
  private List<Terceto> tercetosjump = new ArrayList<>();
  boolean leftIsTerceto = false;
  boolean rightIsTerceto = false;
  boolean sumDetected = false;
  boolean comparisonDetected = false;
  boolean itsSingleNumber = false;
  String leftTercetoIndex;
  String leftTercetoIndexForComparison;
  String leftTercetoIndexForOR;
  String leftTercetoIndexForAND;
  
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
      utils.exportResults(su);
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
  
        private List<Terceto> getTercetos(String operator, String leftValue, String rightValue, boolean leftIsTerceto, boolean rightIsTerceto){
    List<Terceto> aux = new ArrayList<>();
	int amtTercetos = this.tercetos.size();

	boolean v1_isConverted = false;
	boolean v2_isConverted = false;
    boolean v1_isConst = (leftValue.charAt(0) == '-') ? Character.isDigit(leftValue.charAt(1)) || leftValue.charAt(1) == '.' : Character.isDigit(leftValue.charAt(0)) || leftValue.charAt(0) == '.'; 
    boolean v2_isConst = (rightValue.charAt(0) == '-') ? Character.isDigit(rightValue.charAt(1)) || rightValue.charAt(1) == '.' : Character.isDigit(rightValue.charAt(0)) || rightValue.charAt(0) == '.'; 
    // con leftIsTerceto == true leftValue debe ser interpretado como el indice de la lista de tercetos
    String type1 =  leftIsTerceto ? this.tercetos.get(Integer.parseInt(leftValue)-1).getType().toUpperCase() :
                    su.getSymbolsTableValue( v1_isConst ? leftValue : leftValue + "." + CurrentScope.getScope()).getType();
    String type2 =  rightIsTerceto ? this.tercetos.get(Integer.parseInt(rightValue)-1).getType().toUpperCase() :
                    su.getSymbolsTableValue( v2_isConst ? rightValue : rightValue + "." + CurrentScope.getScope()).getType();

    if(!type1.equals(type2) && (type1.contains("DOUBLE") || type2.contains("DOUBLE"))){
      //Si son de distinto tipo, al menos uno es DOUBLE
      Terceto t;
      if(type1.contains("UINT")){
        t = new Terceto("utod", leftIsTerceto ? "["+leftValue+"]" : v1_isConst ? leftValue : leftValue + "." + CurrentScope.getScope(), "-", "double");
        v1_isConverted = true;
        amtTercetos++;
		aux.add(t);
      }else{
        if(type2.contains("UINT")) {
          t = new Terceto("utod", rightIsTerceto ? "["+rightValue+"]" : v2_isConst ? rightValue : rightValue + "." + CurrentScope.getScope(), "-", "double");
          v2_isConverted = true;
		  amtTercetos++;
		  aux.add(t);
        }
      }
      t = new Terceto(
              operator,
              v1_isConverted ? "["+amtTercetos+"]" : leftIsTerceto ? "["+leftValue+"]" : v1_isConst ? leftValue : leftValue + "." + CurrentScope.getScope(),
              v2_isConverted ? "["+amtTercetos+"]" : rightIsTerceto ? "["+rightValue+"]" : v2_isConst ? rightValue : rightValue + "." + CurrentScope.getScope(),
              "double"
      );
      aux.add(t);
    }else {
      Terceto t;
      if(type1.contains("UINT")) {
        //este es el caso de que ambos tipos son UINT
        t = new Terceto(
                operator,
                leftIsTerceto ? "["+leftValue+"]" : v1_isConst ? leftValue : leftValue + "." + CurrentScope.getScope(),
                rightIsTerceto ? "["+rightValue+"]" : v2_isConst ? rightValue : rightValue + "." + CurrentScope.getScope(),
                "uint"
        );
      }else{
        t = new Terceto(
                operator,
                leftIsTerceto ? "["+leftValue+"]" : v1_isConst ? leftValue : leftValue + "." + CurrentScope.getScope(),
                rightIsTerceto ? "["+rightValue+"]" : v2_isConst ? rightValue : rightValue + "." + CurrentScope.getScope(),
                "double"
        );
      }
      aux.add(t);
    }

    return aux;
  }
  
  private void addAssignmentTercetos(String leftSide, String rightSide){
	SymbolTableValue v = su.getSymbolsTableValue(leftSide+"."+CurrentScope.getScope());
	if(v != null){
		String type = v.getType();
		String use = v.getUse();
		if(!use.equals("VARIABLE"))
			su.addError("No es posible realizaz asignaciones a una variable", "Semantica");
		else{
			if(!itsSingleNumber){
				if(type.equals("DOUBLE") && this.tercetos.get(this.tercetos.size() - 1).getType().equals("uint")){
					/*convertir el lado derecho y agregar el terceto*/
					Terceto conversion = new Terceto(
						"utod",
						"["+(this.tercetos.size())+"]",
						"-",
						null
					);
					this.tercetos.add(conversion);
				}
				this.tercetos.add(new Terceto(
					":=",
					leftSide+"."+CurrentScope.getScope(),
					"["+(this.tercetos.size())+"]",
					null
				));

			}else{
				/*hacer logica para convertir el lado derecho si es uint o simplemente asignarlo*/
				boolean isConst = (rightSide.charAt(0) == '-') ? Character.isDigit(rightSide.charAt(1)) || rightSide.charAt(1) == '.' : Character.isDigit(rightSide.charAt(0)) || rightSide.charAt(0) == '.'; 
				String lexeme = isConst ? rightSide : rightSide+"."+CurrentScope.getScope();
				String rightType = su.getSymbolsTableValue(lexeme).getType();
				if(type.equals("DOUBLE")){
					if(rightType.contains("UINT")){
						Terceto conversion = new Terceto(
							"utod",
							isConst ? rightSide : rightSide+"."+CurrentScope.getScope(),
							"-",
							null
						);
						this.tercetos.add(conversion);
						conversion = new Terceto(
							":=",
							leftSide+"."+CurrentScope.getScope(),
							"["+this.tercetos.size()+"]",
							null
						);
						this.tercetos.add(conversion);
					}else{
						this.tercetos.add(new Terceto(
							":=",
							leftSide+"."+CurrentScope.getScope(),
							isConst ? rightSide : rightSide+"."+CurrentScope.getScope(),
							null
						));
					}
				}else{
					if(rightType.contains("DOUBLE"))
						su.addError("No es posible realizaz asignaciones cuando el lado izquierdo es UINT y el derecho no lo es", "Semantica");
					else{
						this.tercetos.add(new Terceto(
							":=",
							leftSide+"."+CurrentScope.getScope(),
							isConst ? rightSide : rightSide+"."+CurrentScope.getScope(),
							null
						));
					}
				}
				
			}
			if(this.tercetos.get(this.tercetos.size() - 1).getType() != null){
				if(type.equals("UINT") && !this.tercetos.get(this.tercetos.size() - 1).getType().equals("uint")){
					su.addError("No es posible realizaz asignaciones cuando el lado izquierdo es UINT y el derecho no lo es", "Semantica");
					/*ver si se hace algo aca, algun terceto o algo*/
				}
			}
		}
	}
	else
		su.addError("El lexema: " + val_peek(2).sval + " no existe en la tabla de simbolos", "Semantica");
  }
  
  private List<String> concatenateScope(List<String> list, String scope){
	  List<String> result = new ArrayList<>();
	  for(String s : list)
		  result.add(s + "." + scope);
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
