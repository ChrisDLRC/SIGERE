package FORMS;
import com.mysql.jdbc.Connection;
import java.io.File;
import java.io.FileWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
/**
 *
 * @author christian
 */
public class ConexionSQL {
    Connection conect = null;
    public static String notas (String mensaje) {
        try {
            String hora = hora ();
            File TextFile = new File("c:/bitacora.txt");
            FileWriter TextOut = new FileWriter(TextFile, true);
            TextOut.write("\n"+mensaje+"    -"+hora+"\n ");
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
    
    public Connection Conectar () {
        try {
            //Cargando driver de my sql
            Class.forName("org.gjt.mm.mysql.Driver");
            conect = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/sigere","root","");
            System.out.println("Conexion a MySQL fue un exito");
            String mensaje = "Conexion a MySQL fue un exito";
            notas (mensaje);
        }
        catch (Exception e) {
            System.out.println("Error en "+e);
            String mensaje = "Error en "+e;
            notas (mensaje);
        }
        return conect;
    }
    public Connection cerrarConexion () {
        try {
            conect.close();
            System.out.println("Cerrando conexion");
            String mensaje = "Conexion cerrada";
            notas (mensaje);
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
        conect=null;
        return conect;
    }

    Statement createStatement() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
