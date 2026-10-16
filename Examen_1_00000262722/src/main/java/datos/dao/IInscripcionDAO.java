/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos.dao;

import datos.entidades.Inscripcion;
import java.util.List;

/**
 *
 * @author jesus
 */
public interface IInscripcionDAO {
    Boolean guardar(Inscripcion inscripcion);
    Inscripcion obtenerPorMatricula(String matricula);
    List<Inscripcion> obtenerTodasPorMatricula(String matricula);
}
