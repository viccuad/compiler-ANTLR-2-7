/////////////////////////
/// Analizador léxico ///
/////////////////////////
header {
package traductor;
}

class AnalizadorLexico extends Lexer; 


options {
	k=3;   // real+separador, 5 caracteres
	caseSensitive=false;
}

tokens{
	REAL = "real";
	INT = "int" ;
}

protected NUEVA_LINEA: "\r\n" {newline();};	  
protected ESPACIO_TAB: ' '|'\t';

BLANCO: (ESPACIO_TAB | NUEVA_LINEA) {$setType(Token.SKIP);}; 

ABRE_PARENTESIS : '('; 
CIERRA_PARENTESIS : ')'; 
protected PUNTO: '.' ;
FIN: '$' {$setType(Token.EOF_TYPE);};
PUNT_COMA: ';';
protected SEPARADOR : BLANCO;

MENOR:'<';
MAYOR:'>';
MENORIGUAL: "<=";
MAYORIGUAL: ">=";
IGUAL: "==";
ASIGNA: '=';
DISTINTO: "!=";
MAS: '+';
MENOS: '-';
OR: "||";
MULT:'*';
DIV:'/';
AND: "&&";
MOD: '%';
NEG: '!';
AMBER: '&';


CAST_INT: ABRE_PARENTESIS 'i''n''t' CIERRA_PARENTESIS;
CAST_REAL: ABRE_PARENTESIS 'r''e''a''l' CIERRA_PARENTESIS;

IN: 'i''n' SEPARADOR;
OUT: 'o''u''t' SEPARADOR;


protected LETRA : ('a'..'z'); 
protected DIGITO : '0'..'9';
IDEN : (LETRA | '_') (LETRA|DIGITO|'_')*;

//protected INT : ("int");protected REAL : ("real");
//TIPO : INT | REAL;


protected NATURAL : '0' | ('1'..'9')(DIGITO)*;
protected DECIMAL : PUNTO(DIGITO)+;
protected NUM_ENTERO : NATURAL | ('1'..'9')(DIGITO)*;
NUM_REAL : NUM_ENTERO (DECIMAL)? ;




COMENTARIO :  '@'(~'\n')* '\n'{$setType(Token.SKIP);newline();};  // para casar cualquier carácter menos el salto de linea: (~'\n')*


