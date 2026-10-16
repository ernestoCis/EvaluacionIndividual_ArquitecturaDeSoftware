/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.controladores;

import datos.entidades.Alumno;
import datos.entidades.Curso;
import datos.entidades.FichaPago;
import datos.entidades.Inscripcion;
import java.util.List;
import java.util.UUID;
import negocio.servicios.InscripcionService;
import presentacion.PantallaFichaPagoFrm;
import presentacion.PantallaSeleccionCursosFrm;

/**
 *
 * @author jesus
 */
public class InscripcionController {
    private final InscripcionService service;
    private PantallaSeleccionCursosFrm vista;
    private final Inscripcion inscripcion;
    
    public InscripcionController(InscripcionService service) {
        this.service = service;
        
        Alumno alumno = new Alumno("00000262722", "Jesus Ernesto Cisneros", "Ingeniería en Software");
        this.inscripcion = new Inscripcion("INS-" + UUID.randomUUID().toString().substring(0, 5), alumno);
    }
    
    public void setVista(PantallaSeleccionCursosFrm vista) {
        this.vista = vista;
    }
    
    public List<Curso> obtenerCursosDisponibles() {
        return service.obtenerCatalogo();
    }
    
    public void seleccionarCurso(String idCurso) {
        service.agregarCurso(inscripcion, idCurso);
        if (vista != null) {
            vista.actualizarResumen(inscripcion.obtenerCursos(), inscripcion.getTotal());
        }
    }
    
    public void deseleccionarCurso(String idCurso) {
        service.eliminarCurso(inscripcion, idCurso);
        if (vista != null) {
            vista.actualizarResumen(inscripcion.obtenerCursos(), inscripcion.getTotal());
        }
    }
    
    public FichaPago confirmarInscripcion(String matricula) {
        FichaPago ficha = service.finalizarInscripcion(inscripcion);

        PantallaFichaPagoFrm pantallaFicha = new PantallaFichaPagoFrm(ficha, this);
        pantallaFicha.cargarDatosCompletos(
            inscripcion.getAlumno(),
            ficha,
            inscripcion.obtenerCursos()
        );
        pantallaFicha.setVisible(true);

        if (vista != null) {
            vista.dispose();
        }

        return ficha;
    }
    
    /**
     * Reinicia el flujo de inscripción creando una nueva sesión y reabriendo la pantalla principal
     */
    public void reiniciarInscripcion() {
        InscripcionController nuevoController = new InscripcionController(service);
        PantallaSeleccionCursosFrm nuevaVista = new PantallaSeleccionCursosFrm(nuevoController);
        nuevoController.setVista(nuevaVista);
        nuevaVista.mostrarCatalogo(nuevoController.obtenerCursosDisponibles());
        nuevaVista.setVisible(true);
    }
}
