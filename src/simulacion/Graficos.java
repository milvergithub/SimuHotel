package simulacion;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;

/**
 * @author MILVER
 */
public class Graficos {
    private ImageIcon imagen=new ImageIcon(getClass().getResource("/img/hoteles.jpg"));
    public Graficos(){
        
    }
    public ChartPanel getGraficoBarras(){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(175, "ECONOMICO", "Dia 1");
        dataset.setValue(140, "NEGOCIO", "Dia 1");
        dataset.setValue(115, "EJECUTIVO", "Dia 1");
        dataset.setValue(78,  "PREMIUM", "Dia 1");
        dataset.setValue(175, "ECONOMICO", "Dia 2");
        dataset.setValue(140, "NEGOCIO", "Dia 2");
        dataset.setValue(115, "EJECUTIVO", "Dia 2");
        dataset.setValue(78,  "PREMIUM", "Dia 2");
        dataset.setValue(175, "ECONOMICO", "Dia 3");
        dataset.setValue(140, "NEGOCIO", "Dia 3");
        dataset.setValue(115, "EJECUTIVO", "Dia 3");
        dataset.setValue(78,  "PREMIUM", "Dia 3");
        // Creando el Grafico
        JFreeChart chart = ChartFactory.createBarChart3D("CLIENTES ","TIPO", "Dia", dataset, PlotOrientation.VERTICAL, true,true, false);
        chart.setBackgroundImage(imagen.getImage());
        chart.getTitle().setPaint(Color.CYAN); 
        CategoryPlot p = chart.getCategoryPlot(); 
        p.setRangeGridlinePaint(Color.red); 
        // Mostrar Grafico
        ChartPanel chartPanel = new ChartPanel(chart);
        return chartPanel;
    }
    public ChartPanel getGraficoPie3D(){
        DefaultPieDataset defaultpiedataset = new DefaultPieDataset(); 
        defaultpiedataset.setValue("ECONOMICO", new Double(41.200000000000003D)); 
        defaultpiedataset.setValue("NEGOCIOS", new Double(11D)); 
        defaultpiedataset.setValue("EJECUTIVO", new Double(19.5D)); 
        defaultpiedataset.setValue("PREMIUM", new Double(30.5D));  
        
        // Creando el Grafico
        JFreeChart chart = ChartFactory.createPieChart3D("TIPOS", defaultpiedataset, true, true, false); 
        chart.setBackgroundImage(imagen.getImage());
        chart.setBackgroundImageAlpha((float) 0.9);
        PiePlot3D pieplot3d = (PiePlot3D)chart.getPlot(); 
        pieplot3d.setDepthFactor(0.5); 
        pieplot3d.setStartAngle(290D); 
        pieplot3d.setDirection(Rotation.CLOCKWISE); 
        pieplot3d.setForegroundAlpha(0.5F);
        
        // Mostrar Grafico
        ChartPanel chartPanel = new ChartPanel(chart);
        return chartPanel;
    }
}
