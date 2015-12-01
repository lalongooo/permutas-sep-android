package com.throrinstudio.android.common.libs.validator;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Form Validation Class
 * <p/>
 * Immediately, only works with EditText
 * 
 * @author throrin19
 * @version 1.0
 */
public class Form {

	private List<AbstractValidate> mValidates = new ArrayList<AbstractValidate>();

	/**
	 * Function adding Validates to our form
	 * 
	 * @param validate
	 *            {@link AbstractValidate} Validate to add
	 */
	public void addValidates(AbstractValidate... validate) {
		for(AbstractValidate abstractValidate : validate){
			mValidates.add(abstractValidate);
		}
	}

	/**
	 * Function removing Validates from our form
	 * 
	 * @param validate
	 *            {@link AbstractValidate} Validate to remove
	 * @return isValid that was removed from the form
	 */
	public boolean removeValidates(AbstractValidate validate) {
		if (mValidates != null && !mValidates.isEmpty()) {
			return mValidates.remove(validate);
		}
		return false;
	}

	/**
	 * Called to isValid our form.
	 * If an error is found, it will be displayed in the corresponding field.
	 * 
	 * @return boolean true if the form is valid, otherwise false
	 */
	public boolean isValid() {
		boolean formValid = true;
		for (AbstractValidate validate : mValidates) {
			formValid = formValid & validate.isValid();	// Use & in order to evaluate both side of the operation.
		}
		return formValid;
	}

	/**
	 * Closes the error popup of the text view.
	 * A little useless due to ability to just call source.setError(null), but added anyways
	 * 
	 * @param sourceTextView
	 * @author Dixon D'Cunha (Exikle)
	 */
	public void closeError(TextView sourceTextView) {
		for (AbstractValidate av : mValidates) {
			Validate v = (Validate) av;
			if (v.getSource().equals(sourceTextView))
				v.getSource().setError(null);
		}
	}

	/**
	 * Closes all error pop ups that were created by validator
	 * 
	 * @author Dixon D'Cunha (Exikle)
	 */
	public void closeAllErrors() {
		for (AbstractValidate av : mValidates) {
			Validate v = (Validate) av;
			v.getSource().setError(null);
		}
	}
}
