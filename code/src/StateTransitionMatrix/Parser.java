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






//#line 2 "backup_gram.y"
package StateTransitionMatrix;
import Text.Text;
import Utils.utils;
import java.io.IOException;
import Structures.SymbolTableValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import IntermediateCode.CurrentScope;
import IntermediateCode.Terceto;
//#line 29 "Parser.java"




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
    6,    6,    8,    8,   10,   12,   12,   12,   12,   15,
    9,   16,    9,   14,   14,   13,   11,    4,    4,   19,
   19,   19,   19,   26,   21,   21,   21,   24,   24,   24,
   25,   25,   25,   27,   23,   23,   28,   28,   28,   29,
   29,   30,   30,   31,   17,   17,   17,   33,   33,   34,
   34,   35,   35,   35,   35,   35,   35,   35,   35,   35,
   22,   36,   36,   36,   36,   20,   18,   18,   18,   37,
   37,   37,   32,   32,   32,   32,   38,   38,   39,   39,
    7,    7,
};
final static short yylen[] = {                            2,
    0,    7,    1,    1,    2,    1,    3,    1,    3,    2,
    2,    2,    3,    1,    6,    9,   10,    8,    9,    0,
    4,    0,    3,    6,    3,    1,    2,    2,    1,    1,
    1,    1,    1,    0,    7,    5,    3,    5,    2,    3,
    5,    2,    3,    0,    4,    3,    5,    2,    3,    2,
    1,    1,    2,    4,    3,    2,    2,    3,    1,    3,
    1,    3,    3,    3,    3,    3,    3,    1,    3,    3,
    3,    3,    2,    2,    2,    4,    3,    3,    1,    3,
    3,    1,    1,    1,    1,    1,    1,    2,    1,    2,
    1,    1,
};
final static short yydefred[] = {                         0,
    3,    0,    0,   14,   91,   92,    0,    1,    0,    6,
    0,    0,    8,    0,    0,    0,    5,    0,    0,   12,
    0,   20,    0,    9,    0,    0,    7,   13,    0,    0,
   23,    0,    0,    0,    0,    0,   29,   30,   31,   32,
   33,    0,   21,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   87,   89,    0,    0,    0,    0,   86,   82,
    0,    0,    0,    0,   84,   85,    0,    0,    0,   28,
    0,    0,    0,    0,    0,    0,    0,   74,   75,    0,
   71,   37,    0,    0,   88,   90,    0,    0,    0,    0,
    0,   57,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   46,    0,    2,   27,   15,   25,    0,
    0,    0,   76,   72,    0,   55,    0,    0,   39,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   80,   81,    0,   45,    0,    0,    0,
    0,   54,   40,    0,   36,    0,    0,    0,    0,    0,
   52,   48,    0,    0,    0,    0,    0,    0,   42,    0,
   49,   53,    0,   51,   24,    0,    0,   38,   43,    0,
   35,    0,   50,    0,    0,    0,   47,   16,    0,   41,
   17,
};
final static short yydgoto[] = {                          2,
    3,    8,   16,   36,    9,   10,   11,   12,   13,   14,
   72,   31,  139,   46,   29,   23,   57,   58,   37,   38,
   39,   40,   41,   88,  147,  121,   68,  137,  163,  152,
   59,   60,   61,   62,   63,   50,   64,   65,   66,
};
final static short yysindex[] = {                      -240,
    0,    0, -194,    0,    0,    0, -221,    0, -194,    0,
 -234,  -18,    0, -194,   -5, -229,    0, -203,   12,    0,
 -195,    0, -210,    0,  251,   63,    0,    0, -210,  -86,
    0, -188,  -40,   -7, -147,  224,    0,    0,    0,    0,
    0, -184,    0,  -51,  -74,  251,  -44,   77, -161,   65,
   71,   86,    0,    0,  -44, -119, -140,   49,    0,    0,
  -25, -124,   14,   73,    0,    0,   87,  143,   88,    0,
 -105,  114,  102,  143,  123,  168,  -34,    0,    0,  127,
    0,    0,  -44,  -24,    0,    0,  195, -110,  -44,  -44,
  -44,    0,  -44,  -44,  -44,  -44,  -44,  -44,  -44,  -44,
  -44,  -44,  -44,    0, -103,    0,    0,    0,    0,  135,
  -44,  142,    0,    0,  145,    0,  132,  251,    0,  134,
  -73,   73,   73, -124,   14,   49,   49,   49,   49,   49,
   49,   49,   49,    0,    0,  172,    0,  -83,  161,   49,
  -44,    0,    0,  227,    0,  196,  -65,  151,  152,  228,
    0,    0,  154,  158,  177,  160,  162,  251,    0,  163,
    0,    0,  206,    0,    0,  -57,  164,    0,    0,  236,
    0,  165,    0,  183,  -30,  203,    0,    0,  213,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  -22,    0,
    0,    0,    0,  -21,  -81,    0,    0,    0,  153,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  149,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -39,    0,    0,    0,    0,    0,   21,    0,    0,
    0,  125,    4,  -31,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  218,
    0,    0,    0,  -29,    0,    0,    0,   11,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   -9,   -1,  126,  121,   29,   44,   52,   67,   75,
   90,   98,  113,    0,    0,    0,    0,    0,    0,  244,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  200,    0,    0,    0,    0,  217,    0,
    0,
};
final static short yygindex[] = {                         0,
    0,  278,    0,  157,    0,  293,  261,  110,    0,    0,
    0,  275,  166,    0,    0,    0,   27,  324,   62,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -66,
    0,   -3,  250,  215,  232,    0,   54,    0,    0,
};
final static int YYTABLESIZE=529;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         49,
   56,   83,   83,   83,   83,   83,   74,   83,   89,   79,
   90,   79,   79,   79,   56,   92,  116,    1,   83,   83,
   83,   83,   83,    4,  113,   21,   79,   79,   79,   79,
   79,   77,   55,   77,   77,   77,    4,   56,   21,   78,
   20,   78,   78,   78,   61,   18,   25,   61,   77,   77,
   77,   77,   77,   24,   26,   21,   78,   78,   78,   78,
   78,   68,   28,    4,   68,   30,    5,    6,   47,   64,
   27,   98,   64,  100,  101,   99,    5,    6,   68,  115,
   68,   68,   68,  164,   65,    7,   64,   65,   64,   64,
   64,   89,   66,   90,  105,   66,  173,   70,  134,  135,
  110,   65,   42,   65,   65,   65,   70,   67,   67,   66,
   67,   66,   66,   66,  102,   70,   15,   78,   70,  103,
   19,   79,   80,   81,   67,   83,   67,   67,   67,   82,
   62,   87,   70,   62,   70,   70,   70,   70,   63,   85,
   86,   63,  122,  123,   93,  104,  106,   62,  119,   62,
   62,   62,  107,   69,  108,   63,   69,   63,   63,   63,
  109,   60,  111,  120,   60,   59,   58,  114,   59,   58,
   69,   32,   69,   69,   69,  136,   11,   33,  138,   11,
   11,  141,   55,   32,   34,  142,   45,   56,   44,   33,
  143,   35,  145,   44,   11,   44,   34,  151,   11,  146,
  153,  154,   76,   35,   73,   70,   75,  159,  160,  161,
  162,  151,  165,   52,   53,   54,  166,  167,  168,  174,
  169,  171,  175,  177,  151,   83,   83,   83,   83,   83,
   83,   70,   83,   79,   79,   79,   79,   79,   79,   83,
   79,  178,   56,   48,   91,   91,  179,   79,   51,   56,
   52,   53,   54,    4,   22,   77,   77,   77,   77,   77,
   77,  180,   77,   78,   78,   78,   78,   78,   78,   77,
   78,  181,   61,   61,  144,   61,   73,   78,   94,   95,
   96,   97,   61,   34,   26,   68,   68,   68,   68,   68,
   68,   22,   68,   64,   64,   64,   64,   64,   64,   68,
   64,   17,   71,   43,   84,  124,  155,   64,   65,   65,
   65,   65,   65,   65,  170,   65,   66,   66,   66,   66,
   66,   66,   65,   66,  125,    0,    0,    0,    0,    0,
   66,   67,   67,   67,   67,   67,   67,    0,   67,   70,
   70,   70,   70,   70,   70,   67,   70,    0,    0,    0,
    0,    0,    0,   70,   62,   62,   62,   62,   62,   62,
    0,   62,   63,   63,   63,   63,   63,   63,   62,   63,
   77,    0,    0,    0,    0,    0,   63,   69,   69,   69,
   69,   69,   69,    0,   69,    0,    0,    0,    0,   60,
   60,   69,   60,    0,   59,   58,   59,   58,    0,   60,
   52,   53,   54,   59,   58,    0,   44,   44,   44,    0,
   10,    0,    0,   10,   10,    0,    0,  126,  127,  128,
  129,  130,  131,  132,  133,   32,    0,  148,   10,   32,
    0,   33,   10,    0,  140,   33,    0,    0,   34,    0,
    0,    0,   34,    0,    0,   35,  149,  150,  112,   35,
  117,  157,   32,   32,    0,    0,    0,   18,   33,   33,
   18,   18,    0,   32,  140,   34,   34,    0,    0,   33,
  118,  158,   35,   35,   19,   18,   34,   19,   19,   18,
  149,   32,  172,   35,   32,   32,    0,   33,    0,    0,
   33,   33,   19,   32,   34,    0,   19,   34,   34,   33,
   69,   35,  149,  156,   35,   35,   34,    0,   32,    0,
    0,    0,  176,   35,   33,    0,    0,    0,    0,    0,
    0,   34,    0,    0,    0,    0,    0,    0,   35,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   45,   41,   42,   43,   44,   45,   58,   47,   43,   41,
   45,   43,   44,   45,   44,   41,   41,  258,   58,   59,
   60,   61,   62,  258,   59,   44,   58,   59,   60,   61,
   62,   41,   40,   43,   44,   45,  258,   45,   44,   41,
   59,   43,   44,   45,   41,  280,  276,   44,   58,   59,
   60,   61,   62,   59,  258,   44,   58,   59,   60,   61,
   62,   41,  258,  258,   44,  276,  261,  262,  257,   41,
   59,   58,   44,   60,   61,   62,  261,  262,   58,   83,
   60,   61,   62,  150,   41,  280,   58,   44,   60,   61,
   62,   43,   41,   45,   68,   44,  163,   36,  102,  103,
   74,   58,   40,   60,   61,   62,   45,   41,  256,   58,
   44,   60,   61,   62,   42,   41,    7,   41,   44,   47,
   11,  283,  284,   59,   58,   40,   60,   61,   62,   59,
   41,  272,   58,   44,   60,   61,   62,   76,   41,  259,
  260,   44,   89,   90,  269,   59,   59,   58,   87,   60,
   61,   62,  258,   41,   41,   58,   44,   60,   61,   62,
   59,   41,   40,  274,   44,   41,   41,   41,   44,   44,
   58,  258,   60,   61,   62,  279,  258,  264,   44,  261,
  262,   40,   40,  258,  271,   41,   30,   45,   40,  264,
   59,  278,   59,   45,  276,  282,  271,  136,  280,  273,
  284,   41,   46,  278,  256,  144,  281,  146,  274,   59,
   59,  150,   59,  258,  259,  260,   59,   41,   59,  277,
   59,   59,   59,   59,  163,  265,  266,  267,  268,  269,
  270,  170,  272,  265,  266,  267,  268,  269,  270,  279,
  272,   59,  272,  284,  270,  270,  277,  279,  256,  279,
  258,  259,  260,  276,  276,  265,  266,  267,  268,  269,
  270,   59,  272,  265,  266,  267,  268,  269,  270,  279,
  272,   59,  269,  270,  118,  272,   59,  279,  265,  266,
  267,  268,  279,  273,   41,  265,  266,  267,  268,  269,
  270,   14,  272,  265,  266,  267,  268,  269,  270,  279,
  272,    9,   42,   29,   55,   91,  141,  279,  265,  266,
  267,  268,  269,  270,  158,  272,  265,  266,  267,  268,
  269,  270,  279,  272,   93,   -1,   -1,   -1,   -1,   -1,
  279,  265,  266,  267,  268,  269,  270,   -1,  272,  265,
  266,  267,  268,  269,  270,  279,  272,   -1,   -1,   -1,
   -1,   -1,   -1,  279,  265,  266,  267,  268,  269,  270,
   -1,  272,  265,  266,  267,  268,  269,  270,  279,  272,
   47,   -1,   -1,   -1,   -1,   -1,  279,  265,  266,  267,
  268,  269,  270,   -1,  272,   -1,   -1,   -1,   -1,  269,
  270,  279,  272,   -1,  270,  270,  272,  272,   -1,  279,
  258,  259,  260,  279,  279,   -1,  258,  259,  260,   -1,
  258,   -1,   -1,  261,  262,   -1,   -1,   94,   95,   96,
   97,   98,   99,  100,  101,  258,   -1,  256,  276,  258,
   -1,  264,  280,   -1,  111,  264,   -1,   -1,  271,   -1,
   -1,   -1,  271,   -1,   -1,  278,  275,  276,  281,  278,
  256,  256,  258,  258,   -1,   -1,   -1,  258,  264,  264,
  261,  262,   -1,  258,  141,  271,  271,   -1,   -1,  264,
  276,  276,  278,  278,  258,  276,  271,  261,  262,  280,
  275,  258,  277,  278,  258,  258,   -1,  264,   -1,   -1,
  264,  264,  276,  258,  271,   -1,  280,  271,  271,  264,
  277,  278,  275,  277,  278,  278,  271,   -1,  258,   -1,
   -1,   -1,  277,  278,  264,   -1,   -1,   -1,   -1,   -1,
   -1,  271,   -1,   -1,   -1,   -1,   -1,   -1,  278,
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
"$$2 :",
"funcion : header_funcion bloque_sentencias_declarativas $$2 cuerpo_funcion",
"$$3 :",
"funcion : header_funcion $$3 cuerpo_funcion",
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
"$$4 :",
"clausula_seleccion : IF condicion bloque_then $$4 bloque_else ENDIF ';'",
"clausula_seleccion : IF condicion bloque_then ENDIF ';'",
"clausula_seleccion : IF error ';'",
"bloque_then : THEN BEGIN sentencias_ejecutables END ';'",
"bloque_then : THEN sentencia_ejecutable",
"bloque_then : THEN error ';'",
"bloque_else : ELSE BEGIN sentencias_ejecutables END ';'",
"bloque_else : ELSE sentencia_ejecutable",
"bloque_else : ELSE error ';'",
"$$5 :",
"while : WHILE $$5 condicion bloque_while",
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

//#line 487 "backup_gram.y"
  private Text text;
  private LexicAnalyzer lexicAnalyzer;
  private StructureUtilities su;
  private List<String> idList;
  public List<Terceto> tercetos = new ArrayList<>();
  private List<Terceto> tercetosjump = new ArrayList<>();
  private Stack<Terceto> tercetosFuncs = new Stack<>();
  private Terceto t; 
  private Terceto t2; 
  boolean isFunction = false;

  
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
      utils.exportResults(su, this.tercetos);
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
  
  private void addTercetos(String operator, ParserVal left, ParserVal right, ParserVal result){
	if(!(left.sval.startsWith("[") && left.sval.endsWith("]"))){
		String correctLeftKey = su.searchForKey(left.sval.split(":")[0], CurrentScope.getScope());
		if(correctLeftKey == null){
			su.addError("La variable no esta al alcance", "Semantica");
			return;
		}else
			left.sval = correctLeftKey;
	}
	
	if(!(right.sval.startsWith("[") && right.sval.endsWith("]"))){
		String correctRightKey = su.searchForKey(right.sval.split(":")[0], CurrentScope.getScope());
		if(correctRightKey == null){
			su.addError("La variable no esta al alcance", "Semantica");
			return;
		}else
			right.sval = correctRightKey;
	}
	
	if(left.type == null || right.type == null){
		su.addError("La variable no tiene tipo", "Semantica");
		return;
	}
	
	char conv = whoConverts(left.type, right.type);
	if(conv == 'L'){
		this.tercetos.add(new Terceto("utod", left.sval, "-", "double"));
		left.sval = "["+this.tercetos.size()+"]";
	}
	else{
		if(conv == 'R'){
			this.tercetos.add(new Terceto("utod", right.sval, "-", "double"));
			right.sval = "["+this.tercetos.size()+"]";
		}
	}
	result.type = left.type.equals("func") ? "func" : conv != 'N' ? "double" : left.type;
	this.tercetos.add(getArithTerceto(operator,left.sval,right.sval,result.type));
	result.sval = "["+this.tercetos.size()+"]";
  }
  
  private char whoConverts(String leftType, String rightType) { 
	  
    if(!leftType.equals(rightType) && !leftType.contains("func")){
      if(leftType.toLowerCase().contains("double") && rightType.toLowerCase().contains("uint"))
        return 'R';
      else
        if(leftType.toLowerCase().contains("uint") && rightType.toLowerCase().contains("double"))
          return 'L';
        else
          su.addError("No es posible realizar la conversion", "Semantica");
      return 'X';
    }else
      return 'N';
  }

  private Terceto getArithTerceto(String operation, String leftValue, String rightValue, String type){
    return new Terceto(operation, leftValue, rightValue, type);
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
//#line 601 "Parser.java"
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
//#line 18 "backup_gram.y"
{
										this.tercetos.add(new Terceto("main", "-", "-", null));
									}
break;
case 2:
//#line 22 "backup_gram.y"
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
//#line 31 "backup_gram.y"
{CurrentScope.addScope(val_peek(0).sval);}
break;
case 7:
//#line 39 "backup_gram.y"
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
//#line 53 "backup_gram.y"
{su.addCodeStructure("Declaracion de variables de tipo FUNC");su.changeSTValues(idList,"FUNC","VARIABLE");su.changeSTKeys(idList,concatenateScope(idList,CurrentScope.getScope()));}
break;
case 10:
//#line 54 "backup_gram.y"
{su.addError(" falta ; luego de la sentencia", "Sintáctica");}
break;
case 11:
//#line 55 "backup_gram.y"
{su.addError(" falta ; luego de la sentencia", "Sintáctica");}
break;
case 12:
//#line 56 "backup_gram.y"
{su.addError(" la/las variables no tienen tipo", "Sintáctica");}
break;
case 13:
//#line 58 "backup_gram.y"
{idList.add(val_peek(0).sval);}
break;
case 14:
//#line 59 "backup_gram.y"
{idList = new ArrayList<>(); idList.add(val_peek(0).sval);}
break;
case 15:
//#line 63 "backup_gram.y"
{
										yyval.sval = val_peek(3).sval;
										yyval.type = val_peek(5).sval;
										su.addCodeStructure("Declaracion de la funcion " + val_peek(3).sval);
										su.changeSTValues(val_peek(3).sval,val_peek(5).sval,"FUNC", val_peek(1).sval + ":" + CurrentScope.getScope() + ":" + val_peek(3).sval);
										su.changeSTKey(val_peek(3).sval,val_peek(3).sval + ":" + CurrentScope.getScope());
										
										this.tercetos.add(new Terceto(
												"var",
												"param_"+val_peek(3).sval+":"+CurrentScope.getScope(), 
												val_peek(1).type,
												val_peek(1).type
											));
										
										su.addSymbolToTable("param_"+val_peek(3).sval+":"+CurrentScope.getScope(), "");
										su.changeSTValues("param_"+val_peek(3).sval+":"+CurrentScope.getScope(), val_peek(1).type, "VARIABLE");
										
										CurrentScope.addScope(val_peek(3).sval);
										su.changeSTKey(val_peek(1).sval,val_peek(1).sval + ":" + CurrentScope.getScope());
										this.tercetosFuncs.push(new Terceto(
											"tag",
											su.searchForKey(val_peek(3).sval, CurrentScope.getScope()),
											val_peek(1).sval + ":" + CurrentScope.getScope(),
											su.getSymbolsTableValue(su.searchForKey(val_peek(3).sval, CurrentScope.getScope())).getType() + "-" +
											val_peek(1).type.toLowerCase()
											));
											
									}
break;
case 16:
//#line 94 "backup_gram.y"
{
										yyval.sval = val_peek(4).sval;
										yyval.type = val_peek(4).type;
									}
break;
case 17:
//#line 99 "backup_gram.y"
{
										yyval.sval = val_peek(4).sval;
										yyval.type = val_peek(4).type;
									}
break;
case 18:
//#line 103 "backup_gram.y"
{su.addError(" falta ; luego del END", "Sintáctica");}
break;
case 19:
//#line 104 "backup_gram.y"
{su.addError(" falta ; luego del END", "Sintáctica");}
break;
case 20:
//#line 106 "backup_gram.y"
{this.tercetos.add(this.tercetosFuncs.pop());}
break;
case 21:
//#line 107 "backup_gram.y"
{	
										ParserVal l = new ParserVal(val_peek(3).sval);
										l.type = val_peek(3).type;
										addTercetos(":=", l, val_peek(0), yyval);
										this.tercetos.add(new Terceto("ret", "-", "-", l.type));
										CurrentScope.deleteScope();
									}
break;
case 22:
//#line 115 "backup_gram.y"
{this.tercetos.add(this.tercetosFuncs.pop());}
break;
case 23:
//#line 116 "backup_gram.y"
{	
										ParserVal l = new ParserVal(val_peek(2).sval);
										l.type = val_peek(2).type;
										addTercetos(":=", l, val_peek(0), yyval);
										this.tercetos.add(new Terceto("ret", "-", "-", l.type));
										CurrentScope.deleteScope();
									}
break;
case 24:
//#line 126 "backup_gram.y"
{
									this.t = this.tercetosjump.remove(this.tercetosjump.size() - 1);
									this.tercetos.add(new Terceto (
															"Pre",
															val_peek(1).sval,
															"-",
															null
															));
									this.tercetos.add(new Terceto (
															"Label"+(this.tercetos.size()+1),
															"",
															"",
															null
															));
									this.t.setV2(String.valueOf("Label"+this.tercetos.size()));
								}
break;
case 25:
//#line 142 "backup_gram.y"
{su.addError(" Se encontro un error no considerado por la gramatica luego del PRE", "Sintáctica");}
break;
case 26:
//#line 145 "backup_gram.y"
{
										yyval.sval = val_peek(0).sval;
										yyval.type = val_peek(0).type;
									}
break;
case 27:
//#line 151 "backup_gram.y"
{
										su.changeSTValues(val_peek(0).sval,val_peek(1).sval,"PARAM");
										yyval.sval = val_peek(0).sval;
										yyval.type = val_peek(1).sval;
									}
break;
case 30:
//#line 161 "backup_gram.y"
{su.addCodeStructure("ASIGNACION");}
break;
case 31:
//#line 162 "backup_gram.y"
{su.addCodeStructure("SENTENCIA IF");}
break;
case 32:
//#line 163 "backup_gram.y"
{su.addCodeStructure("SENTENCIA PRINT");}
break;
case 33:
//#line 164 "backup_gram.y"
{su.addCodeStructure("WHILE");}
break;
case 34:
//#line 168 "backup_gram.y"
{
																this.t = this.tercetosjump.remove(this.tercetosjump.size() - 1);
																this.t2 = new Terceto(
																		"BI",
																		"",
																		"",
																		null
																		);															
																this.tercetos.add(this.t2);
																this.tercetosjump.add(this.t2);
																this.tercetos.add(new Terceto (
																						"Label"+(this.tercetos.size()+1),
																						"",
																						"",
																						null
																						));
																this.t.setV2(String.valueOf("Label"+this.tercetos.size()));
															}
break;
case 35:
//#line 187 "backup_gram.y"
{
																this.tercetos.add(new Terceto (
																						"Label"+(this.tercetos.size()+1),
																						"",
																						"",
																						null
																						));
																this.t = this.tercetosjump.remove(this.tercetosjump.size() - 1);															
																this.t.setV1(String.valueOf("Label"+this.tercetos.size()));
															}
break;
case 36:
//#line 198 "backup_gram.y"
{
																this.tercetos.add(new Terceto (
																						"Label"+(this.tercetos.size()+1),
																						"",
																						"",
																						null
																						));
																this.t = this.tercetosjump.remove(this.tercetosjump.size() - 1);
																this.t.setV2(String.valueOf("Label"+this.tercetos.size()));	
															}
break;
case 37:
//#line 208 "backup_gram.y"
{su.addError(" Se encontro un error no considerado por la gramatica luego del IF", "Sintáctica");}
break;
case 40:
//#line 212 "backup_gram.y"
{su.addError(" Se encontro un error no considerado por la gramatica luego del THEN", "Sintáctica");}
break;
case 43:
//#line 217 "backup_gram.y"
{su.addError(" Se encontro un error no considerado por la gramatica luego del ELSE", "Sintáctica");}
break;
case 44:
//#line 221 "backup_gram.y"
{
										this.t = new Terceto (
																"Label"+(this.tercetos.size()+1),
																"",
																"",
																null
																);
										this.tercetos.add(this.t);
										this.tercetosjump.add(this.t);
									}
break;
case 45:
//#line 232 "backup_gram.y"
{
														this.t = this.tercetosjump.remove(this.tercetosjump.size() - 1);
														this.t2 = this.tercetosjump.remove(this.tercetosjump.size() - 1);
														this.tercetos.add(new Terceto(
				      											"BI",
				      											String.valueOf("Label"+(this.tercetos.indexOf(this.t2)+1)),
				      											"",
				      											null
			      												));
														this.tercetos.add(new Terceto (
																						"Label"+(this.tercetos.size()+1),
																						"",
																						"",
																						null
																						));
														this.t.setV2(String.valueOf("Label"+this.tercetos.size()));
														
													}
break;
case 46:
//#line 250 "backup_gram.y"
{su.addError(" Se encontro un error no considerado por la gramatica luego del WHILE", "Sintáctica");}
break;
case 49:
//#line 255 "backup_gram.y"
{su.addError(" Se encontro un error no considerado por la gramatica luego del DO", "Sintáctica");}
break;
case 54:
//#line 266 "backup_gram.y"
{
										su.addCodeStructure("LLamado a funcion " + val_peek(3).sval);
										String correctFunc = su.searchForKey(val_peek(3).sval,CurrentScope.getScope());
										if(correctFunc == null){
											su.addError("No se puede realizar el llamado a la funcion", "Semantica");
											break;
										}
										SymbolTableValue v = su.getSymbolsTableValue(correctFunc);
										if(v.getUse().equals("FUNC")){
											yyval.type = v.getType();
											ParserVal l = new ParserVal("param_"+val_peek(3).sval+":"+CurrentScope.getScope());
											l.type = su.getSymbolsTableValue("param_"+val_peek(3).sval+":"+CurrentScope.getScope()).getType();
											if(l.type == null){
												l.type = su.getSymbolsTableValue(su.searchForKey("param_"+val_peek(3).sval,CurrentScope.getScope())).getType();
											}
											
											addTercetos(":=", l, val_peek(1), yyval);
											
											this.tercetos.add(new Terceto(
												"call",
												su.searchForKey(val_peek(3).sval,CurrentScope.getScope()),
												"-",
												v.getType()
											));
										}else{
											su.addError("No se puede llamar a otra cosa que no sea una funcion", "Semantica");
										}
									}
break;
case 55:
//#line 297 "backup_gram.y"
{
										        this.t = new Terceto(
              											"BF",
              											"["+this.tercetos.size()+"]",
              											"",
              											null
      												);
      										    this.tercetos.add(this.t);
      										    this.tercetosjump.add(this.t);
												yyval.sval = val_peek(1).sval;
      										        
									}
break;
case 56:
//#line 309 "backup_gram.y"
{su.addError(" falta parentesis de cierre en la condicion", "Sintáctica");}
break;
case 57:
//#line 310 "backup_gram.y"
{su.addError(" falta parentesis de apertura en la condicion", "Sintáctica");}
break;
case 58:
//#line 313 "backup_gram.y"
{
										this.tercetos.add(getArithTerceto("&&",val_peek(2).sval,val_peek(0).sval,yyval.type));
										yyval.sval = "["+this.tercetos.size()+"]";
									}
break;
case 59:
//#line 318 "backup_gram.y"
{
										yyval.sval = val_peek(0).sval;
										yyval.type = val_peek(0).type;
									}
break;
case 60:
//#line 324 "backup_gram.y"
{
										this.tercetos.add(getArithTerceto("||",val_peek(2).sval,val_peek(0).sval,yyval.type));
										yyval.sval = "["+this.tercetos.size()+"]";
									}
break;
case 61:
//#line 329 "backup_gram.y"
{
										yyval.sval = val_peek(0).sval;
										yyval.type = val_peek(0).type;
									}
break;
case 62:
//#line 335 "backup_gram.y"
{
										addTercetos(">", val_peek(2), val_peek(0), yyval);
									}
break;
case 63:
//#line 339 "backup_gram.y"
{
										addTercetos("<", val_peek(2), val_peek(0), yyval);
									}
break;
case 64:
//#line 343 "backup_gram.y"
{
										addTercetos(">=", val_peek(2), val_peek(0), yyval);
									}
break;
case 65:
//#line 347 "backup_gram.y"
{
										addTercetos("<=", val_peek(2), val_peek(0), yyval);
									}
break;
case 66:
//#line 351 "backup_gram.y"
{
										addTercetos("==", val_peek(2), val_peek(0), yyval);
									}
break;
case 67:
//#line 355 "backup_gram.y"
{
										addTercetos("<>", val_peek(2), val_peek(0), yyval);
									}
break;
case 68:
//#line 359 "backup_gram.y"
{
										yyval.sval = val_peek(0).sval;
										yyval.type = val_peek(0).type;
									}
break;
case 69:
//#line 363 "backup_gram.y"
{su.addError("No se reconoce el comparador", "Sintáctica");}
break;
case 70:
//#line 364 "backup_gram.y"
{su.addError("No se reconoce el comparador", "Sintáctica");}
break;
case 71:
//#line 368 "backup_gram.y"
{
										this.tercetos.add(new Terceto(
											"print",
											val_peek(1).sval,
											"-",
											null
										));
									}
break;
case 72:
//#line 378 "backup_gram.y"
{
										yyval.sval = val_peek(1).sval;
										System.err.println(yyval.sval);
									}
break;
case 73:
//#line 382 "backup_gram.y"
{su.addError(" falta parentesis de cierre para la sentencia PRINT", "Sintáctica");}
break;
case 74:
//#line 383 "backup_gram.y"
{su.addError(" falta parentesis de apertura para la sentencia PRINT", "Sintáctica");}
break;
case 75:
//#line 384 "backup_gram.y"
{su.addError("STRING mal escrito", "Sintáctica");}
break;
case 76:
//#line 388 "backup_gram.y"
{
										String correctKey = su.searchForKey(val_peek(3).sval.split(":")[0], CurrentScope.getScope());
										if(su.getSymbolsTableValue(correctKey).getType().toLowerCase().contains("func")){
											String correctFunc = su.searchForKey(val_peek(1).sval.split(":")[0], CurrentScope.getScope());
											if(su.getSymbolsTableValue(correctFunc).getUse().toLowerCase().contains("func")){
                                                String paramType = su.getSymbolsTableValue(su.getSymbolsTableValue(correctFunc).getParameter()).getType();
												ParserVal l = new ParserVal(correctKey);l.setType("func");
												ParserVal r = new ParserVal(correctFunc);r.setType(su.getSymbolsTableValue(correctFunc).getType().toLowerCase());
												addTercetos("func_assig", l, r, yyval);
												
												Terceto auxT = this.tercetos.remove(this.tercetos.size()-1);
												auxT.setType(r.type);
												this.tercetos.add(auxT);
												
												su.changeSTValues(correctKey, r.type, "FUNC", su.getSymbolsTableValue(correctFunc).getParameter());
												this.tercetos.add(new Terceto(
													"var",
													"param_"+val_peek(3).sval+":"+CurrentScope.getScope(), 
													paramType,
													paramType
												));
												su.addSymbolToTable("param_"+val_peek(3).sval+":"+CurrentScope.getScope(), "");
												su.changeSTValues("param_"+val_peek(3).sval+":"+CurrentScope.getScope(), paramType, "VARIABLE");
											}else{
												su.addError("No se puede asignar algo que no sea una funcion cuando el lado izquierdo es de tipo FUNC", "Semantica");
											}
										}else{
											String leftKey = su.searchForKey(val_peek(3).sval, CurrentScope.getScope());
											if(leftKey == null || val_peek(1).type == null){
												su.addError("No se puede realizar la asignacion","Semantica");
												break;
											}
											char conv = whoConverts(su.getSymbolsTableValue(leftKey).getType().toLowerCase(), val_peek(1).type);
											if(conv == 'L'){
												su.addError("No se puede realizar la asignacion por que el tipo del lado izquierdo no es convertible","Semantica");
											}
											else{
												if(conv == 'R'){
													this.tercetos.add(new Terceto("utod", val_peek(1).sval, "-", "double"));
													val_peek(1).sval = "["+this.tercetos.size()+"]";
												}
											}
											this.tercetos.add(getArithTerceto(":=",correctKey,val_peek(1).sval,su.getSymbolsTableValue(correctKey).getType().toLowerCase()));
										}
									}
break;
case 77:
//#line 436 "backup_gram.y"
{
										addTercetos("+", val_peek(2), val_peek(0), yyval);

									}
break;
case 78:
//#line 441 "backup_gram.y"
{
										addTercetos("-", val_peek(2), val_peek(0), yyval);
									}
break;
case 79:
//#line 445 "backup_gram.y"
{
										yyval.sval = val_peek(0).sval;
										yyval.type = val_peek(0).type;
									}
break;
case 80:
//#line 451 "backup_gram.y"
{										
										addTercetos("*", val_peek(2), val_peek(0), yyval);
									}
break;
case 81:
//#line 455 "backup_gram.y"
{
										addTercetos("/", val_peek(2), val_peek(0), yyval);
									}
break;
case 82:
//#line 459 "backup_gram.y"
{
										yyval.sval = val_peek(0).sval;
										yyval.type = val_peek(0).type;
									}
break;
case 83:
//#line 465 "backup_gram.y"
{
										yyval.sval = val_peek(0).sval+":"+CurrentScope.getScope(); 
										String correctKey = su.searchForKey(val_peek(0).sval, CurrentScope.getScope());
										if(correctKey != null)
											yyval.type = su.getSymbolsTableValue(correctKey).getType().toLowerCase();
										else
											su.addError("La variable no fue declarada", "Semantica");
									}
break;
case 84:
//#line 473 "backup_gram.y"
{yyval.sval = val_peek(0).sval;yyval.type = "uint";}
break;
case 85:
//#line 474 "backup_gram.y"
{yyval.sval = val_peek(0).sval;yyval.type = "double";}
break;
case 86:
//#line 475 "backup_gram.y"
{yyval.type = val_peek(0).type; yyval.sval = "["+this.tercetos.size()+"]";}
break;
case 87:
//#line 477 "backup_gram.y"
{yyval.sval = val_peek(0).sval;}
break;
case 88:
//#line 478 "backup_gram.y"
{su.addError("UINT no puede ser negativo", "Sintáctica");}
break;
case 89:
//#line 480 "backup_gram.y"
{yyval.sval = val_peek(0).sval;}
break;
case 90:
//#line 481 "backup_gram.y"
{yyval.sval = "-"+val_peek(0).sval; setToNegative(val_peek(0).sval);}
break;
case 91:
//#line 483 "backup_gram.y"
{yyval.sval = "uint";}
break;
case 92:
//#line 484 "backup_gram.y"
{yyval.sval = "double";}
break;
//#line 1345 "Parser.java"
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
