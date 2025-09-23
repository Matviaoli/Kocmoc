                import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Nebulabrasque extends JFrame {

    private String dbUrl = "jdbc:mysql://localhost:3306/kocmoc";
    private String dbUser = "root";
    private String dbPass = "13102201";

    private JPanel capitao, pipoquinha;
    private JLabel pacoca, batata, pipoca, batatinha;
    private JTextField pacoquinha;
    private JScrollPane xstep;
    private JComboBox<String> sequestro;

    private boolean menu = false, especifico = false, aura = false, criacao = false;

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
        criacao = false;
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
            Users();
            System.out.print("\nErro: " + e.getMessage());
            System.out.print("\nErro ao tentar gerar interface, função: Ispy\n\n");
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

    private void remake(String type, int id) {
        pipoquinha.removeAll();
        especifico = true;
        menu = false;
        aura = false;

        StringBuilder builder = new StringBuilder();
        java.util.List<String> producoes = new ArrayList<>();
        final String nomeEntidade; // Declared as final

        try (Connection conext = DriverManager.getConnection(dbUrl, dbUser, dbPass)) {
            PreparedStatement semata;
            ResultSet hahaha;

            if (type.equals("Produções")) {
                java.util.List<String> elencados = new ArrayList<>();
                java.util.List<String> generos = new ArrayList<>();

                semata = conext.prepareStatement(
                        "SELECT p.titulo_producoes, p.data_producoes, p.numepisodios_producoes, d.nome_diretor " +
                                "FROM producoes p LEFT JOIN diretores d ON p.id_diretor = d.id_diretor " +
                                "WHERE p.id_producoes = ?"
                );
                semata.setInt(1, id);
                hahaha = semata.executeQuery();

                if (hahaha.next()) {
                    nomeEntidade = hahaha.getString("titulo_producoes");
                    builder.append("Título: ").append(nomeEntidade).append("\n");
                    builder.append("Data: ").append(hahaha.getDate("data_producoes")).append("\n");
                    String ditador = hahaha.getString("nome_diretor");
                    builder.append("Diretor: ").append(ditador != null ? ditador : "Desconhecido").append("\n");
                    int numEpisodios = hahaha.getInt("numepisodios_producoes");
                    if (hahaha.wasNull() || numEpisodios == 0) {
                        builder.append("Tipo: Filme").append("\n\n");
                    } else {
                        builder.append("Tipo: Série (").append(numEpisodios).append(" episódios)\n\n");
                    }
                } else {
                    nomeEntidade = "Produção desconhecida";
                }

                semata = conext.prepareStatement(
                        "SELECT g.nome_genero FROM genero g " +
                                "JOIN producoes_generos pg ON g.id_genero = pg.id_genero " +
                                "WHERE pg.id_producoes = ?"
                );
                semata.setInt(1, id);
                hahaha = semata.executeQuery();
                while (hahaha.next()) {
                    generos.add(hahaha.getString("nome_genero"));
                }
                builder.append("Gêneros: ").append(generos.isEmpty() ? "Nenhum gênero associado" : String.join(", ", generos)).append("\n\n");

                semata = conext.prepareStatement(
                        "SELECT a.nome_atores FROM atores a " +
                                "JOIN producoes_atores pa ON a.id_atores = pa.id_atores " +
                                "WHERE pa.id_producoes = ?"
                );
                semata.setInt(1, id);
                hahaha = semata.executeQuery();
                while (hahaha.next()) {
                    elencados.add(hahaha.getString("nome_atores"));
                }
                builder.append("Elenco: ").append(elencados.isEmpty() ? "Nenhum ator associado" : String.join(", ", elencados)).append("\n\n");

            } else if (type.equals("Diretores")) {
                semata = conext.prepareStatement(
                        "SELECT nome_diretor, nascimento_diretor, mandado_para_o_lobby_diretor " +
                                "FROM diretores WHERE id_diretor = ?"
                );
                semata.setInt(1, id);
                hahaha = semata.executeQuery();
                if (hahaha.next()) {
                    nomeEntidade = hahaha.getString("nome_diretor");
                    builder.append("Nome: ").append(nomeEntidade).append("\n");
                    Date nascimento = hahaha.getDate("nascimento_diretor");
                    builder.append("Nascimento: ").append(nascimento).append("\n");
                    Date falecimento = hahaha.getDate("mandado_para_o_lobby_diretor");

                    LocalDate nascimentoDate = nascimento.toLocalDate();
                    LocalDate dataFinal = falecimento != null ? falecimento.toLocalDate() : LocalDate.now();
                    int idade = Period.between(nascimentoDate, dataFinal).getYears();
                    builder.append("Idade: ").append(idade).append(falecimento != null ? " (na data de falecimento)" : "").append("\n");

                    if (falecimento != null) {
                        builder.append("Faleceu em: ").append(falecimento).append("\n");
                    } else {
                        builder.append("Estado: Vivo").append("\n");
                    }
                } else {
                    nomeEntidade = "Diretor desconhecido";
                }

                semata = conext.prepareStatement(
                        "SELECT titulo_producoes FROM producoes WHERE id_diretor = ?"
                );
                semata.setInt(1, id);
                hahaha = semata.executeQuery();
                while (hahaha.next()) {
                    producoes.add(hahaha.getString("titulo_producoes"));
                }
                builder.append("Produções: ").append(producoes.isEmpty() ? "Nenhuma produção associada" : String.join(", ", producoes)).append("\n");

            } else {
                semata = conext.prepareStatement(
                        "SELECT nome_atores, nascimento_atores, mandado_para_o_lobby_atores " +
                                "FROM atores WHERE id_atores = ?"
                );
                semata.setInt(1, id);
                hahaha = semata.executeQuery();
                if (hahaha.next()) {
                    nomeEntidade = hahaha.getString("nome_atores");
                    builder.append("Nome: ").append(nomeEntidade).append("\n");
                    Date nascimento = hahaha.getDate("nascimento_atores");
                    builder.append("Nascimento: ").append(nascimento).append("\n");
                    Date falecimento = hahaha.getDate("mandado_para_o_lobby_atores");

                    LocalDate nascimentoDate = nascimento.toLocalDate();
                    LocalDate dataFinal = falecimento != null ? falecimento.toLocalDate() : LocalDate.now();
                    int idade = Period.between(nascimentoDate, dataFinal).getYears();
                    builder.append("Idade: ").append(idade).append(falecimento != null ? " (na data de falecimento)" : "").append("\n");

                    if (falecimento != null) {
                        builder.append("Faleceu em: ").append(falecimento).append("\n");
                    } else {
                        builder.append("Estado: Vivo").append("\n");
                    }
                } else {
                    nomeEntidade = "Ator desconhecido";
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

            // Criar painel de detalhes
            JPanel cinema = new JPanel(new BorderLayout(10, 10));
            cinema.setPreferredSize(new Dimension(700, 500));

            JTextArea absoluto = new JTextArea(builder.toString());
            absoluto.setEditable(false);
            absoluto.setWrapStyleWord(true);
            absoluto.setLineWrap(true);
            absoluto.setFont(new Font("Arial", Font.PLAIN, 14));

            JScrollPane ystep = new JScrollPane(absoluto);
            cinema.add(ystep, BorderLayout.CENTER);

            // Botão Deletar
            JButton deletar = new JButton("Deletar");
            deletar.setFont(new Font("Arial", Font.BOLD, 14));
            deletar.setBackground(new Color(255, 100, 100)); // Vermelho para indicar exclusão
            deletar.setForeground(Color.WHITE);
            deletar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            deletar.setPreferredSize(new Dimension(100, 30));
            deletar.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    deletar.setBackground(new Color(255, 120, 120));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    deletar.setBackground(new Color(255, 100, 100));
                }
            });
            deletar.addActionListener(e -> {
                int confirmacao = JOptionPane.showConfirmDialog(
                        null,
                        "Deseja realmente excluir " + nomeEntidade + "?",
                        "Confirmar Exclusão",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (confirmacao == JOptionPane.YES_OPTION) {
                    try (Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPass)) {
                        String query;
                        if (type.equals("Produções")) {
                            query = "DELETE FROM producoes WHERE id_producoes = ?";
                        } else if (type.equals("Diretores")) {
                            query = "DELETE FROM diretores WHERE id_diretor = ?";
                        } else {
                            query = "DELETE FROM atores WHERE id_atores = ?";
                        }
                        PreparedStatement stmt = con.prepareStatement(query);
                        stmt.setInt(1, id);
                        int rowsAffected = stmt.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(null, type.substring(0, type.length() - 1) + " excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                            pipoquinha.removeAll();
                            Ispy(type, "");
                            pipoquinha.revalidate();
                            pipoquinha.repaint();
                        } else {
                            JOptionPane.showMessageDialog(null, "Erro ao excluir " + type.substring(0, type.length() - 1) + "!", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Deu ruim no SQL: " + ex.getMessage(), "Fudeu", JOptionPane.ERROR_MESSAGE);
                        System.out.println("Erro completo: " + ex);
                    }
                }
            });

            JPanel botaoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            botaoPanel.setBackground(Color.WHITE);
            botaoPanel.add(deletar);
            cinema.add(botaoPanel, BorderLayout.SOUTH);

            pipoquinha.add(cinema);
            pipoquinha.revalidate();
            pipoquinha.repaint();

        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, "Deu ruim no SQL: " + e.getMessage(), "Fudeu", JOptionPane.ERROR_MESSAGE);
            System.out.println("Erro completo: " + e);
            return;
        }
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

    private void Plasma(String type) {
        pipoquinha.removeAll();
        especifico = false;
        menu = false;
        aura = false;
        criacao = true;

        JPanel formacao = new JPanel(new BorderLayout(10, 10));
        formacao.setBackground(Color.WHITE);
        formacao.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Adicionar " + type.substring(0, type.length() - 1));
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        formacao.add(titulo, BorderLayout.NORTH);

        if (type.equals("Produções")) {

            JPanel textoPanel = new JPanel(new GridBagLayout());
            textoPanel.setBackground(Color.WHITE);
            textoPanel.setBorder(BorderFactory.createTitledBorder("Informações Básicas"));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.WEST;

            JTextField tituloProducao = new JTextField(20);
            tituloProducao.setFont(new Font("Arial", Font.PLAIN, 14));
            JTextField dataProducao = new JTextField(20);
            dataProducao.setFont(new Font("Arial", Font.PLAIN, 14));
            JTextField numEpisodiosProducoes = new JTextField(20);
            numEpisodiosProducoes.setFont(new Font("Arial", Font.PLAIN, 14));

            gbc.gridx = 0;
            gbc.gridy = 0;
            textoPanel.add(new JLabel("Título *:"), gbc);
            gbc.gridx = 1;
            textoPanel.add(tituloProducao, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            textoPanel.add(new JLabel("Data de Lançamento (YYYY-MM-DD) *:"), gbc);
            gbc.gridx = 1;
            textoPanel.add(dataProducao, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            textoPanel.add(new JLabel("Número de Episódios (opcional):"), gbc);
            gbc.gridx = 1;
            textoPanel.add(numEpisodiosProducoes, gbc);

            JPanel selecaoPanel = new JPanel(new GridBagLayout());
            selecaoPanel.setBackground(Color.WHITE);
            selecaoPanel.setBorder(BorderFactory.createTitledBorder("Seleções"));

            JTextField diretorFiltro = new JTextField(20);
            diretorFiltro.setFont(new Font("Arial", Font.PLAIN, 14));
            JComboBox<String> diretorCombo = new JComboBox<>();
            diretorCombo.setFont(new Font("Arial", Font.PLAIN, 14));
            ComboMombo(diretorCombo, "Diretores", "");

            diretorFiltro.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    ComboMombo(diretorCombo, "Diretores", diretorFiltro.getText());
                }
            });

            JPanel generosPanel = new JPanel(new GridLayout(0, 1));
            generosPanel.setBackground(Color.WHITE);
            JScrollPane genStep = new JScrollPane(generosPanel);
            genStep.setPreferredSize(new Dimension(200, 100));
            List<JCheckBox> generosCheckboxes = new ArrayList<>();
            for (String genero : ListaMombo("Genero")) {
                JCheckBox cb = new JCheckBox(genero);
                cb.setFont(new Font("Arial", Font.PLAIN, 14));
                generosCheckboxes.add(cb);
                generosPanel.add(cb);
            }

            JTextField atoresFiltro = new JTextField(20);
            atoresFiltro.setFont(new Font("Arial", Font.PLAIN, 14));
            JPanel atoresPanel = new JPanel(new GridLayout(0, 1));
            atoresPanel.setBackground(Color.WHITE);
            JScrollPane atoStep = new JScrollPane(atoresPanel);
            atoStep.setPreferredSize(new Dimension(200, 100));
            List<JCheckBox> atoresCheckboxes = new ArrayList<>();
            for (String ator : ListaMombo("Atores")) {
                JCheckBox cb = new JCheckBox(ator);
                cb.setFont(new Font("Arial", Font.PLAIN, 14));
                atoresCheckboxes.add(cb);
                atoresPanel.add(cb);
            }

            atoresFiltro.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    String filtro = atoresFiltro.getText().toLowerCase();
                    atoresPanel.removeAll();
                    for (JCheckBox cb : atoresCheckboxes) {
                        if (cb.getText().toLowerCase().contains(filtro)) {
                            atoresPanel.add(cb);
                        }
                    }
                    atoresPanel.revalidate();
                    atoresPanel.repaint();
                }
            });

            gbc.gridx = 0;
            gbc.gridy = 0;
            selecaoPanel.add(new JLabel("Filtrar Diretores:"), gbc);
            gbc.gridx = 1;
            selecaoPanel.add(diretorFiltro, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            selecaoPanel.add(new JLabel("Diretor:"), gbc);
            gbc.gridx = 1;
            selecaoPanel.add(diretorCombo, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            selecaoPanel.add(new JLabel("Gêneros:"), gbc);
            gbc.gridx = 1;
            selecaoPanel.add(genStep, gbc);

            gbc.gridx = 0;
            gbc.gridy = 3;
            selecaoPanel.add(new JLabel("Filtrar Atores:"), gbc);
            gbc.gridx = 1;
            selecaoPanel.add(atoresFiltro, gbc);

            gbc.gridx = 0;
            gbc.gridy = 4;
            selecaoPanel.add(new JLabel("Atores:"), gbc);
            gbc.gridx = 1;
            selecaoPanel.add(atoStep, gbc);

            JPanel conteudoPanel = new JPanel(new BorderLayout(10, 10));
            conteudoPanel.setBackground(Color.WHITE);
            conteudoPanel.add(textoPanel, BorderLayout.NORTH);
            conteudoPanel.add(selecaoPanel, BorderLayout.CENTER);

            formacao.add(conteudoPanel, BorderLayout.CENTER);

            JButton salvar = new JButton("Salvar");
            salvar.setFont(new Font("Arial", Font.BOLD, 14));
            salvar.setBackground(new Color(100, 150, 255));
            salvar.setForeground(Color.WHITE);
            salvar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            salvar.setPreferredSize(new Dimension(100, 30));
            salvar.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    salvar.setBackground(new Color(120, 170, 255));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    salvar.setBackground(new Color(100, 150, 255));
                }
            });
            salvar.addActionListener(e -> {
                List<String> selectedGeneros = generosCheckboxes.stream()
                        .filter(JCheckBox::isSelected)
                        .map(JCheckBox::getText)
                        .collect(Collectors.toList());
                List<String> selectedAtores = atoresCheckboxes.stream()
                        .filter(JCheckBox::isSelected)
                        .map(JCheckBox::getText)
                        .collect(Collectors.toList());
                salveP(tituloProducao.getText(), dataProducao.getText(), numEpisodiosProducoes.getText(),
                        diretorCombo, selectedGeneros, selectedAtores, type);
            });

            JPanel botaoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            botaoPanel.setBackground(Color.WHITE);
            botaoPanel.add(salvar);
            formacao.add(botaoPanel, BorderLayout.SOUTH);

        } else if (type.equals("Diretores") || type.equals("Atores")) {
            JPanel textoPanel = new JPanel(new GridBagLayout());
            textoPanel.setBackground(Color.WHITE);
            textoPanel.setBorder(BorderFactory.createTitledBorder("Informações Pessoais"));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.WEST;

            JTextField nomeField = new JTextField(20);
            nomeField.setFont(new Font("Arial", Font.PLAIN, 14));
            JTextField nascimentoField = new JTextField(20);
            nascimentoField.setFont(new Font("Arial", Font.PLAIN, 14));
            JTextField falecimentoField = new JTextField(20);
            falecimentoField.setFont(new Font("Arial", Font.PLAIN, 14));

            gbc.gridx = 0;
            gbc.gridy = 0;
            textoPanel.add(new JLabel("Nome *:"), gbc);
            gbc.gridx = 1;
            textoPanel.add(nomeField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            textoPanel.add(new JLabel("Data de Nascimento (YYYY-MM-DD) *:"), gbc);
            gbc.gridx = 1;
            textoPanel.add(nascimentoField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            textoPanel.add(new JLabel("Data de Falecimento (opcional):"), gbc);
            gbc.gridx = 1;
            textoPanel.add(falecimentoField, gbc);

            JButton salvar = new JButton("Salvar");
            salvar.setFont(new Font("Arial", Font.BOLD, 14));
            salvar.setBackground(new Color(100, 150, 255));
            salvar.setForeground(Color.WHITE);
            salvar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            salvar.setPreferredSize(new Dimension(100, 30));
            salvar.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    salvar.setBackground(new Color(120, 170, 255));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    salvar.setBackground(new Color(100, 150, 255));
                }
            });
            salvar.addActionListener(e -> salveP2(nomeField.getText(), nascimentoField.getText(), falecimentoField.getText(), type));

            JPanel botaoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            botaoPanel.setBackground(Color.WHITE);
            botaoPanel.add(salvar);

            formacao.add(textoPanel, BorderLayout.NORTH);
            formacao.add(botaoPanel, BorderLayout.SOUTH);
        }

        JScrollPane formStep = new JScrollPane(formacao);
        formStep.setBorder(BorderFactory.createEmptyBorder());
        pipoquinha.add(formStep);
        pipoquinha.revalidate();
        pipoquinha.repaint();
    }

    private void ComboMombo(JComboBox<String> combo, String type, String filtro) {
        combo.removeAllItems();
        try (Connection conext = DriverManager.getConnection(dbUrl, dbUser, dbPass)) {
            String querida = type.equals("Diretores") ?
                    "SELECT nome_diretor FROM diretores WHERE nome_diretor LIKE ?" :
                    "SELECT nome_atores FROM atores WHERE nome_atores LIKE ?";
            PreparedStatement semata = conext.prepareStatement(querida);
            semata.setString(1, "%" + filtro + "%");
            ResultSet hahaha = semata.executeQuery();
            combo.addItem("Nenhum");
            while (hahaha.next()) {
                combo.addItem(hahaha.getString(1));
            }
        } catch (SQLException e) {
            Users();
            System.out.print("\nErro: " + e.getMessage());
            System.out.print("\nErro ao tentar gerar caixa de combo, função: ComboMombo\n\n");
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

            Users();
            System.out.print("\nErro: " + e.getMessage());
            System.out.print("\nErro ao tentar gerar Lista, função: ListaMombo\n\n");

        }

        return lista.toArray(new String[0]);
    }

    private void salveP(String titulo, String data, String episodios, JComboBox<String> diretorCombo, List<String> generos, List<String> atores, String type){

        if(titulo.isEmpty() || data.isEmpty()){

            Users();
            System.out.print("\nErro ao tentar salvar produção, titulo ou data imprecisa ou nula\n\n");

        }

        try (Connection conext = DriverManager.getConnection(dbUrl, dbUser, dbPass)) {

            Integer numEpisodios = null;

            if (!episodios.isEmpty()) {

                try {

                    numEpisodios = Integer.parseInt(episodios);
                    if (numEpisodios < 0) {

                        JOptionPane.showMessageDialog(null, "Episodio -1?", "Usu", JOptionPane.ERROR_MESSAGE);
                        return;

                    }

                } catch (NumberFormatException e) {

                    Users();
                    System.out.print("\nErro: " + e.getMessage());
                    System.out.print("\nErro ao tentar salvar produção, função: SalveP\n\n");

                }
            }

            PreparedStatement semata = conext.prepareStatement(
                    "INSERT INTO producoes (titulo_producoes, data_producoes, numepisodios_producoes, id_diretor) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            semata.setString(1, titulo);
            semata.setDate(2, Date.valueOf(data));
            if (numEpisodios == null || numEpisodios == 0) {
                semata.setNull(3, Types.SMALLINT);
            } else {
                semata.setInt(3, numEpisodios);
            }
            String diretorNome = (String) diretorCombo.getSelectedItem();

            if (diretorNome.equals("Nenhum")) {
                semata.setNull(4, Types.INTEGER);
            } else {
                PreparedStatement diretorSematou = conext.prepareStatement("SELECT id_diretor FROM diretores WHERE nome_diretor = ?");
                diretorSematou.setString(1, diretorNome);
                ResultSet hahaha = diretorSematou.executeQuery();
                if (hahaha.next()) {
                    semata.setInt(4, hahaha.getInt("id_diretor"));
                } else {
                    semata.setNull(4, Types.INTEGER);
                }
            }

            int rowsAffected = semata.executeUpdate();
            if (rowsAffected == 0) {
                JOptionPane.showMessageDialog(null, "Erro ao inserir produção!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ResultSet chaves = semata.getGeneratedKeys();
            int producaoId = chaves.next() ? chaves.getInt(1) : -1;
            if (producaoId == -1) {
                JOptionPane.showMessageDialog(null, "Erro ao gerar ID da produção!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

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
            Users();
            System.out.print("\nErro: " + e.getMessage());
            System.out.print("\nErro ao tentar salvar produção, função: SalveP\n\n");
        }
    }

    private void salveP2(String nome, String nascimento, String mandadoParaOLobby, String type) {
        if (nome.isEmpty() || nascimento.isEmpty()) {
            Users();
            System.out.print("\nErro ao tentar salvar pessoa, nome ou data está vazio\n\n");
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
            Users();
            System.out.print("\nErro: " + e.getMessage());
            System.out.print("\nErro ao salvar pessoa, função: SalveP2\n\n");
        }
    }

    public void Users(){

        Random rogerio = new Random();
        int x = rogerio.nextInt(10), y = rogerio.nextInt(10);
        String[] titulo = {
                "Erro seu!",
                "Clicou errado!",
                "Usuários...",
                "ERRO ERRO",
                "Burro",
                "Fudeo",
                "Bad",
                "Deslike",
                "Unleshed",
                "Falha épica!"
        };
        String[] mensagem = {
                "Ops, parece que você apertou o botão errado... de novo!",
                "Erro 404: Sua habilidade de clicar sumiu!",
                "Kocmoc te olha com desgosto",
                "Vai á algum lugar?",
                "Está se esquecendo de algo?",
                "Erro crítico: Falta de café detectada!",
                "Parabéns, você encontrou o erro mais raro: o seu!",
                "Sistema confuso com suas escolhas... Tenta de novo?",
                "Algo deu errado, e aposto que foi sua digitação!",
                "Por isso que sua familia come pizza sem você."
        };

        JOptionPane.showMessageDialog(null, mensagem[x], titulo[y], JOptionPane.ERROR_MESSAGE);

    }

}
