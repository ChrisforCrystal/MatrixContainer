public class A{
    public void say() {
        System.out.println("Hello I'm A,I'm loaded by " + A.class.getClassLoader());
    }
}