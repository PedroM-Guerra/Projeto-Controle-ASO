package PedroM_Guerra.controle_aso.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

//@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResultadoAso {
    APTO("A", "Apto"),
    INAPTO("I", "Inapto");

    private final String codigo;
    private final String descricao;

    ResultadoAso(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    @JsonCreator
    public static ResultadoAso getEnum(String valor) {
        if (valor == null) {
            return null;
        }
        return switch (valor.toUpperCase()) {
            case "A" -> APTO;
            case "I" -> INAPTO;
            default -> throw new IllegalArgumentException("Tipo de ASO inválido: " + valor);
        };
    }

    @JsonValue
    public String getCodigo() {
        return codigo; }

    public String getDescricao() {
        return descricao; }
}



