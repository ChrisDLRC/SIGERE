package FORMS;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import jxl.Sheet;
import jxl.Workbook;
/**
 *
 * @author christian
 */
public class MigraraSQL {
    ConexionSQL con;
    String No;
    String Nombre;
    String Fecha;
    String Entrada;
    String Salida;
    String Hora;
    private void LeerArchivosExcel(String archivoDestino) {
        int contador=1;
        int base =0;
        try {
            Workbook archivoExcel = Workbook.getWorkbook(new File(archivoDestino));
            for (int hojas=0; hojas<archivoExcel.getNumberOfSheets(); hojas++) {
                Sheet hoja = archivoExcel.getSheet(hojas);
                int numColumnas = hoja.getColumns();
                int numFilas = hoja.getRows();
                String dato;
                //Recorre cada fila de la hoja
                for (int fila =1; fila<numFilas; fila++) {
                    for (int columna=0; columna<numColumnas; columna++) {
                        dato = hoja.getCell(columna, fila) .getContents();
                        System.out.println(dato + "");
                        //Instruccion Switch que evalua la variable contador
                        switch (contador) {
                            case 1:
                            No = dato;
                            contador++;
                            break;
                            case 2:
                            Nombre = dato;
                            contador++;
                            break;
                            case 3:
                            Fecha = dato;
                            contador++;
                            break;
                            case 4:
                            Entrada = dato;
                            contador++;
                            break;
                            case 5:
                            Salida = dato;
                            contador++;
                            break;
                            case 6:
                            Hora = dato;
                            contador=1;
                            break;
                        }
                    }
                    System.out.println("\n");
                    ConexionSQL con = new ConexionSQL();
                    Connection cn = con.Conectar();
                    if (base==0) {
                         if (base==0) {
                             String sentencia="DROP TABLE IF EXISTS reporte";
                             PreparedStatement pst = cn.prepareStatement(sentencia);
                             pst.executeUpdate();
                             System.out.println("-Borrar tabla contacto - Ok");
                         }
                        String sentencia="CREATE TABLE reporte ("+ "No VARCHAR(4), "+ "Nombre VARCHAR(80), " + "Fecha VARCHAR(20), " + "Entrada VARCHAR(20), "  + "Salida VARCHAR(20), " +"Horas VARCHAR(20))";
                        PreparedStatement pst = cn.prepareStatement(sentencia);
                        pst.executeUpdate();
                        System.out.println("-Creada tabla (contacto) - Ok");
                        base=1;
                    }
                    String sentencia="INSERT INTO reporte (`No`,`Nombre`,`Fecha`,`Entrada`,`Salida`,`Horas`) VALUES ('"+No+"','"+Nombre+"','"+Fecha+"','"+Entrada+"','"+Salida+"','"+Hora+"')";
                    PreparedStatement pst = cn.prepareStatement(sentencia);
                    pst.executeUpdate();
                    con.cerrarConexion();
                }
            }
        }
        catch (Exception ioe) {
            ioe.printStackTrace();
        }
    }
    void Migracion () {
        MigraraSQL excel = new MigraraSQL();
        excel.LeerArchivosExcel("C:\\Program Files (x86)\\Att\\reporte.xls");
    }
    public static void main(String[] args) {
        
    }
}