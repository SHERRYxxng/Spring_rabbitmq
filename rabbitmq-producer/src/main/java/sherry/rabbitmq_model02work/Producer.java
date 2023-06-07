package sherry.rabbitmq_model02work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import sherry.util.ConnectionUtil;

/**
 * @Description: 初学MQ之工作模式的生产者
 * @Author: SHERRY
 * @email: <a href="mailto:SherryTh743779@gmail.com">TianHai</a>
 * @Date: 2023/6/5 19:03
 **/
public class Producer {
    public static final String QUEUE_NAME="worker_queue";
    public static void main(String[] args) throws Exception {
        //获取连接
        Connection connection = ConnectionUtil.getConnection();
        //创建信道
        Channel channel = connection.createChannel();
        //声明队列 队列名为worker_queue 持久 不排他 不删除
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        //一次发送10个消息
        for (int i = 0; i < 10; i++) {
            //设置要发送的消息
            String message="消息"+(i+1);
            //参数分别为
            // 1.交换机名字为默认,AMQP, 2.在简单模式中为队列名称,本来应该为路由规则,3.属性,4.消息体->要发送的消息
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        }
        ConnectionUtil.closeResource(channel,connection);
    }
}
