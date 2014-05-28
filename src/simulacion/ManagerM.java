package simulacion;

import ecuaciones.DemandManager;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * @author simulacion
 */
public class ManagerM extends Thread{
    
    private float cantidadEconomica;
    private float cantidadNegocio;
    private float cantidadEjecutivo;
    private float cantidadPremium;
    
    private float segEconomico;
    private float segNegocio;
    private float segEjecutivo;
    private float segPremium;
    
    private int cantidadHabitacionesEconomica;
    private int cantidadHabitacionesNegocio;
    private int cantidadHabitacionesEjecutivo;
    private int cantidadHabitacionesPremium;
    
    private int minutoInicio;
    private int minutoFinal;
    private int cantidadTotal;
    private StageM stagem;
    private DefaultTableModel dtm;
    private JTable tabla;
    private JSlider velocidad;
    private JComboBox ECONOMICO,NEGOCIO,EJECUTIVO,PREMIUM,DIAS;
    private JLabel cantidadHabEconomica,cantidadHabNegocio,cantidadHabEjecutivo,cantidadHabPremium;
    
    public ManagerM(StageM sm, DefaultTableModel dt,JTable t,JSlider vel,JComboBox jcbEcon,JComboBox jcbNeg,JComboBox jcbEje,JComboBox jcbPre,JComboBox jcbdias,JLabel cantHabEco,JLabel cantHabNeg,JLabel cantHabEje,JLabel cantHabPre){
        cantidadTotal=1;
        minutoInicio=480;
        minutoFinal=1440;
        stagem=sm;   
        dtm=dt;
        tabla=t;
        velocidad=vel;
        ECONOMICO=jcbEcon;
        NEGOCIO=jcbNeg;
        EJECUTIVO=jcbEje;
        PREMIUM=jcbPre;
        DIAS=jcbdias;
        cantidadHabEconomica=cantHabEco;
        cantidadHabNegocio=cantHabNeg;
        cantidadHabEjecutivo=cantHabEje;
        cantidadHabPremium=cantHabPre;
        cantidadHabitacionesEconomica=Integer.parseInt(cantidadHabEconomica.getText());
        cantidadHabitacionesNegocio=Integer.parseInt(cantidadHabNegocio.getText());
        cantidadHabitacionesEjecutivo=Integer.parseInt(cantidadHabEjecutivo.getText());
        cantidadHabitacionesPremium=Integer.parseInt(cantidadHabPremium.getText());
    }
    
    @Override
    public void run(){
        int inicio=minutoInicio;
        int cantidadNeg=1,cantidadEco=1,cantidadEje=1,cantidadPre=1;
        for (int dia = 1; dia <= Integer.parseInt(String.valueOf(DIAS.getSelectedItem())); dia++) {
            generadata();
            float minutoEconomico=(((float)inicio)+segEconomico);
            float minutoNegocio=(((float)inicio)+segNegocio);
            float minutoEjecutivo=(((float)inicio)+segEjecutivo);
            float minutoPremium=(((float)inicio)+segPremium);
            while (inicio<minutoFinal) {
                if (inicio==(int)minutoEconomico) {
                    Cliente econo=new Cliente(dia,stagem,dtm,cantidadTotal,cantidadEco,velocidad, DemandManager.TIPO.ECONOMICO,cantidadHabEconomica);
                    stagem.add(econo);
                    Thread he=new Thread(econo);
                    he.start();
                    cantidadEco++;
                    minutoEconomico=minutoInicio+segEconomico*cantidadEco;
                    cantidadTotal++;
                    cantidadHabEconomica.setText(String.valueOf(Integer.parseInt(cantidadHabEconomica.getText())-1));
                }
                if (inicio==(int)minutoNegocio) {
                    Cliente negoc=new Cliente(dia, stagem, dtm, cantidadTotal, cantidadNeg, velocidad, DemandManager.TIPO.NEGOCIOS,cantidadHabNegocio);
                    stagem.add(negoc);
                    Thread hn=new Thread(negoc);
                    hn.start();
                    cantidadNeg++;
                    minutoNegocio=minutoInicio+segNegocio*cantidadNeg;
                    cantidadTotal++;
                    cantidadHabNegocio.setText(String.valueOf(Integer.parseInt(cantidadHabNegocio.getText())-1));
                }
                if (inicio==(int)minutoEjecutivo) {
                    Cliente ejecu=new Cliente(dia, stagem, dtm, cantidadTotal, cantidadEje, velocidad, DemandManager.TIPO.EJECUTIVO,cantidadHabEjecutivo);
                    stagem.add(ejecu);
                    Thread hej=new Thread(ejecu);
                    hej.start();
                    cantidadEje++;
                    minutoEjecutivo=minutoInicio+segEjecutivo*cantidadEje;
                    cantidadTotal++;
                    cantidadHabEjecutivo.setText(String.valueOf(Integer.parseInt(cantidadHabEjecutivo.getText())-1));
                }
                if (inicio==(int)minutoPremium) {
                    Cliente clien=new Cliente(dia, stagem, dtm, cantidadTotal, cantidadPre, velocidad, DemandManager.TIPO.PREMIUM,cantidadHabPremium);
                    stagem.add(clien);
                    Thread hp=new Thread(clien);
                    hp.start();
                    cantidadPre++;
                    minutoPremium=minutoInicio+segPremium*cantidadPre;
                    cantidadTotal++;
                    cantidadHabPremium.setText(String.valueOf(Integer.parseInt(cantidadHabPremium.getText())-1));
                }
                inicio++;
                try{
                    sleep(velocidad.getValue());
                } catch (Exception e){
                }
                System.out.println("INICIO = "+inicio);
            }
            seleccionar(cantidadTotal);
            cantidadEco=1;
            cantidadNeg=1;
            cantidadEje=1;
            cantidadPre=1;
            inicio=minutoInicio;
            cantidadHabEconomica.setText(String.valueOf(cantidadHabitacionesEconomica));
            cantidadHabNegocio.setText(String.valueOf(cantidadHabitacionesNegocio));
            cantidadHabEjecutivo.setText(String.valueOf(cantidadHabitacionesEjecutivo));
            cantidadHabPremium.setText(String.valueOf(cantidadHabitacionesPremium));
        }
      
    }
    private void seleccionar(int cantt){
        String ele = String.valueOf(cantt);
        for (int i = 0; i < tabla.getRowCount(); i++) {
               if (tabla.getValueAt(i, 1).equals(ele)) {                                           
                      tabla.changeSelection(i, 1, false, false);
                      break;
               }
        }
    }
    public void generadata(){
        cantidadEconomica=new DemandManager(DemandManager.TIPO.ECONOMICO, Integer.parseInt(String.valueOf(ECONOMICO.getSelectedItem()))).getDemandaEconomico();
        cantidadNegocio=new DemandManager(DemandManager.TIPO.NEGOCIOS, Float.parseFloat(String.valueOf(NEGOCIO.getSelectedItem()))).getDemandaNegocio();
        cantidadEjecutivo=new DemandManager(DemandManager.TIPO.EJECUTIVO, Integer.parseInt(String.valueOf(EJECUTIVO.getSelectedItem()))).getDemandaEjecutivo();
        cantidadPremium=new DemandManager(DemandManager.TIPO.PREMIUM, Integer.parseInt(String.valueOf(PREMIUM.getSelectedItem()))).getDemandaPremium();
        segEconomico=((minutoFinal-minutoInicio)/cantidadEconomica);
        segNegocio=(minutoFinal-minutoInicio)/cantidadNegocio;
        segEjecutivo=(minutoFinal-minutoInicio)/cantidadEjecutivo;
        segPremium=(minutoFinal-minutoInicio)/cantidadPremium;

        System.out.println("cantidad Economico = "+(int)cantidadEconomica);
        System.out.println("el tiempo que tiene es = "+(int)(minutoFinal-minutoInicio));
        System.out.println("rango ECONOMICO = "+segEconomico);
        System.out.println("rango NEGOCIO = "+segNegocio);
        System.out.println("rango EJECUTIVO = "+segEjecutivo);
        System.out.println("rango PREMIUM = "+segPremium);
        System.out.println("************************");
      }
}
