package com.inventory.stock.utils;

import lombok.extern.log4j.Log4j2;
import org.jdom2.IllegalDataException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayDeque;
import java.util.Deque;

@Log4j2
public class TransactionsConfig {
        private Deque<DefaultTransactionDefinition> defaultTransactionDefinitionList;
        private Deque<DataSourceTransactionManager> dataSourceTransactionManagerList;
        private Deque<TransactionStatus> transactionStatusList;


public void createTransactionDefinition(String transactionID, String... transactionsTags) {
    defaultTransactionDefinitionList = new ArrayDeque<>();

    for(String tag : transactionsTags) {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setName(String.format("%s_%s", tag, transactionID));
        defaultTransactionDefinitionList.add(transactionDefinition);
    }
}

public void beginTransactions(DataSourceTransactionManager... transactionsmanger) throws IllegalDataException {
    if(transactionsmanger.length != defaultTransactionDefinitionList.size()) {
        throw new IllegalDataException("Error al iniciar las transacciones. La cantidad de transacciones a manejar es incorrecta");
    }

    transactionStatusList = new ArrayDeque<>();
    dataSourceTransactionManagerList = new ArrayDeque<>();

    for(DataSourceTransactionManager itemDataSource : transactionsmanger) {
        DefaultTransactionDefinition transactionDefinition = defaultTransactionDefinitionList.removeFirst();

        TransactionStatus transactionStatus = itemDataSource.getTransaction(transactionDefinition);
        transactionStatusList.push(transactionStatus);
        dataSourceTransactionManagerList.push(itemDataSource);
    }
}

public void commitTransactions() {
    if(transactionStatusList != null) {
        while (!transactionStatusList.isEmpty()) {
            TransactionStatus transactionStatus = transactionStatusList.removeFirst();
            DataSourceTransactionManager transactionManager = dataSourceTransactionManagerList.removeFirst();
            transactionManager.commit(transactionStatus);
        }
        log.info("Commit a bases de datos.");
    }

    clearTransactionsList();
}

public void rollbackTransactions() {
    if(transactionStatusList != null) {
        while (!transactionStatusList.isEmpty()) {
            TransactionStatus transactionStatus = transactionStatusList.removeFirst();
            DataSourceTransactionManager transactionManager = dataSourceTransactionManagerList.removeFirst();
            transactionManager.rollback(transactionStatus);
        }
        log.info("Rollback a bases de datos.");
    }

    clearTransactionsList();
}

private void clearTransactionsList() {
    if(dataSourceTransactionManagerList != null) {
        dataSourceTransactionManagerList.clear();
    }

    if(transactionStatusList != null) {
        transactionStatusList.clear();
    }
}
}
