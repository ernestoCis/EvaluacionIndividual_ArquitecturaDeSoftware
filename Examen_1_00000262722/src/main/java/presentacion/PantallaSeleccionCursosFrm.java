package presentacion;

import datos.entidades.Curso;
import negocio.controladores.InscripcionController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 
 * @author jesus
 */
public class PantallaSeleccionCursosFrm extends JFrame {

    private final InscripcionController controller;
    private final NumberFormat monedaFormat = NumberFormat.getCurrencyInstance(Locale.US);

    private JTable tablaDisponibles;
    private DefaultTableModel modelDisponibles;
    private JTable tablaInscritos;
    private DefaultTableModel modelInscritos;
    
    private JPanel panelInscritosVacio;
    private JScrollPane scrollInscritos;
    private JLabel lblTotalMonto;
    private JButton btnFinalizar;

    private List<Curso> listaCatalogo = new ArrayList<>();
    private List<Curso> listaInscritos = new ArrayList<>();

    public PantallaSeleccionCursosFrm(InscripcionController controller) {
        this.controller = controller;
        initComponents();
        this.setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Seleccion de Cursos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLayout(new BorderLayout());

        Color azulOscuroHeader = new Color(0, 40, 85);
        Color azulBoton = new Color(0, 32, 96);
        Color textoGris = new Color(80, 95, 120);
        Color grisFondoPanel = new Color(238, 238, 238);
        Color bordeCard = new Color(180, 200, 220);

        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(azulOscuroHeader);
        panelHeader.setPreferredSize(new Dimension(1100, 50));
        panelHeader.setBorder(new EmptyBorder(0, 20, 0, 20));

        JLabel lblLogo = new JLabel("ITSON");
        lblLogo.setFont(new Font("Arial", Font.BOLD, 22));
        lblLogo.setForeground(Color.WHITE);

        JLabel lblUsuario = new JLabel("Jesus Cisneros");
        lblUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        lblUsuario.setForeground(Color.WHITE);

        panelHeader.add(lblLogo, BorderLayout.WEST);
        panelHeader.add(lblUsuario, BorderLayout.EAST);
        add(panelHeader, BorderLayout.NORTH);

        JPanel panelContenido = new JPanel(new BorderLayout(0, 15));
        panelContenido.setBackground(Color.WHITE);
        panelContenido.setBorder(new EmptyBorder(25, 35, 25, 35));

        // Título y Subtítulo
        JPanel panelTitulos = new JPanel();
        panelTitulos.setLayout(new BoxLayout(panelTitulos, BoxLayout.Y_AXIS));
        panelTitulos.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Inscripción de Clases");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(15, 30, 65));

        JLabel lblSubtitulo = new JLabel("Selecciona los cursos que deseas inscribir. Al hacer clic en un curso, se agregará a tu lista de inscritos.");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitulo.setForeground(textoGris);

        panelTitulos.add(lblTitulo);
        panelTitulos.add(Box.createVerticalStrut(5));
        panelTitulos.add(lblSubtitulo);
        panelContenido.add(panelTitulos, BorderLayout.NORTH);

        // Panel de 2 Columnas (Disponibles e Inscritos)
        JPanel panelColumnas = new JPanel(new GridLayout(1, 2, 25, 0));
        panelColumnas.setBackground(Color.WHITE);

        // ------------------ CURSOS DISPONIBLES ------------------
        JPanel cardDisponibles = createCardPanel(bordeCard);
        JLabel lblDisponibles = new JLabel("Cursos Disponibles");
        lblDisponibles.setFont(new Font("Arial", Font.BOLD, 18));
        lblDisponibles.setForeground(new Color(15, 30, 65));
        lblDisponibles.setBorder(new EmptyBorder(0, 0, 15, 0));

        String[] colsDisponibles = {"Curso", "Créditos", "Costo", "Acción"};
        modelDisponibles = new DefaultTableModel(colsDisponibles, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };

        tablaDisponibles = new JTable(modelDisponibles);
        estilarTabla(tablaDisponibles);

        tablaDisponibles.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer("Agregar", azulBoton, Color.WHITE));
        tablaDisponibles.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox(), "Agregar", (row) -> {
            if (row >= 0 && row < listaCatalogo.size()) {
                Curso seleccionado = listaCatalogo.get(row);
                controller.seleccionarCurso(seleccionado.getId());
            }
        }));

        JScrollPane scrollDisponibles = new JScrollPane(tablaDisponibles);
        scrollDisponibles.setBorder(BorderFactory.createEmptyBorder());
        scrollDisponibles.getViewport().setBackground(Color.WHITE);

        cardDisponibles.add(lblDisponibles, BorderLayout.NORTH);
        cardDisponibles.add(scrollDisponibles, BorderLayout.CENTER);

        // ------------------ CURSOS INSCRITOS ------------------
        JPanel cardInscritos = createCardPanel(bordeCard);
        JLabel lblInscritos = new JLabel("Cursos Inscritos");
        lblInscritos.setFont(new Font("Arial", Font.BOLD, 18));
        lblInscritos.setForeground(new Color(15, 30, 65));
        lblInscritos.setBorder(new EmptyBorder(0, 0, 15, 0));

        // Panel para estado VACÍO
        panelInscritosVacio = new JPanel(new GridBagLayout());
        panelInscritosVacio.setBackground(Color.WHITE);
        JLabel lblVacio = new JLabel("Aún no has inscrito ningún curso");
        lblVacio.setFont(new Font("Arial", Font.BOLD, 16));
        lblVacio.setForeground(new Color(15, 30, 65));
        panelInscritosVacio.add(lblVacio);

        // Tabla para cuando SÍ hay cursos inscritos
        String[] colsInscritos = {"Curso", "Créditos", "Costo", "Acción"};
        modelInscritos = new DefaultTableModel(colsInscritos, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };
        tablaInscritos = new JTable(modelInscritos);
        estilarTabla(tablaInscritos);

        tablaInscritos.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer("Eliminar", new Color(180, 40, 40), Color.WHITE));
        tablaInscritos.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox(), "Eliminar", (row) -> {
            if (row >= 0 && row < listaInscritos.size()) {
                Curso aEliminar = listaInscritos.get(row);
                controller.deseleccionarCurso(aEliminar.getId());
            }
        }));

        scrollInscritos = new JScrollPane(tablaInscritos);
        scrollInscritos.setBorder(BorderFactory.createEmptyBorder());
        scrollInscritos.getViewport().setBackground(Color.WHITE);
        scrollInscritos.setVisible(false);

        // Panel inferior de TOTAL Y FINALIZAR
        JPanel panelInferior = new JPanel(new BorderLayout(0, 10));
        panelInferior.setBackground(grisFondoPanel);
        panelInferior.setBorder(new EmptyBorder(15, 20, 15, 20));

        JPanel panelMonto = new JPanel(new BorderLayout());
        panelMonto.setBackground(grisFondoPanel);

        JLabel lblTotalTexto = new JLabel("Total a pagar");
        lblTotalTexto.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotalTexto.setForeground(new Color(15, 30, 65));

        lblTotalMonto = new JLabel("$0.00");
        lblTotalMonto.setFont(new Font("Arial", Font.BOLD, 18));
        lblTotalMonto.setForeground(new Color(15, 30, 65));

        panelMonto.add(lblTotalTexto, BorderLayout.WEST);
        panelMonto.add(lblTotalMonto, BorderLayout.EAST);

        btnFinalizar = new JButton("Finalizar Inscripción");
        btnFinalizar.setFont(new Font("Arial", Font.BOLD, 14));
        btnFinalizar.setBackground(new Color(130, 140, 150)); // Inhabilitado por defecto (gris)
        btnFinalizar.setForeground(Color.WHITE);
        btnFinalizar.setFocusPainted(false);
        btnFinalizar.setPreferredSize(new Dimension(200, 38));
        btnFinalizar.setEnabled(false);
        btnFinalizar.addActionListener(e -> onFinalizarClicked());

        JPanel panelBtnContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBtnContainer.setBackground(grisFondoPanel);
        panelBtnContainer.add(btnFinalizar);

        panelInferior.add(panelMonto, BorderLayout.NORTH);
        panelInferior.add(panelBtnContainer, BorderLayout.SOUTH);

        JPanel panelCentroInscritos = new JPanel(new CardLayout());
        panelCentroInscritos.setBackground(Color.WHITE);
        panelCentroInscritos.add(panelInscritosVacio, "VACIO");
        panelCentroInscritos.add(scrollInscritos, "TABLA");

        cardInscritos.add(lblInscritos, BorderLayout.NORTH);
        cardInscritos.add(panelCentroInscritos, BorderLayout.CENTER);
        cardInscritos.add(panelInferior, BorderLayout.SOUTH);

        panelColumnas.add(cardDisponibles);
        panelColumnas.add(cardInscritos);

        panelContenido.add(panelColumnas, BorderLayout.CENTER);
        add(panelContenido, BorderLayout.CENTER);
    }

    // ==================== MÉTODOS DE DISEÑO ====================

    public void mostrarCatalogo(List<Curso> cursos) {
        this.listaCatalogo = cursos;
        modelDisponibles.setRowCount(0);
        for (Curso c : cursos) {
            modelDisponibles.addRow(new Object[]{
                c.getNombre(),
                c.getCreditos(),
                monedaFormat.format(c.getCosto()),
                "Agregar"
            });
        }
    }

    public void actualizarResumen(List<Curso> inscritos, Double total) {
        this.listaInscritos = inscritos;
        
        // Actualizar Tabla de Inscritos
        modelInscritos.setRowCount(0);
        for (Curso c : inscritos) {
            modelInscritos.addRow(new Object[]{
                c.getNombre(),
                c.getCreditos(),
                monedaFormat.format(c.getCosto()),
                "Eliminar"
            });
        }

        // Alternar entre mensaje de vacío y lista
        CardLayout cl = (CardLayout) scrollInscritos.getParent().getLayout();
        if (inscritos.isEmpty()) {
            cl.show(scrollInscritos.getParent(), "VACIO");
            btnFinalizar.setEnabled(false);
            btnFinalizar.setBackground(new Color(130, 140, 150));
        } else {
            cl.show(scrollInscritos.getParent(), "TABLA");
            btnFinalizar.setEnabled(true);
            btnFinalizar.setBackground(new Color(0, 32, 96));
        }

        // Actualizar monto total
        lblTotalMonto.setText(monedaFormat.format(total));
    }

    private void onFinalizarClicked() {
        if (controller != null) {
            controller.confirmarInscripcion("2024001"); // Matrícula de prueba del estudiante
        }
    }


    private JPanel createCardPanel(Color borderCol) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(borderCol, 1, true),
            new EmptyBorder(15, 15, 15, 15)
        ));
        return panel;
    }

    private void estilarTabla(JTable tabla) {
        tabla.setRowHeight(38);
        tabla.setFont(new Font("Arial", Font.PLAIN, 13));
        tabla.setShowGrid(false);
        tabla.setIntercellSpacing(new Dimension(0, 0));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(new Color(235, 235, 235));
        tabla.getTableHeader().setForeground(new Color(15, 30, 65));
        tabla.getTableHeader().setPreferredSize(new Dimension(0, 30));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tabla.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tabla.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
    }

    // Renderizador y Editor de Botones dentro de la Tabla
    private static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer(String text, Color bg, Color fg) {
            setText(text);
            setBackground(bg);
            setForeground(fg);
            setFont(new Font("Arial", Font.BOLD, 11));
            setFocusPainted(false);
            setBorderPainted(false);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    private static class ButtonEditor extends DefaultCellEditor {
        private final JButton button;
        private final java.util.function.Consumer<Integer> action;
        private int currentRow;

        public ButtonEditor(JCheckBox checkBox, String text, java.util.function.Consumer<Integer> action) {
            super(checkBox);
            this.action = action;
            button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 11));
            button.setBackground(new Color(0, 32, 96));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.addActionListener(e -> {
                fireEditingStopped();
                action.accept(currentRow);
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.currentRow = row;
            return button;
        }
    }
}

