package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dominio.Seguro;

public class SeguroDao {
	private String host = "jdbc:mysql://localhost:3306/";
	private String user = "root";
	private String pass = "root";
	private String dbname = "SegurosGroup";

	// constructor
	public SeguroDao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public int agregarSeguro(Seguro seg) {
		String query = "insert into seguros(descripcion,idTipo,costoContratacion,costoAsegurado) values(?,?,?,?)";
		Connection cn = null;
		int filas = 0;
		try {
			cn = DriverManager.getConnection(host + dbname, user, pass);
			PreparedStatement ps = (PreparedStatement) cn.prepareStatement(query);
			ps.setString(1, seg.getDescripcion());
			ps.setInt(2, seg.getIdTipo());
			ps.setBigDecimal(3, seg.getCostoContratacion());
			ps.setBigDecimal(4, seg.getCostoAsegurado());
			filas = ps.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();

			return 0;
		}
		return filas;

	}

	public int eliminarSeguro(int id) {
		String query = "Delete from seguros where idSeguro=?";
		Connection cn = null;
		int filas = 0;
		try {
			cn = DriverManager.getConnection(host + dbname, user, pass);
			PreparedStatement ps = cn.prepareStatement(query);
			ps.setInt(1, id);
			filas = ps.executeUpdate();

		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
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

	public Seguro getSeguro(int id) {
		String query = "select * from seguros where idSeguro=?";
		Connection cn = null;
		Seguro seg = new Seguro();
		try {
			cn = DriverManager.getConnection(host + dbname, user, pass);
			PreparedStatement ps = cn.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				seg.setIdSeguro(rs.getInt("idSeguro"));
				seg.setDescripcion(rs.getString("descripcion"));
				seg.setIdTipo(rs.getInt("idTipo"));
				seg.setCostoContratacion(rs.getBigDecimal("costoContratacion"));
				seg.setCostoAsegurado(rs.getBigDecimal("costoAsegurado"));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return seg;

	}

	public ArrayList<Seguro> getSeguros() {
		ArrayList<Seguro> lseg = new ArrayList<Seguro>();
		String query = "select * from seguros";
		Connection cn = null;
		try {
			cn = DriverManager.getConnection(host + dbname, user, pass);
			Statement s = cn.createStatement();
			ResultSet rs = s.executeQuery(query);
			while (rs.next()) {
				Seguro seg = new Seguro();
				seg.setIdSeguro(rs.getInt("idSeguro"));
				seg.setDescripcion(rs.getString("descripcion"));
				seg.setIdTipo(rs.getInt("idTipo"));
				seg.setCostoAsegurado(rs.getBigDecimal("costoAsegurado"));
				seg.setCostoContratacion(rs.getBigDecimal("costoContratacion"));
				lseg.add(seg);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
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

}
