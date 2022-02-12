package com.soon.zuul;

import com.netflix.client.config.IClientConfig;

import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

//https://stackoverflow.com/questions/52881406/com-netflix-client-clientexception-load-balancer-does-not-have-available-server
public class RibbonEurekaClientConfig
{

    private final DiscoveryClient discoveryClient;

    @Autowired
    public RibbonEurekaClientConfig(DiscoveryClient discoveryClient)
    {
        this.discoveryClient = discoveryClient;
    }


    @Bean
    public ServerList<Server> getServerList(IClientConfig config)
    {

        return new ServerList<Server>()
        {
            @Override
            public List<Server> getInitialListOfServers() {
                return this.getUpdatedListOfServers();
            }

            @Override
            public List<Server> getUpdatedListOfServers()
            {
                List<Server> serverList = new ArrayList<>();

                List<ServiceInstance> list = discoveryClient.getInstances(config.getClientName());

                for (ServiceInstance instance : list)
                {
                    serverList.add(new Server(instance.getHost(), instance.getPort()));
                }

                return serverList;
            }
        };
    }
}
