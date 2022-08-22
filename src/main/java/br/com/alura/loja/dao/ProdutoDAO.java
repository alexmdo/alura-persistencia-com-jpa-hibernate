package br.com.alura.loja.dao;

import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Produto;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class ProdutoDAO {

    private EntityManager em;

    public ProdutoDAO(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Produto produto) {
        this.em.persist(produto);
    }

    public Produto atualizar(Produto produto) {
        return this.em.merge(produto);
    }

    public void deletar(Produto produto) {
        this.em.remove(this.em.merge(produto));
    }

    public Produto buscarPorId(long id) {
        return this.em.find(Produto.class, id);
    }

    public List<Produto> buscarTodos() {
        String buscarTodosProdutosQuery = "SELECT p from Produto p";
        return this.em.createQuery(buscarTodosProdutosQuery, Produto.class).getResultList();
    }

    public List<Produto> buscarTodosPorNome(String nome) {
        String buscarProdutoPorNomeQuery = "SELECT p from Produto p where p.nome = :nome";
        return this.em
                .createQuery(buscarProdutoPorNomeQuery, Produto.class)
                .setParameter("nome", nome)
                .getResultList();
    }

    public List<Produto> buscarTodosPorNomeCategoria(String nome) {
        return this.em.createQuery("SELECT p from Produto p where p.categoria.descricao = :nome", Produto.class)
                .setParameter("nome", nome)
                .getResultList();
    }

    public BigDecimal buscarPrecoPorId(long id) {
        return this.em
                .createQuery("SELECT p.preco from Produto p where p.id = :id", BigDecimal.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
