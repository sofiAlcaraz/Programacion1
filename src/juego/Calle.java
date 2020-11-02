package juego;

import java.awt.Color;

import entorno.Entorno;

public class Calle {
	private double largo;
	private double ancho;
	private Color color;
	private double x;
	private double y;
	private double bajadaDePantalla;// podria ser deslizar
	private Auto [] autos;

	public Calle(double altura, double ancho, double x, double y, double bajadaDePantalla) {
		this.largo = altura;
		this.ancho = ancho;
		this.color = color.GRAY;
		this.x = x;
		this.y = y;
		this.bajadaDePantalla = bajadaDePantalla;
		this.autos=new Auto[6];
	}

	public void mover() {
		y += bajadaDePantalla;
		if (y - largo / 2 >= 600) {
			y -= y + largo / 2;
		}

	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarRectangulo(x, y, ancho, largo, 0, color);
	}
	
	
	public void agregarAutos(Entorno entorno) {
		for(int i=0;i<autos.length;i++) {
			if(autos[i]==null) {
			autos[i]=new Auto(60, 45, entorno.ancho(), entorno.alto() / 4, 3, true, bajadaDePantalla);
			}
		}
	}
	

}
