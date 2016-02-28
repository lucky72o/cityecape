package com.cityescape.configuration;

import javax.sql.DataSource;

/**
 * Contract for data source configurations.
 */
public interface DataSourceConfiguration {
    String vendorDialect();

    DataSource primaryDataSource();
}
