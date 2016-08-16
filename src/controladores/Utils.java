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
    
    public static void crearCarpetaTMP(){
        File carpeta = new File("C://pixeldiff");
        
        if(!carpeta.exists()){
            carpeta.mkdir();
        }
        
        File carpetaTMP = new File("C://pixeldiff//tmp");
        
        if(!carpetaTMP.exists()){
            carpetaTMP.mkdir();
        }
        
        
    }
    
    public static void crearCarpeta(String carpetaN) {
         File carpeta = new File(carpetaN);
         carpeta.mkdir();   
                
    }
    
    public static void crearReporte(String path, String browser1, String browser2, String nombre, String fecha, int pxIgual, int pixDiff) throws IOException {
        
        String fechaYhora = "Fecha: " + fecha.replaceAll(" ", " - Hora: ");
        
        File templateReportes =  new File("C://pixeldiff//utils//templateReportes.html"); 
        String htmlString = FileUtils.readFileToString(templateReportes);
        String htmlReemplazado = htmlString.replaceAll("pxIgual", String.valueOf(pxIgual))
                                           .replaceAll("pxIgual", String.valueOf(pxIgual))
                                           .replaceAll("pxDiff", String.valueOf(pixDiff))
                                           .replaceAll("browser1", browser1)
                                           .replaceAll("browser2", browser2)
                                           .replaceAll("nombre", nombre)
                                           .replaceAll("fecha", fechaYhora);
        File newHtmlFile = new File(path);
        FileUtils.writeStringToFile(newHtmlFile, htmlReemplazado);
        
        
    }
        
    public static void mostrarPopupError(String mensaje){
        JOptionPane.showMessageDialog(null, mensaje, "Error",
                JOptionPane.ERROR_MESSAGE);
    }
    
    public static String removerCaracteresInvalidos(String nombre){
        
        return nombre.replaceAll("ñ", "n")
                     .replaceAll("/", "")
                     .replaceAll(":", "")
                     .replaceAll("¿", "")
                     .replaceAll("|", "")
                     .replaceAll("Ñ", "N")
                     .replaceAll("@", "")
                     .replaceAll("$", "")
                     .replaceAll("%", "")
                     .replaceAll("°", "")
                     .replaceAll("<", "")
                     .replaceAll(">", "")
                     .replaceAll("\\\\", "")
                     .replaceAll("\\*", "")
                     .replaceAll("\\?", "")
                     .replaceAll("'", "")
                     .replaceAll("!", "")
                     .replaceAll("¡", "")
                     .replaceAll("\\{", "")
                     .replaceAll("}", "")
                     .replaceAll("\\+", "")
                     .replaceAll("^", "")
                     .replaceAll("~", "")
                     .replaceAll("]", "")
                     .replaceAll("\\[", "")
                     .replaceAll("´", "")
                     .replaceAll("\"", "");
                     
        
    }
    
    
    public static String obtenerBrowserInvalido(String mensaje){
    
        if(mensaje.toUpperCase().contains("CHROME"))
            return "El archivo Chromedriver.exe no se encuentra instalado.";
        
        if(mensaje.toUpperCase().contains("FIREFOX"))
            return "El navegador firefox no se encuentra instalado en el sistema.";
        
        if(mensaje.toUpperCase().contains("EXPLORER") && mensaje.toUpperCase().contains("UNABLE TO FIND ELEMENT"))
            return "La versión del navegador Internet Explorer debe ser igual o inferior a la 10 ";
        
        if(mensaje.toUpperCase().contains("IEDRIVER"))
            return "El archivo iedriver.exe no se encuentra instalado.";
        
        if(mensaje.toUpperCase().contains("OPERA"))
            return "El navegador Opera no se encuentra instalado en el sistema.";
        
        if(mensaje.toUpperCase().contains("EXPLORER"))
            return "El navegador Explorer no se encuentra instalado en el sistema.";
        
        return "";
        
    }
     
}
