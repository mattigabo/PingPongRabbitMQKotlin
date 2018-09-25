package rabbitmqhelloworld

import com.rabbitmq.client.*

/**
 * Created by Matteo Gabellini on 26/09/2018.
 */
class HelloWorldPublisher {
    fun publish() {
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
        //create the exchange
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT)

        //bind the queue to the exchange
        //after this the queue will receive from the exchange all message with the specified routing key
        val routingKey = "helloMessage"

        val message: String = "Hello World RabbitMQ"

        //Publish message to the exchange
        channel.basicPublish(exchangeName, routingKey, null, message.toByteArray(charset("UTF-8")))
        println(" [x] Sent '$message'")

        channel.close();
        connection.close();
    }
}

fun main(argv: Array<String>) {
    val pub = HelloWorldPublisher()
    pub.publish()
}