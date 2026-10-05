package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dominio.Seguro;

public class SeguroDao {
    private final String host = "jdbc:mysql://localhost:3306/";
    private final String user = "root";
    private final String pass = "root";
    private final String dbname = "SegurosGroup?useUnicode=true&characterEncoding=UTF-8&useSSL=false";

    private final String QUERY_PRINCIPAL =
            "SELECT S.idSeguro, S.descripcion, S.idTipo, S.costoContratacion, "
          + "S.costoAsegurado, TS.descripcion AS descripcionSeguro "
          + "FROM seguros S "
          + "INNER JOIN tipoSeguros TS ON S.idTipo = TS.idTipo";

    public SeguroDao() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("No se encontró el driver de MySQL.", e);
        }
    }

    public int agregarSeguro(Seguro seg) throws SQLException {
        String query = "INSERT INTO seguros(descripcion, idTipo, costoContratacion, costoAsegurado) "
                     + "VALUES (?, ?, ?, ?)";

        try (Connection cn = DriverManager.getConnection(host + dbname, user, pass);
             PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setString(1, seg.getDescripcion());
            ps.setInt(2, seg.getIdTipo());
            ps.setBigDecimal(3, seg.getCostoContratacion());
            ps.setBigDecimal(4, seg.getCostoAsegurado());
            return ps.executeUpdate();
        }
    }

    public int eliminarSeguro(int id) throws SQLException {
        String query = "DELETE FROM seguros WHERE idSeguro = ?";

        try (Connection cn = DriverManager.getConnection(host + dbname, user, pass);
             PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        }
    }

    public int modificarSeguro(String descripcion, int idTipo, BigDecimal costoContratacion,
            BigDecimal costoAsegurado, int idSeguro) throws SQLException {
        String query = "UPDATE seguros SET descripcion = ?, idTipo = ?, costoContratacion = ?, "
                     + "costoAsegurado = ? WHERE idSeguro = ?";

        try (Connection cn = DriverManager.getConnection(host + dbname, user, pass);
             PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setString(1, descripcion);
            ps.setInt(2, idTipo);
            ps.setBigDecimal(3, costoContratacion);
            ps.setBigDecimal(4, costoAsegurado);
            ps.setInt(5, idSeguro);
            return ps.executeUpdate();
        }
    }

    public Seguro getSeguro(int id) throws SQLException {
        String query = QUERY_PRINCIPAL + " WHERE S.idSeguro = ?";

        try (Connection cn = DriverManager.getConnection(host + dbname, user, pass);
             PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return getObjectFromRS(rs);
                }
            }
        }
        return null;
    }

    public ArrayList<Seguro> getSeguros() throws SQLException {
        ArrayList<Seguro> lista = new ArrayList<>();

        try (Connection cn = DriverManager.getConnection(host + dbname, user, pass);
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(QUERY_PRINCIPAL)) {

            while (rs.next()) {
                lista.add(getObjectFromRS(rs));
            }
        }
        return lista;
    }

    public int getProximoId() {
        String query = "SELECT IFNULL(MAX(idSeguro), 0) + 1 FROM seguros";

        try (Connection cn = DriverManager.getConnection(host + dbname, user, pass);
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public ArrayList<Seguro> obtenerPorTipoSeguro(int idTipo) throws SQLException {
        ArrayList<Seguro> lista = new ArrayList<>();
        String query = QUERY_PRINCIPAL + " WHERE S.idTipo = ?";

        try (Connection cn = DriverManager.getConnection(host + dbname, user, pass);
             PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setInt(1, idTipo);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(getObjectFromRS(rs));
                }
            }
        }
        return lista;
    }

    private Seguro getObjectFromRS(ResultSet rs) throws SQLException {
        Seguro seg = new Seguro();
        seg.setIdSeguro(rs.getInt("idSeguro"));
        seg.setDescripcion(rs.getString("descripcion"));
        seg.setIdTipo(rs.getInt("idTipo"));
        seg.setCostoContratacion(rs.getBigDecimal("costoContratacion"));
        seg.setCostoAsegurado(rs.getBigDecimal("costoAsegurado"));
        seg.setDescripcionTipoSeguro(rs.getString("descripcionSeguro"));
        return seg;
    }
}
