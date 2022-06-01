
package com.emergentes.dao;

import com.emergentes.modelo.Participante;
import com.emergentes.utiles.ConexionDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class ParticipanteDAOimpl extends ConexionDB implements ParticipanteDAO {

    @Override
    public void insert(Participante participante) throws Exception {
        String sql = "insert into participantes (apellidos,nombres,id_seminario,confirmado) values (?,?,?,?)";
        this.conectar();
        PreparedStatement ps = this.conn.prepareStatement(sql);
        ps.setString(1, participante.getApellidos());
        ps.setString(2, participante.getNombres());
        ps.setInt(3, participante.getId_seminario());
        ps.setInt(4, participante.getConfimado());
        ps.executeUpdate();
        this.desconectar();
    }

    @Override
    public void update(Participante participante) throws Exception {
        String sql = "update participantes set apellidos=?,nombres=?,id_seminario=?,confirmado=? where id=?";
        this.conectar();
        PreparedStatement ps = this.conn.prepareStatement(sql);
        ps.setString(1, participante.getApellidos());
        ps.setString(2, participante.getNombres());
        ps.setInt(3, participante.getId_seminario());
        ps.setInt(4, participante.getConfimado());
        ps.setInt(5, participante.getId());
        ps.executeUpdate();
        this.desconectar();
    }

    @Override
    public void delete(int id) throws Exception {
        String sql = "delete from participantes where id = ?";
        this.conectar();
        PreparedStatement ps = this.conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        this.desconectar();
    }

    @Override
    public Participante getById(int id) throws Exception {
        Participante part = new Participante();
        String sql = "select * from participantes where id = ?";
        this.conectar();
        PreparedStatement ps = this.conn.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {            
            part.setId(rs.getInt("id"));
            part.setApellidos(rs.getString("apellidos"));
            part.setNombres(rs.getString("nombres"));
            part.setId_seminario(rs.getInt("id_seminario"));
            part.setConfimado(rs.getInt("confirmado"));
        }
        this.desconectar();
        return part;
    }

    @Override
    public List<Participante> getAll() throws Exception {
        List<Participante> list = null;
        String sql = "select p.*,s.titulo as seminario from participantes p left join seminarios s on p.id_seminario=s.id;";
        this.conectar();
        PreparedStatement ps = this.conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        list = new ArrayList<Participante>();
        while (rs.next()) {            
            Participante part = new Participante();
            part.setId(rs.getInt("id"));
            part.setApellidos(rs.getString("apellidos"));
            part.setNombres(rs.getString("nombres"));
            part.setId_seminario(rs.getInt("id_seminario"));
            part.setConfimado(rs.getInt("confirmado"));
            part.setSeminario(rs.getString("seminario"));
            list.add(part);
        }
        this.desconectar();
        return list;
    }
    
}
