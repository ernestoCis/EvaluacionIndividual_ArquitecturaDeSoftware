/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos.entidades;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jesus
 */
public class Inscripcion {

    private String id;
    private LocalDateTime fecha;
    private Double total;
    private EstadoInscripcion estado;
    private Alumno alumno;
    private List<Curso> cursos;
    private FichaPago fichaPago;

    public Inscripcion() {
    }

    public Inscripcion(String id, Alumno alumno) {
        this.id = id;
        this.fecha = LocalDateTime.now();
        this.total = 0.0;
        this.estado = EstadoInscripcion.EN_PROCESO;
        this.alumno = alumno;
        this.cursos = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public EstadoInscripcion getEstado() {
        return estado;
    }

    public void setEstado(EstadoInscripcion estado) {
        this.estado = estado;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public FichaPago getFichaPago() {
        return fichaPago;
    }

    public void setFichaPago(FichaPago fichaPago) {
        this.fichaPago = fichaPago;
    }

    public void agregarCurso(Curso curso) {
        if (curso != null) {
            boolean yaExiste = cursos.stream().anyMatch(c -> c.getId().equalsIgnoreCase(curso.getId()));
            if (!yaExiste) {
                cursos.add(curso);
                calcularTotal();
            }
        }
    }

    public void eliminarCurso(Curso curso) {
        if (curso != null) {
            cursos.removeIf(c -> c.getId().equalsIgnoreCase(curso.getId()));
            calcularTotal();
        }
    }

    public Double calcularTotal() {
        double suma = 0.0;

        for (Curso curso : cursos) {
            suma += curso.getCosto();
        }

        this.total = suma;
        return this.total;
    }

    public List<Curso> obtenerCursos() {
        return cursos;
    }

    public void establecerEstado(EstadoInscripcion estado) {
        this.estado = estado;
    }
}
