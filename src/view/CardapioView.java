package view;

import dao.ConnectionFactory;
import dao.ItemDAO;
import dto.Item;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class CardapioView extends JFrame {

    public CardapioView() {

        setTitle("🍔 Cardápio Digital");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel tela = new JPanel(new BorderLayout());

        JLabel titulo = new JLabel("CARDÁPIO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 30));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        tela.add(titulo, BorderLayout.NORTH);

        JPanel painelCards = new JPanel();
        painelCards.setLayout(new GridLayout(0, 3, 20, 20));
        painelCards.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelCards.setBackground(new Color(245, 245, 245));

        Connection con = ConnectionFactory.abrirConexao();
        ItemDAO dao = new ItemDAO(con);

        List<Item> itens = dao.listar();

        for (Item item : itens) {

            painelCards.add(criarCard(item));

        }

        JScrollPane scroll = new JScrollPane(painelCards);
        scroll.setBorder(null);

        tela.add(scroll, BorderLayout.CENTER);

        add(tela);

        ConnectionFactory.fecharConexao(con);
    }

    private JPanel criarCard(Item item) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 2));

        JLabel nome = new JLabel(item.getNome());
        nome.setFont(new Font("Arial", Font.BOLD, 20));
        nome.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel preco = new JLabel("R$ " + item.getPreco());
        preco.setFont(new Font("Arial", Font.BOLD, 18));
        preco.setForeground(new Color(0, 150, 0));
        preco.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel popular = new JLabel();

        if (item.isPopular()) {
            popular.setText("⭐ Popular");
            popular.setForeground(new Color(255, 140, 0));
        }

        popular.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createRigidArea(new Dimension(0, 20)));
        card.add(nome);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(preco);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(popular);

        return card;
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new CardapioView().setVisible(true);

        });

    }

}