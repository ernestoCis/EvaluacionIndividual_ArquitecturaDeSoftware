package presentacion;

import datos.entidades.Alumno;
import datos.entidades.Curso;
import datos.entidades.FichaPago;
import datos.entidades.Inscripcion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 
 * @author jesus
 */
public class PantallaFichaPagoFrm extends JFrame {

    private final NumberFormat monedaFormat = NumberFormat.getCurrencyInstance(Locale.US);
    private final DateTimeFormatter fechaFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy   hh:mm a");

    private JLabel lblNombreVal;
    private JLabel lblMatriculaVal;
    private JLabel lblCarreraVal;
    private JLabel lblFechaVal;
    private JLabel lblFolioVal;
    
    private JTable tablaCursos;
    private DefaultTableModel modelCursos;
    private JLabel lblTotalMonto;
    private FichaPago fichaActual;

    public PantallaFichaPagoFrm() {
        this(null);
    }

    public PantallaFichaPagoFrm(FichaPago ficha) {
        this.fichaActual = ficha;
        initComponents();
        this.setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Ficha de Pago");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 720);
        setLayout(new BorderLayout());

        Color azulOscuroHeader = new Color(0, 40, 85);
        Color azulBoton = new Color(0, 32, 96);
        Color azulTotalBg = new Color(195, 215, 238);
        Color textoOscuro = new Color(15, 30, 65);
        Color textoGris = new Color(80, 95, 120);
        Color bordeCard = new Color(180, 200, 220);

        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(azulOscuroHeader);
        panelHeader.setPreferredSize(new Dimension(1000, 50));
        panelHeader.setBorder(new EmptyBorder(0, 25, 0, 25));

        JLabel lblLogo = new JLabel("ITSON");
        lblLogo.setFont(new Font("Arial", Font.BOLD, 22));
        lblLogo.setForeground(Color.WHITE);

        JLabel lblUsuario = new JLabel("Jesus Cisneros");
        lblUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        lblUsuario.setForeground(Color.WHITE);

        panelHeader.add(lblLogo, BorderLayout.WEST);
        panelHeader.add(lblUsuario, BorderLayout.EAST);
        add(panelHeader, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBackground(Color.WHITE);
        panelCentral.setBorder(new EmptyBorder(20, 35, 20, 35));

        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        panelTitulo.setBackground(Color.WHITE);

        JLabel lblIconoCheck = new JLabel("√", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(azulBoton);
                g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
                super.paintComponent(g);
            }
        };
        lblIconoCheck.setPreferredSize(new Dimension(36, 36));
        lblIconoCheck.setFont(new Font("Arial", Font.BOLD, 20));
        lblIconoCheck.setForeground(Color.WHITE);

        JPanel panelTextosTitulo = new JPanel();
        panelTextosTitulo.setLayout(new BoxLayout(panelTextosTitulo, BoxLayout.Y_AXIS));
        panelTextosTitulo.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Ficha de Inscripción");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(textoOscuro);

        JLabel lblSubtitulo = new JLabel("Tu inscripción ha sido registrada correctamente. Aquí tienes un resumen de tu carga académica y el monto total a pagar");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 13));
        lblSubtitulo.setForeground(textoGris);

        panelTextosTitulo.add(lblTitulo);
        panelTextosTitulo.add(Box.createVerticalStrut(3));
        panelTextosTitulo.add(lblSubtitulo);

        panelTitulo.add(lblIconoCheck);
        panelTitulo.add(panelTextosTitulo);
        panelCentral.add(panelTitulo);
        panelCentral.add(Box.createVerticalStrut(15));

        JPanel cardEstudiante = createCardPanel(bordeCard);
        JLabel lblTituloEst = new JLabel("Datos del Estudiante");
        lblTituloEst.setFont(new Font("Arial", Font.BOLD, 16));
        lblTituloEst.setForeground(textoOscuro);
        lblTituloEst.setBorder(new EmptyBorder(0, 0, 10, 0));

        JPanel panelInfoGrid = new JPanel(new GridLayout(1, 2, 20, 0));
        panelInfoGrid.setBackground(Color.WHITE);

        JPanel panelIzq = new JPanel(new GridBagLayout());
        panelIzq.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(4, 0, 4, 15);

        addLabelPair(panelIzq, gbc, 0, "Nombre:", lblNombreVal = new JLabel("-"));
        addLabelPair(panelIzq, gbc, 1, "Matrícula:", lblMatriculaVal = new JLabel("-"));
        addLabelPair(panelIzq, gbc, 2, "Carrera:", lblCarreraVal = new JLabel("-"));

        JSeparator sepVertical = new JSeparator(JSeparator.VERTICAL);
        sepVertical.setForeground(new Color(200, 210, 225));

        // Columna Derecha (Fecha, Folio)
        JPanel panelDer = new JPanel(new GridBagLayout());
        panelDer.setBackground(Color.WHITE);
        GridBagConstraints gbcD = new GridBagConstraints();
        gbcD.anchor = GridBagConstraints.WEST;
        gbcD.insets = new Insets(4, 15, 4, 0);

        addLabelPairVertical(panelDer, gbcD, 0, "Fecha de Inscripción:", lblFechaVal = new JLabel("-"));
        addLabelPairVertical(panelDer, gbcD, 2, "Folio de pago:", lblFolioVal = new JLabel("-"));

        JPanel panelDatosSplit = new JPanel(new BorderLayout());
        panelDatosSplit.setBackground(Color.WHITE);
        panelDatosSplit.add(panelIzq, BorderLayout.WEST);
        panelDatosSplit.add(sepVertical, BorderLayout.CENTER);
        panelDatosSplit.add(panelDer, BorderLayout.EAST);

        cardEstudiante.add(lblTituloEst, BorderLayout.NORTH);
        cardEstudiante.add(panelDatosSplit, BorderLayout.CENTER);

        panelCentral.add(cardEstudiante);
        panelCentral.add(Box.createVerticalStrut(15));

        JPanel cardCursos = createCardPanel(bordeCard);
        JLabel lblTituloCursos = new JLabel("Cursos Inscritos");
        lblTituloCursos.setFont(new Font("Arial", Font.BOLD, 16));
        lblTituloCursos.setForeground(textoOscuro);
        lblTituloCursos.setBorder(new EmptyBorder(0, 0, 10, 0));

        String[] columnas = {"#", "Curso", "Créditos", "Costo"};
        modelCursos = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaCursos = new JTable(modelCursos);
        estilarTabla(tablaCursos);

        JScrollPane scrollTabla = new JScrollPane(tablaCursos);
        scrollTabla.setPreferredSize(new Dimension(850, 130));
        scrollTabla.setBorder(new LineBorder(bordeCard, 1, true));
        scrollTabla.getViewport().setBackground(Color.WHITE);

        JPanel panelTotalBanner = new JPanel(new BorderLayout());
        panelTotalBanner.setBackground(azulTotalBg);
        panelTotalBanner.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(160, 185, 215), 1, true),
            new EmptyBorder(12, 20, 12, 20)
        ));

        JLabel lblTotalTexto = new JLabel("Total a Pagar");
        lblTotalTexto.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotalTexto.setForeground(textoOscuro);

        lblTotalMonto = new JLabel("$0.00");
        lblTotalMonto.setFont(new Font("Arial", Font.BOLD, 18));
        lblTotalMonto.setForeground(textoOscuro);

        panelTotalBanner.add(lblTotalTexto, BorderLayout.WEST);
        panelTotalBanner.add(lblTotalMonto, BorderLayout.EAST);

        JPanel panelCentroCard2 = new JPanel(new BorderLayout(0, 15));
        panelCentroCard2.setBackground(Color.WHITE);
        panelCentroCard2.add(scrollTabla, BorderLayout.CENTER);
        panelCentroCard2.add(panelTotalBanner, BorderLayout.SOUTH);

        cardCursos.add(lblTituloCursos, BorderLayout.NORTH);
        cardCursos.add(panelCentroCard2, BorderLayout.CENTER);

        panelCentral.add(cardCursos);
        panelCentral.add(Box.createVerticalStrut(20));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panelBotones.setBackground(Color.WHITE);

        JButton btnPDF = new JButton("Descargar PDF");
        btnPDF.setFont(new Font("Arial", Font.BOLD, 14));
        btnPDF.setBackground(azulBoton);
        btnPDF.setForeground(Color.WHITE);
        btnPDF.setFocusPainted(false);
        btnPDF.setPreferredSize(new Dimension(210, 42));
        btnPDF.addActionListener(e -> descargarPDF());

        JButton btnInicio = new JButton("Volver al Inicio");
        btnInicio.setFont(new Font("Arial", Font.BOLD, 14));
        btnInicio.setBackground(Color.WHITE);
        btnInicio.setForeground(textoOscuro);
        btnInicio.setBorder(new LineBorder(bordeCard, 1, true));
        btnInicio.setFocusPainted(false);
        btnInicio.setPreferredSize(new Dimension(210, 42));
        btnInicio.addActionListener(e -> volverAlInicio());

        panelBotones.add(btnPDF);
        panelBotones.add(btnInicio);

        panelCentral.add(panelBotones);

        JScrollPane scrollGeneral = new JScrollPane(panelCentral);
        scrollGeneral.setBorder(null);
        add(scrollGeneral, BorderLayout.CENTER);
    }


    public void mostrarFicha(FichaPago ficha) {
        this.fichaActual = ficha;
        if (ficha != null) {
            lblFolioVal.setText(ficha.getFolio() != null ? ficha.getFolio() : "-");
            lblFechaVal.setText(ficha.getFechaEmision() != null ? ficha.getFechaEmision().format(fechaFormat) : "-");
            lblTotalMonto.setText(monedaFormat.format(ficha.getTotal()));
        }
    }

    public void cargarDatosCompletos(Alumno alumno, FichaPago ficha, List<Curso> cursos) {
        if (alumno != null) {
            lblNombreVal.setText(alumno.getNombre());
            lblMatriculaVal.setText(alumno.getMatricula());
            lblCarreraVal.setText(alumno.getCarrera());
        }
        mostrarFicha(ficha);

        modelCursos.setRowCount(0);
        if (cursos != null) {
            int num = 1;
            for (Curso c : cursos) {
                modelCursos.addRow(new Object[]{
                    num++,
                    c.getNombre(),
                    c.getCreditos(),
                    monedaFormat.format(c.getCosto())
                });
            }
        }
    }

    private void descargarPDF() {
        if (fichaActual != null) {
            fichaActual.generarPDF();
        }
        JOptionPane.showMessageDialog(this, "La ficha de pago ha sido descargada en PDF exitosamente.", "Descarga Completada", JOptionPane.INFORMATION_MESSAGE);
    }

    private void volverAlInicio() {
        this.dispose();
    }


    private JPanel createCardPanel(Color borderCol) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(borderCol, 1, true),
            new EmptyBorder(15, 20, 15, 20)
        ));
        return panel;
    }

    private void addLabelPair(JPanel panel, GridBagConstraints gbc, int row, String title, JLabel valLabel) {
        gbc.gridy = row;
        gbc.gridx = 0;
        JLabel lblT = new JLabel(title);
        lblT.setFont(new Font("Arial", Font.PLAIN, 13));
        lblT.setForeground(new Color(80, 95, 120));
        panel.add(lblT, gbc);

        gbc.gridx = 1;
        valLabel.setFont(new Font("Arial", Font.BOLD, 13));
        valLabel.setForeground(new Color(15, 30, 65));
        panel.add(valLabel, gbc);
    }

    private void addLabelPairVertical(JPanel panel, GridBagConstraints gbc, int row, String title, JLabel valLabel) {
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel lblT = new JLabel(title);
        lblT.setFont(new Font("Arial", Font.PLAIN, 12));
        lblT.setForeground(new Color(120, 130, 145));
        panel.add(lblT, gbc);

        gbc.gridy = row + 1;
        valLabel.setFont(new Font("Arial", Font.BOLD, 13));
        valLabel.setForeground(new Color(15, 30, 65));
        panel.add(valLabel, gbc);
    }

    private void estilarTabla(JTable tabla) {
        tabla.setRowHeight(35);
        tabla.setFont(new Font("Arial", Font.PLAIN, 13));
        tabla.setShowGrid(true);
        tabla.setGridColor(new Color(220, 225, 235));

        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(Color.WHITE);
        tabla.getTableHeader().setForeground(new Color(15, 30, 65));
        tabla.getTableHeader().setPreferredSize(new Dimension(0, 32));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);

        tabla.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabla.getColumnModel().getColumn(0).setCellRenderer(center);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(400);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(2).setCellRenderer(center);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(120);
        tabla.getColumnModel().getColumn(3).setCellRenderer(center);
    }
}
