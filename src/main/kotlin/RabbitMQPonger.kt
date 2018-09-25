/**
 * Created by Matteo Gabellini on 25/09/2018.
 */
class RabbitMQPonger(connector: BrokerConnector) : Ponger {

    private lateinit var publisher: RabbitMQPublisher
    private lateinit var subscriber: RabbitMQSubscriber

    init {
        publisher = RabbitMQPublisher(connector)
        subscriber = RabbitMQSubscriber(connector)

        val onPing = subscriber.createStringConsumer { X ->
            println("Ping received...");
            this.pong()
        }
        subscriber.subscribe("pingTopic", onPing)
    }

    override fun pong() {
        this.publisher.publish("pong", "pongTopic");
    }


}

fun main(argv: Array<String>) {
    BrokerConnector.init("localhost", "PingPong")
    val ponger = RabbitMQPonger(BrokerConnector.INSTANCE)
}