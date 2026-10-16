/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos.mock;

import datos.dao.ICursoDAO;
import datos.entidades.Curso;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jesus
 */
public class CursoDAOMock implements ICursoDAO{
    private final List<Curso> catalogo;
    
    public CursoDAOMock() {
        catalogo = new ArrayList<>();
        catalogo.add(new Curso("CS101", "Arquitectura de Software", 4, 1800.00));
        catalogo.add(new Curso("CS102", "Diseño de Software", 4, 1800.00));
        catalogo.add(new Curso("CS103", "Programación 3", 4, 1900.00));
        catalogo.add(new Curso("CS104", "Análisis de Algoritmos", 4, 1800.00));
        catalogo.add(new Curso("CS104", "Ingeniería de Requisitos", 3, 1600.00));
        catalogo.add(new Curso("CS104", "Aplicaciones Web", 3, 1600.00));
        catalogo.add(new Curso("CS104", "Administración de Proyectos", 2, 1200.00));
    }

    @Override
    public List<Curso> obtenerTodos() {
        return catalogo;
    }

    @Override
    public Curso obtenerPorId(String id) {
        return catalogo.stream()
                .filter(c -> c.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }
    
}
