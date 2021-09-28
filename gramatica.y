%{
package StateTransitionMatrix;
import Text.Text;
import Utils.utils;
%}

%token ASIG ID CTE_UINT CTE_DOUBLE UINT DOUBLE STRING PRINT MAYOR_IGUAL MENOR_IGUAL IGUAL DISTINTO OR AND IF THEN ELSE ENDIF BREAK BEGIN END WHILE DO FUNC RETURN PRE
%%
programa						:	bloque_sentencias_declarativas bloque_sentencias_ejecutables {showResults();}
								;
bloque_sentencias_declarativas	:	sentencias_declarativas
								;
sentencias_declarativas			:	sentencias_declarativas sentencia_declarativa |
									sentencia_declarativa
								;
sentencia_declarativa			:	tipo lista_de_variables ';' |
									funcion';' |
									FUNC lista_de_variables';' |
									tipo lista_de_variables {su.addError(" falta ; luego de la sentencia");} |
									funcion {su.addError(" falta ; luego de la sentencia");} |
									FUNC lista_de_variables {su.addError(" falta ; luego de la sentencia");} |
									lista_de_variables ';'{su.addError(" la/las variables no tienen tipo");}
								;
lista_de_variables				:	lista_de_variables ',' ID |
									ID
								;

funcion							:	tipo FUNC ID '('parametro')' bloque_sentencias_declarativas BEGIN sentencias_ejecutables RETURN '('retorno')'';' END |
									tipo FUNC ID '('parametro')' bloque_sentencias_declarativas BEGIN pre_condicion sentencias_ejecutables RETURN '('retorno')'';' END 
								;
pre_condicion					:	PRE':' condicion ',' STRING ';'|
									PRE':' condicion ',' STRING {su.addError(" falta ; luego de PRE");}|
									PRE condicion ',' STRING ';' {su.addError(" falta : luego de PRE");}|
									PRE':' condicion STRING ';'{su.addError(" falta , luego de la condicion en PRE");} |
									PRE':' ',' STRING ';'{su.addError(" falta falta la condicion para la sentencia PRE");} |
									PRE':' condicion ',' ';'{su.addError(" falta la cadena de caracteres luego de la ,");} |
									PRE':' condicion ';'{su.addError(" falta , y la cadena de caracteres luego de la condicion");} |
									PRE error ';'{su.addError(" la sentencia PRE no es correcta sintacticamente");}
								;
retorno							:	expresion
								;
parametro						:	tipo ID
								;
bloque_sentencias_ejecutables	:	BEGIN sentencias_ejecutables END |
									BEGIN END
								;
sentencias_ejecutables			: 	sentencias_ejecutables sentencia_ejecutable |
									sentencia_ejecutable
								;

sentencia_ejecutable			:	asignacion';' {System.err.println("ASIGNACION");}|
									llamado_funcion';' {System.err.println("LLAMADO FUNCION");} |
									clausula_seleccion';' {System.err.println("CLAUSULA SELECCION");}|
									sentencia_print';' {System.err.println("PRINT");}|
									while';' {System.err.println("WHILE");} |
									asignacion {su.addError(" falta ; luego de la sentencia");}|
									llamado_funcion {su.addError(" falta ; luego de la sentencia");} |
									clausula_seleccion {su.addError(" falta ; luego de la sentencia");}|
									sentencia_print {su.addError(" falta ; luego de la sentencia");}|
									while {su.addError(" falta ; luego de la sentencia");}
								;

while							:	WHILE condicion DO bloque_sentencias_while |
									WHILE DO bloque_sentencias_while {su.addError(" falta la condicion de corte del WHILE");}|
									WHILE condicion bloque_sentencias_while {su.addError(" falta DO en la sentencia WHILE");}
								;
bloque_sentencias_while			:	BEGIN sentencias_ejecutables_while END |
									BEGIN END
								;
sentencias_ejecutables_while	: 	sentencias_ejecutables_while sentencia_ejecutable_while | 
									sentencia_ejecutable_while
								;
sentencia_ejecutable_while		: 	sentencia_ejecutable |
									BREAK';' |
									BREAK {su.addError(" falta ; luego de la sentencia");}
								;

llamado_funcion					:	ID '('parametro')' |
									ID '('error')' {su.addError(" no se reconoce el parametro en la llamada a la funcion");} |
									ID parametro')' {su.addError(" falta parentesis de apertura en la llamada a la funcion");} |
									ID '('parametro {su.addError(" falta parentesis de cierre en la llamada a la funcion");}
								;

clausula_seleccion				:	IF condicion THEN bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables ENDIF |
									IF condicion THEN bloque_sentencias_ejecutables ENDIF |
									IF condicion bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables ENDIF {su.addError(" falta THEN en la sentencia IF");} |
									IF condicion THEN bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables {su.addError(" falta ENDIF en la sentencia IF");} |
									IF condicion bloque_sentencias_ejecutables ENDIF {su.addError(" falta THEN en la sentencia IF");} |
									IF condicion THEN bloque_sentencias_ejecutables {su.addError(" falta ENDIF en la sentencia IF");} |
									IF THEN bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables ENDIF {su.addError(" falta la condicion en la sentencia IF");}|
									IF THEN bloque_sentencias_ejecutables ENDIF {su.addError(" falta la condicion en la sentencia IF");} |
									IF condicion THEN ELSE bloque_sentencias_ejecutables ENDIF {su.addError(" falta bloque de sentencias luego del THEN");}|
									IF condicion THEN ENDIF {su.addError(" falta bloque de sentencias luego del THEN");}|
									IF condicion THEN bloque_sentencias_ejecutables ELSE ENDIF  {su.addError(" falta bloque de sentencias luego del ELSE");}
								;
condicion						:	'('condicion_AND')' |
									'('condicion_AND {su.addError(" falta parentesis de cierre en la condicion");}  |
									condicion_AND')'{su.addError(" falta parentesis de apertura en la condicion");}
								;
condicion_AND					:	condicion_AND AND condicion_OR |
									condicion_OR
								;
condicion_OR					:	condicion_OR OR condicion_simple |
									condicion_simple
								;
condicion_simple				:	condicion_simple '>' expresion |
									condicion_simple '<' expresion |
									condicion_simple MAYOR_IGUAL expresion |
									condicion_simple MENOR_IGUAL expresion |
									condicion_simple IGUAL expresion |
									condicion_simple DISTINTO expresion |
									expresion
								;
sentencia_print					:	PRINT'('STRING')' |
									PRINT STRING ')' {su.addError(" falta parentesis de apertura para la sentencia PRINT");} |
									PRINT '(' STRING {su.addError(" falta parentesis de cierre para la sentencia PRINT");}
								;
asignacion						:	ID ASIG expresion {}
								;
expresion						:	expresion '+' termino |
									expresion '-' termino |
									termino {$$.sval = $1.sval;}|
									expresion error termino {su.addError(" no se reconoce el operador");}
								;
termino							:	termino '*' factor |
									termino '/' factor |
									factor {$$.sval = $1.sval;}
								;
factor							:	ID {$$.sval = $1.sval;}| 
									uint_factor |
									double_factor {$$.sval = $1.sval;}
								;
uint_factor						:	CTE_UINT {$$.sval = $1.sval;} |
									'-' CTE_UINT {su.addError("UINT no puede ser negativo");}
								;
double_factor					:	CTE_DOUBLE {$$.sval = $1.sval;} |
									'-' CTE_DOUBLE {$$.sval = $2.sval; setToNegative($2.sval);}
								;
tipo							: 	UINT |
									DOUBLE
								;
%%
  private Text text;
  private LexicAnalyzer lexicAnalyzer;
  private StructureUtilities su;
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

  private void showResults(){
	System.out.println("Tokens: \n" + su.showTokens());
    System.out.println("Errores: \n" + su.showErrors());
    System.out.println("Warnings: \n" + su.showWarnings());
    System.out.println("Simbolos: \n" + su.showSymbolsTable());
  }

  private void setToNegative(String constant){
    this.su.setToNegative(constant);
  }

  private int yylex(){
	int code = this.lexicAnalyzer.yylex();
	String reference = this.lexicAnalyzer.getSymbolsTableReference();
	if(reference != null)
		yylval = new ParserVal(reference);
    return code;
  }

  private void yyerror(String err){
    su.addError(err);
  }