
package pruebas;

import analizadorlexico.AnalizadorLexico;
import datos.Archivo;

public class PruebaAnalizador {
  public static void main(String[] args) {
    Archivo ar = new Archivo();
    AnalizadorLexico a = new AnalizadorLexico();
    a.automata(ar.leerArchivo());
//    System.out.println(a.getTablaPalabrasReservadas().toString());
//    System.out.println(a.getTablaSimbolos().toString());
    System.out.println(a.getTablaTokens().toString());
//    System.out.println(a.getTablaErrores().toString());
//    String palabrasReservadas, simbolos, tokens, errores;
//    palabrasReservadas = a.getTablaPalabrasReservadas().toString();
//    simbolos = a.getTablaSimbolos().toString();
//    tokens = a.getTablaTokens().toString();
//    errores = a.getTablaErrores().toString();
//    String[] parts = simbolos.split("],[");
//    String[] s= parts[0].split(",");
//    String[] s2= parts[1].split(",");
//    for (String sim : s) {
//      System.out.printf("%-20s\n",sim);
//    }
//    String palabra = "program";
//    System.out.println(Character.isSpace(palabra.charAt(8)));
  }
}
