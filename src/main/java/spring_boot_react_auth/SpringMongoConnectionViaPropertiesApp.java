package spring_boot_react_auth;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableConfigurationProperties(SpringMongoConnectionViaPropertiesApp.MongoProperties.class)
@EnableMongoRepositories(basePackages = "spring_boot_react_auth") 
public class SpringMongoConnectionViaPropertiesApp {

    private static final Logger log = LoggerFactory.getLogger(SpringMongoConnectionViaPropertiesApp.class);

    @Autowired
    private MongoProperties mongoProperties;

    /**
     * Creates a MongoClient using the configured connection string.
     * If username/password are provided, they are included in the URI.
     */
    @Bean
    public MongoClient mongoClient() {
        String uri = buildConnectionString();
        log.info("Connecting to MongoDB with URI: {}", uri.replaceAll(":[^:@/]+@", ":****@"));
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(uri))
                .build();
        return MongoClients.create(settings);
    }

    /**
     * Creates the MongoDatabaseFactory using the MongoClient and database name.
     */
    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory(MongoClient mongoClient) {
        return new SimpleMongoClientDatabaseFactory(mongoClient, mongoProperties.getDatabase());
    }

    /**
     * Creates the MongoTemplate, the central Spring Data MongoDB helper.
     */
    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory factory) {
        return new MongoTemplate(factory);
    }

    /**
     * Builds the MongoDB connection string from properties.
     * Supports authentication if username/password are present.
     */
    private String buildConnectionString() {
        String host = mongoProperties.getHost() != null ? mongoProperties.getHost() : "localhost";
        int port = mongoProperties.getPort() != 0 ? mongoProperties.getPort() : 27017;
        String database = mongoProperties.getDatabase() != null ? mongoProperties.getDatabase() : "test";

        StringBuilder uri = new StringBuilder("mongodb://");

        // Add credentials if present
        if (mongoProperties.getUsername() != null && !mongoProperties.getUsername().isEmpty()) {
            uri.append(mongoProperties.getUsername())
               .append(":")
               .append(mongoProperties.getPassword() != null ? mongoProperties.getPassword() : "")
               .append("@");
        }

        uri.append(host).append(":").append(port).append("/").append(database);

        // Append authentication database if provided and credentials exist
        if (mongoProperties.getAuthenticationDatabase() != null
                && !mongoProperties.getAuthenticationDatabase().isEmpty()
                && mongoProperties.getUsername() != null) {
            uri.append("?authSource=").append(mongoProperties.getAuthenticationDatabase());
        }

        return uri.toString();
    }

    /**
     * Properties class for MongoDB configuration.
     * Maps spring.data.mongodb.* properties.
     */
    @ConfigurationProperties(prefix = "spring.data.mongodb")
    public static class MongoProperties {
        private String host;
        private Integer port;
        private String database;
        private String username;
        private String password;
        private String authenticationDatabase;

        // getters and setters
        public String getHost() { return host; }
        public void setHost(String host) { this.host = host; }
        public Integer getPort() { return port; }
        public void setPort(Integer port) { this.port = port; }
        public String getDatabase() { return database; }
        public void setDatabase(String database) { this.database = database; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getAuthenticationDatabase() { return authenticationDatabase; }
        public void setAuthenticationDatabase(String authenticationDatabase) { this.authenticationDatabase = authenticationDatabase; }
    }
}
