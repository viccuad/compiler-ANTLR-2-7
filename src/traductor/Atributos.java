package traductor;

import antlr.Token;

public class Atributos {
	private String tipo;
	private boolean errVarNoIni,errTipo,errNoExisteVar,errYaExisteVar;
	private Token iden;
	
	
	public Atributos() {
		
		this.tipo = "";
		this.errVarNoIni = false;
		this.errTipo = false;
		this.errNoExisteVar = false;
		this.errYaExisteVar = false;
		this.iden = null;
	}

    public Atributos(Atributos at1) {

        this.tipo = at1.getTipo();
        this.errVarNoIni = at1.isErrVarNoIni();
        this.errTipo = at1.isErrTipo();
        this.errNoExisteVar = at1.isErrNoExisteVar();
        this.errYaExisteVar = at1.isErrYaExisteVar();
        this.iden = at1.getIden();
    }
	
	public Atributos(String tipo, boolean errVarNoIni, boolean errTipo,
			boolean errNoExisteVar, boolean errYaExisteVar) {
		
		this.tipo = tipo;
		this.errVarNoIni = errVarNoIni;
		this.errTipo = errTipo;
		this.errNoExisteVar = errNoExisteVar;
		this.errYaExisteVar = errYaExisteVar;
	}



	public String getTipo() {
		return tipo;
	}



	public void setTipo(String tipo) {
		this.tipo = tipo;
	}



	public boolean isErrVarNoIni() {
		return errVarNoIni;
	}



	public void setErrVarNoIni(boolean errVarNoIni) {
		this.errVarNoIni = errVarNoIni;
	}



	public boolean isErrTipo() {
		return errTipo;
	}



	public void setErrTipo(boolean errTipo) {
		this.errTipo = errTipo;
	}



	public boolean isErrNoExisteVar() {
		return errNoExisteVar;
	}



	public void setErrNoExisteVar(boolean errNoExisteVar) {
		this.errNoExisteVar = errNoExisteVar;
	}



	public boolean isErrYaExisteVar() {
		return errYaExisteVar;
	}



	public void setErrYaExisteVar(boolean errYaExisteVar) {
		this.errYaExisteVar = errYaExisteVar;
	}
	
    public antlr.Token getIden() {
        return iden;
    }

    public void setIden(antlr.Token t) {
        this.iden = t;
    }

    public boolean hayError(){
		return (isErrNoExisteVar() || isErrTipo() || isErrVarNoIni() || isErrYaExisteVar());
	}



}
