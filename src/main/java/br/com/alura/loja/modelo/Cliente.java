package br.com.alura.loja.modelo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Embedded
    private DadosPessoais dadosPessoais;

    @OneToMany(mappedBy = "cliente")
    private final List<Pedido> pedidos = new ArrayList<>();

    public Cliente() {
    }

    public Cliente(String nome, String cpf) {
        this.dadosPessoais = new DadosPessoais(nome, cpf);
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return dadosPessoais.getNome();
    }

    public String getCpf() {
        return dadosPessoais.getCpf();
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void adicionarPedido(final Pedido pedido) {
        pedido.setCliente(this);
        getPedidos().add(pedido);
    }

}