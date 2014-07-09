package interprete;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Interprete {

	private Stack<Double> pila ;
	private int cpila;
	private int cprog;
	private boolean P;
	private boolean errorDeLectura, errorDeEscritura;
	private HashMap<Integer, Double> memoria ;
	
	private int errorByZero;
	private String num;
	
	public Interprete(){
		pila = new Stack<Double>();
		cpila = 0;
		cprog = 0;
		memoria = new HashMap<Integer, Double>();
		
		errorByZero = 0;
	}
	
	public Stack<Double> getPila() {
		return pila;
	}

	public void setPila(Stack<Double> pila) {
		this.pila = pila;
	}

	public Integer getCpila() {
		return cpila;
	}

	public void setCpila(Integer cpila) {
		this.cpila = cpila;
	}

	public Integer getCprog() {
		return cprog;
	}

	public void setCprog(Integer cprog) {
		this.cprog = cprog;
	}

	public HashMap<Integer, Double> getMemoria() {
		return memoria;
	}

	public void setMemoria(HashMap<Integer, Double> memoria) {
		this.memoria = memoria;
	}

	public int getErrorByZero() {
		return errorByZero;
	}

	public void setErrorByZero(int errorByZero) {
		this.errorByZero = errorByZero;
	}

	public void setCpila(int cpila) {
		this.cpila = cpila;
	}

	public void setCprog(int cprog) {
		this.cprog = cprog;
	}

	public void apila(Integer numero){
		pila.push(new Double(numero));
		cpila += 1;
		cprog += 1;
	}
	
	public void apila_dir(Integer direccion){
		Double num = memoria.get(direccion);
		if (num != null){
			pila.push(num);
			cpila += 1;
			cprog += 1;
		}		
	}
	
	public void desapila_dir(Integer direccion, String tipo){
		
		if (tipo == "real"){
			Double numReal = pila.pop();
			memoria.put(direccion, numReal);
		} else {
			Integer num = pila.pop().intValue();
			memoria.put(direccion, new Double(num));
		}
		cpila -= 1;
		cprog += 1;
	}
	
	public Integer truncar(Double numero){
		return numero.intValue();
	}
	
	public void out(){
		System.out.println(pila.pop());
		cpila -= 1;
		cprog += 1;
	}
	
	public void leer(int direccion, String tipo){
		
		try{ 
	        
			Double aux = new Double(0.0);
			leer_sistema();
			
			if (tipo == "int"){ 
	            aux.valueOf(num);
	            truncar(aux);
	        }
			else if (tipo == "real"){ 
	            aux.valueOf(num);
	        }
	        memoria.put(direccion, aux);
	        // si hay errorTipo_numeros de leer_int_sistema, generamos un errorTipo_numeros en ejecuci�n
	    }catch (Exception e){ 
	        errorDeLectura = true; 
	        P = true; 
	    } 
	    cprog = cprog +1; 
	    
	}

	
public void leer_sistema() {
    
	final JFrame parent = new JFrame();
	    JButton button = new JButton();
	    
	     button.setText("Clic para introducir valor");
	     parent.add(button);
	     parent.pack();
	     parent.setVisible(true);

	     button.addActionListener(new java.awt.event.ActionListener() {

	     @Override
	     public void actionPerformed(java.awt.event.ActionEvent evt) {

	     num = JOptionPane.showInputDialog(parent,"Introduzca un numero: ",null);
	     
	     }


	     });
}
	
	public void reservaMem(Integer direccion){
		memoria.put(direccion, null);
		cprog += 1;
	}
	
	public void stop(){

		P = true;
	}

	public void escribir() {
	    if (pila.empty()) {
            //errorTipo_numeros en ejecucion
            errorDeEscritura = true;
            P = true;
        }
        else{ // ejecucion normal
		    Double aux = pila.pop();
            System.out.println(Double.toString(aux));
        }
	}
	
	public void mayor(){
		Double num1 = pila.pop();
		Double num2 = pila.pop();
		
		if (num2 > num1){
			pila.push(new Double(1));
		}
		else{
			pila.push(new Double(0));
		}
		cpila -= 1;
		cprog += 1;
	}
	
	public void menor(){
		Double num1 = pila.pop();
		Double num2 = pila.pop();
		
		if (num2 < num1){
			pila.push(new Double(1));
		}
		else{
			pila.push(new Double(0));
		}
		cpila -= 1;
		cprog += 1;
	}
	
	public void igual(){
		Double num1 = pila.pop();
		Double num2 = pila.pop();
		
		if (num2 == num1){
			pila.push(new Double(1));
		}
		else{
			pila.push(new Double(0));
		}
		cpila -= 1;
		cprog += 1;
	}

	public void mayorIg(){
		Double num1 = pila.pop();
		Double num2 = pila.pop();
		
		if (num2 >= num1){
			pila.push(new Double(1));
		}
		else{
			pila.push(new Double(0));
		}
		cpila -= 1;
		cprog += 1;
	}

	public void menorIg(){
		Double num1 = pila.pop();
		Double num2 = pila.pop();
		
		if (num2 <= num1){
			pila.push(new Double(1));
		}
		else{
			pila.push(new Double(0));
		}
		cpila -= 1;
		cprog += 1;
	}
	
	public void distinto(){
		Double num1 = pila.pop();
		Double num2 = pila.pop();
		
		if (num2 != num1){
			pila.push(new Double(1));
		}
		else{
			pila.push(new Double(0));
		}
		cpila -= 1;
		cprog += 1;
	}
	
	public void suma(){
		Double num1 = pila.pop();
		Double num2 = pila.pop();
		
		pila.push(num2 + num1);
		cpila -= 1;
		cprog += 1;
	}
	
	public void resta(){
		Double num1 = pila.pop();
		Double num2 = pila.pop();
		
		pila.push(num2 - num1);
		cpila -= 1;
		cprog += 1;
	}
	
	public void multiplicacion(){
		Double num1 = pila.pop();
		Double num2 = pila.pop();
		
		pila.push(num2 * num1);
		cpila -= 1;
		cprog += 1;
	}
	
	public void division(String tipo){
		Double num1 = pila.pop();
		
		if (num1 == 0) {
			errorByZero = 1;
		} else {
			if (tipo == "real"){
				// Div real
				Double num2 = pila.pop();
				pila.push(num2/num1);
			} else {
				// Div entera
				Integer n1 = num1.intValue();
				Integer n2 = pila.pop().intValue();
				pila.push(new Double(n2/n1));
			}
		}
		
		cpila -= 1;
		cprog += 1;
	}
	
	public void mod(){
		Double num1 = pila.pop();
		
		if (num1 == 0) {
			errorByZero = 1;
		} else {
			Double num2 = pila.pop();
			pila.push(num2 % num1);
		}
		
		cpila -= 1;
		cprog += 1;
	}
	
	public void or(){
		Double num1 = pila.pop();
		Double num2 = pila.pop();
		
		if ((num1 == 0 && num2 == 0)) {
			pila.push(new Double(0));
		} else {
			pila.push(new Double(1));
		}
		
		cpila -= 1;
		cprog += 1;
	}
	
	public void and(){
		Double num1 = pila.pop();
		Double num2 = pila.pop();
		
		if ((num1 == 1 && num2 == 1)) {
			pila.push(new Double(1));
		} else {
			pila.push(new Double(0));
		}
		
		cpila -= 1;
		cprog += 1;
	}
	
	public void not() {
		Double num1 = pila.pop();
		
		if (num1 != 0) {
			pila.push(new Double(0));
		} else {
			pila.push(new Double(1));
		}
		
		cprog += 1;
	}
	
	public void cambioSigno() {
		Double num1 = pila.pop();
		pila.push(-num1);
		cprog += 1;
	}
	
}
