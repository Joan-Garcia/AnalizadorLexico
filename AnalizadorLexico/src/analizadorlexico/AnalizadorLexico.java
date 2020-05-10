package analizadorlexico;

import java.util.ArrayList;


public class AnalizadorLexico {
  private final String[][] listaPalabrasReservadas;
  private final ArrayList<ArrayList<String>> simbolos;
  private final ArrayList<ArrayList<String>> palabrasReservadas;
  private final ArrayList<ArrayList<String>> tokens;
  private final ArrayList<String> errores;
  
  public AnalizadorLexico(){
    listaPalabrasReservadas = new String[3][1];
    simbolos = new ArrayList();
    palabrasReservadas = new ArrayList();
    tokens = new ArrayList();
    errores = new ArrayList();
    
    // Llenar la lista de palabras reservadas:
    listaPalabrasReservadas[0][0] = "Palabra reservada";
    listaPalabrasReservadas[1][0] = "program";
    listaPalabrasReservadas[2][0] = "begin";
    listaPalabrasReservadas[3][0] = "end";
    
    listaPalabrasReservadas[0][1] = "token";
    listaPalabrasReservadas[1][1] = "600";
    listaPalabrasReservadas[2][1] = "601";
    listaPalabrasReservadas[3][1] = "602";
    
    for (int i = 0; i < 2; i++)
      simbolos.add(new ArrayList());
    
    for (int i = 0; i < 2; i++) 
      palabrasReservadas.add(new ArrayList());
    
    for (int i = 0; i < 3; i++) 
      tokens.add(new ArrayList());
  }
  
  @SuppressWarnings("UnusedAssignment")
  public void procesa(String programa){
    boolean error;
    String palabra;
    int estado, inicio, fin;
    
    error = false;
    palabra = "";
    estado = inicio = fin = 0;
    
    switch(estado){
      case 0:
        if(esMinuscula(programa.charAt(inicio))){                               //Si la palabra empieza con minúscula, es una palabra reservada o error.
          palabra += programa.charAt(inicio);
          estado = 1;
          break;
        } else if (!esMinuscula(programa.charAt(inicio))){                      //Si la palabra empieza con mayúscula.
          palabra += programa.charAt(inicio);
          estado = 3;
          break;
        }
      case 1:                                                                   //Verifica que la palabra es una palabra reservada o un error.
        while(!esEspacio(programa.charAt(fin + 1))){                            //Mientras el siguiente caracter no sea un espacio
          if(esMinuscula(programa.charAt(fin + 1))){                            //si es minúscula, puede ser una palabra reservada.
            palabra += programa.charAt(fin + 1);
            fin++;
          }else{                                                                //si no, es un error.
            error = true;
            palabra += programa.charAt(fin + 1);
            fin++;
          }
        }
        if(!error){                                                             //Terminamos de analizar la palabra, si no es error...
          if(esPalabraReservada(palabra)){                                        //Si esta en lista de palabras reservadas
            //AÑADIR PALABRA RESERVADA
          } else {                                                                //Si no está, es un error:
            //AÑADIR A TABLA ERRORES                                              //Añadir a tabla de errores
          }
        } else {
            //AÑADIR A TABLA DE ERRORES
        }
        //Se termina el análisis:
        palabra = "";
        inicio = fin;
        estado = 0;
        error = false;
        break;
      case 3:
        while(!esEspacio(programa.charAt(fin + 1))){                            //Mientras no siga un espacio
          if(esMinuscula(programa.charAt(fin + 1)) ||                           //Verifica si es minúscula, guión bajo o número
             esGuionBajo(programa.charAt(fin + 1)) ||
             esNumero(programa.charAt(fin + 1))){
            palabra += programa.charAt(fin + 1);
            fin++;
          }else{                                                                //si no, es un error.
            error = true;
            palabra += programa.charAt(fin + 1);
            fin++;
          }
        }
        if(esGuionBajo(palabra.charAt(palabra.length())))                       //Verifica que el último caracter de la palabra no sea, 
          error = true;                                                         //guión bajo.
        if(!error){                                                             //Terminamos de analizar la palabra, si no es error...
          //Añadir a tablas
        } else {
          //AÑADIR A TABLA DE ERRORES
        }
        //Se termina el análisis:
        palabra = "";
        inicio = fin;
        estado = 0;
        error = false;
        break;

    }
  }
  
  public boolean esMinuscula(char c){
    return Character.isUpperCase(c);
  }
  
  public boolean esEspacio(char c){
    return Character.isSpaceChar(c);
  }
  
  public boolean esGuionBajo(char c){
    return new Character(c).equals('_');
  }
  
  public boolean esNumero(char c){
    return Character.isDigit(c);
  }
  
  public boolean esPalabraReservada(String palabra){
    for (int i = 0; i < listaPalabrasReservadas[0].length; i++)
      if(palabra.equals(listaPalabrasReservadas[i][0]))
        return true;
    return false;
  }
  
  
  
  
}
