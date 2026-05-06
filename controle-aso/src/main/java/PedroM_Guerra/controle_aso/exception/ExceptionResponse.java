package PedroM_Guerra.controle_aso.exception;

import java.util.Date;

public record ExceptionResponse(Date timestamp, String message, String details) {}
