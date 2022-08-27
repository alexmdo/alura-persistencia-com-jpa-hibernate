package br.com.alura.loja.dao;

import br.com.alura.loja.modelo.Produto;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ProdutoDAO {

    private final EntityManager em;

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
        return this.em.createNamedQuery("Produto.buscarTodosPorNomeCategoria", Produto.class)
                .setParameter("nome", nome)
                .getResultList();
    }

    public BigDecimal buscarPrecoPorId(long id) {
        return this.em
                .createQuery("SELECT p.preco from Produto p where p.id = :id", BigDecimal.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public List<Produto> buscarPorParametros(final String nome, BigDecimal preco, LocalDate dataCadastro) {
        String jpql = "SELECT p FROM Produto p WHERE 1 = 1";

        if (nome != null && !nome.isBlank()) {
            jpql += " AND p.nome = :nome";
        }

        if (preco != null) {
            jpql += " AND p.preco = :preco";
        }

        if (dataCadastro != null) {
            jpql += " AND p.dataCadastro = :dataCadastro";
        }

        TypedQuery<Produto> query = this.em.createQuery(jpql, Produto.class);
        if (nome != null && !nome.isBlank()) {
            query.setParameter("nome", nome);
        }

        if (preco != null) {
            query.setParameter("preco", preco);
        }

        if (dataCadastro != null) {
            query.setParameter("dataCadastro", dataCadastro);
        }

        return query.getResultList();
    }

    public List<Produto> buscarPorParametrosCriteria(final String nome, BigDecimal preco, LocalDate dataCadastro) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Produto> query = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = query.from(Produto.class);

        Predicate filters = criteriaBuilder.and();
        if (nome != null && !nome.isBlank()) {
            filters = criteriaBuilder.and(filters, criteriaBuilder.equal(root.get("nome"), nome));
        }

        if (preco != null) {
            filters = criteriaBuilder.and(filters, criteriaBuilder.equal(root.get("preco"), preco));
        }

        if (dataCadastro != null) {
            filters = criteriaBuilder.and(filters, criteriaBuilder.equal(root.get("dataCadastro"), dataCadastro));
        }

        query.where(filters);

        return this.em.createQuery(query).getResultList();
    }
}
