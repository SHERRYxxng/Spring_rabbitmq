package sherry.rabbitmq_springboot;

import jdk.nashorn.internal.ir.annotations.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description: springboot整合rabbitmq
 * @Author: SHERRY
 * @email: <a href="mailto:SherryTh743779@gmail.com">TianHai</a>
 * @Date: 2023/6/7 14:11
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-rabbitmq.xml")
public class ProducerTest {
    //注入RabbitTemplate对象注入rabbitMQ模版对象
    @Autowired //本地用@Autowired 远程用@Reference
    private RabbitTemplate rabbitTemplate;

    /*
    测试简单模式
     */
    @Test
    public void testSimple(){
        //向简单模式的队列发送消息 (简单模式的routingKey为队列名)
        rabbitTemplate.convertAndSend("spring_simple_queue","测试springboot整合的简单模式");
        //这两个方法最终调用的是一样的实现结果 但是convertSendAndReceive方法则既发送消息也等待消费者的响应。
        // 具体来说，这个方法会将消息发送到指定的队列，然后阻塞等待消费者返回结果，直到收到消费者的响应或超时为止，
        // 最终返回消费者的响应消息。这种方式适用于生产者需要通过消费者的响应来执行后续操作的场景。
        //rabbitTemplate.convertSendAndReceive("spring_simple_queue","测试springboot整合的简单模式");
    }
    @Test
    public void testFanout(){
        //
        rabbitTemplate.convertAndSend("spring_fanout_exchange",
                "测试springboot整合的广播模式");
    }
}
