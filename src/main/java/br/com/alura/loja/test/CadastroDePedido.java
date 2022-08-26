package br.com.alura.loja.test;

import br.com.alura.loja.dao.CategoriaDAO;
import br.com.alura.loja.dao.ClienteDAO;
import br.com.alura.loja.dao.PedidoDAO;
import br.com.alura.loja.dao.ProdutoDAO;
import br.com.alura.loja.modelo.*;
import br.com.alura.loja.util.JPAUtil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

public class CadastroDePedido {

    public static void main(String[] args) {
        popularBancoDeDados();

        EntityManager em = JPAUtil.getEntityManager();

        ClienteDAO clienteDAO = new ClienteDAO(em);
        Cliente cliente = clienteDAO.buscarPorId(1);
        Pedido pedido = new Pedido(cliente);

        ProdutoDAO produtoDAO = new ProdutoDAO(em);
        Produto produto = produtoDAO.buscarPorId(1);

        pedido.adicionarItem(new ItemPedido(10, pedido, produto));

        em.getTransaction().begin();

        PedidoDAO pedidoDAO = new PedidoDAO(em);
        pedidoDAO.cadastrar(pedido);

        em.getTransaction().commit();

        BigDecimal valorTotalVendido = pedidoDAO.obterValorTotalVendido();
        System.out.println("Valor total vendido: " + valorTotalVendido);

        em.close();
    }

    private static void popularBancoDeDados() {
        Categoria celulares = new Categoria("CELULARES");
        Produto celular = new Produto("IPhone 12", "Apple", new BigDecimal("4000"), celulares);
        Cliente cliente = new Cliente("Alexandre Oliveira", "123456");

        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDAO produtoDAO = new ProdutoDAO(em);
        CategoriaDAO categoriaDAO = new CategoriaDAO(em);
        ClienteDAO clienteDAO = new ClienteDAO(em);

        em.getTransaction().begin();

        categoriaDAO.cadastrar(celulares);
        produtoDAO.cadastrar(celular);
        clienteDAO.cadastrar(cliente);

        em.getTransaction().commit();
        em.close();
    }

}
