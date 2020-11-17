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
		this.velocidad = 5;
		this.color = Color.YELLOW;
		this.diametro = 20;

	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarCirculo(x, y, diametro, color);
	}

	public void mover() {
		y -= velocidad;
	}

	public boolean destruisteAuto(Auto[] autos) { // FIXME
		for (int i = 0; i < autos.length; i++) {
<<<<<<< HEAD
			if(autos[i]!=null) {
			if (y <= autos[i].getY() + autos[i].getAltura() / 2) {
				if ((autos[i].getX() - autos[i].getAncho() / 2) <= x + diametro
						|| (autos[i].getX() + autos[i].getAncho() / 2 <= x + diametro)) {
=======
			if (autos[i] != null && y >= autos[i].getY() - autos[i].getAltura() / 2 && y <= autos[i].getY() + autos[i].getAltura() / 2
					&& x  >= autos[i].getX() - autos[i].getAncho() / 2
					&& x  <= autos[i].getX() + autos[i].getAncho() / 2) {
				{
					autos[i] = null;
>>>>>>> b6fee159f36b64dea587cbc3095a3eb9f75ce0e0
					System.out.println("coliciona con auto de costado");
					return true;
				}
			}
		}
		return false;
	}

	public double getY() {
		return y;
	}

//	public boolean destruisteAuto(Auto[] autos) {  //FIXME
//		for (int i = 0; i < autos.length; i++) {
//			if(autos[i]!=null) {
//			if (y > autos[i].getY() - autos[i].getAltura() / 2 && y < autos[i].getY() + autos[i].getAltura() / 2) {
//				if ((autos[i].getX() - autos[i].getAncho() / 2) <= x + diametro
//						|| (autos[i].getX() + autos[i].getAncho() / 2 <= x + diametro)) {
//					System.out.println("coliciona con auto de costado");
//					
//					return true;
//				}
//			}
//			if (y == (autos[i].getY() + autos[i].getAltura() / 2) && x == autos[i].getX()) {
//				System.out.println("coliciona con auto de frente");
//				autos[i]=null;
//				return true;
//			}
//		}
//		}
//		return false;
//	}

}
