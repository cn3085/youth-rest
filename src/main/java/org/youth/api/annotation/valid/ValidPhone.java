package org.youth.api.annotation.valid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@NotBlank(message = "핸드폰 번호는 필수값입니다.")
@Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "핸드폰 번호가 형식에 맞지 않습니다. (xxx-xxxx-xxxx)")
@Inherited
@Retention(value=RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
public @interface ValidPhone {
}
