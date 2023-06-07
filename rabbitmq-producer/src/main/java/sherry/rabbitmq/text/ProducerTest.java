package sherry.rabbitmq.text;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description: 这个类是用来测试springboot整合rabbitmq的
 * @Author: SHERRY
 * @email: <a href="mailto:SherryTh743779@gmail.com">TianHai</a>
 * @Date: 2023/6/6 19:54
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-rabbitmq.xml")
public class ProducerTest  {
    //注入兔子mq的模版对象
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /*
    测试简单模式
     */
    @Test
    public void testSimple(){

    }
}
