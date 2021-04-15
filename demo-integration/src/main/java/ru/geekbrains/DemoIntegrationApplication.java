package ru.geekbrains;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.Channels;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.jpa.dsl.Jpa;
import ru.geekbrains.model.Product;

import javax.persistence.EntityManagerFactory;
import java.util.function.Function;


@SpringBootApplication
@EntityScan(basePackageClasses = Product.class)
public class DemoIntegrationApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(DemoIntegrationApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public IntegrationFlow retrievingGatewayFlow() {
        return f -> f
                .handle(Jpa.retrievingGateway(this.entityManagerFactory)
                        .jpaQuery("from products s where s.id = 2")
                        .expectSingleResult(true)
                        .parameterExpression("id", "payload"))
                .channel(new Function<Channels, MessageChannelSpec<?, ?>>() {
                    @Override
                    public MessageChannelSpec<?, ?> apply(Channels c) {
                        System.out.println(c.queue("retrieveResults").get().clear());
                        return c.queue("retrieveResults");
                    }
                });
    }
}
