package com.inventory.stock.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class BeansServices {
    @Bean(name="getDistributedTransactions")
    @RequestScope
    TransactionsConfig getDistributedTransactions() {
        return new TransactionsConfig();
    }
}
