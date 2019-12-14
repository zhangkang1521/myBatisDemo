package org.zk;

import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ParameterTest {

    public void test(String a, int b){
    }

    @Test
    public void test1() {
        Method[] methods = ParameterTest.class.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
            System.out.println("-------------");
            Parameter[] parameters = method.getParameters();
            for (Parameter p : parameters) {
                if (p.isNamePresent()) {
                    System.out.println(p.getName());
                }
            }
        }
    }
}
