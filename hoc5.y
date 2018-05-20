%{
import java.lang.Math;
import java.io.*;
import java.util.StringTokenizer;
import java.util.ArrayList;
import javax.swing.*;
%}

%token BLTIN
%token IF ELSE WHILE FOR PRINT
%token EQ NEQ GT GE LT LE

%token NUMBER

%token 'c'
	
%token VAR

%token VARCRD

/* Operators */
%right '='
%left '-' '+'
%left '*' '/' '^'
%right IF ELSE EQ NEQ GT GE LT LE 

%% /* A continuaci?n las reglas gramaticales y las acciones */

list:   
   | list '\n'
   | list asgn '\n'  { maq.code("STOP"); System.out.println("asignacion normal"); return 1; }
   | list stmt '\n'  { maq.code("STOP"); return 1;}
   | list exp '\n'   { maq.code("printNumber"); maq.code("STOP"); return 1;}  
   | list asigncoord '\n'{	
							maq.banderaVar=true;	
						 	maq.code("printVar"); 
							maq.code("STOP"); 
                          	maq.execute(flag); flag = true; 
						 }
	| list expcoord '\n'	{
							maq.banderaVar=false;
							maq.code("imprime");	maq.code("STOP");
							maq.execute(flag); flag = true;
							maq.restablecerBandera();
						}
   ;
   
asgn: VAR '=' exp   { int numI = maq.code("varPush");
                      maq.code((Cadena) $1.obj);
                      maq.code("asgVar");                       
                      $$ = new ParserVal($3.obj);
                    }
      ;

			/*Statements*/
stmt: exp { maq.code("pop"); }
      | PRINT exp { maq.code("printNumber"); 
                    $$ = new ParserVal($2.obj); //dice la direccion a redirirgir (los STOP)
                  }

	 | asigncoord {	
		 System.out.println("nabla");
							maq.banderaVar=true;	
						 	maq.code("printVar"); 
							maq.code("STOP"); 
                          	maq.execute(flag); flag = true; 
						 }

	  | expcoord	{
		  System.out.println("nabla");
							maq.banderaVar=false;
							maq.code("imprime");	maq.code("STOP");
							maq.execute(flag); flag = true;
							maq.restablecerBandera();
						}
	  
      | while cond stmt end { maq.getProg().setElementAt($3.obj, (int) $1.obj + 1);
                              maq.getProg().setElementAt($4.obj, (int) $1.obj + 2); 
                            }
      | if cond stmt end    { maq.getProg().setElementAt($3.obj, (int) $1.obj + 1); 
                              maq.getProg().setElementAt($4.obj, (int) $1.obj + 3); 
                            }
      | if cond stmt end ELSE stmt end {  maq.getProg().setElementAt($3.obj, (int) $1.obj + 1);
                                          maq.getProg().setElementAt($6.obj, (int) $1.obj + 2); 
                                          maq.getProg().setElementAt($7.obj, (int) $1.obj + 3);
	                                     } 
	  | for '(' inicio ';' cond ';' inc ')' stmt end {  maq.getProg().setElementAt($3.obj, (int) $1.obj + 1);
                                                        maq.getProg().setElementAt($5.obj, (int) $1.obj + 2); 
                                                        maq.getProg().setElementAt($7.obj, (int) $1.obj + 3);
                                                        maq.getProg().setElementAt($9.obj, (int) $1.obj + 4);
                                                        maq.getProg().setElementAt($10.obj, (int) $1.obj + 5);
                                                     }
      | '{' stmtlist '}' {$$  =  $2; }
      ;
cond: '(' exp ')' { maq.code("STOP");                    
                    $$ = new ParserVal($2.obj);
                  }
      ;
while:	WHILE                { int numI = maq.code("whileCode"); 
                               maq.code("STOP"); maq.code("STOP");
                               $$ = new ParserVal(new Integer(numI));
                             }
      ;
if: IF  { int numI = maq.code("ifCode");
          maq.code("STOP"); maq.code("STOP"); maq.code("STOP");
          $$ = new ParserVal(new Integer(numI));
        }
		
for: FOR { int numI = maq.code("forCode");
           maq.code("STOP"); maq.code("STOP"); maq.code("STOP"); maq.code("STOP"); maq.code("STOP");
           $$ = new ParserVal(new Integer(numI));
         }
    ;
inicio: exp { maq.code("STOP");
              $$ = new ParserVal($1.obj);
            }
      ;
inc:    exp { maq.code("STOP");
              $$ = new ParserVal($1.obj);
            }
      ;


end:     { maq.code("STOP");
           $$ = new ParserVal(new Integer(maq.getProg().size())); 
		  System.out.println("-->| STOP");
         }
    ;
stmtlist: { $$ = new ParserVal(new Integer(maq.getProg().size())); System.out.println("en stmlist");}
	       		| stmtlist '\n' 
	       		| stmtlist stmt
				;

exp:  NUMBER                      { Numero n = (Numero) $1.obj; 
                                    int num=maq.code("Numero");
                                    maq.code(n);
									$$ = new ParserVal(new Integer(num));
                                  }
     | VAR                        { int num=maq.code("varPush");
                                    maq.code((Cadena) $1.obj);
                                    maq.code("getVarValue");
									$$ = new ParserVal(new Integer(num));
                                  }  
	| asgn   {}       
	| BLTIN '(' exp ')'           { maq.code("bltinPush");
                                    maq.code((Cadena) $1.obj);
                                    maq.code("bltin");                                    
                                  }
     | exp '+' exp                { maq.code("add"); }  
     | exp '-' exp                { maq.code("sub"); }
     | exp '*' exp                { maq.code("mult"); }
     | exp '/' exp                { maq.code("div"); }
     | exp '^' exp                { maq.code("powN");
                                    maq.code((Cadena) $3.obj);
                                    maq.code("pow");                                    
                                  }
     | '(' exp ')'                { $$ = $2; }

			/* Conditionals operations */
     | exp EQ exp                 { maq.code("eq");}
     | exp NEQ exp                { maq.code("neq");}
     | exp GT exp                 { maq.code("gt");}
     | exp GE exp                 { maq.code("ge");}
     | exp LT exp                 { maq.code("lt");}
     | exp LE exp                 { maq.code("le");}
    ;
	
/*gram?tica de coordenadas*/

asigncoord:	VARCRD '=' binstr	{
							maq.code("varPush");
                      		maq.code((Cadena) $1.obj);
                      		maq.code("asgVarCoord"); 
						}
		;
			
binstr: binstr instr	{ 	maq.code("suma");		}
		| 'c'			{	maq.limpiarDireccionesVar(); maq.banderaVar=false; maq.code("comienza");}
		| expcoord 		    {	maq.code("concatenarCoordenadas");maq.restablecerBandera();}
		;
expcoord: 	  expcoord '+' expcoord	{ maq.code("sumafinal");	}
		| expcoord '-' expcoord 	{ maq.code("restafinal");	}
		| sec			{}
		;
		
sec:	 'c'			{ 	maq.code("comienza"); 	}
		| sec instr		{ 	maq.code("suma");		}
		| VARCRD		{	
							maq.code("varPush");
                      		maq.code((Cadena) $1.obj);
                      		maq.code("getVarCoordValue");
						}		
		;

instr:    'e'			{	maq.code("este");  		}
		| 'n'			{	maq.code("norte"); 		}
		| 'o'			{	maq.code("oeste"); 		}
		| 's'			{	maq.code("sur");	  	}
		| 'u'			{	maq.code("up");	  		}
		| 'd'			{	maq.code("down");  		}
		| '&'			{	maq.code("sureste");	}
		| '%'			{	maq.code("noreste");	}
		| '#'			{	maq.code("noroeste");	}
		| '"'			{	maq.code("suroeste");	}
		;
	

%%

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
