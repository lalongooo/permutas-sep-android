package com.throrinstudio.android.common.libs.validator.validator;

import android.content.Context;

import com.throrinstudio.android.common.libs.validator.AbstractValidator;
import com.throrinstudio.android.common.libs.validator.ValidatorException;

/**
 * Validates whether the given value length is between the specified range
 *
 * @author Jorge E. Hernández (@lalongooo)
 */
public class LengthValidator extends AbstractValidator {


    private int mMinLength;
    private int mMaxLength;

    public LengthValidator(Context c, int errorMessageRes, int minLength, int maxLength) {
        super(c, errorMessageRes);
        this.mMinLength = minLength;
        this.mMaxLength = maxLength;
    }

    @Override
    public boolean isValid(String value) throws ValidatorException {
        return value.length() >= this.mMinLength && value.length() <= this.mMaxLength;
    }

    @Override
    public String getMessage() {
        return String.format(super.getMessage(), this.mMinLength, this.mMaxLength);
    }
}
