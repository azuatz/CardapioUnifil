package view;

import dao.ConnectionFactory;
import dao.ItemDAO;
import dto.Item;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class CardapioView extends JFrame {

    private final Color LARANJA = new Color(255,120,0);
    private final Color LARANJA_CLARO = new Color(255,170,80);
    private final Color FUNDO = new Color(255,245,235);
    private final Color AZUL = new Color(0,45,95);

    public CardapioView(){

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

        return card;
    }

    public static void main(String[] args){

        SwingUtilities.invokeLater(() -> {
            new CardapioView().setVisible(true);
        });

    }
}