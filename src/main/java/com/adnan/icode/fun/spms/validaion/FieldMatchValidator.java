package com.adnan.icode.fun.spms.validaion;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.internal.util.privilegedactions.NewInstance;
import org.springframework.beans.BeanWrapperImpl;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
	
	private String firstField;
	private String secondField;
	private String message;
	
	
	
	@Override
	public void initialize(FieldMatch constraintAnnotation) {
		this.firstField = constraintAnnotation.first();
		this.secondField = constraintAnnotation.second();
		this.message = constraintAnnotation.message();
	}



	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		
		final Object firstObj = new BeanWrapperImpl(value).getPropertyValue(firstField);
		final Object secondObj = new BeanWrapperImpl(value).getPropertyValue(secondField);
		if(firstObj != null && secondObj != null) {
			
		if (firstObj.equals(secondObj)) {
			return true;
		}else {
			
			
	            context.buildConstraintViolationWithTemplate(message)
	                    .addPropertyNode(firstField)
	                    .addConstraintViolation()
	                    .disableDefaultConstraintViolation();       
		}
		
		}
		return false;
		
	}

}
