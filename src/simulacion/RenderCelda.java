/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulacion;

/**
 *
 * @author MILVER
 */
import java.awt.Component;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
public class RenderCelda extends DefaultTableCellRenderer 
{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,boolean hasFocus, int row, int column) 
    {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if( value instanceof String )
        {
            if( value.equals("ECONOMICO") )
            {
                cell.setBackground(Color.RED);
                cell.setForeground(Color.WHITE);
            }
            else
            {
             if(value.equals("NEGOCIOS")){
              cell.setBackground(Color.GREEN);
                    cell.setForeground(Color.WHITE);
             }
             else{
              cell.setBackground(Color.WHITE);
              cell.setForeground(Color.BLACK);
             }
            }
        }
        return cell;
    }
}

