import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Consumer
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope

/**
 *
 * Wrapper of a Subscriber for RabbitMq Broker
 *
 * Created by Matteo Gabellini on 25/09/2018.
 */
class RabbitMQSubscriber(val connector: BrokerConnector) : Subscriber<String, Consumer> {

    private var subscribedChannel: HashMap<String, String> = HashMap()

    override fun subscribe(topic: String, consumingLogic: Consumer) {
        if (!subscribedChannel.containsKey(topic)) {
            val queueName: String = connector.getQueue()

            connector.channel.queueBind(queueName, connector.EXCHANGE_NAME, topic)
            subscribedChannel.put(
                    topic,
                    connector.channel.basicConsume(queueName, true, consumingLogic
                    ))
        }
    }

    override fun unsubscribe(topic: String) {
        connector.channel.basicCancel(subscribedChannel.get(topic))
        subscribedChannel.remove(topic)
    }


    override fun subscribedTopics(): Set<String> {
        val set = subscribedChannel.keys
        return set
    }

    fun createStringConsumer(messageHandler: (String) -> Any): Consumer {
        return object : DefaultConsumer(connector.channel) {
            @Throws(java.io.IOException::class)
            override fun handleDelivery(consumerTag: String,
                                        envelope: Envelope,
                                        properties: AMQP.BasicProperties,
                                        body: ByteArray) {
                val message = String(body, Charsets.UTF_8)
                messageHandler(message)
            }
        }
    }
}

fun main(argv: Array<String>) {
    BrokerConnector.init("localhost")
    val sub = RabbitMQSubscriber(BrokerConnector.INSTANCE)

    val consumer = sub.createStringConsumer { X ->
        println(X)
    }
    sub.subscribe("hello", consumer)

    //BrokerConnector.INSTANCE.close()
}