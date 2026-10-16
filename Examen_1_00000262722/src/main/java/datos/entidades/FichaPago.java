/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos.entidades;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String fechaStr = (fechaEmision != null) ? fechaEmision.format(fmt) : "";

        String htmlContent = "<html><head><style>"
                + "body { font-family: Arial, sans-serif; margin: 40px; color: #0F1E41; }"
                + ".header { background-color: #002855; color: white; padding: 15px; text-align: center; font-size: 20px; font-weight: bold; }"
                + ".box { border: 1px solid #B4C8DC; padding: 20px; margin-top: 20px; border-radius: 8px; }"
                + ".total { background-color: #C3D7EE; padding: 10px; font-weight: bold; font-size: 16px; margin-top: 15px; text-align: right; }"
                + "</style></head><body>"
                + "<div class='header'>ITSON - Ficha de Inscripción</div>"
                + "<div class='box'>"
                + "<h2>Comprobante de Pago</h2>"
                + "<p><b>Folio de Pago:</b> " + folio + "</p>"
                + "<p><b>Fecha de Emisión:</b> " + fechaStr + "</p>"
                + "<div class='total'>Total a Pagar: $" + String.format("%.2f", total) + "</div>"
                + "</div>"
                + "<script>window.print();</script>"
                + // Abre la ventana de impresión/PDF automáticamente
                "</body></html>";

        try {
            File archivo = new File("Ficha_Pago_" + folio + ".html");
            try (FileWriter writer = new FileWriter(archivo)) {
                writer.write(htmlContent);
            }
            // Abrir en el navegador
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(archivo);
            }
        } catch (IOException e) {
            System.err.println("Error al generar la ficha: " + e.getMessage());
        }
    }
}
