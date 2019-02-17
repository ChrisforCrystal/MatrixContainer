
import java.lang.reflect.Method;

/**
 * @author gaoyunfan
 */
public class TestHotSwap
{
    public static void main(String[] args)
    {
        Thread t = new Thread(new MonitorHotSwap());
        t.start();
    }

}

class MonitorHotSwap implements Runnable
{

    private String className = "Hot";
    private Class hotClazz = null;
    private HotSwapURLClassLoader hotSwapCL = null;


    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                initLoad();
                Object hot = hotClazz.newInstance();
                Method m = hotClazz.getMethod("hot");
                m.invoke(hot, null);
                Thread.sleep(1000);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void initLoad() throws ClassNotFoundException
    {
        hotSwapCL = HotSwapURLClassLoader.getClassLoader();
        hotClazz = hotSwapCL.loadClass(className);
    }
}