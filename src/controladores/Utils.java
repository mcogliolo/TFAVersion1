/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
    
}
