package juego;

import java.awt.Color;

import entorno.Entorno;

public class Rasengan {
	private double x;
	private double y;
	private double velocidad;
	private Color color;
	private int diametro;

	public Rasengan(double x, double y) 
        {
            this.x = x;
            this.y = y;
            this.velocidad = 5;
            this.color = Color.YELLOW;
            this.diametro = 20;
	}

	public void dibujar(Entorno entorno) 
        {
            entorno.dibujarCirculo(x, y, diametro, color);
	}

	public void mover() {
            y -= velocidad;
	}
        
        public boolean saleDePantalla()
        {
            if(this.y + this.diametro < 0)
                return true;
            
            return false;
        }

        /*
        EL METODO DEVUELVE UN ENTERO PARA CONTROLAR LA DESTRUCCION DE UN
        AUTO. USA EL METODO INTERNO "COLISIONASTE", PARA COMPROBAR CON
        QUE AUTO A COLISIONADO
        */
	public int destruisteAuto(Auto[] autos) 
        {
            for(int i=0; i<autos.length; i++)
            {
                if(colisionaste(autos[i]))
                {
                    return i;
                }
            }
            
            return -1;
	}
        
        /*
        EL METODO "COLISIONASTE" GESTIONA LA COLISION "RECTANGULO-CIRCULO"
        PARA CONTROLAR CUANDO EL RASENGAN HA COLISIONADO CON UN AUTO
        */
        private boolean colisionaste(Auto auto)
        {
            // el auto es un rectangulo
            double testX = this.x;
            double testY = this.y;
            
            if(this.x < auto.getX())
                testX = auto.getX(); // lado izquierdo
            else if(this.x > auto.getX()+auto.getAncho())
                testX = auto.getX()+auto.getAncho(); // lado derecho
            
            if(this.y < auto.getY())
                testY = auto.getY(); // lado superior
            else if(this.y > auto.getY()+auto.getAltura())
                testY = auto.getY()+auto.getAltura(); // lado inferior
            
            double distX = this.x-testX;
            double distY = this.y-testY;
            double distance = Math.sqrt((distX*distX)+(distY*distY));
            
            if(distance <= this.diametro)
                return true;
            
            return false;
        }

	public double getY() 
        {
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
