package traductor;

public class Gestor {
	
public String apilaDir(int dir){
	
	return "apilaDir(" + Integer.toString(dir)+")";
	
}

public String desapilaDir(int dir, String tipo){
	
	return "desapilaDir(" + Integer.toString(dir)+ "," + tipo +  ")";
	
}

public String apila(float num){
	String s = Float.toString(num);
	return "apila(" + s +")"; 
}

public String casting(String tipo){
	return "casting(" + tipo +")"; 
}



public float valorDe(String lex){
	float f = Float.parseFloat(lex);
	return f;
}

public String leer(int dir, String tipo){
	
	return "leer(" + Integer.toString(dir)+ "," + tipo + ")";
	
}


public String dameTipoNum(String num){
	if (num.contains(".")){
		return "real";
	}
	else return "int";
}

public String escribir(){
	
	return "escribir()";
	
}

public String mensaje(boolean errNoExisteVar, boolean errYaExisteVar, boolean errVarNoIni, boolean errTipo, String iden, String tipo1, String tipo2){
	
	String err="";
	
	if (errNoExisteVar){
		err += "La variable " + iden + " no ha sido declarada previamente. ";
	}
	if (errYaExisteVar){
		err += "La variable " + iden + " ya ha sido declarada previamente. ";
	}
	if (errVarNoIni){
		err += "La variable " + iden + " no ha sido inicializada previamente. ";
	}
	if (errTipo){
		err += "Error de tipo en la variable " + iden + ": Se esperaba " + tipo1 + " y se recibio " + tipo2 + ". ";
	}
	return err;
	
}

public String stop(){
	
	return "stop()";
	
}


}
