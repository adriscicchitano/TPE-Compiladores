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
import java.io.IOException;
//#line 22 "Parser.java"




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
   11,   11,   11,   11,   10,    8,    2,    2,    9,    9,
   14,   14,   14,   14,   14,   14,   14,   14,   18,   18,
   18,   19,   19,   20,   20,   21,   21,   21,   22,   22,
   22,   16,   16,   16,   16,   16,   16,   16,   16,   16,
   16,   16,   12,   12,   12,   23,   23,   24,   24,   25,
   25,   25,   25,   25,   25,   25,   17,   17,   17,   15,
   13,   13,   13,   13,   26,   26,   26,   27,   27,   27,
   27,   28,   28,   29,   29,    5,    5,
};
final static short yylen[] = {                            2,
    2,    1,    2,    1,    3,    2,    3,    2,    1,    2,
    2,    3,    1,   15,   16,    3,    6,    5,    5,    5,
    5,    5,    4,    3,    1,    2,    3,    2,    2,    1,
    2,    2,    2,    2,    1,    1,    1,    1,    4,    3,
    3,    3,    2,    2,    1,    1,    2,    1,    4,    3,
    3,    7,    5,    6,    6,    4,    4,    6,    4,    6,
    4,    6,    3,    2,    2,    3,    1,    3,    1,    3,
    3,    3,    3,    3,    3,    1,    4,    3,    3,    3,
    3,    3,    1,    3,    3,    3,    1,    1,    1,    1,
    1,    1,    2,    1,    2,    1,    1,
};
final static short yydefred[] = {                         0,
   13,   96,   97,    0,    0,    0,    0,    4,    0,    0,
    0,    0,    0,    1,    3,    0,    0,   11,    0,    6,
    7,    0,    0,    0,   28,    0,    0,   30,    0,    0,
    0,    0,   16,    0,    5,   12,    0,    0,    0,    0,
   92,   94,    0,    0,    0,    0,    0,   91,    0,    0,
    0,    0,   87,   89,   90,    0,    0,   27,   29,   31,
   32,   33,   34,    0,    0,   78,    0,    0,    0,    0,
    0,    0,   93,   95,    0,    0,    0,    0,    0,    0,
   65,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   40,    0,   41,    0,   77,   51,    0,   26,   50,
    0,   59,   63,    0,   61,    0,    0,   56,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   85,
   86,    0,   43,   46,    0,   45,   39,    0,   49,    0,
    0,    0,   53,    0,   47,   42,   44,    0,   58,   60,
   62,    0,   54,    0,   52,    0,    0,    0,    0,    0,
    0,    0,    0,   24,    0,    0,    0,    0,    0,    0,
    0,   23,    0,    0,    0,    0,    0,   21,   20,    0,
   22,   19,    0,    0,   17,    0,    0,   14,    0,   15,
};
final static short yydgoto[] = {                          5,
    6,   14,    7,    8,    9,   10,   11,   70,   27,  165,
  148,   46,   47,   28,   29,   30,   31,   32,   92,  125,
  126,   48,   49,   50,   51,   52,   53,   54,   55,
};
final static short yysindex[] = {                      -175,
    0,    0,    0, -236,    0, -244, -175,    0, -245,   20,
  -21,   33,  294,    0,    0, -201,   36,    0, -190,    0,
    0, -184,  -31,    9,    0,   79,  317,    0,   22,   35,
   52,   70,    0,  101,    0,    0,  -40,   93, -104,   30,
    0,    0, -244,  -40, -111, -160,  -35,    0,  -27,  -86,
  288,  -13,    0,    0,    0,  -83, -113,    0,    0,    0,
    0,    0,    0,  -85,  -35,    0,  156, -153,  -55,  165,
 -109,  -26,    0,    0, -116,  -73,  -40,  -40,  -40,  -40,
    0,  -40,  -40,  -40,  -40,  -40,  -40,  -40,  -40,  -40,
  223,    0,  -83,    0,  167,    0,    0,  169,    0,    0,
 -244,    0,    0, -244,    0,  132, -244,    0,  -13,  -13,
  -13,  -86,  288,  -35,  -35,  -35,  -35,  -35,  -35,    0,
    0,  153,    0,    0,  264,    0,    0, -175,    0,  -60,
  -58, -170,    0,  -29,    0,    0,    0,  -43,    0,    0,
    0,  -18,    0, -241,    0,  422, -225,  -96,  221,   57,
  249,  295, -151,    0,   41,   66,   65,  -40,  301,  293,
  296,    0,  -52,  306,  326,  -35,  -40,    0,    0,  313,
    0,    0,  316,  341,    0,  107,  330,    0,  115,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,  123,    0,    0,    0,
 -130,  -67,    0,    0,    0,    0,  235,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -193,  -97,
  -79,  168,    0,    0,    0,    0,    0,    0,    0,  -41,
    0,    0,    0,    0,    0,    0,   77,    0,    0,  344,
  310,  -17,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  207,    0,  231,    0,    0,    0,
    0,  198,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  246,    0,    0,    7,   31,
   55,  400,  327,   94,  111,  128,  145,  375,  392,    0,
    0,  286,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  255,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  368,    0,    0,    0,  208,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  290,  430,    0,  413,  -24,   27,    0,   58,   -1,  256,
    0,   -6,  -25,   -2,    0,    0,    0,    0,  -46,    0,
  297,    0,  381,  351,  348,  350,  312,    0,    0,
};
final static int YYTABLESIZE=682;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         88,
   88,   88,   88,   88,   45,   88,  171,   78,   39,   79,
   94,   65,    1,   81,  103,   69,   22,   88,   88,   57,
   88,    1,   23,   83,   59,   83,   83,   83,   89,   24,
   12,   13,   22,   90,   16,   17,   26,   20,   23,   69,
  146,   83,   83,   69,   83,   24,  127,   84,   44,   84,
   84,   84,   26,   45,   33,  152,   34,  114,  115,  116,
  117,  118,  119,   19,   35,   84,   84,   36,   84,   68,
   35,   81,   37,   81,   81,   81,   19,   35,   18,   19,
   60,   35,    1,   35,   35,    2,    3,   35,  124,   81,
   81,   21,   81,   61,   35,   82,   44,   82,   82,   82,
  155,   45,   97,  141,    4,   13,   22,    2,    3,  163,
   62,   75,   23,   82,   82,   13,   82,   76,   44,   24,
   76,   95,  124,   45,  162,   98,   26,    9,   63,  159,
    9,    9,  166,   66,   72,   76,   76,   72,   76,  151,
   64,  166,  147,  156,   59,    9,  153,   73,   74,    9,
   59,   73,   72,   72,   73,   72,  104,  105,   67,   13,
   36,   22,   91,  101,  102,   93,   36,   23,   74,   73,
   73,   74,   73,   36,   24,    2,    3,   36,   37,   36,
   36,   26,   82,   36,   37,   75,   74,   74,   75,   74,
   10,   37,   91,   10,   10,   37,   96,   37,   37,  107,
  108,   37,   99,   75,   75,  100,   75,  128,   10,  129,
  170,  135,   10,  139,   88,  140,   88,   40,   41,   42,
   77,   88,   88,   88,   88,   88,   88,   88,   88,   88,
   88,   38,  144,   88,   88,   88,   88,   88,   83,   88,
   83,   64,   80,   80,  143,   83,   83,   83,   83,   83,
   83,   83,   83,   83,   83,  145,   64,   83,   83,   83,
   83,   83,   84,   83,   84,   80,   40,   41,   42,   84,
   84,   84,   84,   84,   84,   84,   84,   84,   84,  154,
   43,   84,   84,   84,   84,   84,   81,   84,   81,   79,
    2,    3,  157,   81,   81,   81,   81,   81,   81,   81,
   81,   81,   81,  160,   57,   81,   81,   81,   81,   81,
   82,   81,   82,   55,   40,   41,   42,   82,   82,   82,
   82,   82,   82,   82,   82,   82,   82,  164,  161,   82,
   82,   82,   82,   82,  158,   82,   40,   41,   42,   76,
  167,   76,   76,   76,   76,   76,   76,   88,   76,   87,
   69,  168,   76,   69,  169,   76,   72,   56,   72,   72,
   72,   72,   72,   72,  172,   72,  173,   68,   69,   72,
   68,  175,   72,   73,  176,   73,   73,   73,   73,   73,
   73,  177,   73,  178,   67,   68,   73,   67,  179,   73,
   74,  180,   74,   74,   74,   74,   74,   74,    2,   74,
  120,  121,   67,   74,  132,  133,   74,   75,   25,   75,
   75,   75,   75,   75,   75,   70,   75,  138,   70,   15,
   75,  137,  174,   75,   72,   38,  109,  110,  111,  113,
  112,   38,   71,   70,   70,   71,   70,    0,   38,    0,
   66,    0,   38,   66,   38,   38,    0,    0,   38,    0,
   71,   71,    0,   71,    0,    0,    0,    0,   66,    0,
   64,   44,    0,    0,   80,   18,   45,    0,    0,   64,
   80,   18,   71,   64,    0,   76,   64,   80,   18,  150,
   22,   80,    0,   80,   80,   18,   23,   80,   79,    0,
    0,    0,    8,   24,   79,    8,    8,  122,    0,  123,
   26,   79,    0,   57,  106,   79,    0,   79,   79,   57,
    8,   79,   55,    0,    8,    0,   57,    0,   55,    0,
   57,   22,   57,   57,    0,   55,   57,   23,    0,   55,
  130,   55,   55,  131,   24,   55,  134,    0,  122,    0,
  136,   26,    0,   48,    0,    0,    0,    0,    0,   48,
    0,   22,   83,   84,   85,   86,   48,   23,    0,    0,
   48,  142,   48,   48,   24,    0,    0,    0,    0,    0,
   25,   26,   69,    0,   22,    0,    0,    0,   69,   69,
   23,   69,    0,    0,    0,   69,    0,   24,   69,   68,
    0,    0,    0,   58,   26,   68,   68,    0,   68,    0,
    0,    0,   68,    0,    0,   68,   67,    0,    0,    0,
    0,    0,    0,   67,    0,   67,    0,    0,    0,   67,
    0,    0,   67,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   70,    0,   70,
   70,   70,   70,   70,   70,    0,   70,    0,    0,    0,
   70,    0,    0,   70,   71,    0,   71,   71,   71,   71,
   71,   71,   66,   71,    0,    0,    0,   71,    0,   66,
   71,   66,    0,    0,    0,   66,    0,  149,   66,   40,
   41,   42,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   45,   47,   59,   43,   40,   45,
   57,   37,  258,   41,   41,   40,  258,   59,   60,   26,
   62,  258,  264,   41,   27,   43,   44,   45,   42,  271,
    4,  276,  258,   47,  280,    9,  278,   59,  264,   64,
  282,   59,   60,   68,   62,  271,   93,   41,   40,   43,
   44,   45,  278,   45,  256,  281,  258,   83,   84,   85,
   86,   87,   88,   44,  258,   59,   60,  258,   62,   40,
  264,   41,  257,   43,   44,   45,   44,  271,   59,   44,
   59,  275,  258,  277,  278,  261,  262,  281,   91,   59,
   60,   59,   62,   59,   59,   41,   40,   43,   44,   45,
   44,   45,  256,  274,  280,  276,  258,  261,  262,   44,
   59,  272,  264,   59,   60,  276,   62,   41,   40,  271,
   44,   64,  125,   45,   59,   68,  278,  258,   59,  281,
  261,  262,  158,   41,   41,   59,   60,   44,   62,  146,
   40,  167,  144,  150,  147,  276,  148,  259,  260,  280,
  153,   41,   59,   60,   44,   62,  273,  274,  263,  276,
  258,  258,  276,  273,  274,  279,  264,  264,   41,   59,
   60,   44,   62,  271,  271,  261,  262,  275,  258,  277,
  278,  278,  269,  281,  264,   41,   59,   60,   44,   62,
  258,  271,  276,  261,  262,  275,   41,  277,  278,  273,
  274,  281,  258,   59,   60,   41,   62,   41,  276,   41,
  263,   59,  280,  274,  256,  274,  258,  258,  259,  260,
  256,  263,  264,  265,  266,  267,  268,  269,  270,  271,
  272,  263,  276,  275,  276,  277,  278,  279,  256,  281,
  258,   44,  270,  270,  274,  263,  264,  265,  266,  267,
  268,  269,  270,  271,  272,  274,   59,  275,  276,  277,
  278,  279,  256,  281,  258,   59,  258,  259,  260,  263,
  264,  265,  266,  267,  268,  269,  270,  271,  272,   59,
  272,  275,  276,  277,  278,  279,  256,  281,  258,   59,
  261,  262,   44,  263,  264,  265,  266,  267,  268,  269,
  270,  271,  272,  263,   59,  275,  276,  277,  278,  279,
  256,  281,  258,   59,  258,  259,  260,  263,  264,  265,
  266,  267,  268,  269,  270,  271,  272,  263,  263,  275,
  276,  277,  278,  279,   40,  281,  258,  259,  260,  263,
   40,  265,  266,  267,  268,  269,  270,   60,  272,   62,
   41,   59,  276,   44,   59,  279,  263,  279,  265,  266,
  267,  268,  269,  270,   59,  272,   41,   41,   59,  276,
   44,   59,  279,  263,   59,  265,  266,  267,  268,  269,
  270,   41,  272,  277,   41,   59,  276,   44,   59,  279,
  263,  277,  265,  266,  267,  268,  269,  270,  276,  272,
   89,   90,   59,  276,  273,  274,  279,  263,   41,  265,
  266,  267,  268,  269,  270,   41,  272,  128,   44,    7,
  276,  125,  167,  279,   44,  258,   77,   78,   79,   82,
   80,  264,   41,   59,   60,   44,   62,   -1,  271,   -1,
   41,   -1,  275,   44,  277,  278,   -1,   -1,  281,   -1,
   59,   60,   -1,   62,   -1,   -1,   -1,   -1,   59,   -1,
  263,   40,   -1,   -1,  258,  258,   45,   -1,   -1,  272,
  264,  264,   43,  276,   -1,   46,  279,  271,  271,   58,
  258,  275,   -1,  277,  278,  278,  264,  281,  258,   -1,
   -1,   -1,  258,  271,  264,  261,  262,  275,   -1,  277,
  278,  271,   -1,  258,   75,  275,   -1,  277,  278,  264,
  276,  281,  258,   -1,  280,   -1,  271,   -1,  264,   -1,
  275,  258,  277,  278,   -1,  271,  281,  264,   -1,  275,
  101,  277,  278,  104,  271,  281,  107,   -1,  275,   -1,
  277,  278,   -1,  258,   -1,   -1,   -1,   -1,   -1,  264,
   -1,  258,  265,  266,  267,  268,  271,  264,   -1,   -1,
  275,  132,  277,  278,  271,   -1,   -1,   -1,   -1,   -1,
  277,  278,  263,   -1,  258,   -1,   -1,   -1,  269,  270,
  264,  272,   -1,   -1,   -1,  276,   -1,  271,  279,  263,
   -1,   -1,   -1,  277,  278,  269,  270,   -1,  272,   -1,
   -1,   -1,  276,   -1,   -1,  279,  263,   -1,   -1,   -1,
   -1,   -1,   -1,  270,   -1,  272,   -1,   -1,   -1,  276,
   -1,   -1,  279,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  263,   -1,  265,
  266,  267,  268,  269,  270,   -1,  272,   -1,   -1,   -1,
  276,   -1,   -1,  279,  263,   -1,  265,  266,  267,  268,
  269,  270,  263,  272,   -1,   -1,   -1,  276,   -1,  270,
  279,  272,   -1,   -1,   -1,  276,   -1,  256,  279,  258,
  259,  260,
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
"funcion : tipo FUNC error",
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
"sentencia_ejecutable : clausula_seleccion ';'",
"sentencia_ejecutable : sentencia_print ';'",
"sentencia_ejecutable : while ';'",
"sentencia_ejecutable : asignacion",
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
"llamado_funcion : ID parametro ')'",
"llamado_funcion : ID '(' error",
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
"factor : llamado_funcion",
"uint_factor : CTE_UINT",
"uint_factor : '-' CTE_UINT",
"double_factor : CTE_DOUBLE",
"double_factor : '-' CTE_DOUBLE",
"tipo : UINT",
"tipo : DOUBLE",
};

//#line 144 "gramatica.y"
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
//#line 532 "Parser.java"
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
//#line 10 "gramatica.y"
{showResults();}
break;
case 5:
//#line 17 "gramatica.y"
{su.addCodeStructure("Declaracion de variables " + val_peek(2).sval);}
break;
case 7:
//#line 19 "gramatica.y"
{su.addCodeStructure("Declaracion de variables de tipo FUNC");}
break;
case 8:
//#line 20 "gramatica.y"
{su.addError(" falta ; luego de la sentencia");}
break;
case 9:
//#line 21 "gramatica.y"
{su.addError(" falta ; luego de la sentencia");}
break;
case 10:
//#line 22 "gramatica.y"
{su.addError(" falta ; luego de la sentencia");}
break;
case 11:
//#line 23 "gramatica.y"
{su.addError(" la/las variables no tienen tipo");}
break;
case 14:
//#line 29 "gramatica.y"
{su.addCodeStructure("Declaracion de la funcion " + val_peek(12).sval);}
break;
case 15:
//#line 30 "gramatica.y"
{su.addCodeStructure("Declaracion de la funcion " + val_peek(13).sval);}
break;
case 16:
//#line 31 "gramatica.y"
{su.addError("La funcion es incorrecta sintacticamente");}
break;
case 18:
//#line 35 "gramatica.y"
{su.addError(" falta ; luego de PRE");}
break;
case 19:
//#line 36 "gramatica.y"
{su.addError(" falta : luego de PRE");}
break;
case 20:
//#line 37 "gramatica.y"
{su.addError(" falta , luego de la condicion en PRE");}
break;
case 21:
//#line 38 "gramatica.y"
{su.addError(" falta falta la condicion para la sentencia PRE");}
break;
case 22:
//#line 39 "gramatica.y"
{su.addError(" falta la cadena de caracteres luego de la ,");}
break;
case 23:
//#line 40 "gramatica.y"
{su.addError(" falta , y la cadena de caracteres luego de la condicion");}
break;
case 24:
//#line 41 "gramatica.y"
{su.addError(" la sentencia PRE no es correcta sintacticamente");}
break;
case 31:
//#line 54 "gramatica.y"
{su.addCodeStructure("ASIGNACION");}
break;
case 32:
//#line 55 "gramatica.y"
{su.addCodeStructure("SENTENCIA IF");}
break;
case 33:
//#line 56 "gramatica.y"
{su.addCodeStructure("SENTENCIA PRINT");}
break;
case 34:
//#line 57 "gramatica.y"
{su.addCodeStructure("WHILE");}
break;
case 35:
//#line 58 "gramatica.y"
{su.addError(" falta ; luego de la sentencia");}
break;
case 36:
//#line 59 "gramatica.y"
{su.addError(" falta ; luego de la sentencia");}
break;
case 37:
//#line 60 "gramatica.y"
{su.addError(" falta ; luego de la sentencia");}
break;
case 38:
//#line 61 "gramatica.y"
{su.addError(" falta ; luego de la sentencia");}
break;
case 40:
//#line 65 "gramatica.y"
{su.addError(" falta la condicion de corte del WHILE");}
break;
case 41:
//#line 66 "gramatica.y"
{su.addError(" falta DO en la sentencia WHILE");}
break;
case 48:
//#line 76 "gramatica.y"
{su.addError(" falta ; luego de la sentencia");}
break;
case 49:
//#line 79 "gramatica.y"
{su.addCodeStructure("LLamado a funcion " + val_peek(3).sval);}
break;
case 50:
//#line 80 "gramatica.y"
{su.addError("falta parentesis de apertura para el llamado a la funcion " + val_peek(2).sval);}
break;
case 51:
//#line 81 "gramatica.y"
{su.addError("la llamada a la funcion " + val_peek(2).sval + " no es correcta sintacticamente");}
break;
case 54:
//#line 86 "gramatica.y"
{su.addError(" falta THEN en la sentencia IF");}
break;
case 55:
//#line 87 "gramatica.y"
{su.addError(" falta ENDIF en la sentencia IF");}
break;
case 56:
//#line 88 "gramatica.y"
{su.addError(" falta THEN en la sentencia IF");}
break;
case 57:
//#line 89 "gramatica.y"
{su.addError(" falta ENDIF en la sentencia IF");}
break;
case 58:
//#line 90 "gramatica.y"
{su.addError(" falta la condicion en la sentencia IF");}
break;
case 59:
//#line 91 "gramatica.y"
{su.addError(" falta la condicion en la sentencia IF");}
break;
case 60:
//#line 92 "gramatica.y"
{su.addError(" falta bloque de sentencias luego del THEN");}
break;
case 61:
//#line 93 "gramatica.y"
{su.addError(" falta bloque de sentencias luego del THEN");}
break;
case 62:
//#line 94 "gramatica.y"
{su.addError(" falta bloque de sentencias luego del ELSE");}
break;
case 64:
//#line 97 "gramatica.y"
{su.addError(" falta parentesis de cierre en la condicion");}
break;
case 65:
//#line 98 "gramatica.y"
{su.addError(" falta parentesis de apertura en la condicion");}
break;
case 66:
//#line 100 "gramatica.y"
{su.addCodeStructure("Se detecta una condicion AND");}
break;
case 68:
//#line 103 "gramatica.y"
{su.addCodeStructure("Se detecta una condicion OR");}
break;
case 70:
//#line 106 "gramatica.y"
{su.addCodeStructure("Se detecta una condicion >");}
break;
case 71:
//#line 107 "gramatica.y"
{su.addCodeStructure("Se detecta una condicion <");}
break;
case 72:
//#line 108 "gramatica.y"
{su.addCodeStructure("Se detecta una condicion >=");}
break;
case 73:
//#line 109 "gramatica.y"
{su.addCodeStructure("Se detecta una condicion <=");}
break;
case 74:
//#line 110 "gramatica.y"
{su.addCodeStructure("Se detecta una condicion ==");}
break;
case 75:
//#line 111 "gramatica.y"
{su.addCodeStructure("Se detecta una condicion <>");}
break;
case 78:
//#line 115 "gramatica.y"
{su.addError(" falta parentesis de apertura para la sentencia PRINT");}
break;
case 79:
//#line 116 "gramatica.y"
{su.addError(" falta parentesis de cierre para la sentencia PRINT");}
break;
case 81:
//#line 120 "gramatica.y"
{su.addCodeStructure("Se detecta una suma");}
break;
case 82:
//#line 121 "gramatica.y"
{su.addCodeStructure("Se detecta una resta");}
break;
case 83:
//#line 122 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 84:
//#line 123 "gramatica.y"
{su.addError(" no se reconoce el operador");}
break;
case 85:
//#line 125 "gramatica.y"
{su.addCodeStructure("Se detecta una multiplicacion");}
break;
case 86:
//#line 126 "gramatica.y"
{su.addCodeStructure("Se detecta una division");}
break;
case 87:
//#line 127 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 88:
//#line 129 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 90:
//#line 131 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 92:
//#line 134 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 93:
//#line 135 "gramatica.y"
{su.addError("UINT no puede ser negativo");}
break;
case 94:
//#line 137 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 95:
//#line 138 "gramatica.y"
{yyval.sval = val_peek(0).sval; setToNegative(val_peek(0).sval);}
break;
case 96:
//#line 140 "gramatica.y"
{yyval.sval = "UINT";}
break;
case 97:
//#line 141 "gramatica.y"
{yyval.sval = "DOUBLE";}
break;
//#line 949 "Parser.java"
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
