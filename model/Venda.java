package sistema_para_mercado.model;

import java.time.LocalDate;
import java.util.List;

public class Venda {

    private int id;
    private LocalDate data;
    private double total;
    private List<ItemVenda> itens;

    public Venda(int id, LocalDate data, List<ItemVenda> itens) {
        this.id = id;
        this.data = data;
        this.itens = itens;
        this.total = calcularTotal();
    }

    public Venda() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    // Sem setter para total, para evitar inconsistência

    public List<ItemVenda> getItens() {
        return itens;
    }

    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
        this.total = calcularTotal();
    }

    public double calcularTotal() {
        if (itens == null) return 0;
        return itens.stream()
                .mapToDouble(item -> item.getPrecoUnitario() * item.getQuantidade())
                .sum();
    }

    public void adicionarItem(ItemVenda item) {
        if (itens != null) {
            itens.add(item);
            this.total = calcularTotal();
        }
    }

    public void removerItem(ItemVenda item) {
        if (itens != null && itens.remove(item)) {
            this.total = calcularTotal();
        }
    }
}
