package PedroM_Guerra.controle_aso.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GeneroFuncionario {
    MASCULINO("M", "Masculino"),
    FEMININO("F", "Feminino"),
    NAO_INFORMADO("N", "Não Informado");

    private final String codigo;
    private final String descricao;

    private GeneroFuncionario(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    @JsonCreator
    public static GeneroFuncionario getEnum(String valor) {
        if (valor == null) {
            return null;
        }

        return switch (valor.toUpperCase()) {
            case "M" -> MASCULINO;
            case "F" -> FEMININO;
            case "N" -> NAO_INFORMADO;
            default -> NAO_INFORMADO;
        };
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }
}