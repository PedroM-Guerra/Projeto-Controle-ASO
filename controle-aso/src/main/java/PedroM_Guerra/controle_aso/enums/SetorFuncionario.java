package PedroM_Guerra.controle_aso.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SetorFuncionario {
    RECURSOS_HUMANOS("RH","Recursos Humanos"),
    LIMPEZA("LMP","Limpeza"),
    TECNOLOGIA_INFORMACAO("TI","Tecnologia da Informação"),
    ENGENHARIA("ENG","Engenharia"),
    ARQUITETURA("ARQ","Arquitetura"),
    OPERACIONAL("OP","Operacional"),
    ADMINISTRACAO("ADM","Administração"),
    SAUDE_TRABALHO("ST","Saúde do Trabalho");

    private final String codigo;
    private final String descricao;

    SetorFuncionario(String codigo, String descricao){
        this.codigo = codigo;
        this.descricao = descricao;
    }

    @JsonCreator
    public static SetorFuncionario getEnum(String codigo) {
        if (codigo == null) {
            return null;
        }
        return switch (codigo.toUpperCase()) {
            case "RH" -> RECURSOS_HUMANOS;
            case "LMP" -> LIMPEZA;
            case "TI" -> TECNOLOGIA_INFORMACAO;
            case "ENG" -> ENGENHARIA;
            case "ARQ" -> ARQUITETURA;
            case "OP" -> OPERACIONAL;
            case "ADM" -> ADMINISTRACAO;
            case "ST" -> SAUDE_TRABALHO;
            default -> throw new IllegalArgumentException("Setor inválido: " + codigo);
        };
        }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }
}
