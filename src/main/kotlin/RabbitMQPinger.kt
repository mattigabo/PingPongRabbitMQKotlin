/**
 * Created by Matteo Gabellini on 25/09/2018.
 */
class RabbitMQPinger(connector: BrokerConnector) : Pinger {

    private lateinit var publisher: RabbitMQPublisher
    private lateinit var subscriber: RabbitMQSubscriber

    init {
        publisher = RabbitMQPublisher(connector)
        subscriber = RabbitMQSubscriber(connector)


        val onPong = subscriber.createStringConsumer { X ->
            println("Pong received...");
            this.ping()
        }
        subscriber.subscribe("pongTopic", onPong)
    }

    override fun ping() {
        this.publisher.publish("ping", "pingTopic");
    }


}

fun main(argv: Array<String>) {
    BrokerConnector.init("localhost", "PingPong")
    val pinger = RabbitMQPinger(BrokerConnector.INSTANCE)
    pinger.ping();
}