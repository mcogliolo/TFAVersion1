/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 *
 * @author mariela
 */
public class Navegador {
    
    public WebDriver getBrowser(String browserName) {

		if (browserName.equalsIgnoreCase(Navegadores.FF)) //olvide las mayusculas
			return new FirefoxDriver();

		if (browserName.equalsIgnoreCase(Navegadores.CHROME)) {
			System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
			return new ChromeDriver();
		}

		if (browserName.equalsIgnoreCase(Navegadores.IE)) {
			System.setProperty("webdriver.ie.driver", "C:\\iedriver.exe");
			DesiredCapabilities dc = DesiredCapabilities.internetExplorer(); //creo un profile nuevo
			dc.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			return new InternetExplorerDriver(dc);
		}

		return new OperaDriver();
	}
    
}
