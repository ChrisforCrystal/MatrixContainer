public class B{
    public void say() {
        System.out.println("Hello I'm B,I'm loaded by " + B.class.getClassLoader());
        System.out.println("From B Call Exported");
        Export e = new Export();
        e.say();
    }
}