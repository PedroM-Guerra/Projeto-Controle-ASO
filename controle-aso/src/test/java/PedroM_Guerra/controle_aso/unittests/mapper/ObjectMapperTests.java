package PedroM_Guerra.controle_aso.unittests.mapper;
import static PedroM_Guerra.controle_aso.mapper.ObjectMapper.parseListObjects;
import static PedroM_Guerra.controle_aso.mapper.ObjectMapper.parseObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.List;

import PedroM_Guerra.controle_aso.data.dto.FuncionarioDTO;
import PedroM_Guerra.controle_aso.enums.CargoFuncionario;
import PedroM_Guerra.controle_aso.enums.GeneroFuncionario;
import PedroM_Guerra.controle_aso.enums.SetorFuncionario;
import PedroM_Guerra.controle_aso.model.Funcionario;
import PedroM_Guerra.controle_aso.unittests.mapper.mocks.MockFuncionario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ObjectMapperTests {
    MockFuncionario inputObject;

    @BeforeEach
    public void setUp() {
        inputObject = new MockFuncionario();
    }

    @Test
    public void parseEntityToDTOTest() {
        FuncionarioDTO output = parseObject(inputObject.mockEntity(), FuncionarioDTO.class);

        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("Name Test0", output.getNome());
        assertEquals("CPF Test0", output.getCpf());
        assertEquals("Matricula Test0", output.getMatricula());
        assertEquals(GeneroFuncionario.MASCULINO, output.getGenero());

        assertEquals(CargoFuncionario.ESTAGIARIO, output.getCargo());
        assertEquals(SetorFuncionario.RECURSOS_HUMANOS, output.getSetor());

        assertEquals(LocalDate.of(1980, 1, 1), output.getDataNascimento());
        assertEquals(LocalDate.of(1981, 1, 1), output.getDataAdmissao());
        assertEquals(LocalDate.of(1981, 2, 1), output.getDataDemissao());

    }


    @Test
    public void parseEntityListToDTOListTest() {
        List<FuncionarioDTO> outputList = parseListObjects(inputObject.mockEntityList(), FuncionarioDTO.class);
        FuncionarioDTO outputZero = outputList.get(0);

        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("Name Test0", outputZero.getNome());
        assertEquals("CPF Test0", outputZero.getCpf());
        assertEquals("Matricula Test0", outputZero.getMatricula());
        assertEquals(GeneroFuncionario.MASCULINO, outputZero.getGenero());

        assertEquals(SetorFuncionario.RECURSOS_HUMANOS, outputZero.getSetor());
        assertEquals(CargoFuncionario.ESTAGIARIO, outputZero.getCargo());

        assertEquals(LocalDate.of(1980, 1, 1), outputZero.getDataNascimento());
        assertEquals(LocalDate.of(1981, 1, 1), outputZero.getDataAdmissao());
        assertEquals(LocalDate.of(1981, 2, 1), outputZero.getDataDemissao());

        FuncionarioDTO outputSeven = outputList.get(7);

        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("Name Test7", outputSeven.getNome());
        assertEquals("CPF Test7", outputSeven.getCpf());
        assertEquals("Matricula Test7", outputSeven.getMatricula());
        assertEquals(GeneroFuncionario.FEMININO, outputSeven.getGenero());

        assertEquals(CargoFuncionario.OPERARIO, outputSeven.getCargo());
        assertEquals(SetorFuncionario.SAUDE_TRABALHO, outputSeven.getSetor());

        assertEquals(LocalDate.of(1987, 8, 8), outputSeven.getDataNascimento());
        assertEquals(LocalDate.of(1989, 3, 8), outputSeven.getDataAdmissao());
        assertNull(outputSeven.getDataDemissao());

        FuncionarioDTO outputTwelve = outputList.get(12);

        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("Name Test12", outputTwelve.getNome());
        assertEquals("CPF Test12", outputTwelve.getCpf());
        assertEquals("Matricula Test12", outputTwelve.getMatricula());
        assertEquals(GeneroFuncionario.MASCULINO, outputZero.getGenero());

        assertEquals(CargoFuncionario.DIRETOR, outputTwelve.getCargo());
        assertEquals(SetorFuncionario.ARQUITETURA, outputTwelve.getSetor());

        assertEquals(LocalDate.of(1992, 1, 13), outputTwelve.getDataNascimento());
        assertEquals(LocalDate.of(1993, 1, 13), outputTwelve.getDataAdmissao());
        assertEquals(LocalDate.of(1993, 2, 13), outputTwelve.getDataDemissao());
    }

    @Test
    public void parseDTOToEntityTest() {
        Funcionario output = parseObject(inputObject.mockDTO(), Funcionario.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("Name Test0", output.getNome());
        assertEquals("CPF Test0", output.getCpf());
        assertEquals("Matricula Test0", output.getMatricula());
        assertEquals(GeneroFuncionario.MASCULINO, output.getGenero());

        assertEquals(CargoFuncionario.ESTAGIARIO, output.getCargo());
        assertEquals(SetorFuncionario.RECURSOS_HUMANOS, output.getSetor());


        assertEquals(LocalDate.of(1980, 1, 1), output.getDataNascimento());
        assertEquals(LocalDate.of(1981, 1, 1), output.getDataAdmissao());
        assertEquals(LocalDate.of(1981, 2, 1), output.getDataDemissao());
    }

    @Test
    public void parserDTOListToEntityListTest() {
        List<Funcionario> outputList = parseListObjects(inputObject.mockDTOList(), Funcionario.class);
        Funcionario outputZero = outputList.get(0);

        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("Name Test0", outputZero.getNome());
        assertEquals("CPF Test0", outputZero.getCpf());
        assertEquals("Matricula Test0", outputZero.getMatricula());
        assertEquals(GeneroFuncionario.MASCULINO, outputZero.getGenero());

        assertEquals(SetorFuncionario.RECURSOS_HUMANOS, outputZero.getSetor());
        assertEquals(CargoFuncionario.ESTAGIARIO, outputZero.getCargo());

        assertEquals(LocalDate.of(1980, 1, 1), outputZero.getDataNascimento());
        assertEquals(LocalDate.of(1981, 1, 1), outputZero.getDataAdmissao());
        assertEquals(LocalDate.of(1981, 2, 1), outputZero.getDataDemissao());

        Funcionario outputSeven = outputList.get(7);

        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("Name Test7", outputSeven.getNome());
        assertEquals("CPF Test7", outputSeven.getCpf());
        assertEquals("Matricula Test7", outputSeven.getMatricula());
        assertEquals(GeneroFuncionario.FEMININO, outputSeven.getGenero());

        assertEquals(CargoFuncionario.OPERARIO, outputSeven.getCargo());
        assertEquals(SetorFuncionario.SAUDE_TRABALHO, outputSeven.getSetor());

        assertEquals(LocalDate.of(1987, 8, 8), outputSeven.getDataNascimento());
        assertEquals(LocalDate.of(1989, 3, 8), outputSeven.getDataAdmissao());
        assertNull(outputSeven.getDataDemissao());

        Funcionario outputTwelve = outputList.get(12);

        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("Name Test12", outputTwelve.getNome());
        assertEquals("CPF Test12", outputTwelve.getCpf());
        assertEquals("Matricula Test12", outputTwelve.getMatricula());
        assertEquals(GeneroFuncionario.MASCULINO, outputZero.getGenero());

        assertEquals(CargoFuncionario.DIRETOR, outputTwelve.getCargo());
        assertEquals(SetorFuncionario.ARQUITETURA, outputTwelve.getSetor());

        assertEquals(LocalDate.of(1992, 1, 13), outputTwelve.getDataNascimento());
        assertEquals(LocalDate.of(1993, 1, 13), outputTwelve.getDataAdmissao());
        assertEquals(LocalDate.of(1993, 2, 13), outputTwelve.getDataDemissao());
    }
}