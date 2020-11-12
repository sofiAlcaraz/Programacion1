package juego;

import java.awt.Color;

import entorno.Entorno;

public class Rasengan {
		private double y;
		private double x;
		private double velosidad;
		private double sentido;//solo para adelnte o para los costados tambien?
		private double deslizamiento;
		private Color color;
		private int diametro;
		
		public Rasengan(double y, double x, double sentido, double deslizamiento) {
			this.y = y;
			this.x = x;
			this.velosidad =3;
			this.sentido = sentido;
			this.deslizamiento = deslizamiento;
			this.color = Color.YELLOW;
			this.diametro = 2;
		}
		
		public void dibujarRasengan(Entorno entorno) {
			entorno.dibujarCirculo(x, y, diametro, color);
		}
		public void mover() {
			y-=deslizamiento;
			y+=velosidad;
			//FIXMI
		}
		public void moverDerecha() {
			y-=deslizamiento;
			x+=velosidad;
		}
		public void moverIzquierda() {
			y-=deslizamiento;
			x-=velosidad;
		}
		
		
		
		
		
		
}
