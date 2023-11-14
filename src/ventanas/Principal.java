/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
//este sirve
 */
package ventanas;

import com.mycompany.proyectointegrador_tomas_jeremias.AdministradorDeRecursos;
import com.mycompany.proyectointegrador_tomas_jeremias.Experimento;
import com.mycompany.proyectointegrador_tomas_jeremias.Experimento_Biologico;
import com.mycompany.proyectointegrador_tomas_jeremias.Experimento_Fisico;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import com.mycompany.proyectointegrador_tomas_jeremias.Cientifico;
import com.mycompany.proyectointegrador_tomas_jeremias.Equipo;
import javax.swing.ListSelectionModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 *
 * @author jerem
 */
public class Principal extends javax.swing.JFrame {

    int xMouse, yMouse;

    /**
     * Creates new form Principal
     */
    ArrayList<Experimento> listaExperimentosBioFis;
    ArrayList<Equipo> auxListaEquipos;
    ArrayList<Equipo> auxListaEquiposOriginal;
    ArrayList<Cientifico> auxListaCientifico;

    // Indice para recuperar informacion de la lista
    int auxIndex;

    // Esta es la informacion inicial de mi programa    
    AdministradorDeRecursos recursos;

    //Objeto auxiliar para cargar la lista
    //Data cargada
    public Principal() {

        initComponents();
        try {
            setIconImage(new ImageIcon(getClass().getResource("../com/images/experimentar.png")).getImage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        listaExperimentosBioFis = new ArrayList<>();
        auxListaCientifico = new ArrayList<>();
        auxListaEquipos = new ArrayList<>();
        recursos = new AdministradorDeRecursos();

        // Metodo para que solo se pueda seleccionar un elemento de las lista
        // Lista Experimentos
        jListMuestraExperimentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Lista Equipos
        jListEquipos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Lista Cientifico 
        jListCientificos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Seteo model lista Equipo
        DefaultListModel modelEquipo = new DefaultListModel();

        for (Equipo e : recursos.getListaEquipo()) {
            modelEquipo.addElement(e.getNombre());
        }
        jListEquipos.setModel(modelEquipo);

        // Seteo model lista Cientifico
        DefaultListModel modelCientifico = new DefaultListModel();
        for (Cientifico e : recursos.getListaCientifico()) {
            modelCientifico.addElement(e.getNombre() + " " + e.getApellido() + " " + e.getDni());
        }
        jListCientificos.setModel(modelCientifico);

        // Visisbilidad del Tipo de Experimento
        txtFenomeno.setVisible(false);
        txtOrganismo.setVisible(false);
        lFecha.setVisible(false);
        lOrganismo.setVisible(false);

        // Carga de la lista principal con el archivo
        listaExperimentosBioFis=recursos.cargarExperimentos();

        // Control de la lista bio fis por si se llena mal desde el archivo
        if (listaExperimentosBioFis==null) {
            JOptionPane.showMessageDialog(null, "Ocurrio un error en la lectura del archivo");
            listaExperimentosBioFis = new ArrayList<>();
        }

        // Seteo model lista experimento
        DefaultListModel model = new DefaultListModel();
        for (Experimento e : listaExperimentosBioFis) {
            model.addElement(e.getTitulo());
        }
        jListMuestraExperimentos.setModel(model);

        // Botones de seleccion cambien cuando presiono algo o no 
        // Equipo
        jListEquipos.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    Object selectedValue = jListEquipos.getSelectedValue();
                    if (selectedValue != null && selectedValue.toString().contains("-")) {
                        jBtnCargarEquipo.setText("Deseleccionar");
                    } else {
                        jBtnCargarEquipo.setText("Seleccionar");
                    }
                }
            }
        });

        // Cientifico
        jListCientificos.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    Object selectedValue = jListCientificos.getSelectedValue();
                    if (selectedValue != null && selectedValue.toString().contains("-")) {
                        jBtnCargarCientifico.setText("Deseleccionar");
                    } else {
                        jBtnCargarCientifico.setText("Seleccionar");
                    }
                }
            }
        });
        // Equipo Modifica
        jListEquipoModifica.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    Object selectedValue = jListEquipoModifica.getSelectedValue();
                    if (selectedValue != null && jListEquipoModifica.getSelectedValue().contains("-")) {
                        jBtnSeleccionEquipoModifica.setText("Deseleccionar");
                    } else {
                        jBtnSeleccionEquipoModifica.setText("Seleccionar");
                    }
                }
            }
        });

        // Cientifico Modifica
        jListCientificoModifica.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    Object selectedValue = jListCientificoModifica.getSelectedValue();
                    if (selectedValue != null && jListCientificoModifica.getSelectedValue().contains("-")) {
                        jBtnCargarCientificoModifica.setText("Deseleccionar");
                    } else {
                        jBtnCargarCientificoModifica.setText("Seleccionar");
                    }
                }
            }
        });

        // Guardo equipo?????????
        recursos.guardarEquipos();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        frenteAzul = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jBtnCargarExperimento = new javax.swing.JButton();
        jBtnInformacion = new javax.swing.JButton();
        jBtnSalir = new javax.swing.JPanel();
        jBtnSalirTxt = new javax.swing.JLabel();
        jBtnMinimiza = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        contenedor = new javax.swing.JPanel();
        cargarExperimento = new javax.swing.JPanel();
        jComboBoxTipos = new javax.swing.JComboBox<>();
        txtFenomeno = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jDaChFechaInicio = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        lOrganismo = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JTextField();
        txtPresupuesto = new javax.swing.JTextField();
        txtOrganismo = new javax.swing.JTextField();
        lFecha = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtADescripcion = new javax.swing.JTextArea();
        jBtnCargarCientifico = new javax.swing.JButton();
        JbtnModificarExperimento = new javax.swing.JButton();
        jBtnEnviar = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        jListMuestraExperimentos = new javax.swing.JList<>();
        jBtnEliminarExperimento = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jListEquipos = new javax.swing.JList<>();
        jBtnCargarEquipo = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jListCientificos = new javax.swing.JList<>();
        jLabel30 = new javax.swing.JLabel();
        jDaChFechaFin = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        modifica = new javax.swing.JPanel();
        jComboBoxTiposModifica = new javax.swing.JComboBox<>();
        txtFenomenoModifica = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jDaChFechaInicioModifica = new com.toedter.calendar.JDateChooser();
        jDaChFechaFinModifica = new com.toedter.calendar.JDateChooser();
        jLabel16 = new javax.swing.JLabel();
        lOrganismoModifica = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtTituloModifica = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtPresupuestoModifica = new javax.swing.JTextField();
        txtOrganismoModifica = new javax.swing.JTextField();
        lFenomenoModifica = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtADescripcionModifica = new javax.swing.JTextArea();
        btnEnviarModifica = new javax.swing.JButton();
        jBtnCargarCientificoModifica = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        jListCientificoModifica = new javax.swing.JList<>();
        jLabel34 = new javax.swing.JLabel();
        jBtnSeleccionEquipoModifica = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        jListEquipoModifica = new javax.swing.JList<>();
        jLabel33 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jPanelInformacion = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jCantidadExpFisicos = new javax.swing.JLabel();
        jCantidadExpBiologicos = new javax.swing.JLabel();
        jSumaPresupuestosExp = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jCantidadEquipos = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jLabelResultadoMayor = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        jLabelResultadoMenor = new javax.swing.JTextArea();
        jCantidadExpFisicos1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setUndecorated(true);
        setResizable(false);

        frenteAzul.setBackground(new java.awt.Color(129, 154, 204));
        frenteAzul.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        frenteAzul.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                frenteAzulMouseDragged(evt);
            }
        });
        frenteAzul.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                frenteAzulMousePressed(evt);
            }
        });
        frenteAzul.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        frenteAzul.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 40, 40));

        jBtnCargarExperimento.setBackground(new java.awt.Color(190, 204, 231));
        jBtnCargarExperimento.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jBtnCargarExperimento.setText("Carga");
        jBtnCargarExperimento.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnCargarExperimento.setContentAreaFilled(false);
        jBtnCargarExperimento.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnCargarExperimento.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBtnCargarExperimento.setOpaque(true);
        jBtnCargarExperimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCargarExperimentoActionPerformed1(evt);
            }
        });
        frenteAzul.add(jBtnCargarExperimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 100, 40));

        jBtnInformacion.setBackground(new java.awt.Color(190, 204, 231));
        jBtnInformacion.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jBtnInformacion.setText("Informacion");
        jBtnInformacion.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnInformacion.setContentAreaFilled(false);
        jBtnInformacion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnInformacion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBtnInformacion.setOpaque(true);
        jBtnInformacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnInformacionActionPerformed(evt);
            }
        });
        frenteAzul.add(jBtnInformacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 120, 40));

        jBtnSalir.setBackground(new java.awt.Color(129, 154, 204));

        jBtnSalirTxt.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jBtnSalirTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jBtnSalirTxt.setText("X");
        jBtnSalirTxt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnSalirTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jBtnSalirTxtMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBtnSalirTxtMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBtnSalirTxtMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jBtnSalirLayout = new javax.swing.GroupLayout(jBtnSalir);
        jBtnSalir.setLayout(jBtnSalirLayout);
        jBtnSalirLayout.setHorizontalGroup(
            jBtnSalirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jBtnSalirLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBtnSalirTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );
        jBtnSalirLayout.setVerticalGroup(
            jBtnSalirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jBtnSalirTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        frenteAzul.add(jBtnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 10, 40, 40));

        jBtnMinimiza.setBackground(new java.awt.Color(129, 154, 204));

        jLabel12.setBackground(new java.awt.Color(129, 154, 204));
        jLabel12.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 48)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("-");
        jLabel12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jBtnMinimizaLayout = new javax.swing.GroupLayout(jBtnMinimiza);
        jBtnMinimiza.setLayout(jBtnMinimizaLayout);
        jBtnMinimizaLayout.setHorizontalGroup(
            jBtnMinimizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );
        jBtnMinimizaLayout.setVerticalGroup(
            jBtnMinimizaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE)
        );

        frenteAzul.add(jBtnMinimiza, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 10, 40, 40));

        contenedor.setLayout(new java.awt.CardLayout());

        cargarExperimento.setBackground(new java.awt.Color(255, 255, 255));
        cargarExperimento.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        cargarExperimento.setForeground(new java.awt.Color(51, 51, 51));
        cargarExperimento.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBoxTipos.setFont(new java.awt.Font("Yu Gothic", 0, 12)); // NOI18N
        jComboBoxTipos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "Fisico", "Biologico" }));
        jComboBoxTipos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jComboBoxTipos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTiposActionPerformed(evt);
            }
        });
        cargarExperimento.add(jComboBoxTipos, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 120, -1));

        txtFenomeno.setFont(new java.awt.Font("Yu Gothic", 0, 12)); // NOI18N
        txtFenomeno.setSelectionColor(new java.awt.Color(51, 102, 255));
        txtFenomeno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFenomenoActionPerformed(evt);
            }
        });
        cargarExperimento.add(txtFenomeno, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 120, -1));

        jLabel7.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel7.setText("Fecha Fin");
        cargarExperimento.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 220, 100, -1));

        jLabel3.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel3.setText("Descripcion");
        cargarExperimento.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, -1, -1));

        jLabel6.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel6.setText("Fecha Inicio");
        cargarExperimento.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 170, 80, -1));

        jDaChFechaInicio.setBackground(new java.awt.Color(255, 255, 255));
        jDaChFechaInicio.setFocusable(false);
        jDaChFechaInicio.setFont(new java.awt.Font("Yu Gothic", 0, 12)); // NOI18N
        jDaChFechaInicio.setMaxSelectableDate(new java.util.Date(2524622491000L));
        jDaChFechaInicio.setMinSelectableDate(new java.util.Date(1672545691000L));
        jDaChFechaInicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jDaChFechaInicioMouseClicked(evt);
            }
        });
        jDaChFechaInicio.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDaChFechaInicioPropertyChange(evt);
            }
        });
        cargarExperimento.add(jDaChFechaInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 190, 130, -1));

        jLabel8.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel8.setText("Tipo");
        cargarExperimento.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, -1));

        lOrganismo.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        lOrganismo.setText("Organismo");
        cargarExperimento.add(lOrganismo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, -1, -1));

        jLabel4.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel4.setText("Presupuesto");
        cargarExperimento.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(192, 110, 80, -1));

        txtTitulo.setFont(new java.awt.Font("Yu Gothic", 0, 12)); // NOI18N
        txtTitulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTituloActionPerformed(evt);
            }
        });
        cargarExperimento.add(txtTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 290, -1));

        txtPresupuesto.setFont(new java.awt.Font("Yu Gothic", 0, 12)); // NOI18N
        txtPresupuesto.setSelectionColor(new java.awt.Color(51, 102, 255));
        txtPresupuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPresupuestoActionPerformed(evt);
            }
        });
        cargarExperimento.add(txtPresupuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 130, 130, -1));

        txtOrganismo.setSelectionColor(new java.awt.Color(51, 102, 255));
        txtOrganismo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOrganismoActionPerformed(evt);
            }
        });
        cargarExperimento.add(txtOrganismo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 120, 30));

        lFecha.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        lFecha.setText("Fenomeno");
        cargarExperimento.add(lFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, -1, -1));

        txtADescripcion.setBackground(new java.awt.Color(243, 243, 243));
        txtADescripcion.setColumns(20);
        txtADescripcion.setFont(new java.awt.Font("Yu Gothic", 0, 12)); // NOI18N
        txtADescripcion.setRows(5);
        txtADescripcion.setSelectionColor(new java.awt.Color(51, 102, 255));
        jScrollPane2.setViewportView(txtADescripcion);

        cargarExperimento.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 290, 270));

        jBtnCargarCientifico.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jBtnCargarCientifico.setText("Seleccionar");
        jBtnCargarCientifico.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnCargarCientifico.setContentAreaFilled(false);
        jBtnCargarCientifico.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnCargarCientifico.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBtnCargarCientifico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCargarCientificoActionPerformed(evt);
            }
        });
        cargarExperimento.add(jBtnCargarCientifico, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 510, 100, 30));

        JbtnModificarExperimento.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        JbtnModificarExperimento.setText("Modificar");
        JbtnModificarExperimento.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        JbtnModificarExperimento.setContentAreaFilled(false);
        JbtnModificarExperimento.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        JbtnModificarExperimento.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        JbtnModificarExperimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JbtnModificarExperimentoActionPerformed(evt);
            }
        });
        cargarExperimento.add(JbtnModificarExperimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 510, 70, 30));

        jBtnEnviar.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jBtnEnviar.setText("Guardar");
        jBtnEnviar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnEnviar.setContentAreaFilled(false);
        jBtnEnviar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnEnviar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBtnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEnviarActionPerformed(evt);
            }
        });
        cargarExperimento.add(jBtnEnviar, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 550, 100, 30));

        jListMuestraExperimentos.setBackground(new java.awt.Color(243, 243, 243));
        jListMuestraExperimentos.setFont(new java.awt.Font("Yu Gothic", 0, 12)); // NOI18N
        jListMuestraExperimentos.setSelectionBackground(new java.awt.Color(51, 102, 255));
        jScrollPane8.setViewportView(jListMuestraExperimentos);

        cargarExperimento.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 70, 240, 420));

        jBtnEliminarExperimento.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jBtnEliminarExperimento.setText("Eliminar");
        jBtnEliminarExperimento.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnEliminarExperimento.setContentAreaFilled(false);
        jBtnEliminarExperimento.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnEliminarExperimento.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBtnEliminarExperimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEliminarExperimentoActionPerformed(evt);
            }
        });
        cargarExperimento.add(jBtnEliminarExperimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 510, 60, 30));

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel31.setText("EXPERIMENTOS CARGADOS");
        cargarExperimento.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 20, -1, -1));

        jListEquipos.setBackground(new java.awt.Color(243, 243, 243));
        jListEquipos.setFont(new java.awt.Font("Yu Gothic", 0, 12)); // NOI18N
        jListEquipos.setSelectionBackground(new java.awt.Color(51, 102, 255));
        jListEquipos.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListEquiposValueChanged(evt);
            }
        });
        jScrollPane6.setViewportView(jListEquipos);

        cargarExperimento.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 70, 320, 140));

        jBtnCargarEquipo.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jBtnCargarEquipo.setText("Seleccionar");
        jBtnCargarEquipo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnCargarEquipo.setContentAreaFilled(false);
        jBtnCargarEquipo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBtnCargarEquipo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBtnCargarEquipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCargarEquipoActionPerformed(evt);
            }
        });
        cargarExperimento.add(jBtnCargarEquipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 220, 100, 30));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel29.setText("CARGA EQUIPO");
        cargarExperimento.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, -1, -1));

        jListCientificos.setBackground(new java.awt.Color(243, 243, 243));
        jListCientificos.setFont(new java.awt.Font("Yu Gothic", 0, 12)); // NOI18N
        jListCientificos.setSelectionBackground(new java.awt.Color(51, 102, 255));
        jScrollPane7.setViewportView(jListCientificos);

        cargarExperimento.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 300, 320, 190));

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel30.setText("CARGA CIENTIFICO");
        cargarExperimento.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 250, -1, 30));

        jDaChFechaFin.setBackground(new java.awt.Color(255, 255, 255));
        jDaChFechaFin.setFont(new java.awt.Font("Yu Gothic", 0, 12)); // NOI18N
        jDaChFechaFin.setMaxSelectableDate(new java.util.Date(2524622491000L));
        jDaChFechaFin.setMinSelectableDate(new java.util.Date(1672545691000L));
        jDaChFechaFin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jDaChFechaFinMouseClicked(evt);
            }
        });
        jDaChFechaFin.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDaChFechaFinPropertyChange(evt);
            }
        });
        cargarExperimento.add(jDaChFechaFin, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 240, 130, -1));

        jLabel5.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel5.setText("Titulo");
        cargarExperimento.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel36.setText("CARGA EXPERIMENTO");
        cargarExperimento.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 220, 40));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/dinero.png"))); // NOI18N
        cargarExperimento.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 130, 40, -1));

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/marcado (1).png"))); // NOI18N
        cargarExperimento.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 10, 40, 40));

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/cientifico.png"))); // NOI18N
        cargarExperimento.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 250, 40, -1));

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/prueba-de-sangre (2).png"))); // NOI18N
        cargarExperimento.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 40, -1));

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/reunion.png"))); // NOI18N
        cargarExperimento.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, 40, 40));

        contenedor.add(cargarExperimento, "card4");

        modifica.setBackground(new java.awt.Color(255, 255, 255));
        modifica.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBoxTiposModifica.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jComboBoxTiposModifica.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "Fisico", "Biologico" }));
        jComboBoxTiposModifica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTiposModificaActionPerformed(evt);
            }
        });
        modifica.add(jComboBoxTiposModifica, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 120, -1));

        txtFenomenoModifica.setFont(new java.awt.Font("Yu Gothic", 0, 12)); // NOI18N
        modifica.add(txtFenomenoModifica, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, 120, -1));

        jLabel13.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel13.setText("Fecha Fin");
        modifica.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 240, 80, -1));

        jLabel14.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel14.setText("Descripcion");
        modifica.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, 100, -1));

        jLabel15.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel15.setText("Fecha Inicio");
        modifica.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 190, 100, -1));

        jDaChFechaInicioModifica.setBackground(new java.awt.Color(255, 255, 255));
        jDaChFechaInicioModifica.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jDaChFechaInicioModifica.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDaChFechaInicioModificaPropertyChange(evt);
            }
        });
        modifica.add(jDaChFechaInicioModifica, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 210, 120, -1));

        jDaChFechaFinModifica.setBackground(new java.awt.Color(255, 255, 255));
        jDaChFechaFinModifica.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jDaChFechaFinModifica.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDaChFechaFinModificaPropertyChange(evt);
            }
        });
        modifica.add(jDaChFechaFinModifica, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 260, 120, -1));

        jLabel16.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel16.setText("Tipo");
        modifica.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 60, -1));

        lOrganismoModifica.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        lOrganismoModifica.setText("Organismo");
        modifica.add(lOrganismoModifica, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 90, 20));

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel32.setText("MODIFICAR EXPERIMENTO");
        modifica.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 250, 34));

        jLabel17.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel17.setText("Presupuesto");
        modifica.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 130, 100, -1));

        txtTituloModifica.setFont(new java.awt.Font("Yu Gothic", 0, 12)); // NOI18N
        txtTituloModifica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTituloModificaActionPerformed(evt);
            }
        });
        modifica.add(txtTituloModifica, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 270, -1));

        jLabel18.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel18.setText("Titulo");
        modifica.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 60, -1));

        txtPresupuestoModifica.setFont(new java.awt.Font("Yu Gothic", 0, 12)); // NOI18N
        txtPresupuestoModifica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPresupuestoModificaActionPerformed(evt);
            }
        });
        modifica.add(txtPresupuestoModifica, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 150, 120, -1));

        txtOrganismoModifica.setFont(new java.awt.Font("Yu Gothic", 0, 12)); // NOI18N
        txtOrganismoModifica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOrganismoModificaActionPerformed(evt);
            }
        });
        modifica.add(txtOrganismoModifica, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 120, -1));

        lFenomenoModifica.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        lFenomenoModifica.setText("Fenomeno");
        modifica.add(lFenomenoModifica, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, 90, -1));

        txtADescripcionModifica.setBackground(new java.awt.Color(246, 246, 246));
        txtADescripcionModifica.setColumns(20);
        txtADescripcionModifica.setFont(new java.awt.Font("Yu Gothic", 0, 12)); // NOI18N
        txtADescripcionModifica.setRows(5);
        jScrollPane3.setViewportView(txtADescripcionModifica);

        modifica.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, 270, 250));

        btnEnviarModifica.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        btnEnviarModifica.setText("Enviar");
        btnEnviarModifica.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnEnviarModifica.setContentAreaFilled(false);
        btnEnviarModifica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarModificaActionPerformed(evt);
            }
        });
        modifica.add(btnEnviarModifica, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 550, 110, 30));

        jBtnCargarCientificoModifica.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jBtnCargarCientificoModifica.setText("Seleccion");
        jBtnCargarCientificoModifica.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnCargarCientificoModifica.setContentAreaFilled(false);
        jBtnCargarCientificoModifica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCargarCientificoModificaActionPerformed(evt);
            }
        });
        modifica.add(jBtnCargarCientificoModifica, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 490, 120, 30));

        jListCientificoModifica.setBackground(new java.awt.Color(246, 246, 246));
        jListCientificoModifica.setFont(new java.awt.Font("Yu Gothic", 0, 12)); // NOI18N
        jListCientificoModifica.setSelectionBackground(new java.awt.Color(51, 102, 255));
        jScrollPane10.setViewportView(jListCientificoModifica);

        modifica.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 330, 370, 140));

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel34.setText("MODIFICAR CIENTIFICOS");
        modifica.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 280, 240, 34));

        jBtnSeleccionEquipoModifica.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jBtnSeleccionEquipoModifica.setText("Seleccion");
        jBtnSeleccionEquipoModifica.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnSeleccionEquipoModifica.setContentAreaFilled(false);
        jBtnSeleccionEquipoModifica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSeleccionEquipoModificaActionPerformed(evt);
            }
        });
        modifica.add(jBtnSeleccionEquipoModifica, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 240, 120, 30));

        jListEquipoModifica.setBackground(new java.awt.Color(246, 246, 246));
        jListEquipoModifica.setFont(new java.awt.Font("Yu Gothic", 0, 12)); // NOI18N
        jListEquipoModifica.setSelectionBackground(new java.awt.Color(51, 102, 255));
        jScrollPane9.setViewportView(jListEquipoModifica);

        modifica.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 100, 370, 130));

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel33.setText("MODIFICAR EQUIPOS");
        modifica.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 30, 190, 34));

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/prueba-de-sangre (2).png"))); // NOI18N
        modifica.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 30, 40, -1));

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/experimentar.png"))); // NOI18N
        modifica.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 30, 250, 560));

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/cientifico.png"))); // NOI18N
        modifica.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 280, 40, -1));

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/reunion.png"))); // NOI18N
        modifica.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 30, 40, 40));

        contenedor.add(modifica, "card5");

        jPanelInformacion.setBackground(new java.awt.Color(255, 255, 255));
        jPanelInformacion.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanelInformacion.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(288, 18, -1, -1));

        jLabel9.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel9.setText("Suma de presupuesto de todos los experimentos:");
        jPanelInformacion.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        jLabel11.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel11.setText("Cantidad de Experimentos Biologicos:");
        jPanelInformacion.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

        jLabel19.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel19.setText("Cantidad de Experimentos Fisicos:");
        jPanelInformacion.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, -1));

        jCantidadExpFisicos.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jCantidadExpFisicos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/informacion (1).png"))); // NOI18N
        jCantidadExpFisicos.setText("info");
        jPanelInformacion.add(jCantidadExpFisicos, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 20, 480, 570));

        jCantidadExpBiologicos.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jCantidadExpBiologicos.setText("info");
        jPanelInformacion.add(jCantidadExpBiologicos, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 80, 290, 20));

        jSumaPresupuestosExp.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jSumaPresupuestosExp.setText("info");
        jPanelInformacion.add(jSumaPresupuestosExp, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 50, 310, 20));

        jLabel20.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel20.setText("Equipo mas usado");
        jPanelInformacion.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 410, 190, 30));

        jCantidadEquipos.setBackground(new java.awt.Color(241, 241, 241));
        jCantidadEquipos.setColumns(20);
        jCantidadEquipos.setRows(5);
        jScrollPane1.setViewportView(jCantidadEquipos);

        jPanelInformacion.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 440, 470, 120));

        jLabel1.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/tortuga (4).png"))); // NOI18N
        jLabel1.setText("El proyecto de mayor duracion es:");
        jPanelInformacion.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, -1, -1));

        jLabel21.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/images/liebre.png"))); // NOI18N
        jLabel21.setText("El proyecto de menor duracion es:");
        jPanelInformacion.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, 240, 40));

        jLabelResultadoMayor.setBackground(new java.awt.Color(241, 241, 241));
        jLabelResultadoMayor.setColumns(20);
        jLabelResultadoMayor.setRows(5);
        jScrollPane4.setViewportView(jLabelResultadoMayor);

        jPanelInformacion.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 470, 100));

        jLabelResultadoMenor.setBackground(new java.awt.Color(241, 241, 241));
        jLabelResultadoMenor.setColumns(20);
        jLabelResultadoMenor.setRows(5);
        jScrollPane5.setViewportView(jLabelResultadoMenor);

        jPanelInformacion.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 470, 100));

        jCantidadExpFisicos1.setFont(new java.awt.Font("Yu Gothic", 1, 12)); // NOI18N
        jCantidadExpFisicos1.setText("info");
        jPanelInformacion.add(jCantidadExpFisicos1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 110, 290, 20));

        contenedor.add(jPanelInformacion, "card4");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(frenteAzul, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(contenedor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(frenteAzul, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contenedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnCargarExperimentoActionPerformed1(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCargarExperimentoActionPerformed1
        // TODO add your handling code here:
        lFecha.setVisible(false);
        lOrganismo.setVisible(false);
        txtFenomeno.setVisible(false);
        txtOrganismo.setVisible(false);
        auxListaEquipos.clear();
        auxListaCientifico.clear();
        contenedor.removeAll();
        contenedor.add(cargarExperimento);
        contenedor.repaint();
        contenedor.revalidate();
        jBtnCargarExperimento.setText("Cargar");
        jLabel35.setIcon(null);

        limpiarCampos();

    }//GEN-LAST:event_jBtnCargarExperimentoActionPerformed1

    // Funcion para limpiar campos
    private void limpiarCampos() {
        // Limpiar Titulo
        txtTitulo.setText("");

        // Limpiar Combo box
        jComboBoxTipos.setSelectedItem("-");

        // Limpiar Presupuesto
        txtPresupuesto.setText("");

        // Limpiar Descripcion
        txtADescripcion.setText("");

        // Limpiar Fecha inicio
        jDaChFechaInicio.setDate(null);

        // Limpiar Fecha fin
        jDaChFechaFin.setDate(null);

        // Limpiar Organismo
        txtFenomeno.setText("");

        // Limpiar Fenomeno
        txtOrganismo.setText("");

        // Seteo model de cientifico de 0
        DefaultListModel modelCientifico = new DefaultListModel();
        for (Cientifico e : recursos.getListaCientifico()) {
            modelCientifico.addElement(e.getNombre() + " " + e.getApellido() + " " + e.getDni());
        }
        jListCientificos.setModel(modelCientifico);

        // Fecha Contratacion

        // Seteo Model de Equipo de 0 
        DefaultListModel modelEquipo = new DefaultListModel();

        for (Equipo e : recursos.getListaEquipo()) {
            modelEquipo.addElement(e.getNombre());
        }
        jListEquipos.setModel(modelEquipo);

    }

    //Imprime lista BioFis para ver si esta bien cargada
    private void imprimirPorPantallaListaPrincipal() {
        int n = 0;
        for (Experimento e : listaExperimentosBioFis) {
            System.out.println("EXPERIMENTO: " + n++);
            System.out.println(e.getTitulo());
            System.out.println(e.getDescripcion());
            System.out.println(e.getFin());
            System.out.println(e.getInicio());
            System.out.println(e.getPresupuesto());
            int j = 0;
            for (Equipo i : e.getListaEquipo()) {
                System.out.println(i.getNombre() + " EQUIPO" + j++);//"""" IMPRRIME EL NOMBRE Y TAMBIEN IMPRIME EL SET MODEL ????""""
            }
            j = 0;
            for (Cientifico i : e.getListaCientifico()) {
                System.out.println(i.getNombre() + " CIENTIFICO" + j++);//"""" IMPRRIME EL NOMBRE Y TAMBIEN IMPRIME EL SET MODEL ????""""
            }
            if (e instanceof Experimento_Biologico) {
                System.out.println(((Experimento_Biologico) e).getOrganismo());
            } else if (e instanceof Experimento_Fisico) {
                System.out.println(((Experimento_Fisico) e).getFenomeno());
            }
        }
    }
    //

    private void jBtnInformacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnInformacionActionPerformed
        // TODO add your handling code here:
        contenedor.removeAll();
        contenedor.add(jPanelInformacion);
        contenedor.repaint();
        contenedor.revalidate();

        // Presupuesto Total
        float auxTotal = 0;
        for (Experimento e : listaExperimentosBioFis) {
            auxTotal = e.getPresupuesto() + auxTotal;
        }
        jSumaPresupuestosExp.setText(Float.toString(auxTotal));

        // Cantidad de Biologicos
        int cantTotalBiologicos = 0;
        for (Experimento e : listaExperimentosBioFis) {
            if ("Biologico".equals(e.getTipo())) {
                cantTotalBiologicos = cantTotalBiologicos + 1;
            }
        }
        jCantidadExpBiologicos.setText(Float.toString(cantTotalBiologicos));

        // Cantidad de Fisico
        int cantTotalFisicos = 0;
        for (Experimento e : listaExperimentosBioFis) {
            if ("Fisico".equals(e.getTipo())) {
                cantTotalFisicos = cantTotalFisicos + 1;
            }
        }
        jCantidadExpFisicos1.setText(Float.toString(cantTotalFisicos));

        // Experimento mas largo
        int auxFecha = 0;
//        for (Experimento e : listaExperimentosBioFis) {
//            auxFecha = (int) (e.getFin().getTime() - e.getInicio().getTime());
//        }
//        System.out.println(auxFecha);
        // Experimento mas corto

        // El equipo mas utilizado
        int mayor = 0;
        String equipoMayor = "";

        for (Equipo e : recursos.getListaEquipo()) {
            if (e.getContador() > mayor) {
                mayor = e.getContador();
                equipoMayor = e.getNombre();
            } else if (e.getContador() == mayor) {
                equipoMayor += ", " + e.getNombre();
            }
        }

        if (mayor != 0) {
            jCantidadEquipos.setText("El equipo(s) más usado(s) es(son): " + equipoMayor + ", usado(s) esta cantidad: " + String.valueOf(mayor));
        } else {
            jCantidadEquipos.setText("No hay equipos usados");
        }

//        //Determinar el experimento de menor y mayor duracion....        
//        Experimento eMayor = listaExperimentosBioFis.get(0);
//        Experimento eMenor = listaExperimentosBioFis.get(0);
//        
//        
//        //String menor=listaExperimentosBioFis.get(0).getTitulo();
//        // Crear un objeto SimpleDateFormat para formatear fechas ya que getInicio devuelve un string
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDate iniMayor = null;
//        LocalDate finMayor = null;
//        LocalDate iniOtro = null;
//        LocalDate finOtro = null;
//        long duracionOtro = 0;
//        long duracionMayor = 0;
//        long duracionMenor = 0;
//        for (Experimento e : listaExperimentosBioFis) {
//            //casteos de date a LocalDate para poder usar la funcion que calcula dias entre dos fechas dadas(Ya que ChronoUnit solo recibe objetos temporales como LocalDate)
//            iniMayor = LocalDate.parse(eMayor.getInicio(), dtf);
//            finMayor = LocalDate.parse(eMayor.getFin(), dtf);
//            iniOtro = LocalDate.parse(e.getInicio(), dtf);
//            finOtro = LocalDate.parse(e.getFin(), dtf);
//            System.out.println(iniMayor);
//            System.out.println(finMayor);
//            duracionMayor = ChronoUnit.DAYS.between(iniMayor, finMayor);
//            duracionOtro = ChronoUnit.DAYS.between(iniOtro, finOtro);
//            if (duracionMayor < duracionOtro) {
//                eMayor = e;
//            }
//            if (duracionMayor > duracionOtro) {
//                eMenor = e;
//            }
//        }
//        //Las 3 lineas siguientes se hacen para asegurarse que se actualice el valor "duracionMayor" ya que daba problemas si el de mayor duracion era el ultimo elemento.
//        iniMayor = LocalDate.parse(eMayor.getInicio(), dtf);
//        finMayor = LocalDate.parse(eMayor.getFin(), dtf);
//        duracionMayor = ChronoUnit.DAYS.between(iniMayor, finMayor);
//        jLabelResultadoMayor.setText(eMayor.getTitulo() + " con " + duracionMayor + " dias");
//        iniMayor = LocalDate.parse(eMenor.getInicio(), dtf);
//        finMayor = LocalDate.parse(eMenor.getFin(), dtf);
//        duracionMenor = ChronoUnit.DAYS.between(iniMayor, finMayor);
//        System.out.println("Duracion menor2: " + duracionMenor);
//        jLabelResultadoMenor.setText(eMenor.getTitulo() + " con " + duracionMenor + " dias");
        long duracionMayor, duracionMenor;
        duracionMayor = 0;
        duracionMenor = Math.abs(ChronoUnit.DAYS.between(LocalDate.parse(listaExperimentosBioFis.get(0).getInicio()), LocalDate.parse(listaExperimentosBioFis.get(0).getFin())));
        int index1 = 0, index2 = 0;
        String experimentosMayores = "", experimentosMenores = "";

        for (Experimento exp : listaExperimentosBioFis) {
            LocalDate inicioLD = LocalDate.parse(exp.getInicio());
            LocalDate finLD = LocalDate.parse(exp.getFin());

            if (mayor != 0) {
                jCantidadEquipos.setText("El equipo(s) más usado(s) es(son): " + equipoMayor + ", usado(s) esta cantidad: " + String.valueOf(mayor));
            } else {
                jCantidadEquipos.setText("No hay equipos usados");
            }

            // Calculo el mayor
            long duracionAux = Math.abs(ChronoUnit.DAYS.between(inicioLD, finLD));
            if (duracionAux > duracionMayor) {
                duracionMayor = duracionAux;
                experimentosMayores = exp.getTitulo();
            } else if (duracionAux == duracionMayor) {
                experimentosMayores += ", " + exp.getTitulo();
            }

            // Calculo el menor
            long duracion = Math.abs(ChronoUnit.DAYS.between(inicioLD, finLD));

            if (duracion < duracionMenor) {
                duracionMenor = duracion;
                experimentosMenores = exp.getTitulo();
            } else if (duracionAux == duracionMenor) {
                experimentosMenores += ", " + exp.getTitulo();
            }

        }
        jLabelResultadoMayor.setText(experimentosMayores + " con " + duracionMayor + " dias");
        jLabelResultadoMenor.setText(experimentosMenores + " con " + duracionMenor + " dias");

    }//GEN-LAST:event_jBtnInformacionActionPerformed

    private void jComboBoxTiposModificaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTiposModificaActionPerformed
        // TODO add your handling code here:        // TODO add your handling code here:

        if ("Biologico".equals(jComboBoxTiposModifica.getSelectedItem())) {
            lOrganismoModifica.setVisible(true);
            txtOrganismoModifica.setVisible(true);
            txtFenomenoModifica.setText(txtOrganismoModifica.getText());
            lFenomenoModifica.setVisible(false);
            txtFenomenoModifica.setVisible(false);

        }
        if ("Fisico".equals(jComboBoxTiposModifica.getSelectedItem())) {
            lFenomenoModifica.setVisible(true);
            txtFenomenoModifica.setVisible(true);
            txtOrganismoModifica.setText(String.valueOf(txtFenomenoModifica.getText()));
            lOrganismoModifica.setVisible(false);
            txtOrganismoModifica.setVisible(false);

        }
        if ("-".equals(jComboBoxTiposModifica.getSelectedItem())) {
            lOrganismoModifica.setVisible(false);
            txtOrganismoModifica.setVisible(false);
            lFenomenoModifica.setVisible(false);
            txtFenomenoModifica.setVisible(false);
            txtFenomenoModifica.setText("");
            txtOrganismoModifica.setText("");

        }
    }//GEN-LAST:event_jComboBoxTiposModificaActionPerformed

    private void txtTituloModificaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTituloModificaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTituloModificaActionPerformed

    private void txtPresupuestoModificaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPresupuestoModificaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPresupuestoModificaActionPerformed

    private void txtOrganismoModificaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOrganismoModificaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOrganismoModificaActionPerformed

    private void jBtnSeleccionEquipoModificaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSeleccionEquipoModificaActionPerformed
        //Equipo
        String auxEquipo = jListEquipoModifica.getSelectedValue();

        // Control de Equipo
        if (auxEquipo == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un Equipo valido");
            return;
        }
        // LLeno la lista auxiliar para luego pasarla al experimento.
        for (Equipo e : recursos.getListaEquipo()) {
            if ((e.getNombre() + " - Seleccionado").equals(auxEquipo)) {
                auxListaEquipos.remove(e);
            }
            if ((e.getNombre()).equals(auxEquipo)) {
                auxListaEquipos.add(e);
            }
        }

        //Lleno el model del jlist viendo si existe o no dentro de mis lista auxiliar.
        DefaultListModel modelEquipo = new DefaultListModel();

        for (Equipo e : recursos.getListaEquipo()) {
            if (auxListaEquipos.contains(e)) {
                modelEquipo.addElement(e.getNombre() + " - Seleccionado");
            } else {
                modelEquipo.addElement(e.getNombre());
            }

        }

        jListEquipoModifica.setModel(modelEquipo);

//        // Guardo en la lista auxiliar de equipo
//        Equipo equipo = new Equipo(auxEquipo);
//        auxListaEquipos.add(equipo);
        repaint();
        revalidate();


        /*  private void jBtnCargarCientificoActionPerformed(java.awt.event.ActionEvent evt) {                                                     

        // Cientifico 
        String auxCientifico = jListCientificos.getSelectedValue();

        // Control de cientifico 
        if (auxCientifico == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un cientifico valido");
            return;
        }

        // Control de Fecha
        Date auxFecha = jDaChInicioCientificos.getDate();
        if (jDaChInicioCientificos.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Error: Ingresa un valor de fecha válido.");
            return;
            // Manejar el caso en que las fechas sean nulas, por ejemplo, mostrar un mensaje de error
        }

// LLeno la lista auxiliar para luego pasarla al experimento.
        for (Cientifico e : recursos.getListaCientifico()) {
            if (auxCientifico.contains(e.getDni()) && auxCientifico.contains("-")) {
                auxListaCientifico.remove(e);
            }
            if ((e.getNombre() + " " + e.getApellido() + " " + e.getDni()).equals(auxCientifico)) {
                e.setContratacion(auxFecha);
                auxListaCientifico.add(e);
            }
        }
        // Lleno el model del jlist viendo si existe o no dentro de mis lista auxiliar.
//        DefaultListModel modelEquipo = new DefaultListModel();
        DefaultListModel modelCientifico = new DefaultListModel();
        for (Cientifico e : recursos.getListaCientifico()) {
            if (auxListaCientifico.contains(e)) {
                modelCientifico.addElement(e.getNombre() + " " + e.getApellido() + " " + e.getDni() + " - " + e.getContratacion());
            } else {
                modelCientifico.addElement(e.getNombre() + " " + e.getApellido() + " " + e.getDni());
            }
        }

        jListCientificos.setModel(modelCientifico);

        // Repintar contenedor
        contenedor.repaint();
        contenedor.revalidate();

        // TODO add your handling code here:

    }  */

    }//GEN-LAST:event_jBtnSeleccionEquipoModificaActionPerformed

    private void jBtnCargarCientificoModificaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCargarCientificoModificaActionPerformed

        String auxCientifico = jListCientificoModifica.getSelectedValue();

        // Control de cientifico 
        if (auxCientifico == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un cientifico valido");
            return;
        }

        /* Control de Fecha
        Date auxFecha = jDaChInicioCientificos1.getDate();

        LocalDate localDate = auxFecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String fechaCientifico = localDate.toString();*/
           //obtencion de la fecha de contratacion del cientifico
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");//codigo para pasar de string al tipo date
        Date auxFecha=new Date();
        for(Cientifico c: recursos.getListaCientifico()){         
            System.out.println(c.getNombre());
            if(auxCientifico.contains(c.getDni()) && auxCientifico.contains(c.getApellido())){         
                try{
                auxFecha =formato.parse(c.getContratacion());
                }catch(ParseException e){
                    e.printStackTrace();
                }
            }
        }
        LocalDate localDate = auxFecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String fechaCientifico = localDate.toString();


// LLeno la lista auxiliar para luego pasarla al experimento.
        for (Cientifico e : recursos.getListaCientifico()) {
            if (auxCientifico.contains(e.getDni()) && auxCientifico.contains("-")) {
                auxListaCientifico.remove(e);
            }
            if ((e.getNombre() + " " + e.getApellido() + " " + e.getDni()).equals(auxCientifico)) {
                e.setContratacion(fechaCientifico);
                auxListaCientifico.add(e);
            }
        }
        // Lleno el model del jlist viendo si existe o no dentro de mis lista auxiliar.
//        DefaultListModel modelEquipo = new DefaultListModel();
        DefaultListModel modelCientifico = new DefaultListModel();
        for (Cientifico e : recursos.getListaCientifico()) {
            if (auxListaCientifico.contains(e)) {
                modelCientifico.addElement(e.getNombre() + " " + e.getApellido() + " " + e.getDni() + " - " + "SELECCIONADO");
            } else {
                modelCientifico.addElement(e.getNombre() + " " + e.getApellido() + " " + e.getDni());
            }
        }
        //aca empiezan los cambios del control para rango de ffechas del experimento
        Date iniExp = jDaChFechaInicioModifica.getDate();
        Date finExp = jDaChFechaFinModifica.getDate();
        LocalDate localIniExp = iniExp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate finVerde = finExp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate inicioVerde = auxFecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        //if((auxFecha.equals(iniExp)||auxFecha.after(iniExp)) && (auxFecha.equals(finExp) || auxFecha.before(finExp))){
       /* if ((inicioVerde.isAfter(localIniExp) || inicioVerde.equals(localIniExp)) && inicioVerde.isBefore(finVerde) || inicioVerde.equals(finVerde)) {
            ;
        } else {
            JOptionPane.showMessageDialog(null, "Error: Ingresa un valor de fecha que este dentro del Rango de duracion del experimento.");
            return;
        }*/

        int contador = 0;

        for (Experimento e : listaExperimentosBioFis) {

            for (Cientifico c : e.getListaCientifico()) {

                if (auxCientifico.contains(c.getDni()) && !auxCientifico.contains("-")) {

                    // Cuando termina exp de la lista GUARDADA
                    Date expFFin;
                    // Cuando Se contrata al cientifico de la lista GUARDADA
                    Date fContratacion;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {

                        expFFin = dateFormat.parse(e.getFin());
                        fContratacion = dateFormat.parse(c.getContratacion());
                        LocalDate finMorado = expFFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        LocalDate inicioMorado = fContratacion.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                        //if ((inicioMorado.finVerde)||finVerde) && finMorado.finVerde))||finVerde) {
                        //if(finVerde){
                        /*gpt*/
                        if ((inicioMorado.isBefore(finVerde) || inicioMorado.equals(finVerde))
                                && (finMorado.isAfter(inicioVerde) || finMorado.equals(inicioVerde))) {
                            if (contador < 3) {
                                contador += 1;

                            } else {
                                JOptionPane.showMessageDialog(null, "Error: El cientifico no puede pertenecer a 4 proyectos en simultaneo...");
                                return;
                            }
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

        jBtnCargarExperimento.setText("Cargar");

        jListCientificoModifica.setModel(modelCientifico);

        // Repintar contenedor
        contenedor.repaint();
        contenedor.revalidate();

    }//GEN-LAST:event_jBtnCargarCientificoModificaActionPerformed

    private void btnEnviarModificaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarModificaActionPerformed
        // TODO add your handling code here:
        // Titulo
        String expTitulo = txtTituloModifica.getText().trim();
        //Trim borra los espacios iniciales y si tiene un titulo de solo espacios lo toma como si fuera ""
        if ("".equals(expTitulo)) {
            JOptionPane.showMessageDialog(null, "Error: El campo de titulo esta vacio.");
            return;

        }
        // Descripcion
        String expDescripcion = txtADescripcionModifica.getText().trim();
        if ("".equals(expDescripcion)) {
            JOptionPane.showMessageDialog(null, "Error: El campo de descripcion esta vacio.");

            return;
        }

        // Fecha inicio
        Date expFechaInicioValue = jDaChFechaInicioModifica.getDate();
        LocalDate localDate = expFechaInicioValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String expFechaInicioValue1 = localDate.toString();

        if (jDaChFechaInicioModifica.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Error: Ingresa un valor de fecha válido.");
            return;
            // Manejar el caso en que las fechas sean nulas, por ejemplo, mostrar un mensaje de error
        }

        // Fecha fin
        Date expFechaFinValue = jDaChFechaFinModifica.getDate();
        LocalDate localDate2 = expFechaFinValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String expFechaFinValue1 = localDate2.toString();

        if (jDaChFechaFinModifica.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Error: Ingresa un valor de fecha válido.");
            return;
            // Manejar el caso en que las fechas sean nulas, por ejemplo, mostrar un mensaje de error
        }
        LocalDate localExpFechaInicio = expFechaInicioValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localExpFechaFin = expFechaFinValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (localExpFechaFin.isBefore(localExpFechaInicio) || localExpFechaInicio.isAfter(localExpFechaFin)) {
            JOptionPane.showMessageDialog(null, "Error: La fecha de Inicio No puede ser posterior a la fecha de fin.");
            return;
        }

        // Presupuesto
        float expPresupuestoFloat;
        try {
            expPresupuestoFloat = Float.parseFloat(txtPresupuestoModifica.getText().trim());
            // Ahora tienes el valor en formato float
            // Lo que hacemos con el try es ver que sea un numero si no lansa la exepcion. Para que no se rompa el programa y lo atrapamos con
            //el catch.
        } catch (NumberFormatException e) {
            // Maneja aquí la excepción si la entrada no es un float válido
            JOptionPane.showMessageDialog(null, "Error: Ingresa un valor float válido.");
            return;
        }

        // Cientifico
        if (auxListaCientifico.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error: No selecciono ningun cientifico.");
            return;
        }
        for (Cientifico e : auxListaCientifico) {

            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date contratacionCientifico;
                contratacionCientifico = dateFormat.parse(e.getContratacion());
                LocalDate localContratacionCientifico = contratacionCientifico.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
             
            } catch (ParseException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        // Equipos
        if (auxListaEquipos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error: No selecciono ningun equipo.");
            return;
        }

        //Tipo
        //(String) castea lo seleccionado del combo box a tipo String.
        String expTipo = (String) jComboBoxTiposModifica.getSelectedItem();

        if (jComboBoxTiposModifica.getSelectedItem().equals("-")) {
            JOptionPane.showMessageDialog(null, "Error: No selecciono ningun tipo.");
            return;
        }

        // Biologico
        if ("Biologico".equals(jComboBoxTiposModifica.getSelectedItem())) {
            // Organismo
            String expOrganismo = txtOrganismoModifica.getText().trim();
            if ("".equals(expOrganismo)) {
                JOptionPane.showMessageDialog(null, "Error: El campo de organismo esta vacio.");
                return;
            }
            listaExperimentosBioFis.remove(auxIndex);
            listaExperimentosBioFis.add(auxIndex,
                    new Experimento_Biologico(
                            expTitulo,
                            expDescripcion,
                            expPresupuestoFloat,
                            expTipo,
                            expFechaInicioValue1,
                            expFechaFinValue1,
                            (ArrayList<Cientifico>) auxListaCientifico.clone(),//Tengo que clonar la lista porque si lo hago de manera directa lo que pasa en realidad es que el puntero va a la direccion de memoria de la lista y esa esta permamentemente cambiando.
                            (ArrayList<Equipo>) auxListaEquipos.clone(),
                            expOrganismo));
            // Fisico
        } else if ("Fisico".equals(jComboBoxTiposModifica.getSelectedItem())) {
            // Fenomeno
            String expFenomeno = txtFenomenoModifica.getText().trim();
            if ("".equals(expFenomeno)) {
                JOptionPane.showMessageDialog(null, "Error: El campo de fenomeno esta vacio.");
                return;
            }
            listaExperimentosBioFis.remove(auxIndex);
            listaExperimentosBioFis.add(auxIndex,
                    new Experimento_Fisico(
                            expTitulo,
                            expDescripcion,
                            expPresupuestoFloat,
                            expTipo,
                            expFechaInicioValue1,
                            expFechaFinValue1,
                            (ArrayList<Cientifico>) auxListaCientifico.clone(),
                            (ArrayList<Equipo>) auxListaEquipos.clone(),
                            expFenomeno));

        } else {
            JOptionPane.showMessageDialog(null, "Error: elige un tipo de experimento.");
            return;

            //hay que poner que va a dar un error
            //cada vez que hay un campo vacio colocar un return
        }

        // Actualizar contador de equipos
        if (auxListaEquiposOriginal != auxListaEquipos) {
            //Original
            for (Equipo e : auxListaEquiposOriginal) {
                if (!auxListaEquipos.contains(e)) {
                    e.setContador(e.getContador() - 1);
                }
            }
            //Modificada
            for (Equipo e2 : auxListaEquipos) {
                if (!auxListaEquiposOriginal.contains(e2)) {
                    e2.setContador(e2.getContador() + 1);
                }
            }
        }

        // Seteo el model del jlist experimentos. " HACER FUNCION "
        DefaultListModel model = new DefaultListModel();
        for (Experimento e : listaExperimentosBioFis) {
            model.addElement(e.getTitulo());
        }
        jListMuestraExperimentos.setModel(model);
        recursos.guardarExperimentos(listaExperimentosBioFis);
        recursos.guardarEquipos();

        // Funcion para limpiar todo " Hacer todo"
        limpiarCampos();

        // Para ver si se cargan dentro de lista principal
        //
        //
        //        }
        //imprimirPorPantallaListaPrincipal();
        auxListaEquipos.clear();
        auxListaCientifico.clear();
        contenedor.removeAll();
        contenedor.add(cargarExperimento);
        contenedor.repaint();
        contenedor.revalidate();

    }//GEN-LAST:event_btnEnviarModificaActionPerformed

    private void frenteAzulMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_frenteAzulMousePressed
        // TODO add your handling code here:
        xMouse = evt.getX();
        yMouse = evt.getY();

    }//GEN-LAST:event_frenteAzulMousePressed

    private void frenteAzulMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_frenteAzulMouseDragged
        // TODO add your handling code here:

        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();

        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_frenteAzulMouseDragged

    private void jBtnSalirTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBtnSalirTxtMouseClicked
        System.exit(0);        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnSalirTxtMouseClicked

    private void jBtnSalirTxtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBtnSalirTxtMouseEntered
        // TODO add your handling code here:
        jBtnSalir.setBackground(Color.red);
        jBtnSalirTxt.setForeground(Color.white);
    }//GEN-LAST:event_jBtnSalirTxtMouseEntered

    private void jBtnSalirTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBtnSalirTxtMouseExited
        // TODO add your handling code here:

        jBtnSalir.setBackground(new Color(129, 154, 204));
        jBtnSalirTxt.setForeground(Color.black);

    }//GEN-LAST:event_jBtnSalirTxtMouseExited

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        // TODO add your handling code here:
        this.setExtendedState(ICONIFIED);
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jBtnCargarEquipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCargarEquipoActionPerformed

        //Equipo
        String auxEquipo = jListEquipos.getSelectedValue();

        // Control de Equipo
        if (auxEquipo == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un Equipo valido");
            return;
        }
        // LLeno la lista auxiliar para luego pasarla al experimento.
        for (Equipo e : recursos.getListaEquipo()) {
            int aux = 0;
            if ((e.getNombre() + " - Seleccionado").equals(auxEquipo)) {
                auxListaEquipos.remove(e);
            }
            if ((e.getNombre()).equals(auxEquipo)) {
                auxListaEquipos.add(e);
            }
        }

        //Lleno el model del jlist viendo si existe o no dentro de mis lista auxiliar.
        DefaultListModel modelEquipo = new DefaultListModel();

        for (Equipo e : recursos.getListaEquipo()) {
            if (auxListaEquipos.contains(e)) {
                modelEquipo.addElement(e.getNombre() + " - Seleccionado");
            } else {
                modelEquipo.addElement(e.getNombre());
            }

        }

        jListEquipos.setModel(modelEquipo);

        //        // Guardo en la lista auxiliar de equipo
        //        Equipo equipo = new Equipo(auxEquipo);
        //        auxListaEquipos.add(equipo);
        jLabel35.setIcon(null);
        repaint();
        revalidate();


    }//GEN-LAST:event_jBtnCargarEquipoActionPerformed

    private void jListEquiposValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListEquiposValueChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jListEquiposValueChanged

    private void jBtnEliminarExperimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEliminarExperimentoActionPerformed
        if (jListMuestraExperimentos.getSelectedValue() == null) {
            JOptionPane.showMessageDialog(null, "Error: Seleccione un experimento de la lista.");
            return;
        }
        String expe = jListMuestraExperimentos.getSelectedValue();
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Estás seguro que desea eliminar el experimento " + expe + "?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.NO_OPTION) {
            return;
        }

        // Eliminar elemento de la lista con iterador
        // No es seguro modificar lista con un bucle for each ya que lansa exepcion ConcurrentModificationException
        String experimentoSeleccionado = (String) jListMuestraExperimentos.getSelectedValue();

        //Un Iterator es una interfaz en Java que se utiliza para recorrer y manipular colecciones de elementos, como listas, conjuntos y mapas. Se utiliza para garantizar un acceso controlado
        //y seguro a los elementos de una colección, lo que lo hace especialmente útil para eliminar elementos mientras se itera a través de la colección.
        Iterator<Experimento> iter = listaExperimentosBioFis.iterator();

        //iter.hasNext() verifica si hay elementos restantes en la iteración. Mientras haya elementos en la lista no iterados, el bucle continuará.
        //El bucle while continuará hasta que hasNext() sea false, lo que significa que se han revisado todos los elementos de la lista.
        while (iter.hasNext()) {

            //avanza al siguiente elemento en la lista y lo almacena en la variable exp. Esto permite que accedas a los elementos individuales de la lista uno por uno.
            Experimento exp = iter.next();

            //Busca el que queremos eliminar
            if (experimentoSeleccionado.equals(exp.getTitulo())) {
                //Cuando se encuentra el elemento que deseas eliminar, se utiliza el método remove() del Iterator para eliminarlo de la lista listaExperimentosBioFis

                ArrayList<Equipo> recursosAux = recursos.getListaEquipo();

                for (Equipo e2 : recursosAux) {
                    if (exp.getListaEquipo().contains(e2)) {
                        e2.setContador(e2.getContador() - 1);
                    }

                }

                recursos.setListaEquipo(recursosAux);
                iter.remove(); // Elimina el experimento de la lista
            }
        }

        // Seteo el modelo de vuelta para que se vea sin el eliminado
        DefaultListModel model = new DefaultListModel();
        for (Experimento e : listaExperimentosBioFis) {
            model.addElement(e.getTitulo());
        }
        jListMuestraExperimentos.setModel(model);

        recursos.guardarExperimentos(listaExperimentosBioFis);
        recursos.guardarEquipos();

        contenedor.repaint();

        contenedor.revalidate();

    }//GEN-LAST:event_jBtnEliminarExperimentoActionPerformed

    private void JbtnModificarExperimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JbtnModificarExperimentoActionPerformed
        // TODO add your handling code here:

        // imprimirPorPantallaListaPrincipal();
        auxIndex = 0;

        if (jListMuestraExperimentos.getSelectedValue() == null) {
            JOptionPane.showMessageDialog(null, "Error: Seleccione un experimento de la lista.");
            return;
        }
        Experimento aux = null;

        // Busco el elemento seleccionado de la lista dentro de mi lista principal
        for (Experimento exp : listaExperimentosBioFis) {
            if (jListMuestraExperimentos.getSelectedValue().equals(exp.getTitulo())) {

                aux = exp;

                // Guardamos index para luego borrar y guardar en la misma posicion
                auxIndex = listaExperimentosBioFis.indexOf(exp);
            }
        }
        if (aux == null) {
            JOptionPane.showMessageDialog(null, "Esto no va a suceder nunca");
            return;
        }
        contenedor.removeAll();
        contenedor.add(modifica);
        contenedor.repaint();
        contenedor.revalidate();

        // Modifica Titulo
        txtTituloModifica.setText(aux.getTitulo());

        // Modifica Descripcion
        txtADescripcionModifica.setText(aux.getDescripcion());

        // Modifica Presupuesto
        txtPresupuestoModifica.setText(Float.toString(aux.getPresupuesto()));

        //Modifica ComboBox
        jComboBoxTiposModifica.setSelectedItem(aux.getTipo());

        if (aux.getTipo().equals("Biologico")) {
            // Modifica Organismo
            Experimento_Biologico name = (Experimento_Biologico) aux;
            txtOrganismoModifica.setText(name.getOrganismo());
            txtFenomenoModifica.setText(name.getOrganismo());

            txtFenomenoModifica.setVisible(false);
            lFenomenoModifica.setVisible(false);

        } else {
            // Modifica Fenomeno
            Experimento_Fisico name = (Experimento_Fisico) aux;
            txtFenomenoModifica.setText(name.getFenomeno());
            txtOrganismoModifica.setText(name.getFenomeno());

            txtOrganismoModifica.setVisible(false);
            lOrganismoModifica.setVisible(false);

        }

        // Modifica Lista Equipo
        DefaultListModel modelEquipo = new DefaultListModel();

        for (Equipo e : recursos.getListaEquipo()) {
            if (aux.getListaEquipo().contains(e)) {
                modelEquipo.addElement(e.getNombre() + " - Seleccionado");

            } else {
                modelEquipo.addElement(e.getNombre());

            }

        }

        jListEquipoModifica.setModel(modelEquipo);

        // Modifica Lista Cientifico
        DefaultListModel modelCientifico = new DefaultListModel();
        for (Cientifico c : recursos.getListaCientifico()) {
            if (aux.getListaCientifico().contains(c)) {
                modelCientifico.addElement(c.getNombre() + " " + c.getApellido() + " " + c.getDni() + "-" + "SELECCCIONADO");

            } else {
                modelCientifico.addElement(c.getNombre() + " " + c.getApellido() + " " + c.getDni());
            }
        }
        jListCientificoModifica.setModel(modelCientifico);

        // Configurar la fecha de inicio
        String fechaInicioModifica = aux.getInicio();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = dateFormat.parse(fechaInicioModifica);
            jDaChFechaInicioModifica.setDate(startDate);
        } catch (ParseException e) {
            // Maneja la excepción de análisis si es necesario
            e.printStackTrace();
        }

        // Configurar la fecha de fin
        String fechaFinModifica = aux.getFin();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date endDate = dateFormat.parse(fechaFinModifica);
            jDaChFechaFinModifica.setDate(endDate);
        } catch (ParseException e) {
            // Maneja la excepción de análisis si es necesario
            e.printStackTrace();
        }

        LocalDate localExpFechaInicio = jDaChFechaInicioModifica.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localExpFechaFin = jDaChFechaFinModifica.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (localExpFechaFin.isBefore(localExpFechaInicio) || localExpFechaInicio.isAfter(localExpFechaFin)) {
            JOptionPane.showMessageDialog(null, "Error: La fecha de Inicio No puede ser posterior a la fecha de fin.");
            return;
        }

        jBtnCargarExperimento.setText("Atras");
        ImageIcon image = new ImageIcon("esquema-de-boton-circular-de-flecha-hacia-atras-izquierda");
        jLabel35.setIcon(image);

        // LLeno la lista auxiliar para luego pasarla al experimento que voy a modificar.
        auxListaEquipos = (ArrayList<Equipo>) (listaExperimentosBioFis.get(auxIndex).getListaEquipo()).clone();
        // LLeno la lista auxiliar para luego pasarla al experimento que voy a modificar.
        auxListaCientifico = (ArrayList<Cientifico>) (listaExperimentosBioFis.get(auxIndex).getListaCientifico()).clone();
        // Clono la lista original
        auxListaEquiposOriginal = (ArrayList<Equipo>) (listaExperimentosBioFis.get(auxIndex).getListaEquipo()).clone();
        recursos.guardarExperimentos(listaExperimentosBioFis);
        contenedor.repaint();
        contenedor.revalidate();
    }//GEN-LAST:event_JbtnModificarExperimentoActionPerformed

    private void jBtnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEnviarActionPerformed

        // Titulo
        String expTitulo = txtTitulo.getText().trim();
        //Trim borra los espacios iniciales y si tiene un titulo de solo espacios lo toma como si fuera ""
        if ("".equals(expTitulo)) {
            JOptionPane.showMessageDialog(null, "Error: El campo de titulo esta vacio.");
            return;
        } else {
            for (Experimento exp : listaExperimentosBioFis) {
                if (exp.getTitulo().equals(txtTitulo.getText())) {
                    JOptionPane.showMessageDialog(null, "Error: El titulo ya existe en la lista");
                    return;
                }
            }
        }
        // Descripcion
        String expDescripcion = txtADescripcion.getText().trim();
        if ("".equals(expDescripcion)) {
            JOptionPane.showMessageDialog(null, "Error: El campo de descripcion esta vacio.");

            return;
        }

        // Fecha inicio
        Date expFechaInicioValue = jDaChFechaInicio.getDate();
        if (jDaChFechaInicio.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Error: Ingresa un valor de fecha válido.");
            return;
            // Manejar el caso en que las fechas sean nulas, por ejemplo, mostrar un mensaje de error
        }

        LocalDate localDate = expFechaInicioValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String expFechaInicioValue1 = localDate.toString();

        // Fecha fin
        Date expFechaFinValue = jDaChFechaFin.getDate();
        if (jDaChFechaFin.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Error: Ingresa un valor de fecha válido.");
            return;
            // Manejar el caso en que las fechas sean nulas, por ejemplo, mostrar un mensaje de error
        }
        LocalDate localExpFechaInicio = expFechaInicioValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localExpFechaFin = expFechaFinValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (localExpFechaFin.isBefore(localExpFechaInicio) || localExpFechaInicio.isAfter(localExpFechaFin)) {
            JOptionPane.showMessageDialog(null, "Error: La fecha de Inicio No puede ser posterior a la fecha de fin.");
            return;
        }
        LocalDate localDate1 = expFechaFinValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String expFechaFinValue1 = localDate1.toString();

        // Presupuesto
        float expPresupuestoFloat;

        try {
            expPresupuestoFloat = Float.parseFloat(txtPresupuesto.getText().trim());
            if (expPresupuestoFloat < 0) {
                JOptionPane.showMessageDialog(null, "Error: Ingresa un presupuesto válido.(No puede ser negativo...)");
                return;
            }
            // Ahora tienes el valor en formato float
            // Lo que hacemos con el try es ver que sea un numero si no lansa la exepcion. Para que no se rompa el programa y lo atrapamos con
            //el catch.
        } catch (NumberFormatException e) {
            // Maneja aquí la excepción si la entrada no es un float válido
            JOptionPane.showMessageDialog(null, "Error: Ingresa un valor float válido.");
            return;
        }

        // Cientifico
        if (auxListaCientifico.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error: No selecciono ningun cientifico.");
            return;
        }
      

        // Equipos
        if (auxListaEquipos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error: No selecciono ningun equipo.");
            return;
        }

        //Tipo
        //(String) castea lo seleccionado del combo box a tipo String.
        String expTipo = (String) jComboBoxTipos.getSelectedItem();

        if (jComboBoxTipos.getSelectedItem().equals("-")) {
            JOptionPane.showMessageDialog(null, "Error: No selecciono ningun tipo.");
            return;
        }

        // Biologico
        if ("Biologico".equals(jComboBoxTipos.getSelectedItem())) {
            // Organismo
            String expOrganismo = txtOrganismo.getText().trim();
            if ("".equals(expOrganismo)) {
                JOptionPane.showMessageDialog(null, "Error: El campo de organismo esta vacio.");
                return;
            }
            listaExperimentosBioFis.add(
                    new Experimento_Biologico(
                            expTitulo,
                            expDescripcion,
                            expPresupuestoFloat,
                            expTipo,
                            expFechaInicioValue1,
                            expFechaFinValue1,
                            (ArrayList<Cientifico>) auxListaCientifico.clone(),//Tengo que clonar la lista porque si lo hago de manera directa lo que pasa en realidad es que el puntero va a la direccion de memoria de la lista y esa esta permamentemente cambiando.
                            (ArrayList<Equipo>) auxListaEquipos.clone(),
                            expOrganismo));
            // Fisico
        } else if ("Fisico".equals(jComboBoxTipos.getSelectedItem())) {
            // Fenomeno
            String expFenomeno = txtFenomeno.getText().trim();
            if ("".equals(expFenomeno)) {
                JOptionPane.showMessageDialog(null, "Error: El campo de fenomeno esta vacio.");
                return;
            }
            listaExperimentosBioFis.add(
                    new Experimento_Fisico(
                            expTitulo,
                            expDescripcion,
                            expPresupuestoFloat,
                            expTipo,
                            expFechaInicioValue1,
                            expFechaFinValue1,
                            (ArrayList<Cientifico>) auxListaCientifico.clone(),
                            (ArrayList<Equipo>) auxListaEquipos.clone(),
                            expFenomeno));

        } else {
            JOptionPane.showMessageDialog(null, "Error: elija un tipo de experimento.");
            return;

            //hay que poner que va a dar un error
            //cada vez que hay un campo vacio colocar un return
        }

        // Actualizar contador de equipo
        for (Equipo e1 : auxListaEquipos) {
            for (Equipo e2 : recursos.getListaEquipo()) {
                if (e1.getNombre().equals(e2.getNombre())) {
                    e2.setContador(e2.getContador() + 1);
                }
            }
        }

        // Actualizo archivo Equipos.txt
        recursos.guardarEquipos();

        // Seteo el model del jlist experimentos. " HACER FUNCION "
        DefaultListModel model = new DefaultListModel();
        for (Experimento e : listaExperimentosBioFis) {
            model.addElement(e.getTitulo());
        }
        jListMuestraExperimentos.setModel(model);

        // Funcion para limpiar todo " Hacer todo"
        limpiarCampos();

        contenedor.repaint();
        contenedor.revalidate();

        // Para ver si se cargan dentro de lista principal
        //
        //
        //        }
        // imprimirPorPantallaListaPrincipal();
        recursos.guardarExperimentos(listaExperimentosBioFis);
        auxListaEquipos.clear();
        auxListaCientifico.clear();
        jLabel35.setVisible(false);

    }//GEN-LAST:event_jBtnEnviarActionPerformed

    private void jBtnCargarCientificoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCargarCientificoActionPerformed
        String auxCientifico = jListCientificos.getSelectedValue();

        // Control de cientifico
        if (auxCientifico == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un cientifico valido");
            return;
        }

        // Control de Fecha
        if (jDaChFechaInicio.getDate() == null || jDaChFechaFin.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Error: Primero cargue las fechas de inicio y fin de experimento.");
            return;
        }
        //obtencion de la fecha de contratacion del cientifico
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");//codigo para pasar de string al tipo date
        Date auxFecha=new Date();
        for(Cientifico c: recursos.getListaCientifico()){ 
            if(auxCientifico.contains(c.getDni()) && auxCientifico.contains(c.getApellido())){         
                try{
                auxFecha =formato.parse(c.getContratacion());
                }catch(ParseException e){                    
                    
                }
            }
        }
                    
        //Control Para que la fecha de contratacion del cientifico este dentro del rango que dura el experimento
        //iniMayor=eMayor.getInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Date iniExp = jDaChFechaInicio.getDate();
        Date finExp = jDaChFechaFin.getDate();
        LocalDate localIniExp = iniExp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate finVerde = finExp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate inicioVerde = auxFecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
 
        LocalDate localDate = auxFecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String fechaCientifico = localDate.toString();
        // LLeno la lista auxiliar para luego pasarla al experimento.
        for (Cientifico e : recursos.getListaCientifico()) {
            if (auxCientifico.contains(e.getDni()) && auxCientifico.contains("-")) {
                auxListaCientifico.remove(e);
            }
            if ((e.getNombre() + " " + e.getApellido() + " " + e.getDni()).equals(auxCientifico)) {
                e.setContratacion(fechaCientifico);
                auxListaCientifico.add(e);
            }
        }
        // Lleno el model del jlist viendo si existe o no dentro de mis lista auxiliar.
        //        DefaultListModel modelEquipo = new DefaultListModel();
        DefaultListModel modelCientifico = new DefaultListModel();
         for (Cientifico e : recursos.getListaCientifico()) {
            if (auxListaCientifico.contains(e)) {    
                modelCientifico.addElement(e.getNombre() + " " + e.getApellido() + " " + e.getDni() + " - " +"SELECCIONADO");
            } else {
                modelCientifico.addElement(e.getNombre() + " " + e.getApellido() + " " + e.getDni());
            }
        }
      
        //CONTROLLLLLLLL para que cientifico no este en mas de 4 experimentos
        int contador = 0;

        for (Experimento e : listaExperimentosBioFis) {

            for (Cientifico c : e.getListaCientifico()) {

                if (auxCientifico.contains(c.getDni()) && !auxCientifico.contains("-")) {

                    // Cuando termina exp de la lista GUARDADA
                    Date expFFin;
                    // Cuando Se contrata al cientifico de la lista GUARDADA
                    Date fContratacion;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {

                        expFFin = dateFormat.parse(e.getFin());
                        fContratacion = dateFormat.parse(c.getContratacion());
                        LocalDate finMorado = expFFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        LocalDate inicioMorado = fContratacion.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                       
                        if ((inicioMorado.isBefore(finVerde) || inicioMorado.equals(finVerde))
                                && (finMorado.isAfter(inicioVerde) || finMorado.equals(inicioVerde))) {
                            if (contador < 3) {
                                contador += 1;

                            } else {
                                JOptionPane.showMessageDialog(null, "Error: El cientifico no puede pertenecer a 4 proyectos en simultaneo...");
                                return;
                            }
                        }

                    } catch (ParseException ex) {
                        Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }

        }

        jListCientificos.setModel(modelCientifico);   

        // Repintar contenedor
        contenedor.repaint();
        contenedor.revalidate();

        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnCargarCientificoActionPerformed

    private void txtOrganismoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOrganismoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOrganismoActionPerformed

    private void txtPresupuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPresupuestoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPresupuestoActionPerformed

    private void txtTituloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTituloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTituloActionPerformed

    private void txtFenomenoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFenomenoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFenomenoActionPerformed

    private void jComboBoxTiposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTiposActionPerformed
        // TODO add your handling code here:

        if ("Biologico".equals(jComboBoxTipos.getSelectedItem())) {
            lOrganismo.setVisible(true);
            txtOrganismo.setVisible(true);
            lFecha.setVisible(false);
            txtFenomeno.setVisible(false);
            txtFenomeno.setText("");

        } else if ("Fisico".equals(jComboBoxTipos.getSelectedItem())) {
            lFecha.setVisible(true);
            txtFenomeno.setVisible(true);
            lOrganismo.setVisible(false);
            txtOrganismo.setVisible(false);
            txtOrganismo.setText("");
        } else {
            lOrganismo.setVisible(false);
            txtOrganismo.setVisible(false);
            lFecha.setVisible(false);
            txtFenomeno.setVisible(false);
            txtFenomeno.setText("");
            txtOrganismo.setText("");

        }

    }//GEN-LAST:event_jComboBoxTiposActionPerformed

    private void jDaChFechaInicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDaChFechaInicioMouseClicked

    }//GEN-LAST:event_jDaChFechaInicioMouseClicked

    private void jDaChFechaFinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDaChFechaFinMouseClicked

    }//GEN-LAST:event_jDaChFechaFinMouseClicked

    private void jDaChFechaInicioPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDaChFechaInicioPropertyChange

    }//GEN-LAST:event_jDaChFechaInicioPropertyChange

    private void jDaChFechaFinPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDaChFechaFinPropertyChange

    }//GEN-LAST:event_jDaChFechaFinPropertyChange

    private void jDaChFechaInicioModificaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDaChFechaInicioModificaPropertyChange

    }//GEN-LAST:event_jDaChFechaInicioModificaPropertyChange

    private void jDaChFechaFinModificaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDaChFechaFinModificaPropertyChange

    }//GEN-LAST:event_jDaChFechaFinModificaPropertyChange

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JbtnModificarExperimento;
    private javax.swing.JButton btnEnviarModifica;
    private javax.swing.JPanel cargarExperimento;
    private javax.swing.JPanel contenedor;
    private javax.swing.JPanel frenteAzul;
    private javax.swing.JButton jBtnCargarCientifico;
    private javax.swing.JButton jBtnCargarCientificoModifica;
    private javax.swing.JButton jBtnCargarEquipo;
    private javax.swing.JButton jBtnCargarExperimento;
    private javax.swing.JButton jBtnEliminarExperimento;
    private javax.swing.JButton jBtnEnviar;
    private javax.swing.JButton jBtnInformacion;
    private javax.swing.JPanel jBtnMinimiza;
    private javax.swing.JPanel jBtnSalir;
    private javax.swing.JLabel jBtnSalirTxt;
    private javax.swing.JButton jBtnSeleccionEquipoModifica;
    private javax.swing.JTextArea jCantidadEquipos;
    private javax.swing.JLabel jCantidadExpBiologicos;
    private javax.swing.JLabel jCantidadExpFisicos;
    private javax.swing.JLabel jCantidadExpFisicos1;
    private javax.swing.JComboBox<String> jComboBoxTipos;
    private javax.swing.JComboBox<String> jComboBoxTiposModifica;
    private com.toedter.calendar.JDateChooser jDaChFechaFin;
    private com.toedter.calendar.JDateChooser jDaChFechaFinModifica;
    private com.toedter.calendar.JDateChooser jDaChFechaInicio;
    private com.toedter.calendar.JDateChooser jDaChFechaInicioModifica;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextArea jLabelResultadoMayor;
    private javax.swing.JTextArea jLabelResultadoMenor;
    private javax.swing.JList<String> jListCientificoModifica;
    private javax.swing.JList<String> jListCientificos;
    private javax.swing.JList<String> jListEquipoModifica;
    private javax.swing.JList<String> jListEquipos;
    private javax.swing.JList<String> jListMuestraExperimentos;
    private javax.swing.JPanel jPanelInformacion;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel jSumaPresupuestosExp;
    private javax.swing.JLabel lFecha;
    private javax.swing.JLabel lFenomenoModifica;
    private javax.swing.JLabel lOrganismo;
    private javax.swing.JLabel lOrganismoModifica;
    private javax.swing.JPanel modifica;
    private javax.swing.JTextArea txtADescripcion;
    private javax.swing.JTextArea txtADescripcionModifica;
    private javax.swing.JTextField txtFenomeno;
    private javax.swing.JTextField txtFenomenoModifica;
    private javax.swing.JTextField txtOrganismo;
    private javax.swing.JTextField txtOrganismoModifica;
    private javax.swing.JTextField txtPresupuesto;
    private javax.swing.JTextField txtPresupuestoModifica;
    private javax.swing.JTextField txtTitulo;
    private javax.swing.JTextField txtTituloModifica;
    // End of variables declaration//GEN-END:variables
}
