import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author: yaobin
 * @Date: 2019/7/25 18:18
 */
public class 今天吃啥 {
    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        InputStream in = 今天吃啥.class.getClassLoader().getResourceAsStream("eatWhat.properties");
        properties.load(in);

        luckNum(properties);
    }

    public static void luckNum(Properties properties) {
        String Num = String.valueOf((int) ((Math.random() * properties.size()) + 1));
        System.out.println(properties.get(Num));
    }
}
