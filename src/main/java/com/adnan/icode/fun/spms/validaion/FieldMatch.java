package com.adnan.icode.fun.spms.validaion;

import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = FieldMatchValidator.class)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldMatch {
	
	public String message() default "";
	
	public Class<?>[] groups() default {};
	
	public Class<? extends Payload> [] payload() default {};
	
	public String first();
	
	public String second();

	
//Not Necessary	
//	@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
//	@Retention(RetentionPolicy.RUNTIME)
//	@Documented
//	@interface List {
//		FieldMatch [] value();
//	}
	
	
}
