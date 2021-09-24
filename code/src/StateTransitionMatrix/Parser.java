//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "gramatica.y"
package StateTransitionMatrix;
import Text.Text;
//#line 20 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short ASIG=257;
public final static short ID=258;
public final static short CTE_UINT=259;
public final static short CTE_DOUBLE=260;
public final static short UINT=261;
public final static short DOUBLE=262;
public final static short STRING=263;
public final static short PRINT=264;
public final static short MAYOR_IGUAL=265;
public final static short MENOR_IGUAL=266;
public final static short IGUAL=267;
public final static short DISTINTO=268;
public final static short OR=269;
public final static short AND=270;
public final static short IF=271;
public final static short THEN=272;
public final static short ELSE=273;
public final static short ENDIF=274;
public final static short BREAK=275;
public final static short BEGIN=276;
public final static short END=277;
public final static short WHILE=278;
public final static short DO=279;
public final static short FUNC=280;
public final static short RETURN=281;
public final static short PRE=282;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    3,    3,    4,    4,    4,    6,    6,    7,
    7,   11,   11,   11,   10,    8,    2,    9,    9,   14,
   14,   14,   14,   14,   14,   14,   14,   14,   14,   19,
   20,   21,   21,   22,   22,   22,   16,   17,   17,   17,
   17,   17,   17,   17,   17,   17,   17,   17,   12,   12,
   12,   23,   23,   24,   24,   25,   25,   25,   25,   25,
   25,   25,   18,   15,   13,   13,   13,   26,   26,   26,
   26,   27,   27,   27,    5,    5,
};
final static short yylen[] = {                            2,
    2,    1,    2,    1,    3,    2,    3,    3,    1,   15,
   17,    5,    4,    4,    1,    2,    3,    2,    1,    2,
    2,    2,    2,    2,    1,    1,    1,    1,    1,    4,
    3,    2,    1,    1,    2,    1,    4,    7,    5,    6,
    6,    4,    4,    6,    4,    6,    4,    6,    3,    2,
    2,    3,    1,    3,    1,    3,    3,    3,    3,    3,
    3,    1,    4,    3,    3,    3,    1,    3,    3,    2,
    1,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
   75,   76,    0,    0,    0,    0,    4,    0,    0,    9,
    0,    0,    1,    3,    0,    0,    6,    7,    0,    0,
    0,    0,    0,    0,   19,    0,    0,    0,    0,    0,
    0,    5,    8,    0,    0,    0,   72,   73,   74,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   71,    0,
   17,   18,   20,   21,   22,   23,   24,    0,    0,    0,
    0,    0,    0,    0,   70,    0,    0,    0,    0,    0,
   51,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   16,   37,   63,    0,   45,   49,    0,   47,
    0,    0,   42,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   68,   69,    0,   30,    0,    0,    0,
    0,   39,    0,    0,   34,    0,   33,    0,   44,   46,
   48,    0,   40,   35,   31,   32,    0,   38,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   14,
    0,   13,    0,    0,    0,   12,    0,    0,    0,    0,
   10,    0,    0,   11,
};
final static short yydgoto[] = {                          4,
    5,   13,    6,    7,    8,   11,    9,   61,   24,  143,
  131,   43,   44,   25,   26,   27,   28,   29,   30,  107,
  116,  117,   45,   46,   47,   48,   49,
};
final static short yysindex[] = {                      -246,
    0,    0, -238,    0, -249, -246,    0, -247,  -22,    0,
  -27, -156,    0,    0, -207,  -21,    0,    0, -198,   19,
   38,  -39,    5,  169,    0,   22,   28,   42,   46,   54,
   64,    0,    0,  166, -169, -146,    0,    0,    0, -249,
  166,  -57, -203,   27,  -29, -149,  -53,   24,    0, -153,
    0,    0,    0,    0,    0,    0,    0, -169,   27, -125,
   94,  100, -144,  -28,    0, -199, -130,  166,  166,  166,
    0,  166,  166,  166,  166,  166,  166,  166,  -57,  -57,
 -128,  105,    0,    0,    0, -249,    0,    0, -249,    0,
  -75, -249,    0,   24,   24, -149,  -53,   27,   27,   27,
   27,   27,   27,    0,    0, -126,    0, -246, -124, -116,
 -190,    0, -113,  104,    0, -111,    0, -108,    0,    0,
    0, -109,    0,    0,    0,    0, -175,    0,  378, -229,
  114,    5,  134,  147, -156,   -8,  -83,  166,  237,    0,
  -72,    0,  152,   27,  155,    0,  145,  166,  -71,  164,
    0,  148,  -69,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,  -67,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -89,  -81,  200,  216,  221,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   20,    0,  135,  118,  -41,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  157,    0,
    0,    0,    0,  141,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  173,    0,    0,  -19,    3,  140,  130,   35,   50,   65,
   80,   95,  110,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  185,    0,    0,    0,    0,    0,    0,
    0,  195,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  177,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  102,   45,    0,  271,  -30,  235,    0,  233,  -96,  146,
    0,  -13,  -20,  -16,    0,    0,    0,    0,    0,    0,
    0,  179,  252,  227,  234,   30,  -12,
};
final static int YYTABLESIZE=638;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         67,
   41,   67,   67,   67,   60,   42,   78,   52,   77,   50,
   10,   71,   88,   59,    1,    2,   19,   67,   67,   10,
   67,   65,   19,   65,   65,   65,   12,   60,   20,   65,
  130,   18,   15,    3,   21,  141,   17,   32,  139,   65,
   65,   22,   65,   66,   41,   66,   66,   66,   23,   42,
   31,  134,   98,   99,  100,  101,  102,  103,   35,   33,
   62,   66,   66,   62,   66,   79,  104,  105,   66,   68,
   80,   69,   12,   89,   90,   58,   12,   36,   58,   62,
   53,   62,   20,  121,   63,   12,   54,   67,   21,  115,
   59,    1,    2,   59,   58,   22,   58,   94,   95,  115,
   55,   20,   23,   58,   56,   60,  129,   21,   60,   59,
   91,   59,   57,   52,   22,  133,   62,  144,  136,   72,
   61,   23,   52,   61,   60,   81,   60,  144,   86,   87,
  109,   20,   83,  110,   84,   56,  113,   21,   56,   61,
   85,   61,   92,   93,   22,  108,   20,  106,  114,  119,
   57,   23,   21,   57,   56,  122,   56,  120,   55,   22,
  123,   55,  124,  114,  128,  125,   23,  127,   25,   57,
   54,   57,  135,   54,   25,   53,   26,  137,   53,  142,
   52,   25,   26,   52,   50,   25,  138,   25,   25,   26,
  146,   25,  147,   26,  148,   26,   26,  111,  112,   26,
   37,   38,   39,  149,  152,  151,  153,  154,    2,  118,
   42,   73,   74,   75,   76,   64,   67,   15,   37,   38,
   39,   67,   67,   67,   67,   67,   67,   67,   67,   67,
   67,   43,   40,   67,   67,   67,   67,   67,   65,   67,
   70,   70,   16,   65,   65,   65,   65,   65,   65,   65,
   65,   65,   65,   41,  140,   65,   65,   65,   65,   65,
   66,   65,   37,   38,   39,   66,   66,   66,   66,   66,
   66,   66,   66,   66,   66,   34,   14,   66,   66,   66,
   66,   66,   62,   66,   62,   62,   62,   62,   62,   62,
   82,   62,   64,  150,  126,   62,   96,   58,   62,   58,
   58,   58,   58,   58,   58,   97,   58,    0,    0,    0,
   58,    0,   59,   58,   59,   59,   59,   59,   59,   59,
    0,   59,    0,    0,    0,   59,    0,   60,   59,   60,
   60,   60,   60,   60,   60,    0,   60,    0,    0,    0,
   60,    0,   61,   60,   61,   61,   61,   61,   61,   61,
    0,   61,    0,    0,    0,   61,    0,   56,   61,   56,
   56,   56,   56,   56,   56,    0,   56,    0,    0,    0,
   56,    0,   57,   56,   57,   57,   57,   57,   57,   57,
   55,   57,    0,    0,    0,   57,   55,   55,   57,   55,
    0,    0,   54,   55,    0,    0,   55,   53,   54,   54,
    0,   54,   52,   50,   53,   54,   53,    0,   54,   52,
   53,   52,   50,   53,   64,   52,   50,   41,   52,   50,
   64,    0,   42,   37,   38,   39,   20,   64,    0,    0,
   43,   64,   21,   64,   64,  132,   43,   64,    0,   22,
    0,    0,   36,   43,    0,   51,   23,   43,   36,   43,
   43,    0,   41,   43,    0,   36,    0,   27,   41,   36,
    0,   36,   36,   27,    0,   41,    0,    0,    0,   41,
   27,   41,   41,   28,   27,   41,   27,   27,   29,   28,
   27,    0,    0,    0,   29,    0,   28,    0,    0,    0,
   28,   29,   28,   28,   20,   29,   28,   29,   29,    0,
   21,   29,    0,    0,    0,    0,    0,   22,    0,    0,
    0,    0,    0,    0,   23,    0,    0,  145,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   37,   38,   39,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   40,   43,   44,   45,   35,   45,   60,   24,   62,   23,
  258,   41,   41,   34,  261,  262,   44,   59,   60,  258,
   62,   41,   44,   43,   44,   45,  276,   58,  258,   42,
  127,   59,  280,  280,  264,   44,   59,   59,  135,   59,
   60,  271,   62,   41,   40,   43,   44,   45,  278,   45,
  258,  281,   73,   74,   75,   76,   77,   78,   40,  258,
   41,   59,   60,   44,   62,   42,   79,   80,  272,   43,
   47,   45,  276,  273,  274,   41,  276,   40,   44,   60,
   59,   62,  258,  274,   40,  276,   59,   43,  264,  106,
   41,  261,  262,   44,   60,  271,   62,   68,   69,  116,
   59,  258,  278,   40,   59,   41,  282,  264,   44,   60,
   66,   62,   59,  130,  271,  129,  263,  138,  132,  269,
   41,  278,  139,   44,   60,  279,   62,  148,  273,  274,
   86,  258,  258,   89,   41,   41,   92,  264,   44,   60,
   41,   62,  273,  274,  271,   41,  258,  276,  275,  274,
   41,  278,  264,   44,   60,  111,   62,  274,   41,  271,
  274,   44,   59,  275,  274,  277,  278,  276,  258,   60,
   41,   62,   59,   44,  264,   41,  258,   44,   44,  263,
   41,  271,  264,   44,   44,  275,   40,  277,  278,  271,
  263,  281,   41,  275,   40,  277,  278,  273,  274,  281,
  258,  259,  260,   59,   41,  277,   59,  277,  276,  108,
   45,  265,  266,  267,  268,   59,  258,   41,  258,  259,
  260,  263,  264,  265,  266,  267,  268,  269,  270,  271,
  272,   59,  272,  275,  276,  277,  278,  279,  258,  281,
  270,  270,    8,  263,  264,  265,  266,  267,  268,  269,
  270,  271,  272,   59,  263,  275,  276,  277,  278,  279,
  258,  281,  258,  259,  260,  263,  264,  265,  266,  267,
  268,  269,  270,  271,  272,  257,    6,  275,  276,  277,
  278,  279,  263,  281,  265,  266,  267,  268,  269,  270,
   58,  272,   41,  148,  116,  276,   70,  263,  279,  265,
  266,  267,  268,  269,  270,   72,  272,   -1,   -1,   -1,
  276,   -1,  263,  279,  265,  266,  267,  268,  269,  270,
   -1,  272,   -1,   -1,   -1,  276,   -1,  263,  279,  265,
  266,  267,  268,  269,  270,   -1,  272,   -1,   -1,   -1,
  276,   -1,  263,  279,  265,  266,  267,  268,  269,  270,
   -1,  272,   -1,   -1,   -1,  276,   -1,  263,  279,  265,
  266,  267,  268,  269,  270,   -1,  272,   -1,   -1,   -1,
  276,   -1,  263,  279,  265,  266,  267,  268,  269,  270,
  263,  272,   -1,   -1,   -1,  276,  269,  270,  279,  272,
   -1,   -1,  263,  276,   -1,   -1,  279,  263,  269,  270,
   -1,  272,  263,  263,  270,  276,  272,   -1,  279,  270,
  276,  272,  272,  279,  258,  276,  276,   40,  279,  279,
  264,   -1,   45,  258,  259,  260,  258,  271,   -1,   -1,
  258,  275,  264,  277,  278,   58,  264,  281,   -1,  271,
   -1,   -1,  258,  271,   -1,  277,  278,  275,  264,  277,
  278,   -1,  258,  281,   -1,  271,   -1,  258,  264,  275,
   -1,  277,  278,  264,   -1,  271,   -1,   -1,   -1,  275,
  271,  277,  278,  258,  275,  281,  277,  278,  258,  264,
  281,   -1,   -1,   -1,  264,   -1,  271,   -1,   -1,   -1,
  275,  271,  277,  278,  258,  275,  281,  277,  278,   -1,
  264,  281,   -1,   -1,   -1,   -1,   -1,  271,   -1,   -1,
   -1,   -1,   -1,   -1,  278,   -1,   -1,  281,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  258,  259,  260,
};
}
final static short YYFINAL=4;
final static short YYMAXTOKEN=282;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,"':'","';'",
"'<'",null,"'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,"ASIG","ID","CTE_UINT","CTE_DOUBLE","UINT",
"DOUBLE","STRING","PRINT","MAYOR_IGUAL","MENOR_IGUAL","IGUAL","DISTINTO","OR",
"AND","IF","THEN","ELSE","ENDIF","BREAK","BEGIN","END","WHILE","DO","FUNC",
"RETURN","PRE",
};
final static String yyrule[] = {
"$accept : programa",
"programa : bloque_sentencias_declarativas bloque_sentencias_ejecutables",
"bloque_sentencias_declarativas : sentencias_declarativas",
"sentencias_declarativas : sentencias_declarativas sentencia_declarativa",
"sentencias_declarativas : sentencia_declarativa",
"sentencia_declarativa : tipo lista_de_variables ';'",
"sentencia_declarativa : funcion ';'",
"sentencia_declarativa : FUNC lista_de_variables ';'",
"lista_de_variables : lista_de_variables ',' ID",
"lista_de_variables : ID",
"funcion : tipo FUNC ID '(' parametro ')' bloque_sentencias_declarativas BEGIN sentencias_ejecutables RETURN '(' retorno ')' ';' END",
"funcion : tipo FUNC ID '(' parametro ')' bloque_sentencias_declarativas BEGIN pre_condicion ';' sentencias_ejecutables RETURN '(' retorno ')' ';' END",
"pre_condicion : PRE ':' condicion ',' STRING",
"pre_condicion : PRE condicion ',' STRING",
"pre_condicion : PRE ':' condicion STRING",
"retorno : expresion",
"parametro : tipo ID",
"bloque_sentencias_ejecutables : BEGIN sentencias_ejecutables END",
"sentencias_ejecutables : sentencias_ejecutables sentencia_ejecutable",
"sentencias_ejecutables : sentencia_ejecutable",
"sentencia_ejecutable : asignacion ';'",
"sentencia_ejecutable : llamado_funcion ';'",
"sentencia_ejecutable : clausula_seleccion ';'",
"sentencia_ejecutable : sentencia_print ';'",
"sentencia_ejecutable : while ';'",
"sentencia_ejecutable : asignacion",
"sentencia_ejecutable : llamado_funcion",
"sentencia_ejecutable : clausula_seleccion",
"sentencia_ejecutable : sentencia_print",
"sentencia_ejecutable : while",
"while : WHILE condicion DO bloque_sentencias_while",
"bloque_sentencias_while : BEGIN sentencias_ejecutables_while END",
"sentencias_ejecutables_while : sentencias_ejecutables_while sentencia_ejecutable_while",
"sentencias_ejecutables_while : sentencia_ejecutable_while",
"sentencia_ejecutable_while : sentencia_ejecutable",
"sentencia_ejecutable_while : BREAK ';'",
"sentencia_ejecutable_while : BREAK",
"llamado_funcion : ID '(' parametro ')'",
"clausula_seleccion : IF condicion THEN bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables ENDIF",
"clausula_seleccion : IF condicion THEN bloque_sentencias_ejecutables ENDIF",
"clausula_seleccion : IF condicion bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables ENDIF",
"clausula_seleccion : IF condicion THEN bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables",
"clausula_seleccion : IF condicion bloque_sentencias_ejecutables ENDIF",
"clausula_seleccion : IF condicion THEN bloque_sentencias_ejecutables",
"clausula_seleccion : IF THEN bloque_sentencias_ejecutables ELSE bloque_sentencias_ejecutables ENDIF",
"clausula_seleccion : IF THEN bloque_sentencias_ejecutables ENDIF",
"clausula_seleccion : IF condicion THEN ELSE bloque_sentencias_ejecutables ENDIF",
"clausula_seleccion : IF condicion THEN ENDIF",
"clausula_seleccion : IF condicion THEN bloque_sentencias_ejecutables ELSE ENDIF",
"condicion : '(' condicion_AND ')'",
"condicion : '(' condicion_AND",
"condicion : condicion_AND ')'",
"condicion_AND : condicion_AND AND condicion_OR",
"condicion_AND : condicion_OR",
"condicion_OR : condicion_OR OR condicion_simple",
"condicion_OR : condicion_simple",
"condicion_simple : condicion_simple '>' expresion",
"condicion_simple : condicion_simple '<' expresion",
"condicion_simple : condicion_simple MAYOR_IGUAL expresion",
"condicion_simple : condicion_simple MENOR_IGUAL expresion",
"condicion_simple : condicion_simple IGUAL expresion",
"condicion_simple : condicion_simple DISTINTO expresion",
"condicion_simple : expresion",
"sentencia_print : PRINT '(' STRING ')'",
"asignacion : ID ASIG expresion",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : '-' factor",
"termino : factor",
"factor : ID",
"factor : CTE_UINT",
"factor : CTE_DOUBLE",
"tipo : UINT",
"tipo : DOUBLE",
};

//#line 118 "gramatica.y"
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
//#line 465 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 13:
//#line 27 "gramatica.y"
{System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta : luego de PRE");}
break;
case 14:
//#line 28 "gramatica.y"
{System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta , luego de la condicion en PRE");}
break;
case 20:
//#line 40 "gramatica.y"
{System.err.println("ASIGNACION");}
break;
case 21:
//#line 41 "gramatica.y"
{System.err.println("LLAMADO FUNCION");}
break;
case 22:
//#line 42 "gramatica.y"
{System.err.println("CLAUSULA SELECCION");}
break;
case 23:
//#line 43 "gramatica.y"
{System.err.println("PRINT");}
break;
case 24:
//#line 44 "gramatica.y"
{System.err.println("WHILE");}
break;
case 25:
//#line 45 "gramatica.y"
{System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta ; luego de la sentencia");}
break;
case 26:
//#line 46 "gramatica.y"
{System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta ; luego de la sentencia");}
break;
case 27:
//#line 47 "gramatica.y"
{System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta ; luego de la sentencia");}
break;
case 28:
//#line 48 "gramatica.y"
{System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta ; luego de la sentencia");}
break;
case 29:
//#line 49 "gramatica.y"
{System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta ; luego de la sentencia");}
break;
case 36:
//#line 61 "gramatica.y"
{System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta ; luego de la sentencia");}
break;
case 40:
//#line 69 "gramatica.y"
{System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta THEN en la sentencia IF");}
break;
case 41:
//#line 70 "gramatica.y"
{System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta ENDIF en la sentencia IF");}
break;
case 42:
//#line 71 "gramatica.y"
{System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta THEN en la sentencia IF");}
break;
case 43:
//#line 72 "gramatica.y"
{System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta ENDIF en la sentencia IF");}
break;
case 44:
//#line 73 "gramatica.y"
{System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta la condicion en la sentencia IF");}
break;
case 45:
//#line 74 "gramatica.y"
{System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta la condicion en la sentencia IF");}
break;
case 46:
//#line 75 "gramatica.y"
{System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta bloque de sentencias luego del THEN");}
break;
case 47:
//#line 76 "gramatica.y"
{System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta bloque de sentencias luego del THEN");}
break;
case 48:
//#line 77 "gramatica.y"
{System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta bloque de sentencias luego del ELSE");}
break;
case 50:
//#line 80 "gramatica.y"
{System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta parentesis de cierre en la condicion");}
break;
case 51:
//#line 81 "gramatica.y"
{System.err.println("Linea " + this.lexicAnalyzer.getCurrentLine() + " falta parentesis de apertura en la condicion");}
break;
case 64:
//#line 99 "gramatica.y"
{System.err.println(val_peek(0).sval);}
break;
case 67:
//#line 103 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 70:
//#line 107 "gramatica.y"
{yyval.sval = val_peek(0).sval;System.err.println(val_peek(0).sval + " es negativo");}
break;
case 71:
//#line 108 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 72:
//#line 110 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 73:
//#line 111 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 74:
//#line 112 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
//#line 738 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
