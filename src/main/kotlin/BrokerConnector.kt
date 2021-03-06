/**
 * Singleton used to manage the connection with the RabbitMQ Broker
 *
 * Created by Matteo Gabellini on 25/01/2018.
 */

import com.rabbitmq.client.BuiltinExchangeType
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import java.util.concurrent.atomic.AtomicBoolean

class BrokerConnector  private constructor (host: String, val exchangeName: String) {

    private val factory: ConnectionFactory = ConnectionFactory()
    private val connection: Connection
    val channel:Channel

    init {
        factory.host = host
        connection = factory.newConnection()
        channel = connection.createChannel()
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT)
    }

    fun getNewQueue(): String = channel.queueDeclare().queue


    fun close() {
        channel.close()
        connection.close()
    }

    companion object {
        lateinit var INSTANCE: BrokerConnector
        val isInitialized = AtomicBoolean()
        fun init(host: String, exchangeName: String){
            if(!isInitialized.getAndSet(true)){
                INSTANCE = BrokerConnector(host, exchangeName)
            }
        }
    }
}
