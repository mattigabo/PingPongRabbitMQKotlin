package rabbitmqhelloworld

import com.rabbitmq.client.*

class HelloWorldPublisher {
    fun publish() {
        //Establish connection to the Broker
        var factory: ConnectionFactory = ConnectionFactory()
        var connection: Connection
        var channel: Channel

        factory.host = "localhost"
        connection = factory.newConnection()
        channel = connection.createChannel()

        val exchangeName = "HelloExchange"
        //create the exchange
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT)

        val routingKey = "helloMessage"

        val message: String = "Hello World RabbitMQ"

        //Publish message to the exchange with the defined routing key
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