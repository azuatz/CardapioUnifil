package view;

import dao.AdminDAO;
import dao.ConnectionFactory;

import javax.swing.*;
import java.sql.Connection;

public class LoginView {

    public static boolean fazerLogin(){

        JTextField usuario = new JTextField();
        JPasswordField senha = new JPasswordField();

        Object[] campos = {
                "Usuário:", usuario,
                "Senha:", senha
        };

        int opcao = JOptionPane.showConfirmDialog(null, campos, "Login Administrador", JOptionPane.OK_CANCEL_OPTION);

        if(opcao == JOptionPane.OK_OPTION){

            Connection con = ConnectionFactory.abrirConexao();
            AdminDAO dao = new AdminDAO(con);

            boolean logado = dao.login(usuario.getText(), new String(senha.getPassword()));

            ConnectionFactory.fecharConexao(con);

            return logado;
        }

        return false;
    }
}