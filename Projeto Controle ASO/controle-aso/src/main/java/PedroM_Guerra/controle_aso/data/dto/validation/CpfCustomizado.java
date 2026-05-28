package PedroM_Guerra.controle_aso.data.dto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.hibernate.validator.constraints.br.CPF;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {})
@Target({FIELD})
@Retention(RUNTIME)
@CPF(message = "Por favor, insira um CPF válido.")
public @interface CpfCustomizado {
    String message() default "O CPF inserido é inválido.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}