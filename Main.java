package sistema_para_mercado;

import sistema_para_mercado.view.TelaListaProdutos;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaListaProdutos tela = new TelaListaProdutos();
            tela.exibir();
        });
    }
}