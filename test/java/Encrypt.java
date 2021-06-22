import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author index
 * @date 2021/6/21
 **/
public class Encrypt {
    @Test
    public void testEncrypt(){
        String encode = new BCryptPasswordEncoder().encode("123456");
        System.out.println(encode);
    }

    @Test
    public void testRSA(){

    }

}
