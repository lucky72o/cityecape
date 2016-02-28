package com.cityescape.configuration;

/**
 * Definition of Spring profile names. The names are tightly linked
 * with liquibase configuration. If they are changed here they
 * need to be updated there as well.
 *
 */

public abstract class ProfileNames {

    public static final String ENV_COMMON = "env-common";

    public static final String RDBMS_H2 = "rdbms-h2";

    public static final String RDBMS_MYSQL = "rdbms-mysql";
}
