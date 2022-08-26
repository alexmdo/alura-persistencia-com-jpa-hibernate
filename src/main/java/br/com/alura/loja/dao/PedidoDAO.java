package br.com.alura.loja.dao;

import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.modelo.vo.RelatorioVendaVO;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class PedidoDAO {

    private final EntityManager em;

    public PedidoDAO(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Pedido pedido) {
        this.em.persist(pedido);
    }

    public BigDecimal obterValorTotalVendido() {
        String jpql = "SELECT SUM(p.valorTotal) FROM Pedido p";
        return em.createQuery(jpql, BigDecimal.class).getSingleResult();
    }

    public List<RelatorioVendaVO> gerarRelatorioVendas() {
        String jpql = "SELECT new br.com.alura.loja.modelo.vo.RelatorioVendaVO(" +
                "produto.nome, " +
                "SUM(itemPedido.quantidade), " +
                "MAX(pedido.data)) " +
                "FROM Pedido pedido " +
                "JOIN pedido.itens itemPedido " +
                "JOIN itemPedido.produto produto " +
                "GROUP BY produto.nome " +
                "ORDER BY itemPedido.quantidade DESC";
        return em.createQuery(jpql, RelatorioVendaVO.class).getResultList();
    }

}
