package com.example.demo;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/** es配置
 * @author shaos
 * @date 2019/11/11 15:45
 */
@Configuration
public class EsConfiguration {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${elasticsearch.host}")
    private String esHost;

    @Value("${elasticsearch.port}")
    private int esPort;

    @Value("${elasticsearch.clusterName}")
    private String esClusterName;
    
    @Bean
    public TransportClient client() throws UnknownHostException {

        Settings settings = Settings.builder()
                .put("cluster.name", esClusterName).build();

        TransportClient client = new PreBuiltTransportClient(settings)
//                .addTransportAddress(new TransportAddress(InetAddress.getByName("host1"), 9300))
                .addTransportAddress(new TransportAddress(InetAddress.getByName(esHost), esPort));
        logger.info("======= elasticsearch 启动成功！=======");
        return client;

    }


}