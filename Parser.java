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






//#line 1 "hoc5.y"

import java.lang.Math;
import java.io.*;
import java.util.StringTokenizer;
import java.util.ArrayList;
import javax.swing.*;
//#line 24 "Parser.java"




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
public final static short BLTIN=257;
public final static short IF=258;
public final static short ELSE=259;
public final static short WHILE=260;
public final static short FOR=261;
public final static short PRINT=262;
public final static short EQ=263;
public final static short NEQ=264;
public final static short GT=265;
public final static short GE=266;
public final static short LT=267;
public final static short LE=268;
public final static short NUMBER=269;
public final static short VAR=270;
public final static short VARCRD=271;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    0,    0,    0,    1,    2,    2,
    2,    2,    2,    2,    2,    2,    2,    7,    6,    9,
   10,   11,   12,    8,   13,   13,   13,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    4,   14,   14,   14,    5,    5,    5,
   16,   16,   16,   15,   15,   15,   15,   15,   15,   15,
   15,   15,   15,
};
final static short yylen[] = {                            2,
    0,    2,    3,    3,    3,    3,    3,    3,    1,    2,
    1,    1,    4,    4,    7,   10,    3,    3,    1,    1,
    1,    1,    1,    0,    0,    2,    2,    1,    1,    1,
    4,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    2,    1,    1,    3,    3,    1,
    1,    2,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,
};
final static short yydefred[] = {                         1,
    0,    0,   20,   19,   21,    0,   28,   51,    0,    0,
    2,    0,   25,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   30,    0,    0,    0,    0,    0,    3,
    4,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    5,    6,    0,    0,    7,    0,    0,    0,
    0,   54,   55,   56,   57,   58,   59,   60,   61,   62,
   63,   52,    0,    0,    0,   53,    0,    0,   37,   26,
   17,   27,    0,   11,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   49,   48,    0,   24,
   24,    0,    0,   31,   45,   18,   13,    0,    0,    0,
    0,   24,    0,   15,    0,    0,    0,   24,   16,
};
final static short yydgoto[] = {                          1,
   24,   15,   73,   74,   75,   19,   49,   97,   20,   21,
   93,  106,   29,   68,   62,   22,
};
final static short yysindex[] = {                         0,
  742,  -35,    0,    0,    0,  -31,    0,    0,  -53,  -51,
    0,  -31,    0,   10,   11,   58,   12,    3,  -21,  -21,
  -17,  -23,  -31,    0,  610,  -31,  -98,  583,  714,    0,
    0,  -31,  -31,  -31,  -31,  -31,  -31,  -31,  -31,  -31,
  -31,  -31,    0,    0,  -97,  -97,    0,  -31,  -37,  -37,
  -31,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  758,  610,    0,    0,  -39,  -23,    0,    0,
    0,    0,  610,    0,  -39,  -88,  -88,  -88,  -88,  -88,
  -88,  198,  198,  -88,  -88,  -88,    0,    0,  767,    0,
    0,  610,  -34,    0,    0,    0,    0, -233,  -21,  -37,
  -32,    0,  -31,    0,  610,  -12,  -37,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  -10,    7,
    0,    0,    0,  786,    0,    0,    0,    0,    0,    0,
    0,  665,    0,    0,  119,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   94,   26,    0,   45,  565,    0,    0,
    0,    0,  626,    0,  681,  147,  288,  303,  326,  351,
  389,  597,  644,  412,  457,  477,    0,    0,    0,    0,
    0,  -25,    0,    0,    0,    0,    0,  720,    0,    0,
    0,    0,    0,    0,   -2,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   39,    9, 1035,   42,   27,    0,   -4,  -84,    0,    0,
    0,    0,    0,    0,  -15,    0,
};
final static int YYTABLESIZE=1138;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         29,
   65,    8,   12,   46,   23,   45,   98,   26,   12,   27,
   61,   60,   47,   59,   58,   50,   53,  104,   48,   30,
   31,   44,   51,  109,   99,  100,  103,   18,  107,   29,
   29,   29,   29,   22,   29,   46,   29,   72,   23,   14,
   53,   53,   17,   53,   53,   46,   53,   45,   29,   53,
    0,   53,   95,   67,   47,    0,    0,   90,   91,   46,
   46,    8,   46,   46,    0,   46,    0,   43,   51,    0,
   51,   87,   88,    0,    0,    0,   57,   52,   47,   47,
    0,   47,   47,   29,   47,   13,   53,   54,   29,    0,
    0,   55,    0,   56,  101,    0,    0,    0,    0,   40,
   39,    0,   38,    8,   41,   53,   53,   53,  102,    0,
    0,    0,   29,    0,   29,  108,   53,   53,    0,    0,
    0,   53,    0,   53,   46,   46,   46,    0,   10,   53,
    0,   53,    0,    8,    8,   46,   46,    0,    0,    0,
   46,    0,   46,   47,   47,   47,    0,    0,   46,    0,
   46,   42,    8,    0,   47,   47,   38,    0,   10,   47,
    0,   47,    0,    0,    0,    0,    0,   47,    0,   47,
    0,    0,   66,   66,   32,   33,   34,   35,   36,   37,
    0,    0,    0,    0,    0,    0,   38,   38,   38,   38,
    0,   38,    8,   38,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   38,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    8,   10,    8,    2,
    3,    0,    4,    5,    6,    2,    0,    0,    0,    0,
    0,    7,    9,   10,    0,    0,    0,    7,    9,   40,
   38,   10,    0,   10,   41,   38,   29,   29,   29,   29,
   29,   29,   29,   29,   29,   29,   29,   29,   29,   29,
   29,    0,    0,   53,   53,   53,   53,   53,   53,   38,
    0,   38,    0,    0,    0,   53,   53,   53,    0,    0,
    0,    0,   46,   46,   46,   46,   46,   46,    0,    0,
    0,   42,    0,    0,   46,   46,   46,   39,    0,    0,
    0,   47,   47,   47,   47,   47,   47,    0,    0,    0,
    0,    0,   40,   47,   47,   47,    0,    0,    0,    0,
   32,   33,   34,   35,   36,   37,    0,   39,   39,   39,
   39,    0,   39,    0,   39,   41,    0,    0,    0,    0,
    0,    0,   40,   40,   40,   40,   39,   40,    0,   40,
    8,    8,    8,    8,    8,    8,    0,    0,    0,    0,
   42,   40,    8,    8,    8,   41,   41,   41,   41,    0,
   41,    0,   41,    0,    0,   10,   10,   10,   10,   10,
   10,   39,    0,    0,   41,    0,   39,   10,   10,   10,
   42,   42,   42,   42,    0,   42,   40,   42,   43,    0,
    0,   40,    0,   38,   38,   38,   38,   38,   38,   42,
   39,    0,   39,    0,    0,   38,   38,   38,    0,   41,
    0,   34,    0,    0,   41,   40,    0,   40,   43,   43,
   43,   43,    0,   43,    0,   43,    0,    0,    0,    0,
    0,    0,    0,    0,   42,    0,    0,   43,   41,   42,
   41,   34,   34,   34,   34,    0,   34,    0,   34,    0,
   32,   33,   34,   35,   36,   37,   35,    0,    0,    0,
   34,    0,    0,   42,    0,   42,    0,    0,    0,    0,
    0,    0,   43,    0,    0,    0,   36,   43,    0,    0,
    0,    0,    0,    0,    0,    0,   35,   35,   35,   35,
    0,   35,    0,   35,    0,   34,    0,    0,    0,    0,
   34,   43,    0,   43,    0,   35,   36,   36,   36,   36,
    0,   36,    0,   36,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   34,   36,   34,    0,    0,    0,
    0,    0,    0,    0,   39,   39,   39,   39,   39,   39,
   35,    0,    0,    0,    0,   35,   39,   39,   39,   40,
   40,   40,   40,   40,   40,    0,    0,    0,    0,    0,
   36,   40,   40,   40,   44,   36,    0,    0,    0,   35,
    0,   35,   41,   41,   41,   41,   41,   41,    0,    0,
    0,    0,    0,    0,   41,   41,   41,    0,    0,   36,
    0,   36,    0,    0,   44,    0,   33,   42,   42,   42,
   42,   42,   42,    0,    0,    0,    0,    0,    0,   42,
   42,   42,    0,   69,   40,   39,    0,   38,    0,   41,
    0,    0,    0,    0,    0,    9,   33,   33,    0,   33,
    0,   33,    0,    0,    0,   43,   43,   43,   43,   43,
   43,   40,   39,   32,   38,   33,   41,   43,   43,   43,
    0,    0,    0,   44,    0,    9,    0,    0,   34,   34,
   34,   34,   34,   34,   50,    0,   42,    0,    0,    0,
   34,   34,   34,   32,   32,    0,   32,   44,   32,   44,
   12,    0,    0,    0,    0,   33,    0,    0,    0,    0,
    0,    0,   32,   42,   50,    0,    0,   50,    0,   50,
    0,    0,    0,   35,   35,   35,   35,   35,   35,   33,
   12,   33,    0,   70,    9,   35,   35,   35,    0,   14,
    0,    0,    0,   36,   36,   36,   36,   36,   36,    0,
    0,    0,   32,    0,    0,   36,   36,   36,    9,    0,
    9,   11,    0,   12,    0,    0,    0,    0,    0,   14,
    0,    0,    0,   50,    0,    0,   32,    0,   32,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   12,
    0,   12,    0,    0,    0,    0,    0,   50,    0,   50,
    0,    0,    0,    0,    0,    0,    0,    0,   94,   40,
   39,    0,   38,   12,   41,   12,    0,   96,   40,   39,
    0,   38,    8,   41,    0,    0,    0,    0,   14,    0,
    0,   44,   44,   44,   44,   44,   44,   30,   30,    0,
   30,    0,   30,   44,   44,   44,   13,    0,   71,    0,
    8,    0,   14,    0,   14,   32,   33,   34,   35,   36,
   37,   42,    0,   33,   33,   33,   33,   33,   33,    0,
   42,    0,    0,    0,   13,   33,   33,   33,    0,    0,
    0,    0,   32,   33,   34,   35,   36,   37,    0,   30,
    0,    0,    9,    9,    9,    9,    9,    9,    0,    0,
    0,    0,    0,    0,    9,    9,    9,    0,    0,    0,
   32,   32,   32,   32,   32,   32,    0,    0,    0,    0,
    0,    0,   32,   32,   32,    0,    0,    0,    0,    0,
    0,   50,   50,   50,   50,   50,   50,    0,    0,    0,
    0,    0,    0,   50,   50,   50,    0,   12,   12,   12,
   12,   12,   12,    0,    0,    0,    0,    0,    0,   12,
   12,   12,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    2,    3,    0,    4,    5,    6,   14,   14,    0,   14,
   14,   14,    7,    9,   10,    0,    0,    0,   14,   14,
   14,    0,    0,    0,    0,    0,    0,    0,    2,    3,
    0,    4,    5,    6,    0,    0,    0,    0,    0,    0,
    7,    9,   10,    0,    0,    0,    0,    0,    0,    0,
   32,   33,   34,   35,   36,   37,    0,    0,    0,   32,
   33,   34,   35,   36,   37,   16,    0,    0,    0,    0,
   25,    0,    0,    0,    0,    0,   28,    0,   30,   30,
   30,   30,   30,   30,    0,    0,    0,   63,    0,    0,
   64,    0,    0,    0,    0,    0,   76,   77,   78,   79,
   80,   81,   82,   83,   84,   85,   86,    0,    0,    0,
    0,    0,   89,    0,    0,   92,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  105,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         10,
   99,   99,   40,   43,   40,   45,   91,   61,   40,   61,
   34,   35,   10,   37,   38,   20,   10,  102,   40,   10,
   10,   10,   40,  108,   59,  259,   59,    1,   41,   40,
   41,   42,   43,   59,   45,   10,   47,   29,   41,    1,
   34,   35,    1,   37,   38,   43,   40,   45,   59,   43,
   -1,   45,   68,   27,   10,   -1,   -1,   49,   50,   34,
   35,   99,   37,   38,   -1,   40,   -1,   10,   43,   -1,
   45,   45,   46,   -1,   -1,   -1,  100,  101,   34,   35,
   -1,   37,   38,   94,   40,  123,  110,  111,   99,   -1,
   -1,  115,   -1,  117,   99,   -1,   -1,   -1,   -1,   42,
   43,   -1,   45,   10,   47,   99,  100,  101,  100,   -1,
   -1,   -1,  123,   -1,  125,  107,  110,  111,   -1,   -1,
   -1,  115,   -1,  117,   99,  100,  101,   -1,   10,  123,
   -1,  125,   -1,   40,   41,  110,  111,   -1,   -1,   -1,
  115,   -1,  117,   99,  100,  101,   -1,   -1,  123,   -1,
  125,   94,   59,   -1,  110,  111,   10,   -1,   40,  115,
   -1,  117,   -1,   -1,   -1,   -1,   -1,  123,   -1,  125,
   -1,   -1,  271,  271,  263,  264,  265,  266,  267,  268,
   -1,   -1,   -1,   -1,   -1,   -1,   40,   41,   42,   43,
   -1,   45,   99,   47,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   59,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  123,   99,  125,  257,
  258,   -1,  260,  261,  262,  257,   -1,   -1,   -1,   -1,
   -1,  269,  270,  271,   -1,   -1,   -1,  269,  270,   42,
   94,  123,   -1,  125,   47,   99,  257,  258,  259,  260,
  261,  262,  263,  264,  265,  266,  267,  268,  269,  270,
  271,   -1,   -1,  257,  258,  259,  260,  261,  262,  123,
   -1,  125,   -1,   -1,   -1,  269,  270,  271,   -1,   -1,
   -1,   -1,  257,  258,  259,  260,  261,  262,   -1,   -1,
   -1,   94,   -1,   -1,  269,  270,  271,   10,   -1,   -1,
   -1,  257,  258,  259,  260,  261,  262,   -1,   -1,   -1,
   -1,   -1,   10,  269,  270,  271,   -1,   -1,   -1,   -1,
  263,  264,  265,  266,  267,  268,   -1,   40,   41,   42,
   43,   -1,   45,   -1,   47,   10,   -1,   -1,   -1,   -1,
   -1,   -1,   40,   41,   42,   43,   59,   45,   -1,   47,
  257,  258,  259,  260,  261,  262,   -1,   -1,   -1,   -1,
   10,   59,  269,  270,  271,   40,   41,   42,   43,   -1,
   45,   -1,   47,   -1,   -1,  257,  258,  259,  260,  261,
  262,   94,   -1,   -1,   59,   -1,   99,  269,  270,  271,
   40,   41,   42,   43,   -1,   45,   94,   47,   10,   -1,
   -1,   99,   -1,  257,  258,  259,  260,  261,  262,   59,
  123,   -1,  125,   -1,   -1,  269,  270,  271,   -1,   94,
   -1,   10,   -1,   -1,   99,  123,   -1,  125,   40,   41,
   42,   43,   -1,   45,   -1,   47,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   94,   -1,   -1,   59,  123,   99,
  125,   40,   41,   42,   43,   -1,   45,   -1,   47,   -1,
  263,  264,  265,  266,  267,  268,   10,   -1,   -1,   -1,
   59,   -1,   -1,  123,   -1,  125,   -1,   -1,   -1,   -1,
   -1,   -1,   94,   -1,   -1,   -1,   10,   99,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   40,   41,   42,   43,
   -1,   45,   -1,   47,   -1,   94,   -1,   -1,   -1,   -1,
   99,  123,   -1,  125,   -1,   59,   40,   41,   42,   43,
   -1,   45,   -1,   47,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  123,   59,  125,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,
   94,   -1,   -1,   -1,   -1,   99,  269,  270,  271,  257,
  258,  259,  260,  261,  262,   -1,   -1,   -1,   -1,   -1,
   94,  269,  270,  271,   10,   99,   -1,   -1,   -1,  123,
   -1,  125,  257,  258,  259,  260,  261,  262,   -1,   -1,
   -1,   -1,   -1,   -1,  269,  270,  271,   -1,   -1,  123,
   -1,  125,   -1,   -1,   40,   -1,   10,  257,  258,  259,
  260,  261,  262,   -1,   -1,   -1,   -1,   -1,   -1,  269,
  270,  271,   -1,   41,   42,   43,   -1,   45,   -1,   47,
   -1,   -1,   -1,   -1,   -1,   10,   40,   41,   -1,   43,
   -1,   45,   -1,   -1,   -1,  257,  258,  259,  260,  261,
  262,   42,   43,   10,   45,   59,   47,  269,  270,  271,
   -1,   -1,   -1,   99,   -1,   40,   -1,   -1,  257,  258,
  259,  260,  261,  262,   10,   -1,   94,   -1,   -1,   -1,
  269,  270,  271,   40,   41,   -1,   43,  123,   45,  125,
   10,   -1,   -1,   -1,   -1,   99,   -1,   -1,   -1,   -1,
   -1,   -1,   59,   94,   40,   -1,   -1,   43,   -1,   45,
   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,  123,
   40,  125,   -1,   10,   99,  269,  270,  271,   -1,   10,
   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,   -1,
   -1,   -1,   99,   -1,   -1,  269,  270,  271,  123,   -1,
  125,   10,   -1,   40,   -1,   -1,   -1,   -1,   -1,   40,
   -1,   -1,   -1,   99,   -1,   -1,  123,   -1,  125,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   99,
   -1,   40,   -1,   -1,   -1,   -1,   -1,  123,   -1,  125,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   41,   42,
   43,   -1,   45,  123,   47,  125,   -1,   41,   42,   43,
   -1,   45,   99,   47,   -1,   -1,   -1,   -1,   99,   -1,
   -1,  257,  258,  259,  260,  261,  262,   42,   43,   -1,
   45,   -1,   47,  269,  270,  271,  123,   -1,  125,   -1,
   99,   -1,  123,   -1,  125,  263,  264,  265,  266,  267,
  268,   94,   -1,  257,  258,  259,  260,  261,  262,   -1,
   94,   -1,   -1,   -1,  123,  269,  270,  271,   -1,   -1,
   -1,   -1,  263,  264,  265,  266,  267,  268,   -1,   94,
   -1,   -1,  257,  258,  259,  260,  261,  262,   -1,   -1,
   -1,   -1,   -1,   -1,  269,  270,  271,   -1,   -1,   -1,
  257,  258,  259,  260,  261,  262,   -1,   -1,   -1,   -1,
   -1,   -1,  269,  270,  271,   -1,   -1,   -1,   -1,   -1,
   -1,  257,  258,  259,  260,  261,  262,   -1,   -1,   -1,
   -1,   -1,   -1,  269,  270,  271,   -1,  257,  258,  259,
  260,  261,  262,   -1,   -1,   -1,   -1,   -1,   -1,  269,
  270,  271,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  257,  258,   -1,  260,  261,  262,  257,  258,   -1,  260,
  261,  262,  269,  270,  271,   -1,   -1,   -1,  269,  270,
  271,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,
   -1,  260,  261,  262,   -1,   -1,   -1,   -1,   -1,   -1,
  269,  270,  271,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  263,  264,  265,  266,  267,  268,   -1,   -1,   -1,  263,
  264,  265,  266,  267,  268,    1,   -1,   -1,   -1,   -1,
    6,   -1,   -1,   -1,   -1,   -1,   12,   -1,  263,  264,
  265,  266,  267,  268,   -1,   -1,   -1,   23,   -1,   -1,
   26,   -1,   -1,   -1,   -1,   -1,   32,   33,   34,   35,
   36,   37,   38,   39,   40,   41,   42,   -1,   -1,   -1,
   -1,   -1,   48,   -1,   -1,   51,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  103,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=271;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,"'\\n'",null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,"'\"'","'#'",null,"'%'","'&'",null,"'('","')'","'*'",
"'+'",null,"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,
null,"';'",null,"'='",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,"'^'",null,null,null,null,"'c'","'d'","'e'",null,null,
null,null,null,null,null,null,"'n'","'o'",null,null,null,"'s'",null,"'u'",null,
null,null,null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,"BLTIN","IF","ELSE",
"WHILE","FOR","PRINT","EQ","NEQ","GT","GE","LT","LE","NUMBER","VAR","VARCRD",
};
final static String yyrule[] = {
"$accept : list",
"list :",
"list : list '\\n'",
"list : list asgn '\\n'",
"list : list stmt '\\n'",
"list : list exp '\\n'",
"list : list asigncoord '\\n'",
"list : list expcoord '\\n'",
"asgn : VAR '=' exp",
"stmt : exp",
"stmt : PRINT exp",
"stmt : asigncoord",
"stmt : expcoord",
"stmt : while cond stmt end",
"stmt : if cond stmt end",
"stmt : if cond stmt end ELSE stmt end",
"stmt : for '(' inicio ';' cond ';' inc ')' stmt end",
"stmt : '{' stmtlist '}'",
"cond : '(' exp ')'",
"while : WHILE",
"if : IF",
"for : FOR",
"inicio : exp",
"inc : exp",
"end :",
"stmtlist :",
"stmtlist : stmtlist '\\n'",
"stmtlist : stmtlist stmt",
"exp : NUMBER",
"exp : VAR",
"exp : asgn",
"exp : BLTIN '(' exp ')'",
"exp : exp '+' exp",
"exp : exp '-' exp",
"exp : exp '*' exp",
"exp : exp '/' exp",
"exp : exp '^' exp",
"exp : '(' exp ')'",
"exp : exp EQ exp",
"exp : exp NEQ exp",
"exp : exp GT exp",
"exp : exp GE exp",
"exp : exp LT exp",
"exp : exp LE exp",
"asigncoord : VARCRD '=' binstr",
"binstr : binstr instr",
"binstr : 'c'",
"binstr : expcoord",
"expcoord : expcoord '+' expcoord",
"expcoord : expcoord '-' expcoord",
"expcoord : sec",
"sec : 'c'",
"sec : sec instr",
"sec : VARCRD",
"instr : 'e'",
"instr : 'n'",
"instr : 'o'",
"instr : 's'",
"instr : 'u'",
"instr : 'd'",
"instr : '&'",
"instr : '%'",
"instr : '#'",
"instr : '\"'",
};

//#line 208 "hoc5.y"


static Maquina maq = new Maquina();
  private Yylex lexer;

  private int yylex () {
    int yyl_return = -1;
    try {
      yylval = new ParserVal(0);      
      yyl_return = lexer.yylex();
    }
    catch (IOException e) {
      System.err.println("IO error :"+e);
    }  

    return yyl_return;
  }


  public void yyerror (String error) {
    System.err.println ("Error: " + error);
  }


  public Parser(Reader r) {
    lexer = new Yylex(r, this);
  }

  boolean flag = false;
  
  public static void main(String args[]) throws IOException {    
    System.out.println("HOC 5 Simple calc");

    Parser yyparser;
    maq.initCode();
    
    while (true) {
      System.out.print("Expression: ");
      maq.newProgram();
      
      yyparser = new Parser(new InputStreamReader(System.in));        
      yyparser.yyparse();
            
      maq.execute(0);
    }

  }
//#line 552 "Parser.java"
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
case 3:
//#line 31 "hoc5.y"
{ maq.code("STOP"); System.out.println("asignacion normal"); return 1; }
//break;
case 4:
//#line 32 "hoc5.y"
{ maq.code("STOP"); return 1;}
//break;
case 5:
//#line 33 "hoc5.y"
{ maq.code("printNumber"); maq.code("STOP"); return 1;}
//break;
case 6:
//#line 34 "hoc5.y"
{	
							maq.banderaVar=true;	
						 	maq.code("printVar"); 
							maq.code("STOP"); 
                          	maq.execute(flag); flag = true; 
						 }
break;
case 7:
//#line 40 "hoc5.y"
{
							maq.banderaVar=false;
							maq.code("imprime");	maq.code("STOP");
							maq.execute(flag); flag = true;
							maq.restablecerBandera();
						}
break;
case 8:
//#line 48 "hoc5.y"
{ int numI = maq.code("varPush");
                      maq.code((Cadena) val_peek(2).obj);
                      maq.code("asgVar");                       
                      yyval = new ParserVal(val_peek(0).obj);
                    }
break;
case 9:
//#line 56 "hoc5.y"
{ maq.code("pop"); }
break;
case 10:
//#line 57 "hoc5.y"
{ maq.code("printNumber"); 
                    yyval = new ParserVal(val_peek(0).obj); /*dice la direccion a redirirgir (los STOP)
*/
                  }
break;
case 11:
//#line 61 "hoc5.y"
{	
		 System.out.println("nabla");
							maq.banderaVar=true;	
						 	maq.code("printVar"); 
							maq.code("STOP"); 
                          	maq.execute(flag); flag = true; 
						 }
break;
case 12:
//#line 69 "hoc5.y"
{
		  System.out.println("nabla");
							maq.banderaVar=false;
							maq.code("imprime");	maq.code("STOP");
							maq.execute(flag); flag = true;
							maq.restablecerBandera();
						}
break;
case 13:
//#line 77 "hoc5.y"
{ maq.getProg().setElementAt(val_peek(1).obj, (int) val_peek(3).obj + 1);
                              maq.getProg().setElementAt(val_peek(0).obj, (int) val_peek(3).obj + 2); 
                            }
break;
case 14:
//#line 80 "hoc5.y"
{ maq.getProg().setElementAt(val_peek(1).obj, (int) val_peek(3).obj + 1); 
                              maq.getProg().setElementAt(val_peek(0).obj, (int) val_peek(3).obj + 3); 
                            }
break;
case 15:
//#line 83 "hoc5.y"
{  maq.getProg().setElementAt(val_peek(4).obj, (int) val_peek(6).obj + 1);
                                          maq.getProg().setElementAt(val_peek(1).obj, (int) val_peek(6).obj + 2); 
                                          maq.getProg().setElementAt(val_peek(0).obj, (int) val_peek(6).obj + 3);
	                                     }
break;
case 16:
//#line 87 "hoc5.y"
{  maq.getProg().setElementAt(val_peek(7).obj, (int) val_peek(9).obj + 1);
                                                        maq.getProg().setElementAt(val_peek(5).obj, (int) val_peek(9).obj + 2); 
                                                        maq.getProg().setElementAt(val_peek(3).obj, (int) val_peek(9).obj + 3);
                                                        maq.getProg().setElementAt(val_peek(1).obj, (int) val_peek(9).obj + 4);
                                                        maq.getProg().setElementAt(val_peek(0).obj, (int) val_peek(9).obj + 5);
                                                     }
break;
case 17:
//#line 93 "hoc5.y"
{yyval  =  val_peek(1); }
break;
case 18:
//#line 95 "hoc5.y"
{ maq.code("STOP");                    
                    yyval = new ParserVal(val_peek(1).obj);
                  }
break;
case 19:
//#line 99 "hoc5.y"
{ int numI = maq.code("whileCode"); 
                               maq.code("STOP"); maq.code("STOP");
                               yyval = new ParserVal(new Integer(numI));
                             }
break;
case 20:
//#line 104 "hoc5.y"
{ int numI = maq.code("ifCode");
          maq.code("STOP"); maq.code("STOP"); maq.code("STOP");
          yyval = new ParserVal(new Integer(numI));
        }
break;
case 21:
//#line 109 "hoc5.y"
{ int numI = maq.code("forCode");
           maq.code("STOP"); maq.code("STOP"); maq.code("STOP"); maq.code("STOP"); maq.code("STOP");
           yyval = new ParserVal(new Integer(numI));
         }
break;
case 22:
//#line 114 "hoc5.y"
{ maq.code("STOP");
              yyval = new ParserVal(val_peek(0).obj);
            }
break;
case 23:
//#line 118 "hoc5.y"
{ maq.code("STOP");
              yyval = new ParserVal(val_peek(0).obj);
            }
break;
case 24:
//#line 124 "hoc5.y"
{ maq.code("STOP");
           yyval = new ParserVal(new Integer(maq.getProg().size())); 
		  System.out.println("-->| STOP");
         }
break;
case 25:
//#line 129 "hoc5.y"
{ yyval = new ParserVal(new Integer(maq.getProg().size())); System.out.println("en stmlist");}
break;
case 28:
//#line 134 "hoc5.y"
{ Numero n = (Numero) val_peek(0).obj; 
                                    int num=maq.code("Numero");
                                    maq.code(n);
									yyval = new ParserVal(new Integer(num));
                                  }
break;
case 29:
//#line 139 "hoc5.y"
{ int num=maq.code("varPush");
                                    maq.code((Cadena) val_peek(0).obj);
                                    maq.code("getVarValue");
									yyval = new ParserVal(new Integer(num));
                                  }
break;
case 30:
//#line 144 "hoc5.y"
{}
break;
case 31:
//#line 145 "hoc5.y"
{ maq.code("bltinPush");
                                    maq.code((Cadena) val_peek(3).obj);
                                    maq.code("bltin");                                    
                                  }
break;
case 32:
//#line 149 "hoc5.y"
{ maq.code("add"); }
break;
case 33:
//#line 150 "hoc5.y"
{ maq.code("sub"); }
break;
case 34:
//#line 151 "hoc5.y"
{ maq.code("mult"); }
break;
case 35:
//#line 152 "hoc5.y"
{ maq.code("div"); }
break;
case 36:
//#line 153 "hoc5.y"
{ maq.code("powN");
                                    maq.code((Cadena) val_peek(0).obj);
                                    maq.code("pow");                                    
                                  }
break;
case 37:
//#line 157 "hoc5.y"
{ yyval = val_peek(1); }
break;
case 38:
//#line 160 "hoc5.y"
{ maq.code("eq");}
break;
case 39:
//#line 161 "hoc5.y"
{ maq.code("neq");}
break;
case 40:
//#line 162 "hoc5.y"
{ maq.code("gt");}
break;
case 41:
//#line 163 "hoc5.y"
{ maq.code("ge");}
break;
case 42:
//#line 164 "hoc5.y"
{ maq.code("lt");}
break;
case 43:
//#line 165 "hoc5.y"
{ maq.code("le");}
break;
case 44:
//#line 170 "hoc5.y"
{
							maq.code("varPush");
                      		maq.code((Cadena) val_peek(2).obj);
                      		maq.code("asgVarCoord"); 
						}
break;
case 45:
//#line 177 "hoc5.y"
{ 	maq.code("suma");		}
break;
case 46:
//#line 178 "hoc5.y"
{	maq.limpiarDireccionesVar(); maq.banderaVar=false; maq.code("comienza");}
break;
case 47:
//#line 179 "hoc5.y"
{	maq.code("concatenarCoordenadas");maq.restablecerBandera();}
break;
case 48:
//#line 181 "hoc5.y"
{ maq.code("sumafinal");	}
break;
case 49:
//#line 182 "hoc5.y"
{ maq.code("restafinal");	}
break;
case 50:
//#line 183 "hoc5.y"
{}
break;
case 51:
//#line 186 "hoc5.y"
{ 	maq.code("comienza"); 	}
break;
case 52:
//#line 187 "hoc5.y"
{ 	maq.code("suma");		}
break;
case 53:
//#line 188 "hoc5.y"
{	
							maq.code("varPush");
                      		maq.code((Cadena) val_peek(0).obj);
                      		maq.code("getVarCoordValue");
						}
break;
case 54:
//#line 195 "hoc5.y"
{	maq.code("este");  		}
break;
case 55:
//#line 196 "hoc5.y"
{	maq.code("norte"); 		}
break;
case 56:
//#line 197 "hoc5.y"
{	maq.code("oeste"); 		}
break;
case 57:
//#line 198 "hoc5.y"
{	maq.code("sur");	  	}
break;
case 58:
//#line 199 "hoc5.y"
{	maq.code("up");	  		}
break;
case 59:
//#line 200 "hoc5.y"
{	maq.code("down");  		}
break;
case 60:
//#line 201 "hoc5.y"
{	maq.code("sureste");	}
break;
case 61:
//#line 202 "hoc5.y"
{	maq.code("noreste");	}
break;
case 62:
//#line 203 "hoc5.y"
{	maq.code("noroeste");	}
break;
case 63:
//#line 204 "hoc5.y"
{	maq.code("suroeste");	}
break;
//#line 1017 "Parser.java"
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
