package simulacion;

import ecuaciones.DemandManager;

import java.awt.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * @author simulacion
 */
public class Cliente extends Actor implements Runnable{
    private DefaultTableModel dtm;
    private int contador;
    private JSlider velocidad;
    private int dia;
    private int cantidadU;
    private DemandManager.TIPO tipo;
    private JLabel cantidadHabitaciones;
    public Cliente(int di,StageM s, DefaultTableModel d,int cont,int cantU,JSlider vel, DemandManager.TIPO t,JLabel cantHab){
        super(s);
        tipo=t;
        super.setImagen(getAdecuadoImage());
        cantidadU=cantU;
        dtm=d;
        dia=di;
        contador=cont;
        switch(tipo){
            case ECONOMICO:
                this.setLocation(0,70);
                break;
            case NEGOCIOS:
                this.setLocation(0,150);
                break;
            case EJECUTIVO:
                this.setLocation(0,220);
                break;
            case PREMIUM:
                this.setLocation(0,290);
                break;
        }
        cantidadHabitaciones=cantHab;
        velocidad=vel;
    }
    private ImageIcon getAdecuadoImage(){
        ImageIcon imagen=null;
        switch (tipo){
            case ECONOMICO:
                imagen=new ImageIcon(getClass().getResource("/img/personaE.png"));
                break;
            case NEGOCIOS:
                imagen=new ImageIcon(getClass().getResource("/img/personaN.png"));
                break;
            case EJECUTIVO:
                imagen=new ImageIcon(getClass().getResource("/img/personaEj.png"));
                break;
            case PREMIUM:
                imagen=new ImageIcon(getClass().getResource("/img/personaP.png"));
                break;
        }
        return imagen;
    }
    @Override
    public void run() {
        
        int cont=0;
        while (cont<250) {            
            try {
                this.mover();
                Thread.sleep(velocidad.getValue()/10);
            } catch (Exception e) {
            }
            cont++;
        }
        String s[]={String.valueOf(dia),String.valueOf(this.contador),String.valueOf(cantidadU),String.valueOf(tipo),getOcupacion()};
        try {
            dtm.addRow(s);
        } catch (Exception e) {
        }
        super.remover();
    }
    private String getOcupacion(){
        String res="SI";
        if (Integer.parseInt(cantidadHabitaciones.getText())<=0) {
            res="NO";
        }
        return res;
    }
    //solo esta de prueba este metodo nada oficial
    private String getTipo(){
        String res="";
        int numero=(int)generarNumeroRango(1, 10);
        if (numero>0&&numero<4) {
            res="ECONOMICO";
        }
        else{
            if (numero>3&&numero<8) {
                res="NEGOCIOS";
            }else{
                res="PREMIUM";
            }
        }
        return res;
    }
    private double generarNumeroRango(int min, int max) {
        double re = -1;
        while (re < min || re > max) {
            Random rn = new Random();
            re = rn.nextDouble() * 10;
        }
        return re;
    }
}
