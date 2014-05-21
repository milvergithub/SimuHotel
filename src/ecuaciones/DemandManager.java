package ecuaciones;

import java.util.Random;

/**
 * @author simulacion
 */
public class DemandManager {
    
    public enum TIPO{
        ECONOMICO,
        NEGOCIOS,
        EJECUTIVO,
        PREMIUM
    }
    private int demanda;
    private TIPO tipo;
    private double montoRebaja;

    public DemandManager(TIPO t, double rP) {
        tipo=t;
        demanda = getMontoDemanda();
        montoRebaja = rP;
    }
    public int getMontoDemanda(){
        int res=0;
        switch(tipo){
            case ECONOMICO:
                res=175;
                break;
            case NEGOCIOS:
                res=140;
                break;
            case EJECUTIVO:
                res=115;
                break;
            case PREMIUM:
                res=75;
                break;
        }
        return res;
    }
    public int getDemandaEconomico() {
        int res;
        res = (int) (demanda + (demanda * (getPorcentajeRebaja(montoRebaja) / 100)*(getReaccionEconomico(montoRebaja))));
        return res;
    }
    public int getDemandaNegocio() {
        int res;
        res = (int) (demanda + (demanda * (getPorcentajeRebaja(montoRebaja) / 100)*(getReaccionNegocio(montoRebaja))));
        return res;
    }
    public int getDemandaEjecutivo() {
        int res;
        res = (int) (demanda + (demanda * (getPorcentajeRebaja(montoRebaja) / 100)*(getReaccionEjecutivo(montoRebaja))));
        return res;
    }
    public int getDemandaPremium() {
        int res;
        res = (int) (demanda + (demanda * (getPorcentajeRebaja(montoRebaja) / 100)*(getReaccionPremium(montoRebaja))));
        return res;
    }
    public double getPorcentajeRebaja(double cant) {
        double res;
        double actual = demanda - cant;
        double reb = cant;
        if (reb > 20) {
            reb = 20;
        }
        res = 100 - ((actual * 100) / demanda);
        return res;

    }

    private double getReaccionEconomico(double cant) {
        double res = 0;
        switch ((int)cant) {
            case 10:
                res = generarNumeroRango(1, 4);
                break;
            case 20:
                res = generarNumeroRango(3, 5);
                break;
        }
        return res;
    }
    private double getReaccionNegocio(double cant){
        double res=0;
        switch((int)cant){
            case (int)7.5:
                res=generarNumeroRango(0, 2);
                break;
            case 15:
                res=generarNumeroRango(1, 3);
                break;
        }
        return res;
    }
    private double getReaccionEjecutivo(double cant){
        double res=0;
        switch((int)cant){
            case 2:
                res=generarNumeroRango(0, 2);
                break;
            case 5:
                res=generarNumeroRango(1, 2);
                break;
        }
        return res;
    }
    private double getReaccionPremium(double cant){
        double res=0;
        switch((int)cant){
            case 1:
                res= -generarNumeroRango(0, 1);
                break;
            case 5:
                res= -generarNumeroRango(1, 2);
                break;
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
/*
    public static void main(String args[]) {
        DemandManager dme = new DemandManager(TIPO.ECONOMICO, 20);
        DemandManager dmn = new DemandManager(TIPO.NEGOCIOS, 15);
        DemandManager dmej = new DemandManager(TIPO.EJECUTIVO, 5);
        DemandManager dmp = new DemandManager(TIPO.PREMIUM, 5);
        System.out.println("economico es = " + dme.getDemandaEconomico());
        System.out.println("negocio   es = " + dmn.getDemandaNegocio());
        System.out.println("ejecutivo es = " + dmej.getDemandaEjecutivo());
        System.out.println("premium   es = " + dmp.getDemandaPremium());
    }*/
}
