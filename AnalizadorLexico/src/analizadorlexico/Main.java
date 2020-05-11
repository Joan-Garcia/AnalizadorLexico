
package analizadorlexico;

import datos.Archivo;

public class Main {
  Archivo file;
  AnalizadorLexico analizador;
  
  public Main(){
    file = new Archivo();
    analizador = new AnalizadorLexico();
  }
  
  private void run(){
    analizador.automata(file.leerArchivo());
  }
  
  private void resultados(){
    String[] r = new String[analizador.getTablaPalabrasReservadas().size()];
    analizador.getTablaPalabrasReservadas().toArray(r);
    System.out.println("\nPalabras Reservadas");
    System.out.println("----------------------");
    for (String r1 : r)
      System.out.println(r1);
    System.out.println("\n");
    
    String[] s1 = new String[analizador.getTablaSimbolos().get(0).size()];
    String[] s2 = new String[analizador.getTablaSimbolos().get(0).size()];
    analizador.getTablaSimbolos().get(0).toArray(s1);
    analizador.getTablaSimbolos().get(1).toArray(s2);
    System.out.println("\nTabla de Simbolos");
    System.out.printf("%-20s%-20s\n", "Lexema", "Clasificación");
    System.out.println("----------------------------------");
    for(int i = 0; i < s1.length; i++)
      System.out.printf("%-20s%-20s\n", s1[i], s2[i]);
    System.out.println("");
    
    String[] t1 = new String[analizador.getTablaTokens().get(0).size()];
    String[] t2 = new String[analizador.getTablaTokens().get(0).size()];
    String[] t3 = new String[analizador.getTablaTokens().get(0).size()];
    analizador.getTablaTokens().get(0).toArray(t1);
    analizador.getTablaTokens().get(1).toArray(t2);
    analizador.getTablaTokens().get(2).toArray(t3);
    System.out.println("\nTabla de Tokens");
    System.out.printf("%-20s%-30s%-20s\n", "Lexema", "Clasificación", "Atributo");
    System.out.println("------------------------------------------------------------");
    for (int i = 0; i < t1.length; i++)
      System.out.printf("%-20s%-30s%-20s\n", t1[i], t2[i], t3[i]);
    System.out.println("");
    
    String[] e = new String[analizador.getTablaErrores().size()];
    analizador.getTablaErrores().toArray(e);
    System.out.println("\nTabla de Errores");
    System.out.println("----------------------");
    for (String e1 : e)
      System.out.println(e1);
    System.out.println("");
  }
  public static void main(String[] args) {
    Main m = new Main();
    m.run();
    m.resultados();
  }
  
}
