package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static Connection conn = null;
    private  static SessionFactory factory = null;

    public Util() {
    }

    public static SessionFactory getConnectionHibernate() {
        try {
            Configuration configuration = new Configuration()
                    .setProperty("hibernate.connection.driver_class", getProps().getProperty("db.driver"))
                    .setProperty("hibernate.connection.url", getProps().getProperty("db.url"))
                    .setProperty("hibernate.connection.username", getProps().getProperty("db.username"))
                    .setProperty("hibernate.connection.password", getProps().getProperty("db.password"))
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                    .addAnnotatedClass(User.class);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            factory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return factory;
    }

    public static void FactoryClose() {
        if (factory != null) {
            factory.close();
        }
    }
    public static Connection getConnectionJDBS() {
        try {
            if (null == conn || conn.isClosed()) {
                Properties props = getProps();
                conn = DriverManager
                        .getConnection(props.getProperty("db.url"), props.getProperty("db.username"),
                                props.getProperty("db.password"));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return conn;
    }

    private static Properties getProps() throws IOException {
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get(Util.class.getResource("/dbase.properties").toURI()))) {
            props.load(in);
            return props;
        } catch (IOException | URISyntaxException e) {
            throw new IOException("Database config file not found", e);
        }
    }
}
