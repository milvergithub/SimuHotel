package simulacion;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * @author simulacion
 */
public class SaveDatos {
    private ArrayList<Integer> montosRecaudado;
    private ArrayList<String[]>configuraciones;
    private String contenido="";
    private HashMap<String,Integer> economico;
    private HashMap<String,Integer> negocio;
    private HashMap<String,Integer> ejecutivo;
    private HashMap<String,Integer> premium;
    
    private HashMap<String,Integer> precios;
    private HashMap<String,Integer> habitaciones;
    
    public SaveDatos(HashMap<String,Integer> econ,HashMap<String,Integer> neg,HashMap<String,Integer> ejec,HashMap<String,Integer> prem,HashMap<String,Integer> precio,HashMap<String,Integer> hab){
      configuraciones=new ArrayList<String[]>();
      montosRecaudado=new ArrayList<Integer>();
      economico=econ;
      negocio=neg;
      ejecutivo=ejec;
      premium=prem;
      precios=precio;
      habitaciones=hab;
    }
    public void guardarConfigDatos(){
        int montoEconomico;
        int montoNegocio;
        int montoEjecutivo;
        int montoPremium;
        montoEconomico=economico.get("Dia1")*precios.get("ECONOMICO");
        montoNegocio=negocio.get("Dia1")*precios.get("NEGOCIO");
        montoEjecutivo=ejecutivo.get("Dia1")*precios.get("EJECUTIVO");
        montoPremium=premium.get("Dia1")*precios.get("PREMIUM");
        String lineaMonto="Economico;"+String.valueOf(montoEconomico)+";HABITACIONES;"+habitaciones.get("ECONOMICO")+";PRECIO;"+precios.get("ECONOMICO")+"" +
                         ";Negocio;"+String.valueOf(montoNegocio)+";HABITACIONES;"+habitaciones.get("NEGOCIO")+";PRECIO;"+precios.get("NEGOCIO")+"" +
                         ";Ejecutivo;"+String.valueOf(montoEjecutivo)+";HABITACIONES;"+habitaciones.get("EJECUTIVO")+";PRECIO;"+precios.get("EJECUTIVO")+"" +
                         ";Premium;"+String.valueOf(montoPremium)+";HABITACIONES;"+habitaciones.get("PREMIUM")+";PRECIO;"+precios.get("PREMIUM");
        
        guardarDatos(lineaMonto);
    }
    public String getDatelleConfiguracion(){
        String res="*************************\n";
        for (int i = 0; i < configuraciones.size(); i++) {
            String arreglo[]=configuraciones.get(i);
            res = res+"ECONOMICO\n"+"Habitaciones = "+arreglo[3]+"\nPrecio = "+arreglo[5]+
                      "\nNEGOCIO\n"+"habitaciones = "+arreglo[9]+"\nPrecio = "+arreglo[11]+
                      "\nEJECUTIVO\n"+"Habitaciones = "+arreglo[15]+"\nPrecio = "+arreglo[17]+
                      "\nPREMIUM\n"+"Habitaciones = "+arreglo[21]+"\nPrecio = "+arreglo[23]+"\n*************************\n\n";
        }
        return res;
    }
    public void guardarDatos(String content){
      File archivo=new File("archivo.mfa");
      BufferedReader entrada;
      try {
        entrada = new BufferedReader( new FileReader( archivo ) );
        String linea;
        while(entrada.ready()){
            linea = entrada.readLine();
            contenido=contenido+linea+"\n";
            System.out.println(linea);
        }
        FileWriter w = new FileWriter(archivo.getAbsolutePath());
        BufferedWriter bw = new BufferedWriter(w);
        PrintWriter wr = new PrintWriter(bw);  
        wr.write(contenido+"\n"+content);
        wr.close();
        bw.close();
      }catch (IOException e) {
        e.printStackTrace();
      }
      System.out.println("ruta= "+archivo.getAbsolutePath());
      printReporteActualAlmacenado();
    }
    public void printReporteActualAlmacenado(){
        File archivo=new File("archivo.mfa");
        BufferedReader entrada;
        try{
            entrada=new BufferedReader(new FileReader(archivo));
            int contador=1;
            while (entrada.ready()){
                String linea=entrada.readLine();
                if (contador%2==0){
                    String arreglo[]=linea.split(";");
                    configuraciones.add(arreglo);
                }
                contador++;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public int posiMejorConfig(){
        int mejor=50000;
        int pos=0;
        for (int i = 0; i < montosRecaudado.size(); i++) {
            if (montosRecaudado.get(i)>mejor) {
                mejor=montosRecaudado.get(i);
                pos=i;
            }
        }
        return pos;
    }
    public String[] getMejorRecomendado(){
        int pos=posiMejorConfig();
        return configuraciones.get(pos);
    }
    public ChartPanel getRusultadosGuardados(){
        XYSeries seriesSugerencia = new XYSeries("MODIFICADOS");
        XYSeries seriesDefault = new XYSeries("SIN MODIFICACIONES");
        for (int i = 0; i < configuraciones.size(); i++) {
            String arreglo[]=configuraciones.get(i);
            int montoDiaRecaudado;
            montoDiaRecaudado=Integer.parseInt(arreglo[1])+Integer.parseInt(arreglo[7])+Integer.parseInt(arreglo[13])+Integer.parseInt(arreglo[19]);
            montosRecaudado.add(montoDiaRecaudado);
            seriesSugerencia.add(i,montoDiaRecaudado);
            seriesDefault.add(i,54230);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(seriesSugerencia);
        dataset.addSeries(seriesDefault);
        NumberAxis xax = new NumberAxis("SIMULADO");
        NumberAxis yax = new NumberAxis("MONTO REACAUDADO");
        XYSplineRenderer a = new XYSplineRenderer();
        a.setPrecision(100);
        XYPlot xyplot = new XYPlot(dataset, xax, yax, a);
        xyplot.setBackgroundPaint(Color.BLACK);

        JFreeChart chart = new JFreeChart(xyplot);
        chart.setBackgroundPaint(Color.WHITE);
        ChartPanel panel=new ChartPanel(chart);
        return panel;
    }
    public ChartPanel getSugerenciaIndividual(){
        XYSeries seriesEconomicoS = new XYSeries("ECONOMICO MODIFICADO");
        XYSeries seriesNegocioS = new XYSeries("NEGOCIO MODIFICADO");
        XYSeries seriesEjecutivoS = new XYSeries("EJECUTIVO MODIFICADO");
        XYSeries seriesPremiumS = new XYSeries("PREMIUM MODIFICADO");

        XYSeries seriesEconomicoD = new XYSeries("ECONOMICO");
        XYSeries seriesNegocioD = new XYSeries("NEGOCIO");
        XYSeries seriesEjecutivoD = new XYSeries("EJECUTIVO");
        XYSeries seriesPremiumD = new XYSeries("PREMIUM");

        for (int i = 0; i < configuraciones.size(); i++) {
            String arreglo[]=configuraciones.get(i);
            seriesEconomicoS.add(i,Integer.parseInt(arreglo[1]));
            seriesEconomicoD.add(i,173*78);
            seriesNegocioS.add(i,Integer.parseInt(arreglo[7]));
            seriesNegocioD.add(i,138*97);
            seriesEjecutivoS.add(i,Integer.parseInt(arreglo[13]));
            seriesEjecutivoD.add(i,114*120);
            seriesPremiumS.add(i,Integer.parseInt(arreglo[19]));
            seriesPremiumD.add(i,74*180);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(seriesEconomicoS);
        dataset.addSeries(seriesNegocioS);
        dataset.addSeries(seriesEjecutivoS);
        dataset.addSeries(seriesPremiumS);
        dataset.addSeries(seriesEconomicoD);
        dataset.addSeries(seriesNegocioD);
        dataset.addSeries(seriesEjecutivoD);
        dataset.addSeries(seriesPremiumD);
        NumberAxis xax = new NumberAxis("CONFIGURACIONES");
        NumberAxis yax = new NumberAxis("MONTO RECAUDADO");
        XYSplineRenderer a = new XYSplineRenderer();
        a.setPrecision(10);
        XYPlot xyplot = new XYPlot(dataset, xax, yax, a);
        xyplot.setBackgroundPaint(Color.BLACK);

        JFreeChart chart = new JFreeChart(xyplot);
        chart.setBackgroundPaint(Color.WHITE);
        ChartPanel panel=new ChartPanel(chart);
        return panel;
    }
}
