package PedroM_Guerra.controle_aso.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

//@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GeneroFuncionario {
    MASCULINO("M", "Masculino"),
    FEMININO("F", "Feminino"),
    NAO_INFORMADO("N", "Não Informado");

    private final String codigo;
    private final String descricao;

    GeneroFuncionario(String codigo, String descricao) {
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
            default -> NAO_INFORMADO;
        };
    }

    @JsonValue
    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }
}