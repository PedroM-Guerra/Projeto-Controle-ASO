package PedroM_Guerra.controle_aso.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "funcionario")
public class Funcionario implements Serializable {

    private static final long SerialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String cpf;

    //mudar sexo para usar enum futuramente
    @Column(name = "genero_biologico", nullable = false, length = 9)
    private String genero;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "matricula", nullable = false, unique = true, length = 10)
    private String matricula;

    //mudar setor para usar enum futuramente
    @Column(name = "setor", nullable = false, length = 50)
    private String setor;

    //mudar cargo para usar enum futuramente
    @Column(name = "cargo", nullable = false, length = 50)
    private String cargo;

    @Column(name = "data_admissao", nullable = false)
    private LocalDate dataAdmissao;

    @Column(name = "data_demissao")
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
        if (!(o instanceof Funcionario that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getNome(), that.getNome()) && Objects.equals(getCpf(), that.getCpf()) && Objects.equals(getGenero(), that.getGenero()) && Objects.equals(getDataNascimento(), that.getDataNascimento()) && Objects.equals(getMatricula(), that.getMatricula()) && Objects.equals(getSetor(), that.getSetor()) && Objects.equals(getCargo(), that.getCargo()) && Objects.equals(getDataAdmissao(), that.getDataAdmissao()) && Objects.equals(getDataDemissao(), that.getDataDemissao());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), getCpf(), getGenero(), getDataNascimento(), getMatricula(), getSetor(), getCargo(), getDataAdmissao(), getDataDemissao());
    }
}
