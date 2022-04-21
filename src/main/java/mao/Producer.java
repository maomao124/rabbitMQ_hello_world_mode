package mao;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.TimeoutException;

/**
 * Project name(项目名称)：rabbitMQ_hello_world模式
 * Package(包名): mao
 * Class(类名): Producer
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/4/21
 * Time(创建时间)： 19:47
 * Version(版本): 1.0
 * Description(描述)： 生产者
 */

public class Producer
{
    //队列名称
    private static final String QUEUE_NAME = "hello";


    public static int getIntRandom(int min, int max)
    {
        if (min > max)
        {
            min = max;
        }
        return min + (int) (Math.random() * (max - min + 1));
    }

    public static int getIntRandom1(int min, int max)
    {
        if (min > max)
        {
            min = max;
        }
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException
    {
        //创建一个连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("123456");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        //生成一个队列
       /*
        声明一个队列
        参数：
        queue - 队列的名称
        durable - 如果我们声明一个持久队列，则为真（该队列将在服务器重新启动后继续存在）
        exclusive - 如果我们声明一个独占队列，则为真（仅限于此连接）
        autoDelete - 如果我们声明一个自动删除队列，则为真（服务器将在不再使用时将其删除）
        arguments - 队列的其他属性（构造参数）
        */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //消息
        String message = "hello,world----你好";


        while (true)
        {
             /*
             exchange - 将消息发布到交换机
             routingKey - 路由键
             mandatory - 如果要设置“强制”标志，则为 true
             immediate - 如果要设置 'immediate' 标志，则为 true。请注意，RabbitMQ 服务器不支持此标志。
             props - 消息的其他属性 - 路由标头等
             body - 消息正文
             */
            //发送
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("消息发送完毕");
            Thread.sleep(getIntRandom(1,10));
        }

    }

}
