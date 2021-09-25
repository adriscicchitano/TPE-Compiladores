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
import Utils.utils;
//#line 21 "Parser.java"




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
    0,    1,    3,    3,    4,    4,    4,    4,    4,    4,
    4,    6,    6,    7,    7,    7,   11,   11,   11,   11,
   11,   11,   11,   10,    8,    2,    2,    9,    9,   14,
   14,   14,   14,   14,   14,   14,   14,   14,   14,   19,
   19,   19,   20,   20,   21,   21,   22,   22,   22,   16,
   16,   16,   16,   17,   17,   17,   17,   17,   17,   17,
   17,   17,   17,   17,   12,   12,   12,   23,   23,   24,
   24,   25,   25,   25,   25,   25,   25,   25,   18,   18,
   18,   15,   13,   13,   13,   13,   26,   26,   26,   27,
   27,   27,   28,   28,   29,   29,    5,    5,
};
final static short yylen[] = {                            2,
    2,    1,    2,    1,    3,    2,    3,    2,    1,    2,
    2,    3,    1,   15,   17,   16,    5,    4,    4,    4,
    4,    3,    2,    1,    2,    3,    2,    2,    1,    2,
    2,    2,    2,    2,    1,    1,    1,    1,    1,    4,
    3,    3,    3,    2,    2,    1,    1,    2,    1,    4,
    4,    3,    3,    7,    5,    6,    6,    4,    4,    6,
    4,    6,    4,    6,    3,    2,    2,    3,    1,    3,
    1,    3,    3,    3,    3,    3,    3,    1,    4,    3,
    3,    3,    3,    3,    1,    3,    3,    3,    1,    1,
    1,    1,    1,    2,    1,    2,    1,    1,
};
final static short yydefred[] = {                         0,
   13,   97,   98,    0,    0,    0,    0,    4,    0,    0,
    0,    0,    0,    1,    3,    0,    0,   11,    0,    6,
    7,    0,    0,    0,   27,    0,    0,   29,    0,    0,
    0,    0,    0,    0,    5,   12,    0,    0,    0,    0,
    0,    0,   90,   93,   95,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   89,   91,   92,    0,    0,   26,
   28,   30,   31,   32,   33,   34,    0,    0,    0,    0,
   25,   52,   80,    0,    0,    0,   94,   96,    0,    0,
    0,    0,    0,    0,   67,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   41,    0,   42,    0,   51,
   50,   79,    0,   61,   65,    0,   63,    0,    0,   58,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   87,   88,    0,   44,   47,    0,   46,   40,    0,
    0,    0,    0,   55,    0,   48,   43,   45,    0,   60,
   62,   64,    0,   56,    0,   54,    0,    0,    0,   23,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   20,   19,    0,   18,    0,    0,    0,    0,   17,
    0,    0,    0,    0,    0,    0,   14,    0,    0,    0,
   16,   15,
};
final static short yydgoto[] = {                          5,
    6,   14,    7,    8,    9,   10,   11,   40,   27,  166,
  149,   49,   50,   28,   29,   30,   31,   32,   33,   96,
  127,  128,   51,   52,   53,   54,   55,   56,   57,
};
final static short yysindex[] = {                      -133,
    0,    0,    0, -228,    0, -235, -133,    0, -248,  -13,
    6,   11,  333,    0,    0, -177,   29,    0, -173,    0,
    0,  166,  -31,  -39,    0,   55,  342,    0,   30,   37,
   63,   87,   96,   74,    0,    0,   33, -100,  -77,  150,
  157,  -62,    0,    0,    0, -235,   33, -203, -238,  -38,
  -28,  -55,  437,  -22,    0,    0,    0,  -60, -264,    0,
    0,    0,    0,    0,    0,    0, -113,  -38,  204,  216,
    0,    0,    0,  239, -116,  -27,    0,    0, -156, -110,
   33,   33,   33,   33,    0,   33,   33,   33,   33,   33,
   33,   33,   33,   33,  213,    0,  -60,    0,  263,    0,
    0,    0, -235,    0,    0, -235,    0,  -61, -235,    0,
  -22,  -22,  -22,  -55,  437,  -38,  -38,  -38,  -38,  -38,
  -38,    0,    0,  269,    0,    0,  258,    0,    0, -133,
   56,   62, -166,    0,   95,    0,    0,    0,   61,    0,
    0,    0,  108,    0, -242,    0,  429,  187,  185,    0,
    9,  304,  309,  -98,  311,   89,   -7,  120,   33,  315,
  346,    0,    0,  136,    0,  375,  -38,  384,   33,    0,
  366,   33,  385,  152,  389,  372,    0,  373,  156,  162,
    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,  164,    0,    0,    0,
 -178, -135,    0,    0,    0,    0,  -69,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -172,  -99,
  -81,  195,  285,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   53,
    0,  403,  359,  -41,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  231,    0,  246,
    0,    0,    0,  267,    0,  222,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  276,    0,    0,
  -17,    7,   31,  420,  393,   75,   92,  109,  126,  143,
  376,    0,    0,  330,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  306,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  268,    0,    0,    0,
    0,    0,    0,  307,    0,    0,  400,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
  312,   -2,    0,  439,    1,   78,    0,   -3,   50,  -48,
    0,   -9,  -29,  -16,    0,    0,    0,    0,    0,  -26,
    0,  321,  407,  371,  364,  127,   81,    0,    0,
};
final static int YYTABLESIZE=705;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         85,
   47,   85,   85,   85,   82,   48,   83,   68,   42,    1,
   61,   95,   85,  105,   97,   22,   59,   85,   85,   93,
   85,   23,   39,   86,   94,   86,   86,   86,   24,    1,
   19,   16,   98,   79,   70,   26,  164,   13,   39,  147,
   13,   86,   86,   75,   86,   18,   80,   83,   47,   83,
   83,   83,  156,   48,   19,   77,   78,  116,  117,  118,
  119,  120,  121,   99,   20,   83,   83,   39,   83,   21,
  129,   84,   19,   84,   84,   84,  108,   48,  126,    9,
   34,   12,    9,    9,   36,   35,   17,   35,   62,   84,
   84,   35,   84,   78,   47,   63,   78,    9,   35,   48,
  131,    9,   35,  132,   35,   35,  135,  142,   35,   13,
  126,   78,   78,   67,   78,   74,  106,  107,   74,   13,
  173,   64,   10,  175,    1,   10,   10,    2,    3,  167,
  143,   61,   75,   74,   74,   75,   74,  152,   61,  167,
   10,  157,  167,   61,   10,   65,    4,    2,    3,   76,
   75,   75,   76,   75,   66,   69,  103,  104,   36,   22,
    2,    3,  109,  110,   36,   23,   77,   76,   76,   77,
   76,   36,   24,  122,  123,   36,   37,   36,   36,   26,
   71,   36,   37,   72,   77,   77,   72,   77,    8,   37,
   72,    8,    8,   37,  148,   37,   37,   73,  155,   37,
   74,   72,   72,  160,   72,   38,    8,  111,  112,  113,
    8,  133,  134,   86,   85,   95,   85,   81,   43,   44,
   45,   85,   85,   85,   85,   85,   85,   85,   85,   85,
   85,   41,   46,   85,   85,   85,   85,   85,   86,   85,
   86,   84,   84,  154,  100,   86,   86,   86,   86,   86,
   86,   86,   86,   86,   86,  163,  101,   86,   86,   86,
   86,   86,   83,   86,   83,   66,   43,   44,   45,   83,
   83,   83,   83,   83,   83,   83,   83,   83,   83,  102,
   66,   83,   83,   83,   83,   83,   84,   83,   84,   82,
   43,   44,   45,   84,   84,   84,   84,   84,   84,   84,
   84,   84,   84,  130,   53,   84,   84,   84,   84,   84,
   78,   84,   43,   44,   45,   78,   78,   78,   78,   78,
   78,   78,   78,   78,   78,   81,   22,  136,   78,  140,
   78,   78,   74,   58,   59,  141,  145,   74,   74,   74,
   74,   74,   74,   74,   74,   74,   74,  158,  159,   75,
   74,  162,   74,   74,   75,   75,   75,   75,   75,   75,
   75,   75,   75,   75,   57,   21,   76,   75,  144,   75,
   75,   76,   76,   76,   76,   76,   76,   76,   76,   76,
   76,  146,  165,   77,   76,  169,   76,   76,   77,   77,
   77,   77,   77,   77,   77,   77,   77,   77,  170,   71,
   72,   77,   71,   77,   77,   72,   72,   72,   72,   72,
   72,   72,   72,   72,   72,  171,   73,   71,   72,   73,
   72,   72,   37,  172,  174,  176,    2,    3,  177,  178,
  179,  180,  181,   70,   73,   73,   70,   73,  182,    2,
   24,  139,   22,   69,   22,   15,   69,  138,   23,  115,
   23,   70,   38,   76,  114,   24,    0,   24,   38,    0,
   68,   69,   26,   68,   26,   38,    0,  153,   47,   38,
   22,   38,   38,   48,    0,   38,   23,    0,   68,   66,
    0,    0,    0,   24,   66,   66,  151,  124,   82,  125,
   26,    0,   66,   66,   82,    0,   92,   66,   91,   66,
   66,   82,    0,   53,    0,   82,    0,   82,   82,   53,
    0,   82,    0,    0,    0,   22,   53,    0,    0,    0,
   53,   23,   53,   53,   81,   22,   53,    0,   24,    0,
   81,   22,  124,   59,  137,   26,    0,   81,   22,   59,
    0,   81,   39,   81,   81,   22,   59,   81,   39,    0,
   59,    0,   59,   59,    0,   39,   59,    0,    0,   39,
    0,   39,   39,   57,   21,   39,    0,    0,   22,   57,
   21,    0,   22,    0,   23,    0,   57,   21,   23,    0,
   57,   24,   57,   57,   21,   24,   57,   49,   26,    0,
   22,  161,   26,   49,    0,  168,   23,    0,    0,   22,
   49,    0,    0,   24,   49,   23,   49,   49,    0,   25,
   26,    0,   24,    0,    0,    0,   71,    0,   60,   26,
    0,   71,   71,    0,    0,    0,    0,   71,   71,   71,
   71,    0,    0,   73,   71,    0,   71,   71,   73,   73,
   73,   73,   73,   73,   73,   73,   73,   73,    0,    0,
   70,   73,    0,   73,   73,   70,   70,    0,    0,    0,
   69,   70,   70,   70,   70,   69,   69,    0,   70,    0,
   70,   70,   69,   69,   69,    0,    0,   68,   69,    0,
   69,   69,   68,   68,  150,    0,   43,   44,   45,   68,
   68,   68,    0,    0,    0,   68,    0,   68,   68,    0,
    0,   87,   88,   89,   90,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   40,   43,   44,   45,   43,   45,   45,   37,   40,  258,
   27,  276,   41,   41,  279,  258,   26,   59,   60,   42,
   62,  264,   22,   41,   47,   43,   44,   45,  271,  258,
   44,  280,   59,  272,   38,  278,   44,  276,   38,  282,
  276,   59,   60,   46,   62,   59,   49,   41,   40,   43,
   44,   45,   44,   45,   44,  259,  260,   87,   88,   89,
   90,   91,   92,   67,   59,   59,   60,   67,   62,   59,
   97,   41,   44,   43,   44,   45,   79,   45,   95,  258,
  258,    4,  261,  262,  258,  258,    9,   59,   59,   59,
   60,  264,   62,   41,   40,   59,   44,  276,  271,   45,
  103,  280,  275,  106,  277,  278,  109,  274,  281,  276,
  127,   59,   60,   40,   62,   41,  273,  274,   44,  276,
  169,   59,  258,  172,  258,  261,  262,  261,  262,  159,
  133,  148,   41,   59,   60,   44,   62,  147,  155,  169,
  276,  151,  172,  160,  280,   59,  280,  261,  262,   41,
   59,   60,   44,   62,   59,  256,  273,  274,  258,  258,
  261,  262,  273,  274,  264,  264,   41,   59,   60,   44,
   62,  271,  271,   93,   94,  275,  258,  277,  278,  278,
  258,  281,  264,   41,   59,   60,   44,   62,  258,  271,
   41,  261,  262,  275,  145,  277,  278,   41,  149,  281,
  263,   59,   60,  154,   62,   40,  276,   81,   82,   83,
  280,  273,  274,  269,  256,  276,  258,  256,  258,  259,
  260,  263,  264,  265,  266,  267,  268,  269,  270,  271,
  272,  263,  272,  275,  276,  277,  278,  279,  256,  281,
  258,  270,  270,   59,   41,  263,  264,  265,  266,  267,
  268,  269,  270,  271,  272,  263,   41,  275,  276,  277,
  278,  279,  256,  281,  258,   44,  258,  259,  260,  263,
  264,  265,  266,  267,  268,  269,  270,  271,  272,   41,
   59,  275,  276,  277,  278,  279,  256,  281,  258,   59,
  258,  259,  260,  263,  264,  265,  266,  267,  268,  269,
  270,  271,  272,   41,   59,  275,  276,  277,  278,  279,
  258,  281,  258,  259,  260,  263,  264,  265,  266,  267,
  268,  269,  270,  271,  272,   59,   59,   59,  276,  274,
  278,  279,  258,  279,   59,  274,  276,  263,  264,  265,
  266,  267,  268,  269,  270,  271,  272,   44,   40,  258,
  276,  263,  278,  279,  263,  264,  265,  266,  267,  268,
  269,  270,  271,  272,   59,   59,  258,  276,  274,  278,
  279,  263,  264,  265,  266,  267,  268,  269,  270,  271,
  272,  274,  263,  258,  276,   40,  278,  279,  263,  264,
  265,  266,  267,  268,  269,  270,  271,  272,  263,   41,
  258,  276,   44,  278,  279,  263,  264,  265,  266,  267,
  268,  269,  270,  271,  272,   41,   41,   59,  276,   44,
  278,  279,  257,   40,   59,   41,  261,  262,  277,   41,
   59,   59,  277,   41,   59,   60,   44,   62,  277,  276,
   41,  130,  258,   41,  258,    7,   44,  127,  264,   86,
  264,   59,  258,   47,   84,  271,   -1,  271,  264,   -1,
   41,   59,  278,   44,  278,  271,   -1,  281,   40,  275,
  258,  277,  278,   45,   -1,  281,  264,   -1,   59,  258,
   -1,   -1,   -1,  271,  263,  264,   58,  275,  258,  277,
  278,   -1,  271,  272,  264,   -1,   60,  276,   62,  278,
  279,  271,   -1,  258,   -1,  275,   -1,  277,  278,  264,
   -1,  281,   -1,   -1,   -1,  258,  271,   -1,   -1,   -1,
  275,  264,  277,  278,  258,  258,  281,   -1,  271,   -1,
  264,  264,  275,  258,  277,  278,   -1,  271,  271,  264,
   -1,  275,  258,  277,  278,  278,  271,  281,  264,   -1,
  275,   -1,  277,  278,   -1,  271,  281,   -1,   -1,  275,
   -1,  277,  278,  258,  258,  281,   -1,   -1,  258,  264,
  264,   -1,  258,   -1,  264,   -1,  271,  271,  264,   -1,
  275,  271,  277,  278,  278,  271,  281,  258,  278,   -1,
  258,  281,  278,  264,   -1,  281,  264,   -1,   -1,  258,
  271,   -1,   -1,  271,  275,  264,  277,  278,   -1,  277,
  278,   -1,  271,   -1,   -1,   -1,  258,   -1,  277,  278,
   -1,  263,  264,   -1,   -1,   -1,   -1,  269,  270,  271,
  272,   -1,   -1,  258,  276,   -1,  278,  279,  263,  264,
  265,  266,  267,  268,  269,  270,  271,  272,   -1,   -1,
  258,  276,   -1,  278,  279,  263,  264,   -1,   -1,   -1,
  258,  269,  270,  271,  272,  263,  264,   -1,  276,   -1,
  278,  279,  270,  271,  272,   -1,   -1,  258,  276,   -1,
  278,  279,  263,  264,  256,   -1,  258,  259,  260,  270,
  271,  272,   -1,   -1,   -1,  276,   -1,  278,  279,   -1,
   -1,  265,  266,  267,  268,
};
}
final static short YYFINAL=5;
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
"sentencia_declarativa : tipo lista_de_variables",
"sentencia_declarativa : funcion",
"sentencia_declarativa : FUNC lista_de_variables",
"sentencia_declarativa : lista_de_variables ';'",
"lista_de_variables : lista_de_variables ',' ID",
"lista_de_variables : ID",
"funcion : tipo FUNC ID '(' parametro ')' bloque_sentencias_declarativas BEGIN sentencias_ejecutables RETURN '(' retorno ')' ';' END",
"funcion : tipo FUNC ID '(' parametro ')' bloque_sentencias_declarativas BEGIN pre_condicion ';' sentencias_ejecutables RETURN '(' retorno ')' ';' END",
"funcion : tipo FUNC ID '(' parametro ')' bloque_sentencias_declarativas BEGIN pre_condicion sentencias_ejecutables RETURN '(' retorno ')' ';' END",
"pre_condicion : PRE ':' condicion ',' STRING",
"pre_condicion : PRE condicion ',' STRING",
"pre_condicion : PRE ':' condicion STRING",
"pre_condicion : PRE ':' ',' STRING",
"pre_condicion : PRE ':' condicion ','",
"pre_condicion : PRE ':' condicion",
"pre_condicion : PRE error",
"retorno : expresion",
"parametro : tipo ID",
"bloque_sentencias_ejecutables : BEGIN sentencias_ejecutables END",
"bloque_sentencias_ejecutables : BEGIN END",
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
"while : WHILE DO bloque_sentencias_while",
"while : WHILE condicion bloque_sentencias_while",
"bloque_sentencias_while : BEGIN sentencias_ejecutables_while END",
"bloque_sentencias_while : BEGIN END",
"sentencias_ejecutables_while : sentencias_ejecutables_while sentencia_ejecutable_while",
"sentencias_ejecutables_while : sentencia_ejecutable_while",
"sentencia_ejecutable_while : sentencia_ejecutable",
"sentencia_ejecutable_while : BREAK ';'",
"sentencia_ejecutable_while : BREAK",
"llamado_funcion : ID '(' parametro ')'",
"llamado_funcion : ID '(' error ')'",
"llamado_funcion : ID parametro ')'",
"llamado_funcion : ID '(' parametro",
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
"sentencia_print : PRINT STRING ')'",
"sentencia_print : PRINT '(' STRING",
"asignacion : ID ASIG expresion",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"expresion : expresion error termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : ID",
"factor : uint_factor",
"factor : double_factor",
"uint_factor : CTE_UINT",
"uint_factor : '-' CTE_UINT",
"double_factor : CTE_DOUBLE",
"double_factor : '-' CTE_DOUBLE",
"tipo : UINT",
"tipo : DOUBLE",
};

//#line 143 "gramatica.y"
  private Text text;
  private LexicAnalyzer lexicAnalyzer;
  private StructureUtilities su;
  public Parser(Text text, boolean debug){
    this.text = text;
	this.su = new StructureUtilities(text);
    this.lexicAnalyzer = new LexicAnalyzer(text,su);
    yydebug=debug;
  }

  private void showResults(){
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
//#line 527 "Parser.java"
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
case 1:
//#line 9 "gramatica.y"
{showResults();}
break;
case 8:
//#line 19 "gramatica.y"
{su.addError(" falta ; luego de la sentencia");}
break;
case 9:
//#line 20 "gramatica.y"
{su.addError(" falta ; luego de la sentencia");}
break;
case 10:
//#line 21 "gramatica.y"
{su.addError(" falta ; luego de la sentencia");}
break;
case 11:
//#line 22 "gramatica.y"
{su.addError(" la/las variables no tienen tipo");}
break;
case 16:
//#line 30 "gramatica.y"
{su.addError(" falta ; luego de la sentencia PRE dentro de la funcion " + val_peek(13).sval);}
break;
case 18:
//#line 33 "gramatica.y"
{su.addError(" falta : luego de PRE");}
break;
case 19:
//#line 34 "gramatica.y"
{su.addError(" falta , luego de la condicion en PRE");}
break;
case 20:
//#line 35 "gramatica.y"
{su.addError(" falta falta la condicion para la sentencia PRE");}
break;
case 21:
//#line 36 "gramatica.y"
{su.addError(" falta la cadena de caracteres luego de la ,");}
break;
case 22:
//#line 37 "gramatica.y"
{su.addError(" falta , y la cadena de caracteres luego de la condicion");}
break;
case 23:
//#line 38 "gramatica.y"
{su.addError(" la sentencia PRE no es correcta sintacticamente");}
break;
case 30:
//#line 51 "gramatica.y"
{System.err.println("ASIGNACION");}
break;
case 31:
//#line 52 "gramatica.y"
{System.err.println("LLAMADO FUNCION");}
break;
case 32:
//#line 53 "gramatica.y"
{System.err.println("CLAUSULA SELECCION");}
break;
case 33:
//#line 54 "gramatica.y"
{System.err.println("PRINT");}
break;
case 34:
//#line 55 "gramatica.y"
{System.err.println("WHILE");}
break;
case 35:
//#line 56 "gramatica.y"
{su.addError(" falta ; luego de la sentencia");}
break;
case 36:
//#line 57 "gramatica.y"
{su.addError(" falta ; luego de la sentencia");}
break;
case 37:
//#line 58 "gramatica.y"
{su.addError(" falta ; luego de la sentencia");}
break;
case 38:
//#line 59 "gramatica.y"
{su.addError(" falta ; luego de la sentencia");}
break;
case 39:
//#line 60 "gramatica.y"
{su.addError(" falta ; luego de la sentencia");}
break;
case 41:
//#line 64 "gramatica.y"
{su.addError(" falta la condicion de corte del WHILE");}
break;
case 42:
//#line 65 "gramatica.y"
{su.addError(" falta DO en la sentencia WHILE");}
break;
case 49:
//#line 75 "gramatica.y"
{su.addError(" falta ; luego de la sentencia");}
break;
case 51:
//#line 79 "gramatica.y"
{su.addError(" no se reconoce el parametro en la llamada a la funcion");}
break;
case 52:
//#line 80 "gramatica.y"
{su.addError(" falta parentesis de apertura en la llamada a la funcion");}
break;
case 53:
//#line 81 "gramatica.y"
{su.addError(" falta parentesis de cierre en la llamada a la funcion");}
break;
case 56:
//#line 86 "gramatica.y"
{su.addError(" falta THEN en la sentencia IF");}
break;
case 57:
//#line 87 "gramatica.y"
{su.addError(" falta ENDIF en la sentencia IF");}
break;
case 58:
//#line 88 "gramatica.y"
{su.addError(" falta THEN en la sentencia IF");}
break;
case 59:
//#line 89 "gramatica.y"
{su.addError(" falta ENDIF en la sentencia IF");}
break;
case 60:
//#line 90 "gramatica.y"
{su.addError(" falta la condicion en la sentencia IF");}
break;
case 61:
//#line 91 "gramatica.y"
{su.addError(" falta la condicion en la sentencia IF");}
break;
case 62:
//#line 92 "gramatica.y"
{su.addError(" falta bloque de sentencias luego del THEN");}
break;
case 63:
//#line 93 "gramatica.y"
{su.addError(" falta bloque de sentencias luego del THEN");}
break;
case 64:
//#line 94 "gramatica.y"
{su.addError(" falta bloque de sentencias luego del ELSE");}
break;
case 66:
//#line 97 "gramatica.y"
{su.addError(" falta parentesis de cierre en la condicion");}
break;
case 67:
//#line 98 "gramatica.y"
{su.addError(" falta parentesis de apertura en la condicion");}
break;
case 80:
//#line 115 "gramatica.y"
{su.addError(" falta parentesis de apertura para la sentencia PRINT");}
break;
case 81:
//#line 116 "gramatica.y"
{su.addError(" falta parentesis de cierre para la sentencia PRINT");}
break;
case 82:
//#line 118 "gramatica.y"
{}
break;
case 85:
//#line 122 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 86:
//#line 123 "gramatica.y"
{su.addError(" no se reconoce el operador");}
break;
case 89:
//#line 127 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 90:
//#line 129 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 92:
//#line 131 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 93:
//#line 133 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 94:
//#line 134 "gramatica.y"
{su.addError("UINT no puede ser negativo");}
break;
case 95:
//#line 136 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 96:
//#line 137 "gramatica.y"
{yyval.sval = val_peek(0).sval; setToNegative(val_peek(0).sval);}
break;
//#line 880 "Parser.java"
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
