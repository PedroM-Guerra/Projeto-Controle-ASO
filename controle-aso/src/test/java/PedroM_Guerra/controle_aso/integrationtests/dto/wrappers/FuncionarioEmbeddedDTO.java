package PedroM_Guerra.controle_aso.integrationtests.dto.wrappers;

import PedroM_Guerra.controle_aso.integrationtests.dto.FuncionarioDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;

public class FuncionarioEmbeddedDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("funcionarios")
    private List<FuncionarioDTO> funcionarios;

    public FuncionarioEmbeddedDTO() {}

    public List<FuncionarioDTO> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<FuncionarioDTO> funcionarios) {
        this.funcionarios = funcionarios;
    }
}
