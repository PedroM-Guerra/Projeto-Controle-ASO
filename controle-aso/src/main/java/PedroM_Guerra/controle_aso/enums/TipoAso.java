package PedroM_Guerra.controle_aso.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoAso {
    ADMISSIONAL("ADM", "Admissional"),
    PERIODICO("PER", "Periódico"),
    RETORNO_TRABALHO("RET", "Retorno ao Trabalho"),
    MUDANCA_FUNCAO("MUD", "Mudança de Função"),
    DEMISSIONAL("DEM", "Demissional");

    private final String codigo;
    private final String descricao;

    TipoAso(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    @JsonCreator
    public static TipoAso getEnum(String valor) {
        if (valor == null) {
            return null;
        }
        return switch (valor.toUpperCase()) {
            case "ADM" -> ADMISSIONAL;
            case "PER" -> PERIODICO;
            case "RET" -> RETORNO_TRABALHO;
            case "MUD" -> MUDANCA_FUNCAO;
            case "DEM" -> DEMISSIONAL;
            default -> throw new IllegalArgumentException("Tipo de ASO inválido: " + valor);
        };
    }
    public String getCodigo() {
        return codigo; }

    public String getDescricao() {
        return descricao; }
}



