/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIJPT;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;
import elements.Camera;
import elements.SceneLoader;
import maths.Background;
import pathtracer.PathTracer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 *
 * @author RubenM
 */
public class MainGUI extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public MainGUI() {
        initComponents();
    }

    /**
     * Method that initialise the interface
     * It was initially made with a swing gui and altered after
     */
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        dialogDetalles = new javax.swing.JDialog();
        panelDialog = new JPanel();
        titleProyecto = new javax.swing.JLabel();
        commentProyecto = new javax.swing.JLabel();
        titleAlumno = new javax.swing.JLabel();
        commentAlumno = new javax.swing.JLabel();
        commentDNI = new javax.swing.JLabel();
        titleCurso = new javax.swing.JLabel();
        commentCurso = new javax.swing.JLabel();
        leftSidePanel = new JPanel();
        buttonSettings = new JPanel();
        titleOpciones = new javax.swing.JLabel();
        titleSideBar = new javax.swing.JLabel();
        rightSidePanel = new JPanel();
        optionsPanel = new JPanel();
        confRenderPanel = new JPanel();
        labelAncho = new javax.swing.JLabel();
        labelAlto = new javax.swing.JLabel();
        spinnerAncho = new javax.swing.JSpinner();
        spinnerAlto = new javax.swing.JSpinner();
        comboAspecto = new javax.swing.JComboBox<>();
        labelTamanoSalida = new javax.swing.JLabel();
        labelFormatoImagen = new javax.swing.JLabel();
        labelRatio = new javax.swing.JLabel();
        confRenderPanel1 = new JPanel();
        labelValoresCalculo = new javax.swing.JLabel();
        labelNumeroPases = new javax.swing.JLabel();
        spinnerNumRebotes = new javax.swing.JSpinner();
        checkBoxNumPases = new javax.swing.JCheckBox();
        labelNumeroRebotes = new javax.swing.JLabel();
        spinnerNumPases = new javax.swing.JSpinner();
        confEntornoPanel = new JPanel();
        labelColorPrincipal = new javax.swing.JLabel();
        labelColorSecundario = new javax.swing.JLabel();
        checkBoxDegradado = new javax.swing.JCheckBox();
        botonPanelColorSecundario = new JPanel();
        botonPanelColorPrimario = new JPanel();
        Separator = new javax.swing.JSeparator();
        salidaImagenPanel = new JPanel();
        buttonOpenFolder = new javax.swing.JButton();
        textRouteRenders = new javax.swing.JTextField();
        labelDirectionRender = new javax.swing.JLabel();
        checkBoxSalvarImagen = new javax.swing.JCheckBox();
        labelGamma = new javax.swing.JLabel();
        spinnerGamma = new javax.swing.JSpinner();
        buttonSaveRender = new javax.swing.JButton();
        renderPanel = new JPanel();
        renderInfoPanel = new JPanel();
        panelRenderDetails = new JPanel();
        renderTime = new javax.swing.JLabel();
        separador1 = new javax.swing.JLabel();
        numeroPases = new javax.swing.JLabel();
        separador2 = new javax.swing.JLabel();
        tamanoRender = new javax.swing.JLabel();
        panelBotonesRender = new JPanel();
        progressBar = new javax.swing.JProgressBar();
        renderButton = new javax.swing.JButton();
        stopRenderButton = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuOpenScene = new javax.swing.JMenuItem();
        menuReOpenScene = new javax.swing.JMenuItem();
        menuHelp = new javax.swing.JMenu();
        menuInformacion = new javax.swing.JMenuItem();

        dialogDetalles.setTitle("Informacion");
        dialogDetalles.setAlwaysOnTop(true);
        dialogDetalles.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        dialogDetalles.setIconImage(new ImageIcon(getClass().getResource("/resources/logo64x64.png")).getImage());
        dialogDetalles.setLocation(new java.awt.Point(100, 100));
        dialogDetalles.setLocationRelativeTo(this);
        dialogDetalles.setMinimumSize(new java.awt.Dimension(330, 230));
        dialogDetalles.setResizable(false);

        titleProyecto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        titleProyecto.setText("Proyecto final de modulo:");

        commentProyecto.setText("Motor de render / Java Path Tracer Render (JPT Render)");

        titleAlumno.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        titleAlumno.setText("Alumno:");

        commentAlumno.setText("Rubén Maudo Hernández");

        commentDNI.setText("70871992E");

        titleCurso.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        titleCurso.setText("Curso:");

        commentCurso.setText("Desarrollo de Aplicaciones Multiplataforma 2020-2021");

        javax.swing.GroupLayout panelDialogLayout = new javax.swing.GroupLayout(panelDialog);
        panelDialog.setLayout(panelDialogLayout);
        panelDialogLayout.setHorizontalGroup(
            panelDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titleProyecto)
                    .addComponent(commentProyecto)
                    .addComponent(titleAlumno)
                    .addComponent(commentAlumno)
                    .addComponent(commentDNI)
                    .addComponent(titleCurso)
                    .addComponent(commentCurso))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelDialogLayout.setVerticalGroup(
            panelDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleProyecto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(commentProyecto)
                .addGap(18, 18, 18)
                .addComponent(titleAlumno)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(commentAlumno)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(commentDNI)
                .addGap(18, 18, 18)
                .addComponent(titleCurso)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(commentCurso)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout dialogDetallesLayout = new javax.swing.GroupLayout(dialogDetalles.getContentPane());
        dialogDetalles.getContentPane().setLayout(dialogDetallesLayout);
        dialogDetallesLayout.setHorizontalGroup(
            dialogDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 330, Short.MAX_VALUE)
            .addGroup(dialogDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(dialogDetallesLayout.createSequentialGroup()
                    .addGap(0, 18, Short.MAX_VALUE)
                    .addComponent(panelDialog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 18, Short.MAX_VALUE)))
        );
        dialogDetallesLayout.setVerticalGroup(
            dialogDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
            .addGroup(dialogDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(dialogDetallesLayout.createSequentialGroup()
                    .addGap(0, 10, Short.MAX_VALUE)
                    .addComponent(panelDialog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 10, Short.MAX_VALUE)))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JPT Render (Java Path Tracer 20-21)");
        setIconImage(new ImageIcon(getClass().getResource("/resources/logo64x64.png")).getImage());
        setMinimumSize(new java.awt.Dimension(1295, 720));
        setName("MainFrame"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1295, 720));
        setSize(new java.awt.Dimension(1295, 720));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        leftSidePanel.setBackground(new Color(0, 102, 255));
        leftSidePanel.setMaximumSize(new java.awt.Dimension(50, 32767));
        leftSidePanel.setMinimumSize(new java.awt.Dimension(50, 720));
        leftSidePanel.setPreferredSize(new java.awt.Dimension(50, 720));

        buttonSettings.setBackground(new Color(51, 150, 246));
        buttonSettings.setMaximumSize(new java.awt.Dimension(50, 140));
        buttonSettings.setMinimumSize(new java.awt.Dimension(50, 140));
        buttonSettings.setPreferredSize(new java.awt.Dimension(50, 140));
        buttonSettings.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonSettingsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                buttonSettingsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                buttonSettingsMouseExited(evt);
            }
        });

        titleOpciones.setFont(new java.awt.Font("Segoe UI Semibold", 1, 22)); // NOI18N
        titleOpciones.setForeground(new Color(255, 255, 255));
        titleOpciones.setIcon(new ImageIcon(getClass().getResource("/resources/sprocket_dark.png"))); // NOI18N
        titleOpciones.setText("Opciones");

        javax.swing.GroupLayout buttonSettingsLayout = new javax.swing.GroupLayout(buttonSettings);
        buttonSettings.setLayout(buttonSettingsLayout);
        buttonSettingsLayout.setHorizontalGroup(
            buttonSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonSettingsLayout.createSequentialGroup()
                .addComponent(titleOpciones, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        buttonSettingsLayout.setVerticalGroup(
            buttonSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonSettingsLayout.createSequentialGroup()
                .addComponent(titleOpciones, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addContainerGap())
        );

        titleOpciones.setUI(new VerticalLabelUI(false));

        titleSideBar.setFont(new java.awt.Font("Segoe UI Semibold", 1, 36)); // NOI18N
        titleSideBar.setForeground(new Color(255, 255, 255));
        titleSideBar.setText("JPT Render");

        javax.swing.GroupLayout leftSidePanelLayout = new javax.swing.GroupLayout(leftSidePanel);
        leftSidePanel.setLayout(leftSidePanelLayout);
        leftSidePanelLayout.setHorizontalGroup(
            leftSidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftSidePanelLayout.createSequentialGroup()
                .addGroup(leftSidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(buttonSettings, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(titleSideBar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        leftSidePanelLayout.setVerticalGroup(
            leftSidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, leftSidePanelLayout.createSequentialGroup()
                .addComponent(buttonSettings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 339, Short.MAX_VALUE)
                .addComponent(titleSideBar, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        flagButtonSettigns=true;
        titleSideBar.setUI(new VerticalLabelUI(false));

        getContentPane().add(leftSidePanel);

        rightSidePanel.setMinimumSize(new java.awt.Dimension(1230, 720));
        rightSidePanel.setPreferredSize(new java.awt.Dimension(1230, 720));
        rightSidePanel.setLayout(new javax.swing.BoxLayout(rightSidePanel, javax.swing.BoxLayout.PAGE_AXIS));

        optionsPanel.setBackground(new Color(255, 255, 255));
        optionsPanel.setMaximumSize(new java.awt.Dimension(32767, 140));
        optionsPanel.setMinimumSize(new java.awt.Dimension(1230, 140));
        optionsPanel.setPreferredSize(new java.awt.Dimension(1230, 140));
        optionsPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        confRenderPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new Color(51, 150, 246), 2, true), "Configuracion Formato Imagen", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        confRenderPanel.setFocusable(false);
        confRenderPanel.setMaximumSize(new java.awt.Dimension(300, 130));
        confRenderPanel.setMinimumSize(new java.awt.Dimension(300, 130));
        confRenderPanel.setOpaque(false);
        confRenderPanel.setPreferredSize(new java.awt.Dimension(300, 130));
        confRenderPanel.setRequestFocusEnabled(false);

        labelAncho.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelAncho.setText("Ancho:");

        labelAlto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelAlto.setText("Alto:");

        spinnerAncho.setModel(new javax.swing.SpinnerNumberModel(800, 50, 10000, 1));
        spinnerAncho.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnerAnchoStateChanged(evt);
            }
        });

        spinnerAlto.setModel(new javax.swing.SpinnerNumberModel(600, 100, 10000, 1));
        spinnerAlto.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnerAltoStateChanged(evt);
            }
        });

        comboAspecto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Personalizado", "16:9", "16:10", "4:3", "3:2", "1:1" }));
        comboAspecto.setMaximumSize(new java.awt.Dimension(300, 20));
        comboAspecto.setMinimumSize(new java.awt.Dimension(300, 20));
        comboAspecto.setPreferredSize(new java.awt.Dimension(300, 20));
        comboAspecto.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboAspectoItemStateChanged(evt);
            }
        });

        labelTamanoSalida.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelTamanoSalida.setText("Tamaño Salida");

        labelFormatoImagen.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelFormatoImagen.setText("Formato Imagen");

        labelRatio.setText("Ratio= 1.3333");

        javax.swing.GroupLayout confRenderPanelLayout = new javax.swing.GroupLayout(confRenderPanel);
        confRenderPanel.setLayout(confRenderPanelLayout);
        confRenderPanelLayout.setHorizontalGroup(
            confRenderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, confRenderPanelLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(labelTamanoSalida, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(labelFormatoImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(37, 37, 37))
            .addGroup(confRenderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(confRenderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelAlto)
                    .addComponent(labelAncho, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(confRenderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spinnerAncho, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinnerAlto, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(confRenderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(confRenderPanelLayout.createSequentialGroup()
                        .addComponent(labelRatio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(15, 15, 15))
                    .addComponent(comboAspecto, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        confRenderPanelLayout.setVerticalGroup(
            confRenderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(confRenderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(confRenderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTamanoSalida)
                    .addComponent(labelFormatoImagen))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(confRenderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelAncho)
                    .addComponent(spinnerAncho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboAspecto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(confRenderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelAlto)
                    .addComponent(spinnerAlto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelRatio))
                .addContainerGap())
        );

        flagEnableSpinnerWidthListener=true;
        flagEnableSpinnerHeigthListener=true;

        optionsPanel.add(confRenderPanel);

        confRenderPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new Color(51, 150, 246), 2, true), "Configuracion Valores Render", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        confRenderPanel1.setFocusable(false);
        confRenderPanel1.setMaximumSize(new java.awt.Dimension(300, 130));
        confRenderPanel1.setMinimumSize(new java.awt.Dimension(300, 130));
        confRenderPanel1.setOpaque(false);
        confRenderPanel1.setPreferredSize(new java.awt.Dimension(300, 130));
        confRenderPanel1.setRequestFocusEnabled(false);

        labelValoresCalculo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelValoresCalculo.setText("Valores de calculo");

        labelNumeroPases.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelNumeroPases.setText("Numero de pases:");
        labelNumeroPases.setMaximumSize(new java.awt.Dimension(113, 14));
        labelNumeroPases.setMinimumSize(new java.awt.Dimension(113, 14));
        labelNumeroPases.setPreferredSize(new java.awt.Dimension(113, 14));

        spinnerNumRebotes.setModel(new javax.swing.SpinnerNumberModel(50, 1, 50, 1));

        checkBoxNumPases.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        checkBoxNumPases.setText("Sin limites");
        checkBoxNumPases.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkBoxNumPasesItemStateChanged(evt);
            }
        });

        labelNumeroRebotes.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelNumeroRebotes.setText("Numero de rebotes:");

        spinnerNumPases.setModel(new javax.swing.SpinnerNumberModel(50, 2, 9999, 1));

        javax.swing.GroupLayout confRenderPanel1Layout = new javax.swing.GroupLayout(confRenderPanel1);
        confRenderPanel1.setLayout(confRenderPanel1Layout);
        confRenderPanel1Layout.setHorizontalGroup(
            confRenderPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(confRenderPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(confRenderPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(confRenderPanel1Layout.createSequentialGroup()
                        .addGroup(confRenderPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(confRenderPanel1Layout.createSequentialGroup()
                                .addComponent(labelNumeroRebotes, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spinnerNumRebotes, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(labelValoresCalculo, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(94, Short.MAX_VALUE))
                    .addGroup(confRenderPanel1Layout.createSequentialGroup()
                        .addComponent(labelNumeroPases, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinnerNumPases, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkBoxNumPases, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        confRenderPanel1Layout.setVerticalGroup(
            confRenderPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(confRenderPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelValoresCalculo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(confRenderPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spinnerNumPases, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkBoxNumPases)
                    .addComponent(labelNumeroPases, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(confRenderPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spinnerNumRebotes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelNumeroRebotes))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        optionsPanel.add(confRenderPanel1);

        confEntornoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new Color(51, 150, 246), 2, true), "Iluminacion Entorno", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        confEntornoPanel.setFocusable(false);
        confEntornoPanel.setMaximumSize(new java.awt.Dimension(300, 130));
        confEntornoPanel.setMinimumSize(new java.awt.Dimension(300, 130));
        confEntornoPanel.setOpaque(false);
        confEntornoPanel.setPreferredSize(new java.awt.Dimension(300, 130));
        confEntornoPanel.setRequestFocusEnabled(false);

        labelColorPrincipal.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelColorPrincipal.setText("Color Principal cielo");

        labelColorSecundario.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelColorSecundario.setText("Color Secundario cielo");
        labelColorSecundario.setEnabled(false);

        checkBoxDegradado.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        checkBoxDegradado.setText("Cielo Degradado");
        checkBoxDegradado.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkBoxDegradadoItemStateChanged(evt);
            }
        });

        botonPanelColorSecundario.setBackground(new Color(200, 200, 200));
        botonPanelColorSecundario.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        botonPanelColorSecundario.setEnabled(false);
        botonPanelColorSecundario.setMaximumSize(new java.awt.Dimension(100, 50));
        botonPanelColorSecundario.setMinimumSize(new java.awt.Dimension(100, 50));
        botonPanelColorSecundario.setPreferredSize(new java.awt.Dimension(100, 50));
        botonPanelColorSecundario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                botonPanelColorSecundarioMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout botonPanelColorSecundarioLayout = new javax.swing.GroupLayout(botonPanelColorSecundario);
        botonPanelColorSecundario.setLayout(botonPanelColorSecundarioLayout);
        botonPanelColorSecundarioLayout.setHorizontalGroup(
            botonPanelColorSecundarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 94, Short.MAX_VALUE)
        );
        botonPanelColorSecundarioLayout.setVerticalGroup(
            botonPanelColorSecundarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        botonPanelColorPrimario.setBackground(new Color(153, 190, 255));
        botonPanelColorPrimario.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        botonPanelColorPrimario.setMaximumSize(new java.awt.Dimension(100, 50));
        botonPanelColorPrimario.setMinimumSize(new java.awt.Dimension(100, 50));
        botonPanelColorPrimario.setPreferredSize(new java.awt.Dimension(100, 50));
        botonPanelColorPrimario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                botonPanelColorPrimarioMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout botonPanelColorPrimarioLayout = new javax.swing.GroupLayout(botonPanelColorPrimario);
        botonPanelColorPrimario.setLayout(botonPanelColorPrimarioLayout);
        botonPanelColorPrimarioLayout.setHorizontalGroup(
            botonPanelColorPrimarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 94, Short.MAX_VALUE)
        );
        botonPanelColorPrimarioLayout.setVerticalGroup(
            botonPanelColorPrimarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        Separator.setForeground(new Color(51, 150, 246));
        Separator.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout confEntornoPanelLayout = new javax.swing.GroupLayout(confEntornoPanel);
        confEntornoPanel.setLayout(confEntornoPanelLayout);
        confEntornoPanelLayout.setHorizontalGroup(
            confEntornoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(confEntornoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(confEntornoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelColorPrincipal)
                    .addComponent(botonPanelColorPrimario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addComponent(Separator, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(confEntornoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(confEntornoPanelLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(confEntornoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelColorSecundario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(checkBoxDegradado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(confEntornoPanelLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(botonPanelColorSecundario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        confEntornoPanelLayout.setVerticalGroup(
            confEntornoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(confEntornoPanelLayout.createSequentialGroup()
                .addGroup(confEntornoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(confEntornoPanelLayout.createSequentialGroup()
                        .addComponent(checkBoxDegradado, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(confEntornoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelColorPrincipal)
                            .addComponent(labelColorSecundario))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(confEntornoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botonPanelColorSecundario, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonPanelColorPrimario, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 13, Short.MAX_VALUE))
                    .addComponent(Separator))
                .addGap(0, 0, 0))
        );

        optionsPanel.add(confEntornoPanel);

        salidaImagenPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new Color(51, 150, 246), 2, true), "Configuracion Salida Imagen", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        salidaImagenPanel.setFocusable(false);
        salidaImagenPanel.setMaximumSize(new java.awt.Dimension(300, 130));
        salidaImagenPanel.setMinimumSize(new java.awt.Dimension(300, 130));
        salidaImagenPanel.setOpaque(false);
        salidaImagenPanel.setPreferredSize(new java.awt.Dimension(300, 130));
        salidaImagenPanel.setRequestFocusEnabled(false);

        buttonOpenFolder.setIcon(new ImageIcon(getClass().getResource("/resources/folder_classic_opened.png"))); // NOI18N
        buttonOpenFolder.setMaximumSize(new java.awt.Dimension(25, 25));
        buttonOpenFolder.setMinimumSize(new java.awt.Dimension(25, 25));
        buttonOpenFolder.setPreferredSize(new java.awt.Dimension(25, 25));
        buttonOpenFolder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                buttonOpenFolderMouseClicked(evt);
            }
        });

        textRouteRenders.setText(System.getProperty("user.home")+"\\Documents\\JPTR renders");
        textRouteRenders.setMaximumSize(new java.awt.Dimension(2147483647, 25));
        textRouteRenders.setMinimumSize(new java.awt.Dimension(7, 25));
        textRouteRenders.setPreferredSize(new java.awt.Dimension(22, 25));

        labelDirectionRender.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelDirectionRender.setText("Directorio Destino Renders");

        checkBoxSalvarImagen.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        checkBoxSalvarImagen.setSelected(true);
        checkBoxSalvarImagen.setText("Salvar Imagen al completar render");

        labelGamma.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelGamma.setText("Imagen Gamma");

        spinnerGamma.setModel(new javax.swing.SpinnerNumberModel(2.0d, 0.5d, 4.0d, 0.1d));

        buttonSaveRender.setText("Salvar Render");
        buttonSaveRender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveRenderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout salidaImagenPanelLayout = new javax.swing.GroupLayout(salidaImagenPanel);
        salidaImagenPanel.setLayout(salidaImagenPanelLayout);
        salidaImagenPanelLayout.setHorizontalGroup(
                salidaImagenPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(salidaImagenPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(salidaImagenPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(salidaImagenPanelLayout.createSequentialGroup()
                                                .addComponent(buttonOpenFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(textRouteRenders, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(checkBoxSalvarImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(salidaImagenPanelLayout.createSequentialGroup()
                                                .addComponent(labelDirectionRender, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(salidaImagenPanelLayout.createSequentialGroup()
                                                .addComponent(labelGamma, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(spinnerGamma, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(buttonSaveRender, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        salidaImagenPanelLayout.setVerticalGroup(
                salidaImagenPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(salidaImagenPanelLayout.createSequentialGroup()
                                .addComponent(labelDirectionRender)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(salidaImagenPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(buttonOpenFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textRouteRenders, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(checkBoxSalvarImagen)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(salidaImagenPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelGamma)
                                        .addComponent(spinnerGamma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(buttonSaveRender))
                                .addContainerGap())
        );

        optionsPanel.add(salidaImagenPanel);

        rightSidePanel.add(optionsPanel);

        renderPanel.setLayout(new java.awt.BorderLayout());
        rightSidePanel.add(renderPanel);


        renderInfoPanel.setMaximumSize(new java.awt.Dimension(32767, 35));
        renderInfoPanel.setMinimumSize(new java.awt.Dimension(1200, 35));
        renderInfoPanel.setPreferredSize(new java.awt.Dimension(1200, 35));
        renderInfoPanel.setLayout(new java.awt.GridBagLayout());

        panelRenderDetails.setMaximumSize(new java.awt.Dimension(500, 35));
        panelRenderDetails.setMinimumSize(new java.awt.Dimension(500, 35));
        panelRenderDetails.setPreferredSize(new java.awt.Dimension(500, 35));
        panelRenderDetails.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 10));

        renderTime.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        renderTime.setText("Tiempo de render:  0h:0m:0s");
        renderTime.setToolTipText("");
        renderTime.setMaximumSize(new java.awt.Dimension(185, 14));
        renderTime.setMinimumSize(new java.awt.Dimension(185, 14));
        renderTime.setPreferredSize(new java.awt.Dimension(185, 14));
        renderTime.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        panelRenderDetails.add(renderTime);
        renderTime.getAccessibleContext().setAccessibleName("Tiempo de Render:");

        separador1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        separador1.setText("  ||  ");
        separador1.setToolTipText("");
        separador1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        panelRenderDetails.add(separador1);
        separador1.getAccessibleContext().setAccessibleName("");

        numeroPases.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        numeroPases.setText("Numero de pases: ");
        numeroPases.setToolTipText("");
        numeroPases.setMaximumSize(new java.awt.Dimension(135, 14));
        numeroPases.setMinimumSize(new java.awt.Dimension(135, 14));
        numeroPases.setPreferredSize(new java.awt.Dimension(135, 14));
        numeroPases.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        panelRenderDetails.add(numeroPases);

        separador2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        separador2.setText("  ||  ");
        separador2.setToolTipText("");
        separador2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        panelRenderDetails.add(separador2);

        tamanoRender.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tamanoRender.setText("Tamaño de render: 800 x 600 px");
        tamanoRender.setToolTipText("");
        tamanoRender.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        panelRenderDetails.add(tamanoRender);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        renderInfoPanel.add(panelRenderDetails, gridBagConstraints);

        panelBotonesRender.setMaximumSize(new java.awt.Dimension(500, 35));
        panelBotonesRender.setMinimumSize(new java.awt.Dimension(500, 35));
        panelBotonesRender.setPreferredSize(new java.awt.Dimension(500, 35));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 7);
        flowLayout1.setAlignOnBaseline(true);
        panelBotonesRender.setLayout(flowLayout1);

        progressBar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        progressBar.setMaximumSize(new java.awt.Dimension(300, 20));
        progressBar.setMinimumSize(new java.awt.Dimension(300, 20));
        progressBar.setPreferredSize(new java.awt.Dimension(300, 20));
        progressBar.setString("");
        progressBar.setStringPainted(true);
        panelBotonesRender.add(progressBar);

        renderButton.setText("Render");
        renderButton.setAlignmentX(0.5F);
        renderButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                renderButtonMouseClicked(evt);
            }
        });
        panelBotonesRender.add(renderButton);

        stopRenderButton.setText("Stop");
        stopRenderButton.setAlignmentX(0.5F);
        stopRenderButton.setEnabled(false);
        stopRenderButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                stopRenderButtonMouseClicked(evt);
            }
        });

        stopRenderButton.addKeyListener(new KeyListener(){
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    camera.updateCameraPositionRight();
                    controlKeys=true;
                }else if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    camera.updateCameraPositionLeft();
                    controlKeys=true;
                }else if(e.getKeyCode() == KeyEvent.VK_UP){
                    camera.updateCameraPositionForward();
                    controlKeys=true;
                }else if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    camera.updateCameraPositionBackward();
                    controlKeys=true;
                }else if(e.getKeyCode() == KeyEvent.VK_Q){
                    camera.updateCameraPositionUp();
                    controlKeys=true;
                }else if(e.getKeyCode() == KeyEvent.VK_A){
                    camera.updateCameraPositionDown();
                    controlKeys=true;
                }else if(e.getKeyCode() == KeyEvent.VK_W){
                    camera.updateCentrePositionUp();
                    controlKeys=true;
                }else if(e.getKeyCode() == KeyEvent.VK_S){
                    camera.updateCentrePositionDown();
                    controlKeys=true;
                }

            }

            @Override
            public void keyTyped(KeyEvent e) {
                //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //To change body of generated methods, choose Tools | Templates.
            }
        });
        panelBotonesRender.add(stopRenderButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        renderInfoPanel.add(panelBotonesRender, gridBagConstraints);

        rightSidePanel.add(renderInfoPanel);

        getContentPane().add(rightSidePanel);

        menuFile.setText("Archivo");

        menuOpenScene.setText("Cargar Escena");
        menuOpenScene.setIconTextGap(0);
        menuOpenScene.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuOpenSceneActionPerformed(evt);
            }
        });
        menuFile.add(menuOpenScene);

        menuReOpenScene.setText("Recargar Escena");
        menuReOpenScene.setIconTextGap(0);
        menuReOpenScene.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuReOpenSceneActionPerformed(evt);
            }
        });
        menuFile.add(menuReOpenScene);

        menuBar.add(menuFile);

        menuHelp.setText("Ayuda");

        menuInformacion.setText("Informacion");

        menuInformacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuInformacionActionPerformed(evt);
            }
        });
        menuHelp.add(menuInformacion);

        menuBar.add(menuHelp);

        setJMenuBar(menuBar);

        getAccessibleContext().setAccessibleDescription("Java Path Tracer Render");

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Update the color of the background or enviroment (sky) panels
     */
    private void updateBackground(){
        background.setMainColor(Background.fromColorToColorValue(botonPanelColorPrimario.getBackground()));
        background.setSecondaryColor(Background.fromColorToColorValue(botonPanelColorSecundario.getBackground()));
        background.setMixed(checkBoxDegradado.isSelected());
    }

    /**
     * Listener event to control the secondary background button
     * It shows a new color chooser to select a color
     * @param evt
     */
    private void botonPanelColorSecundarioMouseClicked(java.awt.event.MouseEvent evt) {

        if(checkBoxDegradado.isSelected() && botonPanelColorSecundario.isEnabled() ){
            Color newColor = JColorChooser.showDialog(null, "Selecciona el color secundario", colorSecondarySaved);
            if (newColor!=null){
                colorSecondarySaved =newColor;
                botonPanelColorSecundario.setBackground(newColor);
            }
        }
        updateBackground();
    }

    /**
     * Listener event to control the primary background button
     * It shows a new color chooser to select a color
     * @param evt
     */
    private void botonPanelColorPrimarioMouseClicked(java.awt.event.MouseEvent evt) {
        if(botonPanelColorPrimario.isEnabled()){
            Color newColor = JColorChooser.showDialog(null, "Selecciona el color principal", colorPrimarySaved);
            if (newColor!=null){
                colorPrimarySaved=newColor;
                botonPanelColorPrimario.setBackground(newColor);
            }
        }
        updateBackground();
    }

    /**
     * Listener event to control any changes in the selection of secondary button wanted or not
     * @param evt
     */
    private void checkBoxDegradadoItemStateChanged(java.awt.event.ItemEvent evt) {
        if(evt.getStateChange()==1){
            labelColorSecundario.setEnabled(true);
            botonPanelColorSecundario.setEnabled(true);
            botonPanelColorSecundario.setBackground(colorSecondarySaved);
            
        }else if(evt.getStateChange()==2){
            colorSecondarySaved =botonPanelColorSecundario.getBackground();
            Color color=new Color(200,200,200);
            labelColorSecundario.setEnabled(false);
            botonPanelColorSecundario.setBackground(color);
        }
        updateBackground();
    }

    /**
     * Listener event to control the click on the image save button to select folder
     * It create a new dialog to select the folder
     * @param evt
     */
    private void buttonOpenFolderMouseClicked(java.awt.event.MouseEvent evt) {
        if(buttonOpenFolder.isEnabled()){
            JFileChooser chooser;
            chooser = new JFileChooser(); 
            chooser.setCurrentDirectory(new File(System.getProperty("user.home")+"/Documents"));
            chooser.setDialogTitle("Seleccione directorio de destino");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            //
            // disable the "All files" option.
            //
            chooser.setAcceptAllFileFilterUsed(false);
            //    
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
                File selectedFile = chooser.getSelectedFile();
                textRouteRenders.setText(selectedFile.getAbsolutePath());
            }
        }    
    }

    /**
     * Listener event to control the click on the render button
     *
     * @param evt
     */
    private void renderButtonMouseClicked(java.awt.event.MouseEvent evt) {
        if(listenerRenderButtonActive){
            pass=0;//Initialise the number of passes to 0
            numeroPases.setText("Numero de pases:");

            updateProgressBar=true;//We set the progress bar to be updateble
            updateRenderTime();//Create a new timer to control the time since you started the render

            listenerRenderButtonActive=false;//Deactivate the render button listener
            renderButton.setEnabled(false);//Deactivate the render button

            listenerStopButtonActive=true;//Activate the render button listener
            stopRenderButton.setEnabled(true);//Activate the stop render button

            setPanelEnabled(optionsPanel,false);//Disable all the option panel controls
            menuOpenScene.setEnabled(false);//Disable all the menu options
            menuReOpenScene.setEnabled(false);//Disable all the menu options

            /**
             * Update the progressbar
             * status 1 to start the progress bar
             */
            updateProgressBar(1,0);//Update the

            renderPanel.removeAll();//remove the render panell prior to create a new one

            //Create a new buffered image an a render panel to show the new render
            render=new BufferedImage((int)spinnerAncho.getValue(),(int)spinnerAlto.getValue(),BufferedImage.TYPE_INT_RGB);
            renderPanel.add(new ZoomingPanel(render));

            //Obtain camera and pass it to pathTracer
            camera= sceneLoader.getCamera(((double)(int)spinnerAncho.getValue()/(double)(int)spinnerAlto.getValue()));

            //We start a new object pathTracer which create a new thread to start the render
            pathTracer = new PathTracer((int)spinnerAncho.getValue(), (int)spinnerAlto.getValue(),
                    checkBoxNumPases.isSelected(), (int)spinnerNumPases.getValue(), (int)spinnerNumRebotes.getValue(),
                    (double)spinnerGamma.getValue(), render,this,background, sceneLoader, camera);
            Thread thread = new Thread(pathTracer){};
            thread.start();
        }

    }

    private void stopRenderButtonMouseClicked(java.awt.event.MouseEvent evt) {
        if(listenerStopButtonActive){

            timer.cancel();//Stop the timer


            listenerRenderButtonActive=true; //Set the render button listener active
            renderButton.setEnabled(true); //Set the render button active

            listenerStopButtonActive=false; //Set the stop render button listener deactivated
            stopRenderButton.setEnabled(false);//Set the stop render button deactivated
            menuOpenScene.setEnabled(true); //Activate the menu button
            menuReOpenScene.setEnabled(true);//Activate the menu button
            setPanelEnabled(optionsPanel,true); //Activate all the menu options

            /**
             * Update the progressbar
             * status 0 to stop the progress Var
             */
            updateProgressBar(0,0);

            //Pass paremeter to pathTracer to stop the Thread creations
            pathTracer.setActiveThread(false);
        }
    }

    /**
     * Button listener event that control to show or hide the options panel
     * @param evt
     */
    private void buttonSettingsMouseClicked(java.awt.event.MouseEvent evt) {
        if(flagButtonSettigns){
            flagButtonSettigns=false;
            optionsPanel.setVisible(false);
        }else{
            flagButtonSettigns=true;
            optionsPanel.setVisible(true);
        }
    }

    /**
     * Set the color to the button setting to create the effect of button on top
     * @param evt
     */
    private void buttonSettingsMouseEntered(java.awt.event.MouseEvent evt) {
        buttonSettings.setBackground(new Color(169,212,242));
    }

    /**
     * Set the color to the button setting to create the effect of button on top
     * @param evt
     */
    private void buttonSettingsMouseExited(java.awt.event.MouseEvent evt) {
        if(optionsPanel.isVisible()){
            buttonSettings.setBackground(new Color(51,150,246));
        }else{
            buttonSettings.setBackground(leftSidePanel.getBackground());
        }
    }

    /**
     * Event listener to the checkbox to allow the render to be unlimited passes or as per the spinner
     * @param evt
     */
    private void checkBoxNumPasesItemStateChanged(java.awt.event.ItemEvent evt) {
        if(evt.getStateChange()==1){            
            spinnerNumPases.setEnabled(false);
            
        }else if(evt.getStateChange()==2){
            spinnerNumPases.setEnabled(true);
        }
        
    }

    /**
     * Event listener to control the selection of the combobox for the aspect ratio
     * @param evt
     */
    private void comboAspectoItemStateChanged(java.awt.event.ItemEvent evt) {
        if (evt.SELECTED == evt.getStateChange()) {
            updateConfiguracionRenderPanelValues(0);
        }
    }

    /**
     * Event listener to control the spinner Height
     * @param evt
     */
    private void spinnerAltoStateChanged(javax.swing.event.ChangeEvent evt) {
        if(flagEnableSpinnerHeigthListener){
            updateConfiguracionRenderPanelValues(2);
        }
    }
    /**
     * Event listener to control the spinner Width
     * @param evt
     */
    private void spinnerAnchoStateChanged(javax.swing.event.ChangeEvent evt) {
        if(flagEnableSpinnerWidthListener){
            updateConfiguracionRenderPanelValues(1);
        }
    }

    /**
     * Event listener to control the click on the button open new scene
     * Create a file chooser that allow you to select the file to be opened
     * @param evt
     */
    private void menuOpenSceneActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser chooser;
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(sceneFilePath));
        chooser.setDialogTitle("Seleccione archivo");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPT RENDER FILES", "xml");
        chooser.setFileFilter(filter);
        
        chooser.setAcceptAllFileFilterUsed(false);
        
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            sceneFilePath =selectedFile.getAbsolutePath();

            sceneLoader=new SceneLoader(sceneFilePath); //We create a new scene based on the path passed

            switch (sceneLoader.getFlagControlState()){
                case 1:
                    JOptionPane.showMessageDialog(new JFrame(), "La escena ha sido cargada con exito", "Cargar escena",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 2:
                    JOptionPane.showMessageDialog(new JFrame(), "No se pudo cargar la escena", "Cargar escena",
                            JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Event listener method to reload the sceneFile
     * Just in case any change into the xml has been done
     * @param evt
     */
    private void menuReOpenSceneActionPerformed(java.awt.event.ActionEvent evt) {
            sceneLoader=new SceneLoader(sceneFilePath);
    }

    /**
     * Event listener method to show the dialog with the details of the author, date, project, etc
     * @param evt
     */
    private void menuInformacionActionPerformed(java.awt.event.ActionEvent evt) {
        dialogDetalles.setVisible(true);
    }

    /**
     * Event listener method to control the click of the button save render
     * @param evt
     */
    private void buttonSaveRenderActionPerformed(java.awt.event.ActionEvent evt) {
        saveImage();
    }

    /**
     * Method that control the panel Configuration Render panel
     * It interact with the two spinners and the combobox
     * @param item
     */
    private void updateConfiguracionRenderPanelValues(int item){
        int ratioW=1;
        int ratioH=1;
        flagEnableSpinnerHeigthListener=false; //Deactivate the listeners
        flagEnableSpinnerWidthListener=false;//Deactivate the listeners
        
        switch(comboAspecto.getSelectedIndex()) {
            case 1:
                ratioW=16;
                ratioH=9;                
                break;
            case 2:
                ratioW=16;
                ratioH=10; 
                break;
            case 3:
                ratioW=4;
                ratioH=3;                 
                break;
            case 4:
                ratioW=3;
                ratioH=2;                 
                break;
            case 5:
                ratioW=1;
                ratioH=1; 
                break;
                default:
            }
        
        if(comboAspecto.getSelectedIndex()==0){
            updateLabelRatio();
            labelRatio.setVisible(true);
        }else{
            labelRatio.setVisible(false);
            
            switch(item) {
                case 0:    
                    int width= (int) spinnerAncho.getValue();
        
                    int calc= Math.round((width*ratioH)/ratioW);
                    if(calc<100){
                        spinnerAlto.setValue(100);
                        spinnerAncho.setValue((100*ratioW)/ratioH);
                    }else{
                        spinnerAlto.setValue(calc);                                
                    }
                    break;
                case 1:
                    width= (int) spinnerAncho.getValue();
        
                    calc= Math.round((width*ratioH)/ratioW);
                    if(calc<100){
                        spinnerAlto.setValue(100);
                        spinnerAncho.setValue((100*ratioW)/ratioH);
                    }else{
                        spinnerAlto.setValue(calc);                                
                    }
                    break;
                case 2:
                    int height=(int) spinnerAlto.getValue();        
                    calc= Math.round((height*ratioW)/ratioH);
                    spinnerAncho.setValue(calc);
                    break;
                default:
            }
        }
        tamanoRender.setText("Tamaño de render: " + spinnerAncho.getValue() + " x " + spinnerAlto.getValue() + " px");
        flagEnableSpinnerHeigthListener=true;//activate the listeners
        flagEnableSpinnerWidthListener=true;//activate the listeners
    }

    /**
     * Set the label ratio as per the values of the spinners
     */
    private void updateLabelRatio(){
        Double ratio=(double)((Integer)spinnerAncho.getValue())/(double)((Integer)spinnerAlto.getValue());
        labelRatio.setText("Ratio= "+String.format("%1$,.4f", ratio));
    }

    /**
     * Timer method to control the render time
     */
    private void updateRenderTime(){
        timer = new Timer();
        startTime= System.currentTimeMillis();
        
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                renderPanel.repaint();//Every second repaint the render panel

                long partialTime = System.currentTimeMillis();
                long millis = partialTime - startTime;
                
                String time =String.format("%dh:%dm:%ds", 
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -  
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), 
                TimeUnit.MILLISECONDS.toSeconds(millis) - 
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))); 
                
                renderTime.setText("Tiempo de render:  " + time);
            }
        }, 0, 100);

    }

    /**
     * Method that run through all the panel and its childs to enable or disable them
     * @param panel
     * @param isEnabled
     */
    void setPanelEnabled(JPanel panel, Boolean isEnabled) {
        panel.setEnabled(isEnabled);

        Component[] components = panel.getComponents();

        for (Component component : components) {
            if (component instanceof JPanel) {
                setPanelEnabled((JPanel) component, isEnabled);
            }
            component.setEnabled(isEnabled);
        }
        
        if(checkBoxNumPases.isSelected() && isEnabled){
            spinnerNumPases.setEnabled(false);
        }
    }

    /**
     * Method to update the number of passes from the callback of pathTracer
     */
    public void updatePasses(int pass){
        this.pass=pass;
        numeroPases.setText("Numero de pases: " + pass);
        updateProgressBar(2,pass);
    }

    /**
     * Method to control the progress bar
     * @param status Current status, rendering,stop,etc.
     * @param renderPassValue Data to update bar
     */
    private void updateProgressBar(int status,int renderPassValue){

        if(updateProgressBar){
            DecimalFormat df2 = new DecimalFormat("#.##");

            switch (status){
                case 0://UPDATE BAR WHEN STOP BUTTON IS CLICKED
                    updateProgressBar=false;
                    if(progressBar.isIndeterminate()){
                        progressBar.setIndeterminate(false);
                        progressBar.setMinimum(0);
                        progressBar.setMaximum(100);
                        progressBar.setValue(100);
                        progressBar.setString("Render parado");
                    }else{
                        if (renderPassValue==progressBar.getMaximum()){
                            progressBar.setString("Render completado");
                        }else{
                            progressBar.setString("Render parado al " + df2.format((progressBar.getPercentComplete()*100)) + "%");
                        }
                    }
                    break;

                case 1://UPDATE BAR WHEN RENDER BUTTON IS PUSHED
                    if (checkBoxNumPases.isSelected()){
                        progressBar.setString("Render progresivo seleccionado");
                        progressBar.setIndeterminate(true);
                        progressBar.setMaximum(Integer.MAX_VALUE);
                    }else{
                        progressBar.setIndeterminate(false);
                        progressBar.setMinimum(0);
                        progressBar.setMaximum((int) spinnerNumPases.getValue());
                        progressBar.setValue(renderPassValue);
                        progressBar.setString("Rendering..." + df2.format((progressBar.getPercentComplete()*100)) + "%");
                    }
                    break;

                case 2://UPDATE BAR DURING RENDER
                    if(!progressBar.isIndeterminate()){
                        progressBar.setMinimum(0);
                        progressBar.setMaximum((int) spinnerNumPases.getValue());
                        progressBar.setValue(renderPassValue);
                        progressBar.setString("Rendering..." + df2.format((progressBar.getPercentComplete()*100)) + "%");
                    }
                    if(renderPassValue==progressBar.getMaximum()){
                        progressBar.setString("Render completado");
                        timer.cancel();

                        listenerRenderButtonActive=true;
                        renderButton.setEnabled(true);

                        listenerStopButtonActive=false;
                        stopRenderButton.setEnabled(false);

                        menuOpenScene.setEnabled(true);
                        menuReOpenScene.setEnabled(true);
                        setPanelEnabled(optionsPanel,true);
                        pathTracer.setActiveThread(false);

                        //When the render complete it saves the image
                        if(checkBoxSalvarImagen.isSelected()) {
                            saveImage();
                        }
                    };
                    break;
            }
        }
    }

    /**
     * Method to save the image rendered
     */
    private void saveImage(){

        String text = renderTime.getText() + "   ||   " + numeroPases.getText();

        //Print text on image
        Graphics graphics = render.getGraphics();

        graphics.setColor(new Color(127,127,127,55));
        graphics.fillRect(0,0,render.getWidth(), 15);

        graphics.setColor(Color.white);
        graphics.setFont(new Font("Arial", Font.PLAIN, 10));
        graphics.drawString(text, 3, 10);


        Date date = new Date() ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
        File outputfile;
        Path path = Paths.get(textRouteRenders.getText());
        if(Files.exists(path)){
            outputfile = new File(textRouteRenders.getText() + "/RenderJPTr_" + dateFormat.format(date) + ".png");
        }else{
            File folder= new File(textRouteRenders.getText());
            folder.mkdirs();
            outputfile = new File(textRouteRenders.getText() + "/RenderJPTr_" + dateFormat.format(date) + ".png");
        }
        try {
            ImageIO.write(render, "png", outputfile);

            JOptionPane.showMessageDialog(new JFrame(), "Imagen guardada en el directorio seleccionado", "Salvar imagen",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(new JFrame(), "No se pudo guardar la imagen", "Salvar imagen",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        FlatLightLaf.install();
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        
        try {
            UIManager.setLookAndFeel( new FlatArcOrangeIJTheme() );
        } catch( UnsupportedLookAndFeelException ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        
        UIManager.put( "Button.arc", 10 );
        UIManager.put( "Component.arc", 10 );
        UIManager.put( "ProgressBar.arc", 10 );
        UIManager.put( "TextComponent.arc", 10 );     
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainGUI().setVisible(true);
            }
        });
    }



    // Variables for control
    private boolean flagEnableSpinnerWidthListener;
    private boolean flagEnableSpinnerHeigthListener;
    private boolean flagButtonSettigns;  
    
    Color colorSecondarySaved = Color.ORANGE;//Secondary color saved
    Color colorPrimarySaved = new Color(153,190,255);//Primary color for background
    Background background=new Background();//Backgroun object to be passed to the pathTracer
    
    long startTime;//Flag to control the render time
    Timer timer; //Render time timer

    Camera camera;
    BufferedImage render; //Image to be render in
    PathTracer pathTracer; //Thread of render engine
    public boolean controlKeys=false;

    int pass; //Number of passes
    boolean updateProgressBar=true;
    boolean listenerRenderButtonActive=true;
    boolean listenerStopButtonActive=false;

    //Parameter for the scene loader
    String sceneFilePath =System.getProperty("user.home")+"/Documents";
    SceneLoader sceneLoader=new SceneLoader("startScene");

    //GENERATED GUI OBJECTS
    private javax.swing.JSeparator Separator;
    private JPanel botonPanelColorPrimario;
    private JPanel botonPanelColorSecundario;
    private javax.swing.JButton buttonSaveRender;
    private javax.swing.JButton buttonOpenFolder;
    private JPanel buttonSettings;
    private javax.swing.JCheckBox checkBoxDegradado;
    private javax.swing.JCheckBox checkBoxNumPases;
    private javax.swing.JCheckBox checkBoxSalvarImagen;
    private javax.swing.JComboBox<String> comboAspecto;
    private javax.swing.JLabel commentAlumno;
    private javax.swing.JLabel commentCurso;
    private javax.swing.JLabel commentDNI;
    private javax.swing.JLabel commentProyecto;
    private JPanel confEntornoPanel;
    private JPanel confRenderPanel;
    private JPanel confRenderPanel1;
    private javax.swing.JDialog dialogDetalles;
    private javax.swing.JLabel labelAlto;
    private javax.swing.JLabel labelAncho;
    private javax.swing.JLabel labelColorPrincipal;
    private javax.swing.JLabel labelColorSecundario;
    private javax.swing.JLabel labelDirectionRender;
    private javax.swing.JLabel labelFormatoImagen;
    private javax.swing.JLabel labelGamma;
    private javax.swing.JLabel labelNumeroPases;
    private javax.swing.JLabel labelNumeroRebotes;
    private javax.swing.JLabel labelRatio;
    private javax.swing.JLabel labelTamanoSalida;
    private javax.swing.JLabel labelValoresCalculo;
    private JPanel leftSidePanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenuItem menuInformacion;
    private javax.swing.JMenuItem menuOpenScene;
    private javax.swing.JMenuItem menuReOpenScene;
    private javax.swing.JLabel numeroPases;
    private JPanel optionsPanel;
    private JPanel panelBotonesRender;
    private JPanel panelDialog;
    private JPanel panelRenderDetails;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JButton renderButton;
    private JPanel renderInfoPanel;
    private JPanel renderPanel;
    private javax.swing.JLabel renderTime;
    private JPanel rightSidePanel;
    private JPanel salidaImagenPanel;
    private javax.swing.JLabel separador1;
    private javax.swing.JLabel separador2;
    private javax.swing.JSpinner spinnerAlto;
    private javax.swing.JSpinner spinnerAncho;
    private javax.swing.JSpinner spinnerGamma;
    private javax.swing.JSpinner spinnerNumPases;
    private javax.swing.JSpinner spinnerNumRebotes;
    private javax.swing.JButton stopRenderButton;
    private javax.swing.JLabel tamanoRender;
    private javax.swing.JTextField textRouteRenders;
    private javax.swing.JLabel titleAlumno;
    private javax.swing.JLabel titleCurso;
    private javax.swing.JLabel titleOpciones;
    private javax.swing.JLabel titleProyecto;
    private javax.swing.JLabel titleSideBar;
}
