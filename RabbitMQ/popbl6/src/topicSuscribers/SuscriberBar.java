package topicSuscribers;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class SuscriberBar {
	final static String EXCHANGE_NAME = "userregister";
	final static String QUEUE_NAME = "userqueue";
	ConnectionFactory factory;
	
	public SuscriberBar() {
		factory = new ConnectionFactory();
		factory.setHost("mudley.duckdns.org");
		factory.setPort(5671);
		factory.setUsername("mudley");
		factory.setPassword("mudley");
		factory.setVirtualHost("/");
		try {
			factory.useSslProtocol();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

	public void suscribe() {
		
		Channel channel = null;
		try (Connection connection = factory.newConnection()){
			channel = connection.createChannel();
			boolean durableQueue = false;
			boolean exclusive = false;
			boolean autodelete = false;
			Map<String,Object> arguments = null;
			
			channel.queueDeclare(QUEUE_NAME, durableQueue, exclusive,autodelete,arguments);
			boolean durable = true;
			channel.exchangeDeclare(EXCHANGE_NAME, "topic", durable);
			
			channel.queueBind( QUEUE_NAME,EXCHANGE_NAME, "grupo.rock");
			channel.queueBind( QUEUE_NAME,EXCHANGE_NAME, "grupo.jazz");
			
			MiConsumer consumer = new MiConsumer(channel);
			boolean autoack = false;
			String tag = channel.basicConsume(QUEUE_NAME, autoack, consumer);
			
			System.out.println("Bar");
			
			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			channel.basicCancel(tag);
			channel.close();
			
		} catch (IOException | TimeoutException e) {
			
			e.printStackTrace();
		}
		
	}
	public synchronized void stop() {
		this.notify();
	}
	public class MiConsumer extends DefaultConsumer{

		public MiConsumer(Channel channel) {
			super(channel);
		}

		@Override
		public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
				throws IOException {
			
			String json = new String(body);
			
			System.out.println("Recibido:  " + json);
		}
		
		
	}
	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		SuscriberBar suscriber = new SuscriberBar();
		System.out.println(" Esperando mensaje. Pulsa return para terminar");
		Thread hiloEspera = new Thread(()-> {
			teclado.nextLine();
			suscriber.stop();
		});
		hiloEspera.start();
		suscriber.suscribe();
		teclado.close();
		
	}

}
