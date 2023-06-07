package sherry.rabbitmq_model01simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description: 初学MQ的简单的生产者(不算模式)
 * @Author: SHERRY
 * @email: <a href="mailto:SherryTh743779@gmail.com">TianHai</a>
 * @Date: 2023/6/5 16:29
 **/
public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        /*
         * 客户端需要连接RabbitMQ服务器,类似mysql客户端,连接mysql服务器
         * 1.设置工厂和数据源
         * 2.从工厂对象中获取连接对象
         */
        //①设置数据源的操作 1.创建工厂对象
        ConnectionFactory connectionFactory=new ConnectionFactory();

        //① 2.设置主机
        connectionFactory.setHost("localhost");

        //① 3.设置端口
        //15672: 表示后端插件网站的端口
        //5672: 表示RabbitMQ服务器的端口
        connectionFactory.setPort(5672);

        //① 4.设置用户名
        connectionFactory.setUsername("guest");

        //① 5.设置密码
        connectionFactory.setPassword("guest");

        //① 6.为了避免消息错误,需要设置虚拟主机  为什么是斜线,我们在15672中看到的虚拟主机 Virtual Host的值就是"/"
        connectionFactory.setVirtualHost("/");


        //② 1.从工厂对象中获取连接对象也就是通道,   ->创建了一个大连接
        Connection connection=connectionFactory.newConnection();

        //默认情况下,系统使用的是TCP/IP协议,点对点,为了提高性能,底层开启了多线程
        //② 2.在connection对象里面开启子线程,需要多个频道对象, -> 大连接里面设置了一个小链接
        Channel channel=connection.createChannel();

        //② 3.创建(声明一个队列)->小链接里面创建了一个队列
        /*
         *  String  var1 : 表示队列的名称
         *  boolean var2 : 如果为true,队列需要进行持久化操作
         *  boolean var3 : 如果为true,我们会独占这个队列,一般我们设置为false,不会独占连接
         *  boolean var4 : 表示自动删除
         *  Map<String, Object> var5 : 是否需要携带参数
         */
        channel.queueDeclare("simple_queue",true,false,false,null);

        //② 4.客户端需要跟服务端发送消息. ->通过队列发消息
        /*
         * String var1: 第一个参数为exchange表示交换机的名字,这里不写为默认AMQP
         * String var2: 第二个参数表示路由规则, 在简单模式中指定为队列的名字!!记住是在简单模式中
         * AMQP.BasicProperties: 第三个参数表示属性,发送消息的时候是否需要携带属性
         * var3, byte[] var4: 表示消息体,要发布的消息字节数组
         */
        //我们写的是入门案例,不适用交换机,系统会默认给我们提供一个交换机,默认为AMQP消息协议的服务器交换机
        String message="你好,许仙";
        channel.basicPublish("","simple_queue",null,message.getBytes());













    }
}
