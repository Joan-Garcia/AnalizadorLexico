package analizadorlexico;

import datos.Archivo;
import estructurasDeDatos.ListaEnlazada;

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
    ListaEnlazada temp;         //Puente para llamar al metodo toArray() de las
                                //listas enlazadas dentro de otras listas.
    //Impresión de la tabla de palabras reservadas:
    String[] r = analizador.getTablaPalabrasReservadas().toArray();
    System.out.println("\nPalabras Reservadas");
    System.out.println("----------------------");
    for (String r1 : r)
      System.out.println(r1);
    System.out.println("\n");
    
    //Impresión de la tabla de símbolos:
    temp = (ListaEnlazada) analizador.getTablaSimbolos().get(0).getInfo();
    String[] s1 = temp.toArray();
    temp = (ListaEnlazada) analizador.getTablaSimbolos().get(1).getInfo();
    String[] s2 = temp.toArray();
    
    System.out.println("\nTabla de Simbolos");
    System.out.printf("%-20s%-20s\n", "Lexema", "Clasificación");
    System.out.println("----------------------------------");
    for(int i = 0; i < s1.length; i++)
      System.out.printf("%-20s%-20s\n", s1[i], s2[i]);
    System.out.println("");
    
    //Impresión de la tabla de tokens:
    temp = (ListaEnlazada) analizador.getTablaTokens().get(0).getInfo();
    String[] t1 = temp.toArray();
    temp = (ListaEnlazada) analizador.getTablaTokens().get(1).getInfo();
    String[] t2 = temp.toArray();
    temp = (ListaEnlazada) analizador.getTablaTokens().get(2).getInfo();
    String[] t3 = temp.toArray();
    System.out.println("\nTabla de Tokens");
    System.out.printf("%-20s%-30s%-20s\n", "Lexema", "Clasificación", "Atributo");
    System.out.println("------------------------------------------------------------");
    for (int i = 0; i < t1.length; i++)
      System.out.printf("%-20s%-30s%-20s\n", t1[i], t2[i], t3[i]);
    System.out.println("");
    
    //Impresión de la tabla de errores:
    String[] e = analizador.getTablaErrores().toArray();
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
