package com.bulkgym.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.bulkgym.domain.MedidaCorporal;

public class MedidaCorporalData {

    private final String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=Bulk-DB;integratedSecurity=true;";
    private MedidaCorporalExtractor extractor = new MedidaCorporalExtractor();

    public void insertarMedida(String tipoMedida, String unidad) throws SQLException {
        String sql = "INSERT INTO MedidaCorporal (tipoMedida, unidad) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(connectionUrl);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tipoMedida);
            stmt.setString(2, unidad);
            stmt.executeUpdate();
        }
    }

    public List<MedidaCorporal> obtenerTodas() throws SQLException {
        List<MedidaCorporal> lista = new ArrayList<>();
        String sql = "SELECT * FROM MedidaCorporal";

        try (Connection conn = DriverManager.getConnection(connectionUrl);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(extractor.extraerMedida(rs));
            }
        }
        return lista;
    }

    public MedidaCorporal obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM MedidaCorporal WHERE idMedidaCorporal = ?";
        MedidaCorporal m = null;

        try (Connection conn = DriverManager.getConnection(connectionUrl);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                m = extractor.extraerMedida(rs);
            }
        }
        return m;
    }

    public boolean actualizarMedida(int id, String nuevoTipo, String nuevaUnidad) throws SQLException {
        String sql = "UPDATE MedidaCorporal SET tipoMedida = ?, unidad = ? WHERE idMedidaCorporal = ?";

        try (Connection conn = DriverManager.getConnection(connectionUrl);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nuevoTipo);
            stmt.setString(2, nuevaUnidad);
            stmt.setInt(3, id);

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean eliminarMedida(int id) throws SQLException {
        String sql = "DELETE FROM MedidaCorporal WHERE idMedidaCorporal = ?";

        try (Connection conn = DriverManager.getConnection(connectionUrl);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
