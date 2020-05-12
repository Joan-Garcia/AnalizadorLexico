
package datos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Archivo {
  File archivo;
  JFileChooser chooser;
  FileReader fr;
  BufferedReader br;
  String f;
  
  public Archivo() {
    chooser = new JFileChooser();
  }
    
  public String seleccionaArchivo() {
    chooser.setFileFilter(new FileNameExtensionFilter(".txt", "txt"));
    chooser.setDialogTitle("Selecciona un archivo.");
        
    if (chooser.showDialog(null, "Ok") == JFileChooser.CANCEL_OPTION)
      return null;
        
    archivo = chooser.getSelectedFile();
    return archivo.getAbsolutePath();
  }
    
  public String leerArchivo() {
    String cadena = "", linea;
    try {
      f = seleccionaArchivo();
      fr = new FileReader (f);
      br = new BufferedReader(fr);

      while ((linea=br.readLine())!=null) {
        cadena = cadena + linea + " ";
      }
      }catch (IOException e) {
        e.printStackTrace();
      }finally {
        try{
          if( null != fr )
            fr.close();               
        }catch (Exception e2){ 
          e2.printStackTrace();
        }
      }
    return cadena;
  }
  
  public String getRutaArchivo(){
    return f;
  }
}
