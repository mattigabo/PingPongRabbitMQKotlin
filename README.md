# PingPongRabbitMQKotlin

This is a basic implementation of the "Ping-Pong" message exchange between two services using RabbitMQ (AMQP protocol) with Kotlin.

In the "Ping-Pong" there are two entities:
        
 - "Pinger" -> sends a "ping" message to the ponger when receive a "pong" message 
 - "Ponger" -> sends a "pong" message to the pinger when receive a "ping" message
 
"Pinger" start the exchange sending a "ping" message when boot up.       


This is one of the code examples created for the course in "Distributed Systems"