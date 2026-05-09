package PedroM_Guerra.controle_aso.unittests.mocks;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import PedroM_Guerra.controle_aso.data.dto.FuncionarioDTO;
import PedroM_Guerra.controle_aso.enums.CargoFuncionario;
import PedroM_Guerra.controle_aso.enums.GeneroFuncionario;
import PedroM_Guerra.controle_aso.enums.SetorFuncionario;
import PedroM_Guerra.controle_aso.model.Funcionario;


public class MockFuncionario {


    public Funcionario mockEntity() {
        return mockEntity(0);
    }
    
    public FuncionarioDTO mockDTO() {
        return mockDTO(0);
    }
    
    public List<Funcionario> mockEntityList() {
        List<Funcionario> funcionarios = new ArrayList<Funcionario>();
        for (int i = 0; i < 14; i++) {
            funcionarios.add(mockEntity(i));
        }
        return funcionarios;
    }

    public List<FuncionarioDTO> mockDTOList() {
        List<FuncionarioDTO> funcionarios = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            funcionarios.add(mockDTO(i));
        }
        return funcionarios;
    }
    
    public Funcionario mockEntity(Integer number) {
        Funcionario funcionario = new Funcionario();
        SetorFuncionario[] setores = SetorFuncionario.values();
        CargoFuncionario[] cargos = CargoFuncionario.values();

        funcionario.setId(number.longValue());
        funcionario.setNome("Name Test" + number);
        funcionario.setCpf("CPF Test" + number);
        funcionario.setMatricula("Matricula Test" + number);
        funcionario.setGenero(((number % 2)==0) ? GeneroFuncionario.MASCULINO : GeneroFuncionario.FEMININO);
        funcionario.setDataNascimento(LocalDate.of(2000, 7, 1 + number));
        funcionario.setSetor(setores[number % setores.length]);
        funcionario.setCargo(cargos[number % cargos.length]);
        funcionario.setDataAdmissao(LocalDate.of(2025, 4, 1 + number));
        funcionario.setDataDemissao(LocalDate.of(2026, 3, 1 + number));

        return funcionario;
    }

    public FuncionarioDTO mockDTO(Integer number) {
        FuncionarioDTO funcionario = new FuncionarioDTO();
        SetorFuncionario[] setores = SetorFuncionario.values();
        CargoFuncionario[] cargos = CargoFuncionario.values();

        funcionario.setId(number.longValue());
        funcionario.setNome("Name Test" + number);
        funcionario.setCpf("CPF Test" + number);
        funcionario.setMatricula("Matricula Test" + number);
        funcionario.setGenero(((number % 2)==0) ? GeneroFuncionario.MASCULINO : GeneroFuncionario.FEMININO);
        funcionario.setDataNascimento(LocalDate.of(2000, 7, 1 + number));
        funcionario.setSetor(setores[number % setores.length]);
        funcionario.setCargo(cargos[number % cargos.length]);
        funcionario.setDataAdmissao(LocalDate.of(2025, 4, 1 + number));
        funcionario.setDataDemissao(LocalDate.of(2026, 3, 1 + number));

        return funcionario;
    }

}