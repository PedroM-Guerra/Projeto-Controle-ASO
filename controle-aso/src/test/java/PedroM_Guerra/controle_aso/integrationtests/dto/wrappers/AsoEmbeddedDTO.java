package PedroM_Guerra.controle_aso.integrationtests.dto.wrappers;

import PedroM_Guerra.controle_aso.integrationtests.dto.AsoDTO;
import PedroM_Guerra.controle_aso.integrationtests.dto.FuncionarioDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class AsoEmbeddedDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("asos")
    private List<AsoDTO> asos;

    public AsoEmbeddedDTO() {}

    public List<AsoDTO> getAsos() {
        return asos;
    }

    public void setAsos(List<AsoDTO> asos) {
        this.asos = asos;
    }
}
