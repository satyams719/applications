package com.url.urlshortener.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZookeeperClient {

    private static final String ZOOKEEPER_CONNECTION = "localhost:2181";
    private static final String SEQUENTIAL_NODE_PATH = "/url_shortener";

    private final CuratorFramework client;

    public ZookeeperClient(){
        this.client = CuratorFrameworkFactory.newClient(ZOOKEEPER_CONNECTION, new ExponentialBackoffRetry(1000, 3));
        client.start();
        initializePath();
    }

    private void initializePath() {
        try{
            if(client.checkExists().forPath(SEQUENTIAL_NODE_PATH) == null){
                client.create().creatingParentsIfNeeded().forPath(SEQUENTIAL_NODE_PATH);
            }

        }catch (Exception e){
            throw new RuntimeException("Failed to initialize ZooKeeper path", e);
        }
    }

    public String generateUniqueId() {
        try {
            String fullPath = client.create().withMode(org.apache.zookeeper.CreateMode.PERSISTENT_SEQUENTIAL)
                    .forPath(SEQUENTIAL_NODE_PATH + "/id-");
            return fullPath.substring(fullPath.lastIndexOf('-') + 1);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate unique ID from ZooKeeper", e);
        }
    }

    public void close() {
        client.close();
    }


}
