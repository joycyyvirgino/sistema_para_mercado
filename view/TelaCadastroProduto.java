package sistema_para_mercado.view;

import sistema_para_mercado.dao.ProdutoDAO;
import sistema_para_mercado.model.Produto;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroProduto extends JFrame {
    private JTextField campoNome = new JTextField(20);
    private JTextField campoCodigo = new JTextField(20);
    private JTextField campoPreco = new JTextField(10);
    private JTextField campoQuantidade = new JTextField(10);
    private JButton botaoSalvar = new JButton("Salvar");
    private JButton botaoBuscarPorId = new JButton("Buscar por ID");
    private JButton botaoBuscarPorCodigo = new JButton("Buscar por Código");
    private JButton botaoRemover = new JButton("Remover por ID");

    public TelaCadastroProduto() {
        setTitle("Cadastro de Produto");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(7, 2));
        add(new JLabel("Nome:"));
        add(campoNome);
        add(new JLabel("Código de Barras:"));
        add(campoCodigo);
        add(new JLabel("Preço:"));
        add(campoPreco);
        add(new JLabel("Quantidade:"));
        add(campoQuantidade);
        add(botaoSalvar);
        add(botaoBuscarPorId);
        add(botaoBuscarPorCodigo);
        add(botaoRemover);

        ProdutoDAO dao = new ProdutoDAO();

        botaoSalvar.addActionListener(e -> {
            try {
                Produto p = new Produto(
                        campoNome.getText(),
                        campoCodigo.getText(),
                        Double.parseDouble(campoPreco.getText().replace(",", ".")),
                        Integer.parseInt(campoQuantidade.getText())
                );

                boolean sucesso = dao.inserir(p);

                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Produto salvo com sucesso:\n" +
                            "Nome: " + p.getNome() + "\n" +
                            "Código: " + p.getCodigoBarra());
                    limparCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar produto.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Erro nos dados numéricos. Verifique o preço e a quantidade.");
            }
        });

        botaoBuscarPorId.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog(this, "Digite o ID do produto:");
            try {
                int id = Integer.parseInt(idStr);
                Produto produto = dao.buscarPorId(id);
                if (produto != null) {
                    preencherCampos(produto);
                } else {
                    JOptionPane.showMessageDialog(this, "Produto não encontrado.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido.");
            }
        });

        botaoBuscarPorCodigo.addActionListener(e -> {
            String codigo = JOptionPane.showInputDialog(this, "Digite o código de barras:");
            Produto produto = dao.buscarPorCodigoBarra(codigo);
            if (produto != null) {
                preencherCampos(produto);
            } else {
                JOptionPane.showMessageDialog(this, "Produto não encontrado.");
            }
        });

        botaoRemover.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog(this, "Digite o ID do produto para remover:");
            try {
                int id = Integer.parseInt(idStr);
                dao.remover(id);
                JOptionPane.showMessageDialog(this, "Produto removido com sucesso.");
                limparCampos();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido.");
            }
        });
    }

    private void preencherCampos(Produto p) {
        campoNome.setText(p.getNome());
        campoCodigo.setText(p.getCodigoBarra());
        campoPreco.setText(String.valueOf(p.getPreco()));
        campoQuantidade.setText(String.valueOf(p.getQuantidade()));
    }

    private void limparCampos() {
        campoNome.setText("");
        campoCodigo.setText("");
        campoPreco.setText("");
        campoQuantidade.setText("");
    }
}
