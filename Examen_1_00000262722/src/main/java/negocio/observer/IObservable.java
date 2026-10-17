/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio.observer;

/**
 *
 * @author jesus
 */
public interface IObservable {
    void agregarObservador(IInscripcionObserver observador);
    void eliminarObservador(IInscripcionObserver observador);
    void notificarObservadores();
}
