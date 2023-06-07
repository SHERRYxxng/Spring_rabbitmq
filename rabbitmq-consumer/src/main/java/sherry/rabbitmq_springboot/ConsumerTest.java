package sherry.rabbitmq_springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description:
 * @Author: SHERRY
 * @email: <a href="mailto:SherryTh743779@gmail.com">TianHai</a>
 * @Date: 2023/6/7 14:20
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-rabbitmq.xml")
public class ConsumerTest {
    /*
    测试消费
     */
    @Test
    public void testConsume(){
        while(true){

        }

    }

}
