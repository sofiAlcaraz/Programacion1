package juego;

import java.awt.Color;

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
		
		
}
