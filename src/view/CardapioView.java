package view;

import dao.AdminDAO;
import dao.ConnectionFactory;
import dao.ItemDAO;
import dto.Item;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class CardapioView extends JFrame {

    private boolean admin;

    private final Color LARANJA = new Color(255,120,0);
    private final Color LARANJA_CLARO = new Color(255,170,80);
    private final Color FUNDO = new Color(255,245,235);
    private final Color AZUL = new Color(0,45,95);

    public CardapioView(boolean admin){

        this.admin = admin;

        setTitle("Cardápio UniFil");
        setSize(900,550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel tela = new JPanel(new BorderLayout());
        tela.setBackground(FUNDO);

        // HEADER
        JPanel header = new JPanel();
        header.setBackground(LARANJA);
        header.setBorder(new EmptyBorder(20,10,20,10));

        JLabel titulo = new JLabel("CARDÁPIO UNIFIL");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial",Font.BOLD,34));

        header.add(titulo);

        tela.add(header,BorderLayout.NORTH);

        // PAINEL DOS CARDS
        JPanel painelCards = new JPanel(new GridLayout(0,3,25,25));
        painelCards.setBackground(FUNDO);
        painelCards.setBorder(new EmptyBorder(30,30,30,30));

        Connection con = ConnectionFactory.abrirConexao();
        ItemDAO dao = new ItemDAO(con);

        List<Item> itens = dao.listar();

        for(Item item : itens){
            painelCards.add(criarCard(item));
        }

        JScrollPane scroll = new JScrollPane(painelCards);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(FUNDO);

        tela.add(scroll,BorderLayout.CENTER);

        // BOTÃO ADMIN
        if(admin){

            JButton adicionar = new JButton("Adicionar Item");
            adicionar.setBackground(LARANJA);
            adicionar.setForeground(Color.WHITE);
            adicionar.setFont(new Font("Arial",Font.BOLD,16));

            adicionar.addActionListener(e -> {

                String nome = JOptionPane.showInputDialog("Nome do item");

                if(nome == null || nome.isEmpty()){
                    JOptionPane.showMessageDialog(null,"Nome inválido");
                    return;
                }

                String precoStr = JOptionPane.showInputDialog("Preço");

                try{

                    double preco = Double.parseDouble(precoStr);

                    int popularOpcao = JOptionPane.showConfirmDialog(
                            null,
                            "Item é popular?",
                            "Popular",
                            JOptionPane.YES_NO_OPTION
                    );

                    boolean popular = (popularOpcao == JOptionPane.YES_OPTION);

                    Item novo = new Item();
                    novo.setNome(nome);
                    novo.setPreco(preco);
                    novo.setPopular(popular);

                    // NOVA CONEXÃO
                    Connection con2 = ConnectionFactory.abrirConexao();
                    ItemDAO dao2 = new ItemDAO(con2);

                    String resultado = dao2.inserir(novo);

                    ConnectionFactory.fecharConexao(con2);

                    JOptionPane.showMessageDialog(null, resultado);

                    dispose();
                    new CardapioView(admin).setVisible(true);

                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null,"Erro ao adicionar item");
                }

            });

            tela.add(adicionar,BorderLayout.SOUTH);
        }

        add(tela);

        ConnectionFactory.fecharConexao(con);
    }

    private JPanel criarCard(Item item){

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card,BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LARANJA_CLARO,2),
                new EmptyBorder(20,20,20,20)
        ));

        JLabel nome = new JLabel(item.getNome());
        nome.setFont(new Font("Arial",Font.BOLD,22));
        nome.setForeground(AZUL);
        nome.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel preco = new JLabel("R$ " + item.getPreco());
        preco.setFont(new Font("Arial",Font.BOLD,26));
        preco.setForeground(LARANJA);
        preco.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel popular = new JLabel();

        if(item.isPopular()){
            popular.setText("⭐ Popular");
            popular.setFont(new Font("Segoe UI Emoji",Font.BOLD,15));
            popular.setForeground(new Color(255,140,0));
        }

        popular.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createRigidArea(new Dimension(0,10)));
        card.add(nome);
        card.add(Box.createRigidArea(new Dimension(0,15)));
        card.add(preco);
        card.add(Box.createRigidArea(new Dimension(0,10)));
        card.add(popular);

        // BOTÕES ADMIN
        if(admin){

            JButton editar = new JButton("Editar");
            JButton deletar = new JButton("Excluir");

            editar.setAlignmentX(Component.CENTER_ALIGNMENT);
            deletar.setAlignmentX(Component.CENTER_ALIGNMENT);

            editar.addActionListener(e -> {

                String novoNome = JOptionPane.showInputDialog("Novo nome",item.getNome());
                String novoPreco = JOptionPane.showInputDialog("Novo preço",item.getPreco());

                try{

                    item.setNome(novoNome);
                    item.setPreco(Double.parseDouble(novoPreco));

                    Connection con = ConnectionFactory.abrirConexao();
                    ItemDAO dao = new ItemDAO(con);

                    dao.alterar(item);

                    ConnectionFactory.fecharConexao(con);

                    JOptionPane.showMessageDialog(null,"Item atualizado!");
                    dispose();
                    new CardapioView(true).setVisible(true);

                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null,"Erro ao atualizar");
                }

            });

            deletar.addActionListener(e -> {

                int confirm = JOptionPane.showConfirmDialog(null,
                        "Excluir item?",
                        "Confirmar",
                        JOptionPane.YES_NO_OPTION);

                if(confirm == JOptionPane.YES_OPTION){

                    Connection con = ConnectionFactory.abrirConexao();
                    ItemDAO dao = new ItemDAO(con);

                    dao.excluir(item.getID());

                    ConnectionFactory.fecharConexao(con);

                    JOptionPane.showMessageDialog(null,"Item excluído!");
                    dispose();
                    new CardapioView(true).setVisible(true);
                }

            });

            card.add(Box.createRigidArea(new Dimension(0,10)));
            card.add(editar);
            card.add(Box.createRigidArea(new Dimension(0,5)));
            card.add(deletar);
        }

        return card;
    }

    public static void main(String[] args){

        boolean admin = false;

        int opcao = JOptionPane.showConfirmDialog(
                null,
                "Entrar como administrador?",
                "Login",
                JOptionPane.YES_NO_OPTION
        );

        if(opcao == JOptionPane.YES_OPTION){

            String usuario = JOptionPane.showInputDialog("Usuário");
            String senha = JOptionPane.showInputDialog("Senha");

            Connection con = ConnectionFactory.abrirConexao();
            AdminDAO dao = new AdminDAO(con);

            admin = dao.login(usuario, senha);

            ConnectionFactory.fecharConexao(con);
        }

        boolean finalAdmin = admin;

        SwingUtilities.invokeLater(() -> {
            new CardapioView(finalAdmin).setVisible(true);
        });

    }
}