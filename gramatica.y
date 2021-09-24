%{
package StateTransitionMatrix;
import Text.Text;
%}

%token ASIG ID CTE_UINT CTE_DOUBLE UINT DOUBLE STRING PRINT MAYOR_IGUAL MENOR_IGUAL IGUAL DISTINTO OR AND IF THEN ELSE ENDIF BREAK BEGIN END WHILE DO FUNC RETURN PRE
%%
programa						:	bloque_sentencias_declarativas bloque_sentencias_ejecutables
								;
bloque_sentencias_declarativas	:	sentencias_declarativas
								;
sentencias_declarativas			:	sentencias_declarativas sentencia_declarativa |
									sentencia_declarativa
								;
sentencia_declarativa			:	tipo lista_de_variables ';' |
									funcion';' |
									FUNC lista_de_variables';'
								;
lista_de_variables				:	lista_de_variables ',' ID |
									ID
								;

funcion							:	tipo FUNC ID '('parametro')' bloque_sentencias_declarativas BEGIN sentencias_ejecutables RETURN '('retorno')'';' END |
									tipo FUNC ID '('parametro')' bloque_sentencias_declarativas BEGIN pre_condicion ';' sentencias_ejecutables RETURN '('retorno')'';' END
								;
pre_condicion					:	PRE':' condicion ',' STRING |
									PRE condicion ',' STRING {System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta : luego de PRE");}|
									PRE':' condicion STRING {System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta , luego de la condicion en PRE");}
								;
retorno							:	expresion
								;
parametro						:	tipo ID
								;
bloque_sentencias_ejecutables	:	BEGIN sentencias_ejecutables END	
								;
sentencias_ejecutables			: 	sentencias_ejecutables sentencia_ejecutable |
									sentencia_ejecutable
								;

sentencia_ejecutable			:	asignacion';' {System.err.println("ASIGNACION");}|
									llamado_funcion';' {System.err.println("LLAMADO FUNCION");} |
									clausula_seleccion';' {System.err.println("CLAUSULA SELECCION");}|
									sentencia_print';' {System.err.println("PRINT");}|
									while';' {System.err.println("WHILE");} |
									asignacion {System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta ; luego de la sentencia");}|
									llamado_funcion {System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta ; luego de la sentencia");} |
									clausula_seleccion {System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta ; luego de la sentencia");}|
									sentencia_print {System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta ; luego de la sentencia");}|
									while {System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta ; luego de la sentencia");}
								;

while							:	WHILE condicion DO bloque_sentencias_while
								;
bloque_sentencias_while			:	BEGIN sentencias_ejecutables_while END
								;
sentencias_ejecutables_while	: 	sentencias_ejecutables_while sentencia_ejecutable_while | 
									sentencia_ejecutable_while
								;
sentencia_ejecutable_while		: 	sentencia_ejecutable |
									BREAK';' |
									BREAK {System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta ; luego de la sentencia");}
								;

llamado_funcion					:	ID '('parametro')'
								;

clausula_seleccion				:	IF condicion THEN bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables ENDIF |
									IF condicion THEN bloque_sentencias_ejecutables ENDIF |
									IF condicion bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables ENDIF {System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta THEN en la sentencia IF");} |
									IF condicion THEN bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables {System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta ENDIF en la sentencia IF");} |
									IF condicion bloque_sentencias_ejecutables ENDIF {System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta THEN en la sentencia IF");} |
									IF condicion THEN bloque_sentencias_ejecutables {System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta ENDIF en la sentencia IF");} |
									IF THEN bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables ENDIF {System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta la condicion en la sentencia IF");}|
									IF THEN bloque_sentencias_ejecutables ENDIF {System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta la condicion en la sentencia IF");} |
									IF condicion THEN ELSE bloque_sentencias_ejecutables ENDIF {System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta bloque de sentencias luego del THEN");}|
									IF condicion THEN ENDIF {System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta bloque de sentencias luego del THEN");}|
									IF condicion THEN bloque_sentencias_ejecutables ELSE ENDIF  {System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta bloque de sentencias luego del ELSE");}
								;
condicion						:	'('condicion_AND')' |
									'('condicion_AND {System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta parentesis de cierre en la condicion");}  |
									condicion_AND')'{System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta parentesis de apertura en la condicion");}
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
sentencia_print					:	PRINT'('STRING')'
								;
asignacion						:	ID ASIG expresion {System.err.println($3.sval);}
								;
expresion						:	expresion '+' termino |
									expresion '-' termino |
									termino {$$.sval = $1.sval;}
								;
termino							:	termino '*' factor |
									termino '/' factor |
									'-' factor {$$.sval = $2.sval;System.err.println($2.sval + " es negativo");} |
									factor {$$.sval = $1.sval;}
								;
factor							:	ID {$$.sval = $1.sval;}| 
									CTE_UINT {$$.sval = $1.sval;}|
									CTE_DOUBLE {$$.sval = $1.sval;}
								;
tipo							: 	UINT |
									DOUBLE
								;
%%
  private Text text;
  private LexicAnalyzer lexicAnalyzer;
  public Parser(Text text, boolean debug){
    this.text = text;
    this.lexicAnalyzer = new LexicAnalyzer(text);
    yydebug=debug;
  }

  private int yylex(){
	int code = this.lexicAnalyzer.yylex();
	String reference = this.lexicAnalyzer.getSymbolsTableReference();
	if(reference != null)
		yylval = new ParserVal(reference);
    return code;
  }

  private void yyerror(String err){
    System.err.println(err);
  }