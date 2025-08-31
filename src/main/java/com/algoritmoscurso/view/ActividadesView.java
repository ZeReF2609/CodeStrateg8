package com.algoritmoscurso.view;

import com.algoritmoscurso.interfaces.IView;
import javax.swing.*;
import java.awt.*;
import javax.swing.tree.*;
import javax.swing.event.TreeSelectionListener;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Vista que contiene las actividades (sidebar, árbol de navegación y pestañas de contenido)
 */
public class ActividadesView extends JPanel implements IView {
    private JPanel contentPanel;
    private JPanel sidebarPanel; // Panel lateral para navegación de semanas/actividades
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JTextArea descriptionArea;
    private JTabbedPane contentTabbedPane;
    private JTree navigationTree; // Árbol de navegación desplegable
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode rootNode;
    // Evitar pestañas duplicadas: id -> component
    private Map<String, Component> openTabs = new HashMap<>();

    // Holder para mostrar título en el árbol pero usar id internamente
    private static class TreeItem {
        final String id;
        final String title;
        TreeItem(String id, String title) { this.id = id; this.title = title; }
        @Override public String toString() { return title; }
        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TreeItem)) return false;
            TreeItem t = (TreeItem) o;
            return id != null ? id.equals(t.id) : t.id == null;
        }
        @Override public int hashCode() { return id != null ? id.hashCode() : 0; }
    }

    public ActividadesView() {
        initializeView();
    }

    public void initializeView() {
        setupComponents();
        setupLayout();
    }

    private void setupComponents() {
        // Panel principal
        contentPanel = new JPanel(new BorderLayout());

        // Sidebar para navegación entre semanas y actividades
    sidebarPanel = new JPanel(new BorderLayout());
    sidebarPanel.setBackground(new Color(250, 250, 250));
    sidebarPanel.setPreferredSize(new Dimension(220, 0));
    sidebarPanel.setBorder(BorderFactory.createEmptyBorder(8, 6, 8, 6));

        // Crear árbol de navegación
    rootNode = new DefaultMutableTreeNode(new TreeItem("root", "Repositorio"));
    treeModel = new DefaultTreeModel(rootNode);
    navigationTree = new JTree(treeModel);
    navigationTree.setRootVisible(true);
    navigationTree.setShowsRootHandles(true);
    navigationTree.setBackground(new Color(255, 255, 255));
    navigationTree.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
    sidebarPanel.add(new JScrollPane(navigationTree), BorderLayout.CENTER);

        // Panel principal de contenido
    mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBackground(Color.WHITE);
    mainPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Título principal (más compacto)
    titleLabel = new JLabel("Repositorio de Algoritmos");
    titleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
    titleLabel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

        // Área de descripción (más compacta)
    descriptionArea = new JTextArea();
    descriptionArea.setFont(new Font("SansSerif", Font.PLAIN, 12));
    descriptionArea.setBackground(new Color(250, 250, 250));
    descriptionArea.setEditable(false);
    descriptionArea.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
    descriptionArea.setWrapStyleWord(true);
    descriptionArea.setLineWrap(true);

        // Panel de pestañas para contenido
        contentTabbedPane = new JTabbedPane();
        contentTabbedPane.setBackground(Color.WHITE);
    }

    private void setupLayout() {
        // Panel de título
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Panel superior con encabezado y descripción
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);
        topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)));

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(contentTabbedPane, BorderLayout.CENTER);

        // Añadir sidebar y contenido principal
        contentPanel.add(sidebarPanel, BorderLayout.WEST);
        contentPanel.add(mainPanel, BorderLayout.CENTER);
        setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);
    }

    /**
     * Agrega un botón de semana al sidebar
     */
    public void addWeekButton(String weekId, String weekTitle, ActionListener listener) {
        // Evitar duplicados por id
        for (int i = 0; i < rootNode.getChildCount(); i++) {
            DefaultMutableTreeNode existing = (DefaultMutableTreeNode) rootNode.getChildAt(i);
            Object obj = existing.getUserObject();
            if (obj instanceof TreeItem && ((TreeItem) obj).id.equals(weekId)) return;
        }
        DefaultMutableTreeNode weekNode = new DefaultMutableTreeNode(new TreeItem(weekId, weekTitle));
        rootNode.add(weekNode);
        treeModel.reload();
        navigationTree.expandPath(new TreePath(weekNode.getPath()));
    }

    /**
     * Agrega una actividad bajo una semana existente (busca el nodo por weekId)
     */
    public void addActivity(String weekId, String activityId, String title) {
        DefaultMutableTreeNode weekNode = null;
        for (int i = 0; i < rootNode.getChildCount(); i++) {
            DefaultMutableTreeNode n = (DefaultMutableTreeNode) rootNode.getChildAt(i);
            Object uo = n.getUserObject();
            if (uo instanceof TreeItem && weekId.equals(((TreeItem) uo).id)) { weekNode = n; break; }
        }
        if (weekNode == null) return;
        addActivityToTree(weekNode, activityId, title);
        treeModel.reload();
        navigationTree.expandPath(new TreePath(weekNode.getPath()));
    }

    /**
     * Agrega una sección (child) dentro de una actividad concreta
     */
    public void addSection(String weekId, String activityId, String sectionTitle) {
        // find week node
        DefaultMutableTreeNode weekNode = null;
        for (int i = 0; i < rootNode.getChildCount(); i++) {
            DefaultMutableTreeNode n = (DefaultMutableTreeNode) rootNode.getChildAt(i);
            Object uo = n.getUserObject();
            if (uo instanceof TreeItem && weekId.equals(((TreeItem) uo).id)) { weekNode = n; break; }
        }
        if (weekNode == null) return;
        // find activity node under week
        DefaultMutableTreeNode activityNode = null;
        for (int i = 0; i < weekNode.getChildCount(); i++) {
            DefaultMutableTreeNode c = (DefaultMutableTreeNode) weekNode.getChildAt(i);
            Object u = c.getUserObject();
            if (u instanceof TreeItem && ((TreeItem) u).id.equals(weekId + "/" + activityId)) { activityNode = c; break; }
        }
        if (activityNode == null) return;

        String compositeId = weekId + "/" + activityId + "/" + sectionTitle;
        // avoid duplicates by id
        for (int i = 0; i < activityNode.getChildCount(); i++) {
            DefaultMutableTreeNode ch = (DefaultMutableTreeNode) activityNode.getChildAt(i);
            Object u = ch.getUserObject();
            if (u instanceof TreeItem && compositeId.equals(((TreeItem) u).id)) return;
        }
        DefaultMutableTreeNode sectionNode = new DefaultMutableTreeNode(new TreeItem(compositeId, sectionTitle));
        activityNode.add(sectionNode);
        treeModel.reload();
        navigationTree.expandPath(new TreePath(activityNode.getPath()));
    }

    private void addActivityToTree(DefaultMutableTreeNode weekNode, String activityId, String title) {
        Object uo = weekNode.getUserObject();
        String weekId = (uo instanceof TreeItem) ? ((TreeItem) uo).id : String.valueOf(uo);
        String compositeId = weekId + "/" + activityId;
        // Evitar duplicados entre hijos (por id)
        for (int i = 0; i < weekNode.getChildCount(); i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) weekNode.getChildAt(i);
            Object obj = child.getUserObject();
            if (obj instanceof TreeItem && ((TreeItem) obj).id.equals(compositeId)) return;
        }
        DefaultMutableTreeNode activityNode = new DefaultMutableTreeNode(new TreeItem(compositeId, title));
        weekNode.add(activityNode);
    }

    public void setTreeSelectionListener(TreeSelectionListener listener) {
        navigationTree.addTreeSelectionListener(listener);
    }

    public String getSelectedTreeItemId() {
        TreePath selectedPath = navigationTree.getSelectionPath();
        if (selectedPath != null) {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
            Object u = selectedNode.getUserObject();
            if (u instanceof TreeItem) return ((TreeItem) u).id;
            return u != null ? String.valueOf(u) : null;
        }
        return null;
    }

    public void updateTitle(String title) {
        titleLabel.setText(title);
    }

    public void updateDescription(String description) {
        descriptionArea.setText(description);
    }

    public void addTab(String title, Component component) {
        contentTabbedPane.addTab(title, component);
    }

    public void clearTabs() {
        contentTabbedPane.removeAll();
    openTabs.clear();
    }

    public void addActivityView(String id, Component view) {
        if (openTabs.containsKey(id)) {
            Component existing = openTabs.get(id);
            contentTabbedPane.setSelectedComponent(existing);
            return;
        }
        String tabTitle = (view != null && view.getName() != null && !view.getName().isEmpty()) ? view.getName() : id;
        contentTabbedPane.addTab(tabTitle, view);
        openTabs.put(id, view);
        contentTabbedPane.setSelectedComponent(view);
    }

    // Expandir todos los nodos
    public void showWeeks() {
        for (int i = 0; i < navigationTree.getRowCount(); i++) {
            navigationTree.expandRow(i);
        }
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
        contentPanel.setEnabled(enabled);
    }
}
