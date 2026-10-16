/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos.mock;

import datos.dao.IInscripcionDAO;
import datos.entidades.Inscripcion;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jesus
 */
public class InscripcionDAOMock implements IInscripcionDAO{
    private final List<Inscripcion> bdSimulada = new ArrayList<>();

    @Override
    public Boolean guardar(Inscripcion inscripcion) {
        bdSimulada.add(inscripcion);
        System.out.println("[Mock] Inscripción guardada con ID: " + inscripcion.getId());
        return true;
    }

    @Override
    public Inscripcion obtenerPorMatricula(String matricula) {
        return bdSimulada.stream()
                .filter(i -> i.getAlumno() != null && i.getAlumno().getMatricula().equalsIgnoreCase(matricula))
                .findFirst()
                .orElse(null);
    }
    
    
}
