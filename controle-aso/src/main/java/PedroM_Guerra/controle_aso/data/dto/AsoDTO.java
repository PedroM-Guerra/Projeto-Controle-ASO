package PedroM_Guerra.controle_aso.data.dto;

import PedroM_Guerra.controle_aso.enums.ResultadoAso;
import PedroM_Guerra.controle_aso.enums.TipoAso;
import PedroM_Guerra.controle_aso.model.Funcionario;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class AsoDTO extends RepresentationModel<AsoDTO> implements Serializable {

    private Long id;
    private Funcionario funcionario;
    private String crmMedico;
    private String nomeMedico;
    private String descricaoExame;
    private String urlDocumentoScan;
    private LocalDate dataEmissao;
    private LocalDate dataValidade;
    private TipoAso tipoAso;
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
        if (!(o instanceof AsoDTO aso)) return false;
        return Objects.equals(getId(), aso.getId()) && Objects.equals(getFuncionario(), aso.getFuncionario()) && Objects.equals(getCrmMedico(), aso.getCrmMedico()) && Objects.equals(getNomeMedico(), aso.getNomeMedico()) && Objects.equals(getDescricaoExame(), aso.getDescricaoExame()) && Objects.equals(getUrlDocumentoScan(), aso.getUrlDocumentoScan()) && Objects.equals(getDataEmissao(), aso.getDataEmissao()) && Objects.equals(getDataValidade(), aso.getDataValidade()) && getTipoAso() == aso.getTipoAso() && getResultadoAso() == aso.getResultadoAso();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFuncionario(), getCrmMedico(), getNomeMedico(), getDescricaoExame(), getUrlDocumentoScan(), getDataEmissao(), getDataValidade(), getTipoAso(), getResultadoAso());
    }
}
