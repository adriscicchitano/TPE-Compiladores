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






//#line 2 "test_gramatica.y"
package StateTransitionMatrix;
import Text.Text;
import Utils.utils;
import java.io.IOException;
import Structures.SymbolTableValue;
import java.util.ArrayList;
import java.util.List;
import IntermediateCode.CurrentScope;
import IntermediateCode.Terceto;
//#line 27 "Parser.java"




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
    3,    0,    1,    2,    5,    5,    6,    6,    6,    6,
    6,    6,    8,    8,   10,   12,   12,   12,   12,    9,
   15,    9,   14,   14,   13,   11,    4,    4,   18,   18,
   18,   18,   25,   20,   20,   20,   23,   23,   23,   24,
   24,   24,   22,   22,   26,   26,   26,   27,   27,   28,
   28,   29,   16,   16,   16,   31,   31,   32,   32,   33,
   33,   33,   33,   33,   33,   33,   33,   33,   21,   34,
   34,   34,   34,   19,   17,   17,   17,   35,   35,   35,
   30,   30,   30,   30,   36,   36,   37,   37,    7,    7,
};
final static short yylen[] = {                            2,
    0,    7,    1,    1,    2,    1,    3,    1,    3,    2,
    2,    2,    3,    1,    6,    9,   10,    8,    9,    3,
    0,    3,    6,    3,    1,    2,    2,    1,    1,    1,
    1,    1,    0,    7,    5,    3,    5,    2,    3,    5,
    2,    3,    3,    3,    5,    2,    3,    2,    1,    1,
    2,    4,    3,    2,    2,    3,    1,    3,    1,    3,
    3,    3,    3,    3,    3,    1,    3,    3,    3,    3,
    2,    2,    2,    4,    3,    3,    1,    3,    3,    1,
    1,    1,    1,    1,    1,    2,    1,    2,    1,    1,
};
final static short yydefred[] = {                         0,
    3,    0,    0,   14,   89,   90,    0,    1,    0,    6,
    0,    0,    8,    0,    0,    0,    5,    0,    0,   12,
    0,    0,    0,    9,    0,    0,    7,   13,    0,   20,
   22,    0,    0,    0,    0,    0,   28,   29,   30,   31,
   32,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   85,   87,    0,    0,    0,    0,   84,   80,    0,
    0,    0,    0,   82,   83,    0,    0,    0,   27,    0,
    0,    0,    0,    0,    0,    0,   72,   73,    0,   69,
   36,    0,    0,   86,   88,    0,    0,    0,    0,    0,
   55,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   44,    0,   43,    2,   26,   15,   24,    0,
    0,    0,   74,   70,    0,   53,    0,    0,   38,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   78,   79,    0,    0,    0,   50,   46,
    0,    0,    0,    0,   52,   39,    0,   35,    0,    0,
   47,   51,    0,   49,    0,    0,    0,    0,    0,    0,
   41,    0,    0,   48,   23,    0,    0,   37,   42,    0,
   34,   45,    0,    0,    0,   16,    0,   40,   17,
};
final static short yydgoto[] = {                          2,
    3,    8,   16,   36,    9,   10,   11,   12,   13,   14,
   71,   30,  142,   45,   23,   56,   57,   37,   38,   39,
   40,   41,   87,  150,  121,  105,  153,  140,   58,   59,
   60,   61,   62,   49,   63,   64,   65,
};
final static short yysindex[] = {                      -221,
    0,    0, -194,    0,    0,    0, -211,    0, -194,    0,
 -234,  -33,    0, -194,   10, -192,    0, -161,   19,    0,
 -157, -158, -158,    0,  -46,   69,    0,    0,  -86,    0,
    0, -133,  -40,   -7,  143,  234,    0,    0,    0,    0,
    0, -167,  -51,  -78,  -46,  -44,   85, -163,   71,   73,
  101,    0,    0,  -44, -137, -134,   72,    0,    0,  -25,
 -124,   14,   24,    0,    0,   87, -132,   94,    0, -109,
  114,  102,  149,  123,  -74,   -4,    0,    0,  127,    0,
    0,  -44,  -24,    0,    0,  174, -110,  -44,  -44,  -44,
    0,  -44,  -44,  -44,  -44,  -44,  -44,  -44,  -44,  -44,
  -44,  -44,    0,  171,    0,    0,    0,    0,    0,  132,
  -44,  137,    0,    0,  138,    0,  122,  -46,    0,  128,
  -91,   24,   24, -124,   14,   72,   72,   72,   72,   72,
   72,   72,   72,    0,    0,  142,  147,  238,    0,    0,
  -93,  161,   72,  -44,    0,    0,  250,    0,  195,  -66,
    0,    0,  226,    0,  150,  151,  170,  160,  162,  -46,
    0,  163,  164,    0,    0,  -57,  165,    0,    0,  259,
    0,    0,  183,  -30,  213,    0,  216,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  -22,    0,
    0,    0,    0,  -21,  -63,    0,    0,    0,  196,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -39,    0,    0,    0,    0,    0,   21,    0,    0,    0,
  125,    4,  -31,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  218,    0,
    0,    0,  -29,    0,    0,    0,  -11,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   -9,   -1,  126,  121,   29,   44,   52,   67,   75,
   90,   98,  113,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  243,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  203,    0,    0,    0,  219,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  271,    0,  -20,    0,  283,  260,   96,    0,    0,
    0,  280,  181,    0,    0,  -17,  325,  340,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -61,    0,   -2,
  251,  214,  215,    0,   55,    0,    0,
};
final static int YYTABLESIZE=537;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         48,
   55,   81,   81,   81,   81,   81,   73,   81,   44,   77,
   21,   77,   77,   77,   54,   91,  116,   67,   81,   81,
   81,   81,   81,    4,   75,   20,   77,   77,   77,   77,
   77,   75,   54,   75,   75,   75,    1,   55,   88,   76,
   89,   76,   76,   76,   59,   18,    4,   59,   75,   75,
   75,   75,   75,   21,  113,  110,   76,   76,   76,   76,
   76,   66,   21,    4,   66,  101,    5,    6,   24,   62,
  102,   97,   62,   99,  100,   98,  154,   27,   66,  115,
   66,   66,   66,   25,   63,    7,   62,   63,   62,   62,
   62,  164,   64,    5,    6,   64,   26,  147,  134,  135,
   28,   63,   15,   63,   63,   63,   19,   65,   42,   64,
   65,   64,   64,   64,   88,   68,   89,   29,   68,   78,
   79,   84,   85,   46,   65,   77,   65,   65,   65,   80,
   60,   81,   68,   60,   68,   68,   68,   86,   61,  170,
   82,   61,  122,  123,   92,  103,  104,   60,  107,   60,
   60,   60,  106,   67,  108,   61,   67,   61,   61,   61,
  109,   58,  111,  120,   58,   57,   56,  114,   57,   56,
   67,   32,   67,   67,   67,  141,  144,   33,  145,   32,
  146,  149,   54,   32,   34,   33,  148,   55,   54,   33,
  155,   35,   34,   55,   11,   43,   34,   11,   11,   35,
  151,  156,   74,   35,   72,  152,  112,  162,  165,  166,
  167,   32,   11,   51,   52,   53,   11,   33,  168,  173,
  169,  171,  172,  174,   34,   81,   81,   81,   81,   81,
   81,   35,   81,   77,   77,   77,   77,   77,   77,   81,
   77,  176,   54,   47,   90,   90,  177,   77,   50,   54,
   51,   52,   53,    4,   21,   75,   75,   75,   75,   75,
   75,   33,   75,   76,   76,   76,   76,   76,   76,   75,
   76,  178,   59,   59,  179,   59,   71,   76,   93,   94,
   95,   96,   59,   25,   22,   66,   66,   66,   66,   66,
   66,   17,   66,   62,   62,   62,   62,   62,   62,   66,
   62,   70,   31,  124,   83,    0,  125,   62,   63,   63,
   63,   63,   63,   63,    0,   63,   64,   64,   64,   64,
   64,   64,   63,   64,  157,    0,    0,    0,    0,    0,
   64,   65,   65,   65,   65,   65,   65,    0,   65,   68,
   68,   68,   68,   68,   68,   65,   68,    0,    0,    0,
    0,    0,    0,   68,   60,   60,   60,   60,   60,   60,
    0,   60,   61,   61,   61,   61,   61,   61,   60,   61,
   76,    0,    0,    0,    0,   69,   61,   67,   67,   67,
   67,   67,   67,   69,   67,    0,    0,    0,    0,   58,
   58,   67,   58,    0,   57,   56,   57,   56,   66,   58,
   51,   52,   53,   57,   56,    0,   51,   52,   53,    0,
    0,    0,    0,    0,   69,    0,    0,  126,  127,  128,
  129,  130,  131,  132,  133,  119,  136,    0,   32,  117,
    0,   32,    0,    0,   33,  143,    0,   33,    0,    0,
    0,   34,    0,  139,   34,  137,  138,    0,   35,  118,
  159,   35,   32,   10,    0,    0,   10,   10,   33,    0,
   18,    0,    0,   18,   18,   34,    0,    0,  143,    0,
  160,   10,   35,    0,    0,   10,   19,  139,   18,   19,
   19,    0,   18,   32,    0,    0,   69,    0,  161,   33,
    0,   32,  139,    0,   19,   32,   34,   33,   19,    0,
  137,   33,  163,   35,   34,    0,    0,   32,   34,   69,
   68,   35,  137,   33,    0,   35,   32,    0,    0,    0,
   34,    0,   33,    0,    0,    0,  158,   35,    0,   34,
    0,    0,    0,    0,    0,  175,   35,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   45,   41,   42,   43,   44,   45,   58,   47,   29,   41,
   44,   43,   44,   45,   44,   41,   41,   35,   58,   59,
   60,   61,   62,  258,   45,   59,   58,   59,   60,   61,
   62,   41,   40,   43,   44,   45,  258,   45,   43,   41,
   45,   43,   44,   45,   41,  280,  258,   44,   58,   59,
   60,   61,   62,   44,   59,   73,   58,   59,   60,   61,
   62,   41,   44,  258,   44,   42,  261,  262,   59,   41,
   47,   58,   44,   60,   61,   62,  138,   59,   58,   82,
   60,   61,   62,  276,   41,  280,   58,   44,   60,   61,
   62,  153,   41,  261,  262,   44,  258,  118,  101,  102,
  258,   58,    7,   60,   61,   62,   11,   41,   40,   58,
   44,   60,   61,   62,   43,   41,   45,  276,   44,  283,
  284,  259,  260,  257,   58,   41,   60,   61,   62,   59,
   41,   59,   58,   44,   60,   61,   62,  272,   41,  160,
   40,   44,   88,   89,  269,   59,  279,   58,  258,   60,
   61,   62,   59,   41,   41,   58,   44,   60,   61,   62,
   59,   41,   40,  274,   44,   41,   41,   41,   44,   44,
   58,  258,   60,   61,   62,   44,   40,  264,   41,  258,
   59,  273,   40,  258,  271,  264,   59,   45,   40,  264,
  284,  278,  271,   45,  258,  282,  271,  261,  262,  278,
   59,   41,  281,  278,  256,   59,  281,  274,   59,   59,
   41,  258,  276,  258,  259,  260,  280,  264,   59,  277,
   59,   59,   59,   59,  271,  265,  266,  267,  268,  269,
  270,  278,  272,  265,  266,  267,  268,  269,  270,  279,
  272,   59,  272,  284,  270,  270,  277,  279,  256,  279,
  258,  259,  260,  276,  276,  265,  266,  267,  268,  269,
  270,  273,  272,  265,  266,  267,  268,  269,  270,  279,
  272,   59,  269,  270,   59,  272,   59,  279,  265,  266,
  267,  268,  279,   41,   14,  265,  266,  267,  268,  269,
  270,    9,  272,  265,  266,  267,  268,  269,  270,  279,
  272,   42,   23,   90,   54,   -1,   92,  279,  265,  266,
  267,  268,  269,  270,   -1,  272,  265,  266,  267,  268,
  269,  270,  279,  272,  144,   -1,   -1,   -1,   -1,   -1,
  279,  265,  266,  267,  268,  269,  270,   -1,  272,  265,
  266,  267,  268,  269,  270,  279,  272,   -1,   -1,   -1,
   -1,   -1,   -1,  279,  265,  266,  267,  268,  269,  270,
   -1,  272,  265,  266,  267,  268,  269,  270,  279,  272,
   46,   -1,   -1,   -1,   -1,   36,  279,  265,  266,  267,
  268,  269,  270,   44,  272,   -1,   -1,   -1,   -1,  269,
  270,  279,  272,   -1,  270,  270,  272,  272,  256,  279,
  258,  259,  260,  279,  279,   -1,  258,  259,  260,   -1,
   -1,   -1,   -1,   -1,   75,   -1,   -1,   93,   94,   95,
   96,   97,   98,   99,  100,   86,  256,   -1,  258,  256,
   -1,  258,   -1,   -1,  264,  111,   -1,  264,   -1,   -1,
   -1,  271,   -1,  104,  271,  275,  276,   -1,  278,  276,
  256,  278,  258,  258,   -1,   -1,  261,  262,  264,   -1,
  258,   -1,   -1,  261,  262,  271,   -1,   -1,  144,   -1,
  276,  276,  278,   -1,   -1,  280,  258,  138,  276,  261,
  262,   -1,  280,  258,   -1,   -1,  147,   -1,  149,  264,
   -1,  258,  153,   -1,  276,  258,  271,  264,  280,   -1,
  275,  264,  277,  278,  271,   -1,   -1,  258,  271,  170,
  277,  278,  275,  264,   -1,  278,  258,   -1,   -1,   -1,
  271,   -1,  264,   -1,   -1,   -1,  277,  278,   -1,  271,
   -1,   -1,   -1,   -1,   -1,  277,  278,
};
}
final static short YYFINAL=2;
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
"$$1 :",
"programa : nombre_programa bloque_sentencias_declarativas $$1 BEGIN sentencias_ejecutables END ';'",
"nombre_programa : ID",
"bloque_sentencias_declarativas : sentencias_declarativas",
"sentencias_declarativas : sentencias_declarativas sentencia_declarativa",
"sentencias_declarativas : sentencia_declarativa",
"sentencia_declarativa : tipo lista_de_variables ';'",
"sentencia_declarativa : funcion",
"sentencia_declarativa : FUNC lista_de_variables ';'",
"sentencia_declarativa : tipo lista_de_variables",
"sentencia_declarativa : FUNC lista_de_variables",
"sentencia_declarativa : lista_de_variables ';'",
"lista_de_variables : lista_de_variables ',' ID",
"lista_de_variables : ID",
"header_funcion : tipo FUNC ID '(' parametro ')'",
"cuerpo_funcion : BEGIN sentencias_ejecutables RETURN '(' retorno ')' ';' END ';'",
"cuerpo_funcion : BEGIN pre_condicion sentencias_ejecutables RETURN '(' retorno ')' ';' END ';'",
"cuerpo_funcion : BEGIN sentencias_ejecutables RETURN '(' retorno ')' ';' END",
"cuerpo_funcion : BEGIN pre_condicion sentencias_ejecutables RETURN '(' retorno ')' ';' END",
"funcion : header_funcion bloque_sentencias_declarativas cuerpo_funcion",
"$$2 :",
"funcion : header_funcion $$2 cuerpo_funcion",
"pre_condicion : PRE ':' condicion ',' CTE_STRING ';'",
"pre_condicion : PRE error ';'",
"retorno : expresion",
"parametro : tipo ID",
"sentencias_ejecutables : sentencias_ejecutables sentencia_ejecutable",
"sentencias_ejecutables : sentencia_ejecutable",
"sentencia_ejecutable : asignacion",
"sentencia_ejecutable : clausula_seleccion",
"sentencia_ejecutable : sentencia_print",
"sentencia_ejecutable : while",
"$$3 :",
"clausula_seleccion : IF condicion bloque_then $$3 bloque_else ENDIF ';'",
"clausula_seleccion : IF condicion bloque_then ENDIF ';'",
"clausula_seleccion : IF error ';'",
"bloque_then : THEN BEGIN sentencias_ejecutables END ';'",
"bloque_then : THEN sentencia_ejecutable",
"bloque_then : THEN error ';'",
"bloque_else : ELSE BEGIN sentencias_ejecutables END ';'",
"bloque_else : ELSE sentencia_ejecutable",
"bloque_else : ELSE error ';'",
"while : WHILE condicion bloque_while",
"while : WHILE error ';'",
"bloque_while : DO BEGIN sentencias_ejecutables_while END ';'",
"bloque_while : DO sentencia_ejecutable_while",
"bloque_while : DO error ';'",
"sentencias_ejecutables_while : sentencias_ejecutables_while sentencia_ejecutable_while",
"sentencias_ejecutables_while : sentencia_ejecutable_while",
"sentencia_ejecutable_while : sentencia_ejecutable",
"sentencia_ejecutable_while : BREAK ';'",
"llamado_funcion : ID '(' factor ')'",
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
"sentencia_print : PRINT to_print ';'",
"to_print : '(' CTE_STRING ')'",
"to_print : '(' CTE_STRING",
"to_print : CTE_STRING ')'",
"to_print : '(' WRONG_STRING",
"asignacion : ID ASIG expresion ';'",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
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

//#line 497 "test_gramatica.y"
  private Text text;
  private LexicAnalyzer lexicAnalyzer;
  private StructureUtilities su;
  private List<String> idList;
  public List<Terceto> tercetos = new ArrayList<>();
  private List<Terceto> tercetosjump = new ArrayList<>();
  private Terceto t; 
  private Terceto t2;
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
  
  private List<Terceto> getTercetos(String operator, String leftValue, String rightValue, boolean leftIsTerceto, boolean rightIsTerceto, boolean compositeComparison){
    List<Terceto> aux = new ArrayList<>();
	int amtTercetos = this.tercetos.size();

	boolean v1_isConverted = false;
	boolean v2_isConverted = false;
    boolean v1_isConst = (leftValue.charAt(0) == '-') ? Character.isDigit(leftValue.charAt(1)) || leftValue.charAt(1) == '.' : Character.isDigit(leftValue.charAt(0)) || leftValue.charAt(0) == '.'; 
    boolean v2_isConst = (rightValue.charAt(0) == '-') ? Character.isDigit(rightValue.charAt(1)) || rightValue.charAt(1) == '.' : Character.isDigit(rightValue.charAt(0)) || rightValue.charAt(0) == '.'; 
    // con leftIsTerceto == true leftValue debe ser interpretado como el indice de la lista de tercetos
	
	String correctLeftValue = (v1_isConst || leftIsTerceto) ? leftValue : su.getSymbolsTableValue(su.searchForKey(leftValue, CurrentScope.getScope())).getCallable() != null ? su.getSymbolsTableValue(su.searchForKey(leftValue, CurrentScope.getScope())).getCallable() : su.searchForKey(leftValue, CurrentScope.getScope());
	String correctRightValue = (v2_isConst || rightIsTerceto) ? rightValue : su.getSymbolsTableValue(su.searchForKey(rightValue, CurrentScope.getScope())).getCallable() != null ? su.getSymbolsTableValue(su.searchForKey(rightValue, CurrentScope.getScope())).getCallable() : su.searchForKey(rightValue, CurrentScope.getScope());
	
	if(correctLeftValue == null){
		su.addError("La variable " + leftValue + " no fue definida","Semántica");
        //retorno lista vacia
		return new ArrayList<>();
	}
    if(correctRightValue == null){
      su.addError("La variable " + rightValue + " no fue definida","Semántica");
      //retorno lista vacia
      return new ArrayList<>();
    }
	
    String type1 =  leftIsTerceto ? this.tercetos.get(Integer.parseInt(leftValue)-1).getType().toUpperCase() :
                    su.getSymbolsTableValue(correctLeftValue).getType();
    String type2 =  rightIsTerceto ? this.tercetos.get(Integer.parseInt(rightValue)-1).getType().toUpperCase() :
                    su.getSymbolsTableValue(correctRightValue).getType();

    String v1 = leftIsTerceto ? "[" + leftValue + "]" : v1_isConst ? leftValue : correctLeftValue;
    String v2 = rightIsTerceto ? "[" + rightValue + "]" : v2_isConst ? rightValue : correctRightValue;
    if(!type1.equals(type2) && (type1.contains("DOUBLE") || type2.contains("DOUBLE"))){
      //Si son de distinto tipo, al menos uno es DOUBLE
      Terceto t;
      if(type1.contains("UINT") && !compositeComparison){
        t = new Terceto("utod", v1, "-", "double");
        v1_isConverted = true;
        amtTercetos++;
		aux.add(t);
      }else{
        if(type2.contains("UINT") && !compositeComparison) {
          t = new Terceto("utod", v2, "-", "double");
          v2_isConverted = true;
		  amtTercetos++;
		  aux.add(t);
        }
      }
      t = new Terceto(
              operator,
              v1_isConverted ? "["+amtTercetos+"]" : v1,
              v2_isConverted ? "["+amtTercetos+"]" : v2,
              "double"
      );
      aux.add(t);
    }else {
      Terceto t;
      if(type1.contains("UINT")) {
        //este es el caso de que ambos tipos son UINT
        t = new Terceto(
                operator,
                v1,
                v2,
                "uint"
        );
      }else{
        t = new Terceto(
                operator,
                v1,
                v2,
                "double"
        );
      }
      aux.add(t);
    }

    return aux;
  }
  
  private void addAssignmentTercetos(String leftSide, String rightSide){
	
	String correctKey = su.searchForKey(leftSide,CurrentScope.getScope());
	if(correctKey == null){
		su.addError("La variable " + leftSide + " no fue definida","Semántica");
		return;
	}
	SymbolTableValue v = su.getSymbolsTableValue(correctKey);

    String type = v.getType();
    String use = v.getUse();
    if(!use.equals("VARIABLE") && !use.equals("PARAM"))
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
                correctKey,
                "["+(this.tercetos.size())+"]",
                null
            ));

        }else{
            /*hacer logica para convertir el lado derecho si es uint o simplemente asignarlo*/
            boolean isConst = (rightSide.charAt(0) == '-') ? Character.isDigit(rightSide.charAt(1)) || rightSide.charAt(1) == '.' : Character.isDigit(rightSide.charAt(0)) || rightSide.charAt(0) == '.';
            String lexeme = isConst ? rightSide : su.searchForKey(rightSide,CurrentScope.getScope());
			if(lexeme == null){
				su.addError("La variable " + rightSide + " no fue definida","Semántica");
				return;
			}
			if(su.getSymbolsTableValue(lexeme).getCallable() != null)
              lexeme = su.getSymbolsTableValue(lexeme).getCallable();
            String rightType = su.getSymbolsTableValue(lexeme).getType();
            String rightUse = su.getSymbolsTableValue(lexeme).getUse();
			if(type.equals("DOUBLE")){
                if(rightType.contains("UINT")){
                    Terceto conversion = new Terceto(
                        "utod",
                        lexeme,
                        "-",
                        null
                    );
                    this.tercetos.add(conversion);
                    conversion = new Terceto(
                        ":=",
                        correctKey,
                        "["+this.tercetos.size()+"]",
                        null
                    );
                    this.tercetos.add(conversion);
                }else{
                    this.tercetos.add(new Terceto(
                        ":=",
                        correctKey,
                        lexeme,
                        null
                    ));
                }
            }else{
                if(rightType.contains("DOUBLE"))
                    su.addError("No es posible realizaz asignaciones cuando el lado izquierdo es UINT y el derecho no lo es", "Semantica");
                else{
                    this.tercetos.add(new Terceto(
                        ":=",
                        correctKey,
                        lexeme,
                        null
                    ));
                }
            }
			//lado izquierdo de tipo FUNC
			if(type.equals("FUNC") ){
				if(rightUse.equals("FUNC")){
					v.setCallable(su.searchForKey(rightSide,CurrentScope.getScope()));
				}else{
					su.addError("No se puede asignar otra cosa que no sea una funcion cuando el lado izquierdo es de tupi FUNC", "Semantica");
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
//#line 719 "Parser.java"
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
//#line 16 "test_gramatica.y"
{
										this.tercetos.add(new Terceto("main", "-", "-", null));
									}
break;
case 2:
//#line 20 "test_gramatica.y"
{	
										showResults();
										int i = 1;
										for(Terceto t : this.tercetos) {
											System.out.println(i + "\t->\t" + t);
											i++;
									  }
									}
break;
case 3:
//#line 29 "test_gramatica.y"
{CurrentScope.addScope(val_peek(0).sval);}
break;
case 7:
//#line 37 "test_gramatica.y"
{
										su.addCodeStructure("Declaracion de variables " + val_peek(2).sval);
										su.changeSTValues(idList,val_peek(2).sval,"VARIABLE");
										su.changeSTKeys(idList,concatenateScope(idList,CurrentScope.getScope()));
										for(String var : idList){
											this.tercetos.add(new Terceto(
												"var",
												su.searchForKey(var, CurrentScope.getScope()), 
												val_peek(2).sval,
												val_peek(2).sval
											));
										}
									}
break;
case 9:
//#line 51 "test_gramatica.y"
{su.addCodeStructure("Declaracion de variables de tipo FUNC");su.changeSTValues(idList,"FUNC","VARIABLE");su.changeSTKeys(idList,concatenateScope(idList,CurrentScope.getScope()));}
break;
case 10:
//#line 52 "test_gramatica.y"
{su.addError(" falta ; luego de la sentencia", "Sintáctica");}
break;
case 11:
//#line 53 "test_gramatica.y"
{su.addError(" falta ; luego de la sentencia", "Sintáctica");}
break;
case 12:
//#line 54 "test_gramatica.y"
{su.addError(" la/las variables no tienen tipo", "Sintáctica");}
break;
case 13:
//#line 56 "test_gramatica.y"
{idList.add(val_peek(0).sval);}
break;
case 14:
//#line 57 "test_gramatica.y"
{idList = new ArrayList<>(); idList.add(val_peek(0).sval);}
break;
case 15:
//#line 61 "test_gramatica.y"
{
										yyval.sval = val_peek(3).sval;
										su.addCodeStructure("Declaracion de la funcion " + val_peek(3).sval);
										su.changeSTValues(val_peek(3).sval,val_peek(5).sval,"FUNC");
										su.changeSTKey(val_peek(3).sval,val_peek(3).sval + ":" + CurrentScope.getScope());
										CurrentScope.addScope(val_peek(3).sval);
										su.changeSTKey(val_peek(1).sval,val_peek(1).sval + ":" + CurrentScope.getScope());
										
										this.tercetos.add(new Terceto(
											"tag",
											su.searchForKey(val_peek(3).sval, CurrentScope.getScope()),
											su.searchForKey(val_peek(1).sval, CurrentScope.getScope()),
											null
										));
									}
break;
case 18:
//#line 80 "test_gramatica.y"
{su.addError(" falta ; luego del END", "Sintáctica");}
break;
case 19:
//#line 81 "test_gramatica.y"
{su.addError(" falta ; luego del END", "Sintáctica");}
break;
case 20:
//#line 84 "test_gramatica.y"
{	
										CurrentScope.deleteScope();
									}
break;
case 21:
//#line 89 "test_gramatica.y"
{
										this.tercetos.add(new Terceto(
											"tag",
											su.searchForKey(val_peek(0).sval, CurrentScope.getScope()),
											"-",
											null
										));
									}
break;
case 22:
//#line 97 "test_gramatica.y"
{	
										CurrentScope.deleteScope();
									}
break;
case 24:
//#line 103 "test_gramatica.y"
{su.addError(" Se encontro un error no considerado por la gramatica luego del PRE", "Sintáctica");}
break;
case 25:
//#line 106 "test_gramatica.y"
{
										this.tercetos.addAll(this.getTercetos(
											"ret",
											leftTercetoIndex != null && (!leftTercetoIndex.equals("0")) ? leftTercetoIndex : val_peek(0).sval,
											leftTercetoIndex != null && (!leftTercetoIndex.equals("0")) ? leftTercetoIndex : val_peek(0).sval,
											leftTercetoIndex != null && (!leftTercetoIndex.equals("0")), 
											leftTercetoIndex != null && (!leftTercetoIndex.equals("0")),
											false
										));
									}
break;
case 26:
//#line 117 "test_gramatica.y"
{su.changeSTValues(val_peek(0).sval,val_peek(1).sval,"PARAM");yyval.sval = val_peek(0).sval;}
break;
case 29:
//#line 123 "test_gramatica.y"
{su.addCodeStructure("ASIGNACION");}
break;
case 30:
//#line 124 "test_gramatica.y"
{su.addCodeStructure("SENTENCIA IF");}
break;
case 31:
//#line 125 "test_gramatica.y"
{su.addCodeStructure("SENTENCIA PRINT");}
break;
case 32:
//#line 126 "test_gramatica.y"
{su.addCodeStructure("WHILE");}
break;
case 33:
//#line 130 "test_gramatica.y"
{
																this.t = this.tercetosjump.get(this.tercetosjump.size() - 1);
																this.t2 = new Terceto(
																		"BI",
																		"",
																		"",
																		null
																		);															
																this.tercetos.add(this.t2);
																this.tercetosjump.add(this.t2);		
																this.t.setV2(String.valueOf((Integer)(tercetos.size()+1)));
															}
break;
case 34:
//#line 143 "test_gramatica.y"
{
																this.t = this.tercetosjump.get(this.tercetosjump.size() - 1);															
																this.t.setV2(String.valueOf((Integer)(tercetos.size()+1)));
															}
break;
case 35:
//#line 148 "test_gramatica.y"
{
																this.t = this.tercetosjump.get(this.tercetosjump.size() - 1);
																this.t.setV2(String.valueOf((Integer)(tercetos.size()+1)));	
															}
break;
case 36:
//#line 152 "test_gramatica.y"
{su.addError(" Se encontro un error no considerado por la gramatica luego del IF", "Sintáctica");}
break;
case 39:
//#line 156 "test_gramatica.y"
{su.addError(" Se encontro un error no considerado por la gramatica luego del THEN", "Sintáctica");}
break;
case 42:
//#line 161 "test_gramatica.y"
{su.addError(" Se encontro un error no considerado por la gramatica luego del ELSE", "Sintáctica");}
break;
case 43:
//#line 165 "test_gramatica.y"
{
														this.t = this.tercetosjump.get(this.tercetosjump.size() - 1);
														this.t2 = new Terceto(
				      											"BI",
				      											"",
				      											String.valueOf((Integer)(this.tercetos.indexOf(this.t))),
				      											null
			      												);
			      										this.tercetos.add(this.t2);			
														this.t.setV2(String.valueOf((Integer)(tercetos.size()+1)));
														
													}
break;
case 44:
//#line 177 "test_gramatica.y"
{su.addError(" Se encontro un error no considerado por la gramatica luego del WHILE", "Sintáctica");}
break;
case 47:
//#line 182 "test_gramatica.y"
{su.addError(" Se encontro un error no considerado por la gramatica luego del DO", "Sintáctica");}
break;
case 52:
//#line 193 "test_gramatica.y"
{
										su.addCodeStructure("LLamado a funcion " + val_peek(3).sval);
										SymbolTableValue v = su.getSymbolsTableValue(su.searchForKey(val_peek(3).sval,CurrentScope.getScope()));
										if(v.getUse().equals("FUNC")){
											this.tercetos.add(new Terceto(
												"call",
												su.searchForKey(val_peek(3).sval,CurrentScope.getScope()),
												su.searchForKey(val_peek(1).sval,CurrentScope.getScope()),
												su.getSymbolsTableValue(su.searchForKey(val_peek(1).sval,CurrentScope.getScope())).getType().toLowerCase()
											));
										}else{
											if(v.getUse().equals("VARIABLE") && v.getType().equals("FUNC")){
												this.tercetos.add(new Terceto(
												"call",
												v.getCallable(),
												su.searchForKey(val_peek(1).sval,CurrentScope.getScope()),
												su.getSymbolsTableValue(su.searchForKey(val_peek(1).sval,CurrentScope.getScope())).getType().toLowerCase()
											));
											}
											else{
												su.addError("No se puede llamar a algo que no es una funcion", "Semantica");
											}
										}
									}
break;
case 53:
//#line 220 "test_gramatica.y"
{
										        this.t = new Terceto(
              											"BF",
              											"["+this.tercetos.size()+"]",
              											"",
              											null
      												);
      										    this.tercetos.add(this.t);
      										    this.tercetosjump.add(this.t);
      										        
									}
break;
case 54:
//#line 231 "test_gramatica.y"
{su.addError(" falta parentesis de cierre en la condicion", "Sintáctica");}
break;
case 55:
//#line 232 "test_gramatica.y"
{su.addError(" falta parentesis de apertura en la condicion", "Sintáctica");}
break;
case 56:
//#line 235 "test_gramatica.y"
{
										su.addCodeStructure("Se detecta una condicion AND");
										this.tercetos.addAll(this.getTercetos(
											"&&",
											leftTercetoIndexForAND != null && (!leftTercetoIndexForAND.equals("0")) ? leftTercetoIndexForAND : val_peek(2).sval,
											itsSingleNumber ? val_peek(0).sval : String.valueOf((Integer)tercetos.size()),
											leftTercetoIndexForAND != null && (!leftTercetoIndexForAND.equals("0")), 
											true,
											true
										));
										leftTercetoIndexForAND = String.valueOf((Integer)tercetos.size()); 
									}
break;
case 57:
//#line 248 "test_gramatica.y"
{
										leftTercetoIndexForAND = String.valueOf((Integer)tercetos.size()); 
									}
break;
case 58:
//#line 253 "test_gramatica.y"
{
										su.addCodeStructure("Se detecta una condicion OR");
										this.tercetos.addAll(this.getTercetos(
											"||",
											leftTercetoIndexForOR != null && (!leftTercetoIndexForOR.equals("0")) ? leftTercetoIndexForOR : val_peek(2).sval,
											itsSingleNumber ? val_peek(0).sval : String.valueOf((Integer)tercetos.size()),
											leftTercetoIndexForOR != null && (!leftTercetoIndexForOR.equals("0")), 
											true,
											true
										));
										leftTercetoIndexForOR = String.valueOf((Integer)tercetos.size()); 
									}
break;
case 59:
//#line 266 "test_gramatica.y"
{
										leftTercetoIndexForOR = String.valueOf((Integer)tercetos.size()); 
									}
break;
case 60:
//#line 271 "test_gramatica.y"
{
										su.addCodeStructure("Se detecta una condicion >");
										this.tercetos.addAll(this.getTercetos(
											">",
											leftTercetoIndexForComparison != null && (!leftTercetoIndexForComparison.equals("0")) ? leftTercetoIndexForComparison : val_peek(2).sval,
											itsSingleNumber ? val_peek(0).sval : String.valueOf((Integer)tercetos.size()),
											leftTercetoIndexForComparison != null && (!leftTercetoIndexForComparison.equals("0")), 
											!itsSingleNumber,
											false
										));
										leftTercetoIndex = String.valueOf((Integer)tercetos.size());
										itsSingleNumber = false;
									}
break;
case 61:
//#line 285 "test_gramatica.y"
{
										su.addCodeStructure("Se detecta una condicion <");
										this.tercetos.addAll(this.getTercetos(
											"<",
											leftTercetoIndexForComparison != null && (!leftTercetoIndexForComparison.equals("0")) ? leftTercetoIndexForComparison : val_peek(2).sval,
											itsSingleNumber ? val_peek(0).sval : String.valueOf((Integer)tercetos.size()),
											leftTercetoIndexForComparison != null && (!leftTercetoIndexForComparison.equals("0")), 
											!itsSingleNumber,
											false
										));
										leftTercetoIndex = String.valueOf((Integer)tercetos.size());
										itsSingleNumber = false;
									}
break;
case 62:
//#line 299 "test_gramatica.y"
{
										su.addCodeStructure("Se detecta una condicion >=");
										this.tercetos.addAll(this.getTercetos(
											">=",
											leftTercetoIndexForComparison != null && (!leftTercetoIndexForComparison.equals("0")) ? leftTercetoIndexForComparison : val_peek(2).sval,
											itsSingleNumber ? val_peek(0).sval : String.valueOf((Integer)tercetos.size()),
											leftTercetoIndexForComparison != null && (!leftTercetoIndexForComparison.equals("0")), 
											!itsSingleNumber,
											false
										));
										leftTercetoIndex = String.valueOf((Integer)tercetos.size());
										itsSingleNumber = false;
									}
break;
case 63:
//#line 313 "test_gramatica.y"
{
										su.addCodeStructure("Se detecta una condicion <=");
										this.tercetos.addAll(this.getTercetos(
											"<=",
											leftTercetoIndexForComparison != null && (!leftTercetoIndexForComparison.equals("0")) ? leftTercetoIndexForComparison : val_peek(2).sval,
											itsSingleNumber ? val_peek(0).sval : String.valueOf((Integer)tercetos.size()),
											leftTercetoIndexForComparison != null && (!leftTercetoIndexForComparison.equals("0")), 
											!itsSingleNumber,
											false
										));
										leftTercetoIndex = String.valueOf((Integer)tercetos.size());
										itsSingleNumber = false;
									}
break;
case 64:
//#line 327 "test_gramatica.y"
{
										su.addCodeStructure("Se detecta una condicion ==");
										this.tercetos.addAll(this.getTercetos(
											"==",
											leftTercetoIndexForComparison != null && (!leftTercetoIndexForComparison.equals("0")) ? leftTercetoIndexForComparison : val_peek(2).sval,
											itsSingleNumber ? val_peek(0).sval : String.valueOf((Integer)tercetos.size()),
											leftTercetoIndexForComparison != null && (!leftTercetoIndexForComparison.equals("0")), 
											!itsSingleNumber,
											false
										));
										leftTercetoIndex = String.valueOf((Integer)tercetos.size());
										itsSingleNumber = false;
									}
break;
case 65:
//#line 341 "test_gramatica.y"
{
										su.addCodeStructure("Se detecta una condicion <>");
										this.tercetos.addAll(this.getTercetos(
											"<>",
											leftTercetoIndexForComparison != null && (!leftTercetoIndexForComparison.equals("0")) ? leftTercetoIndexForComparison : val_peek(2).sval,
											itsSingleNumber ? val_peek(0).sval : String.valueOf((Integer)tercetos.size()),
											leftTercetoIndexForComparison != null && (!leftTercetoIndexForComparison.equals("0")), 
											!itsSingleNumber,
											false
										));
										leftTercetoIndex = String.valueOf((Integer)tercetos.size());
										itsSingleNumber = false;
									}
break;
case 66:
//#line 355 "test_gramatica.y"
{
										comparisonDetected = true;
										leftTercetoIndexForComparison = leftTercetoIndex;
										/*leftTercetoIndexForComparison = leftIsTerceto ? String.valueOf((Integer)tercetos.size()) : null; */
										leftIsTerceto = false;
									}
break;
case 67:
//#line 361 "test_gramatica.y"
{su.addError("No se reconoce el comparador", "Sintáctica");}
break;
case 68:
//#line 362 "test_gramatica.y"
{su.addError("No se reconoce el comparador", "Sintáctica");}
break;
case 69:
//#line 366 "test_gramatica.y"
{
										this.tercetos.add(new Terceto(
											"print",
											val_peek(1).sval,
											"-",
											null
										));
									}
break;
case 70:
//#line 376 "test_gramatica.y"
{
										yyval.sval = val_peek(1).sval;
										System.err.println(yyval.sval);
									}
break;
case 71:
//#line 380 "test_gramatica.y"
{su.addError(" falta parentesis de cierre para la sentencia PRINT", "Sintáctica");}
break;
case 72:
//#line 381 "test_gramatica.y"
{su.addError(" falta parentesis de apertura para la sentencia PRINT", "Sintáctica");}
break;
case 73:
//#line 382 "test_gramatica.y"
{su.addError("STRING mal escrito", "Sintáctica");}
break;
case 74:
//#line 386 "test_gramatica.y"
{
										addAssignmentTercetos(val_peek(3).sval,val_peek(1).sval);
										leftIsTerceto = false;
										rightIsTerceto = false;
										sumDetected = false;
										leftTercetoIndex = null;
										itsSingleNumber = false;
									}
break;
case 75:
//#line 396 "test_gramatica.y"
{
										su.addCodeStructure("Se detecta una suma");
										this.tercetos.addAll(this.getTercetos(
											"+",
											leftTercetoIndex != null && (!leftTercetoIndex.equals("0")) ? leftTercetoIndex : val_peek(2).sval,
											rightIsTerceto ? String.valueOf((Integer)tercetos.size()) : val_peek(0).sval,
											leftTercetoIndex != null && (!leftTercetoIndex.equals("0")), 
											rightIsTerceto,
											false
										));
										if (comparisonDetected)
											leftIsTerceto = true;
										leftTercetoIndex = String.valueOf((Integer)tercetos.size());
										itsSingleNumber = false;
									}
break;
case 76:
//#line 412 "test_gramatica.y"
{
										su.addCodeStructure("Se detecta una resta");
										this.tercetos.addAll(this.getTercetos(
											"-",
											leftTercetoIndex != null && (!leftTercetoIndex.equals("0")) ? leftTercetoIndex : val_peek(2).sval,
											rightIsTerceto ? String.valueOf((Integer)tercetos.size()) :val_peek(0).sval,
											leftTercetoIndex != null && (!leftTercetoIndex.equals("0")),
											rightIsTerceto,
											false
										));
										if (comparisonDetected)
											leftIsTerceto = true;
										leftTercetoIndex = String.valueOf((Integer)tercetos.size());
										itsSingleNumber = false;
									}
break;
case 77:
//#line 428 "test_gramatica.y"
{
										yyval.sval = leftIsTerceto ? String.valueOf((Integer)tercetos.size()) : val_peek(0).sval;
										rightIsTerceto = false;
										sumDetected = true; 
										leftTercetoIndex = (leftIsTerceto || comparisonDetected && !itsSingleNumber) ? String.valueOf((Integer)tercetos.size()) : null;
										/*itsSingleNumber = !leftIsTerceto;*/
										if(comparisonDetected && !itsSingleNumber)
											leftIsTerceto = true;
										else 
											leftIsTerceto = false;
										if(itsSingleNumber)
											itsSingleNumber = true;
									}
break;
case 78:
//#line 443 "test_gramatica.y"
{
										su.addCodeStructure("Se detecta una multiplicacion");
										this.tercetos.addAll(this.getTercetos(
											"*",
											leftIsTerceto ? String.valueOf((Integer)tercetos.size()) : val_peek(2).sval,
											val_peek(0).sval,
											leftIsTerceto,
											false,
											false
										));
										leftIsTerceto = true;
										if (sumDetected)
											rightIsTerceto = true;
										itsSingleNumber = false;
									}
break;
case 79:
//#line 459 "test_gramatica.y"
{
										su.addCodeStructure("Se detecta una division");
										this.tercetos.addAll(this.getTercetos(
											"/",
											leftIsTerceto ? String.valueOf((Integer)tercetos.size()) : val_peek(2).sval,
											val_peek(0).sval,
											leftIsTerceto,
											false,
											false
										));
										leftIsTerceto = true;
										if (sumDetected)
											rightIsTerceto = true;
										itsSingleNumber = false;
									}
break;
case 80:
//#line 475 "test_gramatica.y"
{
										yyval.sval = val_peek(0).sval;
										leftIsTerceto = false;
										rightIsTerceto = false;
										itsSingleNumber = true;
									}
break;
case 81:
//#line 482 "test_gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 83:
//#line 484 "test_gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 85:
//#line 487 "test_gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 86:
//#line 488 "test_gramatica.y"
{su.addError("UINT no puede ser negativo", "Sintáctica");}
break;
case 87:
//#line 490 "test_gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 88:
//#line 491 "test_gramatica.y"
{yyval.sval = "-"+val_peek(0).sval; setToNegative(val_peek(0).sval);}
break;
case 89:
//#line 493 "test_gramatica.y"
{yyval.sval = "UINT";}
break;
case 90:
//#line 494 "test_gramatica.y"
{yyval.sval = "DOUBLE";}
break;
//#line 1453 "Parser.java"
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
