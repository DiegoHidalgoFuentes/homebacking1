package com.minhub.homebacking;
import com.minhub.homebacking.models.*;
import com.minhub.homebacking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
public class HomebackingApplication {

	public static void main(String[] args) {SpringApplication.run(HomebackingApplication.class, args);}
	@Autowired
	private PasswordEncoder passwordEncoder;


		@Bean
		public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository,
										  TransactionRepository transactionRepository, LoanRepository loanRepository,
										  ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
			return (args) -> {
				// save a couple of customers
				//clientRepository.save(new Client("Jack", "Bauer","asdasd@123"));
				//clientRepository.save(new Client("Pedro","Gutierrez","123@asdasd"));

				Client client1=new Client("Jack", "Bauer","123@123",passwordEncoder.encode("123456"));
				Client client2=new Client("Pedro","Gutierrez","1234@123",passwordEncoder.encode("123456"));
				Client client3=new Client("Pedro","Fernandez","12345@123",passwordEncoder.encode("123456"));
				Client client4=new Client("Admin"," ","1@123",passwordEncoder.encode("123456"));
				clientRepository.save(client1);
				clientRepository.save(client2);
				clientRepository.save(client3);

				Account account1= new Account("VIN001", LocalDateTime.now(),5000,client1);
				Account account2= new Account("VIN002",LocalDateTime.now().plusDays(1),7500,client1);
				Account account3= new Account("VIN003", LocalDateTime.now(),8000,client2);
				Account account4= new Account("VIN004",LocalDateTime.now(),9500,client2);

				accountRepository.save(account1);
				accountRepository.save(account2);
				accountRepository.save(account3);
				accountRepository.save(account4);

				Transaction transaction1 = new Transaction(TransactionType.CREDIT, 1000, "Compra de Pan panaderia XXX", LocalDateTime.now(), account1 );
				Transaction transaction2 = new Transaction(TransactionType.DEBIT, -55000, "Compra de ropa tienda XXX", LocalDateTime.now(), account1 );
				Transaction transaction3 = new Transaction(TransactionType.CREDIT, 7500, "Compra de dulces panaderia XXX", LocalDateTime.now(), account3 );
				Transaction transaction4 = new Transaction(TransactionType.DEBIT, -1000, "Compra Supermercado XXX", LocalDateTime.now(), account3 );
				Transaction transaction5 = new Transaction(TransactionType.CREDIT, 1000, "Pago  Energia Electrica xxx", LocalDateTime.now(), account3 );
				Transaction transaction6 = new Transaction(TransactionType.DEBIT, -1000, "Compra Supermercado XXX", LocalDateTime.now(), account1 );
				Transaction transaction7 = new Transaction(TransactionType.DEBIT, -1000, "Pago Agua xxxx", LocalDateTime.now(), account3 );
				Transaction transaction8 = new Transaction(TransactionType.CREDIT, 1000, "Compra Supermercado XXX", LocalDateTime.now(), account3 );
				Transaction transaction9 = new Transaction(TransactionType.DEBIT, -1000, "Pago gas xxx", LocalDateTime.now(), account3 );
				Transaction transaction10 = new Transaction(TransactionType.DEBIT, -1000, "Compra Supermercado XXX", LocalDateTime.now(), account1 );

				transactionRepository.save(transaction1);
				transactionRepository.save(transaction2);
				transactionRepository.save(transaction3);
				transactionRepository.save(transaction4);
				transactionRepository.save(transaction5);
				transactionRepository.save(transaction6);
				transactionRepository.save(transaction7);
				transactionRepository.save(transaction8);
				transactionRepository.save(transaction9);
				transactionRepository.save(transaction10);

				Loan loan1= new Loan("Hipotecario",500000, Arrays.asList(12,24,36,48,60));
				loanRepository.save(loan1);
				Loan loan2= new Loan("Personal",100000,Arrays.asList(6,12,24));
				Loan loan3= new Loan("Automotriz",300000,Arrays.asList(6,12,24,36));
				loanRepository.save(loan2);
				loanRepository.save(loan3);

				ClientLoan clientLoan1=new ClientLoan(400000,60,client1,loan1);
				ClientLoan clientLoan2=new ClientLoan(50000,12,client1,loan2);
				ClientLoan clientLoan3=new ClientLoan(100000,24,client2,loan2);
				ClientLoan clientLoan4=new ClientLoan(200000,36,client2,loan3);
				clientLoanRepository.save(clientLoan1);
				clientLoanRepository.save(clientLoan2);
				clientLoanRepository.save(clientLoan3);
				clientLoanRepository.save(clientLoan4);

				Card card1= new Card(client1.getFirstName()+" "+client1.getLastName(),CardType.DEBIT,CardColor.GOLD,"5497-2376-1653-5586",224,LocalDateTime.now(),LocalDateTime.now().plusYears(5),client1);
				Card card2= new Card(client1.getFirstName()+" "+client1.getLastName(),CardType.CREDIT,CardColor.TITANIUM,"5389-2692-5194-9534",612,LocalDateTime.now(),LocalDateTime.now().plusYears(5),client1);
				Card card3= new Card(client2.getFirstName()+" "+client2.getLastName(),CardType.CREDIT,CardColor.SILVER,"4936-4329-7515-0123",194,LocalDateTime.now(),LocalDateTime.now().plusYears(5),client2);
				cardRepository.save(card1);
				cardRepository.save(card2);
				cardRepository.save(card3);


			};
		}

	}


