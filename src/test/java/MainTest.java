import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: MainTest
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author lcc
 * @Create 2023/3/20 18:51
 * @Version
 */
public class MainTest {

    @Test
    public void test() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123456"));
    }

    @Test
    public void toDate() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String dateString = "2023/03/31";
        System.out.println(dateString);
        Date date = simpleDateFormat.parse(dateString);
        System.out.println(date);  // 输出解析后的 Date 对象
    }

    @Test
    public void getRandom() {
        Random random = new Random();
        int randomNum = random.nextInt(90000000) + 10000000;
        System.out.println(randomNum);
    }

    @Test
    public void TestListAdd() {
        List<String> name = new ArrayList<>();
        name.add("sdsd");
        name.add(null);
        name.add("sdds");
        System.out.println(name);
    }

    @Test
    public void time() {
        System.out.println(TimeUnit.DAYS.convert(269374154, TimeUnit.MILLISECONDS));
    }
}
