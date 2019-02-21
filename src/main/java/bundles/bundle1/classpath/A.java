package bundles.bundle1.classpath;

/**
 * @author yunfan.gyf
 **/
public class A {
    public void say() {
        System.out.println("Hello I'm A,I'm loaded by " + A.class.getClassLoader());
    }

    public static void main(String[] args) {

    }
}
