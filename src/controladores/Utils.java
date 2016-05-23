/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Agustin
 */
public class Utils {
    
    
    public static String getFecha() {
    
            Date d = new Date();
            Calendar c = new GregorianCalendar(); 
            c.setTime(d);

            String dia, mes, annio, hora, minuto, segundo;

            dia = Integer.toString(c.get(Calendar.DATE));
            mes = Integer.toString(c.get(Calendar.MONTH));
            annio = Integer.toString(c.get(Calendar.YEAR));
            hora = Integer.toString(c.get(Calendar.HOUR_OF_DAY));
            minuto = Integer.toString(c.get(Calendar.MINUTE));
            segundo = Integer.toString(c.get(Calendar.SECOND));
                            
            return(dia + "-" + mes +"-" + annio + " " + hora + "." + minuto + "." + segundo);
    
    }
    
    
    public static void crearCarpeta(String carpetaN) {
         File carpeta = new File(carpetaN);
         carpeta.mkdir();   
                
    }
    
    public static void crearReporte(String path, String browser1, String browser2, String nombre, String fecha, int pxIgual, int pixDiff) throws IOException {
        File templateReportes =  new File("src/recursos/templateReportes.html"); 
        String htmlString = FileUtils.readFileToString(templateReportes);
        String htmlReemplazado = htmlString.replaceAll("pxIgual", String.valueOf(pxIgual))
                                           .replaceAll("pxIgual", String.valueOf(pxIgual))
                                           .replaceAll("pxDiff", String.valueOf(pixDiff))
                                           .replaceAll("browser1", browser1)
                                           .replaceAll("browser2", browser2)
                                           .replaceAll("nombre", nombre)
                                           .replaceAll("fecha", fecha);
        File newHtmlFile = new File(path);
        FileUtils.writeStringToFile(newHtmlFile, htmlReemplazado);
        
        
    }
    
    
    public static void mostrarPopupError(String mensaje){
        JOptionPane.showMessageDialog(null, mensaje, "Eror",
                JOptionPane.ERROR_MESSAGE);
    }
    
}
