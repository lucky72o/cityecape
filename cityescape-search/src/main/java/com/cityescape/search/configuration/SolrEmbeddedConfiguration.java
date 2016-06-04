package com.cityescape.search.configuration;

import com.cityescape.configuration.Profiles;
import org.apache.solr.client.solrj.SolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.server.support.EmbeddedSolrServerFactory;
import org.xml.sax.SAXException;

import javax.annotation.Resource;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by Slava on 07/05/2016.
 */
//@Configuration
//@Profile(Profiles.LOCAL_SOLR)
    //todo: CONFIGURE embedded solr for local run
public class SolrEmbeddedConfiguration {

//    @Bean
//    public SolrServer solrServer() throws IOException, SAXException, ParserConfigurationException {
//        EmbeddedSolrServerFactory factory = new EmbeddedSolrServerFactory("/Users/Slava/dev/solr-5.5.0/");
//        return factory.getSolrServer();
//    }
}
