package PedroM_Guerra.controle_aso.unittests.mapper.mocks;

import PedroM_Guerra.controle_aso.data.dto.AsoDTO;
import PedroM_Guerra.controle_aso.enums.*;
import PedroM_Guerra.controle_aso.model.Aso;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class MockAso {


    public Aso mockEntity() {
        return mockEntity(0);
    }
    
    public AsoDTO mockDTO() {
        return mockDTO(0);
    }

    private MockFuncionario mockFuncionario = new MockFuncionario();
    
    public List<Aso> mockEntityList() {
        List<Aso> asos = new ArrayList<Aso>();
        for (int i = 0; i < 14; i++) {
            asos.add(mockEntity(i));
        }
        return asos;
    }

    public List<AsoDTO> mockDTOList() {
        List<AsoDTO> asos = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            asos.add(mockDTO(i));
        }
        return asos;
    }
    
    public Aso mockEntity(Integer number) {
        Aso aso = new Aso();
        TipoAso[] tipoAsos = TipoAso.values();

        aso.setId(number.longValue());
        aso.setFuncionario(mockFuncionario.mockEntity(number));

        aso.setCrmMedico("CRM Test" + number);
        aso.setNomeMedico("Nome Medico Test" + number);
        aso.setDescricaoExame("Descição Exame Test" + number);
        aso.setUrlDocumentoScan("URL Test" + number);

        aso.setDataEmissao(LocalDate.of(2026, 5, 1 + number));
        aso.setDataValidade(aso.getDataEmissao().plusYears(1));

        aso.setTipoAso(tipoAsos[number % tipoAsos.length]);
        aso.setResultadoAso(((number % 2)==0) ? ResultadoAso.APTO : ResultadoAso.INAPTO);

        return aso;
    }

    public AsoDTO mockDTO(Integer number) {
        AsoDTO aso = new AsoDTO();
        TipoAso[] tipoAsos = TipoAso.values();

        aso.setId(number.longValue());
        aso.setFuncionarioId(number.longValue());

        aso.setCrmMedico("CRM Test" + number);
        aso.setNomeMedico("Nome Medico Test" + number);
        aso.setDescricaoExame("Descição Exame Test" + number);
        aso.setUrlDocumentoScan("URL Test" + number);

        aso.setDataEmissao(LocalDate.of(2026, 5, 1 + number));
        aso.setDataValidade(aso.getDataEmissao().plusYears(1));

        aso.setTipoAso(tipoAsos[number % tipoAsos.length]);
        aso.setResultadoAso(((number % 2)==0) ? ResultadoAso.APTO : ResultadoAso.INAPTO);

        return aso;
    }

}