package simulacion;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JSlider;

/**
 * @author Simulacion 
 */
public class Hora extends Thread{
    
    private JLabel hora;
    private JSlider velocidad;
    private int minutoInicio;
    private int numHora;
    private int numMinuto;
    public Hora(JLabel l,JSlider vel){
        minutoInicio=480;
        numHora=minutoInicio/60;
        numMinuto=0;
        hora=l;
        velocidad=vel;
        hora.setForeground(Color.RED);
        hora.setFont(new Font("Calibri", 5, 24));
    }
    @Override
    public void run(){
        int contador=minutoInicio;
        while (true) {
            if (contador>=1440) {
                contador=minutoInicio;
            }
            try {
                hora.setText("hora es = "+getHoraActual(contador));
                //System.out.println("hora es = "+getHoraActual(contador));
                sleep(velocidad.getValue()/10);
            } catch (Exception e) {
            }
          contador++;
        }
    }
    private String getHoraActual(int cont){
        String horaActual;
        int hh=cont/60;
        int mm=cont%60;
        horaActual=getFormatoHora(hh)+" : "+getFormatoMinuto(mm);
        return horaActual;
    }
    private String getFormatoHora(int h){
        String hh=String.valueOf(h);
        if (h<10) {
            hh="0"+String.valueOf(h);
        }
        return hh;
    }
    private String getFormatoMinuto(int m){
        String mm= String.valueOf(m);
        if (m<10) {
            mm="0"+m;
        }
        return mm;  
    }
}
