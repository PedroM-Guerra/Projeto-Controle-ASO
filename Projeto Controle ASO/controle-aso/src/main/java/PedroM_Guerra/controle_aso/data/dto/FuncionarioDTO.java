package PedroM_Guerra.controle_aso.data.dto;

import PedroM_Guerra.controle_aso.enums.CargoFuncionario;
import PedroM_Guerra.controle_aso.enums.GeneroFuncionario;
import PedroM_Guerra.controle_aso.enums.SetorFuncionario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Relation(collectionRelation = "funcionarios")
public class FuncionarioDTO extends RepresentationModel<FuncionarioDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    private Boolean enabled;

    @NotBlank(message = "O CPF é obrigatório.")
    private String cpf;

    @NotBlank(message = "A matriícula é obrigatória.")
    private String matricula;

    @NotNull(message = "A data de nascimento é obrigatória.")
    @Past(message = "A data de nascimento deve ser uma data passada.")
    private LocalDate dataNascimento;

    @NotNull(message = "O gênero é obrigatório.")
    private GeneroFuncionario genero;

    @NotNull(message = "O setor é obrigatório.")
    private SetorFuncionario setor;

    @NotNull(message = "O cargo é obrigatório.")
    private CargoFuncionario cargo;

    @NotNull(message = "A data de adimissão é obrigatória.")
    @PastOrPresent(message = "A data de adimissão não pode ser uma data futura.")
    private LocalDate dataAdmissao;

    private LocalDate dataDemissao;

//    private Usuario cadastradoPor;

    public FuncionarioDTO() {
    }

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
        if (!(o instanceof FuncionarioDTO that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getNome(), that.getNome()) && Objects.equals(getCpf(), that.getCpf()) && Objects.equals(getMatricula(), that.getMatricula()) && Objects.equals(getDataNascimento(), that.getDataNascimento()) && getGenero() == that.getGenero() && getSetor() == that.getSetor() && getCargo() == that.getCargo() && Objects.equals(getDataAdmissao(), that.getDataAdmissao()) && Objects.equals(getDataDemissao(), that.getDataDemissao()) && Objects.equals(getEnabled(), that.getEnabled());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getNome(), getCpf(), getMatricula(), getDataNascimento(), getGenero(), getSetor(), getCargo(), getDataAdmissao(), getDataDemissao(), getEnabled());
    }
}
