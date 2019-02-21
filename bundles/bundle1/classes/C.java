import bundles.bundle1.classpath.A;

public class C{
    public void say() {
        System.out.println("Hello I'm A,I'm loaded by " + A.class.getClassLoader());
    }

    public static void main(String[] args) {

    }
}