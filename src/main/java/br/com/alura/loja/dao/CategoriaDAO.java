package br.com.alura.loja.dao;

import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Produto;

import javax.persistence.EntityManager;

public class CategoriaDAO {

    private EntityManager em;

    public CategoriaDAO(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Categoria categoria) {
        this.em.persist(categoria);
    }

    public Categoria atualizar(Categoria categoria) {
        return this.em.merge(categoria);
    }

    public void deletar(Categoria categoria) {
        this.em.remove(this.em.merge(categoria));
    }

}
