package sistema_para_mercado.view;


import sistema_para_mercado.model.Produto;
import sistema_para_mercado.dao.ProdutoDAO;
import sistema_para_mercado.view.TelaCadastroProduto;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaListaProdutos extends JFrame {
    private JTextArea areaTexto = new JTextArea(10, 40);
    private JButton botaoCadastrar = new JButton("Cadastrar Produto");

    public TelaListaProdutos() {
        setTitle("Lista de Produtos");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);

        areaTexto.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaTexto);

        botaoCadastrar.addActionListener(e -> {
            new TelaCadastroProduto().setVisible(true);
        });

        add(scroll, BorderLayout.CENTER);
        add(botaoCadastrar, BorderLayout.SOUTH);
        atualizarLista();
    }

    public void exibir() {
        setVisible(true);
    }

    public void atualizarLista() {
        ProdutoDAO dao = new ProdutoDAO();
        List<Produto> lista = dao.buscarTodos();
        areaTexto.setText("");
        for (Produto p : lista) {
            areaTexto.append(
                    "ID: " + p.getId() +
                            " | Nome: " + p.getNome() +
                            " | Código: " + p.getCodigoBarra() +
                            " | Preço: " + p.getPreco() +
                            " | Qtde: " + p.getQuantidade() + "\n"
            );
        }
    }
}
