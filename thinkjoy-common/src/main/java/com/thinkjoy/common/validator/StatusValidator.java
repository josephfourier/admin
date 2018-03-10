package com.thinkjoy.common.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import com.thinkjoy.common.base.BaseConstants;

/**
 * 校验不为null
 * Created by  on 2017/2/18.
 */
public class StatusValidator extends ValidatorHandler<String> implements Validator<String> {

    private String fieldName;

    public StatusValidator(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public boolean validate(ValidatorContext context, String s) {
        if (s.equals(BaseConstants.Status.LOCKING)||s.equals("锁定")) {
            context.addError(ValidationError.create(String.format("%s锁定,信息不能维护！", fieldName))
                    .setErrorCode(-1)
                    .setField(fieldName)
                    .setInvalidValue(s));
            return false;
        }
        return true;
    }






}
