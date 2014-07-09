////////////////////////////
// Analizador sintactico //
////////////////////////////
header {
package traductor;
}

class AnalizadorSintactico extends Parser;

options {
	buildAST= true;
	k=2;
	importVocab=AnalizadorLexico;
	defaultErrorHandler=false; // para que lanze las excepciones y no las trate
}

{
	private TablaSimbolos ts = new TablaSimbolos();
	private String cod= "", listaErrores = "";
	private int dir;
	private Gestor gestor = new Gestor();
	private Atributos at1 = new Atributos();
	
	public String getCod(){
		return cod;
	}
	
	public TablaSimbolos getTS(){
		return ts;
	}
	
	public String getListaErrores(){
		return listaErrores;
	}
}

prog : //Decs & Accs
    decs
    //AMBER
    accs
    ;

        
decs : //Dec RDecs
    {
    	dir = 0;
    }
    dec
    rdecs
    ;

rdecs : //Dec RDecs
    dec
    rdecs
    |
    ;

dec {boolean errYaExisteVar; String stipo;}: //tipo iden puntcoma
    stipo=tipo i:IDEN 
	PUNT_COMA
	{
        errYaExisteVar = ts.existeID(i.getText());
        System.out.println("identificador: " + " " +i.getText());
        if (errYaExisteVar){
        	listaErrores = listaErrores + "\n" + "Linea: "+ i.getLine() +" . "+  gestor.mensaje(false, errYaExisteVar, false, false, i.getText(), null, null); 
        }else{
        	 ts.anyadeID(i.getText(),stipo,dir);
        	 cod = cod + "\n" + ts.reservaMem(ts.dameDir(i.getText()));
        	 dir++;
        }      
    };

tipo returns [String tipo]: // "int" | "real"
	"int" {tipo="int";} 
	| 
	"real"{tipo="real";} 
	;


accs : //Acc RAccs
    acc
    raccs
    ;
raccs : //Acc RAccs | lambda
    acc
    raccs
    |
    {
        cod = cod + "\n" + gestor.stop();
    }
    ;

acc : //E puntcoma
    at1 = e 
    PUNT_COMA
    {
    };

e returns [Atributos at1 = null] {String tipo1;}: //in iden | out Ein | Ein
    {at1 = new Atributos(); }
    IN 
    i:IDEN
    {
        at1.setErrNoExisteVar(!ts.existeID(i.getText()));
        at1.setErrVarNoIni(false);
		if (at1.isErrNoExisteVar())  
        	tipo1 = "terr";  // si no existe, no tiene tipo, le damos terr para continuar
		else 
        	tipo1 = ts.dameTipo(i.getText()); // si existe, obtenemos su tipo
        at1.setTipo(tipo1);
		at1.setErrTipo( !(tipo1.equalsIgnoreCase("int")) && !(tipo1.equalsIgnoreCase("real"))
                            );
        if (at1.hayError()){
        	listaErrores = listaErrores + "\n"  + "Linea: "+ i.getLine()+ " . "  + gestor.mensaje(at1.isErrNoExisteVar(), at1.isErrYaExisteVar(), at1.isErrVarNoIni(), at1.isErrTipo(), i.getText(), tipo1, "terr");
        }
        else{
        	ts.inicializaID(i.getText()); 
        	cod = cod + "\n" + gestor.leer(ts.dameDir(i.getText()), tipo1);
        }            
    }
    |
    {at1 = new Atributos();}
    OUT
    at1=ein
    {
        //if (!at1.hayError()){
        	cod = cod  + "\n" + gestor.escribir();
			//}
    }
    |
    {at1 = new Atributos();}
    at1=ein
    ;



ein returns [Atributos at1= null] {String tipo2; String tipo1;}: //iden = Ein | Eigual 
    i:IDEN
    ASIGNA
    at1=ein
    {	
    	// calculamos errores
        at1.setErrNoExisteVar(!ts.existeID(i.getText()) || at1.isErrNoExisteVar());
        if (at1.isErrNoExisteVar())  
        	tipo1 = "terr";  // si no existe, no tiene tipo, le damos terr para continuar
        else 
        	tipo1 = ts.dameTipo(i.getText()); // si existe, obtenemos su tipo
        
        ts.inicializaID(i.getText()); 	
        at1.setErrVarNoIni(!ts.estaIni(i.getText()) || at1.isErrNoExisteVar());
        tipo2 = at1.getTipo();
        at1.setErrTipo(tipo1.equalsIgnoreCase("int") && !(tipo2.equalsIgnoreCase("int")) );
        
        
        if (at1.hayError()){ // si hay error, mostrarlos
	        listaErrores = 
	            listaErrores + "\n" + "Linea: "+ i.getLine() +" . " + gestor.mensaje(at1.isErrNoExisteVar(), false, at1.isErrVarNoIni(), at1.isErrTipo(), i.getText(), tipo1, tipo2);
        }else{				 // si no hay error
        	if (ts.dameTipo(i.getText()).equalsIgnoreCase("real")) {
	        	at1.setTipo("real");
	        } else {
	        	at1.setTipo("int");
	        }
	        ts.inicializaID(i.getText());
	        cod = cod + "\n" + gestor.desapilaDir(ts.dameDir(i.getText()), ts.dameTipo(i.getText()));
        }
    }
   	|
	{at1 = new Atributos();}

    at1 = eigual
    ;

opComp returns [String codOp=""] : 
	MENOR 
    {
        codOp = "menor";
    }
    |
    MAYOR 
    {
        codOp = "mayor";
    }
    |
    MENORIGUAL 
    {
        codOp = "menorIgual";
    }
    |
    MAYORIGUAL
    {
        codOp = "mayorIgual";
    }
    |
    IGUAL 
    {
        codOp = "igual";
    }
    |
    DISTINTO 
    {
        codOp = "distinto";
    }
    ;
    
eigual returns [Atributos at1=null] {Atributos at2,at3;}: //EopComp FEigual
    {
    	at1 = new Atributos();
    	at2 = new Atributos();
    	at3 = new Atributos();
    }
    at2 = eopComp 
    at3 = feigual[at2]
    {
        at1 = new Atributos(at3);
    };

feigual [Atributos at1h] returns [Atributos at1=null] {Atributos at2; String codOp;} : //opComp EopComp | lambda
    {
    	at1 = new Atributos();
    	at2 = new Atributos();
    }
    codOp = opComp
    at2 = eopComp																			
    {
    	at1.setErrTipo( (!at1h.getTipo().equalsIgnoreCase("real") &&  !at1h.getTipo().equalsIgnoreCase("int")) ||
                             (!at2.getTipo().equalsIgnoreCase("real") && !at2.getTipo().equalsIgnoreCase("int")));
        at1.setErrNoExisteVar(at1h.isErrNoExisteVar() || at2.isErrNoExisteVar());
        at1.setTipo("int");                                                          
        at1.setErrVarNoIni(at1h.isErrVarNoIni() || at2.isErrVarNoIni());
        
        if (at1.hayError()){ // si hay error, mostrarlos
	        listaErrores = 
	            listaErrores + "\n" + "Linea: "+ at2.getIden().getLine() +" . " + gestor.mensaje(at1.isErrNoExisteVar(), false, at1.isErrVarNoIni(), at1.isErrTipo(), at2.getIden().getText(), at1h.getTipo(), at2.getTipo());
        }else{				 // si no hay error
            cod = cod + "\n" + codOp;
        }
    }
    |
    {	at1 = new Atributos(at1h);
    }
    ;

opAd returns [Operador op=null] {String codOp;}: //opAdArit | OpAdLog
    codOp=opAdArit
    {	
    	op = new Operador();
	    op.setTipoOp("arit");
	    op.setCodOp(codOp);
    }
    |
    codOp=opAdLog
    {
	    op = new Operador();
	    op.setTipoOp("log");
	    op.setCodOp(codOp);
    }
    ;

opAdArit returns [String codOp=""] : 
	MAS 
    {
        codOp = "suma";
    }
    |
    MENOS
    {
         codOp = "resta";
    };
    
opAdLog returns [String codOp=""] : 
	OR 
    {
         codOp = "or";
    }
    ;

eopComp returns [Atributos at1= null] {Atributos at2,at3;}: //EopAd REopComp 
    {
    	at1 = new Atributos();
    	at2 = new Atributos();
    	at3 = new Atributos();
    }    
    at2 = eopAd
    at3 = reopComp[at2]
    {
        at1 = new Atributos(at3);
    };

reopComp [Atributos at1h] returns [Atributos at1=null] {Operador op; Atributos at2,at3,at3h;} : //OpAd EopAd REopComp | lambda
    {
    	at1 = new Atributos();
    	at2 = new Atributos();
    	at3h = new Atributos();
    	at3 = new Atributos();
    	op = new Operador();
    } 
    op = opAd
    at2 = eopAd
    {
    	at3h = new Atributos();
        if (op.getTipoOp().equals("arit")) {
        	if(at1h.getTipo().equalsIgnoreCase("int") && at2.getTipo().equalsIgnoreCase("int")) {
        		at3h.setTipo("int");
        	} else if (at1h.getTipo().equalsIgnoreCase("real") && at2.getTipo().equalsIgnoreCase("real")) {
        		at3h.setTipo("real");
        	} else { 
        		at3h.setTipo("real"); 
        	}
		}
         else { // tipoOp == log
            	at3h.setTipo("int"); 
              	at3h.setErrTipo(!at1h.getTipo().equalsIgnoreCase("int") || !at2.getTipo().equalsIgnoreCase("int"));
        }
        at3h.setErrNoExisteVar( at1h.isErrNoExisteVar() || at2.isErrNoExisteVar());
        at3h.setErrVarNoIni( at1h.isErrVarNoIni() || at2.isErrVarNoIni());
        
        if (at3h.hayError()){ // si hay error, mostrarlos
      	        listaErrores =
        	            listaErrores + "\n" + "Linea: "+ at2.getIden().getLine() +" . " + gestor.mensaje(at3h.isErrNoExisteVar(), false, at3h.isErrVarNoIni(), at3h.isErrTipo(), at2.getIden().getText(), at3h.getTipo(), at2.getTipo());
        }else{
            cod = cod + "\n" + op.getCodOp();
        }
	}
    at3=reopComp[at3h]
    {
        at1 = new Atributos(at3);
    }
	|
	{
    	at1 = new Atributos(at1h);
	}
	;

opMul returns [Operador op=null] {String codOp;}: //opMulArit
    {op = new Operador();}
    codOp=opMulArit
    {
    	op.setTipoOp("arit");
    	op.setCodOp(codOp);
    }
	|
	codOp=opMulLog
    {
    	op = new Operador();
    	op.setTipoOp("log");
    	op.setCodOp(codOp);
    }
	;

opMulArit returns [String codOp=""] : 
	MULT
    {
    	codOp = "mult";
    }
	|
	DIV 
    {
        codOp = "div";
    }
	;
	
opMulLog returns [String codOp=""] : 
	AND
    {
       codOp = "and";
    }
	|
	MOD
    {
        codOp = "mod";
    }
	;


eopAd returns [Atributos at1=null] {Atributos at2,at3,at3h;} : //EopMul REopAd
    {
    	at1 = new Atributos();
    	at2 = new Atributos();
    	at3h = new Atributos();
    	at3 = new Atributos();
    }
    at2=eopMul
    {
        at3h = new Atributos(at2);
    }
    at3=reopAd[at3h]
    {
        at1 = new Atributos(at3);
    }
    ;


reopAd[Atributos at1h] returns [Atributos at1=null] {Atributos at2, at3,at3h; Operador op;} : //OpMul EopMul REopAd | lambda
    {
    	at1 = new Atributos();
    	at2 = new Atributos();
    	at3h = new Atributos();
    	at3 = new Atributos();
    	op = new Operador();
    } 
    op=opMul
    at2=eopMul
    {	
    	at3h = new Atributos();
        if (op.getTipoOp().equals("arit")) {
        	if (at1h.getTipo().equalsIgnoreCase("int") && at2.getTipo().equalsIgnoreCase("int")) {
        		at3h.setTipo("int");
        	} else if (at1h.getTipo().equalsIgnoreCase("real") && at2.getTipo().equalsIgnoreCase("real")) {
        		at3h.setTipo("real");
        	} else {
        		at3h.setTipo("real");
        	}
        }else{ // opAd.tipoOp == log
        	at3h.setTipo("int");
          	at3h.setErrTipo((!at1h.getTipo().equalsIgnoreCase("int") || !at2.getTipo().equalsIgnoreCase("int")));
        }
        

        at3h.setErrNoExisteVar(at1h.isErrNoExisteVar() || at2.isErrNoExisteVar());
        at3h.setErrVarNoIni(at2.isErrVarNoIni() || at1h.isErrVarNoIni());
       
        if (at3h.hayError()){ // si hay error, mostrarlos
                	        listaErrores =
                	            listaErrores + "\n" + "Linea: "+ at2.getIden().getLine() +" . " + gestor.mensaje(at3h.isErrNoExisteVar(), false, at3h.isErrVarNoIni(), at3h.isErrTipo(), at2.getIden().getText(), at3h.getTipo(), at2.getTipo());
        }else{
             cod = cod + "\n" + op.getCodOp();
        }
    }
    at3=reopAd[at3h]
    {
        at1 =  new Atributos(at3);
    }
	|
	{	
		at1 = new Atributos(at1h);
	}
	;

opUnNeg returns [String codOp=""] : 
	NEG
    {
         codOp = "neg";
    };
opUnCambio returns [String codOp=""] : 
	MENOS
    {
         codOp = "cds";
    };

eopMul returns [Atributos at1=null] {String codOp; Atributos at2;}: //opUnNeg EopUn | opUnCambio EopUn | EopUn
    {
    	 at1= new Atributos();
    	 at2= new Atributos();
    }
    codOp=opUnNeg
    at2=eopUn
    {
		at1= new Atributos(at2);
        at1.setTipo("int");
        at1.setErrTipo(!at2.getTipo().equals("int"));

        if (at1.hayError()){ // si hay error, mostrarlos
                	        	listaErrores =
                	            	listaErrores + "\n" + "Linea: "+ at2.getIden().getLine() +" . " + gestor.mensaje(false, false, false, at1.isErrTipo(), at2.getIden().getText(), at1.getTipo(), at2.getTipo());
        }else{
            cod = cod + "\n" + codOp;
        }

    }
	|
	{
    	 at1= new Atributos();
    	 at2= new Atributos();
    }
	codOp=opUnCambio
    at2=eopUn
    {
    	at1= new Atributos(at2);
        at1.setTipo(at2.getTipo());
        at1.setErrTipo(!at2.getTipo().equalsIgnoreCase("int") && !at2.getTipo().equalsIgnoreCase("real"));

          
        if (at1.hayError()){ // si hay error, mostrarlos
                           listaErrores =
                              listaErrores + "\n" + "Linea: "+ at2.getIden().getLine() +" . " + gestor.mensaje(false, false, false, at1.isErrTipo(), at2.getIden().getText(), at1.getTipo(), at2.getTipo());
        }else{
            cod = cod + "\n" + codOp;
        }
    }
	|
	{
    	 at1= new Atributos();
    	 at2= new Atributos();
    }
	at2=eopUn
	{
		at1= new Atributos(at2);
	}	
	; 

cast returns [String codOp=""] : 
	CAST_INT
    {
        codOp = "castInt";
    }
	|
	CAST_REAL
    {
        codOp = "castReal";
    };

eopUn returns [Atributos at1=null] {String codOp; Atributos at2;}: //Cast EopCast | EopCast
    
    {
    	 at1= new Atributos();
    	 at2= new Atributos();
    }
    codOp=cast
    at2=eopCast
    {	
    	at1= new Atributos(at2);
    	if (!at2.hayError()){
	    	if (codOp.equals("castInt")){
	    		at1.setTipo("int");
	    	}
	    	else 
	    		at1.setTipo("real");
	    	cod = cod + "\n" + gestor.casting(at1.getTipo());
    	}
    }
	|
	{
    	 at1= new Atributos();
    	 at2= new Atributos();
    }
	at2=eopCast
    {
    	at1= new Atributos(at2);
    };

eopCast returns [Atributos at1=null] {Atributos at2;} : //( E ) | iden | numero
    {
    	at1 = new Atributos();
    	at2 = new Atributos();
    }
    ABRE_PARENTESIS 
    at2=e
    CIERRA_PARENTESIS
    {	
    	at1= new Atributos(at2);
    }
	|
	i:IDEN
    {	
    	at1 = new Atributos();

        // crear errores
        at1.setErrNoExisteVar(!ts.existeID(i.getText()));
        if (!at1.isErrNoExisteVar()){
            at1.setErrVarNoIni(!ts.estaIni(i.getText()));
        }
        at1.setErrTipo(false);
        at1.setIden(i);

        if (at1.hayError()){
        	at1.setTipo("terr");
        	listaErrores = listaErrores + "\n"  + "Linea: "+ i.getLine() +" . " + gestor.mensaje(at1.isErrNoExisteVar(), false, at1.isErrVarNoIni(), false, i.getText(), null, null);
        }
        else{
        	at1.setTipo(ts.dameTipo(i.getText()));
           	cod = cod + "\n" + gestor.apilaDir(ts.dameDir(i.getText()));
        }
    }
    |
	lex:NUM_REAL
    {
        at1 = new Atributos();
        at1.setErrNoExisteVar(false);
        at1.setErrVarNoIni(false);
        at1.setTipo(gestor.dameTipoNum(lex.getText()));
        at1.setErrTipo(false);
        at1.setIden(lex);
        
        cod = cod + "\n" + gestor.apila(gestor.valorDe(lex.getText()));
    };

    
