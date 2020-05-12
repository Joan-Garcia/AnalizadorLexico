package analizadorlexico;

import estructurasDeDatos.ListaEnlazada;
import estructurasDeDatos.Nodo;

public class AnalizadorLexico {
  private final String[][] listaPalabrasReservadas;
  private final ListaEnlazada simbolos, palabrasReservadas, tokens, errores;
  
  public AnalizadorLexico(){
    listaPalabrasReservadas = new String[4][2];
    simbolos = new ListaEnlazada();
    palabrasReservadas = new ListaEnlazada();
    tokens = new ListaEnlazada();
    errores = new ListaEnlazada();
    
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
      simbolos.add(new Nodo(new ListaEnlazada()));
    
//    for (int i = 0; i < 2; i++) 
//      palabrasReservadas.add(new ArrayList());
    
    for (int i = 0; i < 3; i++) 
      tokens.add(new Nodo(new ListaEnlazada()));
  }
  
  public void automata(String programa){
    String palabra;
    int estado, inicio;
    boolean error  = false;
    palabra = "";
    estado = inicio = 0;
    while(inicio != programa.length())
    switch(estado){
      case 0:
        if (esSimbolo(programa.charAt(inicio))){ 
          estado = 2;                                   
          break;
        } else if(error){
          palabra += programa.charAt(inicio);
          if(esEspacio(programa.charAt(inicio + 1))){
            System.out.println("Error en: " + palabra);
            errores.add(new Nodo(palabra));
            palabra = "";
            error = false;
          }
          inicio++;
          estado = 0;
          break;
        } else if(esNumero(programa.charAt(inicio))){
          estado = 5;
          break;
        } else if(esMayuscula(programa.charAt(inicio))){
          palabra += programa.charAt(inicio);
          inicio++;
          estado = 3;
          break;
        } else if(esMinuscula(programa.charAt(inicio))){
          estado = 1;
          break;
        } else if(esEspacio(programa.charAt(inicio))){
          inicio++;
          estado = 0;
          break;
        } else {                                      //Es caracter no permitido
          palabra += programa.charAt(inicio);
          if(esPunto(programa.charAt(inicio))){
            while(esNumero(programa.charAt(inicio + 1))){
              palabra += programa.charAt(inicio + 1);
              inicio++;
            }
          }
          if(esEspacio(programa.charAt(inicio + 1))|| 
             esSimbolo(programa.charAt(inicio + 1))  ||
             esNumero(programa.charAt(inicio + 1))   ||
             esMinuscula(programa.charAt(inicio + 1))||
             esMayuscula(programa.charAt(inicio + 1))  
             ){
            System.out.println("Error en: " + palabra);
            errores.add(new Nodo(palabra));
            palabra = "";
          }
          inicio++;
          estado = 0;
          break;
        }
      case 1:                                               //PALABRASRESERVADAS
        if(esMinuscula(programa.charAt(inicio))){
          palabra += programa.charAt(inicio);
          inicio++;
          estado = 1;
          break;
        } else {
          if(esPalabraReservada(palabra)){
            /* Añadirla a las listas **
              Símbolo. Token = tokenPalabraRervada(palabra)
            */
            palabrasReservadas.add(new Nodo(palabra));
            
            añadeFilaATokens(palabra, "Palabra reservada",
                             String.valueOf(tokenPalabraReservada(palabra)));
            
            palabra = "";
            inicio++;
            estado = 0;
          } else {
            System.out.println("Error en: " + palabra);
            errores.add(new Nodo(palabra));
            palabra = "";
            inicio++;
            estado = 0;
          }
        }
      case 2:                                               // SÍMBOLO
        if(esSimbolo(programa.charAt(inicio))){
          /* Añadir a las listas ***
            Símbolo. Token = ASCII
          */
          
          añadeFilaATokens(String.valueOf(programa.charAt(inicio)), 
                           "Caracter simple", 
                           String.valueOf(valorASCII(programa.charAt(inicio))));
          inicio++;
          estado = 2;
          break;
        } else {
          estado = 0;
          break;
        }
      case 3:                                               //IDENTIFICADOR         
        if(esMinuscula(programa.charAt(inicio))){
          palabra += programa.charAt(inicio);
          inicio++;
          estado = 3;
          break;
        } else if(esGuionBajo(programa.charAt(inicio))){
          if(esEspacio(programa.charAt(inicio + 1))){
            estado = 0;
            error = true;
            break;
          }
          palabra += programa.charAt(inicio);
          inicio++;
          estado = 3;
          break;
        } else if(esNumero(programa.charAt(inicio))){
          palabra += programa.charAt(inicio);
          inicio++;
          estado = 3;
          break;
        } else{
          if(esMayuscula(programa.charAt(inicio)) && !esSimbolo(programa.charAt(inicio)) && 
                  !esEspacio(programa.charAt(inicio))){
            estado = 0;
            error = true;
            break;
          }
          /* Añadir a las listas ***
            Identificador. Token = 400
          */
          
          añadeFilaASimbolos(palabra, "Identificador");
          añadeFilaATokens(palabra, "Identificador", "400");
          
          palabra = "";
          estado = 0;
          break;
        }
      case 5:
        if(esNumero(programa.charAt(inicio))){              // NÚMERO ENTERO
          palabra += programa.charAt(inicio);             //Guardamos el número
          inicio++;
          estado = 5;
          break;
        } else if (esPunto(programa.charAt(inicio))){  // sigue un punto.
          palabra += programa.charAt(inicio);          //Guardamos el punto
          if (!esNumero(programa.charAt(inicio + 1))){
            System.out.println("Error en: " + palabra);
            errores.add(new Nodo(palabra));
            palabra = "";
            inicio++;
            estado = 0;
            break;
          } else {           
            inicio++;
            estado = 6;                                     //Es flotante
            break;
          }
        } else {                                       //Termina el número entero
          if(esMayuscula(programa.charAt(inicio)) || 
                  !esSimbolo(programa.charAt(inicio)) ||
                  esMinuscula(programa.charAt(inicio))){
            estado = 0;
            error = true;
            break;
          }
          /* Añadir a las listas ***
            Número entero. Token = 300
          */
          
          añadeFilaATokens(palabra, "Número entero", "300");
          palabra = "";
          estado = 0;
          break;
        }
      case 6:                                             // NÚMERO FLOTANTE
        if(esNumero(programa.charAt(inicio))){
          palabra += programa.charAt(inicio); 
          inicio++;
          estado = 6;
          break;
        } else {                                      //Termina el número float
          /* Añadir a las listas **
            Número flotante. Token = 500
          */
          
          añadeFilaATokens(palabra, "Número de punto flotante", "500");
          palabra = "";
          estado = 0;
          break;
        }
    }
  }
  
  private void añadeFilaASimbolos(String lexema, String clasificacion){
    ListaEnlazada temp = new ListaEnlazada();
            
    temp = (ListaEnlazada) simbolos.get(0).getInfo();
    temp.add(new Nodo(lexema));
            
    temp = (ListaEnlazada) simbolos.get(1).getInfo();
    temp.add(new Nodo(clasificacion));
  }
  
  private void añadeFilaATokens(String lexema, String clasificacion,
                                String atributo){
    ListaEnlazada temp = new ListaEnlazada();
            
    temp = (ListaEnlazada) tokens.get(0).getInfo();
    temp.add(new Nodo(lexema));
            
    temp = (ListaEnlazada) tokens.get(1).getInfo();
    temp.add(new Nodo(clasificacion));
            
    temp = (ListaEnlazada) tokens.get(2).getInfo();
    temp.add(new Nodo(atributo));
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
  
  private boolean esMayuscula(char c){
    return Character.isUpperCase(c);
  }
  
  private boolean esEspacio(char c){
    return Character.isWhitespace(c);
  }
  
  private boolean esGuionBajo(char c){
    return new Character(c).equals('_');
  }
  
  private boolean esNumero(char c){
    return Character.isDigit(c);
  }
  
  private int valorASCII(char c){
    return (int) c;
  }
  
  // Devuelve el token correspondiente a la palabra reservada.
  private int tokenPalabraReservada(String palabra){
    for (int i = 0; i <= 3; i++)
      if(palabra.equals(listaPalabrasReservadas[i][0]))
        return Integer.parseInt(listaPalabrasReservadas[i][1]);
    return -1;
  }
  
  // Comprueba si una palabra forma parte de las palabras reservadas.
  public boolean esPalabraReservada(String palabra){
    for (int i = 0; i <= 3; i++)
      if(palabra.equals(listaPalabrasReservadas[i][0]))
        return true;
    return false;
  }
  
  public ListaEnlazada getTablaSimbolos(){
    return simbolos;
  }
  
  public ListaEnlazada getTablaTokens(){
    return tokens;
  }
  
  public ListaEnlazada getTablaPalabrasReservadas(){
    return palabrasReservadas;
  }
  
  public ListaEnlazada getTablaErrores(){
    return errores;
  }
}
