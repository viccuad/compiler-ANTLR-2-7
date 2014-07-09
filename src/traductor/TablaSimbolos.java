package traductor;
import java.util.HashMap;

public class TablaSimbolos {

	private HashMap<String, ElementoTS> tabla;

    public TablaSimbolos() {
		super();
		this.crearTS();
	}
		
	public void crearTS(){
		tabla = new HashMap<String, ElementoTS>();	
	}
	
	public void anyadeID(String iden, String tipo, int dir){
		//anadir variable a la tabla y crear su objeto
		if (tabla == null)
			crearTS();
		
		ElementoTS elem = new ElementoTS(tipo, dir, false);
		tabla.put(iden,elem);
	}
	
	public boolean existeID(String iden){
		return tabla.containsKey(iden);
	}



	public int dameDir(String iden){
		return tabla.get(iden).getDir();
	}
	
	public String dameTipo(String iden){
		ElementoTS e=tabla.get(iden);
		//if (e!=null)
			return tabla.get(iden).getTipo();
		//else return "";
	}
	
	
	public boolean estaIni(String iden){
		ElementoTS e=tabla.get(iden);
		if (e!=null)
			return e.isInicializada();
		else return false;
	}
	
	public String reservaMem(int dir){
		
		return "";
		
	}
	

	public void inicializaID(String iden){
		if(tabla.containsKey(iden)){
			ElementoTS elem = tabla.get(iden);
			elem.setInicializada(true);
		}
	}

	public HashMap<String, ElementoTS> getTabla() {
		return tabla;
	}

	public void setTabla(HashMap<String, ElementoTS> tabla) {
		this.tabla = tabla;
	}
	
	
}
