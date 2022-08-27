package br.com.alura.loja.modelo;

import javax.persistence.*;

@Entity
@Table(name = "categorias")
public class Categoria {

    @EmbeddedId
    private CategoriaId id;

    public Categoria() {
    }

    public Categoria(CategoriaId id) {
        this.id = id;
    }

    public String getDescricao() {
        return this.id.getDescricao();
    }
}
