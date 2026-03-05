package main;

import dao.ItemDAO;
import dao.ConnectionFactory;
import dto.Item;

import java.sql.Connection;

public class ItemCrud {

    public static void main(String[] args) {

        Connection con = ConnectionFactory.abrirConexao();

        ItemDAO itemDAO = new ItemDAO(con);

        // CREATE
        Item item = new Item();
        item.setNome("Paçoca");
        item.setPreco(2.0);
        item.setPopular(false);

        System.out.println(itemDAO.inserir(item));

        // READ
        System.out.println("\nLista de itens:");
        for (Item i : itemDAO.listar()) {

            System.out.println(
                    i.getID() + " - " +
                            i.getNome() + " - " +
                            i.getPreco() + " - " +
                            i.isPopular()
            );
        }

        // UPDATE
        //Item itemAlterar = new Item();
        //itemAlterar.setID(0); // define qual será alterado
        //itemAlterar.setNome("Pastel");
        //itemAlterar.setPreco(8.0);
        //itemAlterar.setPopular(true);

        //System.out.println(itemDAO.alterar(itemAlterar));

        // DELETE
        // System.out.println(itemDAO.excluir(1)); // id

        ConnectionFactory.fecharConexao(con);
    }
}