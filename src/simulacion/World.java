package simulacion;

import java.awt.Font;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.jfree.chart.ChartPanel;

import ecuaciones.PanelM;
import java.util.HashMap;
/**
 * @author simulacion
 */
public class World extends javax.swing.JFrame {
    
    private int contador;
    private JTable tablaResultados;
    private JTable tablaResultadosDetalle;
    private JTable tablaReporte;
    private DefaultTableModel dtm;
    private DefaultTableModel dtmRep;
    
    private HashMap<String,Integer> economicos;
    private HashMap<String,Integer> negocios;
    private HashMap<String,Integer> ejecutivos;
    private HashMap<String,Integer> premium;
    
    private HashMap<String,Integer> precios;
    
    private ImageIcon imagen=new ImageIcon(getClass().getResource("/img/iconhotel.gif"));
    private ManagerM mm;
    private Hora h;
    public World() {
      economicos=new HashMap<String, Integer>();
      negocios=new HashMap<String, Integer>();
      ejecutivos=new HashMap<String, Integer>();
      premium=new HashMap<String, Integer>();
      precios=new HashMap<String, Integer>();
      contador=0;
      setTitle("TALLER DE SIMULACION");
      initComponents();
      setings.setMaximizable(true);
      setings.setTitle("CONFIGURACIONES DE LA SIMULACION");
      this.setLocation(100, 0);
      this.setSize(950, 710);
      this.setResizable(false);
      btnIniciar.setEnabled(true);
      btnPausar.setEnabled(false);
      btnTerminar.setEnabled(false);
      this.setIconImage(new ImageIcon(getClass().getResource("/img/iconhotel.gif")).getImage());
    }
    
    public void crearTabla(){
        String titulos[]={"DIA","CANTIDAD TOTAL","cantidad unitaria","TIPO","ESTADO"};
        String contenido[][]=null;
        
        dtm=new DefaultTableModel(contenido, titulos);
        tablaResultados=new JTable(dtm);
        tablaResultados.setShowVerticalLines(false);
        tablaResultados.setFont(new Font("arial", Font.PLAIN, 10));
        tablaResultadosDetalle=new JTable(dtm);
        tablaResultadosDetalle.setShowVerticalLines(false);
        tablaResultadosDetalle.setFont(new Font("arial", Font.PLAIN, 10));
        JScrollPane jspd=new JScrollPane(tablaResultadosDetalle);
        jspd.setBounds(0, 0, PanelTablaDetalle.getWidth(), PanelTablaDetalle.getHeight()-50);
        
        JScrollPane jsp=new JScrollPane(tablaResultados);
        jsp.setBounds(0, 0, panelTabla.getWidth(), panelTabla.getHeight()-50);
        panelTabla.add(jsp);
        PanelTablaDetalle.add(jspd);
        
    }
    private int cantidadReal(int num1,int num2){
        int signo=1;
        if (num2>0){
            return num1;
        }
        else {
            return num1+num2;
        }
    }
    private void mostrarReporte(){
        panelResultado.removeAll();
        String titulos[]={"DIA","TIPO","CANTIDAD OCUPADO","CANTIDAD DESOCUPADO","COSTO HABITACION","TOTAL RECAUDADO"};
        String vacio[]={"","","","","",""};
        String banner[]={"","","DETALLE DE","RESULTADOS","",""};
        DefaultTableColumnModel dtcm=new DefaultTableColumnModel();
        JTableHeader th=new JTableHeader(dtcm);
        Object contenidoReporte[][]=null;
        ArrayList<Integer> dias=cuantosDias();
        int cantidadLlegadosEcon=0;
        int cantidadLlegadosNego=0;
        int cantidadLlegadoEjecu=0;
        int cantidadLlegadoPremi=0;
        
        int montoRecaudadoEconomico=0;
        int montoRecaudadoNegocio=0;
        int montoRecaudadoEjecutivo=0;
        int montoRecaudadoPremium=0;
        
        int cantidadLlegadoTotal=tablaResultadosDetalle.getRowCount();
        
        dtmRep=new DefaultTableModel(contenidoReporte, titulos);
        tablaReporte=new JTable(dtmRep);
        tablaReporte.setTableHeader(th);
        tablaReporte.setRowHeight(0, 50);
        tablaReporte.setShowVerticalLines(false);
        JScrollPane jspReporte=new JScrollPane(tablaReporte);
        jspReporte.setBounds(0,0,panelResultado.getWidth(),panelResultado.getHeight()-50);
        
        panelResultado.add(jspReporte);
        dtmRep.addRow(vacio);
        dtmRep.addRow(banner);
        dtmRep.addRow(vacio);
        dtmRep.addRow(titulos);
        for (int i = 0; i < dias.size(); i++){
            cantidadLlegadosEcon=dameCantidadXTipo((i+1), "ECONOMICO");
            int cantDesEcon=(200+Integer.parseInt(txtEconomico.getText()))-(cantidadLlegadosEcon);
            int costoHabEco=78-(Integer.parseInt(String.valueOf(jcbEconomico.getSelectedItem())));
            montoRecaudadoEconomico=montoRecaudadoEconomico+(cantidadLlegadosEcon*costoHabEco);
            economicos.put("Dia"+(i+1), cantidadReal(cantidadLlegadosEcon,cantDesEcon));
            String se[]={String.valueOf(dias.get(i)),"ECONOMICO",String.valueOf((cantidadReal(cantidadLlegadosEcon,cantDesEcon))),String.valueOf(cantDesEcon),String.valueOf(costoHabEco),String.valueOf(getFormatoSubTotal(((cantidadReal(cantidadLlegadosEcon,cantDesEcon))*costoHabEco)))};
            dtmRep.addRow(se);
            
            cantidadLlegadosNego=dameCantidadXTipo((i+1), "NEGOCIOS");
            negocios.put("Dia"+(i+1), cantidadLlegadosNego);
            int cantDesNego=(200+Integer.parseInt(txtNegocio.getText()))-(cantidadLlegadosNego);
            int costoHabNeg=97-(Integer.parseInt(String.valueOf(jcbNegocio.getSelectedItem())));
            montoRecaudadoNegocio=montoRecaudadoNegocio+(cantidadLlegadosNego*costoHabNeg);
            String sn[]={String.valueOf(dias.get(i)),"NEGOCIOS",String.valueOf(cantidadLlegadosNego),String.valueOf(cantDesNego),String.valueOf(costoHabNeg),String.valueOf(getFormatoSubTotal((cantidadLlegadosNego*costoHabNeg)))};
            dtmRep.addRow(sn);
            
            cantidadLlegadoEjecu=dameCantidadXTipo((i+1), "EJECUTIVO");
            ejecutivos.put("Dia"+(i+1), cantidadLlegadoEjecu);
            int cantDesEjec=(200+Integer.parseInt(txtEjecutivo.getText()))-(cantidadLlegadosNego);
            int costoHabEje=120-(Integer.parseInt(String.valueOf(jcbEjecutivo.getSelectedItem())));
            montoRecaudadoEjecutivo=montoRecaudadoEjecutivo+(cantidadLlegadoEjecu*costoHabEje);
            String sej[]={String.valueOf(dias.get(i)),"EJECUTIVO",String.valueOf(cantidadLlegadoEjecu),String.valueOf(cantDesEjec),String.valueOf(costoHabEje),String.valueOf(getFormatoSubTotal((cantidadLlegadoEjecu*costoHabEje)))};
            dtmRep.addRow(sej);
            
            cantidadLlegadoPremi=dameCantidadXTipo((i+1), "PREMIUM");
            premium.put("Dia"+(i+1), cantidadLlegadoPremi);
            int cantDesPrem=(100+Integer.parseInt(txtPremium.getText()))-(cantidadLlegadoPremi);
            int costoHabPrem=180+(Integer.parseInt(String.valueOf(jcbPremium.getSelectedItem())));
            montoRecaudadoPremium=montoRecaudadoPremium+(cantidadLlegadoPremi*costoHabPrem);
            String sp[]={String.valueOf(dias.get(i)),"PREMIUM",String.valueOf(cantidadLlegadoPremi),String.valueOf(cantDesPrem),String.valueOf(costoHabPrem),String.valueOf(getFormatoSubTotal((cantidadLlegadoPremi*costoHabPrem)))};
            dtmRep.addRow(sp);
            
            int montoSubTotal=montoRecaudadoEconomico+montoRecaudadoNegocio+montoRecaudadoEjecutivo+montoRecaudadoPremium;
            String subtotal[]={"","","","","MONTO SUBTOTAL =>",String.valueOf(montoSubTotal)};
            dtmRep.addRow(subtotal);
            String spacio[]={"","","",""};
            dtmRep.addRow(spacio);
        }
        int montoTotal=montoRecaudadoEconomico+montoRecaudadoNegocio+montoRecaudadoEjecutivo+montoRecaudadoPremium;
        String resumen[]={"","","","","MONTO TOTAL =>",String.valueOf(getFormatoCantidadTotal(montoTotal))};
        dtmRep.addRow(resumen);
        tablaReporte.repaint();
        
        precios.put("ECONOMICO", (78-Integer.parseInt(String.valueOf(jcbEconomico.getSelectedItem()))));
        precios.put("NEGOCIO", (97-Integer.parseInt(String.valueOf(jcbEconomico.getSelectedItem()))));
        precios.put("EJECUTIVO", (120-Integer.parseInt(String.valueOf(jcbEconomico.getSelectedItem()))));
        precios.put("PREMIUM", (180+Integer.parseInt(String.valueOf(jcbEconomico.getSelectedItem()))));
        
        Graficos graficos=new Graficos(economicos, negocios, ejecutivos, premium,precios);
        
        ChartPanel panelBB=graficos.getGraficoBarras();
        panelBB.setBounds(0, 0, PanelBarra.getWidth(), PanelBarra.getHeight()-50);
        PanelBarra.add(panelBB);
        PanelBarra.repaint();
        
        ChartPanel panelTT=graficos.getGraficoPie3D();
        panelTT.setBounds(0, 0, PanelPieChart.getWidth(), PanelPieChart.getHeight()-50);
        PanelPieChart.add(panelTT);
        PanelPieChart.repaint();
        
        ChartPanel panelXY=graficos.getXYGraphics();
        panelXY.setBounds(0, 0, PanelXYSeries.getWidth(), PanelXYSeries.getHeight()-50);
        PanelXYSeries.add(panelXY);
        PanelXYSeries.repaint();
    }
    private String getFormatoSubTotal(int cant){
        String res="<html><style type=\"text/css\">body{font-weight:bold;font-size:12px;}.subtotal{color:#0443BE;}</style><span class=\"subtotal\">"+cant+"</span></html>";
        return res;
    }
    private String getFormatoCantidadTotal(int cant){
        String res="<html><style type=\"text/css\">body{ font-weight:bold;font-size:12px;background-color: #cecece;} .verde{ color:#009900;}</style><span class=\"verde\">"+cant+"</span></html>";
        return res;
    }
    private int dameCantidadXTipo(int dia,String tipo){
        int cantidad=0;
        for (int i = 0; i <tablaResultadosDetalle.getRowCount(); i++) {
            if ((Integer.parseInt(String.valueOf(tablaResultadosDetalle.getValueAt(i, 0)))==dia)&&((String.valueOf(tablaResultadosDetalle.getValueAt(i, 3)).equals(tipo)))) {
                cantidad=cantidad+1;
            }
        }
        return cantidad;
    }
    private ArrayList<Integer> cuantosDias(){
        ArrayList<Integer> diasSimulado=new ArrayList<Integer>();
        int tamanio=tablaResultadosDetalle.getRowCount();
        int obj=Integer.parseInt(String.valueOf(tablaResultadosDetalle.getValueAt(0, 0)));
        System.out.println("DIA ES="+obj);
        diasSimulado.add(obj);
        for (int i = 0; i < tamanio; i++) {
            if (existe(diasSimulado, Integer.parseInt(String.valueOf(tablaResultadosDetalle.getValueAt(i, 0))))) {
                diasSimulado.add(Integer.parseInt(String.valueOf(tablaResultadosDetalle.getValueAt(i, 0))));
                obj=Integer.parseInt(String.valueOf(tablaResultadosDetalle.getValueAt(i, 0)));
            }
        }
        System.out.println("dias ES ="+diasSimulado);
        return diasSimulado;
    }
    private boolean existe(ArrayList<Integer> lista,int obj){
        boolean res=true;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i)==obj) {
                res=false;
                break;
            }
        }
        return res;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtpCentral = new javax.swing.JTabbedPane();
        stage = new StageM();
        setings = new javax.swing.JInternalFrame();
        PanelConfig = new PanelM();
        PanelHabitaciones = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtEconomico = new javax.swing.JFormattedTextField();
        txtNegocio = new javax.swing.JFormattedTextField();
        txtPremium = new javax.swing.JFormattedTextField();
        txtEjecutivo = new javax.swing.JFormattedTextField();
        jcbEconomico = new javax.swing.JComboBox();
        jcbNegocio = new javax.swing.JComboBox();
        jcbEjecutivo = new javax.swing.JComboBox();
        jcbPremium = new javax.swing.JComboBox();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jsVelocidad = new javax.swing.JSlider();
        PanelStart = new javax.swing.JPanel();
        btnIniciar = new javax.swing.JButton();
        btnPausar = new javax.swing.JButton();
        btnTerminar = new javax.swing.JButton();
        jcbDias = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        horario = new javax.swing.JLabel();
        lblHabitacionesEconomica1 = new javax.swing.JLabel();
        lblHabitacionesEconomica3 = new javax.swing.JLabel();
        lblHabitacionesEconomica4 = new javax.swing.JLabel();
        lblHabitacionesEconomica5 = new javax.swing.JLabel();
        lblCantidadPremium = new javax.swing.JLabel();
        lblCantidadEconomico = new javax.swing.JLabel();
        lblCantidadNegocio = new javax.swing.JLabel();
        lblCantidadEjecutivo = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();
        panelTabla = new javax.swing.JPanel();
        PanelTablaDetalle = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        PanelPieChart = new javax.swing.JPanel();
        PanelXYSeries = new javax.swing.JPanel();
        PanelBarra = new javax.swing.JPanel();
        panelResultado = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jmiGraficoBarra = new javax.swing.JMenuItem();
        jmiGrafico3DPie = new javax.swing.JMenuItem();
        jmiXYSeries = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        stage.setBackground(new java.awt.Color(197, 182, 168));
        stage.setLayout(null);

        setings.setVisible(true);

        PanelConfig.setBackground(new java.awt.Color(102, 102, 102));

        PanelHabitaciones.setBorder(javax.swing.BorderFactory.createTitledBorder("CANTIDADES DE INCREMENTO DE HABITACIONES"));
        PanelHabitaciones.setLayout(null);

        jLabel5.setText("ECONOMICA");
        PanelHabitaciones.add(jLabel5);
        jLabel5.setBounds(10, 60, 62, 20);

        jLabel6.setText("NEGOCIOS");
        PanelHabitaciones.add(jLabel6);
        jLabel6.setBounds(10, 90, 62, 20);

        jLabel7.setText("EJECUTIVO");
        PanelHabitaciones.add(jLabel7);
        jLabel7.setBounds(10, 120, 62, 20);

        jLabel8.setText("PREMIUM");
        PanelHabitaciones.add(jLabel8);
        jLabel8.setBounds(10, 150, 62, 20);

        txtEconomico.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtEconomico.setText("0");
        txtEconomico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEconomicoKeyTyped(evt);
            }
        });
        PanelHabitaciones.add(txtEconomico);
        txtEconomico.setBounds(200, 60, 40, 20);

        txtNegocio.setText("0");
        txtNegocio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNegocioActionPerformed(evt);
            }
        });
        txtNegocio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNegocioKeyTyped(evt);
            }
        });
        PanelHabitaciones.add(txtNegocio);
        txtNegocio.setBounds(200, 90, 40, 20);

        txtPremium.setText("0");
        txtPremium.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPremiumKeyTyped(evt);
            }
        });
        PanelHabitaciones.add(txtPremium);
        txtPremium.setBounds(200, 150, 40, 20);

        txtEjecutivo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtEjecutivo.setText("0");
        txtEjecutivo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEjecutivoKeyTyped(evt);
            }
        });
        PanelHabitaciones.add(txtEjecutivo);
        txtEjecutivo.setBounds(200, 120, 40, 20);

        jcbEconomico.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "10", "20" }));
        jcbEconomico.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jcbEconomicoPropertyChange(evt);
            }
        });
        PanelHabitaciones.add(jcbEconomico);
        jcbEconomico.setBounds(330, 60, 50, 20);

        jcbNegocio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "7.5", "15" }));
        PanelHabitaciones.add(jcbNegocio);
        jcbNegocio.setBounds(330, 90, 50, 20);

        jcbEjecutivo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "2", "5" }));
        PanelHabitaciones.add(jcbEjecutivo);
        jcbEjecutivo.setBounds(330, 120, 50, 20);

        jcbPremium.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "5" }));
        PanelHabitaciones.add(jcbPremium);
        jcbPremium.setBounds(330, 150, 50, 20);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        PanelHabitaciones.add(jSeparator1);
        jSeparator1.setBounds(100, 20, 10, 150);

        jLabel1.setText("78  -");
        PanelHabitaciones.add(jLabel1);
        jLabel1.setBounds(270, 60, 40, 20);

        jLabel2.setText("97  -");
        PanelHabitaciones.add(jLabel2);
        jLabel2.setBounds(270, 90, 40, 20);

        jLabel3.setText("120  -");
        PanelHabitaciones.add(jLabel3);
        jLabel3.setBounds(270, 120, 40, 20);

        jLabel4.setText("180  +");
        PanelHabitaciones.add(jLabel4);
        jLabel4.setBounds(270, 150, 40, 20);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        PanelHabitaciones.add(jSeparator2);
        jSeparator2.setBounds(250, 20, 10, 150);

        jLabel9.setText("200  +");
        PanelHabitaciones.add(jLabel9);
        jLabel9.setBounds(130, 60, 50, 20);

        jLabel10.setText("200  +");
        PanelHabitaciones.add(jLabel10);
        jLabel10.setBounds(130, 90, 50, 20);

        jLabel11.setText("200  +");
        PanelHabitaciones.add(jLabel11);
        jLabel11.setBounds(130, 120, 50, 20);

        jLabel12.setText("100  +");
        PanelHabitaciones.add(jLabel12);
        jLabel12.setBounds(130, 150, 50, 20);

        jLabel13.setText("TIPO");
        PanelHabitaciones.add(jLabel13);
        jLabel13.setBounds(10, 20, 60, 20);
        PanelHabitaciones.add(jSeparator3);
        jSeparator3.setBounds(10, 50, 370, 10);

        jLabel14.setText("CANT. HABITACION");
        PanelHabitaciones.add(jLabel14);
        jLabel14.setBounds(110, 20, 130, 20);

        jLabel15.setText("COSTO HABITACION");
        PanelHabitaciones.add(jLabel15);
        jLabel15.setBounds(260, 20, 140, 20);

        jsVelocidad.setMajorTickSpacing(50);
        jsVelocidad.setMaximum(300);
        jsVelocidad.setMinimum(10);
        jsVelocidad.setMinorTickSpacing(50);
        jsVelocidad.setPaintLabels(true);
        jsVelocidad.setPaintTicks(true);
        jsVelocidad.setSnapToTicks(true);
        jsVelocidad.setValue(300);
        jsVelocidad.setBorder(javax.swing.BorderFactory.createTitledBorder("VELOCIDAD DE SIMULACION"));

        btnIniciar.setText("INICIAR");
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });

        btnPausar.setText("PAUSAR");
        btnPausar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPausarActionPerformed(evt);
            }
        });

        btnTerminar.setText("TERMINAR");
        btnTerminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTerminarActionPerformed(evt);
            }
        });

        jcbDias.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        jLabel16.setText("DIAS");

        javax.swing.GroupLayout PanelStartLayout = new javax.swing.GroupLayout(PanelStart);
        PanelStart.setLayout(PanelStartLayout);
        PanelStartLayout.setHorizontalGroup(
            PanelStartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelStartLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnIniciar)
                .addGap(18, 18, 18)
                .addComponent(btnPausar)
                .addGap(18, 18, 18)
                .addComponent(btnTerminar)
                .addGap(44, 44, 44)
                .addGroup(PanelStartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcbDias, 0, 73, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        PanelStartLayout.setVerticalGroup(
            PanelStartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelStartLayout.createSequentialGroup()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PanelStartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbDias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTerminar)
                    .addComponent(btnPausar)
                    .addComponent(btnIniciar))
                .addContainerGap())
        );

        javax.swing.GroupLayout PanelConfigLayout = new javax.swing.GroupLayout(PanelConfig);
        PanelConfig.setLayout(PanelConfigLayout);
        PanelConfigLayout.setHorizontalGroup(
            PanelConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jsVelocidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelHabitaciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        PanelConfigLayout.setVerticalGroup(
            PanelConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelConfigLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jsVelocidad, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelStart, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelHabitaciones, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout setingsLayout = new javax.swing.GroupLayout(setings.getContentPane());
        setings.getContentPane().setLayout(setingsLayout);
        setingsLayout.setHorizontalGroup(
            setingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelConfig, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        setingsLayout.setVerticalGroup(
            setingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelConfig, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        stage.add(setings);
        setings.setBounds(500, 10, 440, 380);
        try {
            setings.setIcon(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        stage.add(horario);
        horario.setBounds(10, 320, 216, 58);

        lblHabitacionesEconomica1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lblHabitacionesEconomica1.setForeground(new java.awt.Color(255, 255, 255));
        lblHabitacionesEconomica1.setText("<html>HABITACIONES<br> PREMIUM</html>");
        stage.add(lblHabitacionesEconomica1);
        lblHabitacionesEconomica1.setBounds(390, 10, 90, 30);

        lblHabitacionesEconomica3.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lblHabitacionesEconomica3.setForeground(new java.awt.Color(255, 255, 255));
        lblHabitacionesEconomica3.setText("<html>HABITACIONES<br> ECONOMICA</html>");
        stage.add(lblHabitacionesEconomica3);
        lblHabitacionesEconomica3.setBounds(100, 10, 90, 30);

        lblHabitacionesEconomica4.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lblHabitacionesEconomica4.setForeground(new java.awt.Color(255, 255, 255));
        lblHabitacionesEconomica4.setText("<html>HABITACIONES<br> NEGOCIO</html>");
        stage.add(lblHabitacionesEconomica4);
        lblHabitacionesEconomica4.setBounds(200, 10, 90, 30);

        lblHabitacionesEconomica5.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lblHabitacionesEconomica5.setForeground(new java.awt.Color(255, 255, 255));
        lblHabitacionesEconomica5.setText("<html>HABITACIONES<br>EJECUTIVO</html>");
        stage.add(lblHabitacionesEconomica5);
        lblHabitacionesEconomica5.setBounds(290, 10, 90, 30);

        lblCantidadPremium.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblCantidadPremium.setForeground(new java.awt.Color(255, 255, 255));
        lblCantidadPremium.setText("100");
        stage.add(lblCantidadPremium);
        lblCantidadPremium.setBounds(390, 40, 40, 14);

        lblCantidadEconomico.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblCantidadEconomico.setForeground(new java.awt.Color(255, 255, 255));
        lblCantidadEconomico.setText("200");
        stage.add(lblCantidadEconomico);
        lblCantidadEconomico.setBounds(100, 40, 40, 14);

        lblCantidadNegocio.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblCantidadNegocio.setForeground(new java.awt.Color(255, 255, 255));
        lblCantidadNegocio.setText("200");
        stage.add(lblCantidadNegocio);
        lblCantidadNegocio.setBounds(200, 40, 40, 14);

        lblCantidadEjecutivo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblCantidadEjecutivo.setForeground(new java.awt.Color(255, 255, 255));
        lblCantidadEjecutivo.setText("200");
        stage.add(lblCantidadEjecutivo);
        lblCantidadEjecutivo.setBounds(290, 40, 40, 14);

        panelTabla.setPreferredSize(new java.awt.Dimension(890, 150));
        panelTabla.setLayout(null);
        tabs.addTab("DETALLE DE LA SIMULACION", panelTabla);

        stage.add(tabs);
        tabs.setBounds(0, 370, 940, 280);

        jtpCentral.addTab("SIMULACION", stage);

        javax.swing.GroupLayout PanelTablaDetalleLayout = new javax.swing.GroupLayout(PanelTablaDetalle);
        PanelTablaDetalle.setLayout(PanelTablaDetalleLayout);
        PanelTablaDetalleLayout.setHorizontalGroup(
            PanelTablaDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 945, Short.MAX_VALUE)
        );
        PanelTablaDetalleLayout.setVerticalGroup(
            PanelTablaDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 652, Short.MAX_VALUE)
        );

        jtpCentral.addTab("DETALLE TABLA", PanelTablaDetalle);

        javax.swing.GroupLayout PanelPieChartLayout = new javax.swing.GroupLayout(PanelPieChart);
        PanelPieChart.setLayout(PanelPieChartLayout);
        PanelPieChartLayout.setHorizontalGroup(
            PanelPieChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 940, Short.MAX_VALUE)
        );
        PanelPieChartLayout.setVerticalGroup(
            PanelPieChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 624, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("GRAFICO Pie Chart", PanelPieChart);

        javax.swing.GroupLayout PanelXYSeriesLayout = new javax.swing.GroupLayout(PanelXYSeries);
        PanelXYSeries.setLayout(PanelXYSeriesLayout);
        PanelXYSeriesLayout.setHorizontalGroup(
            PanelXYSeriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 940, Short.MAX_VALUE)
        );
        PanelXYSeriesLayout.setVerticalGroup(
            PanelXYSeriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 624, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("GRAFICO XYSeries", PanelXYSeries);

        PanelBarra.setLayout(null);
        jTabbedPane1.addTab("GRAFICO Barra", PanelBarra);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        jtpCentral.addTab("GRAFICOS", jPanel4);

        javax.swing.GroupLayout panelResultadoLayout = new javax.swing.GroupLayout(panelResultado);
        panelResultado.setLayout(panelResultadoLayout);
        panelResultadoLayout.setHorizontalGroup(
            panelResultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 945, Short.MAX_VALUE)
        );
        panelResultadoLayout.setVerticalGroup(
            panelResultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 652, Short.MAX_VALUE)
        );

        jtpCentral.addTab("REPORTES", panelResultado);

        getContentPane().add(jtpCentral);
        jtpCentral.setBounds(0, 0, 950, 680);

        jMenu1.setText("Menu");

        jMenuItem2.setText("Simulacion");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Detalle Simulacion");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setText("Graficos");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem5.setText("Obtener Reporte");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("Graficos");

        jmiGraficoBarra.setText("Grafico Barra");
        jmiGraficoBarra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiGraficoBarraActionPerformed(evt);
            }
        });
        jMenu3.add(jmiGraficoBarra);

        jmiGrafico3DPie.setText("Grafico 3D Pie");
        jmiGrafico3DPie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiGrafico3DPieActionPerformed(evt);
            }
        });
        jMenu3.add(jmiGrafico3DPie);

        jmiXYSeries.setText("Grafico XYSeries");
        jmiXYSeries.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiXYSeriesActionPerformed(evt);
            }
        });
        jMenu3.add(jmiXYSeries);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
        // TODO add your handling code here:
        if (verificarCantidadLimite()) {
            actulizarHabitaciones();
            panelTabla.removeAll();
            crearTabla();
            mm=new ManagerM((StageM)stage,dtm,tablaResultados,jsVelocidad,jcbEconomico,jcbNegocio,jcbEjecutivo,jcbPremium,jcbDias,lblCantidadEconomico,lblCantidadNegocio,lblCantidadEjecutivo,lblCantidadPremium);
            mm.start();
            btnIniciar.setEnabled(false);
            btnPausar.setEnabled(true);
            btnTerminar.setEnabled(true);
            h=new Hora(horario,jsVelocidad);
            h.start();
        }
        else{
            JOptionPane.showMessageDialog(null, "LA CANTIDAD DE AUMENTO DE HABITACIONES TOTAL DEBER SER COMO MAXIMO 75 !!!");
        }
    }//GEN-LAST:event_btnIniciarActionPerformed
    private void actulizarHabitaciones(){
        lblCantidadEconomico.setText(String.valueOf((Integer.parseInt(lblCantidadEconomico.getText())+Integer.parseInt(txtEconomico.getText()))));
        lblCantidadNegocio.setText(String.valueOf((Integer.parseInt(lblCantidadNegocio.getText())+Integer.parseInt(txtNegocio.getText()))));
        lblCantidadEjecutivo.setText(String.valueOf((Integer.parseInt(lblCantidadEjecutivo.getText())+Integer.parseInt(txtEjecutivo.getText()))));
        lblCantidadPremium.setText(String.valueOf((Integer.parseInt(lblCantidadPremium.getText())+Integer.parseInt(txtPremium.getText()))));
    }
    private boolean verificarCantidadLimite(){
        boolean res=true;
        int cantAumentEconomico,cantAumentNegocio,cantAumentEjecutivo,cantAumentPremium,cantidadTotalHabitaciones;
        setDefectoValor();
        cantAumentEconomico=Integer.parseInt(txtEconomico.getText());
        cantAumentNegocio=Integer.parseInt(txtNegocio.getText());
        cantAumentEjecutivo=Integer.parseInt(txtEjecutivo.getText());
        cantAumentPremium=Integer.parseInt(txtPremium.getText());
        cantidadTotalHabitaciones=cantAumentEconomico+cantAumentNegocio+cantAumentEjecutivo+cantAumentPremium;
        if (cantidadTotalHabitaciones>75) {
            res=false;
        }else{
            res=true;
        }
        return res;
    }
    private void setDefectoValor(){
        if (txtEconomico.getText().equals("")||txtEconomico.getText().equals(null)) {
            txtEconomico.setText("0");
        }
        if (txtNegocio.getText().equals("")||txtNegocio.getText().equals(null)) {
            txtNegocio.setText("0");
        }
        if (txtEjecutivo.getText().equals("")||txtEjecutivo.getText().equals(null)) {
            txtEjecutivo.setText("0");
        }
        if (txtPremium.getText().equals("")||txtPremium.getText().equals(null)) {
            txtPremium.setText("0");
        }
    }
    private void btnPausarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPausarActionPerformed
        // TODO add your handling code here:
        if (btnPausar.getText().equals("PAUSAR")) {
            mm.suspend();
            h.suspend();
            btnPausar.setText("REANUDAR");
        }
        else{
           mm.resume();
           h.resume();
           btnPausar.setText("PAUSAR");
        }
    }//GEN-LAST:event_btnPausarActionPerformed

    private void btnTerminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTerminarActionPerformed
        // TODO add your handling code here:
        mm.stop();
        h.stop();
        btnPausar.setText("PAUSAR");
        btnIniciar.setEnabled(true);
        btnPausar.setEnabled(false);
        btnTerminar.setEnabled(false);
        lblCantidadEconomico.setText("200");
        lblCantidadNegocio.setText("200");
        lblCantidadEjecutivo.setText("200");
        lblCantidadPremium.setText("100");
    }//GEN-LAST:event_btnTerminarActionPerformed

    private void txtNegocioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNegocioActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtNegocioActionPerformed

    private void txtEconomicoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEconomicoKeyTyped
        char car = evt.getKeyChar();
        if(txtEconomico.getText().length()>=2) evt.consume();
        if((car<'0' || car>'9')) evt.consume();
    }//GEN-LAST:event_txtEconomicoKeyTyped

    private void txtNegocioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNegocioKeyTyped
        char car = evt.getKeyChar();
        if(txtNegocio.getText().length()>=2) evt.consume();
        if((car<'0' || car>'9')) evt.consume();
    }//GEN-LAST:event_txtNegocioKeyTyped

    private void txtEjecutivoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEjecutivoKeyTyped
        // TODO add your handling code here:
        char car = evt.getKeyChar();
        if(txtEjecutivo.getText().length()>=2) evt.consume();
        if((car<'0' || car>'9')) evt.consume();
    }//GEN-LAST:event_txtEjecutivoKeyTyped

    private void txtPremiumKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPremiumKeyTyped
        // TODO add your handling code here:
        char car = evt.getKeyChar();
        if(txtPremium.getText().length()>=2) evt.consume();
        if((car<'0' || car>'9')) evt.consume();
    }//GEN-LAST:event_txtPremiumKeyTyped

    private void jcbEconomicoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jcbEconomicoPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbEconomicoPropertyChange

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        jtpCentral.setSelectedIndex(1);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        jtpCentral.setSelectedIndex(2);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        jtpCentral.setSelectedIndex(0);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        mostrarReporte();
        jtpCentral.setSelectedIndex(3);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jmiGraficoBarraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiGraficoBarraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jmiGraficoBarraActionPerformed

    private void jmiGrafico3DPieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiGrafico3DPieActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jmiGrafico3DPieActionPerformed

    private void jmiXYSeriesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiXYSeriesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jmiXYSeriesActionPerformed
    
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
            //UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
            //UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
            //UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
            //UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
            //UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new World().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelBarra;
    private javax.swing.JPanel PanelConfig;
    private javax.swing.JPanel PanelHabitaciones;
    private javax.swing.JPanel PanelPieChart;
    private javax.swing.JPanel PanelStart;
    private javax.swing.JPanel PanelTablaDetalle;
    private javax.swing.JPanel PanelXYSeries;
    private javax.swing.JButton btnIniciar;
    private javax.swing.JButton btnPausar;
    private javax.swing.JButton btnTerminar;
    private javax.swing.JLabel horario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JComboBox jcbDias;
    private javax.swing.JComboBox jcbEconomico;
    private javax.swing.JComboBox jcbEjecutivo;
    private javax.swing.JComboBox jcbNegocio;
    private javax.swing.JComboBox jcbPremium;
    private javax.swing.JMenuItem jmiGrafico3DPie;
    private javax.swing.JMenuItem jmiGraficoBarra;
    private javax.swing.JMenuItem jmiXYSeries;
    private javax.swing.JSlider jsVelocidad;
    private javax.swing.JTabbedPane jtpCentral;
    private javax.swing.JLabel lblCantidadEconomico;
    private javax.swing.JLabel lblCantidadEjecutivo;
    private javax.swing.JLabel lblCantidadNegocio;
    private javax.swing.JLabel lblCantidadPremium;
    private javax.swing.JLabel lblHabitacionesEconomica1;
    private javax.swing.JLabel lblHabitacionesEconomica3;
    private javax.swing.JLabel lblHabitacionesEconomica4;
    private javax.swing.JLabel lblHabitacionesEconomica5;
    private javax.swing.JPanel panelResultado;
    private javax.swing.JPanel panelTabla;
    private javax.swing.JInternalFrame setings;
    private javax.swing.JPanel stage;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JFormattedTextField txtEconomico;
    private javax.swing.JFormattedTextField txtEjecutivo;
    private javax.swing.JFormattedTextField txtNegocio;
    private javax.swing.JFormattedTextField txtPremium;
    // End of variables declaration//GEN-END:variables
}
