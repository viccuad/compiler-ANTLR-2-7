// $ANTLR : "AnalizadorSintactico.g" -> "AnalizadorSintactico.java"$

package traductor;

public interface AnalizadorSintacticoTokenTypes {
	int EOF = 1;
	int NULL_TREE_LOOKAHEAD = 3;
	int REAL = 4;
	int INT = 5;
	int NUEVA_LINEA = 6;
	int ESPACIO_TAB = 7;
	int BLANCO = 8;
	int ABRE_PARENTESIS = 9;
	int CIERRA_PARENTESIS = 10;
	int PUNTO = 11;
	int FIN = 12;
	int PUNT_COMA = 13;
	int SEPARADOR = 14;
	int MENOR = 15;
	int MAYOR = 16;
	int MENORIGUAL = 17;
	int MAYORIGUAL = 18;
	int IGUAL = 19;
	int ASIGNA = 20;
	int DISTINTO = 21;
	int MAS = 22;
	int MENOS = 23;
	int OR = 24;
	int MULT = 25;
	int DIV = 26;
	int AND = 27;
	int MOD = 28;
	int NEG = 29;
	int AMBER = 30;
	int CAST_INT = 31;
	int CAST_REAL = 32;
	int IN = 33;
	int OUT = 34;
	int LETRA = 35;
	int DIGITO = 36;
	int IDEN = 37;
	int NATURAL = 38;
	int DECIMAL = 39;
	int NUM_ENTERO = 40;
	int NUM_REAL = 41;
	int COMENTARIO = 42;
}
