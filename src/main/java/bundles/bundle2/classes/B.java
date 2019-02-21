package bundles.bundle2.classes;

import bundles.bundle1.classpath.Export;

/**
 * @author yunfan.gyf
 **/
public class B {
    public static void main(String[] args) {

    }
    public void say() {
        System.out.println("Hello I'm B,I'm loaded by " + B.class.getClassLoader());
        System.out.println("From B Call Exported");
        Export e = new Export();
        e.say();
    }
}
