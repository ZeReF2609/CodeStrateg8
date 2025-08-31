package com.algoritmoscurso.view;

import com.algoritmoscurso.interfaces.IView;
import com.algoritmoscurso.controller.ApplicationController;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.GradientPaint;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * Vista principal de la aplicación: contiene únicamente la carátula.
 */
public class MainView extends JFrame implements IView {
    private ApplicationController controller;
    private JPanel caratulaPanel;
    private ActividadesView actividadesView;
    private CardLayout cardLayout;
    private JPanel cardsContainer;

    public MainView() {
        initializeView();
    }

    public void setController(ApplicationController controller) {
        this.controller = controller;
    }

    @Override
    public void initializeView() {
        setupWindow();
        setupComponents();
        setupLayout();
    }

    private void setupWindow() {
        setTitle("Repositorio de Algoritmos - Análisis de Algoritmos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
    }

    private void setupComponents() {
    // carátula: panel con imagen y texto de grupo
    caratulaPanel = new JPanel(new BorderLayout());
    caratulaPanel.setBackground(Color.WHITE);

    // Modern two-column carátula: left image, right header + table
    JPanel content = new JPanel(new BorderLayout());
    content.setBackground(new Color(250, 250, 250));

    // Left: image panel with subtle gradient and centered rounded logo
    JPanel left = new JPanel(new GridBagLayout()) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gp = new GradientPaint(0, 0, new Color(245, 250, 255), 0, getHeight(), Color.WHITE);
            g2.setPaint(gp);
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();
        }
    };
    left.setBackground(Color.WHITE);
    left.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 12));
    // give left column a stable width so the logo can be larger without collapsing
    left.setPreferredSize(new Dimension(320, 0));

    // Try several locations for the logo in order, load into a BufferedImage
    // and present via a responsive component that scales with layout.
    BufferedImage logoImg = null;
    try {
        if (logoImg == null) {
            java.io.File f = new java.io.File("src/main/java/com/algoritmoscurso/assets/img/upn_icon.png");
            if (f.exists()) try { logoImg = ImageIO.read(f); } catch (Exception _e) { logoImg = null; }
        }
    } catch (Exception ex) {
        logoImg = null;
    }

    // responsive logo component (scales to available width)
    JComponent logoComp;
    if (logoImg != null) {
        logoComp = new ResponsiveImage(logoImg);
    } else {
        JPanel placeholder = new JPanel(new BorderLayout());
        placeholder.setPreferredSize(new Dimension(200, 200));
        placeholder.setOpaque(true);
        placeholder.setBackground(new Color(245, 245, 245));
        placeholder.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230,230,230)),
                BorderFactory.createEmptyBorder(8,8,8,8)));
        JLabel lab = new JLabel("Logo", SwingConstants.CENTER);
        lab.setForeground(new Color(140,140,140));
        placeholder.add(lab, BorderLayout.CENTER);
        logoComp = placeholder;
    }

    // Right: header + table
    JPanel right = new JPanel(new BorderLayout(0, 12));
    right.setBackground(Color.WHITE);
    right.setBorder(BorderFactory.createEmptyBorder(24, 12, 24, 24));

    JLabel header = new JLabel("Repositorio de Algoritmos");
    header.setFont(new Font("SansSerif", Font.BOLD, 28));
    header.setForeground(new Color(18, 76, 150));

    JLabel subtitle = new JLabel("Análisis y estructuras - Grupo 8");
    subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
    subtitle.setForeground(new Color(100, 100, 100));

    // header container with a subtle accent line
    JPanel hdr = new JPanel(new BorderLayout());
    hdr.setBackground(Color.WHITE);
    JPanel titles = new JPanel(new GridLayout(2,1));
    titles.setBackground(Color.WHITE);
    titles.add(header);
    titles.add(subtitle);
    hdr.add(titles, BorderLayout.CENTER);
    JPanel accent = new JPanel();
    accent.setBackground(new Color(20, 85, 160));
    accent.setPreferredSize(new Dimension(80, 6));
    JPanel accentWrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 8));
    accentWrap.setBackground(Color.WHITE);
    accentWrap.add(accent);
    hdr.add(accentWrap, BorderLayout.SOUTH);

    String[] cols = {"Apellido","Nombre", "Código", "Email"};
    Object[][] data = {
        {"Ramos Ynca", "Alvaro Omar", "N00397208", "N00397208@UPN.PE"},
        {"Rojas Marin", "Wilder Enrique", "N00487018", "N00487018@UPN.PE"},
        {"Carlos Castro", "Roger Percy", "N00386640", "N00386640@UPN.PE"},
        {"Cornejo Arcaya", "Luis Alberto", "N00255638", "N00255638@UPN.PE"}
    };

    javax.swing.table.DefaultTableModel tm = new javax.swing.table.DefaultTableModel(data, cols) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    JTable table = new JTable(tm);
    table.setFillsViewportHeight(true);
    table.setRowHeight(26);
    table.setShowGrid(false);
    table.setIntercellSpacing(new Dimension(0, 0));
    table.setBackground(new Color(250,250,250));
    table.setFont(new Font("SansSerif", Font.PLAIN, 13));
    // cleaner look: remove header border and make viewport transparent
    table.getTableHeader().setReorderingAllowed(false);
    table.getTableHeader().setOpaque(false);
    table.getTableHeader().setBackground(new Color(250,250,250));
    table.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
    table.setShowHorizontalLines(false);
    table.setShowVerticalLines(false);
    table.setRowSelectionAllowed(false);

    // Center-align code column
    javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
    table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

    JScrollPane tableScroll = new JScrollPane(table);
    tableScroll.setBorder(BorderFactory.createEmptyBorder());
    tableScroll.getViewport().setOpaque(false);

    right.add(hdr, BorderLayout.NORTH);
    right.add(tableScroll, BorderLayout.CENTER);

    // Compose content
    JPanel wrapper = new JPanel(new BorderLayout());
    wrapper.setBackground(Color.WHITE);
    wrapper.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230,230,230)),
            BorderFactory.createEmptyBorder(12,12,12,12)));
    // Only add the main content (right) so it fills the card; logo will be placed below the table
    wrapper.add(right, BorderLayout.CENTER);

    // bottom logo container
    JPanel bottomLogoWrap = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 12));
    bottomLogoWrap.setBackground(Color.WHITE);
    bottomLogoWrap.add(logoComp);
    right.add(bottomLogoWrap, BorderLayout.SOUTH);

    content.add(wrapper, BorderLayout.CENTER);
    caratulaPanel.add(content, BorderLayout.CENTER);

    // actividades view (embedded card)
    actividadesView = new ActividadesView();

    // cards container
    cardLayout = new CardLayout();
    cardsContainer = new JPanel(cardLayout);
    cardsContainer.add(caratulaPanel, "CARATULA");
    cardsContainer.add(actividadesView, "ACTIVIDADES");

        // Menú superior minimalista con acceso rápido
        JMenuBar menuBar = new JMenuBar();
    JMenuItem caratulaItem = new JMenuItem("Inicio");
    JMenuItem semanasItem = new JMenuItem("Semanas");
    // Replace plain "Salir" menu item with a compact power button
    JButton powerButton = new JButton("\u23FB"); // power symbol
    powerButton.setToolTipText("Apagar");
    powerButton.setFocusable(false);
    powerButton.setBorderPainted(false);
    powerButton.setContentAreaFilled(false);
    powerButton.setFont(new Font("Dialog", Font.PLAIN, 16));
    // Make menu items visually tappable and allow active-state styling
    List<JMenuItem> topItems = Arrays.asList(caratulaItem, semanasItem);
    Consumer<JMenuItem> setActive = (selected) -> {
        for (JMenuItem it : topItems) {
            it.setOpaque(true);
            it.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
            if (it == selected) {
                it.setBackground(new Color(18, 76, 150));
                it.setForeground(Color.WHITE);
            } else {
                it.setBackground(menuBar.getBackground());
                it.setForeground(new Color(60, 60, 60));
            }
        }
    };
    menuBar.add(caratulaItem);
    menuBar.add(semanasItem);
    // default active
    setActive.accept(caratulaItem);
    menuBar.add(Box.createHorizontalGlue());
    menuBar.add(powerButton);
        setJMenuBar(menuBar);

        // Acciones del menú
        caratulaItem.addActionListener((ActionEvent e) -> {
            e.getActionCommand();
            showCover();
            setActive.accept(caratulaItem);
        });
        semanasItem.addActionListener((ActionEvent e) -> {
            e.getActionCommand();
            if (controller != null) controller.openActividadesWindow();
            setActive.accept(semanasItem);
        });
        powerButton.addActionListener((ActionEvent e) -> {
            e.getActionCommand();
            Window w = SwingUtilities.getWindowAncestor(MainView.this);
            if (w != null) w.dispose();
            System.exit(0);
        });
    }

    private void setupLayout() {
    setLayout(new BorderLayout());
    add(cardsContainer, BorderLayout.CENTER);
    }

    @Override
    public void updateView(Object data) {
        repaint();
    }

    @Override
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void showError(String error) {
        JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
    caratulaPanel.setEnabled(enabled);
    actividadesView.setEnabled(enabled);
    }

    // Mostrar la carátula
    public void showCover() {
        cardLayout.show(cardsContainer, "CARATULA");
    }

    // Mostrar actividades dentro de la misma ventana
    public void showActivities() {
        cardLayout.show(cardsContainer, "ACTIVIDADES");
    }

    // Exponer la vista de actividades para que el controlador la pueble
    public ActividadesView getActividadesView() {
        return actividadesView;
    }

    private static class ResponsiveImage extends JComponent {
        private final BufferedImage img;
        private final float arc = 20f;

        ResponsiveImage(BufferedImage img) {
            this.img = img;
            setOpaque(false);
            setPreferredSize(new Dimension(300, 200));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (img == null) return;
            int w = getWidth();
            int h = getHeight();

            double imgW = img.getWidth();
            double imgH = img.getHeight();
            double scale = Math.min((double) w / imgW, (double) h / imgH);
            int dw = (int) Math.round(imgW * scale);
            int dh = (int) Math.round(imgH * scale);
            int x = (w - dw) / 2;
            int y = (h - dh) / 2;

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Shape clip = new RoundRectangle2D.Float(x, y, dw, dh, arc, arc);
            g2.setClip(clip);
            g2.drawImage(img, x, y, dw, dh, null);
            g2.setClip(null);

            // soft border
            g2.setStroke(new BasicStroke(2f));
            g2.setColor(new Color(230,230,230,180));
            g2.draw(clip);
            g2.dispose();
        }
    }
}
