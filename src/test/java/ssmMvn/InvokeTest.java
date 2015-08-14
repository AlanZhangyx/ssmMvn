package ssmMvn;

import java.lang.annotation.Annotation;

public class InvokeTest {

    public static void main(String[] args) {
        //如果有Action2注解
        if (ActionTest.class.isAnnotationPresent(Action2.class)) {
            Annotation annotation = ActionTest.class.getAnnotation(Action2.class);
            
        }
        
    }

}
