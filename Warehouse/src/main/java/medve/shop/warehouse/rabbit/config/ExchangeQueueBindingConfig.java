package medve.shop.warehouse.rabbit.config;



import medve.shop.warehouse.WarehouseApplication;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
public class ExchangeQueueBindingConfig  extends SpringBootServletInitializer implements RabbitListenerConfigurer {


    @Autowired
    private ApplicationConfigReader applicationConfig;

    public ApplicationConfigReader getApplicationConfig() {
        return applicationConfig;
    }


    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WarehouseApplication.class);
    }

    /* This bean is to read the properties file configs */
    @Bean
    public ApplicationConfigReader applicationConfig() {
        return new ApplicationConfigReader();
    }

    //OrderManagerAdd


    /* Creating a bean for the Message queue Exchange */
    @Bean
    public TopicExchange getWarehouseItemExchange() {
        return new TopicExchange(getApplicationConfig().getWarehouseItemExchange());
    }

    /* Creating a bean for the Message queue */
    @Bean
    public Queue getWarehouseItemQueue() {
        return new Queue(getApplicationConfig().getWarehouseItemQueue());
    }

    /* Binding between Exchange and Queue using routing key */
    @Bean
    public Binding declareBindingWarehouseItem() {
        return BindingBuilder.bind(getWarehouseItemQueue()).to(getWarehouseItemExchange()).with(getApplicationConfig().getWarehouseItemRoutingKey());
    }



    /* Bean for rabbitTemplate */
    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(consumerJackson2MessageConverter());
        return factory;
    }

    @Override
    public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

}
