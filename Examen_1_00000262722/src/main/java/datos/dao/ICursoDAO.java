/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package datos.dao;

import datos.entidades.Curso;
import java.util.List;

/**
 *
 * @author jesus
 */
public interface ICursoDAO {
    List<Curso> obtenerTodos();
    Curso obtenerPorId(String id);
}
