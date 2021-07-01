package com.casabonita.spring.spring_boot;

import com.casabonita.spring.spring_boot.repository.*;
import com.casabonita.spring.spring_boot.entity.*;
import org.assertj.db.api.Assertions;
import org.assertj.db.type.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TestAccountRepositoryIT {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PlatformTransactionManager transactionManager;

    private TransactionTemplate transactionTemplate;

    @BeforeEach
    public void setUp() {
        transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Test
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/account_init.sql", "/db_scripts/payment_init.sql"})
    public void testGetAllAccounts(){

        List<Account> accountList = accountRepository.findAll();

        assertThat(accountList).extracting(x -> x.getId()).contains(1, 2, 3, 4, 5, 6, 7);
        assertThat(accountList).extracting(x -> x.getNumber()).contains("62.01.001", "62.01.002", "62.01.003", "62.01.004", "62.01.005", "62.01.006", "62.01.007");
        assertThat(accountList).extracting(x -> x.getAccountContract().getId()).contains(1, 2, 3, 4, 5, 6, 7);
    }

    @Test
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/account_init.sql", "/db_scripts/payment_init.sql"})
    public void testSaveAccount(){

        int testContractId = 1;
        String testNumber = "TestNumber";

        transactionTemplate.execute(transactionStatus -> {

            Contract contract = contractRepository.findById(testContractId).get();

            Account account = new Account();

            account.setNumber(testNumber);
            account.setAccountContract(contract);

            contract.setAccount(account);

            accountRepository.save(account);

            return null;
        });

        Request request = new Request(dataSource,
                "SELECT * FROM account WHERE id = 8");

        Assertions.assertThat(request)
                .hasNumberOfRows(1)
                .column("id").hasValues(8)
                .column("account_number").hasValues(testNumber)
                .column("contract_id").hasValues(testContractId);
    }

    @Test
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/account_init.sql", "/db_scripts/payment_init.sql"})
    public void testGetAccount(){

        int id = 1;
        String accountNumber = "62.01.001";
        int contractId = 1;

        // проверка через getAccount
        Account account = accountRepository.findById(id).get();

        assertThat(id).isEqualTo(account.getId());
        assertThat(accountNumber).isEqualTo(account.getNumber());
        assertThat(contractId).isEqualTo(account.getAccountContract().getId());

        // проверка без getAccount через request из БД
        Request request = new Request(dataSource,
                "SELECT * FROM account WHERE id = 1");

        Assertions.assertThat(request)
                .hasNumberOfRows(1)
                .column("id").hasValues(id)
                .column("account_number").hasValues(accountNumber)
                .column("contract_id").hasValues(contractId);
    }

    @Test
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/account_init.sql", "/db_scripts/payment_init.sql"})
    public void testGetAccountByContractId(){

        int id = 1;
        String accountNumber = "62.01.001";
        int contractId = 1;

        // проверка через getAccount
        Account account = accountRepository.findAccountByAccountContract_Id(contractId);

        assertThat(id).isEqualTo(account.getId());
        assertThat(accountNumber).isEqualTo(account.getNumber());
        assertThat(contractId).isEqualTo(account.getAccountContract().getId());

        // проверка без getAccount через request из БД
        Request request = new Request(dataSource,
                "SELECT * FROM account WHERE contract_id = 1");

        Assertions.assertThat(request)
                .hasNumberOfRows(1)
                .column("id").hasValues(id)
                .column("account_number").hasValues(accountNumber)
                .column("contract_id").hasValues(contractId);
    }

    @Test
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/account_init.sql", "/db_scripts/payment_init.sql"})
    public void testGetAccountByNumber(){

        int id = 1;
        String accountNumber = "62.01.001";
        int contractId = 1;

        // проверка через getAccount
        Account account = accountRepository.findAccountByNumber(accountNumber);

        assertThat(id).isEqualTo(account.getId());
        assertThat(accountNumber).isEqualTo(account.getNumber());
        assertThat(contractId).isEqualTo(account.getAccountContract().getId());

        // проверка без getAccount через request из БД
        Request request = new Request(dataSource,
                "SELECT * FROM account WHERE account_number = '62.01.001'");

        Assertions.assertThat(request)
                .hasNumberOfRows(1)
                .column("id").hasValues(id)
                .column("account_number").hasValues(accountNumber)
                .column("contract_id").hasValues(contractId);
    }

    @Test
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/account_init.sql"})
    public void testDeleteAccountById(){

        int id = 1;

        transactionTemplate.execute(transactionStatus -> {
            accountRepository.deleteBy(id);
            return null;
        });
        // проверка через request удаления строки
        Request requestOne = new Request(dataSource,
                "SELECT * FROM account WHERE id = 1");

        Assertions.assertThat(requestOne)
                .isEmpty();

        // проверка через request всех оставшихся после удаления строк таблицы
        Request requestAll = new Request(dataSource,
                "SELECT * FROM account");

        Assertions.assertThat(requestAll)
                .column("id").hasValues(2, 3, 4, 5, 6, 7)
                .column("account_number").hasValues("62.01.002", "62.01.003", "62.01.004", "62.01.005", "62.01.006", "62.01.007")
                .column("contract_id").hasValues(2, 3, 4, 5, 6, 7);

        //проверка через getAllAccounts
        List<Account> accountList = accountRepository.findAll();

        assertThat(accountList).extracting(x -> x.getId()).contains(2, 3, 4, 5, 6, 7);
        assertThat(accountList).extracting(x -> x.getNumber()).contains("62.01.002", "62.01.003", "62.01.004", "62.01.005", "62.01.006", "62.01.007");
        assertThat(accountList).extracting(x -> x.getAccountContract().getId()).contains(2, 3, 4, 5, 6, 7);
    }

    @Test
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/account_init.sql", "/db_scripts/payment_init.sql"})
    public void testUpdate(){

        String accountNumber = "TestNumber";
        int id = 1;

        transactionTemplate.execute(transactionStatus -> {
            Account accountExpected = accountRepository.findById(id).get();

            accountExpected.setNumber(accountNumber);

            accountRepository.save(accountExpected);

            return null;
        });

        Request request = new Request(dataSource,
                "SELECT * FROM account WHERE id = 1");

        Assertions.assertThat(request)
                .hasNumberOfRows(1)
                .column("id").hasValues(id)
                .column("account_number").hasValues(accountNumber);
    }
}
