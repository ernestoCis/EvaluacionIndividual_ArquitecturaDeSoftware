/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos.entidades;

import java.time.LocalDateTime;

/**
 *
 * @author jesus
 */
public class FichaPago {
    private String folio;
    private LocalDateTime fechaEmision;
    private Double total;

    public FichaPago() {
    }

    public FichaPago(String folio, LocalDateTime fechaEmision, Double total) {
        this.folio = folio;
        this.fechaEmision = fechaEmision;
        this.total = total;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public void generarPDF() {
        System.out.println("[PDF] Generando comprobante de pago con Folio: " + folio);
    }
}
