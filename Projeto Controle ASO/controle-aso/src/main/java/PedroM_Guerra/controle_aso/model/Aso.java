package PedroM_Guerra.controle_aso.model;

import PedroM_Guerra.controle_aso.enums.*;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "aso")
public class Aso implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionario funcionario;

    @Column(name = "crm_medico", nullable = false, length = 50)
    private String crmMedico;

    @Column(name = "nome_medico", nullable = false, length = 255)
    private String nomeMedico;

    @Column(name = "descricao_exame", length = 1000)
    private String descricaoExame;

    @Column(name = "url_documento", nullable = false, length = 500)
    private String urlDocumentoScan;

    @Column(name = "data_emissao", nullable = false)
    private LocalDate dataEmissao;

    @Column(name = "data_validade", nullable = false)
    private LocalDate dataValidade;

    @Column(name = "tipo_aso", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoAso tipoAso;

    @Column(name = "resultado_aso", nullable = false)
    @Enumerated(EnumType.STRING)
    private ResultadoAso resultadoAso;

    //criar cadastradoPor usuario futuramente

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public String getCrmMedico() {
        return crmMedico;
    }

    public void setCrmMedico(String crmMedico) {
        this.crmMedico = crmMedico;
    }

    public String getNomeMedico() {
        return nomeMedico;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }

    public String getDescricaoExame() {
        return descricaoExame;
    }

    public void setDescricaoExame(String descricaoExame) {
        this.descricaoExame = descricaoExame;
    }

    public String getUrlDocumentoScan() {
        return urlDocumentoScan;
    }

    public void setUrlDocumentoScan(String urlDocumentoScan) {
        this.urlDocumentoScan = urlDocumentoScan;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public TipoAso getTipoAso() {
        return tipoAso;
    }

    public void setTipoAso(TipoAso tipoAso) {
        this.tipoAso = tipoAso;
    }

    public ResultadoAso getResultadoAso() {
        return resultadoAso;
    }

    public void setResultadoAso(ResultadoAso resultadoAso) {
        this.resultadoAso = resultadoAso;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Aso aso)) return false;
        return Objects.equals(getId(), aso.getId()) && Objects.equals(getFuncionario(), aso.getFuncionario()) && Objects.equals(getCrmMedico(), aso.getCrmMedico()) && Objects.equals(getNomeMedico(), aso.getNomeMedico()) && Objects.equals(getDescricaoExame(), aso.getDescricaoExame()) && Objects.equals(getUrlDocumentoScan(), aso.getUrlDocumentoScan()) && Objects.equals(getDataEmissao(), aso.getDataEmissao()) && Objects.equals(getDataValidade(), aso.getDataValidade()) && getTipoAso() == aso.getTipoAso() && getResultadoAso() == aso.getResultadoAso();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFuncionario(), getCrmMedico(), getNomeMedico(), getDescricaoExame(), getUrlDocumentoScan(), getDataEmissao(), getDataValidade(), getTipoAso(), getResultadoAso());
    }
}
