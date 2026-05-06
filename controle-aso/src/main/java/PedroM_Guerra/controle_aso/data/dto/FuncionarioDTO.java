package PedroM_Guerra.controle_aso.data.dto;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


public class FuncionarioDTO implements Serializable {

    private static final long SerialVersionUID = 1L;

    private Long id;
    private String nome;
    private String cpf;
    //mudar sexo para usar enum futuramente
    private String genero;
    private LocalDate dataNascimento;
    private String matricula;
    //mudar setor para usar enum futuramente
    private String setor;
    //mudar cargo para usar enum futuramente
    private String cargo;
    private LocalDate dataAdmissao;
    private LocalDate dataDemissao;

//    private List<Aso> asos;
//    private Usuario cadastradoPor;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public LocalDate getDataDemissao() {
        return dataDemissao;
    }

    public void setDataDemissao(LocalDate dataDemissao) {
        this.dataDemissao = dataDemissao;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FuncionarioDTO that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getNome(), that.getNome()) && Objects.equals(getCpf(), that.getCpf()) && Objects.equals(getGenero(), that.getGenero()) && Objects.equals(getDataNascimento(), that.getDataNascimento()) && Objects.equals(getMatricula(), that.getMatricula()) && Objects.equals(getSetor(), that.getSetor()) && Objects.equals(getCargo(), that.getCargo()) && Objects.equals(getDataAdmissao(), that.getDataAdmissao()) && Objects.equals(getDataDemissao(), that.getDataDemissao());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), getCpf(), getGenero(), getDataNascimento(), getMatricula(), getSetor(), getCargo(), getDataAdmissao(), getDataDemissao());
    }
}
