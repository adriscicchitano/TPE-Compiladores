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
import Structures.SymbolsTable;
import java.util.ArrayList;
import java.util.List;
import Structures.CurrentScope;
//#line 26 "Parser.java"




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
    0,    1,    4,    2,    5,    5,    6,    6,    6,    6,
    6,    6,    6,    6,    8,    8,   10,    9,    9,   14,
   14,   14,   14,   14,   14,   14,   14,   13,   11,    3,
    3,   12,   12,   17,   17,   17,   17,   17,   17,   17,
   17,   17,   21,   21,   21,   22,   22,   23,   23,   24,
   24,   24,   25,   19,   19,   19,   19,   19,   19,   19,
   19,   19,   19,   19,   15,   15,   15,   27,   27,   28,
   28,   29,   29,   29,   29,   29,   29,   29,   29,   29,
   20,   30,   30,   30,   30,   18,   16,   16,   16,   31,
   31,   31,   26,   26,   26,   26,   32,   32,   33,   33,
    7,    7,
};
final static short yylen[] = {                            2,
    3,    1,    2,    1,    2,    1,    3,    2,    3,    2,
    1,    2,    2,    1,    3,    1,    6,   10,   11,    6,
    5,    5,    5,    5,    5,    4,    5,    1,    2,    4,
    3,    2,    1,    2,    2,    2,    2,    1,    1,    1,
    1,    1,    4,    3,    3,    3,    2,    2,    1,    1,
    2,    1,    4,    7,    5,    6,    6,    4,    4,    6,
    4,    6,    4,    6,    3,    2,    2,    3,    1,    3,
    1,    3,    3,    3,    3,    3,    3,    1,    3,    3,
    2,    3,    2,    2,    2,    3,    3,    3,    1,    3,
    3,    1,    1,    1,    1,    1,    1,    2,    1,    2,
    1,    1,
};
final static short yydefred[] = {                         0,
    2,    0,    0,    0,   16,  101,  102,    0,    0,   14,
    0,    6,    0,    0,    0,    0,    3,    0,    0,    1,
    5,    0,    0,   13,    0,    8,    0,    9,    0,    0,
    0,    0,    0,   42,    0,   33,    0,    0,    0,    0,
    0,    7,   15,    0,    0,    0,    0,   81,    0,   97,
   99,    0,    0,    0,    0,    0,   96,   92,    0,    0,
    0,    0,   94,   95,   31,    0,    0,    0,   32,   34,
   35,   36,   37,    0,    0,    0,    0,    0,   84,   85,
    0,    0,    0,    0,   98,  100,    0,    0,    0,    0,
    0,   67,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   44,    0,   45,   30,    0,    0,
    0,    0,    0,    0,   82,    0,    0,   61,   65,    0,
   63,    0,    0,   58,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   90,   91,    0,   47,
   50,    0,   49,   43,   29,   17,    0,    0,    0,    0,
    0,   53,    0,    0,    0,   55,    0,   51,   46,   48,
    0,    0,   26,    0,    0,    0,    0,    0,   60,   62,
   64,    0,   56,   24,   23,   27,    0,   25,   22,    0,
    0,   54,   20,    0,    0,   18,    0,   19,
};
final static short yydgoto[] = {                          2,
    3,    9,   20,   34,   11,   12,   13,   14,   15,   16,
  110,   35,  166,   77,   55,   56,   36,   37,   38,   39,
   40,  105,  142,  143,   57,   58,   59,   60,   61,   48,
   62,   63,   64,
};
final static short yysindex[] = {                      -243,
    0,    0, -150,  -34,    0,    0,    0, -209, -239,    0,
 -150,    0, -245,  -32,   39, -150,    0,   41,  494,    0,
    0, -148,   61,    0, -127,    0, -134,    0, -102,  -40,
  170,   90,  166,    0,  512,    0,  105,  107,  109,  112,
  137,    0,    0, -199,  -38,  148, -196,    0,  155,    0,
    0, -239,  -38, -115, -221,  -29,    0,    0,  -24,  -70,
  114,   16,    0,    0,    0,  -76, -190,  154,    0,    0,
    0,    0,    0, -110,  296,  231,  485,  -29,    0,    0,
  173,  -38, -120,   -2,    0,    0, -212,  -88,  -38,  -38,
  -38,    0,  -38,  -38,  -38,  -38,  -38,  -38,  -38,  -38,
  -38,  -38,  -38,  461,    0,  -76,    0,    0,  -51,  175,
  376,  174,  183,  313,    0,  206, -239,    0,    0, -239,
    0,  -82, -239,    0,   16,   16,  -70,  114,  -29,  -29,
  -29,  -29,  -29,  -29,  -29,  -29,    0,    0,  189,    0,
    0,  484,    0,    0,    0,    0,  -25,  -26,  -14,  -38,
  232,    0,   -3,    8, -142,    0,   18,    0,    0,    0,
  224,  235,    0,  -49,  236,  255,  -29,  -38,    0,    0,
    0,   42,    0,    0,    0,    0,  248,    0,    0,  254,
  278,    0,    0,   49,  269,    0,   52,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   54,    0,    0,    0, -220,    0,    0,  -68,    0,    0,
    0,    0,  190,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -174, -108,  252,  352,
    0,    0,    0,    0,    0,    0,    0,    0,  -39,    0,
    0,    0,    0,    0,    0,   55,    0,    0,    0,  311,
  274,  -15,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  247,    0,    0,
  301,    0,    0,  165,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  343,    0,    0,    9,   33,  333,  291,   77,   99,
  121,  143,  373,  395,  417,  439,    0,    0,  489,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  297,    0,    0,    0,
    0,  456,    0,    0,    0,    0,  517,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  321,  351,  130,    0,  329,  277,   67,    0,    0,
    0,  -43,  180,    0,   -9,   28,    5,    0,    0,    0,
    0,  -58,    0,  215,    0,  -71,  305,  268,  270,    0,
  108,    0,    0,
};
final static int YYTABLESIZE=795;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         47,
   76,   93,   93,   93,   93,   93,   54,   93,  107,  178,
  116,   25,    5,   89,    1,   90,   92,  164,   93,   93,
   93,   93,   93,   67,   17,   89,   24,   89,   89,   89,
  137,  138,  163,  114,   22,   11,   19,   11,  119,   69,
   11,   11,   89,   89,   89,   89,   89,  144,    5,   87,
   87,   87,   87,   87,   19,   11,    4,  102,   29,   11,
  120,  121,  103,   19,   30,  112,   87,   87,   87,   87,
   87,   31,   78,   88,   18,   88,   88,   88,   33,   23,
   69,   38,   75,   38,   25,  104,   80,   81,  106,   38,
   88,   88,   88,   88,   88,   78,   38,   26,   78,   28,
   38,  148,   38,   38,   25,    4,   38,    5,  141,   41,
    6,    7,   78,   78,   78,   78,   78,   74,   69,   42,
   74,  129,  130,  131,  132,  133,  134,  135,  136,    8,
   43,  171,   10,   19,   74,   74,   74,   74,   74,   75,
   10,   44,   75,   85,   86,   10,  141,   39,   65,   39,
    6,    7,  117,  118,   45,   39,   75,   75,   75,   75,
   75,   76,   39,   70,   76,   71,   39,   72,   39,   39,
   73,   98,   39,  100,  101,   99,   74,  167,   76,   76,
   76,   76,   76,   77,  123,  124,   77,   12,   79,   12,
  155,  156,   12,   12,   82,  167,  125,  126,   93,  104,
   77,   77,   77,   77,   77,   53,  145,   12,   66,   53,
   54,   12,  108,  115,   54,  146,   93,  149,   93,   49,
   50,   51,  150,   66,   93,   93,   93,   93,   93,   93,
   93,   93,   93,  176,  177,   93,   93,   93,   93,   93,
   89,   93,   89,   46,   93,   91,  152,  158,   89,   89,
   89,   89,   89,   89,   89,   89,   89,  162,  161,   89,
   89,   89,   89,   89,   87,   89,   87,   91,   89,  165,
  169,  168,   87,   87,   87,   87,   87,   87,   87,   87,
   87,  170,  174,   87,   87,   87,   87,   87,   88,   87,
   88,  173,   87,  175,  179,  180,   88,   88,   88,   88,
   88,   88,   88,   88,   88,   86,  183,   88,   88,   88,
   88,   88,  184,   88,   71,  182,   88,   71,  185,   78,
   78,   78,   78,   78,   78,  186,   78,  187,  188,    4,
   78,   70,   71,   78,   70,   53,   27,   28,   78,   21,
   54,   74,   74,   74,   74,   74,   74,  181,   74,   70,
  109,   69,   74,  111,   69,   74,  160,   84,  127,   83,
   74,    0,  128,   75,   75,   75,   75,   75,   75,   69,
   75,    0,    0,   68,   75,    0,   68,   75,   94,   95,
   96,   97,   75,    0,    0,   76,   76,   76,   76,   76,
   76,   68,   76,    0,    0,    0,   76,    0,    0,   76,
    0,   59,   83,    0,   76,   88,    0,   77,   77,   77,
   77,   77,   77,   80,   77,   53,   80,    0,   77,  147,
   54,   77,    0,   49,   50,   51,   77,   49,   50,   51,
   80,   80,   80,   80,   80,   72,   66,  122,   72,    0,
   66,   52,    0,   66,   66,   10,    0,   10,   66,    0,
   10,   10,   72,   72,   72,   72,   72,   73,    0,    0,
   73,    0,    0,    0,    0,   10,    0,  153,    0,   10,
  154,    0,    0,  157,   73,   73,   73,   73,   73,   79,
    0,    0,   79,    0,    0,    0,    4,    0,   29,    0,
    0,    0,    0,    0,   30,    0,   79,   79,   79,   79,
   79,   31,   86,    0,   86,  172,    0,   40,   33,   40,
   86,  113,    0,    0,   57,   40,    0,   86,    0,    0,
    0,   86,   40,   86,   86,    0,   40,   86,   40,   40,
    0,    0,   40,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   71,   71,    0,   71,    0,    0,    0,   71,
    0,    0,   71,   49,   50,   51,   83,   71,   83,   70,
   70,    0,   70,    0,   83,    0,   70,    0,    4,   70,
   29,   83,    0,    0,   70,   83,   30,   83,   83,    0,
   69,   83,   69,   31,    0,    0,   69,    0,    0,   69,
   33,    0,    0,  151,   69,    0,    0,    0,   59,    0,
   59,    0,   68,    0,   68,    0,   59,   41,   68,   41,
    0,   68,    0,   59,    0,   41,   68,   59,    0,   59,
   59,    0,   41,   59,    0,    0,   41,    0,   41,   41,
    0,    0,   41,   49,   50,   51,    0,   80,   80,   80,
   80,   80,   80,    0,   80,    0,    0,    0,   80,    0,
    0,   80,    0,    0,    0,    0,   80,    0,    0,   72,
   72,   72,   72,   72,   72,    0,   72,    0,    0,    0,
   72,    0,    0,   72,    0,    0,    0,    0,   72,    0,
    0,   73,   73,   73,   73,   73,   73,    0,   73,    0,
    0,    0,   73,    0,    0,   73,    0,    0,    0,    0,
   73,    0,    0,   79,   79,   79,   79,   79,   79,    0,
   79,   57,    0,   57,   79,    0,    4,   79,   29,   57,
    0,    0,   79,    0,   30,    0,   57,    0,    0,    0,
   57,   31,   57,   57,    0,  139,   57,  140,   33,    4,
    4,   29,   29,    0,   52,    0,   52,   30,   30,    4,
    0,   29,   52,    0,   31,   31,    0,   30,  139,   52,
  159,   33,   33,   52,   31,   52,   52,    4,    0,   29,
   32,   33,   21,    0,   21,   30,    0,    0,    0,    0,
   21,    0,   31,    0,    0,    0,    0,   21,   68,   33,
    0,    0,    0,    0,   21,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   44,   41,   42,   43,   44,   45,   45,   47,   67,   59,
   82,   44,  258,   43,  258,   45,   41,   44,   58,   59,
   60,   61,   62,   33,   59,   41,   59,   43,   44,   45,
  102,  103,   59,   77,  280,  256,  276,  258,   41,   35,
  261,  262,   58,   59,   60,   61,   62,  106,  258,   41,
  272,   43,   44,   45,  276,  276,  256,   42,  258,  280,
  273,  274,   47,  276,  264,   75,   58,   59,   60,   61,
   62,  271,   45,   41,    8,   43,   44,   45,  278,   13,
   76,  256,  282,  258,   44,  276,  283,  284,  279,  264,
   58,   59,   60,   61,   62,   41,  271,   59,   44,   59,
  275,  111,  277,  278,   44,  256,  281,  258,  104,  258,
  261,  262,   58,   59,   60,   61,   62,   41,  114,   59,
   44,   94,   95,   96,   97,   98,   99,  100,  101,  280,
  258,  274,    3,  276,   58,   59,   60,   61,   62,   41,
   11,  276,   44,  259,  260,   16,  142,  256,   59,  258,
  261,  262,  273,  274,  257,  264,   58,   59,   60,   61,
   62,   41,  271,   59,   44,   59,  275,   59,  277,  278,
   59,   58,  281,   60,   61,   62,   40,  150,   58,   59,
   60,   61,   62,   41,  273,  274,   44,  256,   41,  258,
  273,  274,  261,  262,   40,  168,   89,   90,  269,  276,
   58,   59,   60,   61,   62,   40,  258,  276,   44,   40,
   45,  280,   59,   41,   45,   41,  256,   44,  258,  258,
  259,  260,   40,   59,  264,  265,  266,  267,  268,  269,
  270,  271,  272,  283,  284,  275,  276,  277,  278,  279,
  256,  281,  258,  284,  284,  270,   41,   59,  264,  265,
  266,  267,  268,  269,  270,  271,  272,  284,  284,  275,
  276,  277,  278,  279,  256,  281,  258,  270,  284,  284,
  274,   40,  264,  265,  266,  267,  268,  269,  270,  271,
  272,  274,   59,  275,  276,  277,  278,  279,  256,  281,
  258,  274,  284,   59,   59,   41,  264,  265,  266,  267,
  268,  269,  270,  271,  272,   59,   59,  275,  276,  277,
  278,  279,   59,  281,   41,  274,  284,   44,   41,  265,
  266,  267,  268,  269,  270,  277,  272,   59,  277,  276,
  276,   41,   59,  279,   44,   40,   16,   41,  284,   11,
   45,  265,  266,  267,  268,  269,  270,  168,  272,   59,
   74,   41,  276,   58,   44,  279,  142,   53,   91,   59,
  284,   -1,   93,  265,  266,  267,  268,  269,  270,   59,
  272,   -1,   -1,   41,  276,   -1,   44,  279,  265,  266,
  267,  268,  284,   -1,   -1,  265,  266,  267,  268,  269,
  270,   59,  272,   -1,   -1,   -1,  276,   -1,   -1,  279,
   -1,   59,   52,   -1,  284,   55,   -1,  265,  266,  267,
  268,  269,  270,   41,  272,   40,   44,   -1,  276,   44,
   45,  279,   -1,  258,  259,  260,  284,  258,  259,  260,
   58,   59,   60,   61,   62,   41,  272,   87,   44,   -1,
  276,  272,   -1,  279,  279,  256,   -1,  258,  284,   -1,
  261,  262,   58,   59,   60,   61,   62,   41,   -1,   -1,
   44,   -1,   -1,   -1,   -1,  276,   -1,  117,   -1,  280,
  120,   -1,   -1,  123,   58,   59,   60,   61,   62,   41,
   -1,   -1,   44,   -1,   -1,   -1,  256,   -1,  258,   -1,
   -1,   -1,   -1,   -1,  264,   -1,   58,   59,   60,   61,
   62,  271,  256,   -1,  258,  155,   -1,  256,  278,  258,
  264,  281,   -1,   -1,   59,  264,   -1,  271,   -1,   -1,
   -1,  275,  271,  277,  278,   -1,  275,  281,  277,  278,
   -1,   -1,  281,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  269,  270,   -1,  272,   -1,   -1,   -1,  276,
   -1,   -1,  279,  258,  259,  260,  256,  284,  258,  269,
  270,   -1,  272,   -1,  264,   -1,  276,   -1,  256,  279,
  258,  271,   -1,   -1,  284,  275,  264,  277,  278,   -1,
  270,  281,  272,  271,   -1,   -1,  276,   -1,   -1,  279,
  278,   -1,   -1,  281,  284,   -1,   -1,   -1,  256,   -1,
  258,   -1,  270,   -1,  272,   -1,  264,  256,  276,  258,
   -1,  279,   -1,  271,   -1,  264,  284,  275,   -1,  277,
  278,   -1,  271,  281,   -1,   -1,  275,   -1,  277,  278,
   -1,   -1,  281,  258,  259,  260,   -1,  265,  266,  267,
  268,  269,  270,   -1,  272,   -1,   -1,   -1,  276,   -1,
   -1,  279,   -1,   -1,   -1,   -1,  284,   -1,   -1,  265,
  266,  267,  268,  269,  270,   -1,  272,   -1,   -1,   -1,
  276,   -1,   -1,  279,   -1,   -1,   -1,   -1,  284,   -1,
   -1,  265,  266,  267,  268,  269,  270,   -1,  272,   -1,
   -1,   -1,  276,   -1,   -1,  279,   -1,   -1,   -1,   -1,
  284,   -1,   -1,  265,  266,  267,  268,  269,  270,   -1,
  272,  256,   -1,  258,  276,   -1,  256,  279,  258,  264,
   -1,   -1,  284,   -1,  264,   -1,  271,   -1,   -1,   -1,
  275,  271,  277,  278,   -1,  275,  281,  277,  278,  256,
  256,  258,  258,   -1,  256,   -1,  258,  264,  264,  256,
   -1,  258,  264,   -1,  271,  271,   -1,  264,  275,  271,
  277,  278,  278,  275,  271,  277,  278,  256,   -1,  258,
  277,  278,  256,   -1,  258,  264,   -1,   -1,   -1,   -1,
  264,   -1,  271,   -1,   -1,   -1,   -1,  271,  277,  278,
   -1,   -1,   -1,   -1,  278,
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
"programa : nombre_programa bloque_sentencias_declarativas bloque_sentencias_ejecutables",
"nombre_programa : ID",
"sentencia_erronea : error ';'",
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
"sentencia_declarativa : sentencia_erronea",
"lista_de_variables : lista_de_variables ',' ID",
"lista_de_variables : ID",
"header_funcion : tipo FUNC ID '(' parametro ')'",
"funcion : header_funcion bloque_sentencias_declarativas BEGIN sentencias_ejecutables RETURN '(' retorno ')' ';' END",
"funcion : header_funcion bloque_sentencias_declarativas BEGIN pre_condicion sentencias_ejecutables RETURN '(' retorno ')' ';' END",
"pre_condicion : PRE ':' condicion ',' CTE_STRING ';'",
"pre_condicion : PRE ':' condicion ',' CTE_STRING",
"pre_condicion : PRE condicion ',' CTE_STRING ';'",
"pre_condicion : PRE ':' condicion CTE_STRING ';'",
"pre_condicion : PRE ':' ',' CTE_STRING ';'",
"pre_condicion : PRE ':' condicion ',' ';'",
"pre_condicion : PRE ':' condicion ';'",
"pre_condicion : PRE ':' condicion ',' WRONG_STRING",
"retorno : expresion",
"parametro : tipo ID",
"bloque_sentencias_ejecutables : BEGIN sentencias_ejecutables END ';'",
"bloque_sentencias_ejecutables : BEGIN END ';'",
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
"sentencia_ejecutable : sentencia_erronea",
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
"llamado_funcion : ID '(' factor ')'",
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
"to_print : '(' CTE_STRING ')'",
"to_print : '(' CTE_STRING",
"to_print : CTE_STRING ')'",
"to_print : '(' WRONG_STRING",
"asignacion : ID ASIG expresion",
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

//#line 158 "gramatica.y"
  private Text text;
  private LexicAnalyzer lexicAnalyzer;
  private StructureUtilities su;
  private List<String> idList;
  
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
  
  private List<String> concatenateScope(List<String> list, String scope){
	  List<String> result = new ArrayList<>();
	  for(String s : list)
		  result.add(s + "." + scope);
	  return result;
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
//#line 595 "Parser.java"
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
//#line 14 "gramatica.y"
{showResults();}
break;
case 2:
//#line 16 "gramatica.y"
{CurrentScope.addScope(val_peek(0).sval);System.out.println(CurrentScope.getScope());}
break;
case 3:
//#line 18 "gramatica.y"
{su.addError("Se encontro un error no considerado por la gramatica", "Sintáctica");}
break;
case 7:
//#line 25 "gramatica.y"
{su.addCodeStructure("Declaracion de variables " + val_peek(2).sval);su.changeSTValues(idList,val_peek(2).sval,"VARIABLE");su.changeSTKeys(idList,concatenateScope(idList,CurrentScope.getScope()));}
break;
case 9:
//#line 27 "gramatica.y"
{su.addCodeStructure("Declaracion de variables de tipo FUNC");su.changeSTValues(idList,"FUNC","VARIABLE");su.changeSTKeys(idList,concatenateScope(idList,CurrentScope.getScope()));}
break;
case 10:
//#line 28 "gramatica.y"
{su.addError(" falta ; luego de la sentencia", "Sintáctica");}
break;
case 11:
//#line 29 "gramatica.y"
{su.addError(" falta ; luego de la sentencia", "Sintáctica");}
break;
case 12:
//#line 30 "gramatica.y"
{su.addError(" falta ; luego de la sentencia", "Sintáctica");}
break;
case 13:
//#line 31 "gramatica.y"
{su.addError(" la/las variables no tienen tipo", "Sintáctica");}
break;
case 15:
//#line 34 "gramatica.y"
{idList.add(val_peek(0).sval);}
break;
case 16:
//#line 35 "gramatica.y"
{idList = new ArrayList<>(); idList.add(val_peek(0).sval);}
break;
case 17:
//#line 37 "gramatica.y"
{su.addCodeStructure("Declaracion de la funcion " + val_peek(3).sval);su.changeSTValues(val_peek(3).sval,val_peek(5).sval,"FUNC");CurrentScope.addScope(val_peek(3).sval);System.out.println(CurrentScope.getScope());su.changeSTKey(val_peek(1).sval,val_peek(1).sval + "." + CurrentScope.getScope());}
break;
case 18:
//#line 39 "gramatica.y"
{CurrentScope.deleteScope();}
break;
case 19:
//#line 40 "gramatica.y"
{CurrentScope.deleteScope();}
break;
case 21:
//#line 44 "gramatica.y"
{su.addError(" falta ; luego de PRE", "Sintáctica");}
break;
case 22:
//#line 45 "gramatica.y"
{su.addError(" falta : luego de PRE", "Sintáctica");}
break;
case 23:
//#line 46 "gramatica.y"
{su.addError(" falta , luego de la condicion en PRE", "Sintáctica");}
break;
case 24:
//#line 47 "gramatica.y"
{su.addError(" falta falta la condicion para la sentencia PRE", "Sintáctica");}
break;
case 25:
//#line 48 "gramatica.y"
{su.addError(" falta la cadena de caracteres luego de la ,", "Sintáctica");}
break;
case 26:
//#line 49 "gramatica.y"
{su.addError(" falta , y la cadena de caracteres luego de la condicion", "Sintáctica");}
break;
case 27:
//#line 50 "gramatica.y"
{su.addError("El STRING es incorrecto", "Sintáctica");}
break;
case 29:
//#line 54 "gramatica.y"
{su.changeSTValues(val_peek(0).sval,val_peek(1).sval,"PARAM");yyval.sval = val_peek(0).sval;}
break;
case 34:
//#line 63 "gramatica.y"
{su.addCodeStructure("ASIGNACION");}
break;
case 35:
//#line 64 "gramatica.y"
{su.addCodeStructure("SENTENCIA IF");}
break;
case 36:
//#line 65 "gramatica.y"
{su.addCodeStructure("SENTENCIA PRINT");}
break;
case 37:
//#line 66 "gramatica.y"
{su.addCodeStructure("WHILE");}
break;
case 38:
//#line 67 "gramatica.y"
{su.addError(" falta ; luego de la sentencia", "Sintáctica");}
break;
case 39:
//#line 68 "gramatica.y"
{su.addError(" falta ; luego de la sentencia", "Sintáctica");}
break;
case 40:
//#line 69 "gramatica.y"
{su.addError(" falta ; luego de la sentencia", "Sintáctica");}
break;
case 41:
//#line 70 "gramatica.y"
{su.addError(" falta ; luego de la sentencia", "Sintáctica");}
break;
case 44:
//#line 75 "gramatica.y"
{su.addError(" falta la condicion de corte del WHILE", "Sintáctica");}
break;
case 45:
//#line 76 "gramatica.y"
{su.addError(" falta DO en la sentencia WHILE", "Sintáctica");}
break;
case 52:
//#line 86 "gramatica.y"
{su.addError(" falta ; luego de la sentencia", "Sintáctica");}
break;
case 53:
//#line 89 "gramatica.y"
{su.addCodeStructure("LLamado a funcion " + val_peek(3).sval);}
break;
case 56:
//#line 94 "gramatica.y"
{su.addError(" falta THEN en la sentencia IF", "Sintáctica");}
break;
case 57:
//#line 95 "gramatica.y"
{su.addError(" falta ENDIF en la sentencia IF", "Sintáctica");}
break;
case 58:
//#line 96 "gramatica.y"
{su.addError(" falta THEN en la sentencia IF", "Sintáctica");}
break;
case 59:
//#line 97 "gramatica.y"
{su.addError(" falta ENDIF en la sentencia IF", "Sintáctica");}
break;
case 60:
//#line 98 "gramatica.y"
{su.addError(" falta la condicion en la sentencia IF", "Sintáctica");}
break;
case 61:
//#line 99 "gramatica.y"
{su.addError(" falta la condicion en la sentencia IF", "Sintáctica");}
break;
case 62:
//#line 100 "gramatica.y"
{su.addError(" falta bloque de sentencias luego del THEN", "Sintáctica");}
break;
case 63:
//#line 101 "gramatica.y"
{su.addError(" falta bloque de sentencias luego del THEN", "Sintáctica");}
break;
case 64:
//#line 102 "gramatica.y"
{su.addError(" falta bloque de sentencias luego del ELSE", "Sintáctica");}
break;
case 66:
//#line 105 "gramatica.y"
{su.addError(" falta parentesis de cierre en la condicion", "Sintáctica");}
break;
case 67:
//#line 106 "gramatica.y"
{su.addError(" falta parentesis de apertura en la condicion", "Sintáctica");}
break;
case 68:
//#line 108 "gramatica.y"
{su.addCodeStructure("Se detecta una condicion AND");}
break;
case 70:
//#line 111 "gramatica.y"
{su.addCodeStructure("Se detecta una condicion OR");}
break;
case 72:
//#line 114 "gramatica.y"
{su.addCodeStructure("Se detecta una condicion >");}
break;
case 73:
//#line 115 "gramatica.y"
{su.addCodeStructure("Se detecta una condicion <");}
break;
case 74:
//#line 116 "gramatica.y"
{su.addCodeStructure("Se detecta una condicion >=");}
break;
case 75:
//#line 117 "gramatica.y"
{su.addCodeStructure("Se detecta una condicion <=");}
break;
case 76:
//#line 118 "gramatica.y"
{su.addCodeStructure("Se detecta una condicion ==");}
break;
case 77:
//#line 119 "gramatica.y"
{su.addCodeStructure("Se detecta una condicion <>");}
break;
case 79:
//#line 121 "gramatica.y"
{su.addError("No se reconoce el comparador", "Sintáctica");}
break;
case 80:
//#line 122 "gramatica.y"
{su.addError("No se reconoce el comparador", "Sintáctica");}
break;
case 83:
//#line 128 "gramatica.y"
{su.addError(" falta parentesis de cierre para la sentencia PRINT", "Sintáctica");}
break;
case 84:
//#line 129 "gramatica.y"
{su.addError(" falta parentesis de apertura para la sentencia PRINT", "Sintáctica");}
break;
case 85:
//#line 130 "gramatica.y"
{su.addError("STRING mal escrito", "Sintáctica");}
break;
case 87:
//#line 135 "gramatica.y"
{su.addCodeStructure("Se detecta una suma");}
break;
case 88:
//#line 136 "gramatica.y"
{su.addCodeStructure("Se detecta una resta");}
break;
case 89:
//#line 137 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 90:
//#line 139 "gramatica.y"
{su.addCodeStructure("Se detecta una multiplicacion");}
break;
case 91:
//#line 140 "gramatica.y"
{su.addCodeStructure("Se detecta una division");}
break;
case 92:
//#line 141 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 93:
//#line 143 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 95:
//#line 145 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 97:
//#line 148 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 98:
//#line 149 "gramatica.y"
{su.addError("UINT no puede ser negativo", "Sintáctica");}
break;
case 99:
//#line 151 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 100:
//#line 152 "gramatica.y"
{yyval.sval = val_peek(0).sval; setToNegative(val_peek(0).sval);}
break;
case 101:
//#line 154 "gramatica.y"
{yyval.sval = "UINT";}
break;
case 102:
//#line 155 "gramatica.y"
{yyval.sval = "DOUBLE";}
break;
//#line 1032 "Parser.java"
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
