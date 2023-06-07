package sherry.rabbitmq_model04Direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import sherry.util.ConnectionUtil;

/**
 * @Description: 初学MQ之路由模式的生产者 路由模式相对广播,订阅模式来说多了路由key使交换机不再把消息交给每一个绑定的队列而<br/>
 *                  是分组了根据队列的不同key来给不同的消息<br/>
 *                  首先创建了2个枚举属性用于队列的名字<br/>
 *                  再通过工具类实现了工厂对象的创建和数据源的配置<br/>
 *                  创建频道<br/>
 *                  随后声明了一个  &精准查询模式 DIRECT  的交换机<br/>
 *                  在创建了2个队列<br/>
 *                  将交换机和队列进行绑定  &同时添加路由<br/>
 *                  发布路由发布<br/>
 * @Exchange:  交换机有四种模式我们常用的只有三种模式广播模式,定向模式和通配符模式,交换机的作用:接收客户端发送过来的消息,转发接收到的消息给队列
 * @Author: SHERRY
 * @email: <a href="mailto:SherryTh743779@gmail.com">TianHai</a>
 * @Date: 2023/6/5 20:47
 */
public class Producer {
    //设置两个常量
    public static final String QUEUE_NAME1="Routing_queue1";
    public static final String QUEUE_NAME2="Routing_queue2";

    public static void main(String[] args) throws Exception {
        //获取连接通道 大连接
        Connection connection = ConnectionUtil.getConnection();
        //创建信道,频道 小连接,可以在这个大连接上创建多个小连接
        Channel channel = connection.createChannel();
        /*
         * 声明交换机
         * 参数说明:
         * @param exchange 指定交换机的名字
         * @param type     指定交换机的类型
         * BuiltinExchangeType type:
         *          DIRECT("direct"):表示发送消息,类似mysql里面的精准查询
         *          FANOUT("FANOUT"):表示广播模式,消息一旦发送出去,所有的队列都可以接收到消息
         *          TOPIC("topic"):主题(模糊匹配),类似mysql中的模糊查询
         *          HEADERS("headers")::开发中不会使
         * @booolean durable  指定交换机是否是持久的,->是否持久化
         * @booolean autoDelete 是否自动删除
         * @booolean internal 是否内部使用
         * @Map<String,Object> arguments 是否需要携带参数
         */

        channel.exchangeDeclare("text_Routing",
                BuiltinExchangeType.DIRECT,
                true,
                false,
                null);
    /*
    声明一个队列
    String queue:表示队列的名称
    boolean durable:如果为true 队列需要进行持久化操作
    boolean exclusive:如果为true,我们就会独占这个队列,这个地方设置为false,不会独占
    boolean autoDelete: 表示自动删除,如果队列不用是否需要自动删除
    Map<String,Object> arguments  :是否需要携带参数
     */
        //频道,通信->队列声明
    channel.queueDeclare(QUEUE_NAME1,true,false,false,null);
    channel.queueDeclare(QUEUE_NAME2,true,false,false,null);
    //客户端需要跟服务器发送消息

        /*
        交换机需要把消息转发给队列,为了转发方便所以交换机需要跟队列进行绑定
        queue – 表示队列
        exchange – 表示交换机
        routingKey – 表示路由规则
         arguments – 表示消息体
         */
        //这里比订阅模式多了;路由key的判断让特定的消息只能特定的队列接受到
        channel.queueBind(QUEUE_NAME1,"text_Routing","error");


        channel.queueBind(QUEUE_NAME2,"text_Routing","error");
        channel.queueBind(QUEUE_NAME2,"text_Routing","info");
        channel.queueBind(QUEUE_NAME2,"text_Routing","warning");
        channel.queueBind(QUEUE_NAME2,"text_Routing","sherry");

        /*
        exchange – 表示交换机
        routingKey – 表示路由规则
        BasicProperties - 表示发送消息的时候是否携带属性
        byte[] - 表示消息体
         */
        String message="许仙,一念";
        channel.basicPublish("text_Routing","info",null,message.getBytes());
        ConnectionUtil.closeResource(channel,connection);
    }

}
