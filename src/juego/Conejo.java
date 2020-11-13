package juego;

import java.awt.Color;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import entorno.Entorno;
import entorno.Herramientas;

public class Conejo {
	// por el momento está bien, pero hay que corregirlo
	private double altura;
	private double ancho;
	private double x;
	private double y;
	private double velocidadDeSalto;
	private double deslizarHaciaAbajo;
	private Color pelaje;

	public Conejo(double altura, double ancho, double x, double y, double velocidad, double movAbajo) {
		this.altura = altura;
		this.ancho = ancho;
		this.x = x;
		this.y = y;
		this.velocidadDeSalto = velocidad;
		this.pelaje = Color.WHITE;
		deslizarHaciaAbajo = movAbajo;
	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarRectangulo(x, y, altura, ancho, -Math.PI / 2, pelaje);
	}

	public void esperar() {
		y += deslizarHaciaAbajo;
	}

	public void saltar() {
		// sonidoSalto();
		if (y - altura / 2 - velocidadDeSalto <= 0) {
			y = altura / 2;
		} else {
			y -= velocidadDeSalto;
		}
	}

	public void saltarIzquierda() {
		// sonidoSalto();
		if (x - ancho / 2 - velocidadDeSalto <= 0) {
			x = ancho / 2;
		} else {
			x -= velocidadDeSalto;
		}

	}

	public void saltarDerecha(Entorno entorno) {
		// sonidoSalto();
		if (x + ancho / 2 + velocidadDeSalto >= entorno.ancho()) {
			x = entorno.ancho() - ancho / 2;
		} else {
			x += velocidadDeSalto;
		}
	}

	public Rasengan disparar() {
		return new Rasengan(x, y);
	}

	// DELETE
//	private void sonidoSalto(Herramientas her) {
//		//Herramientas.cargarSonido("jump")
//		try {
//			Clip salto = AudioSystem.getClip();
//			salto.open(AudioSystem.getAudioInputStream(new File("jump.wav")));
//			salto.start();
//			while (salto.isRunning())
//				Thread.sleep(1000);
//		} catch (Exception e) {
//			System.out.println("" + e);
//
//		}
//	}

	public boolean chocasteAlgunAuto(Auto[] autos) {
		for (int i = 0; i < autos.length; i++) {
			if (x < autos[i].getX() + autos[i].getAncho() && x + ancho > autos[i].getX()
					&& y < autos[i].getY() + autos[i].getAltura() && y + altura > autos[i].getY()) {
				return true;
			}
			if (x < autos[i].getX() + autos[i].getAncho() && x + ancho > autos[i].getX()
					&& y < autos[i].getY() + autos[i].getAltura() && y + altura > autos[i].getY()) {
				return true;
			}
		}
		return false;
	}

}
