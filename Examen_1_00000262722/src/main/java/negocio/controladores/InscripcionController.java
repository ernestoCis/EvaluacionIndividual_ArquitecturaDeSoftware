/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.controladores;

import datos.entidades.Alumno;
import datos.entidades.Curso;
import datos.entidades.FichaPago;
import datos.entidades.Inscripcion;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import negocio.observer.IInscripcionObserver;
import negocio.observer.IObservable;
import negocio.servicios.InscripcionService;
import presentacion.PantallaFichaPagoFrm;
import presentacion.PantallaSeleccionCursosFrm;

/**
 *
 * @author jesus
 */
public class InscripcionController implements IObservable{
    private final InscripcionService service;
    private PantallaSeleccionCursosFrm vista;
    private final Inscripcion inscripcion;
    
    private final List<IInscripcionObserver> observadores = new ArrayList<>();
    
    public InscripcionController(InscripcionService service) {
        this.service = service;
        
        Alumno alumno = new Alumno("00000262722", "Jesus Ernesto Cisneros", "Ingeniería en Software");
        this.inscripcion = new Inscripcion("INS-" + UUID.randomUUID().toString().substring(0, 5), alumno);
    }
    
    public void setVista(PantallaSeleccionCursosFrm vista) {
        this.vista = vista;
        agregarObservador(vista);
    }
    
    public List<Curso> obtenerCursosDisponibles() {
        return service.obtenerCursosDisponibles(inscripcion.getAlumno().getMatricula());
    }
    
    public void seleccionarCurso(String idCurso) {
        service.agregarCurso(inscripcion, idCurso);
        notificarObservadores();
    }
    
    public void deseleccionarCurso(String idCurso) {
        service.eliminarCurso(inscripcion, idCurso);
        notificarObservadores();
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

    @Override
    public void agregarObservador(IInscripcionObserver observador) {
        if (observador != null && !observadores.contains(observador)) {
            observadores.add(observador);
        }
    }

    @Override
    public void eliminarObservador(IInscripcionObserver observador) {
        observadores.remove(observador);
    }

    @Override
    public void notificarObservadores() {
        for (IInscripcionObserver obs : observadores) {
            obs.actualizar(inscripcion);
        }
    }
}
