package PedroM_Guerra.controle_aso.model;

import PedroM_Guerra.controle_aso.enums.CargoFuncionario;
import PedroM_Guerra.controle_aso.enums.GeneroFuncionario;
import PedroM_Guerra.controle_aso.enums.SetorFuncionario;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "funcionario")
public class Funcionario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "enabled", nullable = false, columnDefinition = "BIT(1) DEFAULT 1")
    private Boolean enabled;

    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(name = "genero_biologico", nullable = false)
    @Enumerated(EnumType.STRING)
    private GeneroFuncionario genero;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "matricula", nullable = false, unique = true, length = 10)
    private String matricula;

    @Column(name = "setor", nullable = false)
    @Enumerated(EnumType.STRING)
    private SetorFuncionario setor;

    @Column(name = "cargo", nullable = false)
    @Enumerated(EnumType.STRING)
    private CargoFuncionario cargo;

    @Column(name = "data_admissao", nullable = false)
    private LocalDate dataAdmissao;

    @Column(name = "data_demissao")
    private LocalDate dataDemissao;

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

    public GeneroFuncionario getGenero() {
        return genero;
    }

    public void setGenero(GeneroFuncionario genero) {
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

    public SetorFuncionario getSetor() {
        return setor;
    }

    public void setSetor(SetorFuncionario setor) {
        this.setor = setor;
    }

    public CargoFuncionario getCargo() {
        return cargo;
    }

    public void setCargo(CargoFuncionario cargo) {
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Funcionario that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getNome(), that.getNome()) && Objects.equals(getEnabled(), that.getEnabled()) && Objects.equals(getCpf(), that.getCpf()) && getGenero() == that.getGenero() && Objects.equals(getDataNascimento(), that.getDataNascimento()) && Objects.equals(getMatricula(), that.getMatricula()) && getSetor() == that.getSetor() && getCargo() == that.getCargo() && Objects.equals(getDataAdmissao(), that.getDataAdmissao()) && Objects.equals(getDataDemissao(), that.getDataDemissao());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), getEnabled(), getCpf(), getGenero(), getDataNascimento(), getMatricula(), getSetor(), getCargo(), getDataAdmissao(), getDataDemissao());
    }
}
