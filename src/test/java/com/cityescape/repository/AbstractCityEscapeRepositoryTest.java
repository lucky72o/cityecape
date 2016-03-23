package com.cityescape.repository;

import com.cityescape.Application;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by Slava on 23/03/2016.
 */
@WebAppConfiguration //todo find out why this is required
@SpringApplicationConfiguration(classes = {Application.class})
@Rollback(value = false)
public class AbstractCityEscapeRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
}
