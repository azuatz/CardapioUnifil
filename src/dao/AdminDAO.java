package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDAO {

    private Connection con;

    public AdminDAO(Connection con){
        this.con = con;
    }

    public boolean login(String usuario, String senha){

        String sql = "SELECT * FROM admin WHERE usuario=? AND senha=?";

        try{

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1,usuario);
            ps.setString(2,senha);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return true;
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
}