package br.com.btsoftware.algafood.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MultipleValidator implements ConstraintValidator<Multiple, Number> {

	private int numberMultiple;

	@Override
	public void initialize(Multiple constraintAnnotation) {
		this.numberMultiple = constraintAnnotation.number();
	}

	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {

		boolean valid = true;

		if (value != null) {
			var valueDecimal = BigDecimal.valueOf(value.doubleValue());
			var multipleDecimal = BigDecimal.valueOf(this.numberMultiple);
			var leftover = valueDecimal.remainder(multipleDecimal);/// resto

			valid = BigDecimal.ZERO.compareTo(leftover) == 0;
		}

		return valid;
	}

}
