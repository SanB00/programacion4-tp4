package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dominio.TipoSeguros;

public class TipoSegurosDao {
    private final String host = "jdbc:mysql://localhost:3306/";
    private final String user = "root";
    private final String pass = "root";
    private final String dbname = "SegurosGroup?useUnicode=true&characterEncoding=UTF-8&useSSL=false";

    public TipoSegurosDao() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("No se encontró el driver de MySQL.", e);
        }
    }

    public boolean agregarTipoSeguro(TipoSeguros tipoSeguros) throws SQLException {
        String query = "INSERT INTO tipoSeguros (descripcion) VALUES (?)";

        try (Connection cn = DriverManager.getConnection(host + dbname, user, pass);
             PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setString(1, tipoSeguros.getDescripcion());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminarTipoSeguro(int idTipo) throws SQLException {
        String query = "DELETE FROM tipoSeguros WHERE idTipo = ?";

        try (Connection cn = DriverManager.getConnection(host + dbname, user, pass);
             PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setInt(1, idTipo);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean modificarTipoSeguro(TipoSeguros tipoSeguros) throws SQLException {
        String query = "UPDATE tipoSeguros SET descripcion = ? WHERE idTipo = ?";

        try (Connection cn = DriverManager.getConnection(host + dbname, user, pass);
             PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setString(1, tipoSeguros.getDescripcion());
            ps.setInt(2, tipoSeguros.getIdTipo());
            return ps.executeUpdate() > 0;
        }
    }

    public TipoSeguros getTipoSeguro(int idTipo) throws SQLException {
        String query = "SELECT idTipo, descripcion FROM tipoSeguros WHERE idTipo = ?";

        try (Connection cn = DriverManager.getConnection(host + dbname, user, pass);
             PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setInt(1, idTipo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new TipoSeguros(rs.getInt("idTipo"), rs.getString("descripcion"));
                }
            }
        }
        return null;
    }

    public ArrayList<TipoSeguros> getTipoSeguros() throws SQLException {
        ArrayList<TipoSeguros> lista = new ArrayList<>();
        String query = "SELECT idTipo, descripcion FROM tipoSeguros ORDER BY idTipo";

        try (Connection cn = DriverManager.getConnection(host + dbname, user, pass);
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                lista.add(new TipoSeguros(rs.getInt("idTipo"), rs.getString("descripcion")));
            }
        }
        return lista;
    }
}
