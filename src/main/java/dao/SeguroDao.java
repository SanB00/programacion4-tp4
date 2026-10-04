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
	private String host = "jdbc:mysql://localhost:3306/";
	private String user = "root";
	private String pass = "root";
	private String dbname = "SegurosGroup";
	private final String QUERY_PRINCIPAL = "SELECT * \r\n" + ",(\r\n"
			+ "	SELECT TS.descripcion FROM segurosgroup.tiposeguros TS\r\n" + "    WHERE TS.idTipo = S.idTipo\r\n"
			+ ") as descripcionSeguro\r\n" + "FROM segurosgroup.seguros S\r\n" + "\r\n";

	// constructor
	public SeguroDao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public int agregarSeguro(Seguro seg) throws SQLException {
		String query = "insert into seguros(descripcion,idTipo,costoContratacion,costoAsegurado) values(?,?,?,?)";
		Connection cn = null;
		int filas = 0;
		cn = DriverManager.getConnection(host + dbname, user, pass);
		PreparedStatement ps = (PreparedStatement) cn.prepareStatement(query);
		ps.setString(1, seg.getDescripcion());
		ps.setInt(2, seg.getIdTipo());
		ps.setBigDecimal(3, seg.getCostoContratacion());
		ps.setBigDecimal(4, seg.getCostoAsegurado());
		filas = ps.executeUpdate();
		return filas;

	}

	public int eliminarSeguro(int id) throws SQLException {
		String query = "Delete from seguros where idSeguro=?";
		Connection cn = null;
		int filas = 0;
		cn = DriverManager.getConnection(host + dbname, user, pass);
		PreparedStatement ps = cn.prepareStatement(query);
		ps.setInt(1, id);
		filas = ps.executeUpdate();

		return filas;

	}

	public int modificarSeguro(String descripcion, int idTipo, BigDecimal costoContratacion, BigDecimal costoAsegurado,
			int idSeguro) {
		String query = "Update Seguros set descripcion=?,idTipo=?,costoContratacion=?,costoAsegurado=? where idSeguro=?";
		Connection cn = null;
		int filas = 0;
		try {
			cn = DriverManager.getConnection(host + dbname, user, pass);
			PreparedStatement ps = cn.prepareStatement(query);
			ps.setString(1, descripcion);
			ps.setInt(2, idTipo);
			ps.setBigDecimal(3, costoContratacion);
			ps.setBigDecimal(4, costoAsegurado);
			ps.setInt(5, idSeguro);
			filas = ps.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
		return filas;
	}

	public Seguro getSeguro(int id) throws SQLException {
		// String query = "select * from seguros where idSeguro=?";
		String query = this.QUERY_PRINCIPAL + " where idSeguro=?";

		Connection cn = null;
		Seguro seg = new Seguro();
		cn = DriverManager.getConnection(host + dbname, user, pass);
		PreparedStatement ps = cn.prepareStatement(query);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next())
			return this.getObjectFromRS(rs);
		return null;
	}

	public ArrayList<Seguro> getSeguros() throws SQLException {
		ArrayList<Seguro> lseg = new ArrayList<Seguro>();
		String query = this.QUERY_PRINCIPAL; // String query = "select * from seguros";
		Connection cn = null;
		cn = DriverManager.getConnection(host + dbname, user, pass);
		Statement s = cn.createStatement();
		ResultSet rs = s.executeQuery(query);
		while (rs.next()) {
			lseg.add(this.getObjectFromRS(rs));
		}

		return lseg;

	}

	public int getProximoId() {
		String query = "select ifnull(max(idSeguro),0)+1 from seguros";
		try (Connection cn = DriverManager.getConnection(host + dbname, user, pass);
				Statement s = cn.createStatement();
				ResultSet rs = s.executeQuery(query)) {
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 1;
	}

	public ArrayList<Seguro> obtenerPorTipoSeguro(int id) throws SQLException {
		ArrayList<Seguro> lista = new ArrayList<Seguro>();
		String query = this.QUERY_PRINCIPAL + " WHERE idTipo = " + id + "\r\n";
		Connection cn = DriverManager.getConnection(host + dbname, user, pass);
		Statement s = cn.createStatement();
		ResultSet rs = s.executeQuery(query);
		while (rs.next()) {
			lista.add(this.getObjectFromRS(rs));
		}
		return lista;

	}

	public Seguro getObjectFromRS(ResultSet rs) throws SQLException {
		Seguro seg = new Seguro();
		seg.setIdSeguro(rs.getInt("idSeguro"));
		seg.setDescripcion(rs.getString("descripcion"));
		seg.setIdTipo(rs.getInt("idTipo"));
		seg.setCostoAsegurado(rs.getBigDecimal("costoAsegurado"));
		seg.setCostoContratacion(rs.getBigDecimal("costoContratacion"));
		seg.setDescripcionTipoSeguro(rs.getString("descripcionSeguro"));
		return seg;
	}
}
