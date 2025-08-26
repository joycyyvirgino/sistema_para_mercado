package sistema_para_mercado.controller;

import sistema_para_mercado.model.Produto;
import sistema_para_mercado.dao.ProdutoDAO;
import java.util.List;

public class ProdutoController {
    private sistema_para_mercado.dao.ProdutoDAO produtoDAO;

    public ProdutoController() {
        this.produtoDAO = new ProdutoDAO();
    }

    public void salvarProduto(Produto p) {
        produtoDAO.inserir(p);
    }

    public List<Produto> listarProdutos() {
        return produtoDAO.buscarTodos();
    }
}
