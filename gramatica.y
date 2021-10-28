%{
package StateTransitionMatrix;
import Text.Text;
import Utils.utils;
import java.io.IOException;
import Structures.SymbolsTable;
import java.util.ArrayList;
import java.util.List;
import Structures.CurrentScope;
%}

%token ASIG ID CTE_UINT CTE_DOUBLE UINT DOUBLE STRING PRINT MAYOR_IGUAL MENOR_IGUAL IGUAL DISTINTO OR AND IF THEN ELSE ENDIF BREAK BEGIN END WHILE DO FUNC RETURN PRE WRONG_STRING CTE_STRING
%%
programa						:	nombre_programa bloque_sentencias_declarativas bloque_sentencias_ejecutables {showResults();} 
								;
nombre_programa					: 	ID {CurrentScope.addScope($1.sval);System.out.println(CurrentScope.getScope());}
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
header_funcion					:	tipo FUNC ID '('parametro')' {su.addCodeStructure("Declaracion de la funcion " + $3.sval);su.changeSTValues($3.sval,$1.sval,"FUNC");CurrentScope.addScope($3.sval);System.out.println(CurrentScope.getScope());su.changeSTKey($5.sval,$5.sval + "." + CurrentScope.getScope());}
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

while							:	WHILE condicion DO bloque_sentencias_while |
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

clausula_seleccion				:	IF condicion THEN bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables ENDIF |
									IF condicion THEN bloque_sentencias_ejecutables ENDIF |
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
condicion						:	'('condicion_AND')' |
									'('condicion_AND {su.addError(" falta parentesis de cierre en la condicion", "Sintáctica");}  |
									condicion_AND')'{su.addError(" falta parentesis de apertura en la condicion", "Sintáctica");}
								;
condicion_AND					:	condicion_AND AND condicion_OR {su.addCodeStructure("Se detecta una condicion AND");}|
									condicion_OR
								;
condicion_OR					:	condicion_OR OR condicion_simple {su.addCodeStructure("Se detecta una condicion OR");}|
									condicion_simple
								;
condicion_simple				:	condicion_simple '>' expresion {su.addCodeStructure("Se detecta una condicion >");}|
									condicion_simple '<' expresion {su.addCodeStructure("Se detecta una condicion <");}|
									condicion_simple MAYOR_IGUAL expresion {su.addCodeStructure("Se detecta una condicion >=");}|
									condicion_simple MENOR_IGUAL expresion {su.addCodeStructure("Se detecta una condicion <=");}|
									condicion_simple IGUAL expresion {su.addCodeStructure("Se detecta una condicion ==");}|
									condicion_simple DISTINTO expresion {su.addCodeStructure("Se detecta una condicion <>");}|
									expresion |
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
								;
expresion						:	expresion '+' termino {su.addCodeStructure("Se detecta una suma");}|
									expresion '-' termino {su.addCodeStructure("Se detecta una resta");}|
									termino {$$.sval = $1.sval;}
								;
termino							:	termino '*' factor {su.addCodeStructure("Se detecta una multiplicacion");}|
									termino '/' factor {su.addCodeStructure("Se detecta una division");}|
									factor {$$.sval = $1.sval;}
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
  
  private List<String> concatenateScope(List<String> list, String scope){
	  List<String> result = new ArrayList<>();
	  for(String s : list)
		  result.add(s + "." + scope);
	  return result;
  }	  

  private int yylex(){
	int code = this.lexicAnalyzer.yylex();
	String reference = this.lexicAnalyzer.getSymbolsTableReference();
	if(reference != null)
		yylval = new ParserVal(reference);
    return code;
  }

  private void yyerror(String err){
    //su.addError(err);
  }