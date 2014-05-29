package simulacion;

import java.awt.Color;
import java.awt.GradientPaint;
import java.util.HashMap;
import javax.swing.ImageIcon;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.Rotation;

/**
 * @author MILVER
 */
public class Graficos {
    private final ImageIcon imagen=new ImageIcon(getClass().getResource("/img/fondo.png"));
    private final ImageIcon imagenFondo=new ImageIcon(getClass().getResource("/img/hotelfond.jpg"));
    private HashMap<String,Integer> economico;
    private HashMap<String,Integer> negocio;
    private HashMap<String,Integer> ejecutivo;
    private HashMap<String,Integer> premium;
    
    private HashMap<String,Integer> precios;
    
    public Graficos(HashMap<String,Integer> eco,HashMap<String,Integer>neg,HashMap<String,Integer> eje,HashMap<String,Integer>pre,HashMap<String,Integer> p){
        economico=eco;
        negocio=neg;
        ejecutivo=eje;
        premium=pre;
        precios=p;
    }
    public ChartPanel getGraficoBarras(){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 1; i <= economico.size(); i++) {
           dataset.setValue(economico.get("Dia"+i), "ECONOMICO", "Dia "+i);
           dataset.setValue(negocio.get("Dia"+i), "NEGOCIO", "Dia "+i);
           dataset.setValue(ejecutivo.get("Dia"+i), "EJECUTIVO", "Dia "+i);
           dataset.setValue(premium.get("Dia"+i), "PREMIUM", "Dia "+i);
        }
        // Creando el Grafico
       JFreeChart chart = ChartFactory.createBarChart("CLIENTES ","TIPO", "Dia", dataset, PlotOrientation.VERTICAL, true,true, true );
        chart.setBackgroundPaint(Color.WHITE);
        CategoryPlot plot=(CategoryPlot)chart.getPlot();
        plot.setBackgroundImage(imagenFondo.getImage());
        //plot.setBackgroundImageAlpha(0.9f);
        
      //Lineas divisorias
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.CYAN);
        final NumberAxis rangeAxis=(NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        //Dar color a cada barra
        GradientPaint gp0= new GradientPaint(0,0,Color.blue,0.0f,0.0f,Color.BLACK);
        GradientPaint gp1= new GradientPaint(0.0f,0.0f,Color.green,0.0f,0.0f,Color.BLACK);
        GradientPaint gp2= new GradientPaint(0.0f,0.0f,Color.red,0.0f,0.0f,new Color(64,0,0));
        GradientPaint gp3= new GradientPaint(0.0f,0.0f,Color.BLACK,0.0f,0.0f,new Color(64,0,0));
        
        renderer.setSeriesPaint(0,gp0);
        renderer.setSeriesPaint(1,gp1);
        renderer.setSeriesPaint(2,gp2);
        renderer.setSeriesPaint(3,gp3);
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
        CategoryLabelPositions.createUpRotationLabelPositions(Math.PI/6.0));
        
         
        // Mostrar Grafico
        ChartPanel chartPanel = new ChartPanel(chart);
        return chartPanel;
    }
    public ChartPanel getGraficoPie3D(){
        DefaultPieDataset defaultpiedataset = new DefaultPieDataset();
        int ceco=0;int cneg=0;int ceje=0;int cpre=0;
        for (int i = 1; i <= economico.size(); i++) {
           ceco=ceco+economico.get("Dia"+i);
           cneg=cneg+negocio.get("Dia"+i);
           ceje=ceje+ejecutivo.get("Dia"+i);
           cpre=cpre+premium.get("Dia"+i);
        }
        defaultpiedataset.setValue("ECONOMICO", ceco); 
        defaultpiedataset.setValue("NEGOCIOS", cneg); 
        defaultpiedataset.setValue("EJECUTIVO", ceje); 
        defaultpiedataset.setValue("PREMIUM", cpre);  
        
        // Creando el Grafico
        JFreeChart chart = ChartFactory.createPieChart3D("TIPOS", defaultpiedataset, true, true, false); 
        chart.setBackgroundImage(imagen.getImage());
        chart.setBackgroundImageAlpha((float) 0.9);
        PiePlot3D pieplot3d = (PiePlot3D)chart.getPlot(); 
        pieplot3d.setDepthFactor(0.3);
        pieplot3d.setStartAngle(290D);
        pieplot3d.setDirection(Rotation.CLOCKWISE);
        pieplot3d.setBackgroundImage(imagenFondo.getImage());
        pieplot3d.setForegroundAlpha(0.5F);
        
        // Mostrar Grafico
        ChartPanel chartPanel = new ChartPanel(chart);
        return chartPanel;
    }
    public ChartPanel getXYGraphics(){
        XYSeries seriesEconomico = new XYSeries("ECONOMICO");
        XYSeries seriesNegocio = new XYSeries("NEGOCIO");
        XYSeries seriesEjecutivo = new XYSeries("EJECUTIVO");
        XYSeries seriesPremium = new XYSeries("PREMIUM");
        for (int i = 1; i <= economico.size(); i++) {
           
           seriesEconomico.add(i-1,(economico.get("Dia"+i)*precios.get("ECONOMICO")));
            System.out.println("ECONOMICO CANTIDAD=> "+economico.get("Dia"+i));
           seriesNegocio.add(i-1,(negocio.get("Dia"+i)*precios.get("NEGOCIO")));
            System.out.println("NEGOCIO CANTIDAD =>"+negocio.get("Dia"+i));
           seriesEjecutivo.add(i-1,(ejecutivo.get("Dia"+i)*precios.get("EJECUTIVO")));
           
           seriesPremium.add(i-1,(premium.get("Dia"+i)*precios.get("PREMIUM")));
        }
        
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(seriesEconomico);
        dataset.addSeries(seriesNegocio);
        dataset.addSeries(seriesEjecutivo);
        dataset.addSeries(seriesPremium);
        NumberAxis xax = new NumberAxis("DIA");
        NumberAxis yax = new NumberAxis("CANTIDAD");
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
