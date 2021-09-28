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
   14,   14,   14,   14,   14,   14,   14,   18,   18,   18,
   19,   19,   20,   20,   21,   21,   21,   22,   22,   22,
   16,   16,   16,   16,   16,   16,   16,   16,   16,   16,
   16,   12,   12,   12,   23,   23,   24,   24,   25,   25,
   25,   25,   25,   25,   25,   17,   17,   17,   15,   13,
   13,   13,   13,   26,   26,   26,   27,   27,   27,   27,
   28,   28,   29,   29,    5,    5,
};
final static short yylen[] = {                            2,
    2,    1,    2,    1,    3,    2,    3,    2,    1,    2,
    2,    3,    1,   15,   16,    6,    5,    5,    5,    5,
    5,    4,    3,    1,    2,    3,    2,    2,    1,    2,
    2,    2,    2,    1,    1,    1,    1,    4,    3,    3,
    3,    2,    2,    1,    1,    2,    1,    4,    3,    3,
    7,    5,    6,    6,    4,    4,    6,    4,    6,    4,
    6,    3,    2,    2,    3,    1,    3,    1,    3,    3,
    3,    3,    3,    3,    1,    4,    3,    3,    3,    3,
    3,    1,    3,    3,    3,    1,    1,    1,    1,    1,
    1,    2,    1,    2,    1,    1,
};
final static short yydefred[] = {                         0,
   13,   95,   96,    0,    0,    0,    0,    4,    0,    0,
    0,    0,    0,    1,    3,    0,    0,   11,    0,    6,
    7,    0,    0,    0,   27,    0,    0,   29,    0,    0,
    0,    0,    0,    5,   12,    0,    0,    0,    0,   91,
   93,    0,    0,    0,    0,    0,   90,    0,    0,    0,
    0,   86,   88,   89,    0,    0,   26,   28,   30,   31,
   32,   33,    0,    0,   77,    0,    0,    0,    0,    0,
    0,   92,   94,    0,    0,    0,    0,    0,    0,   64,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   39,    0,   40,    0,   76,   50,    0,   25,   49,    0,
   58,   62,    0,   60,    0,    0,   55,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   84,   85,
    0,   42,   45,    0,   44,   38,    0,   48,    0,    0,
    0,   52,    0,   46,   41,   43,    0,   57,   59,   61,
    0,   53,    0,   51,    0,    0,    0,    0,    0,    0,
    0,    0,   23,    0,    0,    0,    0,    0,    0,    0,
   22,    0,    0,    0,    0,    0,   20,   19,    0,   21,
   18,    0,    0,   16,    0,    0,   14,    0,   15,
};
final static short yydgoto[] = {                          5,
    6,   14,    7,    8,    9,   10,   11,   69,   27,  164,
  147,   45,   46,   28,   29,   30,   31,   32,   91,  124,
  125,   47,   48,   49,   50,   51,   52,   53,   54,
};
final static short yysindex[] = {                      -225,
    0,    0,    0, -245,    0, -254, -225,    0, -246,  -24,
  -29,  -13,  294,    0,    0, -202,  -12,    0, -194,    0,
    0, -184,  -31,    9,    0,   79,  317,    0,   28,   45,
   63,   84,  109,    0,    0,  -40,   93,  -86,   30,    0,
    0, -254,  -40, -165, -247,  -35,    0,  -27,  -87,  288,
   21,    0,    0,    0,  -93, -148,    0,    0,    0,    0,
    0,    0, -181,  -35,    0,  151, -179,  -64,  155, -161,
  -26,    0,    0, -168, -144,  -40,  -40,  -40,  -40,    0,
  -40,  -40,  -40,  -40,  -40,  -40,  -40,  -40,  -40,  223,
    0,  -93,    0,  159,    0,    0,  161,    0,    0, -254,
    0,    0, -254,    0, -114, -254,    0,   21,   21,   21,
  -87,  288,  -35,  -35,  -35,  -35,  -35,  -35,    0,    0,
  144,    0,    0,  264,    0,    0, -225,    0,  -66,  -60,
 -235,    0,  -18,    0,    0,    0,  -67,    0,    0,    0,
    6,    0, -193,    0,  422, -155,  208,  174,   57,  201,
  253,  167,    0,   41,   66,   65,  -40,  295,  282,  293,
    0,  -52,  296,  324,  -35,  -40,    0,    0,  308,    0,
    0,  313,  334,    0,  105,  325,    0,  112,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,  116,    0,    0,    0,
  -95,  188,    0,    0,    0,    0,  235,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -131, -113,
  -80,  -65,    0,    0,    0,    0,    0,    0,  -41,    0,
    0,    0,    0,    0,    0,   77,    0,    0,  344,  310,
  -17,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  207,    0,  231,    0,    0,    0,    0,
  198,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  246,    0,    0,    7,   31,   55,
  400,  327,   94,  111,  128,  145,  375,  392,    0,    0,
  286,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  255,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  358,    0,    0,    0,  320,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  274,  431,    0,  395,  -23,   75,    0,   25,  -36,  239,
    0,   12,  -25,   -4,    0,    0,    0,    0,  -39,    0,
  285,    0,  363,  339,  341,   98,   91,    0,    0,
};
final static int YYTABLESIZE=682;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         87,
   87,   87,   87,   87,   44,   87,  170,   77,   38,   78,
   64,    1,    1,   80,  102,   68,   93,   87,   87,   19,
   87,   13,   58,   82,   74,   82,   82,   82,   13,   20,
   19,   19,    1,   16,   18,    2,    3,   56,  140,   68,
   13,   82,   82,   68,   82,   21,   34,   83,   43,   83,
   83,   83,  126,   44,    4,   33,  113,  114,  115,  116,
  117,  118,   88,   35,   22,   83,   83,   89,   83,   67,
   23,   80,   36,   80,   80,   80,   96,   24,   12,    2,
    3,    2,    3,   17,   26,  123,   59,   94,  145,   80,
   80,   97,   80,   72,   73,   81,   43,   81,   81,   81,
  154,   44,   22,   60,  103,  104,  146,   13,   23,  162,
  152,  100,  101,   81,   81,   24,   81,   75,   43,  123,
   75,   61,   26,   44,  161,  151,   34,   90,  106,  107,
   92,  165,   34,   65,   71,   75,   75,   71,   75,   34,
  165,   58,   62,   34,   35,   34,   34,   58,   63,   34,
   35,   72,   71,   71,   72,   71,  150,   35,  131,  132,
  155,   35,    9,   35,   35,    9,    9,   35,   73,   72,
   72,   73,   72,  108,  109,  110,   66,   36,  119,  120,
    9,   81,   90,   36,    9,   74,   73,   73,   74,   73,
   36,   95,   37,   98,   36,   99,   36,   36,   37,  127,
   36,  128,  134,   74,   74,   37,   74,  138,  143,   37,
  169,   37,   37,  139,   87,   37,   87,   39,   40,   41,
   76,   87,   87,   87,   87,   87,   87,   87,   87,   87,
   87,   37,  153,   87,   87,   87,   87,   87,   82,   87,
   82,   63,   79,   79,  156,   82,   82,   82,   82,   82,
   82,   82,   82,   82,   82,  142,   63,   82,   82,   82,
   82,   82,   83,   82,   83,   79,   39,   40,   41,   83,
   83,   83,   83,   83,   83,   83,   83,   83,   83,  144,
   42,   83,   83,   83,   83,   83,   80,   83,   80,   78,
    2,    3,  157,   80,   80,   80,   80,   80,   80,   80,
   80,   80,   80,  159,   56,   80,   80,   80,   80,   80,
   81,   80,   81,   54,   39,   40,   41,   81,   81,   81,
   81,   81,   81,   81,   81,   81,   81,  163,  160,   81,
   81,   81,   81,   81,  166,   81,   39,   40,   41,   75,
  167,   75,   75,   75,   75,   75,   75,   87,   75,   86,
   68,  168,   75,   68,  171,   75,   71,   55,   71,   71,
   71,   71,   71,   71,  172,   71,  174,   67,   68,   71,
   67,  175,   71,   72,  176,   72,   72,   72,   72,   72,
   72,  177,   72,  178,   66,   67,   72,   66,  179,   72,
   73,    2,   73,   73,   73,   73,   73,   73,   24,   73,
  137,   15,   66,   73,  173,   71,   73,   74,  136,   74,
   74,   74,   74,   74,   74,   69,   74,  111,   69,    0,
   74,  112,    0,   74,   22,    0,    0,    0,    0,    0,
   23,    0,   70,   69,   69,   70,   69,   24,    0,    0,
   65,    0,    0,   65,   26,   10,    0,  158,   10,   10,
   70,   70,    0,   70,    0,    0,    0,    0,   65,    0,
   63,   43,    0,   10,   79,   22,   44,   10,    0,   63,
   79,   23,   70,   63,    0,   75,   63,   79,   24,  149,
   22,   79,    0,   79,   79,   26,   23,   79,   78,    0,
    0,    0,    8,   24,   78,    8,    8,  121,    0,  122,
   26,   78,    0,   56,  105,   78,    0,   78,   78,   56,
    8,   78,   54,    0,    8,    0,   56,    0,   54,    0,
   56,   22,   56,   56,    0,   54,   56,   23,    0,   54,
  129,   54,   54,  130,   24,   54,  133,    0,  121,    0,
  135,   26,    0,   47,    0,    0,    0,    0,    0,   47,
    0,   22,   82,   83,   84,   85,   47,   23,    0,    0,
   47,  141,   47,   47,   24,    0,    0,    0,    0,    0,
   25,   26,   68,    0,   22,    0,    0,   17,   68,   68,
   23,   68,    0,   17,    0,   68,    0,   24,   68,   67,
   17,    0,    0,   57,   26,   67,   67,   17,   67,    0,
    0,    0,   67,    0,    0,   67,   66,    0,    0,    0,
    0,    0,    0,   66,    0,   66,    0,    0,    0,   66,
    0,    0,   66,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   69,    0,   69,
   69,   69,   69,   69,   69,    0,   69,    0,    0,    0,
   69,    0,    0,   69,   70,    0,   70,   70,   70,   70,
   70,   70,   65,   70,    0,    0,    0,   70,    0,   65,
   70,   65,    0,    0,    0,   65,    0,  148,   65,   39,
   40,   41,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   45,   47,   59,   43,   40,   45,
   36,  258,  258,   41,   41,   39,   56,   59,   60,   44,
   62,  276,   27,   41,  272,   43,   44,   45,  276,   59,
   44,   44,  258,  280,   59,  261,  262,   26,  274,   63,
  276,   59,   60,   67,   62,   59,   59,   41,   40,   43,
   44,   45,   92,   45,  280,  258,   82,   83,   84,   85,
   86,   87,   42,  258,  258,   59,   60,   47,   62,   40,
  264,   41,  257,   43,   44,   45,  256,  271,    4,  261,
  262,  261,  262,    9,  278,   90,   59,   63,  282,   59,
   60,   67,   62,  259,  260,   41,   40,   43,   44,   45,
   44,   45,  258,   59,  273,  274,  143,  276,  264,   44,
  147,  273,  274,   59,   60,  271,   62,   41,   40,  124,
   44,   59,  278,   45,   59,  281,  258,  276,  273,  274,
  279,  157,  264,   41,   41,   59,   60,   44,   62,  271,
  166,  146,   59,  275,  258,  277,  278,  152,   40,  281,
  264,   41,   59,   60,   44,   62,  145,  271,  273,  274,
  149,  275,  258,  277,  278,  261,  262,  281,   41,   59,
   60,   44,   62,   76,   77,   78,  263,  258,   88,   89,
  276,  269,  276,  264,  280,   41,   59,   60,   44,   62,
  271,   41,  258,  258,  275,   41,  277,  278,  264,   41,
  281,   41,   59,   59,   60,  271,   62,  274,  276,  275,
  263,  277,  278,  274,  256,  281,  258,  258,  259,  260,
  256,  263,  264,  265,  266,  267,  268,  269,  270,  271,
  272,  263,   59,  275,  276,  277,  278,  279,  256,  281,
  258,   44,  270,  270,   44,  263,  264,  265,  266,  267,
  268,  269,  270,  271,  272,  274,   59,  275,  276,  277,
  278,  279,  256,  281,  258,   59,  258,  259,  260,  263,
  264,  265,  266,  267,  268,  269,  270,  271,  272,  274,
  272,  275,  276,  277,  278,  279,  256,  281,  258,   59,
  261,  262,   40,  263,  264,  265,  266,  267,  268,  269,
  270,  271,  272,  263,   59,  275,  276,  277,  278,  279,
  256,  281,  258,   59,  258,  259,  260,  263,  264,  265,
  266,  267,  268,  269,  270,  271,  272,  263,  263,  275,
  276,  277,  278,  279,   40,  281,  258,  259,  260,  263,
   59,  265,  266,  267,  268,  269,  270,   60,  272,   62,
   41,   59,  276,   44,   59,  279,  263,  279,  265,  266,
  267,  268,  269,  270,   41,  272,   59,   41,   59,  276,
   44,   59,  279,  263,   41,  265,  266,  267,  268,  269,
  270,  277,  272,   59,   41,   59,  276,   44,  277,  279,
  263,  276,  265,  266,  267,  268,  269,  270,   41,  272,
  127,    7,   59,  276,  166,   43,  279,  263,  124,  265,
  266,  267,  268,  269,  270,   41,  272,   79,   44,   -1,
  276,   81,   -1,  279,  258,   -1,   -1,   -1,   -1,   -1,
  264,   -1,   41,   59,   60,   44,   62,  271,   -1,   -1,
   41,   -1,   -1,   44,  278,  258,   -1,  281,  261,  262,
   59,   60,   -1,   62,   -1,   -1,   -1,   -1,   59,   -1,
  263,   40,   -1,  276,  258,  258,   45,  280,   -1,  272,
  264,  264,   42,  276,   -1,   45,  279,  271,  271,   58,
  258,  275,   -1,  277,  278,  278,  264,  281,  258,   -1,
   -1,   -1,  258,  271,  264,  261,  262,  275,   -1,  277,
  278,  271,   -1,  258,   74,  275,   -1,  277,  278,  264,
  276,  281,  258,   -1,  280,   -1,  271,   -1,  264,   -1,
  275,  258,  277,  278,   -1,  271,  281,  264,   -1,  275,
  100,  277,  278,  103,  271,  281,  106,   -1,  275,   -1,
  277,  278,   -1,  258,   -1,   -1,   -1,   -1,   -1,  264,
   -1,  258,  265,  266,  267,  268,  271,  264,   -1,   -1,
  275,  131,  277,  278,  271,   -1,   -1,   -1,   -1,   -1,
  277,  278,  263,   -1,  258,   -1,   -1,  258,  269,  270,
  264,  272,   -1,  264,   -1,  276,   -1,  271,  279,  263,
  271,   -1,   -1,  277,  278,  269,  270,  278,  272,   -1,
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

//#line 142 "gramatica.y"
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
    //su.addError(err);
  }
//#line 525 "Parser.java"
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
//#line 33 "gramatica.y"
{su.addError(" falta ; luego de PRE");}
break;
case 18:
//#line 34 "gramatica.y"
{su.addError(" falta : luego de PRE");}
break;
case 19:
//#line 35 "gramatica.y"
{su.addError(" falta , luego de la condicion en PRE");}
break;
case 20:
//#line 36 "gramatica.y"
{su.addError(" falta falta la condicion para la sentencia PRE");}
break;
case 21:
//#line 37 "gramatica.y"
{su.addError(" falta la cadena de caracteres luego de la ,");}
break;
case 22:
//#line 38 "gramatica.y"
{su.addError(" falta , y la cadena de caracteres luego de la condicion");}
break;
case 23:
//#line 39 "gramatica.y"
{su.addError(" la sentencia PRE no es correcta sintacticamente");}
break;
case 30:
//#line 52 "gramatica.y"
{System.err.println("ASIGNACION");}
break;
case 31:
//#line 53 "gramatica.y"
{System.err.println("CLAUSULA SELECCION");}
break;
case 32:
//#line 54 "gramatica.y"
{System.err.println("PRINT");}
break;
case 33:
//#line 55 "gramatica.y"
{System.err.println("WHILE");}
break;
case 34:
//#line 56 "gramatica.y"
{su.addError(" falta ; luego de la sentencia");}
break;
case 35:
//#line 57 "gramatica.y"
{su.addError(" falta ; luego de la sentencia");}
break;
case 36:
//#line 58 "gramatica.y"
{su.addError(" falta ; luego de la sentencia");}
break;
case 37:
//#line 59 "gramatica.y"
{su.addError(" falta ; luego de la sentencia");}
break;
case 39:
//#line 63 "gramatica.y"
{su.addError(" falta la condicion de corte del WHILE");}
break;
case 40:
//#line 64 "gramatica.y"
{su.addError(" falta DO en la sentencia WHILE");}
break;
case 47:
//#line 74 "gramatica.y"
{su.addError(" falta ; luego de la sentencia");}
break;
case 49:
//#line 78 "gramatica.y"
{su.addError("falta parentesis de apertura para el llamado a la funcion " + val_peek(2).sval);}
break;
case 50:
//#line 79 "gramatica.y"
{su.addError("la llamada a la funcion " + val_peek(2).sval + " no es correcta sintacticamente");}
break;
case 53:
//#line 84 "gramatica.y"
{su.addError(" falta THEN en la sentencia IF");}
break;
case 54:
//#line 85 "gramatica.y"
{su.addError(" falta ENDIF en la sentencia IF");}
break;
case 55:
//#line 86 "gramatica.y"
{su.addError(" falta THEN en la sentencia IF");}
break;
case 56:
//#line 87 "gramatica.y"
{su.addError(" falta ENDIF en la sentencia IF");}
break;
case 57:
//#line 88 "gramatica.y"
{su.addError(" falta la condicion en la sentencia IF");}
break;
case 58:
//#line 89 "gramatica.y"
{su.addError(" falta la condicion en la sentencia IF");}
break;
case 59:
//#line 90 "gramatica.y"
{su.addError(" falta bloque de sentencias luego del THEN");}
break;
case 60:
//#line 91 "gramatica.y"
{su.addError(" falta bloque de sentencias luego del THEN");}
break;
case 61:
//#line 92 "gramatica.y"
{su.addError(" falta bloque de sentencias luego del ELSE");}
break;
case 63:
//#line 95 "gramatica.y"
{su.addError(" falta parentesis de cierre en la condicion");}
break;
case 64:
//#line 96 "gramatica.y"
{su.addError(" falta parentesis de apertura en la condicion");}
break;
case 77:
//#line 113 "gramatica.y"
{su.addError(" falta parentesis de apertura para la sentencia PRINT");}
break;
case 78:
//#line 114 "gramatica.y"
{su.addError(" falta parentesis de cierre para la sentencia PRINT");}
break;
case 79:
//#line 116 "gramatica.y"
{}
break;
case 82:
//#line 120 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 83:
//#line 121 "gramatica.y"
{su.addError(" no se reconoce el operador");}
break;
case 86:
//#line 125 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 87:
//#line 127 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 89:
//#line 129 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 91:
//#line 132 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 92:
//#line 133 "gramatica.y"
{su.addError("UINT no puede ser negativo");}
break;
case 93:
//#line 135 "gramatica.y"
{yyval.sval = val_peek(0).sval;}
break;
case 94:
//#line 136 "gramatica.y"
{yyval.sval = val_peek(0).sval; setToNegative(val_peek(0).sval);}
break;
//#line 866 "Parser.java"
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
