package analizadorlexico;

import java.util.ArrayList;


public class AnalizadorLexico {
  private final String[][] listaPalabrasReservadas;
  private final ArrayList<ArrayList<String>> simbolos;
  private final ArrayList<String> palabrasReservadas;
  private final ArrayList<ArrayList<String>> tokens;
  private final ArrayList<String> errores;
  
  public AnalizadorLexico(){
    listaPalabrasReservadas = new String[4][2];
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
    
//    for (int i = 0; i < 2; i++) 
//      palabrasReservadas.add(new ArrayList());
    
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
      System.out.println(programa.length());
    while(fin <= (programa.length()-1))
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
          while(!esEspacio(programa.charAt(fin + 1)) && (fin + 1)<=(programa.length())){                            //Mientras el siguiente caracter no sea un espacio
            if(esMinuscula(programa.charAt(fin + 1))){                            //si es minúscula, puede ser una palabra reservada.
              palabra += programa.charAt(fin + 1);
              fin++;
                System.out.println(palabra);
            }else{                                                                //si no, es un error.
              error = true;
              palabra += programa.charAt(fin + 1);
              fin++;
            }
          }
          if(!error){                                                             //Terminamos de analizar la palabra, si no es error...
            if(esPalabraReservada(palabra)){                                      //Si esta en lista de palabras reservadas
              palabrasReservadas.add(palabra);                                    //Agregala a la tabla de palabras reservadas.
            } else {                                                              //Si no está, es un error:
              errores.add(palabra);                                               //Añadir a tabla de errores
            }
          } else {
              errores.add(palabra);
          }
          //Se termina el análisis:
          palabra = "";
          fin+=2;
          inicio = fin;
          estado = 0;
          error = false;
          break;
        case 3:
          if((fin + 1)<=(programa.length()))
          while(!esEspacio(programa.charAt(fin + 1)) && (fin + 1)<=(programa.length())){                            //Mientras no siga un espacio
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
          if(esGuionBajo(palabra.charAt(palabra.length()-1)))                       //Verifica que el último caracter de la palabra no sea, 
            error = true;                                                         //guión bajo.
          if(!error){                                                             //Terminamos de analizar la palabra, si no es error...
            simbolos.get(0).add(palabra);                                         //Agrega la palabra a la columna lexema en la tabla se simbolos.
            simbolos.get(1).add("Identificador");                                 //Agrega su clasificación.
            tokens.get(0).add(palabra);                                           //Agrega la palabra a la columna lexema a la tabla de tokens.
            tokens.get(0).add("Identificador");                                   //Agrega su clasificación.
            tokens.get(0).add("400");                                             //Agrega el Atributo de Identificador.
          } else {
            errores.add(palabra);
          }
          //Se termina el análisis:
          palabra = "";
          inicio = fin;
          estado = 0;
          error = false;
          break;

      }
    
  }
  
  public void automata(String programa){
    String palabra;
    int estado, inicio, fin;
    
    palabra = "";
    estado = inicio = fin = 0;
    
    switch(estado){
      case 0:
        if(esSimbolo(programa.charAt(inicio))){ 
          estado = 2;                                   
          break;
        } else if(esNumero(programa.charAt(inicio))){
          estado = 5;
          break;
        } else if(esMayuscula(programa.charAt(inicio))){
          
        }
        
      case 2:                                               // SÍMBOLO
        if(esSimbolo(programa.charAt(inicio))){
          /* Añadir a las listas ***
            Símbolo. Token = ASCII
          */
          inicio++;
          fin++;
          estado = 2;
          break;
        } else {
          estado = 0;
          break;
        }
      case 5:
        if(esNumero(programa.charAt(inicio))){              // NÚMERO ENTERO
          palabra += programa.charAt(inicio);             //Guardamos el número
          inicio++;
          fin++;
          estado = 5;
          break;
        } else if (esPunto(programa.charAt(inicio))){       // sigue un punto.
          palabra += programa.charAt(inicio);             //Guardamos el punto
          inicio++;
          fin++;
          estado = 6;                                     //Es flotante
          break;
        } else{                                       //Termina el número entero
          /* Añadir a las listas ***
            Número entero. Token = 300
          */
          palabra = "";
          estado = 0;
          break;
        }
      case 6:                                             // NÚMERO FLOTANTE
        if(esNumero(programa.charAt(inicio))){
          palabra += programa.charAt(inicio); 
          inicio++;
          fin++;
          estado = 6;
          break;
        } else {                                      //Termina el número float
          /* Añadir a las listas **
            Número flotante. Token = 500
          */
          palabra = "";
          estado = 0;
          break;
        }
        
    }
  }
  
  private boolean esPunto(char c){
    return c == '.';
  }
  
  private boolean esSimbolo(char c){
    return c == ';' || c == '=' || c == '+' || c == '-' || c == '*' || 
           c == '(' || c == ')'; 
  }
  
  private boolean esMinuscula(char c){
    return Character.isLowerCase(c);
  }
  
  private boolean esEspacio(char c){
    return Character.isSpaceChar(c);
  }
  
  private boolean esGuionBajo(char c){
    return new Character(c).equals('_');
  }
  
  private boolean esNumero(char c){
    return Character.isDigit(c);
  }
  
  private boolean esPalabraReservada(String palabra){
    for (int i = 0; i < listaPalabrasReservadas[0].length; i++)
      if(palabra.equals(listaPalabrasReservadas[i][0]))
        return true;
    return false;
  }
  
  public ArrayList<ArrayList<String>> getTablaSimbolos(){
    return simbolos;
  }
  
  public ArrayList<ArrayList<String>> getTablaTokens(){
    return tokens;
  }
  
  public ArrayList<String> getTablaPalabrasReservadas(){
    return palabrasReservadas;
  }
  
}
