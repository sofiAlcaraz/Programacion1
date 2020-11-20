package juego;

import java.awt.Image;

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
	private Image imagenConejoEsperando;
	private Image imagenConejoIzquierda;
	private Image imagenConejoDerecha;
	private char ultimoMovimiento;

	public Conejo(double altura, double ancho, double x, double y, double velocidad, double movAbajo) {
		this.altura = altura;
		this.ancho = ancho;
		this.x = x;
		this.y = y;
		this.velocidadDeSalto = velocidad;
		this.ultimoMovimiento=' ';
		this.imagenConejoEsperando = Herramientas.cargarImagen("conejoEsperando.png");
		this.imagenConejoIzquierda = Herramientas.cargarImagen("conejoIzquierda.png");
		this.imagenConejoDerecha = Herramientas.cargarImagen("conejoDerecha.png");
		this.deslizarHaciaAbajo = movAbajo;

	}

	public void dibujar(Entorno entorno) {

		switch (ultimoMovimiento) {
		case 'i':
			entorno.dibujarImagen(imagenConejoIzquierda, x, y, 0, 2);
			break;
		case 'd':
			entorno.dibujarImagen(imagenConejoDerecha, x, y, 0, 2);
			break;
		default:
			entorno.dibujarImagen(imagenConejoEsperando, x, y, 0, 2);
			break;

		}
	}

	public void hacerSonidoDeSalto() {
		Herramientas.cargarSonido("jump.wav").start();
	}

	public void esperar() {
		y += deslizarHaciaAbajo;
	}

	public void saltar(Entorno entorno, double altoAuto, double espacioEntreAutos) {
		ultimoMovimiento = ' ';

		if (y - altura / 2 - velocidadDeSalto <= 0) {
			y = altura;
		} else {
			y -= altoAuto / 2 + espacioEntreAutos;
		}
	}

	public void saltarIzquierda(Entorno entorno) {
		ultimoMovimiento = 'i';
		if (x - ancho / 2 - velocidadDeSalto <= 0) {
			x = ancho / 2;
		} else {
			x -= velocidadDeSalto;
		}

	}

	public void saltarDerecha(Entorno entorno) {
		ultimoMovimiento = 'd';
		if (x + ancho / 2 + velocidadDeSalto >= entorno.ancho()) {
			x = entorno.ancho() - ancho / 2;
		} else {
			x += velocidadDeSalto;

		}
	}

	public Rasengan disparar() {
		return new Rasengan(x, y);
	}

	public boolean seFueDePantalla() {
		if (y + (altura / 2) > 600) {
			return true;
		}
		return false;
	}

	public boolean chocasteAlgunAuto(Auto[] autos) {
		for (int i = 0; i < autos.length; i++) {
			if (autos[i] != null) {
				if (x <= autos[i].getX() + autos[i].getAncho() / 2 && x + ancho >= autos[i].getX()
						&& y < autos[i].getY() + autos[i].getAltura() / 2 && y + altura > autos[i].getY()) {
					return true;
				}
				if (x <= autos[i].getX() + autos[i].getAncho() / 2 && x + ancho >= autos[i].getX()
						&& y < autos[i].getY() + autos[i].getAltura() / 2 && y + altura > autos[i].getY()) {
					return true;
				}
			}
		}
		return false;
	}

}
