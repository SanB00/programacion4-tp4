package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dominio.TipoSeguros;

public class TipoSegurosDao {
	private String host = "jdbc:mysql://localhost:3306/";
	private String user = "root";
	private String pass = "root";
	private String dbname = "SegurosGroup?useUnicode=yes&characterEncoding=UTF-8&useSSL=false";

	public boolean agregarTipoSeguro(TipoSeguros tipoSeguros) {
		String query = "INSERT INTO tipoSeguros (descripcion) VALUES (?)";
		Connection cn = null;
		int filas = 0;

		try {
			cn = DriverManager.getConnection(host + dbname, user, pass);
			PreparedStatement pst = cn.prepareStatement(query);
			pst.setString(1, tipoSeguros.getDescripcion());

			filas = pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return filas > 0;
	}

	public boolean eliminarTipoSeguro(int idTipo) {

		String query = "DELETE FROM tipoSeguros WHERE idTipo = ?";
		Connection cn = null;
		int filas = 0;

		try {
			cn = DriverManager.getConnection(host + dbname, user, pass);
			PreparedStatement pst = cn.prepareStatement(query);
			pst.setInt(1, idTipo);

			filas = pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return filas > 0;
	}

	public boolean modificarTipoSeguro(TipoSeguros tipoSeguros) {

		String query = "UPDATE tipoSeguros SET descripcion = ? WHERE idTipo = ?";
		Connection cn = null;
		int filas = 0;

		try {
			cn = DriverManager.getConnection(host + dbname, user, pass);
			PreparedStatement pst = cn.prepareStatement(query);
			pst.setString(1, tipoSeguros.getDescripcion());
			pst.setInt(2, tipoSeguros.getIdTipo());

			filas = pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return filas > 0;
	}

	public TipoSeguros getTipoSeguro(int idTipo) {
		String query = "SELECT * FROM tipoSeguros WHERE idTipo=?";
		Connection cn = null;
		TipoSeguros tipoSeguro = new TipoSeguros();
		try {
			cn = DriverManager.getConnection(host + dbname, user, pass);
			PreparedStatement pst = cn.prepareStatement(query);
			pst.setInt(1, idTipo);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				tipoSeguro.setIdTipo(idTipo);
				tipoSeguro.setDescripcion(rs.getString("descripcion"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return tipoSeguro;
	}

	public ArrayList<TipoSeguros> getTipoSeguros() {
		ArrayList<TipoSeguros> listaTipoSeguros = new ArrayList<TipoSeguros>();
		String query = "SELECT * FROM tiposeguros";
		Connection cn = null;

		try {
			cn = DriverManager.getConnection(host + "segurosgroup", user, pass);
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				TipoSeguros ts = new TipoSeguros();
				ts.setIdTipo(rs.getInt("idTipo"));
				ts.setDescripcion(rs.getString("descripcion"));

				listaTipoSeguros.add(ts);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaTipoSeguros;
	}

}
