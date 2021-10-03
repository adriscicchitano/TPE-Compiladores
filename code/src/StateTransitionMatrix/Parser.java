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
public final static short WRONG_STRING=283;
public final static short CTE_STRING=284;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    3,    3,    4,    4,    4,    4,    4,    4,
    4,    6,    6,    7,    7,    7,   11,   11,   11,   11,
   11,   11,   11,   11,   11,   10,    8,    2,    2,    9,
    9,   14,   14,   14,   14,   14,   14,   14,   14,   18,
   18,   18,   19,   19,   20,   20,   21,   21,   21,   22,
   22,   22,   16,   16,   16,   16,   16,   16,   16,   16,
   16,   16,   16,   12,   12,   12,   23,   23,   24,   24,
   25,   25,   25,   25,   25,   25,   25,   25,   25,   17,
   17,   26,   26,   26,   26,   26,   15,   15,   13,   13,
   13,   13,   27,   27,   27,   28,   28,   28,   28,   29,
   29,   30,   30,    5,    5,    5,
};
final static short yylen[] = {                            2,
    2,    1,    2,    1,    3,    2,    3,    2,    1,    2,
    2,    3,    1,   15,   16,    4,    6,    5,    5,    5,
    5,    5,    4,    3,    5,    1,    2,    3,    2,    2,
    1,    2,    2,    2,    2,    1,    1,    1,    1,    4,
    3,    3,    3,    2,    2,    1,    1,    2,    1,    4,
    3,    3,    7,    5,    6,    6,    4,    4,    6,    4,
    6,    4,    6,    3,    2,    2,    3,    1,    3,    1,
    3,    3,    3,    3,    3,    3,    1,    3,    3,    2,
    2,    3,    2,    2,    3,    2,    3,    2,    3,    3,
    1,    3,    3,    3,    1,    1,    1,    1,    1,    1,
    2,    1,    2,    1,    1,    1,
};
final static short yydefred[] = {                         0,
   13,  104,  105,  106,    0,    0,    0,    0,    4,    0,
    0,    0,    0,    0,    1,    3,    0,    0,   11,    0,
    6,    7,    0,    0,    0,   29,    0,    0,   31,    0,
    0,    0,    0,    0,    5,   12,   88,    0,   81,    0,
    0,   80,    0,  100,  102,    0,    0,    0,    0,    0,
   99,    0,    0,    0,    0,   95,   97,   98,    0,    0,
   28,   30,   32,   33,   34,   35,   16,    0,    0,   84,
    0,   86,    0,    0,    0,    0,    0,    0,  101,  103,
    0,    0,    0,    0,    0,    0,   66,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   41,
    0,   42,    0,   85,   82,   52,    0,   27,   51,    0,
   60,   64,    0,   62,    0,    0,   57,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   93,   94,    0,   44,   47,    0,   46,   40,    0,   50,
    0,    0,    0,   54,    0,   48,   43,   45,    0,   59,
   61,   63,    0,   55,    0,   53,    0,    0,    0,    0,
    0,    0,    0,    0,   24,    0,    0,    0,    0,    0,
    0,    0,   23,    0,    0,    0,    0,    0,   21,   20,
   25,    0,   22,   19,    0,    0,   17,    0,    0,   14,
    0,   15,
};
final static short yydgoto[] = {                          6,
    7,   15,    8,    9,   10,   11,   12,   76,   28,  176,
  159,   49,   50,   29,   30,   31,   32,   33,  100,  136,
  137,   51,   52,   53,   54,   42,   55,   56,   57,   58,
};
final static short yysindex[] = {                      -174,
    0,    0,    0,    0, -221,    0, -261, -174,    0, -246,
  -28,   16,   -8,  535,    0,    0, -210,    5,    0, -168,
    0,    0, -160,  -40,  320,    0,  168,  544,    0,   44,
   46,   62,   65,  -33,    0,    0,    0,   36,    0,   85,
 -243,    0,   91,    0,    0, -261,   36, -124, -214,  -34,
    0,  -24, -123,  390,  126,    0,    0,    0, -127, -241,
    0,    0,    0,    0,    0,    0,    0, -106,  -34,    0,
  124,    0,  128, -129,  -87,  133,  -76,   -2,    0,    0,
  -81,  -74,   36,   36,   36,   36,    0,   36,   36,   36,
   36,   36,   36,   36,   36,   36,   36,   36,  514,    0,
 -127,    0,  147,    0,    0,    0,  150,    0,    0, -261,
    0,    0, -261,    0,  -72, -261,    0,  126,  126,  126,
 -123,  390,  -34,  -34,  -34,  -34,  -34,  -34,  -34,  -34,
    0,    0,  152,    0,    0,  523,    0,    0, -174,    0,
  -62,  -60, -249,    0,  -56,    0,    0,    0,  -61,    0,
    0,    0,  -54,    0, -199,    0,  500,  255, -198,  162,
  378,  180,  207,  279,    0,  -36,  -26,  -25,   36,  230,
  213,  223,    0,  -49,  224,  251,  -34,   36,    0,    0,
    0,  247,    0,    0,  248,  277,    0,   42,  261,    0,
   53,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,   61,    0,    0,
    0, -133, -110,    0,    0,    0,    0,  -86,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  234,
  288,  354,  505,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -39,    0,    0,    0,    0,    0,    0,   79,
    0,    0,  335,  298,  -15,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  212,    0,
    0,    0,  325,    0,    0,    0,    0,  272,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  346,    0,    0,    9,   33,   57,
  473,  315,  101,  123,  145,  375,  397,  419,  441,  463,
    0,    0,  532,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  490,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  299,    0,    0,    0,
    0,  283,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
  204,  351,    0,  342,  -11,   94,    0,  -13,  -73,  183,
    0,   -3,   18,  -14,    0,    0,    0,    0,  -59,    0,
  226,    0,  317,  286,  287,    0,   95,  112,    0,    0,
};
final static int YYTABLESIZE=822;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         41,
  102,   96,   96,   96,   96,   96,   68,   96,   84,  183,
   85,    1,   71,   62,   14,   20,   87,  174,   96,   96,
   96,   96,   96,   60,  152,   91,   14,   91,   91,   91,
   19,   75,  173,   17,   99,   20,    1,  101,  112,   72,
   73,  138,   91,   91,   91,   91,   91,   34,   20,   92,
   22,   92,   92,   92,  103,   69,   75,   81,   23,   23,
  107,   14,   75,   35,   24,   24,   92,   92,   92,   92,
   92,   25,   25,   89,   21,   89,   89,   89,   27,   27,
   48,  158,  157,    1,  135,  164,    2,    3,    4,   36,
   89,   89,   89,   89,   89,   37,   38,   90,   13,   90,
   90,   90,   63,   18,   64,    5,  123,  124,  125,  126,
  127,  128,  129,  130,   90,   90,   90,   90,   90,   77,
   65,  135,   77,   66,    9,   70,  106,    9,    9,    9,
   74,    2,    3,    4,   79,   80,   77,   77,   77,   77,
   77,   73,    9,   62,   73,   88,    9,   10,   99,   62,
   10,   10,   10,  162,    2,    3,    4,  167,   73,   73,
   73,   73,   73,   74,  104,   10,   74,   97,  105,   10,
  108,    8,   98,  109,    8,    8,    8,  118,  119,  120,
   74,   74,   74,   74,   74,   75,  177,  139,   75,    8,
  140,  113,  114,    8,   14,  177,  110,  111,  116,  117,
  143,  144,   75,   75,   75,   75,   75,   47,  131,  132,
  146,  150,   48,  151,  155,   39,   96,  154,   96,  156,
  165,   83,   67,  168,   96,   96,   96,   96,   96,   96,
   96,   96,   96,  181,  182,   96,   96,   96,   96,   96,
   91,   96,   91,   40,   96,   86,  169,  171,   91,   91,
   91,   91,   91,   91,   91,   91,   91,  172,  175,   91,
   91,   91,   91,   91,   92,   91,   92,   86,   91,  178,
   87,  179,   92,   92,   92,   92,   92,   92,   92,   92,
   92,  180,  184,   92,   92,   92,   92,   92,   89,   92,
   89,  185,   92,   43,   44,   45,   89,   89,   89,   89,
   89,   89,   89,   89,   89,  187,  188,   89,   89,   89,
   89,   89,   90,   89,   90,   65,   89,  189,  190,  191,
   90,   90,   90,   90,   90,   90,   90,   90,   90,  192,
   65,   90,   90,   90,   90,   90,    2,   90,   70,   26,
   90,   70,  149,   77,   77,   77,   77,   77,   77,   16,
   77,    2,    3,    4,   77,   69,   70,   77,   69,   47,
  186,  148,   77,   78,   48,   73,   73,   73,   73,   73,
   73,  121,   73,   69,  122,   68,   73,    0,   68,   73,
    0,    0,    0,   83,   73,    0,    0,   74,   74,   74,
   74,   74,   74,   68,   74,    0,   77,    0,   74,   82,
    0,   74,    0,    0,   58,    0,   74,    0,    0,   75,
   75,   75,   75,   75,   75,   76,   75,   47,   76,    0,
   75,  166,   48,   75,    0,   43,   44,   45,   75,    0,
    0,  115,   76,   76,   76,   76,   76,   79,    0,    0,
   79,    0,    0,    0,    0,    0,   59,   93,    0,   95,
   96,   94,    0,    0,   79,   79,   79,   79,   79,   71,
  141,    0,   71,  142,    0,    0,  145,    0,    0,   87,
    0,    0,    0,    0,    0,   87,   71,   71,   71,   71,
   71,   72,   87,    0,   72,    0,   87,    0,   87,   87,
    0,   36,   87,  153,    0,    0,    0,   36,   72,   72,
   72,   72,   72,   78,   36,    0,   78,    0,   36,    0,
   36,   36,   23,   67,   36,    0,   67,    0,   24,    0,
   78,   78,   78,   78,   78,   25,    0,    0,    0,    0,
    0,   67,   27,    0,    0,  163,   23,    0,    0,   47,
   18,    0,   24,   65,   48,   37,   18,   65,   56,   25,
   65,   37,    0,   18,    0,   65,   27,  161,   37,  170,
   18,    0,   37,    0,   37,   37,   70,   70,   37,   70,
    0,    0,    0,   70,    0,    0,   70,   43,   44,   45,
    0,   70,   83,   69,   69,    0,   69,    0,   83,    0,
   69,   46,    0,   69,    0,   83,    0,    0,   69,   83,
    0,   83,   83,   58,   68,   83,   68,    0,    0,   58,
   68,   38,    0,   68,    0,    0,   58,   38,   68,    0,
   58,    0,   58,   58,   38,    0,   58,    0,   38,    0,
   38,   38,    0,    0,   38,   43,   44,   45,    0,   76,
   76,   76,   76,   76,   76,    0,   76,    0,    0,    0,
   76,    0,    0,   76,   89,   90,   91,   92,   76,    0,
    0,   79,   79,   79,   79,   79,   79,    0,   79,    0,
    0,    0,   79,    0,    0,   79,    0,    0,    0,    0,
   79,    0,    0,   71,   71,   71,   71,   71,   71,    0,
   71,    0,    0,    0,   71,    0,    0,   71,    0,    0,
    0,    0,   71,    0,    0,   72,   72,   72,   72,   72,
   72,    0,   72,    0,    0,    0,   72,    0,    0,   72,
    0,    0,    0,    0,   72,    0,    0,   78,   78,   78,
   78,   78,   78,    0,   78,    0,    0,    0,   78,    0,
    0,   78,   67,    0,   67,    0,   78,   56,   67,    0,
    0,   67,    0,   56,    0,  160,   67,   43,   44,   45,
   56,    0,   39,    0,   56,    0,   56,   56,   39,    0,
   56,   23,    0,    0,    0,   39,    0,   24,    0,   39,
   23,   39,   39,    0,   25,   39,   24,    0,  133,   49,
  134,   27,   23,   25,    0,   49,    0,  133,   24,  147,
   27,   23,   49,    0,    0,   25,   49,   24,   49,   49,
    0,   26,   27,    0,   25,    0,    0,    0,    0,    0,
   61,   27,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   60,   41,   42,   43,   44,   45,   40,   47,   43,   59,
   45,  258,  256,   28,  276,   44,   41,   44,   58,   59,
   60,   61,   62,   27,  274,   41,  276,   43,   44,   45,
   59,   43,   59,  280,  276,   44,  258,  279,   41,  283,
  284,  101,   58,   59,   60,   61,   62,  258,   44,   41,
   59,   43,   44,   45,   68,   38,   68,  272,  258,  258,
   74,  276,   74,   59,  264,  264,   58,   59,   60,   61,
   62,  271,  271,   41,   59,   43,   44,   45,  278,  278,
   45,  155,  282,  258,   99,  159,  261,  262,  263,  258,
   58,   59,   60,   61,   62,  256,  257,   41,    5,   43,
   44,   45,   59,   10,   59,  280,   89,   90,   91,   92,
   93,   94,   95,   96,   58,   59,   60,   61,   62,   41,
   59,  136,   44,   59,  258,   41,  256,  261,  262,  263,
   40,  261,  262,  263,  259,  260,   58,   59,   60,   61,
   62,   41,  276,  158,   44,  269,  280,  258,  276,  164,
  261,  262,  263,  157,  261,  262,  263,  161,   58,   59,
   60,   61,   62,   41,   41,  276,   44,   42,   41,  280,
  258,  258,   47,   41,  261,  262,  263,   83,   84,   85,
   58,   59,   60,   61,   62,   41,  169,   41,   44,  276,
   41,  273,  274,  280,  276,  178,  273,  274,  273,  274,
  273,  274,   58,   59,   60,   61,   62,   40,   97,   98,
   59,  274,   45,  274,  276,  256,  256,  274,  258,  274,
   59,  256,  256,   44,  264,  265,  266,  267,  268,  269,
  270,  271,  272,  283,  284,  275,  276,  277,  278,  279,
  256,  281,  258,  284,  284,  270,   40,  284,  264,  265,
  266,  267,  268,  269,  270,  271,  272,  284,  284,  275,
  276,  277,  278,  279,  256,  281,  258,  270,  284,   40,
   59,   59,  264,  265,  266,  267,  268,  269,  270,  271,
  272,   59,   59,  275,  276,  277,  278,  279,  256,  281,
  258,   41,  284,  258,  259,  260,  264,  265,  266,  267,
  268,  269,  270,  271,  272,   59,   59,  275,  276,  277,
  278,  279,  256,  281,  258,   44,  284,   41,  277,   59,
  264,  265,  266,  267,  268,  269,  270,  271,  272,  277,
   59,  275,  276,  277,  278,  279,  276,  281,   41,   41,
  284,   44,  139,  265,  266,  267,  268,  269,  270,    8,
  272,  261,  262,  263,  276,   41,   59,  279,   44,   40,
  178,  136,  284,   47,   45,  265,  266,  267,  268,  269,
  270,   86,  272,   59,   88,   41,  276,   -1,   44,  279,
   -1,   -1,   -1,   59,  284,   -1,   -1,  265,  266,  267,
  268,  269,  270,   59,  272,   -1,   46,   -1,  276,   49,
   -1,  279,   -1,   -1,   59,   -1,  284,   -1,   -1,  265,
  266,  267,  268,  269,  270,   41,  272,   40,   44,   -1,
  276,   44,   45,  279,   -1,  258,  259,  260,  284,   -1,
   -1,   81,   58,   59,   60,   61,   62,   41,   -1,   -1,
   44,   -1,   -1,   -1,   -1,   -1,  279,   58,   -1,   60,
   61,   62,   -1,   -1,   58,   59,   60,   61,   62,   41,
  110,   -1,   44,  113,   -1,   -1,  116,   -1,   -1,  258,
   -1,   -1,   -1,   -1,   -1,  264,   58,   59,   60,   61,
   62,   41,  271,   -1,   44,   -1,  275,   -1,  277,  278,
   -1,  258,  281,  143,   -1,   -1,   -1,  264,   58,   59,
   60,   61,   62,   41,  271,   -1,   44,   -1,  275,   -1,
  277,  278,  258,   41,  281,   -1,   44,   -1,  264,   -1,
   58,   59,   60,   61,   62,  271,   -1,   -1,   -1,   -1,
   -1,   59,  278,   -1,   -1,  281,  258,   -1,   -1,   40,
  258,   -1,  264,  272,   45,  258,  264,  276,   59,  271,
  279,  264,   -1,  271,   -1,  284,  278,   58,  271,  281,
  278,   -1,  275,   -1,  277,  278,  269,  270,  281,  272,
   -1,   -1,   -1,  276,   -1,   -1,  279,  258,  259,  260,
   -1,  284,  258,  269,  270,   -1,  272,   -1,  264,   -1,
  276,  272,   -1,  279,   -1,  271,   -1,   -1,  284,  275,
   -1,  277,  278,  258,  270,  281,  272,   -1,   -1,  264,
  276,  258,   -1,  279,   -1,   -1,  271,  264,  284,   -1,
  275,   -1,  277,  278,  271,   -1,  281,   -1,  275,   -1,
  277,  278,   -1,   -1,  281,  258,  259,  260,   -1,  265,
  266,  267,  268,  269,  270,   -1,  272,   -1,   -1,   -1,
  276,   -1,   -1,  279,  265,  266,  267,  268,  284,   -1,
   -1,  265,  266,  267,  268,  269,  270,   -1,  272,   -1,
   -1,   -1,  276,   -1,   -1,  279,   -1,   -1,   -1,   -1,
  284,   -1,   -1,  265,  266,  267,  268,  269,  270,   -1,
  272,   -1,   -1,   -1,  276,   -1,   -1,  279,   -1,   -1,
   -1,   -1,  284,   -1,   -1,  265,  266,  267,  268,  269,
  270,   -1,  272,   -1,   -1,   -1,  276,   -1,   -1,  279,
   -1,   -1,   -1,   -1,  284,   -1,   -1,  265,  266,  267,
  268,  269,  270,   -1,  272,   -1,   -1,   -1,  276,   -1,
   -1,  279,  270,   -1,  272,   -1,  284,  258,  276,   -1,
   -1,  279,   -1,  264,   -1,  256,  284,  258,  259,  260,
  271,   -1,  258,   -1,  275,   -1,  277,  278,  264,   -1,
  281,  258,   -1,   -1,   -1,  271,   -1,  264,   -1,  275,
  258,  277,  278,   -1,  271,  281,  264,   -1,  275,  258,
  277,  278,  258,  271,   -1,  264,   -1,  275,  264,  277,
  278,  258,  271,   -1,   -1,  271,  275,  264,  277,  278,
   -1,  277,  278,   -1,  271,   -1,   -1,   -1,   -1,   -1,
  277,  278,
};
}
final static short YYFINAL=6;
final static short YYMAXTOKEN=284;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,"':'","';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
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
"RETURN","PRE","WRONG_STRING","CTE_STRING",
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
"funcion : tipo FUNC ID error",
"pre_condicion : PRE ':' condicion ',' CTE_STRING ';'",
"pre_condicion : PRE ':' condicion ',' CTE_STRING",
"pre_condicion : PRE condicion ',' CTE_STRING ';'",
"pre_condicion : PRE ':' condicion CTE_STRING ';'",
"pre_condicion : PRE ':' ',' CTE_STRING ';'",
"pre_condicion : PRE ':' condicion ',' ';'",
"pre_condicion : PRE ':' condicion ';'",
"pre_condicion : PRE error ';'",
"pre_condicion : PRE ':' condicion ',' WRONG_STRING",
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
"condicion_simple : condicion_simple '=' expresion",
"condicion_simple : condicion_simple ':' expresion",
"sentencia_print : PRINT to_print",
"sentencia_print : PRINT error",
"to_print : '(' CTE_STRING ')'",
"to_print : '(' CTE_STRING",
"to_print : CTE_STRING ')'",
"to_print : '(' error ')'",
"to_print : '(' WRONG_STRING",
"asignacion : ID ASIG expresion",
"asignacion : ID error",
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
"tipo : STRING",
};

//#line 157 "gramatica.y"
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
//#line 592 "Parser.java"
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
{su.addError("La funcion es " + val_peek(1).sval + " incorrecta sintacticamente");}
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
case 25:
//#line 42 "gramatica.y"
{su.addError("El STRING es incorrecto");}
break;
case 32:
//#line 56 "gramatica.y"
{su.addCodeStructure("ASIGNACION");}
break;
case 33:
//#line 57 "gramatica.y"
{su.addCodeStructure("SENTENCIA IF");}
break;
case 34:
//#line 58 "gramatica.y"
{su.addCodeStructure("SENTENCIA PRINT");}
break;
case 35:
//#line 59 "gramatica.y"
{su.addCodeStructure("WHILE");}
break;
case 36:
//#line 60 "gramatica.y"
{su.addError(" falta ; luego de la sentencia");}
break;
case 37:
//#line 61 "gramatica.y"
{su.addError(" falta ; luego de la sentencia");}
break;
case 38:
//#line 62 "gramatica.y"
{su.addError(" falta ; luego de la sentencia");}
break;
case 39:
//#line 63 "gramatica.y"
{su.addError(" falta ; luego de la sentencia");}
break;
case 41:
//#line 67 "gramatica.y"
{su.addError(" falta la condicion de corte del WHILE");}
break;
case 42:
//#line 68 "gramatica.y"
{su.addError(" falta DO en la sentencia WHILE");}
break;
case 49:
//#line 78 "gramatica.y"
{su.addError(" falta ; luego de la sentencia");}
break;
case 50:
//#line 81 "gramatica.y"
{su.addCodeStructure("LLamado a funcion " + val_peek(3).sval);}
break;
case 51:
//#line 82 "gramatica.y"
{su.addError("falta parentesis de apertura para el llamado a la funcion " + val_peek(2).sval);}
break;
case 52:
//#line 83 "gramatica.y"
{su.addError("la llamada a la funcion " + val_peek(2).sval + " no es correcta sintacticamente");}
break;
case 55:
//#line 88 "gramatica.y"
{su.addError(" falta THEN en la sentencia IF");}
break;
case 56:
//#line 89 "gramatica.y"
{su.addError(" falta ENDIF en la sentencia IF");}
break;
case 57:
//#line 90 "gramatica.y"
{su.addError(" falta THEN en la sentencia IF");}
break;
case 58:
//#line 91 "gramatica.y"
{su.addError(" falta ENDIF en la sentencia IF");}
break;
case 59:
//#line 92 "gramatica.y"
{su.addError(" falta la condicion en la sentencia IF");}
break;
case 60:
//#line 93 "gramatica.y"
{su.addError(" falta la condicion en la sentencia IF");}
break;
case 61:
//#line 94 "gramatica.y"
{su.addError(" falta bloque de sentencias luego del THEN");}
break;
case 62:
//#line 95 "gramatica.y"
{su.addError(" falta bloque de sentencias luego del THEN");}
break;
case 63:
//#line 96 "gramatica.y"
{su.addError(" falta bloque de sentencias luego del ELSE");}
break;
case 65:
//#line 99 "gramatica.y"
{su.addError(" falta parentesis de cierre en la condicion");}
break;
case 66:
//#line 100 "gramatica.y"
{su.addError(" falta parentesis de apertura en la condicion");}
break;
case 67:
//#line 102 "gramatica.y"
{su.addCodeStructure("Se detecta una condicion AND");}
break;
case 69:
//#line 105 "gramatica.y"
{su.addCodeStructure("Se detecta una condicion OR");}
break;
case 71:
//#line 108 "gramatica.y"
{su.addCodeStructure("Se detecta una condicion >");}
break;
case 72:
//#line 109 "gramatica.y"
{su.addCodeStructure("Se detecta una condicion <");}
break;
case 73:
//#line 110 "gramatica.y"
{su.addCodeStructure("Se detecta una condicion >=");}
break;
case 74:
//#line 111 "gramatica.y"
{su.addCodeStructure("Se detecta una condicion <=");}
break;
case 75:
//#line 112 "gramatica.y"
{su.addCodeStructure("Se detecta una condicion ==");}
break;
case 76:
//#line 113 "gramatica.y"
{su.addCodeStructure("Se detecta una condicion <>");}
break;
case 78:
//#line 115 "gramatica.y"
{su.addError("No se reconoce el comparador");}
break;
case 79:
//#line 116 "gramatica.y"
{su.addError("No se reconoce el comparador");}
break;
case 81:
//#line 120 "gramatica.y"
{su.addError("la sentencia PRINT no es correcta sintacticamente");}
break;
case 83:
//#line 123 "gramatica.y"
{su.addError(" falta parentesis de cierre para la sentencia PRINT");}
break;
case 84:
//#line 124 "gramatica.y"
{su.addError(" falta parentesis de apertura para la sentencia PRINT");}
break;
case 85:
//#line 125 "gramatica.y"
{su.addError(" STRING mal escrito");}
break;
case 86:
//#line 126 "gramatica.y"
{su.addError("STRING mal escrito");}
break;
case 88:
//#line 130 "gramatica.y"
{su.addError("La asignacion no es correcta sintacticamente");}
break;
case 89:
//#line 132 "gramatica.y"
{su.addCodeStructure("Se detecta una suma");}
break;
case 90:
//#line 133 "gramatica.y"
{su.addCodeStructure("Se detecta una resta");}
break;
case 91:
//#line 134 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 92:
//#line 135 "gramatica.y"
{su.addError(" no se reconoce el operador");}
break;
case 93:
//#line 137 "gramatica.y"
{su.addCodeStructure("Se detecta una multiplicacion");}
break;
case 94:
//#line 138 "gramatica.y"
{su.addCodeStructure("Se detecta una division");}
break;
case 95:
//#line 139 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 96:
//#line 141 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 98:
//#line 143 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 100:
//#line 146 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 101:
//#line 147 "gramatica.y"
{su.addError("UINT no puede ser negativo");}
break;
case 102:
//#line 149 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 103:
//#line 150 "gramatica.y"
{yyval.sval = val_peek(0).sval; setToNegative(val_peek(0).sval);}
break;
case 104:
//#line 152 "gramatica.y"
{yyval.sval = "UINT";}
break;
case 105:
//#line 153 "gramatica.y"
{yyval.sval = "DOUBLE";}
break;
case 106:
//#line 154 "gramatica.y"
{yyval.sval = "STRING";}
break;
//#line 1041 "Parser.java"
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
