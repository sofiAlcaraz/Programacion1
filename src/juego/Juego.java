package juego;

import java.awt.Color;

import javax.sound.sampled.Clip;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	private int reloj; // tiempo, reloj

	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private double velocidadDeBajadaDePantalla;
	private Conejo conejo;
	private Calle callePrimaria; // calleAngosta
	private Calle calleSecundaria; // calleSuperior,
	private Auto[] autosCalleSecundaria;
	private Auto[] autosCallePrimaria;
	private Rasengan[] rasengans; // lista mutable de los Rasengans
//	Menu menu = new Menu();
	private boolean partidaCorriendo;
	private boolean partidaPausada;
	private Clip jump;
	private int intentos;
	private int puntaje;
	private int saltos;

	public Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Conejo Ninja", 800, 600);
		// Inicializar lo necesario para el juego
		partidaCorriendo = false;
		partidaPausada = false;
		velocidadDeBajadaDePantalla = 1;

		// BUSCAR SIMETRIA ENTRE DISTANCIA DE AUTOS

		int altoDelAuto = 42;
		int altoDeLaCalle = 220;
		int extremoInferiorCallePrimaria = entorno.alto() / 10 + altoDeLaCalle / 2;
		int extremoInferiorCalleSecundaria = entorno.alto() / 10 * -4 + altoDeLaCalle / 2;
		int espacioEntreAutos = (altoDeLaCalle - (altoDelAuto * 4)) / 5;
		int posicionPrimerAutoCallePrimaria = extremoInferiorCallePrimaria - espacioEntreAutos - altoDelAuto / 2;
		int posicionDelSiguienteAuto = espacioEntreAutos + altoDelAuto;
		int posicionDelPrimerAutoCalleSecundaria = extremoInferiorCalleSecundaria - espacioEntreAutos - altoDelAuto / 2;

		// AUDIOS
		jump = Herramientas.cargarSonido("jump.wav");

		// CREA AUTOS EN LA CALLE PRIMARIA
		autosCallePrimaria = new Auto[16];
		double posicionEnXAutosHaciaDerecha = 0;
		double posicionEnXAutosHaciaIzquierda = entorno.ancho();

		for (int i = 0; i < autosCallePrimaria.length; i++) {
			if (i < 4) {
				autosCallePrimaria[i] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaDerecha,
						posicionPrimerAutoCallePrimaria, 2, false, velocidadDeBajadaDePantalla);
				posicionEnXAutosHaciaDerecha += autosCallePrimaria[i].getAncho() * 3;

			} else if (i < 8) {
				autosCallePrimaria[i] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaIzquierda,
						posicionPrimerAutoCallePrimaria - posicionDelSiguienteAuto, 1, true,
						velocidadDeBajadaDePantalla);
				posicionEnXAutosHaciaIzquierda += autosCallePrimaria[i].getAncho() * 3;
			}

			else if (i < 12) {
				autosCallePrimaria[i] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaDerecha,
						posicionPrimerAutoCallePrimaria - posicionDelSiguienteAuto * 2, 2, false,
						velocidadDeBajadaDePantalla);
				posicionEnXAutosHaciaDerecha += autosCallePrimaria[i].getAncho() * 3;
			} else {
				autosCallePrimaria[i] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaIzquierda,
						posicionPrimerAutoCallePrimaria - posicionDelSiguienteAuto * 3, 1, true,
						velocidadDeBajadaDePantalla);
				posicionEnXAutosHaciaIzquierda += autosCallePrimaria[i].getAncho() * 3;
			}

		}

		// CREA AUTOS EN LA CALLE SECUNDARIA
		autosCalleSecundaria = new Auto[16];
		posicionEnXAutosHaciaDerecha = 0;
		posicionEnXAutosHaciaIzquierda = entorno.ancho();

		for (int i = 0; i < autosCalleSecundaria.length; i++) {
			if (i < 4) {
				autosCalleSecundaria[i] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaIzquierda,
						posicionDelPrimerAutoCalleSecundaria, 2, true, velocidadDeBajadaDePantalla);
				posicionEnXAutosHaciaIzquierda += autosCalleSecundaria[i].getAncho() * 5;
			} else if (i < 8) {
				autosCalleSecundaria[i] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaDerecha,
						posicionDelPrimerAutoCalleSecundaria - posicionDelSiguienteAuto, 1, false,
						velocidadDeBajadaDePantalla);
				posicionEnXAutosHaciaDerecha += autosCalleSecundaria[i].getAncho() * 5;
			} else if (i < 12) {
				autosCalleSecundaria[i] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaIzquierda,
						posicionDelPrimerAutoCalleSecundaria - posicionDelSiguienteAuto * 2, 2, true,
						velocidadDeBajadaDePantalla);
				posicionEnXAutosHaciaIzquierda += autosCalleSecundaria[i].getAncho() * 5;
			} else {
				autosCalleSecundaria[i] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaDerecha,
						posicionDelPrimerAutoCalleSecundaria - posicionDelSiguienteAuto * 3, 1, false,
						velocidadDeBajadaDePantalla);
				posicionEnXAutosHaciaDerecha += autosCalleSecundaria[i].getAncho() * 5;
			}
		}

		conejo = new Conejo(30, 30, entorno.ancho() / 2, posicionPrimerAutoCallePrimaria + 420, 40,
				velocidadDeBajadaDePantalla);

		callePrimaria = new Calle(altoDeLaCalle, 810, entorno.ancho() / 2, entorno.alto() / 10,
				velocidadDeBajadaDePantalla);
		calleSecundaria = new Calle(altoDeLaCalle, 810, entorno.ancho() / 2, (entorno.alto() / 10) * -4,
				velocidadDeBajadaDePantalla);

		intentos = 1;

		// Inicia el juego!
		entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y por lo
	 * tanto es el método más importante de esta clase. Aquí se debe actualizar el
	 * estado interno del juego para simular el paso del tiempo (ver el enunciado
	 * del TP para mayor detalle).
	 */
	public void tick() {
		// Procesamiento de un instante de tiempo
		reloj++;

		// if (estaIniciado && !estáPausado) {

		// if (running && !pausado) {
		// if(intentos!=0) {

		if (entorno.sePresiono('w') || entorno.sePresiono(entorno.TECLA_ARRIBA)) {
			conejo.saltar();
			saltos++;
			jump.start();
		}

		if (entorno.sePresiono('a') || entorno.sePresiono(entorno.TECLA_IZQUIERDA)) {
			conejo.saltarIzquierda();
			jump.start();
		}

		if (entorno.sePresiono('d') || entorno.sePresiono(entorno.TECLA_DERECHA)) {
			conejo.saltarDerecha(entorno);
			jump.start();

		}

		// SONIDOS EN EL JUEGO
		if (entorno.sePresiono('a') || entorno.sePresiono('s') || entorno.sePresiono('d') || entorno.sePresiono('w')
				|| entorno.sePresiono(entorno.TECLA_ABAJO) || entorno.sePresiono(entorno.TECLA_ARRIBA)
				|| entorno.sePresiono(entorno.TECLA_DERECHA) || entorno.sePresiono(entorno.TECLA_IZQUIERDA)) {
			jump.start();

		}

		if (conejo.chocasteAlgunAuto(autosCalleSecundaria) || conejo.chocasteAlgunAuto(autosCallePrimaria)
				|| conejo.seFueDePantalla()) {
			intentos--;
		}
		if (entorno.sePresiono(entorno.TECLA_ESPACIO)) {
			// mutar lista
			if (rasengans != null) {
				Rasengan[] r = new Rasengan[rasengans.length + 1];
				for (int i = 0; i < rasengans.length; i++) {
					r[i] = rasengans[i];
				}
				r[r.length - 1] = conejo.disparar();
				rasengans = r;
			} else {
				Rasengan[] r = new Rasengan[1];
				r[0] = conejo.disparar();
				rasengans = r;
			}
		}

		// dibujar rasengans y controlar sus colisiones

		if (entorno.sePresiono('p')) {
			partidaPausada = true;
		}

		callePrimaria.deslizarHaciaAbajo(entorno);
		callePrimaria.dibujar(entorno);

		calleSecundaria.deslizarHaciaAbajo(entorno);
		calleSecundaria.dibujar(entorno);

		// Conejo
		conejo.esperar();
		conejo.dibujar(entorno);

		for (Auto a : autosCallePrimaria) {
			if (a != null) {
				a.mover(entorno);
				a.dibujar(entorno);
			}
		}

		for (Auto a : autosCalleSecundaria) {
			if (a != null) {
				a.mover(entorno);
				a.dibujar(entorno);
			}
		}

		if(rasengans!=null) {
			for (int i=0;i<rasengans.length;i++) {
				if(rasengans[i]!=null && rasengans[i].destruisteAuto(autosCallePrimaria)) {	
					puntaje+=5;
					
				}
				if(rasengans[i]!=null && rasengans[i].destruisteAuto(autosCalleSecundaria)) {
					puntaje+=5;
					
				}
				else if(rasengans[i]!=null && !rasengans[i].saleDePantalla()) {
					rasengans[i].dibujar(entorno);
					rasengans[i].mover();
				}
		}


			// CONTROLA LA COLISION DE LOS RASENGANS
			// EL CONDICIONAL ES NECESARIO PARA EVITAR EL ERROR "NULL-POINTER"
//		if (rasengans != null) {
//			// ITERA POR CADA RASENGAN EN LA LISTA "RASENGANS"
//			for (int i = 0; i < rasengans.length; i++) {
//				// destruye un ransengan y el auto con el que ha colisionado (calle primaria)
//
//				// EL CONTROLADOR SIRVE PARA COMPROBAR SI HUBO UNA COLISION
//				// SI EL VALOR DEL CONTROLADOR ES "-1" ES PORQUE NO HUBO COLISION
//				// SI EL VALOR ES DISTINTO DE -1, SIGNIFICA QUE HUBO UNA COLISION
//				int controladorCallePrimaria = rasengans[i].destruisteAuto(autosCallePrimaria);
//				if (controladorCallePrimaria != -1) {
//					Rasengan[] r = new Rasengan[rasengans.length - 1]; // CREA UN NUEVO ARRAY PARA EL NUEVO TAMAÑO DEL
//																		// ARRAY DE LOS RASENGANS
//					int controladorRasengans = 0; // UN CONTROLADOR PARA COPIAR LOS OBJETOS EN LA NUEVA LISTA, SIRVE
//													// PARA EVIAR EL "INDEX OUT ERROR"
//					if (r != null) // COMPRUEBA SI LA LISTA "R" ES NULA O NO
//					{
//						// RECORRE LA LISTA DE RASENGANS
//						for (int j = 0; j < rasengans.length; j++) {
//							// SI EL VALOR DEL INDICE "J" EN LA LISTA DE RASENGANS ES DIFERENTE AL VALOR DE
//							// RASENGAS EN EL INDICE I, EJECUTA EL BLOQUE DE CODIGO
//							if (j != i) {
//								r[controladorRasengans] = rasengans[j]; // ASIGNA EL VALOR DE RASENGANS[I] EN R[I] ->
//																		// ESTO ES LA COPIA DE LOS DATOS QUE QUEREMOS
//																		// CONSERVAR
//								controladorRasengans++; // AUMENTA EL VALOR DEL CONTROLADOR PARA SEGUIR AREGREGANDO
//														// ELEMENTOS REQUERIDOS EN "R"
//							}
//						}
//						rasengans = r; // ASGINA LA NUEVA LISTA DE RASENGANS A LA ANTIGUA LISTA
//					}
//
//					// ELIMINA EL AUTO CON EL QUE COLISIONO
//					Auto[] a = new Auto[autosCallePrimaria.length - 1];
//					int controladorAutos = 0; // CONTROLADOR QUE AYUDA A EVITAR EL ERROR "INDEX OUT"
//					if (a != null) // CONTROLA SI EL ARRAY "A" ES NULO O NO
//					{
//						// ITERA EN LA LISTA DE AUTOS DE LA CALLE PRIMARIA
//						for (int n = 0; n < autosCallePrimaria.length; n++) {
//							if (n != controladorCallePrimaria) // SI EL OBJETO "N" EN LA LISTA DE AUTOS DE LA CALLE
//																// PRIMARIA ES DISTINTA AL CONTROLADOR DE AUTOS, EJECUTA
//																// EL BLOQUE DE CODIGO
//							{
//								a[controladorAutos] = autosCallePrimaria[n]; // ASIGNA EL ELEMENTO "N" DE LA LISTA DE
//																				// AUTOS DE LA CALLE PRIMARIA A LA NUEVA
//																				// LISTA
//								controladorAutos++; // AUMENTA EL CONTROLADOR
//							}
//						}
//						autosCallePrimaria = a; // ASGINA LA NUEVA LISTA A LA ANTIGUA LISTA DE AUTOS
//					}
//
//					puntaje += 5; // AUMENTA EL PUNTAJE CADA VEZ QUE EL RASENGAN COLISIONA CON UN AUTO
//					return; // SE USA LA PALABRA CLAVE "RETURN" PARA EVITAR QUE EL BUCLE CONTINUE, DE NO
//							// PONERLO, INTENTA TRABAJAR CON UN OBJETO QUE YA NO EXISTE, DANDO COMO
//							// RESULTADO UN "INDEX OUT ERROR"
//				}
//
//				// LO DE ARRIBA X2 XD
//				int controladorCalleSecundaria = rasengans[i].destruisteAuto(autosCalleSecundaria);
//				if (controladorCalleSecundaria != -1) {
//					Rasengan[] r = new Rasengan[rasengans.length - 1];
//					int controladorRasengans = 0;
//					if (r != null) {
//						for (int j = 0; j < rasengans.length; j++) {
//							if (j != i) {
//								r[controladorRasengans] = rasengans[j];
//								controladorRasengans++;
//							}
//						}
//						rasengans = r;
//					}
//
//					if (controladorCalleSecundaria != -1) {
//						Auto[] a = new Auto[autosCalleSecundaria.length - 1];
//						int controladorAutos = 0;
//						if (a != null) {
//							for (int n = 0; n < autosCalleSecundaria.length; n++) {
//								if (n != controladorCalleSecundaria) {
//									a[controladorAutos] = autosCalleSecundaria[n];
//									controladorAutos++;
//								}
//							}
//							autosCalleSecundaria = a;
//						}
//					}
//
//					// agregar un nuevo auto
//
//					puntaje += 5;
//					return;
//				}
//
//				// CONTROLA SI EL RASENGAN ESTÁ FUERA DE LA PANTALLA DE JUEGO
//				// DE SER ASI, ELIMINA EL RASENGAN
//				// ACA NO ES NECESARIO EL USO DE LA PALABRA CLAVE "RETURN"
//				// YA QUE ES LA ULTIMA COMPROBACION QUE SE HACE
//				if (rasengans[i].saleDePantalla()) {
//					Rasengan[] r = new Rasengan[rasengans.length - 1];
//					int controladorRasengans = 0;
//					if (r != null) {
//						for (int j = 0; j < rasengans.length; j++) {
//							if (j != i) {
//								r[controladorRasengans] = rasengans[j];
//								controladorRasengans++;
//							}
//						}
//						rasengans = r;
//					}
//				}
//			}

			// actualizar y dibujar rasengans
			// CONTROLA SI LA LISTA DE RASENGAN ES NULA O NO
			// SI NO SE PONE EL CONDICIONAL, JAVA VA A INTENTAR DIBUJAR UN OBJETO NULO
			// DANDO UN ERROR "NULL-POINTER"
			if (rasengans != null) {
				// ITERA EN CADA RASENGAN, LO ACTUALIZA Y LO DIBUJA
				for (Rasengan r : rasengans) {
					r.mover();
					r.dibujar(entorno);
				}
			}

			// IMPRIME EN CONSOLA LA CANTIDAD DE RASENGANS ACTUALES
			// TAMBIEN ES NECESARIO PONER EL CONDICIONAL, PARA QUE NO DE ERROR
			if (rasengans != null)
				System.out.println(rasengans.length);

			entorno.cambiarFont(Integer.toString(reloj), 30, Color.MAGENTA);
			entorno.escribirTexto("Tiempo: " + Integer.toString(reloj / 100), 30, 30);
			entorno.cambiarFont("", 30, Color.PINK);
			entorno.escribirTexto("saltos:", entorno.ancho() / 2 - 100, 30);
			entorno.cambiarFont(Integer.toString(saltos), 30, Color.PINK);
			entorno.escribirTexto(Integer.toString(saltos), entorno.ancho() / 2, 30);
			entorno.cambiarFont("", 30, Color.PINK);
			entorno.escribirTexto("Puntos:", 550, 30);
			entorno.cambiarFont(Integer.toString(puntaje), 30, Color.PINK);
			entorno.escribirTexto(Integer.toString(puntaje), 700, 30);

			// System.out.println(rasengans.size());

//		if (!partidaCorriendo || partidaPausada) {
//			menu.dibujarMenu(entorno, this);
//		}

			// imprime la accion actual
			// System.out.println(menu.getAccion());

		}

		// si hay algún setter en el código, va a reentrega
//	public void setRunning(boolean value) {
//		this.running = value;
//	}
//
//	public void setPausado(boolean value) {
//		this.pausado = value;
//	}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}

}
