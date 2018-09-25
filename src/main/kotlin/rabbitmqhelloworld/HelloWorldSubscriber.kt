package rabbitmqhelloworld

import com.rabbitmq.client.*
import java.io.IOException

/**
 * Created by Matteo Gabellini on 26/09/2018.
 */
class HelloWorldSubscriber {

    fun subscribe() {
        //Establish connection to the Broker
        var factory: ConnectionFactory = ConnectionFactory()
        var connection: Connection
        var channel: Channel

        factory.host = "localhost"
        connection = factory.newConnection()
        channel = connection.createChannel()

        // create a
        // non-durable (the queue will not survive a broker restart)
        // exclusive (used by only one connection and the queue will be deleted when that connection closes)
        // autodelete (queue that has had at least one consumer is deleted when last consumer unsubscribes)
        // queue with a generated name:
        val queueName: String = channel.queueDeclare().queue

        val exchangeName = "HelloExchange"
        //declare the exchange
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT)

        //bind the queue to the exchange
        //after this the queue will receive from the exchange all message with the specified routing key
        val routingKey = "helloMessage"
        channel.queueBind(queueName, exchangeName, routingKey)



        val helloWorldMessagesConsumer: Consumer = object : DefaultConsumer(channel) {
                @Throws(IOException::class)
                override fun handleDelivery(consumerTag: String,
                                            envelope: Envelope,
                                            properties: AMQP.BasicProperties,
                                            body: ByteArray) {
                    val messageContent = String(body, Charsets.UTF_8)
                    println("Received message with content: " + messageContent)
                }
            }


        //bind a consumer to the queue
        channel.basicConsume(queueName, true, helloWorldMessagesConsumer)

    }

}

fun main(argv: Array<String>) {
    val sub = HelloWorldSubscriber()
    sub.subscribe()
}