package PedroM_Guerra.controle_aso.integrationtests.dto.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class WrapperAsoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("_embedded")
    private AsoEmbeddedDTO embedded;

    public WrapperAsoDTO() {
    }

    public AsoEmbeddedDTO getEmbedded() {
        return embedded;
    }

    public void setEmbedded(AsoEmbeddedDTO embedded) {
        this.embedded = embedded;
    }
}
