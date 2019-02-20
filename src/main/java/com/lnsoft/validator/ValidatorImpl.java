package com.lnsoft.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * Created by chr on 2019/1/1/0001.
 * spring-bean初始化完成之后回调该方法
 */
@Component
public class ValidatorImpl implements InitializingBean {

    //javax的定义的接口工具
    private Validator validator;

    //实现检验其方法并返回校验结果
    public ValidationResult validate(Object bean) {
        final ValidationResult result = new ValidationResult();
        //入参为校验的bean，出参为ConstraintViolation，
        //如果有bean里的参数的规则，只要违背了validation定义的Annotation，constraintViolationSet就有值
        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(bean);
        if (constraintViolationSet.size() > 0) {
            //有错误
            result.setHasErrors(true);
            constraintViolationSet.forEach(constraintViolation -> {//since jdk 8.0
                //获得异常信息
                String errorMessage = constraintViolation.getMessage();
                //获得哪个字段出错
                String propertyName = constraintViolation.getPropertyPath().toString();
                //放入ErrorMap,key:对应的属性，value：对应的错误码message
                /**
                 *     如：
                 *     @Max(value = 150, message = "年龄必须小于150岁")//最大值是150
                       private Integer age;

                        如果该信息输入age=200，有错，则key是age，value是年龄必须小于150岁
                 */
                result.getErrorMessageMap().put(propertyName, errorMessage);
            });
        }
        return result;
    }

    //校验器
    @Override
    public void afterPropertiesSet() throws Exception {
        //将hibernate的validator通过工厂的初始化方式 进行初始化
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
}
