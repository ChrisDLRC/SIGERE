package FORMS;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
    public static String notas (String mensaje) {
        try {
            String hora = hora ();
            File TextFile = new File("c:/bitacora.txt");
            FileWriter TextOut = new FileWriter(TextFile, true);
            TextOut.write("\n"+mensaje+"    -"+hora+"\n");
            TextOut.close();
        }
        catch (Exception notas) {
            System.out.println("ERROR EN GRABACION EN BITACORA: "+notas);
        }
        String grabado = "grabado";
        return (grabado);
    }
    
    public static String hora ( ) {
        Calendar calendar =Calendar.getInstance(); //obtiene la fecha de hoy 
        Calendar calendario = new GregorianCalendar();
        int hora, minutos, segundos;
        hora =calendario.get(Calendar.HOUR_OF_DAY);
        minutos = calendario.get(Calendar.MINUTE);
        segundos = calendario.get(Calendar.SECOND);
        String horas = hora + ":" + minutos + ":" + segundos;
        String fecha = (String.format("%1$td-%1$tm-%1$ty",  calendar.getTime()));
        String fechas = fecha+"/"+horas;
        return (fechas);
    }
    
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
                        String mensaje = dato + "";
                        notas (mensaje);
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
                             String mensaje = "-Borrar tabla reporte - Ok";
                             notas (mensaje);
                         }
                        String sentencia="CREATE TABLE reporte ("+ "No VARCHAR(4), "+ "Nombre VARCHAR(80), " + "Fecha VARCHAR(20), " + "Entrada VARCHAR(20), "  + "Salida VARCHAR(20), " +"Horas VARCHAR(20))";
                        PreparedStatement pst = cn.prepareStatement(sentencia);
                        pst.executeUpdate();
                        System.out.println("-Creada tabla (reporte) - Ok");
                        String mensaje = "-Creada tabla (reporte) - Ok";
                        notas (mensaje);
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