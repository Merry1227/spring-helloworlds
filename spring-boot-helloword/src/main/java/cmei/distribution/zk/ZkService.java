package cmei.distribution.zk;


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * ConfigService
 * https://my.oschina.net/manmao/blog/687658
 * @author meicanhua
 * @create 2018-06-06 下午2:36
 **/
public class ZkService {

    public static void main(String args[]) {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("localhost:2180")
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .build();

        client.start();

        try {
            countDownLatch.await();
        } catch (Exception e) {
        } finally {
            countDownLatch.countDown();
        }
    }

    public static void simpleOPs(CuratorFramework client) {
        try {

            //测试连接
            List<String> children = client.getChildren().forPath("/zookeeper");

            //创建节点
            Stat stat = client.checkExists().forPath("/hello");
            if (stat == null) {
                client.create()
                        .creatingParentContainersIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .forPath("/hello", "hello-data".getBytes());
            } else {
                System.out.println(stat.getVersion());
            }

            //测试事务
            client.inTransaction().check().forPath("/hello")
                    //.and()
                    //.create().withMode(CreateMode.PERSISTENT).forPath("/hello/tz1", "data_1".getBytes())
                    .and()
                    .setData().forPath("/hello/tz1", "data_2".getBytes())
                    .and()
                    .commit();

            //异步创建
            Executor executor = Executors.newFixedThreadPool(2);
            client.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .inBackground(
                            (curatorFramework, curatorEvent) -> {
                                System.out.println(
                                        (String.format("%s, eventType:%s, resultCode:%s", Thread.currentThread().getName(),curatorEvent.getType(), curatorEvent.getResultCode()))
                                );
                            },executor)
                    .forPath("/hello/tz2");

            Stat initStat = new Stat();
            byte[] initialData = client.getData().storingStatIn(initStat).forPath("/hello/tz2");
            client.setData().withVersion(initStat.getAversion()).forPath("/hello/tz2", "data_tz2_xxx2".getBytes());

            byte[] hellotz2 = client.getData().forPath("/hello/tz2");
            System.out.println(new String(hellotz2));

            Thread.sleep(10000);
            client.setData().forPath("/hello/tz2", "data_tz2_xxx3".getBytes());

            Thread.sleep(10000);
            Stat newStat = new Stat();
            client.getData().storingStatIn(newStat).forPath("/hello/tz2");
            client.setData().forPath("/hello/tz2", "data_tz2_xxx4".getBytes());
            System.out.println(newStat.toString());

            Thread.sleep(10000);
            client.setData().forPath("/hello/tz2", "data_tz2_xxx3".getBytes());

            client.blockUntilConnected();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}