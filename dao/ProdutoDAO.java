package sistema_para_mercado.dao;
import sistema_para_mercado.model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    private Connection conn;

    public ProdutoDAO() {
        this.conn = ConexaoBD.getConexao();
    }


    private Produto mapearProduto(ResultSet rs) throws SQLException {
        Produto p = new Produto();
        p.setId(rs.getInt("id"));
        p.setNome(rs.getString("nome"));
        p.setCodigoBarra(rs.getString("codigoBarra"));
        p.setPreco(rs.getDouble("preco"));
        p.setQuantidade(rs.getInt("quantidade"));
        return p;
    }

    //metado para inserir um novo produto no banco
    public boolean inserir(Produto p) {
        String sql = "INSERT INTO produto (nome, codigoBarra, preco, quantidade) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getCodigoBarra());
            stmt.setDouble(3, p.getPreco());
            stmt.setInt(4, p.getQuantidade());
            stmt.executeUpdate();
            return true; // sucesso
        } catch (SQLException e) {
            System.out.println("Erro ao inserir produto: " + e.getMessage());
            return false; // erro
        }
    }


    public void atualizar(Produto produto) {
        String sql = "UPDATE produto SET  nome=?, codigoBarra=?, preco=?, quantidade=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getCodigoBarra());
            stmt.setDouble(3, produto.getPreco());
            stmt.setInt(4, produto.getQuantidade());
            stmt.setInt(5, produto.getId());
            stmt.executeUpdate();//executar o coando SQL
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar produto: " + e.getMessage());
        }
    }

    public void remover(int id) {
        String sql = "DELETE FROM produto WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao remover produto : " + e.getMessage());
        }
    }

    public sistema_para_mercado.model.Produto buscarPorId(int id) {
        String sql = "SELECT * FROM produto WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);//Definir o parâmetro do id
            ResultSet rs = stmt.executeQuery();//Executar consuta
            if (rs.next()) { //se encontrar um resultado
                return mapearProduto(rs);// Converte o resultado em objeto Produto
            }
        } catch (SQLException e) {
            System.out.println("Erro ao busacar produto por id: " + e.getMessage());
        }
        return null;// Se não encontrar, retorna null
    }

    public sistema_para_mercado.model.Produto buscarPorCodigoBarra(String codigo) {
        String sql = "SELECT * FROM produto WHERE codigoBarra=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapearProduto(rs);
            }
        } catch (SQLException e) {
            System.out.println("Erro não foi possivel encontrar o produto por codigo de barra: " + e.getMessage());
        }
        return null;
    }

    public List<Produto> buscarTodos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                produtos.add(mapearProduto(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar produtos: " + e.getMessage());
        }
        return produtos;
    }
}