package com.framework.runtime.application.posp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.framework.runtime.application.net.TransportMessage;
import com.framework.runtime.application.net.trans.Transaction;
import com.framework.runtime.application.net.trans.TransactionTransport;

public class PospTransactionTransport extends TransactionTransport {
	
	@Autowired
	private List<Transaction> transactions;

	public PospTransactionTransport(String name) {
		super(name);
	}
	
	public PospTransactionTransport() {
		
	}

	@Override
	protected Transaction matchTransaction(TransportMessage message) {
		try {
			for(Transaction transaction:transactions) {
				if(transaction.match(message)) {
					return transaction;
				}
			}
			
		} catch (Exception e) {
			
		}
		
		return null;
	}

}
