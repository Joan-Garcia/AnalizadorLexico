
package pruebas;

import analizadorlexico.AnalizadorLexico;
import datos.Archivo;

public class PruebaAnalizador {
  public static void main(String[] args) {
    AnalizadorLexico a = new AnalizadorLexico();
    a.procesa("program Test");
    System.out.println(a.getTablaPalabrasReservadas().toString());
    System.out.println(a.getTablaSimbolos().toString());
//    String palabra = "program";
//    System.out.println(Character.isSpace(palabra.charAt(8)));
  }
}
