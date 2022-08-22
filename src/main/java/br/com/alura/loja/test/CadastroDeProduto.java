package br.com.alura.loja.test;

import br.com.alura.loja.dao.CategoriaDAO;
import br.com.alura.loja.dao.ProdutoDAO;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.List;

public class CadastroDeProduto {

    public static void main(String[] args) {
        cadastrarProduto();

        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDAO produtoDAO = new ProdutoDAO(em);
        /*Produto produto = produtoDAO.buscarPorId(1L);
        System.out.println(produto);*/
//        List<Produto> produtos = produtoDAO.buscarTodos();
//        List<Produto> produtos = produtoDAO.buscarTodosPorNome("IPhone 12");
//        List<Produto> produtos = produtoDAO.buscarTodosPorNomeCategoria("CELULARES");
//        produtos.forEach(p -> System.out.println(p));
        BigDecimal precoProduto = produtoDAO.buscarPrecoPorId(1L);
        System.out.println(precoProduto);
    }

    private static void cadastrarProduto() {
        Categoria celulares = new Categoria("CELULARES");
        Produto celular = new Produto("IPhone 12", "Apple", new BigDecimal("4000"), celulares);

        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDAO produtoDAO = new ProdutoDAO(em);
        CategoriaDAO categoriaDAO = new CategoriaDAO(em);

        em.getTransaction().begin();

        categoriaDAO.cadastrar(celulares);
        produtoDAO.cadastrar(celular);

//        celulares.setDescricao("XPTO");
//        produtoDAO.deletar(celular);

        em.getTransaction().commit();
        em.close();
    }

}
