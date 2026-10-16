/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;

import datos.dao.*;
import datos.mock.*;
import javax.swing.SwingUtilities;
import negocio.controladores.InscripcionController;
import negocio.servicios.InscripcionService;
import presentacion.PantallaSeleccionCursosFrm;

/**
 *
 * @author jesus
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Instanciar Mocks de BD
            ICursoDAO cursoDAO = new CursoDAOMock();
            IInscripcionDAO inscripcionDAO = new InscripcionDAOMock();

            InscripcionService service = new InscripcionService(cursoDAO, inscripcionDAO);

            InscripcionController controller = new InscripcionController(service);

            PantallaSeleccionCursosFrm vista = new PantallaSeleccionCursosFrm(controller);
            controller.setVista(vista);

            vista.mostrarCatalogo(controller.obtenerCursosDisponibles());
            vista.setVisible(true);
        });
    }
    
}
