public class Export{
    public void say() {
        System.out.println("Hello I'm Export,I'm loaded by "+Export.class.getClassLoader());
    }
}