package main;


import antlr.ANTLRException;
import antlr.Token;
import traductor.AnalizadorLexico;
import traductor.AnalizadorSintactico;
import traductor.ElementoTS;
import traductor.TablaSimbolos;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Element;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;


public class Interfaz extends JFrame implements ActionListener {

	private static final long serialVersionUID = 7139285601475792936L;
	
	private JTextArea textAreaCodigoFuente, lineas, textAreaTokens, textAreaCodigoPila, textAreaErrores;
	private JMenuItem cargarCodigo,traductor;
	private DefaultTableModel tSimbolos;
	private File f ;
	private static FileInputStream fich;
	private static FileInputStream fich2;
	private String listadoErrores = "";
	
	public Interfaz(){		
		// Creamos las distintas areas de texto y tabla de simbolos
		textAreaCodigoFuente = new JTextArea(10,50);
        lineas = new JTextArea("1");
        lineas.setBackground(Color.LIGHT_GRAY);
        lineas.setEditable(false);
        textAreaTokens = new JTextArea(8,20);
	    textAreaCodigoPila = new JTextArea(18,20);
	    textAreaErrores = new JTextArea(10,15);
	    tSimbolos = new DefaultTableModel();

	    //Anadimos los distintos campos a la tabla de simbolos
	    tSimbolos.addColumn("Id");
	    tSimbolos.addColumn("Tipo");
	    tSimbolos.addColumn("Dirección");
	    tSimbolos.addColumn("Inicializada");
	    
	    // Deshabilitamos las areas de texto para que el usuario no las pueda alterar
	    textAreaTokens.setEditable(false);
	    textAreaCodigoPila.setEditable(false);
	    textAreaErrores.setEditable(false);

        // codigo para el nÃºmero de lÃ­neas
        textAreaCodigoFuente.getDocument().addDocumentListener(new DocumentListener(){
            public String getText(){
                int caretPosition = textAreaCodigoFuente.getDocument().getLength();
                Element root = textAreaCodigoFuente.getDocument().getDefaultRootElement();
                String text = "1" + System.getProperty("line.separator");
                for(int i = 2; i < root.getElementIndex( caretPosition ) + 2; i++){
                    text += i + System.getProperty("line.separator");
                }
                return text;
            }
            @Override
            public void changedUpdate(DocumentEvent de) {
                lineas.setText(getText());
            }

            @Override
            public void insertUpdate(DocumentEvent de) {
                lineas.setText(getText());
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                lineas.setText(getText());
            }

        });

	    inicializarInterfaz();
		
	}
	
	public static void main(String []args){
		Interfaz interfaz = new Interfaz();
		interfaz.setVisible(true);
		interfaz.setEnabled(true);
		interfaz.pack();
	}
	
	public void inicializarInterfaz(){
		setTitle("Practica PLG Grupo 4");
		setJMenuBar(getMenuPrincipal());
		this.setContentPane(getPanelCanvas());
	}
	
	public JPanel getPanelCanvas() {
		// Creamos el panel en donde iran todos los componentes de la pantalla de compilacion
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		
		/* Creamos el panel de la izquierda, que contendra el area para escribir el codigo del programa */
		JPanel panelIzquierda = new JPanel();
		panelIzquierda.setLayout(new BorderLayout());
		//Para ajustar al tamaÃ±o de la ventana aunque pongamos un tamaÃ±o excesivo para TextAreaCodigoFuente
		textAreaCodigoFuente.setLineWrap(true);
		JScrollPane scrollCodigoFuente = new JScrollPane();
        scrollCodigoFuente.getViewport().add(textAreaCodigoFuente);
        scrollCodigoFuente.setRowHeaderView(lineas);
        scrollCodigoFuente.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		//Para redimensionar el tamaÃ±o del TextAreaCodigoFuente ajustando al tamaÃ±o del panel izquierdo
		scrollCodigoFuente.setViewportView(textAreaCodigoFuente);
		// Introducimos el area del codigo en el centro del panel izquierda
		panelIzquierda.add(scrollCodigoFuente,BorderLayout.CENTER);
		// Creamos un label con la informacion de los paneles que va a tener debajo
		JLabel labelCodigoFuente = new JLabel("Código fuente");
		// Introducimos el label en el norte del panel izquierda
		panelIzquierda.add(labelCodigoFuente,BorderLayout.NORTH);
		// Introducimos el panel izquierda en la izquierda del panel principal
		panel.add(panelIzquierda, BorderLayout.WEST);
		
		
		/* Creamos el panel de la derecha, que contendra el codigo  pila, los tokens y la tabla de simbolos */
		JPanel panelDerecha = new JPanel();
		panelDerecha.setLayout(new BorderLayout());
		// Creamos la zona del codigo pila
		textAreaCodigoPila.setLineWrap(true);
	    JScrollPane scrollCodigoPila= new JScrollPane();
	    scrollCodigoPila.setViewportView(textAreaCodigoPila);
	    
	    // Creamos la zona de los tokens
	    textAreaTokens.setLineWrap(true);
	    JScrollPane scrollTokens = new JScrollPane();
	    scrollTokens.setViewportView(textAreaTokens);
	    
	    // Creamos la zona de la tabla de simbolos
	    JTable tabla = new JTable(tSimbolos);
	    tabla.setPreferredScrollableViewportSize(new Dimension(370,0));
	    JScrollPane scrollTablaSimbolos = new JScrollPane();
	    scrollTablaSimbolos.setViewportView(tabla);
	    // Creamos el label con el titulo para la tabla de simbolos y el codigo a pila
	    JLabel labelTablaYCodigo = new JLabel("Tabla de símbolos                                                                                            Código pila:                        ");
	    // Creamos el label con el titulo para separar el area de la tabla de simbolos y el codigo a pila
	    JLabel labelSepTablaCodigo = new JLabel("    ");
	    // Creamos el label para separar el fin del area de los tokens
	    JLabel labelIntroTablaCodigo = new JLabel("\n");
	    
	    // Creamos otro panel para la parte superior del panel de la derecha
	    JPanel panelDerechaSuperior = new JPanel();
	    panelDerechaSuperior.setLayout(new BorderLayout());
	    
	    // Creamos otro panel para la parte superior de la parte superior del panel superior de la derecha para los tokens
	    JPanel panelDerechaSuperiorSuperior = new JPanel();
	    panelDerechaSuperiorSuperior.setLayout(new BorderLayout());
	    // Creamos el label con el titulo para los tokens
	    JLabel labelTokens = new JLabel("Tokens");
		// Introducimos el label con el titulo de los tokens en el norte del panel superior superior
	    panelDerechaSuperiorSuperior.add(labelTokens,BorderLayout.NORTH);
	    // Introducimos el area de los tokens en el sur del panel superior superior
	    panelDerechaSuperiorSuperior.add(scrollTokens, BorderLayout.SOUTH );
	    
	    // Introducimos el panel superior superior en el norte del panel derecha superior
	    panelDerechaSuperior.add(panelDerechaSuperiorSuperior, BorderLayout.NORTH);
	    // Introducimos el label con el intro en el centro del panel derecha superior
	    panelDerechaSuperior.add(labelIntroTablaCodigo, BorderLayout.CENTER);
	    // Introducimos el label con el titulo de la tabla de simbolos y codigo a pila en el centro del panel derecha superior
	    panelDerechaSuperior.add(labelTablaYCodigo, BorderLayout.SOUTH);
	    
	    // Introducimos el panel derecha superior en el norte del panel derecha
	    panelDerecha.add(panelDerechaSuperior,BorderLayout.NORTH);
	    // Introducimos el area del codigo pila en el oeste del panel derecha
	    panelDerecha.add(scrollCodigoPila,BorderLayout.EAST);
	    // Introducimos el area de la tabla de simbolos en el este del panel derecha
	    panelDerecha.add(scrollTablaSimbolos,BorderLayout.WEST);
	    // Introducimos el label con espacios para separar las areas dede la tabla de simbolos y del codigo a pila
	    panelDerecha.add(labelSepTablaCodigo, BorderLayout.CENTER);
	   
	    // Introducimos el panel derecha, en este del panel principal
	    panel.add(panelDerecha, BorderLayout.EAST);
	    
	    // Creamos un label para separar el panel izquierda del panel de la derecha
	    JLabel labelIntroCons = new JLabel("     ");
	    // Introducimos el separador en el panel en el centro del panel principal
	    panel.add(labelIntroCons, BorderLayout.CENTER);
	    
	    /* Creamos el panel inferior, en donde tendremos el area de errores*/
	    JPanel panelInferior = new JPanel();
	    panelInferior.setLayout(new BorderLayout());
		textAreaErrores.setLineWrap(true);
	    JScrollPane scrollErrores = new JScrollPane();
	    scrollErrores.setViewportView(textAreaErrores);
	    // Creamos un label separador
	    JLabel labelIntroErrores1 = new JLabel("\n");
	    // Creamos el label con el titulo de los errores
	    JLabel labelErrores = new JLabel("Errores");
	    
	    // Introducimos el label intro en el norte del panel inferior
	    panelInferior.add(labelIntroErrores1,BorderLayout.NORTH);
	    // Introducimos el label con el titulo errores en el centro del panel inferior
	    panelInferior.add(labelErrores,BorderLayout.CENTER);
	    // Introducimos el area de errores en el sur del panel inferior
	    panelInferior.add(scrollErrores,BorderLayout.SOUTH);
	    
	    // Introducimos el panel inferior en el sur del panel principal
	    panel.add(panelInferior,BorderLayout.SOUTH);
	   
		return panel;
	}

	public JMenuBar getMenuPrincipal(){
		JMenuBar barraMenu = new JMenuBar();
		barraMenu.add(getMenuArchivo());	
		return barraMenu;
	}

	public JMenu getMenuArchivo() {
		JMenu menuArchivo = new JMenu("ACCIONES");
		menuArchivo.add(getCargarCodigo());
		menuArchivo.add(getTraductor());
		return menuArchivo;
	}
	
	public JMenuItem getCargarCodigo() {
		cargarCodigo = new JMenuItem("Cargar código");
		cargarCodigo.addActionListener(this);
		return cargarCodigo;
	}
	
	public JMenuItem getTraductor() {
		traductor = new JMenuItem("Traductor");
		traductor.addActionListener(this);
		return traductor;
	}
	


	// Metodo encargado de implementar el funcionamiento de elemento del menu(Cargar,traducir)
	public void actionPerformed(ActionEvent e) {
		// Si se ha pinchado sobre Traducir
		if(traductor == e.getSource()){
			String listadoTokens = "";
			
			// Inicializar los textArea
			textAreaErrores.setText("");
			textAreaTokens.setText("");
			textAreaCodigoPila.setText("");

			try{
				fich = new FileInputStream(f.getPath());
				AnalizadorLexico lexico = new AnalizadorLexico(fich);
				Token token = lexico.nextToken();
				while(token.getType() != Token.EOF_TYPE){
					listadoTokens += token + "\n";
					token = lexico.nextToken();
				}

				fich2 = new FileInputStream(f.getPath());
			/*	fich = new FileInputStream(f.getPath());
				AnalizadorLexico lexico2 = new AnalizadorLexico(fich);
				Token token2 = lexico.nextToken();
				while(token2.getType() != Token.EOF_TYPE){
					listadoTokens += token + "\n";
					token = lexico.nextToken();
				}*/
				AnalizadorLexico lexico2 = new AnalizadorLexico(fich2);
				AnalizadorSintactico sintactico = new AnalizadorSintactico(lexico2);
				sintactico.prog();
				// Mostramos el cÃ³digo pila
				mostrarCodigoPila(sintactico.getCod());
				// Mostramos la tabla de simbolos
				mostrarTablaSimbolos(sintactico.getTS());
				String errAux = sintactico.getListaErrores();
				textAreaErrores.setText(errAux);
			}
			catch (ANTLRException ae){
				System.err.println(ae.getMessage());
				listadoErrores += ae.getMessage() + "\n";
				textAreaErrores.setText(listadoErrores);
			} 
			catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			// Mostrar en el textArea de tokens
			textAreaTokens.setText(listadoTokens);
			
			
		}
		// Si se ha pinchado sobre Cargar
		else if(cargarCodigo == e.getSource()){
			
			JFileChooser file = new JFileChooser("./ejemplos/");
			int result = file.showOpenDialog(null);
			if(result == JFileChooser.APPROVE_OPTION){
				f = file.getSelectedFile();  
				cargarAreaCodigo(f);
			}
		}
		else{
		escribirErrores("Existen errores en el análisis léxico o en el análisis sintáctico.");
		}
	}
	
	// Metodo encargado de leer cargar el contenido de un fichero en el area de texto del codigo del programa
	public void cargarAreaCodigo(File f){
		Scanner archivo = null;
		String fichero = "";
		try{
			archivo = new Scanner(f);
			// Cargamos todo lo que tenga el fichero
			while (archivo.hasNext()){
				fichero = fichero  + archivo.nextLine()+ "\n";
			}
			archivo.close();
			// Cargamos lo que hemos leido en el area de texto del codigo del programa
			textAreaCodigoFuente.setText(fichero);
		}
		catch (FileNotFoundException e) {
			System.out.println("Error al cargar el archivo");
		}
	}
	// Metodo encargado de mostrar al usuario aquellos mensajes que se generen en tiempo de compilacion
		public void escribirErrores(String info){
			int numCaracteres = textAreaErrores.getText().length();
			textAreaErrores.insert(info+"\n", numCaracteres);
		}
		
		// Metodo encargado de mostrar la tabla de simbolos
		public void mostrarTablaSimbolos(TablaSimbolos tablaSimbolos){
			//Limpiamos la tabla menos su cabecera
			int numFilas = tSimbolos.getRowCount()-1;
			while(numFilas>=0){
				tSimbolos.removeRow(numFilas);
				numFilas--;
			}
			//AÃ±adimos los valores a la tabla de la interfaz
			//HashMap de claves
			if(tablaSimbolos!=null){
				HashMap<String, ElementoTS> hasTabla = tablaSimbolos.getTabla();
				// Coleccion de valores del HasMap
				Set<String> conjuntoClaves = hasTabla.keySet();
				// Iterador para el recorrido del conjunto de claves
				Iterator<String> itString = conjuntoClaves.iterator();
				while (itString.hasNext()){
					String clave = itString.next();
					ElementoTS valor = hasTabla.get(clave);
					Vector<String> unVector = new Vector<String>();
					//ID
					unVector.add(clave); 
					//TIPO
					unVector.add(valor.getTipo()); 
					//DIRECCION
					unVector.add("" + valor.getDir());
					//INICIALIZADA
					unVector.add("" + valor.isInicializada());  
					tSimbolos.addRow(unVector);
				}
			}
		}
		
		// Metodo encargado de mostrar el codigo a pila
		public void mostrarCodigoPila(String codigo){
			String cod = "";
			String tok;
			StringTokenizer tokenCodPila = new StringTokenizer(codigo);
			while(tokenCodPila.hasMoreElements()){
				tok = tokenCodPila.nextToken();
				cod = cod + tok + "\n";
			}
			textAreaCodigoPila.setText(cod);
		}

}
