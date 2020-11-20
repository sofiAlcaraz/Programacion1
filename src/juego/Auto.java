package juego;

import java.awt.Color;

import entorno.Entorno;

public class Auto {
	private double alto;
	private double ancho;
	private double x;
	private double y;
	private double velocidad;
	private boolean sentido;
	private double bajadaDePantalla;
	private Color color;

	public Auto(double altura, double ancho, double x, double y, double velocidad, boolean sentido,
			double bajadaDePantalla) {
		this.alto = altura;
		this.ancho = ancho;
		this.x = x;
		this.y = y;
		this.velocidad = velocidad;
		this.sentido = sentido;
		this.bajadaDePantalla = bajadaDePantalla;
		this.color = color.RED;
	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarRectangulo(x, y, ancho, alto, 0, color);// cambie direccion por cero ya que no lo vamos a usar
	}

        /*
        LOS OBJETOS DEBEN DESAPARECER CUANDO ESTAN TOTALMENTE FUERA DE PANTALLA
        POR ELLO LA CORREPCION EN (•) Y (♥)
        • AQUI SE CONTROLA CUANDO EL AUTO VA HACIA LA IZQUIERDA
        PARA QUE EL AUTO DESAPAREZCA, SIMPLEMETE SE SUMA EL ANCHO DEL AUTO A LA
        POSICION EN X DEL MISMO. SI EL VALOR DADO, ES MENOR A 0, SIGNIFICA QUE EL
        AUTO ESTA FUERA DE PANTALLA
        ♥ AQUI SE CONTROLA CUANDO EL AUTO VA HACIA LA DERECHA
        PARA QUE EL AUTO DESAPAREZCA, SE CONTROLA SI LA POSICION EN X ES MAYOR AL
        ANCHO DE LA PANTALLA SUMADO CON EL ANCHO DEL AUTO, SE SER ASI, EL AUTO ESTARIA
        FUERA DE LA PANTALLA
        SI NO SE DESEAN ERRORES DE SOBREPOSICION DE AUTOS, DEJAR LAS SIGUIENTES ECUACIONES
        TAL CUAL HAN SIDO CORREGIDAS
        
        PARA(•) -> (X + ANCHO < 0)
        PARA(♥) -> (X > ENTORNO.ANCHO()+ANCHO)
        
        CONSEJO DE PROGRAMDOR DE VIDEOJUEGOS:
        PARA MEJOR ESTETICA EN EL AMBIO DE VIDEOJUEGOS, CUANDO SE TRABAJA CON OBJETOS
        QUE SALDRAN DE LA PANTALLA, EN EL CASO DE QUE SEA UNA PANTALLA ESTATICA, ES
        RECOMENDABLE ESPERAR A QUE EL OBJETO SALGA TOTALMENTE DE LA PANTALLA, DE LO
        CONTRARIO DEJA UN EFECTO ALGO RARO, YA QUE EL OBJETO DESAPARECE DE LA NADA.
        SI TRABAJAN CON CAMARAS, AL OBJETO SE LE DA UN TIEMPO DE VIDA, CUANDO SE USAN
        CAMARAS, EL EFECTO DE QUE EL OBJETO SE DESTRUYA CUANDO SU TIEMPO DE VIDA SE ACABA
        NO SE NOTA, EN EL CASO DE USO DE CAMARAS, EL EFECTO QUEDA BONITO.
        */
	public void mover(Entorno entorno) {
            y += this.bajadaDePantalla;
            if (y - ancho / 2 > 600) {
                    y -= entorno.alto() * 2;
            }
            if (sentido) { // •
                    if (x+ancho < 0) {
                            x = entorno.ancho();
                    } else {
                            x -= velocidad;
                    }
            } else if (x  > entorno.ancho()+ancho) { // ♥
                    x = 0;
            } else {
                    x += velocidad;
            }
	}
	
	

	public double getAltura() {
		return alto;
	}

	public double getAncho() {
		return ancho;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

}
