package sistema_para_mercado.dao;

import sistema_para_mercado.model.Venda;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {
    private Connection conn;

    public VendaDAO(){
        this.conn = ConexaoBD.getConexao();
    }

    private Venda mapearVenda(ResultSet rs) throws SQLException {
        Venda v = new Venda();
        v.setId(rs.getInt("id"));
        v.setData(rs.getDate("data").toLocalDate());  // melhor do que parse String
        v.setTotal(rs.getDouble("total"));
        // Itens da venda podem ser carregados separadamente, por exemplo
        return v;
    }

    public void inserir(Venda venda) {
        String sql = "INSERT INTO venda(data, total) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, java.sql.Date.valueOf(venda.getData()));
            stmt.setDouble(2, venda.getTotal());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGerado = rs.getInt(1);
                    venda.setId(idGerado);
                }
            }
        } catch (SQLException e) {
            System.err.println("[VendaDAO] Erro ao inserir venda: " + e.getMessage());
        }
    }

    public List<Venda> buscarVenda() {
        List<Venda> vendas = new ArrayList<>();
        String sql = "SELECT * FROM venda";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                vendas.add(mapearVenda(rs));
            }

        } catch (SQLException e) {
            System.err.println("[VendaDAO] Erro ao buscar vendas: " + e.getMessage());
        }
        return vendas;
    }
}
