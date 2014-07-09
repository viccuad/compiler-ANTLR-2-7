// $ANTLR : "AnalizadorSintactico.g" -> "AnalizadorSintactico.java"$

package traductor;

import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;
import antlr.collections.AST;
import java.util.Hashtable;
import antlr.ASTFactory;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;

public class AnalizadorSintactico extends antlr.LLkParser       implements AnalizadorSintacticoTokenTypes
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

protected AnalizadorSintactico(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public AnalizadorSintactico(TokenBuffer tokenBuf) {
  this(tokenBuf,2);
}

protected AnalizadorSintactico(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public AnalizadorSintactico(TokenStream lexer) {
  this(lexer,2);
}

public AnalizadorSintactico(ParserSharedInputState state) {
  super(state,2);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

	public final void prog() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST prog_AST = null;
		
		decs();
		astFactory.addASTChild(currentAST, returnAST);
		accs();
		astFactory.addASTChild(currentAST, returnAST);
		prog_AST = (AST)currentAST.root;
		returnAST = prog_AST;
	}
	
	public final void decs() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST decs_AST = null;
		
		
			dir = 0;
		
		dec();
		astFactory.addASTChild(currentAST, returnAST);
		rdecs();
		astFactory.addASTChild(currentAST, returnAST);
		decs_AST = (AST)currentAST.root;
		returnAST = decs_AST;
	}
	
	public final void accs() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST accs_AST = null;
		
		acc();
		astFactory.addASTChild(currentAST, returnAST);
		raccs();
		astFactory.addASTChild(currentAST, returnAST);
		accs_AST = (AST)currentAST.root;
		returnAST = accs_AST;
	}
	
	public final void dec() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST dec_AST = null;
		Token  i = null;
		AST i_AST = null;
		boolean errYaExisteVar; String stipo;
		
		stipo=tipo();
		astFactory.addASTChild(currentAST, returnAST);
		i = LT(1);
		i_AST = astFactory.create(i);
		astFactory.addASTChild(currentAST, i_AST);
		match(IDEN);
		AST tmp1_AST = null;
		tmp1_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp1_AST);
		match(PUNT_COMA);
		
		errYaExisteVar = ts.existeID(i.getText());
		System.out.println("identificador: " + " " +i.getText());
		if (errYaExisteVar){
			listaErrores = listaErrores + "\n" + "Linea: "+ i.getLine() +" . "+  gestor.mensaje(false, errYaExisteVar, false, false, i.getText(), null, null); 
		}else{
			 ts.anyadeID(i.getText(),stipo,dir);
			 cod = cod + "\n" + ts.reservaMem(ts.dameDir(i.getText()));
			 dir++;
		}      
		
		dec_AST = (AST)currentAST.root;
		returnAST = dec_AST;
	}
	
	public final void rdecs() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST rdecs_AST = null;
		
		switch ( LA(1)) {
		case REAL:
		case INT:
		{
			dec();
			astFactory.addASTChild(currentAST, returnAST);
			rdecs();
			astFactory.addASTChild(currentAST, returnAST);
			rdecs_AST = (AST)currentAST.root;
			break;
		}
		case ABRE_PARENTESIS:
		case MENOS:
		case NEG:
		case CAST_INT:
		case CAST_REAL:
		case IN:
		case OUT:
		case IDEN:
		case NUM_REAL:
		{
			rdecs_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = rdecs_AST;
	}
	
	public final String  tipo() throws RecognitionException, TokenStreamException {
		String tipo;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST tipo_AST = null;
		
		switch ( LA(1)) {
		case INT:
		{
			AST tmp2_AST = null;
			tmp2_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp2_AST);
			match(INT);
			tipo="int";
			tipo_AST = (AST)currentAST.root;
			break;
		}
		case REAL:
		{
			AST tmp3_AST = null;
			tmp3_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp3_AST);
			match(REAL);
			tipo="real";
			tipo_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = tipo_AST;
		return tipo;
	}
	
	public final void acc() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST acc_AST = null;
		
		at1=e();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp4_AST = null;
		tmp4_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp4_AST);
		match(PUNT_COMA);
		
		
		acc_AST = (AST)currentAST.root;
		returnAST = acc_AST;
	}
	
	public final void raccs() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST raccs_AST = null;
		
		switch ( LA(1)) {
		case ABRE_PARENTESIS:
		case MENOS:
		case NEG:
		case CAST_INT:
		case CAST_REAL:
		case IN:
		case OUT:
		case IDEN:
		case NUM_REAL:
		{
			acc();
			astFactory.addASTChild(currentAST, returnAST);
			raccs();
			astFactory.addASTChild(currentAST, returnAST);
			raccs_AST = (AST)currentAST.root;
			break;
		}
		case EOF:
		{
			
			cod = cod + "\n" + gestor.stop();
			
			raccs_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = raccs_AST;
	}
	
	public final Atributos  e() throws RecognitionException, TokenStreamException {
		Atributos at1 = null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST e_AST = null;
		Token  i = null;
		AST i_AST = null;
		String tipo1;
		
		switch ( LA(1)) {
		case IN:
		{
			at1 = new Atributos();
			AST tmp5_AST = null;
			tmp5_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp5_AST);
			match(IN);
			i = LT(1);
			i_AST = astFactory.create(i);
			astFactory.addASTChild(currentAST, i_AST);
			match(IDEN);
			
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
			
			e_AST = (AST)currentAST.root;
			break;
		}
		case OUT:
		{
			at1 = new Atributos();
			AST tmp6_AST = null;
			tmp6_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp6_AST);
			match(OUT);
			at1=ein();
			astFactory.addASTChild(currentAST, returnAST);
			
			//if (!at1.hayError()){
				cod = cod  + "\n" + gestor.escribir();
						//}
			
			e_AST = (AST)currentAST.root;
			break;
		}
		case ABRE_PARENTESIS:
		case MENOS:
		case NEG:
		case CAST_INT:
		case CAST_REAL:
		case IDEN:
		case NUM_REAL:
		{
			at1 = new Atributos();
			at1=ein();
			astFactory.addASTChild(currentAST, returnAST);
			e_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = e_AST;
		return at1;
	}
	
	public final Atributos  ein() throws RecognitionException, TokenStreamException {
		Atributos at1= null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST ein_AST = null;
		Token  i = null;
		AST i_AST = null;
		String tipo2; String tipo1;
		
		if ((LA(1)==IDEN) && (LA(2)==ASIGNA)) {
			i = LT(1);
			i_AST = astFactory.create(i);
			astFactory.addASTChild(currentAST, i_AST);
			match(IDEN);
			AST tmp7_AST = null;
			tmp7_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp7_AST);
			match(ASIGNA);
			at1=ein();
			astFactory.addASTChild(currentAST, returnAST);
				
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
			
			ein_AST = (AST)currentAST.root;
		}
		else if ((_tokenSet_0.member(LA(1))) && (_tokenSet_1.member(LA(2)))) {
			at1 = new Atributos();
			at1=eigual();
			astFactory.addASTChild(currentAST, returnAST);
			ein_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		returnAST = ein_AST;
		return at1;
	}
	
	public final Atributos  eigual() throws RecognitionException, TokenStreamException {
		Atributos at1=null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST eigual_AST = null;
		Atributos at2,at3;
		
		
			at1 = new Atributos();
			at2 = new Atributos();
			at3 = new Atributos();
		
		at2=eopComp();
		astFactory.addASTChild(currentAST, returnAST);
		at3=feigual(at2);
		astFactory.addASTChild(currentAST, returnAST);
		
		at1 = new Atributos(at3);
		
		eigual_AST = (AST)currentAST.root;
		returnAST = eigual_AST;
		return at1;
	}
	
	public final String  opComp() throws RecognitionException, TokenStreamException {
		String codOp="";
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST opComp_AST = null;
		
		switch ( LA(1)) {
		case MENOR:
		{
			AST tmp8_AST = null;
			tmp8_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp8_AST);
			match(MENOR);
			
			codOp = "menor";
			
			opComp_AST = (AST)currentAST.root;
			break;
		}
		case MAYOR:
		{
			AST tmp9_AST = null;
			tmp9_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp9_AST);
			match(MAYOR);
			
			codOp = "mayor";
			
			opComp_AST = (AST)currentAST.root;
			break;
		}
		case MENORIGUAL:
		{
			AST tmp10_AST = null;
			tmp10_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp10_AST);
			match(MENORIGUAL);
			
			codOp = "menorIgual";
			
			opComp_AST = (AST)currentAST.root;
			break;
		}
		case MAYORIGUAL:
		{
			AST tmp11_AST = null;
			tmp11_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp11_AST);
			match(MAYORIGUAL);
			
			codOp = "mayorIgual";
			
			opComp_AST = (AST)currentAST.root;
			break;
		}
		case IGUAL:
		{
			AST tmp12_AST = null;
			tmp12_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp12_AST);
			match(IGUAL);
			
			codOp = "igual";
			
			opComp_AST = (AST)currentAST.root;
			break;
		}
		case DISTINTO:
		{
			AST tmp13_AST = null;
			tmp13_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp13_AST);
			match(DISTINTO);
			
			codOp = "distinto";
			
			opComp_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = opComp_AST;
		return codOp;
	}
	
	public final Atributos  eopComp() throws RecognitionException, TokenStreamException {
		Atributos at1= null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST eopComp_AST = null;
		Atributos at2,at3;
		
		
			at1 = new Atributos();
			at2 = new Atributos();
			at3 = new Atributos();
		
		at2=eopAd();
		astFactory.addASTChild(currentAST, returnAST);
		at3=reopComp(at2);
		astFactory.addASTChild(currentAST, returnAST);
		
		at1 = new Atributos(at3);
		
		eopComp_AST = (AST)currentAST.root;
		returnAST = eopComp_AST;
		return at1;
	}
	
	public final Atributos  feigual(
		Atributos at1h
	) throws RecognitionException, TokenStreamException {
		Atributos at1=null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST feigual_AST = null;
		Atributos at2; String codOp;
		
		switch ( LA(1)) {
		case MENOR:
		case MAYOR:
		case MENORIGUAL:
		case MAYORIGUAL:
		case IGUAL:
		case DISTINTO:
		{
			
				at1 = new Atributos();
				at2 = new Atributos();
			
			codOp=opComp();
			astFactory.addASTChild(currentAST, returnAST);
			at2=eopComp();
			astFactory.addASTChild(currentAST, returnAST);
			
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
			
			feigual_AST = (AST)currentAST.root;
			break;
		}
		case CIERRA_PARENTESIS:
		case PUNT_COMA:
		{
				at1 = new Atributos(at1h);
			
			feigual_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = feigual_AST;
		return at1;
	}
	
	public final Operador  opAd() throws RecognitionException, TokenStreamException {
		Operador op=null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST opAd_AST = null;
		String codOp;
		
		switch ( LA(1)) {
		case MAS:
		case MENOS:
		{
			codOp=opAdArit();
			astFactory.addASTChild(currentAST, returnAST);
				
				op = new Operador();
				    op.setTipoOp("arit");
				    op.setCodOp(codOp);
			
			opAd_AST = (AST)currentAST.root;
			break;
		}
		case OR:
		{
			codOp=opAdLog();
			astFactory.addASTChild(currentAST, returnAST);
			
				    op = new Operador();
				    op.setTipoOp("log");
				    op.setCodOp(codOp);
			
			opAd_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = opAd_AST;
		return op;
	}
	
	public final String  opAdArit() throws RecognitionException, TokenStreamException {
		String codOp="";
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST opAdArit_AST = null;
		
		switch ( LA(1)) {
		case MAS:
		{
			AST tmp14_AST = null;
			tmp14_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp14_AST);
			match(MAS);
			
			codOp = "suma";
			
			opAdArit_AST = (AST)currentAST.root;
			break;
		}
		case MENOS:
		{
			AST tmp15_AST = null;
			tmp15_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp15_AST);
			match(MENOS);
			
			codOp = "resta";
			
			opAdArit_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = opAdArit_AST;
		return codOp;
	}
	
	public final String  opAdLog() throws RecognitionException, TokenStreamException {
		String codOp="";
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST opAdLog_AST = null;
		
		AST tmp16_AST = null;
		tmp16_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp16_AST);
		match(OR);
		
		codOp = "or";
		
		opAdLog_AST = (AST)currentAST.root;
		returnAST = opAdLog_AST;
		return codOp;
	}
	
	public final Atributos  eopAd() throws RecognitionException, TokenStreamException {
		Atributos at1=null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST eopAd_AST = null;
		Atributos at2,at3,at3h;
		
		
			at1 = new Atributos();
			at2 = new Atributos();
			at3h = new Atributos();
			at3 = new Atributos();
		
		at2=eopMul();
		astFactory.addASTChild(currentAST, returnAST);
		
		at3h = new Atributos(at2);
		
		at3=reopAd(at3h);
		astFactory.addASTChild(currentAST, returnAST);
		
		at1 = new Atributos(at3);
		
		eopAd_AST = (AST)currentAST.root;
		returnAST = eopAd_AST;
		return at1;
	}
	
	public final Atributos  reopComp(
		Atributos at1h
	) throws RecognitionException, TokenStreamException {
		Atributos at1=null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST reopComp_AST = null;
		Operador op; Atributos at2,at3,at3h;
		
		switch ( LA(1)) {
		case MAS:
		case MENOS:
		case OR:
		{
			
				at1 = new Atributos();
				at2 = new Atributos();
				at3h = new Atributos();
				at3 = new Atributos();
				op = new Operador();
			
			op=opAd();
			astFactory.addASTChild(currentAST, returnAST);
			at2=eopAd();
			astFactory.addASTChild(currentAST, returnAST);
			
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
				
			at3=reopComp(at3h);
			astFactory.addASTChild(currentAST, returnAST);
			
			at1 = new Atributos(at3);
			
			reopComp_AST = (AST)currentAST.root;
			break;
		}
		case CIERRA_PARENTESIS:
		case PUNT_COMA:
		case MENOR:
		case MAYOR:
		case MENORIGUAL:
		case MAYORIGUAL:
		case IGUAL:
		case DISTINTO:
		{
			
				at1 = new Atributos(at1h);
				
			reopComp_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = reopComp_AST;
		return at1;
	}
	
	public final Operador  opMul() throws RecognitionException, TokenStreamException {
		Operador op=null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST opMul_AST = null;
		String codOp;
		
		switch ( LA(1)) {
		case MULT:
		case DIV:
		{
			op = new Operador();
			codOp=opMulArit();
			astFactory.addASTChild(currentAST, returnAST);
			
				op.setTipoOp("arit");
				op.setCodOp(codOp);
			
			opMul_AST = (AST)currentAST.root;
			break;
		}
		case AND:
		case MOD:
		{
			codOp=opMulLog();
			astFactory.addASTChild(currentAST, returnAST);
			
				op = new Operador();
				op.setTipoOp("log");
				op.setCodOp(codOp);
			
			opMul_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = opMul_AST;
		return op;
	}
	
	public final String  opMulArit() throws RecognitionException, TokenStreamException {
		String codOp="";
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST opMulArit_AST = null;
		
		switch ( LA(1)) {
		case MULT:
		{
			AST tmp17_AST = null;
			tmp17_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp17_AST);
			match(MULT);
			
				codOp = "mult";
			
			opMulArit_AST = (AST)currentAST.root;
			break;
		}
		case DIV:
		{
			AST tmp18_AST = null;
			tmp18_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp18_AST);
			match(DIV);
			
			codOp = "div";
			
			opMulArit_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = opMulArit_AST;
		return codOp;
	}
	
	public final String  opMulLog() throws RecognitionException, TokenStreamException {
		String codOp="";
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST opMulLog_AST = null;
		
		switch ( LA(1)) {
		case AND:
		{
			AST tmp19_AST = null;
			tmp19_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp19_AST);
			match(AND);
			
			codOp = "and";
			
			opMulLog_AST = (AST)currentAST.root;
			break;
		}
		case MOD:
		{
			AST tmp20_AST = null;
			tmp20_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp20_AST);
			match(MOD);
			
			codOp = "mod";
			
			opMulLog_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = opMulLog_AST;
		return codOp;
	}
	
	public final Atributos  eopMul() throws RecognitionException, TokenStreamException {
		Atributos at1=null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST eopMul_AST = null;
		String codOp; Atributos at2;
		
		switch ( LA(1)) {
		case NEG:
		{
			
				 at1= new Atributos();
				 at2= new Atributos();
			
			codOp=opUnNeg();
			astFactory.addASTChild(currentAST, returnAST);
			at2=eopUn();
			astFactory.addASTChild(currentAST, returnAST);
			
					at1= new Atributos(at2);
			at1.setTipo("int");
			at1.setErrTipo(!at2.getTipo().equals("int"));
			
			if (at1.hayError()){ // si hay error, mostrarlos
				        	listaErrores =
				            	listaErrores + "\n" + "Linea: "+ at2.getIden().getLine() +" . " + gestor.mensaje(false, false, false, at1.isErrTipo(), at2.getIden().getText(), at1.getTipo(), at2.getTipo());
			}else{
			cod = cod + "\n" + codOp;
			}
			
			
			eopMul_AST = (AST)currentAST.root;
			break;
		}
		case MENOS:
		{
			
				 at1= new Atributos();
				 at2= new Atributos();
			
			codOp=opUnCambio();
			astFactory.addASTChild(currentAST, returnAST);
			at2=eopUn();
			astFactory.addASTChild(currentAST, returnAST);
			
				at1= new Atributos(at2);
			at1.setTipo(at2.getTipo());
			at1.setErrTipo(!at2.getTipo().equalsIgnoreCase("int") && !at2.getTipo().equalsIgnoreCase("real"));
			
			
			if (at1.hayError()){ // si hay error, mostrarlos
			listaErrores =
			listaErrores + "\n" + "Linea: "+ at2.getIden().getLine() +" . " + gestor.mensaje(false, false, false, at1.isErrTipo(), at2.getIden().getText(), at1.getTipo(), at2.getTipo());
			}else{
			cod = cod + "\n" + codOp;
			}
			
			eopMul_AST = (AST)currentAST.root;
			break;
		}
		case ABRE_PARENTESIS:
		case CAST_INT:
		case CAST_REAL:
		case IDEN:
		case NUM_REAL:
		{
			
				 at1= new Atributos();
				 at2= new Atributos();
			
			at2=eopUn();
			astFactory.addASTChild(currentAST, returnAST);
			
					at1= new Atributos(at2);
				
			eopMul_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = eopMul_AST;
		return at1;
	}
	
	public final Atributos  reopAd(
		Atributos at1h
	) throws RecognitionException, TokenStreamException {
		Atributos at1=null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST reopAd_AST = null;
		Atributos at2, at3,at3h; Operador op;
		
		switch ( LA(1)) {
		case MULT:
		case DIV:
		case AND:
		case MOD:
		{
			
				at1 = new Atributos();
				at2 = new Atributos();
				at3h = new Atributos();
				at3 = new Atributos();
				op = new Operador();
			
			op=opMul();
			astFactory.addASTChild(currentAST, returnAST);
			at2=eopMul();
			astFactory.addASTChild(currentAST, returnAST);
				
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
			
			at3=reopAd(at3h);
			astFactory.addASTChild(currentAST, returnAST);
			
			at1 =  new Atributos(at3);
			
			reopAd_AST = (AST)currentAST.root;
			break;
		}
		case CIERRA_PARENTESIS:
		case PUNT_COMA:
		case MENOR:
		case MAYOR:
		case MENORIGUAL:
		case MAYORIGUAL:
		case IGUAL:
		case DISTINTO:
		case MAS:
		case MENOS:
		case OR:
		{
				
					at1 = new Atributos(at1h);
				
			reopAd_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = reopAd_AST;
		return at1;
	}
	
	public final String  opUnNeg() throws RecognitionException, TokenStreamException {
		String codOp="";
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST opUnNeg_AST = null;
		
		AST tmp21_AST = null;
		tmp21_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp21_AST);
		match(NEG);
		
		codOp = "neg";
		
		opUnNeg_AST = (AST)currentAST.root;
		returnAST = opUnNeg_AST;
		return codOp;
	}
	
	public final String  opUnCambio() throws RecognitionException, TokenStreamException {
		String codOp="";
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST opUnCambio_AST = null;
		
		AST tmp22_AST = null;
		tmp22_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp22_AST);
		match(MENOS);
		
		codOp = "cds";
		
		opUnCambio_AST = (AST)currentAST.root;
		returnAST = opUnCambio_AST;
		return codOp;
	}
	
	public final Atributos  eopUn() throws RecognitionException, TokenStreamException {
		Atributos at1=null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST eopUn_AST = null;
		String codOp; Atributos at2;
		
		switch ( LA(1)) {
		case CAST_INT:
		case CAST_REAL:
		{
			
				 at1= new Atributos();
				 at2= new Atributos();
			
			codOp=cast();
			astFactory.addASTChild(currentAST, returnAST);
			at2=eopCast();
			astFactory.addASTChild(currentAST, returnAST);
				
				at1= new Atributos(at2);
				if (!at2.hayError()){
				    	if (codOp.equals("castInt")){
				    		at1.setTipo("int");
				    	}
				    	else 
				    		at1.setTipo("real");
				    	cod = cod + "\n" + gestor.casting(at1.getTipo());
				}
			
			eopUn_AST = (AST)currentAST.root;
			break;
		}
		case ABRE_PARENTESIS:
		case IDEN:
		case NUM_REAL:
		{
			
				 at1= new Atributos();
				 at2= new Atributos();
			
			at2=eopCast();
			astFactory.addASTChild(currentAST, returnAST);
			
				at1= new Atributos(at2);
			
			eopUn_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = eopUn_AST;
		return at1;
	}
	
	public final String  cast() throws RecognitionException, TokenStreamException {
		String codOp="";
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST cast_AST = null;
		
		switch ( LA(1)) {
		case CAST_INT:
		{
			AST tmp23_AST = null;
			tmp23_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp23_AST);
			match(CAST_INT);
			
			codOp = "castInt";
			
			cast_AST = (AST)currentAST.root;
			break;
		}
		case CAST_REAL:
		{
			AST tmp24_AST = null;
			tmp24_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp24_AST);
			match(CAST_REAL);
			
			codOp = "castReal";
			
			cast_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = cast_AST;
		return codOp;
	}
	
	public final Atributos  eopCast() throws RecognitionException, TokenStreamException {
		Atributos at1=null;
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST eopCast_AST = null;
		Token  i = null;
		AST i_AST = null;
		Token  lex = null;
		AST lex_AST = null;
		Atributos at2;
		
		switch ( LA(1)) {
		case ABRE_PARENTESIS:
		{
			
				at1 = new Atributos();
				at2 = new Atributos();
			
			AST tmp25_AST = null;
			tmp25_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp25_AST);
			match(ABRE_PARENTESIS);
			at2=e();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp26_AST = null;
			tmp26_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp26_AST);
			match(CIERRA_PARENTESIS);
				
				at1= new Atributos(at2);
			
			eopCast_AST = (AST)currentAST.root;
			break;
		}
		case IDEN:
		{
			i = LT(1);
			i_AST = astFactory.create(i);
			astFactory.addASTChild(currentAST, i_AST);
			match(IDEN);
				
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
			
			eopCast_AST = (AST)currentAST.root;
			break;
		}
		case NUM_REAL:
		{
			lex = LT(1);
			lex_AST = astFactory.create(lex);
			astFactory.addASTChild(currentAST, lex_AST);
			match(NUM_REAL);
			
			at1 = new Atributos();
			at1.setErrNoExisteVar(false);
			at1.setErrVarNoIni(false);
			at1.setTipo(gestor.dameTipoNum(lex.getText()));
			at1.setErrTipo(false);
			at1.setIden(lex);
			
			cod = cod + "\n" + gestor.apila(gestor.valorDe(lex.getText()));
			
			eopCast_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = eopCast_AST;
		return at1;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"\"real\"",
		"\"int\"",
		"NUEVA_LINEA",
		"ESPACIO_TAB",
		"BLANCO",
		"ABRE_PARENTESIS",
		"CIERRA_PARENTESIS",
		"PUNTO",
		"FIN",
		"PUNT_COMA",
		"SEPARADOR",
		"MENOR",
		"MAYOR",
		"MENORIGUAL",
		"MAYORIGUAL",
		"IGUAL",
		"ASIGNA",
		"DISTINTO",
		"MAS",
		"MENOS",
		"OR",
		"MULT",
		"DIV",
		"AND",
		"MOD",
		"NEG",
		"AMBER",
		"CAST_INT",
		"CAST_REAL",
		"IN",
		"OUT",
		"LETRA",
		"DIGITO",
		"IDEN",
		"NATURAL",
		"DECIMAL",
		"NUM_ENTERO",
		"NUM_REAL",
		"COMENTARIO"
	};
	
	protected void buildTokenTypeASTClassMap() {
		tokenTypeToASTClassMap=null;
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 2343449920000L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 2369747133952L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	
	}
