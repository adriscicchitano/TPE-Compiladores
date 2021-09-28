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
    4,    6,    6,    7,    7,   11,   11,   11,   11,   11,
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
    2,    3,    1,   15,   16,    6,    5,    5,    5,    5,
    5,    4,    3,    1,    2,    3,    2,    2,    1,    2,
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
   62,   64,    0,   56,    0,   54,    0,    0,    0,    0,
    0,    0,    0,    0,   23,    0,    0,    0,    0,    0,
    0,    0,   22,    0,    0,    0,    0,    0,   20,   19,
    0,   21,   18,    0,    0,   16,    0,    0,   14,    0,
   15,
};
final static short yydgoto[] = {                          5,
    6,   14,    7,    8,    9,   10,   11,   40,   27,  166,
  149,   49,   50,   28,   29,   30,   31,   32,   33,   96,
  127,  128,   51,   52,   53,   54,   55,   56,   57,
};
final static short yysindex[] = {                      -223,
    0,    0,    0, -248,    0, -253, -223,    0, -236,   -3,
  -13,   19, -137,    0,    0, -196,   21,    0, -187,    0,
    0,  103,  -31,  -15,    0,   55,  199,    0,   20,   23,
   30,   33,   51,   95,    0,    0,  272, -138, -119,  101,
  109, -102,    0,    0,    0, -253,  272, -107, -256,  -38,
  -28, -110,  -54,   26,    0,    0,    0, -106, -243,    0,
    0,    0,    0,    0,    0,    0,  -93,  -38,  135,  152,
    0,    0,    0,  155,   17,   -4,    0,    0, -175,  105,
  272,  272,  272,  272,    0,  272,  272,  272,  272,  272,
  272,  272,  272,  272,  327,    0, -106,    0,  158,    0,
    0,    0, -253,    0,    0, -253,    0,  142, -253,    0,
   26,   26,   26, -110,  -54,  -38,  -38,  -38,  -38,  -38,
  -38,    0,    0,  143,    0,    0,  345,    0,    0, -223,
  -73,  -67, -188,    0,  -64,    0,    0,    0,  -57,    0,
    0,    0,   -5,    0, -224,    0,  337, -162,  270,  208,
  354,  237,  253,  -86,    0,   42,  -30,   48,  272,  284,
  267,  282,    0,  -47,  289,  287,  -38,  272,    0,    0,
  292,    0,    0,  299,  290,    0,   66,  302,    0,   91,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,   99,    0,    0,    0,
 -125,  -72,    0,    0,    0,    0,  189,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -194, -104,
  181,  269,  293,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   53,
    0,  286,  150,  -41,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  197,    0,  209,
    0,    0,    0,  221,    0,  161,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  233,    0,    0,
  -17,    7,   31,  303,  162,   70,   87,  104,  121,  138,
  368,    0,    0,  381,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  245,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  340,    0,    0,    0,
  281,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
  255,  475,    0,  385,   -7,  116,    0,   -6,  -90,  228,
    0,  -25,   16,  -10,    0,    0,    0,    0,    0,  -48,
    0,  275,  364,  334,  335,   75,   94,    0,    0,
};
final static int YYTABLESIZE=659;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         85,
   59,   85,   85,   85,   82,   92,   83,   91,   42,    1,
   98,  172,   85,  164,   39,   79,   61,   85,   85,   13,
   85,    1,   13,   86,   47,   86,   86,   86,  163,   48,
   39,   70,   95,   22,    1,   97,  105,    2,    3,   23,
   19,   86,   86,   16,   86,   20,   24,   83,  129,   83,
   83,   83,   68,   26,  148,   18,    4,  147,  154,   39,
   99,   34,   19,   35,   19,   83,   83,   93,   83,   35,
   36,   84,   94,   84,   84,   84,   35,   21,   62,   35,
   35,   63,   35,   35,  126,  142,   35,   13,   64,   84,
   84,   65,   84,   78,   47,   22,   78,  106,  107,   48,
   13,   23,  116,  117,  118,  119,  120,  121,   24,   66,
   74,   78,   78,   74,   78,   26,  126,   69,  153,   12,
   22,  152,    2,    3,   17,  157,   23,   75,   74,   74,
   75,   74,    9,   24,   67,    9,    9,   61,   71,   25,
   26,   72,   38,   61,   76,   75,   75,   76,   75,   73,
    9,   77,   78,   36,    9,  111,  112,  113,   86,   36,
   74,   77,   76,   76,   77,   76,   36,    2,    3,   95,
   36,   22,   36,   36,  167,  100,   36,   23,   72,   77,
   77,   72,   77,  167,   24,   10,  122,  123,   10,   10,
   71,   26,  101,   71,  160,  102,   72,   72,  130,   72,
  140,  136,   70,   10,   66,   70,  141,   10,   71,  144,
   87,   88,   89,   90,   85,  171,   85,   81,  145,   66,
   70,   85,   85,   85,   85,   85,   85,   85,   85,   85,
   85,   41,  162,   85,   85,   85,   85,   85,   86,   85,
   86,   84,   43,   44,   45,   86,   86,   86,   86,   86,
   86,   86,   86,   86,   86,   82,   46,   86,   86,   86,
   86,   86,   83,   86,   83,   84,  155,   53,  146,   83,
   83,   83,   83,   83,   83,   83,   83,   83,   83,   81,
  158,   83,   83,   83,   83,   83,   84,   83,   84,  103,
  104,   59,  159,   84,   84,   84,   84,   84,   84,   84,
   84,   84,   84,   57,  161,   84,   84,   84,   84,   84,
  165,   84,   43,   44,   45,   78,   48,   78,   78,   78,
   78,   78,   78,  168,   78,  169,   69,  174,   78,   69,
  178,   78,   74,   58,   74,   74,   74,   74,   74,   74,
  170,   74,  179,   68,   69,   74,   68,  173,   74,   75,
  176,   75,   75,   75,   75,   75,   75,  177,   75,   37,
  180,   68,   75,    2,    3,   75,   76,  181,   76,   76,
   76,   76,   76,   76,    2,   76,   47,  109,  110,   76,
   24,   48,   76,   77,  139,   77,   77,   77,   77,   77,
   77,   15,   77,   47,  151,  175,   77,  156,   48,   77,
   72,  138,   72,   72,   72,   72,   72,   72,   73,   72,
   76,   73,   71,   72,  133,  134,   72,  114,   71,   71,
  115,   71,    0,   66,   70,   71,   73,   73,   71,   73,
   70,   70,   66,   70,    0,    0,   66,   70,   37,   66,
   70,    0,    0,    0,   37,    0,    8,    0,    0,    8,
    8,   37,    0,    0,   82,   37,   22,   37,   37,    0,
   82,   37,   23,    0,    8,    0,   53,   82,    8,   24,
    0,   82,   53,   82,   82,   60,   26,   82,   81,   53,
    0,    0,    0,   53,   81,   53,   53,    0,    0,   53,
   59,   81,    0,    0,    0,   81,   59,   81,   81,    0,
    0,   81,   57,   59,    0,    0,    0,   59,   57,   59,
   59,    0,    0,   59,    0,   57,    0,    0,    0,   57,
   75,   57,   57,   80,    0,   57,   38,   22,    0,   43,
   44,   45,   38,   23,    0,    0,    0,    0,   17,   38,
   24,    0,    0,   38,   17,   38,   38,   26,   69,   38,
   39,   17,    0,  108,    0,   69,   39,   69,   17,    0,
    0,   69,    0,   39,   69,   68,    0,   39,    0,   39,
   39,    0,   68,   39,   68,    0,    0,  131,   68,    0,
  132,   68,    0,  135,   22,    0,    0,    0,    0,    0,
   23,    0,  150,    0,   43,   44,   45,   24,    0,    0,
    0,  124,   22,  125,   26,    0,    0,  143,   23,    0,
    0,   43,   44,   45,    0,   24,    0,    0,    0,  124,
    0,  137,   26,    0,    0,    0,    0,    0,    0,    0,
   73,    0,   73,   73,   73,   73,   73,   73,   49,   73,
    0,    0,    0,   73,   49,    0,   73,    0,    0,    0,
    0,   49,    0,    0,    0,   49,    0,   49,   49,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   26,   43,   44,   45,   43,   60,   45,   62,   40,  258,
   59,   59,   41,   44,   22,  272,   27,   59,   60,  276,
   62,  258,  276,   41,   40,   43,   44,   45,   59,   45,
   38,   38,  276,  258,  258,  279,   41,  261,  262,  264,
   44,   59,   60,  280,   62,   59,  271,   41,   97,   43,
   44,   45,   37,  278,  145,   59,  280,  282,  149,   67,
   67,  258,   44,  258,   44,   59,   60,   42,   62,  264,
  258,   41,   47,   43,   44,   45,  271,   59,   59,   59,
  275,   59,  277,  278,   95,  274,  281,  276,   59,   59,
   60,   59,   62,   41,   40,  258,   44,  273,  274,   45,
  276,  264,   87,   88,   89,   90,   91,   92,  271,   59,
   41,   59,   60,   44,   62,  278,  127,  256,  281,    4,
  258,  147,  261,  262,    9,  151,  264,   41,   59,   60,
   44,   62,  258,  271,   40,  261,  262,  148,  258,  277,
  278,   41,   40,  154,   41,   59,   60,   44,   62,   41,
  276,  259,  260,  258,  280,   81,   82,   83,  269,  264,
  263,   41,   59,   60,   44,   62,  271,  261,  262,  276,
  275,  258,  277,  278,  159,   41,  281,  264,   41,   59,
   60,   44,   62,  168,  271,  258,   93,   94,  261,  262,
   41,  278,   41,   44,  281,   41,   59,   60,   41,   62,
  274,   59,   41,  276,   44,   44,  274,  280,   59,  274,
  265,  266,  267,  268,  256,  263,  258,  256,  276,   59,
   59,  263,  264,  265,  266,  267,  268,  269,  270,  271,
  272,  263,  263,  275,  276,  277,  278,  279,  256,  281,
  258,  270,  258,  259,  260,  263,  264,  265,  266,  267,
  268,  269,  270,  271,  272,   59,  272,  275,  276,  277,
  278,  279,  256,  281,  258,  270,   59,   59,  274,  263,
  264,  265,  266,  267,  268,  269,  270,  271,  272,   59,
   44,  275,  276,  277,  278,  279,  256,  281,  258,  273,
  274,   59,   40,  263,  264,  265,  266,  267,  268,  269,
  270,  271,  272,   59,  263,  275,  276,  277,  278,  279,
  263,  281,  258,  259,  260,  263,   45,  265,  266,  267,
  268,  269,  270,   40,  272,   59,   41,   41,  276,   44,
   41,  279,  263,  279,  265,  266,  267,  268,  269,  270,
   59,  272,  277,   41,   59,  276,   44,   59,  279,  263,
   59,  265,  266,  267,  268,  269,  270,   59,  272,  257,
   59,   59,  276,  261,  262,  279,  263,  277,  265,  266,
  267,  268,  269,  270,  276,  272,   40,  273,  274,  276,
   41,   45,  279,  263,  130,  265,  266,  267,  268,  269,
  270,    7,  272,   40,   58,  168,  276,   44,   45,  279,
  263,  127,  265,  266,  267,  268,  269,  270,   41,  272,
   47,   44,  263,  276,  273,  274,  279,   84,  269,  270,
   86,  272,   -1,  263,  263,  276,   59,   60,  279,   62,
  269,  270,  272,  272,   -1,   -1,  276,  276,  258,  279,
  279,   -1,   -1,   -1,  264,   -1,  258,   -1,   -1,  261,
  262,  271,   -1,   -1,  258,  275,  258,  277,  278,   -1,
  264,  281,  264,   -1,  276,   -1,  258,  271,  280,  271,
   -1,  275,  264,  277,  278,  277,  278,  281,  258,  271,
   -1,   -1,   -1,  275,  264,  277,  278,   -1,   -1,  281,
  258,  271,   -1,   -1,   -1,  275,  264,  277,  278,   -1,
   -1,  281,  258,  271,   -1,   -1,   -1,  275,  264,  277,
  278,   -1,   -1,  281,   -1,  271,   -1,   -1,   -1,  275,
   46,  277,  278,   49,   -1,  281,  258,  258,   -1,  258,
  259,  260,  264,  264,   -1,   -1,   -1,   -1,  258,  271,
  271,   -1,   -1,  275,  264,  277,  278,  278,  263,  281,
  258,  271,   -1,   79,   -1,  270,  264,  272,  278,   -1,
   -1,  276,   -1,  271,  279,  263,   -1,  275,   -1,  277,
  278,   -1,  270,  281,  272,   -1,   -1,  103,  276,   -1,
  106,  279,   -1,  109,  258,   -1,   -1,   -1,   -1,   -1,
  264,   -1,  256,   -1,  258,  259,  260,  271,   -1,   -1,
   -1,  275,  258,  277,  278,   -1,   -1,  133,  264,   -1,
   -1,  258,  259,  260,   -1,  271,   -1,   -1,   -1,  275,
   -1,  277,  278,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  263,   -1,  265,  266,  267,  268,  269,  270,  258,  272,
   -1,   -1,   -1,  276,  264,   -1,  279,   -1,   -1,   -1,
   -1,  271,   -1,   -1,   -1,  275,   -1,  277,  278,
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
"funcion : tipo FUNC ID '(' parametro ')' bloque_sentencias_declarativas BEGIN pre_condicion sentencias_ejecutables RETURN '(' retorno ')' ';' END",
"pre_condicion : PRE ':' condicion ',' STRING ';'",
"pre_condicion : PRE ':' condicion ',' STRING",
"pre_condicion : PRE condicion ',' STRING ';'",
"pre_condicion : PRE ':' condicion STRING ';'",
"pre_condicion : PRE ':' ',' STRING ';'",
"pre_condicion : PRE ':' condicion ',' ';'",
"pre_condicion : PRE ':' condicion ';'",
"pre_condicion : PRE error ';'",
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
//#line 524 "Parser.java"
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
case 17:
//#line 32 "gramatica.y"
{su.addError(" falta ; luego de PRE");}
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
//#line 877 "Parser.java"
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
