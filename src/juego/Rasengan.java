package juego;

import java.awt.Color;

import entorno.Entorno;

public class Rasengan {
	private double x;
	private double y;
	private double velocidad;
	private Color color;
	private int diametro;

	public Rasengan(double x, double y) {
		this.x = x;
		this.y = y;
		this.velocidad = 20;
		this.color = Color.YELLOW;
		this.diametro = 20;
	}

	// rasengan.dibujar();
	//
	public void dibujar(Entorno entorno) {
		entorno.dibujarCirculo(x, y, diametro, color);
	}

	public void mover() {
		y -= velocidad;
	}

	// destruisteAuto(auto)
	public boolean colicionAuto(Auto auto) {
		if (y > auto.getY() - auto.getAltura() / 2 && y < auto.getY() + auto.getAltura() / 2) {
			if ((auto.getX() - auto.getAncho() / 2) <= x + diametro
					|| (auto.getX() + auto.getAncho() / 2 <= x + diametro)) {
				System.out.println("coliciona con auto de costado");
				return true;
			}
		}
		if (y == (auto.getY() + auto.getAltura() / 2) && x == auto.getX()) {
			System.out.println("coliciona con auto de frente");
			return true;
		}
		return false;
	}

}
