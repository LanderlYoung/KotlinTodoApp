package io.github.landerlyoung.awesometodo.test;

import java.util.List;

/**
 * <pre>
 * Author: landerlyoung@gmail.com
 * Date:   2017-05-20
 * Time:   14:52
 * Life with Passion, Code with Creativity.
 * </pre>
 */

public class JavaClass {
    public void invokeKotlin() {
        KotlinTest kotlinTest = new KotlinTest();
        List<String> list = kotlinTest.collection();



        User kotlinUser = new User("taylor", "845490", 0);
        kotlinUser.hashCode();

        KotlinEnum.ONE.name();

    }
}
