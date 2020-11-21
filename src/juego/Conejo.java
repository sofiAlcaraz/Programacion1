package juego;

import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;
import entorno.Herramientas;

public class Conejo {
	private Rectangle tamaño;
	private double velocidadDeSalto;
	private double deslizarHaciaAbajo;
	private Image imagenConejoEsperando;
	private Image imagenConejoIzquierda;
	private Image imagenConejoDerecha;
	private char ultimoMovimiento;

	public Conejo(int alto, int ancho, int x, int y, double movAbajo) {
		this.tamaño=new Rectangle(x,y,ancho,alto);
		this.velocidadDeSalto = 40;
		this.ultimoMovimiento = ' ';
		this.imagenConejoEsperando = Herramientas.cargarImagen("conejoEsperando.png");
		this.imagenConejoIzquierda = Herramientas.cargarImagen("conejoIzquierda.png");
		this.imagenConejoDerecha = Herramientas.cargarImagen("conejoDerecha.png");
		this.deslizarHaciaAbajo = movAbajo;

	}

	public void dibujar(Entorno entorno) {

		switch (ultimoMovimiento) {
		case 'i':
			entorno.dibujarImagen(imagenConejoIzquierda, tamaño.x, tamaño.y, 0, 2);
			break;
		case 'd':
			entorno.dibujarImagen(imagenConejoDerecha, tamaño.x, tamaño.y, 0, 2);
			break;
		default:
			entorno.dibujarImagen(imagenConejoEsperando, tamaño.x, tamaño.y, 0, 2);
			break;

		}
	}

	public void esperar() {
		tamaño.y += deslizarHaciaAbajo;
	}

	public void saltar(Entorno entorno, double altoAuto, double espacioEntreAutos) {
		ultimoMovimiento = ' ';
		
		if (tamaño.y - tamaño.height / 2 - velocidadDeSalto <= 0) {//
			tamaño.y = tamaño.height;
		} else {
			tamaño.y -= altoAuto / 2 + espacioEntreAutos;
		}
	}

	public void saltarIzquierda(Entorno entorno) {
		ultimoMovimiento = 'i';
		
		if (tamaño.x - tamaño.width / 2 - velocidadDeSalto <= 0) {
			tamaño.x = tamaño.width / 2;
		} else {
			tamaño.x -= velocidadDeSalto;
		}

	}

	public void saltarDerecha(Entorno entorno) {
		ultimoMovimiento = 'd';
		if (tamaño.x + tamaño.width / 2 + velocidadDeSalto >= entorno.ancho()) {
			tamaño.x = entorno.ancho() - tamaño.width / 2;
		} else {
			tamaño.x += velocidadDeSalto;

		}
	}

	public Rasengan disparar() {
		return new Rasengan(tamaño.x, tamaño.y);
	}

	public boolean seFueDePantalla() {
		if (tamaño.y + (tamaño.height / 2) > 600) {
			return true;
		}
		return false;
	}

	public boolean chocasteAlgunAuto(Auto[] autos) {
		for (int i = 0; i < autos.length; i++) {
			if (autos[i] != null) {
				if (tamaño.x <= autos[i].getX() + autos[i].getAncho() / 2 && tamaño.x + tamaño.width >= autos[i].getX()
						&& tamaño.y < autos[i].getY() + autos[i].getAltura() / 2 && tamaño.y + tamaño.height > autos[i].getY()) {
					return true;
				}
				if (tamaño.x <= autos[i].getX() + autos[i].getAncho() / 2 && tamaño.x + tamaño.width >= autos[i].getX()
						&& tamaño.y < autos[i].getY() + autos[i].getAltura() / 2 && tamaño.y + tamaño.height > autos[i].getY()) {
					return true;
				}
			}
		}
		return false;
	}

}
