package com.interswitchgroup.discoverpostinjectweb.service;

import com.interswitchgroup.discoverpostinjectweb.dao.TransactionDao;
import com.interswitchgroup.discoverpostinjectweb.dto.request.CreateTransactionRequest;
import com.interswitchgroup.discoverpostinjectweb.dto.request.QueryRequest;
import com.interswitchgroup.discoverpostinjectweb.dto.response.CreateTransactionResponse;
import com.interswitchgroup.discoverpostinjectweb.exception.RequestNotValidException;
import com.interswitchgroup.discoverpostinjectweb.model.Page;
import com.interswitchgroup.discoverpostinjectweb.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionDao transactionDao;

    public CreateTransactionResponse create(CreateTransactionRequest createTransactionRequest) {
        if(createTransactionRequest == null) {
            throw new RequestNotValidException("You cant insert empty value to the database");
        }
        Transaction transaction = new Transaction();
        transaction.setFilename(createTransactionRequest.getFilename());
        transaction.setInitiator(createTransactionRequest.getInitiator());
        transaction.setDateConverted(createTransactionRequest.getDateConverted());
        transaction.setTotalTransaction(createTransactionRequest.getTotalTransaction());
        transactionDao.create(transaction);
        return new CreateTransactionResponse("201", "Transaction record was saved successfully");
    }

    public Page<Transaction> findAll(QueryRequest request) {
        return transactionDao.findAll(request.getPageNum(), request.getPageSize(), request.getSearchKey());
    }

    public Page<Transaction> getAll(QueryRequest request) {
      return transactionDao.getAll(request);
    }
    
}
