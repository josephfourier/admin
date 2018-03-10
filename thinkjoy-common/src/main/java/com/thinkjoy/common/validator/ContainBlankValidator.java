package com.thinkjoy.common.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import org.apache.commons.lang.StringUtils;

/**
 * 校验不为null
 * Created by  on 2017/2/18.
 */
public class ContainBlankValidator extends ValidatorHandler<String> implements Validator<String> {

    private String fieldName;

    public ContainBlankValidator(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public boolean validate(ValidatorContext context, String s) {
        if (s.contains(" ")) {
            context.addError(ValidationError.create(String.format("%s不能有空格!", fieldName))
                    .setErrorCode(-1)
                    .setField(fieldName)
                    .setInvalidValue(s));
            return false;
        }
        return true;
    }

}
