package dao;

import dto.Item;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    private Connection con;

    public ItemDAO(Connection con) {
        this.con = con;
    }

    // CREATE
    public String inserir(Item item) {

        String sql = "INSERT INTO item (nome, preco, popular) VALUES (?, ?, ?)";

        try {

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, item.getNome());
            ps.setDouble(2, item.getPreco());
            ps.setBoolean(3, item.isPopular());

            ps.executeUpdate();

            return "Item inserido com sucesso!";

        } catch (SQLException e) {

            return "Erro de SQL: " + e.getMessage();
        }
    }

    // READ
    public List<Item> listar() {

        List<Item> lista = new ArrayList<>();

        String sql = "SELECT * FROM item";

        try {

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Item item = new Item();

                item.setID(rs.getInt("id"));
                item.setNome(rs.getString("nome"));
                item.setPreco(rs.getDouble("preco"));
                item.setPopular(rs.getBoolean("popular"));

                lista.add(item);
            }

        } catch (SQLException e) {

            System.out.println("Erro de SQL: " + e.getMessage());
        }

        return lista;
    }

    // UPDATE
    public String alterar(Item item) {

        String sql = "UPDATE item SET nome=?, preco=?, popular=? WHERE id=?";

        try {

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, item.getNome());
            ps.setDouble(2, item.getPreco());
            ps.setBoolean(3, item.isPopular());
            ps.setInt(4, item.getID());

            ps.executeUpdate();

            return "Item alterado!";

        } catch (SQLException e) {

            return "Erro de SQL: " + e.getMessage();
        }
    }

    // DELETE
    public String excluir(int id) {

        String sql = "DELETE FROM item WHERE id=?";

        try {

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();

            return "Item excluído!";

        } catch (SQLException e) {

            return "Erro de SQL: " + e.getMessage();
        }
    }
}