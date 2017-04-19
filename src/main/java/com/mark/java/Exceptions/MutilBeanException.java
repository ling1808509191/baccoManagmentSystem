package com.mark.java.Exceptions;

/**
 * Created by vcc on 2017/3/24.
 */
public class MutilBeanException extends  Exception {
  private   String BeanName ="";
    public MutilBeanException(String BeanName){

        super(BeanName);
    }

    @Override
    public String getMessage() {
        return "In table "+BeanName+" ,There should be one bean,but we found more than one";
    }
}
