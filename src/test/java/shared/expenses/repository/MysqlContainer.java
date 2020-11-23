package shared.expenses.repository;

import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class MysqlContainer extends MySQLContainer<MysqlContainer> {

    private static final String IMAGE_VERSION = "mysql:latest";
    @Container
    private static MysqlContainer container;

    private MysqlContainer() {
        super(IMAGE_VERSION);
    }

    public static MysqlContainer getInstance() {
        if (container == null)
            container = new MysqlContainer()
                    .withInitScript("./src/main/resources/sql/init.sql");
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("datasources.default.url", container.getJdbcUrl());
        System.setProperty("datasources.default.username", container.getUsername());
        System.setProperty("datasources.default.password", container.getPassword());
    }


}
