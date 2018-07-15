package FORMS;
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author christian
 */
public class ConexionSQL {
    Connection conect = null;
    public Connection Conectar () {
        try {
            //Cargando driver de my sql
            Class.forName("org.gjt.mm.mysql.Driver");
            conect = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/sigere","root","");
            System.out.println("Conexion a MySQL fue un exito");
        }
        catch (Exception e) {
            System.out.println("Error en "+e);
        }
        return conect;
    }
    public Connection cerrarConexion () {
        try {
            conect.close();
            System.out.println("Cerrando conexion");
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
