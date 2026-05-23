package PedroM_Guerra.controle_aso.integrationtests.dto.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

public class WrapperFuncionarioDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("_embedded")
    private FuncionarioEmbeddedDTO embedded;

    public WrapperFuncionarioDTO() {}

    public FuncionarioEmbeddedDTO getEmbeded() {
        return embedded;
    }

    public void setEmbedded(FuncionarioEmbeddedDTO embeded) {
        this.embedded = embeded;
    }
}
