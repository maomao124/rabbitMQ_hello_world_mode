package mao;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * Project name(项目名称)：rabbitMQ_hello_world模式
 * Package(包名): mao
 * Class(类名): Consumer
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/4/21
 * Time(创建时间)： 20:11
 * Version(版本): 1.0
 * Description(描述)： 无
 */

public class Consumer
{
    //队列名称
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException
    {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("123456");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        System.out.println("等待接收消息");
        //使用服务器生成的 consumerTag 启动一个 non-nolocal、non-exclusive 消费者。
        // 仅提供对basic.deliver和basic.cancel AMQP 方法的访问（这对于大多数情况来说已经足够了）
        //queue - 队列的名称
        //autoAck - 如果服务器应该认为消息在传递后得到确认，则为 true；如果服务器应该期待明确的确认，则为 false
        //deliverCallback - 消息传递时的回调
        //cancelCallback - 消费者取消时的回调
        channel.basicConsume(QUEUE_NAME, true, new DeliverCallback()
        {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException
            {
                byte[] messageBody = message.getBody();
                String message1 = new String(messageBody, StandardCharsets.UTF_8);
                System.out.println("消息为：" + message1);
            }
        }, new CancelCallback()
        {
            @Override
            public void handle(String consumerTag) throws IOException
            {
                System.out.println("消息被中断");
                System.out.println("中断：" + consumerTag);
            }
        });

    }
}
