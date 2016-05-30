/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class Comparador {
        
        private Navegador navegador;
        
        private int cont = 0;
        private int pxIgual = 0;
        private int pxDiff = 0;
        
        
        public Comparador(Navegador navegador){
        
            this.navegador = navegador;
        }
        
        
        public boolean comparar(String navegador1, String navegador2, String url, String ruta, String nombreVerdadero) {
          
            this.cont = 0;
            this.pxDiff = 0; 
            this.pxIgual = 0;
            
            String fecha = Utils.getFecha();
            
            String nombrePrueba = Utils.removerCaracteresInvalidos(nombreVerdadero);
            
            String titulo;
           String tituloDir;
            
                if (nombrePrueba.isEmpty()){
                    titulo = "Sin Titulo";  
                    tituloDir = titulo.trim(); //trim = le saca los espacios al texto.
                }else if(nombrePrueba.length() > 50) {
                    titulo = nombrePrueba.substring(0, 47) + "...";
                    tituloDir = nombrePrueba.substring(0, 47).trim();
                }else{
                    titulo = nombrePrueba;
                    tituloDir = titulo.trim();
                }
            
            
            String path = ruta + fecha + " - " + tituloDir + "//";
              
            Utils.crearCarpeta(path);
            
            String pathOne = path +"screenshot-br1.png";
            String pathTwo = path + "screenshot-br2.png";
            String pathThree = path + "z-result.png";
            String pathReport = path + "reporte.html";
        
            try {
		navigateAndTakeScreenshot(navegador1, pathOne, url);
		navigateAndTakeScreenshot(navegador2, pathTwo, url);

		BufferedImage imgOne = ImageIO.read(new File(pathOne));
		BufferedImage imgTwo = ImageIO.read(new File(pathTwo));

		this.bufferedImagesEqual(imgOne, imgTwo, pathThree);
                this.pxIgual = this.cont - this.pxDiff;
                Utils.crearReporte(pathReport, navegador1, navegador2, titulo, fecha, this.pxIgual, this.pxDiff);
                
                int mostrarReporte = JOptionPane.showConfirmDialog(null,
                       "La prueba ha finalizado exitosamente. Desea ver los resultados?", "EXITO",
                     JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                       if (mostrarReporte == JOptionPane.YES_OPTION){
                           File reporteMostrar = new File(pathReport);
                           Desktop.getDesktop().open(reporteMostrar);
                       }
                
                return true;
                
            } catch (HeadlessException e) {
                Utils.mostrarPopupError("No se ha detectado un monitor");
                return false;
            } catch (InterruptedException e) {
		Utils.mostrarPopupError("Error de interrupción del S.O.");
                return false;
            } catch (IOException e) {
		Utils.mostrarPopupError("Problemas accediendo al archivo C:/tmp");
                return false;
            } catch (AWTException e) {
                Utils.mostrarPopupError("Error con la placa grafica del sistema");
                return false;
            } catch (Exception ex) {
                Utils.mostrarPopupError("Error inesperado");
                return false;
            }
            
        }


	private synchronized void navigateAndTakeScreenshot(String browserName, String path, String url)
			throws InterruptedException, IOException, HeadlessException, AWTException {
		WebDriver browser = this.navegador.getBrowser(browserName);
		browser.manage().window().maximize(); //manege: manipula el browser
		browser.get(url); // que abra esa ruta

		Thread.sleep(10000);
		browser.findElement(By.tagName("body")).click();
		Actions action = new Actions(browser);
		action.sendKeys(Keys.F11).perform();

		Thread.sleep(8000);
		
		Robot robot = new Robot(); //maneja mi compu
                robot.mouseMove(450, 450);
                robot.mouseWheel(-500);
                Thread.sleep(1500);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle rectangle = new Rectangle(dimension);
		BufferedImage image = robot.createScreenCapture(rectangle); //guardo la captura en la variable dinamica
		
		ImageIO.write(image, "png", new File(path)); //llamo al metodo write de la clase estatica IO
		
		action.sendKeys(Keys.F11).perform();
		browser.close();
	}

	

	private boolean bufferedImagesEqual(BufferedImage img1, BufferedImage img2, String path) throws IOException {
		boolean isEqual;

		if (img1.getWidth() == img2.getWidth() && img1.getHeight() == img2.getHeight()) {
			BufferedImage bi = new BufferedImage(img1.getWidth(), img1.getHeight(), BufferedImage.TYPE_INT_RGB);
                        
			for (int x = 0; x < img1.getWidth(); x++) {
				for (int y = 0; y < img1.getHeight(); y++) {
					if (img1.getRGB(x, y) != img2.getRGB(x, y)) {
						isEqual = false;
						bi.setRGB(x, y, Color.ORANGE.getRGB());
                                                this.pxDiff++;
					}
                                        this.cont++;
				}
			}

			ImageIO.write(bi, "png", new File(path));

		} else {
			isEqual = false;
		}

		isEqual = true;

		return isEqual;
	}
	
	private boolean isPixelEqualtoItsContext(int x, int y, BufferedImage img1, BufferedImage img2) {
		if (img1.getRGB(x, y) != img2.getRGB(x, y))
			return false;
		if (img1.getRGB(x, y) != img2.getRGB(x - 1, y))
			return false;
		if (img1.getRGB(x, y) != img2.getRGB(x - 2, y))
			return false;
		if (img1.getRGB(x, y) != img2.getRGB(x + 1, y))
			return false;
		if (img1.getRGB(x, y) != img2.getRGB(x + 2, y))
			return false;
		if (img1.getRGB(x, y) != img2.getRGB(x, y - 1))
			return false;
		if (img1.getRGB(x, y) != img2.getRGB(x - 1, y - 1))
			return false;
		if (img1.getRGB(x, y) != img2.getRGB(x - 2, y - 1))
			return false;
		if (img1.getRGB(x, y) != img2.getRGB(x + 1, y - 1))
			return false;
		if (img1.getRGB(x, y) != img2.getRGB(x + 2, y - 1))
			return false;
		if (img1.getRGB(x, y) != img2.getRGB(x, y - 2))
			return false;
		if (img1.getRGB(x, y) != img2.getRGB(x - 1, y - 2))
			return false;
		if (img1.getRGB(x, y) != img2.getRGB(x - 2, y - 2))
			return false;
		if (img1.getRGB(x, y) != img2.getRGB(x + 1, y - 2))
			return false;
		if (img1.getRGB(x, y) != img2.getRGB(x + 2, y - 2))
			return false;
		if (img1.getRGB(x, y) != img2.getRGB(x, y + 1))
			return false;
		if (img1.getRGB(x, y) != img2.getRGB(x - 1, y + 1))
			return false;
		if (img1.getRGB(x, y) != img2.getRGB(x - 2, y + 1))
			return false;
		if (img1.getRGB(x, y) != img2.getRGB(x + 1, y + 1))
			return false;
		if (img1.getRGB(x, y) != img2.getRGB(x + 2, y + 1))
			return false;
		if (img1.getRGB(x, y) != img2.getRGB(x, y + 2))
			return false;
		if (img1.getRGB(x, y) != img2.getRGB(x - 1, y + 2))
			return false;
		if (img1.getRGB(x, y) != img2.getRGB(x - 2, y + 2))
			return false;
		if (img1.getRGB(x, y) != img2.getRGB(x + 1, y + 2))
			return false;
		if (img1.getRGB(x, y) != img2.getRGB(x + 2, y + 2))
			return false;
		
		return true;
		
	}

}
