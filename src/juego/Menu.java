package juego;

import entorno.Entorno;
import java.awt.Color;

public class Menu 
{
    // para boton jugar
	// si son constantes
    private final double POSICION_DEL_BOTON_JUGAR_X = 800/2;
    private final double POSICION_DEL_BOTON_JUGAR_Y = 800/2;

    private double jugarBotonPosY = 600/2 - 50;
    // para boton salir
    private double salirBotonPosX = 800/2;
    private double salirBotonPosY = 600/2 + 50;
    // marcador de seleccion
    private double seleccionadoX = 800/2 - 150/2 - 30;
    private double seleccionadoY = jugarBotonPosY;
    
    // accion actual del menu principal
    private String accion = "jugar";
    
	// Menu menu = new Menu();
	// 
	// menu.dibujarMenu();
	//
//    public void dibujarMenu(Entorno entorno, Juego juego)
//    {
//		// si son variables van acá
//		accion = "jugar"; // va en el constructor
//        botonJugar(entorno);
//        botonSalir(entorno);
//        mostrarSeleccionado(entorno);
//        actualizarSeleccion(entorno);
//        ejecutarAccion(entorno, juego);
//    }
    
    private void actualizarSeleccion(Entorno entorno)
    {
        if (entorno.sePresiono(entorno.TECLA_ABAJO))
        {
            seleccionadoY = salirBotonPosY;
            accion = "salir";
        }
        if (entorno.sePresiono(entorno.TECLA_ARRIBA))
        {
            seleccionadoY = jugarBotonPosY;
            accion = "jugar";
        }
    }
    
<<<<<<< HEAD
    private void ejecutarAccion(Entorno entorno, Juego juego)
    {
        if(entorno.sePresiono('x'))
        {
            if(accion == "jugar")
            {
            	
               // juego.setRunning(true);
               // juego.setPausado(false);
            }
            if(accion == "salir")
            {
               System.exit(0);
            }
        }
    }
=======
//    private void ejecutarAccion(Entorno entorno, Juego juego)
//    {
//        if(entorno.sePresiono('x'))
//        {
//            if(accion == "jugar")
//            {
//                juego.setRunning(true);
//                juego.setPausado(false);
//            }
//            if(accion == "salir")
//            {
//               System.exit(0);
//            }
//        }
//    }
>>>>>>> 5a72322aeae2fa58715e0c85d77c4382d283d985
    
    private void mostrarSeleccionado(Entorno entorno)
    {
        entorno.dibujarTriangulo(seleccionadoX, seleccionadoY, 20, 50, 0, Color.BLUE);
    }
    
<<<<<<< HEAD
    private void botonJugar(Entorno entorno)
    {//FIXME
        entorno.dibujarRectangulo(POSICION_DEL_BOTON_JUGAR_X, jugarBotonPosY, 150, 50, 0, Color.GREEN);
    }
=======
//    private void botonJugar(Entorno entorno)
//    {
//        entorno.dibujarRectangulo(jugarBotonPosX, jugarBotonPosY, 150, 50, 0, Color.GREEN);
//    }
>>>>>>> 5a72322aeae2fa58715e0c85d77c4382d283d985
    
    private void botonSalir(Entorno entorno)
    {
        entorno.dibujarRectangulo(salirBotonPosX, salirBotonPosY, 150, 50, 0, Color.RED);
    }
    
    public String getAccion()
    {
        return this.accion;
    }

}
