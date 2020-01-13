package com.example.demo;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
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
import java.util.List;

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

        // 自动嗅探整个集群的状态，把集群中其他ES节点的ip添加到本地的客户端列表中
        Settings settings = Settings.builder()
                .put("cluster.name", esClusterName)
                .put("client.transport.sniff", true)
                .build();

        // 此步骤添加 IP ，至少一个，其实一个就够了，因为添加了自动嗅探配置
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName(esHost), esPort));
        logger.info("##elasticsearch 启动成功!##");

        List<DiscoveryNode> discoveryNodes = client.connectedNodes();
        for (DiscoveryNode node : discoveryNodes) {
            logger.info("##集群节点信息：##");
            logger.info(node.getHostAddress());
        }
        return client;

    }


}