package negocio.servicios;

import datos.dao.ICursoDAO;
import datos.dao.IInscripcionDAO;
import datos.entidades.Curso;
import datos.entidades.EstadoInscripcion;
import datos.entidades.FichaPago;
import datos.entidades.Inscripcion;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author jesus
 */
public class InscripcionService {

    private final ICursoDAO cursoDAO;
    private final IInscripcionDAO inscripcionDAO;

    public InscripcionService(ICursoDAO cursoDAO, IInscripcionDAO inscripcionDAO) {
        this.cursoDAO = cursoDAO;
        this.inscripcionDAO = inscripcionDAO;
    }

    /**
     * Consulta el catálogo general de materias disponibles a través del DAO
     */
    public List<Curso> obtenerCatalogo() {
        return cursoDAO.obtenerTodos();
    }

    /**
     * Consulta el catálogo de materias excluyendo las que el alumno ya
     * inscribió previamente.
     */
    public List<Curso> obtenerCursosDisponibles(String matricula) {
        List<Curso> todosLosCursos = cursoDAO.obtenerTodos();
        List<Inscripcion> inscripcionesAnteriores = inscripcionDAO.obtenerTodasPorMatricula(matricula);

        // Extraer los IDs de todos los cursos previamente confirmados
        List<String> idsInscritos = new ArrayList<>();
        if (inscripcionesAnteriores != null) {
            for (Inscripcion insc : inscripcionesAnteriores) {
                if (insc.getEstado() == EstadoInscripcion.CONFIRMADA && insc.obtenerCursos() != null) {
                    for (Curso c : insc.obtenerCursos()) {
                        idsInscritos.add(c.getId());
                    }
                }
            }
        }

        // Retornar solo las materias que NO han sido inscritas aún
        List<Curso> disponibles = new ArrayList<>();
        for (Curso c : todosLosCursos) {
            if (!idsInscritos.contains(c.getId())) {
                disponibles.add(c);
            }
        }

        return disponibles;
    }

    /**
     * Agrega el curso especificado a la inscripción enviada por parámetro
     */
    public Inscripcion agregarCurso(Inscripcion inscripcion, String idCurso) {
        Curso curso = cursoDAO.obtenerPorId(idCurso);

        System.out.println("ID recibido por el servicio: " + idCurso);
        System.out.println("Curso encontrado por DAO: "
                + (curso != null ? curso.getNombre() : "No encontrado"));

        if (curso != null && inscripcion != null) {
            inscripcion.agregarCurso(curso);
        }
        return inscripcion;
    }

    /**
     * Remueve el curso especificado de la inscripción enviada por parámetro
     */
    public Inscripcion eliminarCurso(Inscripcion inscripcion, String idCurso) {
        Curso curso = cursoDAO.obtenerPorId(idCurso);
        if (curso != null && inscripcion != null) {
            inscripcion.eliminarCurso(curso);
        }
        return inscripcion;
    }

    /**
     * Finaliza la transacción: cambia el estado, genera la ficha de pago y
     * persiste la inscripción
     */
    public FichaPago finalizarInscripcion(Inscripcion inscripcion) {
        if (inscripcion == null) {
            return null;
        }

        inscripcion.establecerEstado(EstadoInscripcion.CONFIRMADA);

        String matricula = (inscripcion.getAlumno() != null) ? inscripcion.getAlumno().getMatricula() : "00000000000";

        String folio = generarFolio(matricula);

        FichaPago ficha = new FichaPago(folio, LocalDateTime.now(), inscripcion.getTotal());
        inscripcion.setFichaPago(ficha);

        inscripcionDAO.guardar(inscripcion);

        return ficha;
    }

    /**
     * Genera el folio único de pago con la estructura:
     * [matricula]-[milisegundos]-ITS
     */
    public String generarFolio(String matricula) {
        long milisegundos = System.currentTimeMillis();
        return matricula + "-" + milisegundos + "-ITS";
    }
}
