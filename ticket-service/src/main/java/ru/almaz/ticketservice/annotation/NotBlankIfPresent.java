package ru.almaz.ticketservice.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.almaz.ticketservice.validator.NotBlankIfPresentValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NotBlankIfPresentValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlankIfPresent {
    String message() default "Поле не должно быть пустым, если присутствует";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
