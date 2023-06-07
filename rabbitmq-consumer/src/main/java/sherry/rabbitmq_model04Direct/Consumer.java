package sherry.rabbitmq_model04Direct;

import com.rabbitmq.client.*;
import sherry.util.ConnectionUtil;

import java.io.IOException;

/**
 * @Description: 初学MQ之路由模式的消费者1 这两个类是为了体现两者是怎么处理的,不会重复消费,每人均分
 * @Author: SHERRY
 * @email: <a href="mailto:SherryTh743779@gmail.com">TianHai</a>
 * @Date: 2023/6/5 20:15
 **/

public class Consumer {
    public static final String QUEUE_NAME1="Routing_queue1";
    public static final String QUEUE_NAME2="Routing_queue2";

    public static void main(String[] args) throws Exception {
        //获取连接
        Connection connection = ConnectionUtil.getConnection();
        //创建信道
        Channel channel = connection.createChannel();
        //创建Consumer对象
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //获取消费者唯一标识
                System.out.println("consumerTag = " + consumerTag);
                //获取交换机的名字
                System.out.println("envelope.getExchange() = " + envelope.getExchange());
                //获取路由key
                System.out.println(envelope.getRoutingKey());
                //获取当前消息是第几条消息
                System.out.println("envelope.getDeliveryTag() = " + envelope.getDeliveryTag());
                //获取消息主体->内容
                System.out.println("new String(body) = " + new String(body));

            }
        };
        //消费消息
        channel.basicConsume(QUEUE_NAME2,true,defaultConsumer);

    }
}
