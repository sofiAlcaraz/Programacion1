package juego;

import java.awt.Color;

import entorno.Entorno;

public class Rasengan {
		private double y;
		private double x;
		private double moverse;
		
		private Color color;
		private int diametro;
		
		public Rasengan(double x, double y) {
			this.y = y;
			this.x = x;
			this.moverse =20;
			this.color = Color.YELLOW;
			this.diametro = 50;
		}
		
		public void dibujarRasengan(Entorno entorno) {
			entorno.dibujarCirculo(x, y, diametro, color);
		}
		public void mover() {
			y+=moverse;
			
		}
		public void moverDerecha() {

			x+=moverse;
		}
		public void moverIzquierda() {
			x-=moverse;
		}
		
		
		
		
		
		
		
}
