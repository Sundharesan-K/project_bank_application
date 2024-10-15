package com.springboot_project.bank_application.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.Collections;

@Configuration
public class MongoConfig {

  @Bean
  public MongoClient mongoClient() {
    return MongoClients.create("mongodb://localhost:27017");
  }

  @Bean
  public MongoDatabaseFactory mongoDatabaseFactory(MongoClient mongoClient) {
    return new SimpleMongoClientDatabaseFactory(mongoClient, "bank_application");
  }

  @Bean
  public MappingMongoConverter mappingMongoConverter(MongoDatabaseFactory factory) {
    // Set up the necessary components for MappingMongoConverter
    DefaultDbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
    MongoMappingContext mappingContext = new MongoMappingContext();
    mappingContext.setAutoIndexCreation(true);  // Optional but recommended

    // Set empty custom conversions
    MongoCustomConversions conversions = new MongoCustomConversions(Collections.emptyList());
    mappingContext.setSimpleTypeHolder(conversions.getSimpleTypeHolder());

    // Create and configure the converter
    MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mappingContext);
    converter.setTypeMapper(new DefaultMongoTypeMapper(null));  // Disable _class field

    return converter;
  }

  @Bean
  public MongoTemplate mongoTemplate(MongoDatabaseFactory factory, MappingMongoConverter converter) {
    return new MongoTemplate(factory, converter);
  }
}
