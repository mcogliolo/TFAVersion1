/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

/**
 *
 * @author Mariela Cogliolo
 */
public class Manejador {
    
    private Navegador navegador;
    private Comparador comparador;
    
    public Manejador(){
        this.navegador = new Navegador();
        this.comparador = new Comparador(this.navegador);
  }
    
    public boolean compararImg(String navegador1, String navegador2, String url, String ruta, String titulo){
        return this.comparador.comparar(navegador1, navegador2, url, ruta, titulo);
    }
    
    public void finalizarProceso(){
        System.exit(1); 
    }
 
}
