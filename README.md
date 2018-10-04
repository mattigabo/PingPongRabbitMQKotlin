# PingPongRabbitMQKotlin

This is a basic implementation of the "Ping-Pong" message exchange between two services using RabbitMQ (AMQP protocol) with Kotlin.

In the "Ping-Pong" there are two entities:
        
 - "Pinger" -> sends a message "ping" to the ponger when receive a message "pong" 
 - "Ponger" -> sends a message "pong" to the pinger when receive a message "ping"
 
Pinger start the exchange sending a "ping" message when boot up.       


This is one of the code examples created for the course in "Distributed Systems"