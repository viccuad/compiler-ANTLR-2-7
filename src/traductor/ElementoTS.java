package traductor;

public class ElementoTS {
	
		private String tipo;
		private int dir;
		private boolean inicializada;
		
		public ElementoTS(String t, int d, boolean inicializada){
			this.tipo = t;
			this.dir=d;
			this.inicializada = inicializada;
		}
		
		public String getTipo() {
			return tipo;
		}
		public void setTipo(String t) {
			this.tipo = t;
		}
		public int getDir() {
			return dir;
		}
		public void setDir(int dir) {
			this.dir = dir;
		}
		public boolean isInicializada() {
			return inicializada;
		}

		public void setInicializada(boolean inicializada) {
			this.inicializada = inicializada;
		}
		
		
		
}
