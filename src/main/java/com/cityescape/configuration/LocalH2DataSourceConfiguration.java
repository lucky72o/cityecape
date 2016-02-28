package com.cityescape.configuration;

import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;

/**
 * H2 data source configuration for non-container managed environments.
 *
 */
@EnableJpaRepositories(basePackages = {"com.cityescape.repository"})
@Configuration
@Profile({ProfileNames.RDBMS_H2})
public class LocalH2DataSourceConfiguration implements DataSourceConfiguration, DisposableBean {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    JdbcConnectionPool dataSource;

    @Override
    public String vendorDialect() {
        return "org.hibernate.dialect.H2Dialect";
    }

    @Override
    @Bean
    public DataSource primaryDataSource() {
        logger.info("Creating jdbc:h2:file:~/cityescape");
        Server server = new Server();
        dataSource = JdbcConnectionPool.create("jdbc:h2:file:~/cityescape;MODE=Mysql;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=TRUE", "sa", "sa");
        return new TransactionAwareDataSourceProxy(dataSource);
    }

    @Override
    public void destroy() throws Exception {
        logger.debug("Closing the connection....");
        if (dataSource != null &&  dataSource.getConnection() != null) {
            dataSource.getConnection().close();
        }
    }
}
