import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
    public static Connection conectar(){
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/app_financeiro",
                    "root",
                    "12345"
            );
            System.out.println("Conectado!");
            return con;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar no banco", e);
        }
    }
}
