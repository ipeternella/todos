package com.todos.config;

import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import com.mongodb.MongoClient;
import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;

/**
 * Config class to run an embedded version of mongoDB for local testing.
 * Not for production.
 * 
 * @author igp
 */

@Configuration
public class MongoConfig {
    
    private static final String MONGO_DB_URL = "localhost";
    private static final String MONGO_DB_NAME = "todos_test";
    
    @Bean
    public MongoTemplate mongoTemplate() throws IOException {
        EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();
        mongo.setBindIp(MONGO_DB_URL);
        
        MongoClient mongoClient = mongo.getObject();
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, MONGO_DB_NAME);
        
        return mongoTemplate;
    }
}
