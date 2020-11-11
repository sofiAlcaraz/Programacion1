package juego;

import java.awt.Color;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import entorno.Entorno;

public class Conejo {

	// por el momento está bien, pero hay que corregirlo
	private double altura;
	private double ancho;
	private Color color;
	
	private double x;
	private double y;
	private double velocidadDeSalto;
	private double bajadaDePantalla; // si se puede mejorar el nombre
	//private double direccion; // por ahí no lo necesitan

	public Conejo(double altura, double ancho, double x, double y, double velocidad,double movAbajo) {
		
		this.altura = altura;
		this.ancho = ancho;
		this.x = x;
		this.y = y;
		this.velocidadDeSalto = velocidad;
		this.color = Color.WHITE;
		//this.direccion = -Math.PI/2;
		bajadaDePantalla=movAbajo;
		// acá también va lo de bajadaDePantalla
	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarRectangulo(x, y, altura, ancho,-Math.PI/2, color);
	}

	public void esperar () {
		y += bajadaDePantalla;
	}
	
	public void saltar() {
		sonidoSalto();
		if (y-altura/2-velocidadDeSalto <= 0) {
			y = altura/2;
			
		}else {
			y -= velocidadDeSalto;
		}
	}

	public void saltarIzquierda() {
		//falta agregar cambio de direccion
		sonidoSalto();
		if (x-ancho/2-velocidadDeSalto <= 0) {
			x = ancho/2;
		}else {
			x -= velocidadDeSalto;
		}
		
	}

	public void saltarDerecha(Entorno entorno) {
		//falta agregar cambio de direccion	
		sonidoSalto();
		if (x+ancho/2+velocidadDeSalto >= entorno.ancho()) {
			x = entorno.ancho() - ancho/2;
		}else {
			x += velocidadDeSalto;
		}
	}
	
	
	
	private void sonidoSalto() {
		try {
			Clip salto = AudioSystem.getClip();
			salto.open(AudioSystem.getAudioInputStream(new File("jump.wav")));
			salto.start();
			while (salto.isRunning())
                Thread.sleep(1000);
		}catch  (Exception e){
			System.out.println(""+e);
			
		}
	}
	
	
	
	
	
	
}
