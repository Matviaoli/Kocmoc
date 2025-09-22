import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
public class Kocmoc {
    public static void main(String[] args) {


        System.out.print("Iniciando tradução do JOption Pane...");

        Locale.setDefault(new Locale("pt", "BR"));

        UIManager.put("OptionPane.yesButtonText", "Sim");
        UIManager.put("OptionPane.noButtonText", "Não");
        UIManager.put("OptionPane.cancelButtonText", "Cancelar");

        System.out.print("\nTradução do JOption Pane bem sucedida!");

        System.out.print("\nIniciando tentativa de conexão com Kocmoc...");

        recriacao();

        System.out.print("\nConexão com Kocmoc bem sucedida!");
        System.out.print("\nIniciando interface Cosmos...");

        Nebulabrasque nebulaframe = new Nebulabrasque();
        nebulaframe.setVisible(true);

    }

    public static void recriacao(){

        String url = "jdbc:mysql://localhost:3306/kocmoc";
        String user = "root";
        String pass = "mysql";

        try (Connection conext = DriverManager.getConnection(url, user, pass);
             Statement semata = conext.createStatement()){

            semata.executeUpdate("CREATE TABLE IF NOT EXISTS diretores (" +
                    "id_diretor INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nome_diretor VARCHAR(255) NOT NULL, " +
                    "nascimento_diretor DATE NOT NULL, " +
                    "mandado_para_o_lobby_diretor DATE NULL)"
            );

            semata.executeUpdate("CREATE TABLE IF NOT EXISTS genero (" +

                    "id_genero TINYINT UNSIGNED AUTO_INCREMENT PRIMARY KEY, " +
                    "nome_genero VARCHAR(50) NOT NULL UNIQUE)"
            );
            
            String[] generosPadrao = {
                    "Ação", "Drama", "Ficção Científica", "Comédia", "Romance",
                    "Aventura", "Terror", "Suspense", "Documentário", "Fantasia"
            };
            for (String genero : generosPadrao) {
                semata.executeUpdate("INSERT IGNORE INTO genero (nome_genero) VALUES ('" + genero + "')");
            }

            semata.executeUpdate("CREATE TABLE IF NOT EXISTS producoes (" +
                    "id_producoes INT AUTO_INCREMENT PRIMARY KEY, " +
                    "titulo_producoes VARCHAR(255) NOT NULL, " +
                    "data_producoes DATE NOT NULL, " +
                    "numepisodios_producoes SMALLINT UNSIGNED, "+
                    "id_diretor INT, " +
                    "FOREIGN KEY (id_diretor) REFERENCES diretores(id_diretor) ON DELETE SET NULL" +
                    ")"
            );

            semata.executeUpdate("CREATE TABLE IF NOT EXISTS producoes_generos (" +
                    "id_producoes INT NOT NULL, " +
                    "id_genero TINYINT UNSIGNED NOT NULL, " +
                    "PRIMARY KEY(id_producoes, id_genero), " +
                    "FOREIGN KEY (id_producoes) REFERENCES producoes(id_producoes) ON DELETE CASCADE, " +
                    "FOREIGN KEY (id_genero) REFERENCES genero(id_genero) ON DELETE CASCADE" +
                    ")"
            );

            semata.executeUpdate("CREATE TABLE IF NOT EXISTS atores (" +
                    "id_atores INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nome_atores VARCHAR(255) NOT NULL, " +
                    "nascimento_atores DATE NOT NULL, " +
                    "mandado_para_o_lobby_atores DATE NULL)"
            );

            semata.executeUpdate("CREATE TABLE IF NOT EXISTS producoes_atores (" +
                    "id_producoes INT NOT NULL, " +
                    "id_atores INT NOT NULL, " +
                    "PRIMARY KEY(id_producoes, id_atores), " +
                    "FOREIGN KEY (id_producoes) REFERENCES producoes(id_producoes) ON DELETE CASCADE, " +
                    "FOREIGN KEY (id_atores) REFERENCES atores(id_atores) ON DELETE CASCADE)"
            );


        } catch (SQLException e){

            JOptionPane.showConfirmDialog(null,"Deu ruim no SQL","Fudeu", JOptionPane.ERROR_MESSAGE);
            System.out.print(e.getMessage());
            System.out.print("\nErro ao tentar estabelecer conexão com Kocmoc, verifique se o comando CREATE DATABASE kocmoc foi usado, verifique se os dados de acesso do banco de dados estão certos");
            System.exit(1);

        }

    }
}
