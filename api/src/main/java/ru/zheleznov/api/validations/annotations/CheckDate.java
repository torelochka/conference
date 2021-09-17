package ru.zheleznov.api.validations.annotations;

import ru.zheleznov.api.validations.validators.CheckDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CheckDateValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckDate {
    String message() default "The difference between the dates can not be more than 2 hours and less than 0";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
