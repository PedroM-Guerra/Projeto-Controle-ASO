package PedroM_Guerra.controle_aso.data.dto;

import PedroM_Guerra.controle_aso.enums.ResultadoAso;
import PedroM_Guerra.controle_aso.enums.TipoAso;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Relation(collectionRelation = "asos")
public class AsoDTO extends RepresentationModel<AsoDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    private Long funcionarioId;
    private String crmMedico;
    private String nomeMedico;
    private String descricaoExame;
    private String urlDocumentoScan;
    private LocalDate dataEmissao;
    private LocalDate dataValidade;
    private TipoAso tipoAso;
    private ResultadoAso resultadoAso;
    //criar cadastradoPor usuario futuramente

    public AsoDTO(){
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(Long funcionarioId) {
        this.funcionarioId = funcionarioId;
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
        if (!(o instanceof AsoDTO asoDTO)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getId(), asoDTO.getId()) && Objects.equals(getFuncionarioId(), asoDTO.getFuncionarioId()) && Objects.equals(getCrmMedico(), asoDTO.getCrmMedico()) && Objects.equals(getNomeMedico(), asoDTO.getNomeMedico()) && Objects.equals(getDescricaoExame(), asoDTO.getDescricaoExame()) && Objects.equals(getUrlDocumentoScan(), asoDTO.getUrlDocumentoScan()) && Objects.equals(getDataEmissao(), asoDTO.getDataEmissao()) && Objects.equals(getDataValidade(), asoDTO.getDataValidade()) && getTipoAso() == asoDTO.getTipoAso() && getResultadoAso() == asoDTO.getResultadoAso();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getFuncionarioId(), getCrmMedico(), getNomeMedico(), getDescricaoExame(), getUrlDocumentoScan(), getDataEmissao(), getDataValidade(), getTipoAso(), getResultadoAso());
    }
}
