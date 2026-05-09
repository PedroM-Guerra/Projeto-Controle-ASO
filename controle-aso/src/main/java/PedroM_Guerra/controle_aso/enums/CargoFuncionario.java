package PedroM_Guerra.controle_aso.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CargoFuncionario {
    ESTAGIARIO("EST", "Estagiário"),
    SUPERVISOR("SUP", "Supervisor"),
    DIRETOR("DIR", "Diretor"),
    ENGENHEIRO("ENG", "Engenheiro"),
    TECNICO("TI", "Técnico de TI"),
    MEDICO("MED", "Médico"),
    ARQUITETO("ARQ", "Arquiteto"),
    OPERARIO("OPR","Operário de Obras"),
    SECRETARIO("SEC","Secretário"),
    AUXILIAR("AUX","Auxiliar de Limpeza");

    private final String codigo;
    private final String descricao;

    private CargoFuncionario(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public String getCodigo() { return codigo; }
    public String getDescricao() { return descricao; }

    @JsonCreator
    public static CargoFuncionario getEnum(String valor) {
        if (valor == null) {
            return null;
        }

        return switch (valor.toUpperCase()) {
            case "EST" -> ESTAGIARIO;
            case "SUP" -> SUPERVISOR;
            case "DIR" -> DIRETOR;
            case "ENG" -> ENGENHEIRO;
            case "ARQ" -> ARQUITETO;
            case "TI"  -> TECNICO;
            case "MED" -> MEDICO;
            case "OP"  -> OPERARIO;
            case "SEC" -> SECRETARIO;
            case "AUX" -> AUXILIAR;
            default -> throw new IllegalArgumentException("Cargo inválido: " + valor);
        };
    }
}



