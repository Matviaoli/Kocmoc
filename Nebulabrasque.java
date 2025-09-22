import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Nebulabrasque extends JFrame {

    private String dbUrl = "jdbc:mysql://localhost:3306/kocmoc";
    private String dbUser = "root";
    private String dbPass = "mysql";

    private JPanel capitao, pipoquinha;
    private JLabel pacoca, batata, pipoca, batatinha;
    private JTextField pacoquinha;
    private JScrollPane xstep;
    private JComboBox<String> sequestro;

    private boolean menu = false, especifico = false, aura = false;

    public Nebulabrasque(){

        super("Kocmoc - Cosmos");

        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        capitao = new JPanel(new FlowLayout(FlowLayout.LEFT));

        batatinha = new JLabel("Pesquisar: ");

        pipoca = new JLabel("Pesquisar!");
        pipoca.setBackground(Color.LIGHT_GRAY);
        pipoca.setOpaque(true);
        pipoca.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pipoca.setCursor(new Cursor(Cursor.HAND_CURSOR));

        batata = new JLabel("Tecnologia Avançada");
        batata.setBackground(Color.LIGHT_GRAY);
        batata.setOpaque(true);
        batata.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        batata.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pacoca = new JLabel("⬅ Voltar");
        pacoca.setBackground(Color.LIGHT_GRAY);
        pacoca.setOpaque(true);
        pacoca.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pacoca.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pacoquinha = new JTextField("", 30);

        String[] tipagens = {"Produções", "Diretores", "Atores"};
        sequestro = new JComboBox<>(tipagens);
        sequestro.setSelectedItem("Produções");

        capitao.add(pacoca);
        capitao.add(batatinha);
        capitao.add(pacoquinha);
        capitao.add(pipoca);
        capitao.add(sequestro);
        capitao.add(batata);


        add(capitao, BorderLayout.NORTH);

        pipoquinha = new JPanel();
        pipoquinha.setLayout(new FlowerLayout(FlowLayout.LEFT, 10, 10));

        xstep= new JScrollPane(pipoquinha);
        xstep.setPreferredSize(new Dimension(780, 500));
        xstep.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        xstep.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        add(xstep, BorderLayout.CENTER);

        pacoca.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pipoquinha.removeAll();
                Ispy("Produções", "");
                pipoquinha.revalidate();
                pipoquinha.repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e){
                pacoca.setBackground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e){
                pacoca.setBackground(Color.LIGHT_GRAY);
            }
        });

        pipoca.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                String type = (String) sequestro.getSelectedItem();
                String termo = pacoquinha.getText();
                pipoquinha.removeAll();
                Ispy(type, termo);
                especifico = false;
                menu = true;
                pipoquinha.revalidate();
                pipoquinha.repaint();

            }

            @Override
            public void mouseEntered(MouseEvent e){
                pipoca.setBackground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e){
                pipoca.setBackground(Color.LIGHT_GRAY);
            }
        });

        batata.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showConfirmDialog(null, "Ainda não ta pronto\nPesquisa avançada em breve...", "Tecnolia avançada", JOptionPane.PLAIN_MESSAGE);
            }

            @Override
            public void mouseEntered(MouseEvent e){
                batata.setBackground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e){
                batata.setBackground(Color.LIGHT_GRAY);
            }
        });

        Ispy("Produções", "");

    }

    private void Ispy (String type, String beat){

        pipoquinha.removeAll();
        menu = true;
        aura = false;
        especifico = false;
        try(Connection conext = DriverManager.getConnection(dbUrl, dbUser, dbPass)){

            String querida = "";
            PreparedStatement semata;

            if(type.equals("Produções")){

                querida = "SELECT id_producoes, titulo_producoes FROM producoes WHERE titulo_producoes LIKE ?";
                semata = conext.prepareStatement(querida);
                semata.setString(1, "%" + beat + "%");
                ResultSet hahaha = semata.executeQuery();
                while (hahaha.next()){

                    int id = hahaha.getInt("id_producoes");
                    String nome = hahaha.getString("titulo_producoes");
                    pipoquinha.add(TeethFortress(nome, type, id)); //adicionar icone depois

                }

            }else if(type.equals("Diretores")){

                querida = "SELECT id_diretor, nome_diretor FROM diretores WHERE nome_diretor LIKE ?";
                semata = conext.prepareStatement(querida);
                semata.setString(1, "%" + beat + "%");
                ResultSet hahaha = semata.executeQuery();

                while(hahaha.next()){

                    int id = hahaha.getInt("id_diretor");
                    String nome = hahaha.getString("nome_diretor");
                    pipoquinha.add(TeethFortress(nome, type, id));

                }

            }else if(type.equals("Atores")){

                querida = "SELECT id_atores, nome_atores FROM atores WHERE nome_atores LIKE ?";
                semata = conext.prepareStatement(querida);
                semata.setString(1, "%" + beat + "%");
                ResultSet hahaha = semata.executeQuery();

                while (hahaha.next()) {

                    int id = hahaha.getInt("id_atores");
                    String nome = hahaha.getString("nome_atores");
                    pipoquinha.add(TeethFortress(nome, type, id));

                }

            }

        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null,"Deu ruim no SQL","Fudeu", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        pipoquinha.add(Criacao(type));

        pipoquinha.revalidate();
        pipoquinha.repaint();

    }

    private JLabel TeethFortress(String nome, String type, int id){

        //adicionar icone depois

        JLabel babel = new JLabel(nome, JLabel.CENTER);
        babel.setVerticalTextPosition(SwingConstants.BOTTOM);
        babel.setHorizontalTextPosition(SwingConstants.CENTER);
        babel.setPreferredSize(new Dimension(100, 100));
        babel.setOpaque(true);
        babel.setBackground(Color.WHITE);
        babel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        babel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        babel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                remake(type, id);

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                babel.setBackground(new Color(220, 220, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                babel.setBackground(Color.WHITE);
            }
        });

        return babel;

    }

    private void remake(String type, int id){

        pipoquinha.removeAll();
        especifico = true;
        menu = false;
        aura = false;

        StringBuilder builder = new StringBuilder();

        java.util.List<String> producoes = new ArrayList<>();
        //adicionar imagem depois

        try(Connection conext = DriverManager.getConnection(dbUrl, dbUser, dbPass)){
            PreparedStatement semata;
            ResultSet hahaha;

            if (type.equals("Produções")) {

                java.util.List<String> elencados = new ArrayList<>();
                java.util.List<String> generos = new ArrayList<>();

                semata = conext.prepareStatement(
                "SELECT p.titulo_producoes, p.data_producoes, d.nome_diretor " +
                    "FROM producoes p LEFT JOIN diretores d ON p.id_diretor = d.id_diretor " +
                    "WHERE p.id_producoes = ?"
                );

                semata.setInt(1, id);
                hahaha = semata.executeQuery();

                if(hahaha.next()){

                    builder.append("Título: ").append(hahaha.getString("titulo_producoes")).append("\n");
                    builder.append("Data: ").append(hahaha.getDate("data_producoes")).append("\n");
                    String ditador = hahaha.getString("nome_diretor");
                    builder.append("Diretor: ").append(ditador != null ? ditador : "Desconhecido").append("\n\n");



                    //adicionar função de poster depois

                }


                semata = conext.prepareStatement(
                "SELECT g.nome_genero FROM genero g " +
                    "JOIN producoes_generos pg ON g.id_genero = pg.id_genero " +
                    "WHERE pg.id_producoes = ?"
                );
                semata.setInt(1, id);
                hahaha = semata.executeQuery();
                while (hahaha.next()){

                    generos.add(hahaha.getString("nome_genero"));

                }

                builder.append("Gêneros: ").append(generos.isEmpty() ? "Nenhum gênero associado" : String.join(", ", generos)).append("\n\n");


                semata = conext.prepareStatement("" +
                    "SELECT a.nome_atores FROM atores a " +
                    "JOIN producoes_atores pa ON a.id_atores = pa.id_atores " +
                    "WHERE pa.id_producoes = ?"
                );

                while (hahaha.next()){

                    elencados.add(hahaha.getString("nome_atores"));

                }

                builder.append("Elenco: ").append(elencados.isEmpty() ? "Nenhum ator associado" : String.join(", ", elencados)).append("\n\n");

            }else if(type.equals("Diretores")){

                semata = conext.prepareStatement(
                "SELECT nome_diretor, nascimento_diretor, mandado_para_o_lobby_diretor " +
                    "FROM diretores WHERE id_diretor = ?"
                );
                semata.setInt(1, id);
                hahaha = semata.executeQuery();
                if(hahaha.next()){

                    String nome = hahaha.getString("nome_diretor");
                    Date nascimento = hahaha.getDate("nascimento_diretor");
                    Date falecimento = hahaha.getDate("mandado_para_o_lobby_diretor");

                    builder.append("Nome: ").append(nome).append("\n");
                    builder.append("Nascimento: ").append(nascimento).append("\n");

                    LocalDate nascimentoDate = nascimento.toLocalDate();
                    LocalDate dataFinal = falecimento != null ? falecimento.toLocalDate() : LocalDate.now();
                    int idade = Period.between(nascimentoDate, dataFinal).getYears();
                    builder.append("Idade: ").append(idade).append(falecimento != null ? " (na data de falecimento)" : "").append("\n");

                    if (falecimento != null) {
                        builder.append("Faleceu em: ").append(falecimento).append("\n");
                    } else {
                        builder.append("Estado: Vivo").append("\n");
                    }

                }

                semata = conext.prepareStatement(
                "SELECT titulo_producoes FROM producoes WHERE id_diretor = ?"
                );
                semata.setInt(1, id);
                hahaha = semata.executeQuery();

                while(hahaha.next()){

                    producoes.add(hahaha.getString("titulo_producoes"));

                }

                builder.append("Produções: ").append(producoes.isEmpty() ? "Nenhuma produção associada" : String.join(", ", producoes)).append("\n");

            } else if (type.equals("Atores")) {

                semata = conext.prepareStatement(
                        "SELECT nome_atores, nascimento_atores, mandado_para_o_lobby_atores " +
                                "FROM atores WHERE id_atores = ?");
                semata.setInt(1, id);
                hahaha = semata.executeQuery();
                if (hahaha.next()) {
                    String nome = hahaha.getString("nome_atores");
                    Date nascimento = hahaha.getDate("nascimento_atores");
                    Date falecimento = hahaha.getDate("mandado_para_o_lobby_atores");

                    builder.append("Nome: ").append(nome).append("\n");
                    builder.append("Nascimento: ").append(nascimento).append("\n");

                    LocalDate nascimentoDate = nascimento.toLocalDate();
                    LocalDate dataFinal = falecimento != null ? falecimento.toLocalDate() : LocalDate.now();
                    int idade = Period.between(nascimentoDate, dataFinal).getYears();
                    builder.append("Idade: ").append(idade).append(falecimento != null ? " (na data de falecimento)" : "").append("\n");

                    if (falecimento != null) {
                        builder.append("Faleceu em: ").append(falecimento).append("\n");
                    } else {
                        builder.append("Estado: Vivo").append("\n");
                    }
                }

                semata = conext.prepareStatement(
                "SELECT p.titulo_producoes FROM producoes p " +
                    "JOIN producoes_atores pa ON p.id_producoes = pa.id_producoes " +
                    "WHERE pa.id_atores = ?"
                );
                semata.setInt(1, id);
                hahaha = semata.executeQuery();
                while (hahaha.next()) {
                    producoes.add(hahaha.getString("titulo_producoes"));
                }
                builder.append("Produções: ").append(producoes.isEmpty() ? "Nenhuma produção associada" : String.join(", ", producoes)).append("\n");

            }

        } catch (SQLException e){

            JOptionPane.showConfirmDialog(null,"Deu ruim no SQL","Fudeu", JOptionPane.ERROR_MESSAGE);
            System.out.print("\nErro: " + e + "\nErro ao tentar acessar informações de uma celula sql");
            return;

        }

        JPanel cinema = new JPanel(new BorderLayout(10, 10));
        cinema.setPreferredSize(new Dimension(700, 500));

        JTextArea absoluto = new JTextArea(builder.toString());
        absoluto.setEditable(false);
        absoluto.setWrapStyleWord(true);
        absoluto.setLineWrap(true);

        JScrollPane ystep = new JScrollPane(absoluto);
        cinema.add(ystep, BorderLayout.CENTER);

        pipoquinha.add(cinema);
        pipoquinha.revalidate();
        pipoquinha.repaint();



    }

    private JLabel Criacao (String type){

        JLabel plus = new JLabel("+", JLabel.CENTER);
        plus.setPreferredSize(new Dimension(100, 100));
        plus.setOpaque(true);
        plus.setBackground(Color.WHITE);
        plus.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        plus.setCursor(new Cursor(Cursor.HAND_CURSOR));

        plus.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Plasma(type);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                plus.setBackground(new Color(220, 220, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                plus.setBackground(Color.WHITE);
            }
        });

        return (plus);
    }

    private void Plasma (String type){

        pipoquinha.removeAll();
        especifico = true;
        menu = false;
        aura = false;

        JPanel formacao = new JPanel(new GridLayout(0,2, 10, 10));
        formacao.setPreferredSize(new Dimension(700, 500));

        if(type.equals("Produções")){

            JTextField titulo_producao = new JTextField();
            JTextField data_producao = new JTextField();

            JComboBox<String> diretorCombo = new JComboBox<>();
            ComboMombo(diretorCombo, "Diretores");

            JList<String> generosList = new JList<>(ListaMombo("Genero"));
            generosList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            JScrollPane genStep = new JScrollPane(generosList);

            JList<String> atoresList = new JList<>(ListaMombo("Atores"));
            atoresList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            JScrollPane atoStep = new JScrollPane(atoresList);

            formacao.add(new JLabel("Título:"));
            formacao.add(titulo_producao);
            formacao.add(new JLabel("Data de Lançamento (YYYY-MM-DD):"));
            formacao.add(data_producao);
            formacao.add(new JLabel("Diretor:"));
            formacao.add(diretorCombo);
            formacao.add(new JLabel("Gêneros:"));
            formacao.add(genStep);
            formacao.add(new JLabel("Atores:"));
            formacao.add(atoStep);

            JButton salvar = new JButton("Salvar");
            salvar.addActionListener(e -> salveP(titulo_producao.getText(), data_producao.getText(), diretorCombo, generosList.getSelectedValuesList(), atoresList.getSelectedValuesList(), type));
            formacao.add(salvar);

        } else if (type.equals("Diretores")) {

            JTextField nomeField = new JTextField();
            JTextField nascimentoField = new JTextField();
            JTextField falecimentoField = new JTextField();

            formacao.add(new JLabel("Nome:"));
            formacao.add(nomeField);
            formacao.add(new JLabel("Data de Nascimento (YYYY-MM-DD):"));
            formacao.add(nascimentoField);
            formacao.add(new JLabel("Data de Falecimento (YYYY-MM-DD, opcional):"));
            formacao.add(falecimentoField);

            JButton salvar = new JButton("Salvar");
            salvar.addActionListener(e -> salveP2(nomeField.getText(), nascimentoField.getText(), falecimentoField.getText(), type));
            formacao.add(salvar);

        } else if (type.equals("Atores")) {

            JTextField nomeField = new JTextField();
            JTextField nascimentoField = new JTextField();
            JTextField falecimentoField = new JTextField();

            formacao.add(new JLabel("Nome:"));
            formacao.add(nomeField);
            formacao.add(new JLabel("Data de Nascimento (YYYY-MM-DD):"));
            formacao.add(nascimentoField);
            formacao.add(new JLabel("Data de Falecimento (YYYY-MM-DD, opcional):"));
            formacao.add(falecimentoField);

            JButton salvar = new JButton("Salvar");
            salvar.addActionListener(e -> salveP2(nomeField.getText(), nascimentoField.getText(), falecimentoField.getText(), type));
            formacao.add(salvar);

        }

        JScrollPane formStep = new JScrollPane(formacao);
        pipoquinha.add(formStep);
        pipoquinha.revalidate();
        pipoquinha.repaint();

    }

    private void ComboMombo(JComboBox<String> combo, String type){

        try (Connection conext = DriverManager.getConnection(dbUrl, dbUser, dbPass)) {
            String querida = type.equals("Diretores") ? "SELECT nome_diretor FROM diretores" : "SELECT nome_atores FROM atores";
            PreparedStatement semata = conext.prepareStatement(querida);
            ResultSet hahaha = semata.executeQuery();
            combo.addItem("Nenhum");
            while (hahaha.next()) {
                combo.addItem(hahaha.getString(1));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Deu ruim no SQL", "Fudeo", JOptionPane.ERROR_MESSAGE);
        }

    }

    private String[] ListaMombo(String type) {
        List<String> lista = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass)) {
            String query = type.equals("Genero") ? "SELECT nome_genero FROM genero" : "SELECT nome_atores FROM atores";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(rs.getString(1));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar " + type + ": " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return lista.toArray(new String[0]);
    }

    private void salveP(String titulo, String data, JComboBox<String> diretorCombo, List<String> generos, List<String> atores, String type){

        if(titulo.isEmpty() || data.isEmpty()){

            JOptionPane.showMessageDialog(null, "Esta se esquecendo de algo?", "Usuários...", JOptionPane.ERROR_MESSAGE);
            return;

        }

        try (Connection conext = DriverManager.getConnection(dbUrl, dbUser, dbPass)) {

            PreparedStatement semata = conext.prepareStatement(
            "INSERT INTO producoes (titulo_producoes, data_producoes, id_diretor) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );
            semata.setString(1, titulo);
            semata.setDate(2, Date.valueOf(data));
            String diretorNome = (String) diretorCombo.getSelectedItem();

            if (diretorNome.equals("Nenhum")) {

                semata.setNull(3, Types.INTEGER);

            } else {
                PreparedStatement diretorSematou = conext.prepareStatement("SELECT id_diretor FROM diretores WHERE nome_diretor = ?");
                diretorSematou.setString(1, diretorNome);
                ResultSet hahaha = diretorSematou.executeQuery();

                if (hahaha.next()) {

                    semata.setInt(3, hahaha.getInt("id_diretor"));

                } else {

                    semata.setNull(3, Types.INTEGER);

                }
            }

            semata.executeUpdate();
            ResultSet chaves = semata.getGeneratedKeys();
            int producaoId = chaves.next() ? chaves.getInt(1) : -1;


            for (String genero : generos) {

                PreparedStatement genSemata = conext.prepareStatement("SELECT id_genero FROM genero WHERE nome_genero = ?");
                genSemata.setString(1, genero);
                ResultSet hahaha = genSemata.executeQuery();

                if (hahaha.next()) {

                    PreparedStatement pgSemata = conext.prepareStatement("INSERT INTO producoes_generos (id_producoes, id_genero) VALUES (?, ?)");
                    pgSemata.setInt(1, producaoId);
                    pgSemata.setInt(2, hahaha.getInt("id_genero"));
                    pgSemata.executeUpdate();

                }
            }

            for (String ator : atores) {

                PreparedStatement atorSematou = conext.prepareStatement("SELECT id_atores FROM atores WHERE nome_atores = ?");
                atorSematou.setString(1, ator);
                ResultSet rs = atorSematou.executeQuery();

                if (rs.next()) {

                    PreparedStatement paSemata = conext.prepareStatement("INSERT INTO producoes_atores (id_producoes, id_atores) VALUES (?, ?)");
                    paSemata.setInt(1, producaoId);
                    paSemata.setInt(2, rs.getInt("id_atores"));
                    paSemata.executeUpdate();

                }
            }

            JOptionPane.showMessageDialog(null, "Produção adicionada com sucesso!");
            Ispy(type, "");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Deu ruim no SQL", "Fudeo", JOptionPane.ERROR_MESSAGE);
        }


    }

    private void salveP2(String nome, String nascimento, String mandadoParaOLobby, String type) {
        if (nome.isEmpty() || nascimento.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass)) {
            String table = type.equals("Diretores") ? "diretores" : "atores";
            String nomeCol = type.equals("Diretores") ? "nome_diretor" : "nome_atores";
            String nascimentoCol = type.equals("Diretores") ? "nascimento_diretor" : "nascimento_atores";
            String falecimentoCol = type.equals("Diretores") ? "mandado_para_o_lobby_diretor" : "mandado_para_o_lobby_atores";

            PreparedStatement semata = conn.prepareStatement(
                    "INSERT INTO " + table + " (" + nomeCol + ", " + nascimentoCol + ", " + falecimentoCol + ") VALUES (?, ?, ?)"
            );
            semata.setString(1, nome);
            semata.setDate(2, Date.valueOf(nascimento));
            if (mandadoParaOLobby.isEmpty()) {
                semata.setNull(3, Types.DATE);
            } else {
                semata.setDate(3, Date.valueOf(mandadoParaOLobby));
            }
            semata.executeUpdate();

            JOptionPane.showMessageDialog(null, type.substring(0, type.length() - 1) + " adicionado com sucesso!");
            Ispy(type, "");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Deu ruim no SQL", "Fudeo", JOptionPane.ERROR_MESSAGE);
        }
    }

}

