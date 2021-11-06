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
    0,    1,    2,    4,    4,    5,    5,    5,    5,    5,
    5,    7,    7,    9,   11,   11,   11,   11,    8,   13,
   13,   12,   10,    3,    3,   16,   16,   16,   16,   18,
   18,   18,   21,   21,   21,   22,   22,   22,   20,   20,
   23,   23,   23,   24,   24,   25,   25,   26,   14,   14,
   14,   28,   28,   29,   29,   30,   30,   30,   30,   30,
   30,   30,   30,   30,   19,   31,   31,   31,   31,   17,
   15,   15,   15,   32,   32,   32,   27,   27,   27,   27,
   33,   33,   34,   34,    6,    6,
};
final static short yylen[] = {                            2,
    6,    1,    1,    2,    1,    3,    1,    3,    2,    2,
    2,    3,    1,    6,    9,   10,    8,    9,    3,    6,
    3,    1,    2,    2,    1,    1,    1,    1,    1,    6,
    5,    3,    5,    2,    3,    5,    2,    3,    3,    3,
    5,    2,    3,    2,    1,    1,    2,    4,    3,    2,
    2,    3,    1,    3,    1,    3,    3,    3,    3,    3,
    3,    1,    3,    3,    3,    3,    2,    2,    2,    4,
    3,    3,    1,    3,    3,    1,    1,    1,    1,    1,
    1,    2,    1,    2,    1,    1,
};
final static short yydefred[] = {                         0,
    2,    0,    0,   13,   85,   86,    0,    0,    0,    5,
    0,    0,    7,    0,    0,    0,    4,    0,    0,   11,
    0,    0,    8,    0,    0,    0,    0,    0,   25,   26,
   27,   28,   29,    0,    6,   12,    0,   19,    0,    0,
    0,    0,    0,    0,   81,   83,    0,    0,    0,    0,
   80,   76,    0,    0,    0,    0,   78,   79,    0,    0,
    0,   24,    0,    0,    0,    0,    0,   68,   69,    0,
   65,   32,    0,    0,   82,   84,    0,    0,    0,    0,
    0,   51,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   40,    0,   39,    1,    0,    0,    0,
    0,    0,    0,   70,   66,    0,   49,    0,    0,   34,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   74,   75,    0,    0,    0,
   46,   42,   23,   14,   21,    0,    0,    0,   48,   35,
    0,    0,    0,   37,   31,    0,   43,   47,    0,   45,
    0,    0,    0,    0,    0,   38,    0,   30,    0,   44,
    0,    0,    0,   33,    0,   41,   20,    0,    0,   36,
    0,    0,   15,    0,   16,
};
final static short yydgoto[] = {                          2,
    3,    8,   28,    9,   10,   11,   12,   13,   14,   99,
   38,  152,   66,   49,   50,   29,   30,   31,   32,   33,
   78,  113,   96,  149,  132,   51,   52,   53,   54,   55,
   42,   56,   57,   58,
};
final static short yysindex[] = {                      -211,
    0,    0, -194,    0,    0,    0, -195, -199, -194,    0,
 -234,   -5,    0, -194,   -3,  -46,    0, -163,   25,    0,
 -161, -184,    0, -157,  -40,   -7,  143,  232,    0,    0,
    0,    0,    0,   69,    0,    0,  -86,    0,  -44,   74,
 -166,   65,   71,   86,    0,    0,  -44, -137, -134,  -34,
    0,    0,  -25, -122,   14,   24,    0,    0,   94, -124,
  102,    0, -121,  -51,  -78,  -46,   35,    0,    0,  108,
    0,    0,  -44,  -24,    0,    0,  186, -130,  -44,  -44,
  -44,    0,  -44,  -44,  -44,  -44,  -44,  -44,  -44,  -44,
  -44,  -44,  -44,    0,  183,    0,    0,  -95,  123,  109,
  149,  136,  -74,    0,    0,  138,    0,  118,  -46,    0,
  207,  122,  -92,   24,   24, -122,   14,  -34,  -34,  -34,
  -34,  -34,  -34,  -34,  -34,    0,    0,  128,  132,  242,
    0,    0,    0,    0,    0,  157,  -44,  162,    0,    0,
  250,  147,  -46,    0,    0,  150,    0,    0,  148,    0,
  -76,  169,  -34,  -44,  152,    0,  254,    0,  160,    0,
  161,  163,  180,    0,  164,    0,    0,  -53,  188,    0,
  195,  -35,    0,  196,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  -14,    0,
    0,    0,    0,    0,  -63,    0,    0,    0,  211,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -39,    0,    0,    0,    0,    0,   21,
    0,    0,    0,  125,    4,  -31,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  213,
    0,    0,    0,  -29,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   -9,   -1,  126,  121,   29,   44,   52,
   67,   75,   90,   98,  113,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  234,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  219,    0,    0,  231,    0,
};
final static short yygindex[] = {                         0,
    0,  263,  -11,    0,  275,  222,   96,    0,    0,    0,
    0,  151,    0,   -2,  347,  345,    0,    0,    0,    0,
    0,    0,    0,    0, -112,    0,   28,  245,  221,  220,
    0,   66,    0,    0,
};
final static int YYTABLESIZE=532;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         41,
   48,   77,   77,   77,   77,   77,  101,   77,   79,   73,
   80,   73,   73,   73,   50,   82,  107,  150,   77,   77,
   77,   77,   77,    4,   60,   65,   73,   73,   73,   73,
   73,   71,   47,   71,   71,   71,  160,   48,   21,   72,
   21,   72,   72,   72,   55,   18,    1,   55,   71,   71,
   71,   71,   71,   20,  103,   23,   72,   72,   72,   72,
   72,   62,    4,    4,   62,   92,    5,    6,   21,   58,
   93,   88,   58,   90,   91,   89,   16,   79,   62,   80,
   62,   62,   62,   35,   59,    7,   58,   59,   58,   58,
   58,   37,   60,  104,   34,   60,   36,  141,  136,   39,
  106,   59,   15,   59,   59,   59,   19,   61,   63,   60,
   61,   60,   60,   60,   68,   64,   69,   70,   64,  126,
  127,   75,   76,   71,   61,   73,   61,   61,   61,   72,
   56,  157,   64,   56,   64,   64,   64,   77,   57,    5,
    6,   57,  111,  112,  114,  115,   83,   56,  105,   56,
   56,   56,   94,   63,   95,   57,   63,   57,   57,   57,
   97,   54,  133,  134,   54,   53,   52,  135,   53,   52,
   63,   24,   63,   63,   63,  137,  140,   25,  139,   24,
  145,  146,   47,   24,   26,   25,  147,   48,   47,   25,
  148,   27,   26,   48,   10,   64,   26,   10,   10,   27,
  151,  154,  102,   27,  100,  156,  138,  161,  158,  162,
  164,   24,   10,   44,   45,   46,   10,   25,  166,  167,
  169,  168,  170,  171,   26,   77,   77,   77,   77,   77,
   77,   27,   77,   73,   73,   73,   73,   73,   73,   77,
   73,  174,   50,   40,   81,   81,  172,   73,   43,   50,
   44,   45,   46,  173,  175,   71,   71,   71,   71,   71,
   71,    3,   71,   72,   72,   72,   72,   72,   72,   71,
   72,   67,   55,   55,   22,   55,   22,   72,   84,   85,
   86,   87,   55,   17,   98,   62,   62,   62,   62,   62,
   62,   74,   62,   58,   58,   58,   58,   58,   58,   62,
   58,  116,  117,    0,  163,    0,    0,   58,   59,   59,
   59,   59,   59,   59,    0,   59,   60,   60,   60,   60,
   60,   60,   59,   60,    0,    0,    0,    0,    0,    0,
   60,   61,   61,   61,   61,   61,   61,    0,   61,   64,
   64,   64,   64,   64,   64,   61,   64,    0,    0,    0,
    0,    0,    0,   64,   56,   56,   56,   56,   56,   56,
    0,   56,   57,   57,   57,   57,   57,   57,   56,   57,
    0,    0,   62,    0,    0,    0,   57,   63,   63,   63,
   63,   63,   63,    0,   63,   67,    0,    0,    0,   54,
   54,   63,   54,    0,   53,   52,   53,   52,   59,   54,
   44,   45,   46,   53,   52,   24,   44,   45,   46,   62,
    0,   25,    0,    0,    0,    0,    0,    0,   26,    0,
    0,  110,  129,    0,  159,   27,    0,    0,    0,    0,
  118,  119,  120,  121,  122,  123,  124,  125,  128,  131,
   24,  108,    0,   24,    0,    0,   25,   62,    0,   25,
    0,    0,    0,   26,    0,  144,   26,  129,  130,    0,
   27,  109,  142,   27,   24,    0,    0,    0,    9,    0,
   25,    9,    9,    0,  131,    0,   17,   26,    0,   17,
   17,    0,  143,  153,   27,   62,    9,    0,   18,   24,
    9,   18,   18,  131,   17,   25,    0,    0,   17,   24,
  153,   62,   26,    0,    0,   25,   18,   24,   61,   27,
   18,   24,   26,   25,    0,    0,  129,   25,    0,   27,
   26,    0,    0,    0,   26,    0,  155,   27,    0,    0,
  165,   27,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   45,   41,   42,   43,   44,   45,   58,   47,   43,   41,
   45,   43,   44,   45,   44,   41,   41,  130,   58,   59,
   60,   61,   62,  258,   27,   37,   58,   59,   60,   61,
   62,   41,   40,   43,   44,   45,  149,   45,   44,   41,
   44,   43,   44,   45,   41,  280,  258,   44,   58,   59,
   60,   61,   62,   59,   66,   59,   58,   59,   60,   61,
   62,   41,  258,  258,   44,   42,  261,  262,   44,   41,
   47,   58,   44,   60,   61,   62,  276,   43,   58,   45,
   60,   61,   62,   59,   41,  280,   58,   44,   60,   61,
   62,  276,   41,   59,  258,   44,  258,  109,  101,  257,
   73,   58,    7,   60,   61,   62,   11,   41,   40,   58,
   44,   60,   61,   62,   41,   41,  283,  284,   44,   92,
   93,  259,  260,   59,   58,   40,   60,   61,   62,   59,
   41,  143,   58,   44,   60,   61,   62,  272,   41,  261,
  262,   44,  273,  274,   79,   80,  269,   58,   41,   60,
   61,   62,   59,   41,  279,   58,   44,   60,   61,   62,
   59,   41,  258,   41,   44,   41,   41,   59,   44,   44,
   58,  258,   60,   61,   62,   40,   59,  264,   41,  258,
   59,  274,   40,  258,  271,  264,   59,   45,   40,  264,
   59,  278,  271,   45,  258,  282,  271,  261,  262,  278,
   44,   40,  281,  278,  256,   59,  281,  284,   59,   41,
   59,  258,  276,  258,  259,  260,  280,  264,   59,   59,
   41,   59,   59,  277,  271,  265,  266,  267,  268,  269,
  270,  278,  272,  265,  266,  267,  268,  269,  270,  279,
  272,  277,  272,  284,  270,  270,   59,  279,  256,  279,
  258,  259,  260,   59,   59,  265,  266,  267,  268,  269,
  270,  276,  272,  265,  266,  267,  268,  269,  270,  279,
  272,   59,  269,  270,   41,  272,   14,  279,  265,  266,
  267,  268,  279,    9,   63,  265,  266,  267,  268,  269,
  270,   47,  272,  265,  266,  267,  268,  269,  270,  279,
  272,   81,   83,   -1,  154,   -1,   -1,  279,  265,  266,
  267,  268,  269,  270,   -1,  272,  265,  266,  267,  268,
  269,  270,  279,  272,   -1,   -1,   -1,   -1,   -1,   -1,
  279,  265,  266,  267,  268,  269,  270,   -1,  272,  265,
  266,  267,  268,  269,  270,  279,  272,   -1,   -1,   -1,
   -1,   -1,   -1,  279,  265,  266,  267,  268,  269,  270,
   -1,  272,  265,  266,  267,  268,  269,  270,  279,  272,
   -1,   -1,   28,   -1,   -1,   -1,  279,  265,  266,  267,
  268,  269,  270,   -1,  272,   39,   -1,   -1,   -1,  269,
  270,  279,  272,   -1,  270,  270,  272,  272,  256,  279,
  258,  259,  260,  279,  279,  258,  258,  259,  260,   65,
   -1,  264,   -1,   -1,   -1,   -1,   -1,   -1,  271,   -1,
   -1,   77,  275,   -1,  277,  278,   -1,   -1,   -1,   -1,
   84,   85,   86,   87,   88,   89,   90,   91,  256,   95,
  258,  256,   -1,  258,   -1,   -1,  264,  103,   -1,  264,
   -1,   -1,   -1,  271,   -1,  111,  271,  275,  276,   -1,
  278,  276,  256,  278,  258,   -1,   -1,   -1,  258,   -1,
  264,  261,  262,   -1,  130,   -1,  258,  271,   -1,  261,
  262,   -1,  276,  137,  278,  141,  276,   -1,  258,  258,
  280,  261,  262,  149,  276,  264,   -1,   -1,  280,  258,
  154,  157,  271,   -1,   -1,  264,  276,  258,  277,  278,
  280,  258,  271,  264,   -1,   -1,  275,  264,   -1,  278,
  271,   -1,   -1,   -1,  271,   -1,  277,  278,   -1,   -1,
  277,  278,
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
"programa : nombre_programa bloque_sentencias_declarativas BEGIN sentencias_ejecutables END ';'",
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
"clausula_seleccion : IF condicion bloque_then bloque_else ENDIF ';'",
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

//#line 371 "test_gramatica.y"
  private Text text;
  private LexicAnalyzer lexicAnalyzer;
  private StructureUtilities su;
  private List<String> idList;
  private List<Terceto> tercetos = new ArrayList<>();
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
	
	String correctLeftValue = (v1_isConst || leftIsTerceto) ? leftValue : su.searchForKey(leftValue, CurrentScope.getScope());
	String correctRightValue = (v2_isConst || rightIsTerceto) ? rightValue : su.searchForKey(rightValue, CurrentScope.getScope());
	
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

    if(!type1.equals(type2) && (type1.contains("DOUBLE") || type2.contains("DOUBLE"))){
      //Si son de distinto tipo, al menos uno es DOUBLE
      Terceto t;
      if(type1.contains("UINT") && !compositeComparison){
        t = new Terceto("utod", leftIsTerceto ? "["+leftValue+"]" : v1_isConst ? leftValue : leftValue + ":" + CurrentScope.getScope(), "-", "double");
        v1_isConverted = true;
        amtTercetos++;
		aux.add(t);
      }else{
        if(type2.contains("UINT") && !compositeComparison) {
          t = new Terceto("utod", rightIsTerceto ? "["+rightValue+"]" : v2_isConst ? rightValue : rightValue + ":" + CurrentScope.getScope(), "-", "double");
          v2_isConverted = true;
		  amtTercetos++;
		  aux.add(t);
        }
      }
      t = new Terceto(
              operator,
              v1_isConverted ? "["+amtTercetos+"]" : leftIsTerceto ? "["+leftValue+"]" : v1_isConst ? leftValue : leftValue + ":" + CurrentScope.getScope(),
              v2_isConverted ? "["+amtTercetos+"]" : rightIsTerceto ? "["+rightValue+"]" : v2_isConst ? rightValue : rightValue + ":" + CurrentScope.getScope(),
              "double"
      );
      aux.add(t);
    }else {
      Terceto t;
      if(type1.contains("UINT")) {
        //este es el caso de que ambos tipos son UINT
        t = new Terceto(
                operator,
                leftIsTerceto ? "["+leftValue+"]" : v1_isConst ? leftValue : leftValue + ":" + CurrentScope.getScope(),
                rightIsTerceto ? "["+rightValue+"]" : v2_isConst ? rightValue : rightValue + ":" + CurrentScope.getScope(),
                "uint"
        );
      }else{
        t = new Terceto(
                operator,
                leftIsTerceto ? "["+leftValue+"]" : v1_isConst ? leftValue : leftValue + ":" + CurrentScope.getScope(),
                rightIsTerceto ? "["+rightValue+"]" : v2_isConst ? rightValue : rightValue + ":" + CurrentScope.getScope(),
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
    if(!use.equals("VARIABLE"))
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
            String rightType = su.getSymbolsTableValue(lexeme).getType();
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
//#line 699 "Parser.java"
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
										showResults();
										int i = 1;
										for(Terceto t : this.tercetos) {
											System.out.println(i + "\t->\t" + t);
											i++;
									  }
									}
break;
case 2:
//#line 25 "test_gramatica.y"
{CurrentScope.addScope(val_peek(0).sval);}
break;
case 6:
//#line 32 "test_gramatica.y"
{su.addCodeStructure("Declaracion de variables " + val_peek(2).sval);su.changeSTValues(idList,val_peek(2).sval,"VARIABLE");su.changeSTKeys(idList,concatenateScope(idList,CurrentScope.getScope()));}
break;
case 8:
//#line 34 "test_gramatica.y"
{su.addCodeStructure("Declaracion de variables de tipo FUNC");su.changeSTValues(idList,"FUNC","VARIABLE");su.changeSTKeys(idList,concatenateScope(idList,CurrentScope.getScope()));}
break;
case 9:
//#line 35 "test_gramatica.y"
{su.addError(" falta ; luego de la sentencia", "Sintáctica");}
break;
case 10:
//#line 36 "test_gramatica.y"
{su.addError(" falta ; luego de la sentencia", "Sintáctica");}
break;
case 11:
//#line 37 "test_gramatica.y"
{su.addError(" la/las variables no tienen tipo", "Sintáctica");}
break;
case 12:
//#line 39 "test_gramatica.y"
{idList.add(val_peek(0).sval);}
break;
case 13:
//#line 40 "test_gramatica.y"
{idList = new ArrayList<>(); idList.add(val_peek(0).sval);}
break;
case 14:
//#line 44 "test_gramatica.y"
{
										su.addCodeStructure("Declaracion de la funcion " + val_peek(3).sval);
										su.changeSTValues(val_peek(3).sval,val_peek(5).sval,"FUNC");
										su.changeSTKey(val_peek(3).sval,val_peek(3).sval + ":" + CurrentScope.getScope());
										CurrentScope.addScope(val_peek(3).sval);
										su.changeSTKey(val_peek(1).sval,val_peek(1).sval + ":" + CurrentScope.getScope());
									}
break;
case 17:
//#line 55 "test_gramatica.y"
{su.addError(" falta ; luego del END", "Sintáctica");}
break;
case 18:
//#line 56 "test_gramatica.y"
{su.addError(" falta ; luego del END", "Sintáctica");}
break;
case 19:
//#line 58 "test_gramatica.y"
{CurrentScope.deleteScope();}
break;
case 21:
//#line 61 "test_gramatica.y"
{su.addError(" Se encontro un error no considerado por la gramatica luego del PRE", "Sintáctica");}
break;
case 23:
//#line 65 "test_gramatica.y"
{su.changeSTValues(val_peek(0).sval,val_peek(1).sval,"PARAM");yyval.sval = val_peek(0).sval;}
break;
case 26:
//#line 71 "test_gramatica.y"
{su.addCodeStructure("ASIGNACION");}
break;
case 27:
//#line 72 "test_gramatica.y"
{su.addCodeStructure("SENTENCIA IF");}
break;
case 28:
//#line 73 "test_gramatica.y"
{su.addCodeStructure("SENTENCIA PRINT");}
break;
case 29:
//#line 74 "test_gramatica.y"
{su.addCodeStructure("WHILE");}
break;
case 32:
//#line 79 "test_gramatica.y"
{su.addError(" Se encontro un error no considerado por la gramatica luego del IF", "Sintáctica");}
break;
case 35:
//#line 84 "test_gramatica.y"
{su.addError(" Se encontro un error no considerado por la gramatica luego del THEN", "Sintáctica");}
break;
case 38:
//#line 89 "test_gramatica.y"
{su.addError(" Se encontro un error no considerado por la gramatica luego del ELSE", "Sintáctica");}
break;
case 40:
//#line 93 "test_gramatica.y"
{su.addError(" Se encontro un error no considerado por la gramatica luego del WHILE", "Sintáctica");}
break;
case 43:
//#line 98 "test_gramatica.y"
{su.addError(" Se encontro un error no considerado por la gramatica luego del DO", "Sintáctica");}
break;
case 48:
//#line 109 "test_gramatica.y"
{
										su.addCodeStructure("LLamado a funcion " + val_peek(3).sval);
										SymbolTableValue v1 = su.getSymbolsTableValue(su.searchForKey(val_peek(3).sval,CurrentScope.getScope()));
										SymbolTableValue v2 = su.getSymbolsTableValue(su.searchForKey(val_peek(1).sval,CurrentScope.getScope()));
										System.err.println(v1 + " - " + v2);
									}
break;
case 50:
//#line 118 "test_gramatica.y"
{su.addError(" falta parentesis de cierre en la condicion", "Sintáctica");}
break;
case 51:
//#line 119 "test_gramatica.y"
{su.addError(" falta parentesis de apertura en la condicion", "Sintáctica");}
break;
case 52:
//#line 122 "test_gramatica.y"
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
case 53:
//#line 135 "test_gramatica.y"
{
										leftTercetoIndexForAND = String.valueOf((Integer)tercetos.size()); 
									}
break;
case 54:
//#line 140 "test_gramatica.y"
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
case 55:
//#line 153 "test_gramatica.y"
{
										leftTercetoIndexForOR = String.valueOf((Integer)tercetos.size()); 
									}
break;
case 56:
//#line 158 "test_gramatica.y"
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
case 57:
//#line 172 "test_gramatica.y"
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
case 58:
//#line 186 "test_gramatica.y"
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
case 59:
//#line 200 "test_gramatica.y"
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
case 60:
//#line 214 "test_gramatica.y"
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
case 61:
//#line 228 "test_gramatica.y"
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
case 62:
//#line 242 "test_gramatica.y"
{
										comparisonDetected = true;
										leftTercetoIndexForComparison = leftIsTerceto ? String.valueOf((Integer)tercetos.size()) : null; 
										leftIsTerceto = false;
									}
break;
case 63:
//#line 247 "test_gramatica.y"
{su.addError("No se reconoce el comparador", "Sintáctica");}
break;
case 64:
//#line 248 "test_gramatica.y"
{su.addError("No se reconoce el comparador", "Sintáctica");}
break;
case 67:
//#line 254 "test_gramatica.y"
{su.addError(" falta parentesis de cierre para la sentencia PRINT", "Sintáctica");}
break;
case 68:
//#line 255 "test_gramatica.y"
{su.addError(" falta parentesis de apertura para la sentencia PRINT", "Sintáctica");}
break;
case 69:
//#line 256 "test_gramatica.y"
{su.addError("STRING mal escrito", "Sintáctica");}
break;
case 70:
//#line 260 "test_gramatica.y"
{
										addAssignmentTercetos(val_peek(3).sval,val_peek(1).sval);
										leftIsTerceto = false;
										rightIsTerceto = false;
										sumDetected = false;
										leftTercetoIndex = null;
										itsSingleNumber = false;
									}
break;
case 71:
//#line 270 "test_gramatica.y"
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
case 72:
//#line 286 "test_gramatica.y"
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
case 73:
//#line 302 "test_gramatica.y"
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
case 74:
//#line 317 "test_gramatica.y"
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
case 75:
//#line 333 "test_gramatica.y"
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
case 76:
//#line 349 "test_gramatica.y"
{
										yyval.sval = val_peek(0).sval;
										leftIsTerceto = false;
										rightIsTerceto = false;
										itsSingleNumber = true;
									}
break;
case 77:
//#line 356 "test_gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 79:
//#line 358 "test_gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 81:
//#line 361 "test_gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 82:
//#line 362 "test_gramatica.y"
{su.addError("UINT no puede ser negativo", "Sintáctica");}
break;
case 83:
//#line 364 "test_gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 84:
//#line 365 "test_gramatica.y"
{yyval.sval = "-"+val_peek(0).sval; setToNegative(val_peek(0).sval);}
break;
case 85:
//#line 367 "test_gramatica.y"
{yyval.sval = "UINT";}
break;
case 86:
//#line 368 "test_gramatica.y"
{yyval.sval = "DOUBLE";}
break;
//#line 1280 "Parser.java"
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
