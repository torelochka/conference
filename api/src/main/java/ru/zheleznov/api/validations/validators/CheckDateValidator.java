package ru.zheleznov.api.validations.validators;

import ru.zheleznov.api.forms.TalkCreateForm;
import ru.zheleznov.api.validations.annotations.CheckDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckDateValidator implements ConstraintValidator<CheckDate, TalkCreateForm> {

    @Override
    public boolean isValid(TalkCreateForm talkCreateForm, ConstraintValidatorContext constraintValidatorContext) {
        if (talkCreateForm.getDateStart() != null && talkCreateForm.getDateEnd() != null) {
            long diff = talkCreateForm.getDateEnd().getTime() - talkCreateForm.getDateStart().getTime();

            int minutes = (int) (diff / (60 * 1000));
            return minutes > 0 && minutes <= 120 ;
        }

        return false;
    }
}
