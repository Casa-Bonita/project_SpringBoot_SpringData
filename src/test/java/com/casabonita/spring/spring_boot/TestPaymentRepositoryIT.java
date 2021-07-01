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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TestPaymentRepositoryIT {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PlatformTransactionManager transactionManager;

    private TransactionTemplate transactionTemplate;

    @BeforeEach
    public void setUp(){
        transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Test
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/account_init.sql", "/db_scripts/payment_init.sql"})
    public void testGetAllPayments() throws ParseException{

        List<Payment> paymentList = paymentRepository.findAll();

        String d1 = "2021-03-01";
        String d2 = "2021-02-01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        assertThat(paymentList).extracting(x -> x.getId()).contains(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
        assertThat(paymentList).extracting(x -> x.getAccount().getId()).contains(1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7);
        assertThat(paymentList).extracting(x -> x.getAmount()).contains(1000, 1100, 2200, 2100, 1500, 1700, 3000, 2900, 2400, 2500, 3500, 3400, 2000, 1900);
        assertThat(paymentList).extracting(x -> sdf.parse(x.getDate().toString())).contains( sdf.parse(d1), sdf.parse(d2), sdf.parse(d1), sdf.parse(d2), sdf.parse(d1), sdf.parse(d2), sdf.parse(d1), sdf.parse(d2), sdf.parse(d1), sdf.parse(d2), sdf.parse(d1), sdf.parse(d2), sdf.parse(d1), sdf.parse(d2));
        assertThat(paymentList).extracting(x -> x.getPurpose()).contains("Oplata za elektroenergiu za fevral 2021", "Oplata za elektroenergiu za yanvar 2021", "Oplata za elektroenergiu za fevral 2021", "Oplata za elektroenergiu za yanvar 2021", "Oplata za elektroenergiu za fevral 2021", "Oplata za elektroenergiu za yanvar 2021", "Oplata za elektroenergiu za fevral 2021", "Oplata za elektroenergiu za yanvar 2021", "Oplata za elektroenergiu za fevral 2021", "Oplata za elektroenergiu za yanvar 2021", "Oplata za elektroenergiu za fevral 2021", "Oplata za elektroenergiu za yanvar 2021", "Oplata za elektroenergiu za fevral 2021", "Oplata za elektroenergiu za yanvar 2021");
    }

    @Test
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/account_init.sql"})
    public void testSavePayment() throws ParseException {

        String d = "2021-01-01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        int id = 1;
        int testAmount = 100;
        Date testDate = sdf.parse(d);
        String testPurpose = "TestPurpose";

        transactionTemplate.execute(transactionStatus -> {
            Account account = accountRepository.findById(id).get();

            Payment payment = new Payment();

            payment.setAccount(account);
            payment.setAmount(testAmount);
            payment.setDate(testDate);
            payment.setPurpose(testPurpose);

            paymentRepository.save(payment);

            return null;
        });

        Request request = new Request(dataSource,
                "SELECT * FROM account_data");

        Assertions.assertThat(request)
                .hasNumberOfRows(1)
                .column("id").hasValues(id)
                .column("account_id").hasValues(id)
                .column("payment").hasValues(testAmount)
                .column("payment_date").hasValues(d)
                .column("payment_purpose").hasValues(testPurpose);
    }

    @Test
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/account_init.sql", "/db_scripts/payment_init.sql"})
    public void testGetPayment() throws ParseException{

        String d = "2021-03-01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        int id = 1;
        int accountId = 1;
        int amount = 1000;
        Date date = sdf.parse(d);
        String purpose = "Oplata za elektroenergiu za fevral 2021";

        // проверка через getPayment
        Payment payment = paymentRepository.findById(id).get();

        assertThat(id).isEqualTo(payment.getId());
        assertThat(accountId).isEqualTo(payment.getAccount().getId());
        assertThat(amount).isEqualTo(payment.getAmount());
        assertThat(date).isEqualTo(payment.getDate());
        assertThat(purpose).isEqualTo(payment.getPurpose());

        // тест без использования getPayment
        Request request = new Request(dataSource,
                "SELECT * FROM account_data WHERE id = 1");

        Assertions.assertThat(request)
                .hasNumberOfRows(1)
                .column("id").hasValues(id)
                .column("account_id").hasValues(accountId)
                .column("payment").hasValues(amount)
                .column("payment_date").hasValues(d)
                .column("payment_purpose").hasValues(purpose);
    }

    @Test
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/account_init.sql", "/db_scripts/payment_init.sql"})
    public void testDeletePaymentById() throws ParseException{

        int id = 1;

        transactionTemplate.execute(transactionStatus -> {

            paymentRepository.deleteBy(id);
            return null;
        });

        List<Payment> paymentList = paymentRepository.findAll();

        assertThat(paymentList)
                .isNotEmpty()
                .hasSize(13);

        String d1 = "2021-03-01";
        String d2 = "2021-02-01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        assertThat(paymentList).extracting(x -> x.getId()).contains(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
        assertThat(paymentList).extracting(x -> x.getAccount().getId()).contains(1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7);
        assertThat(paymentList).extracting(x -> x.getAmount()).contains(1100, 2200, 2100, 1500, 1700, 3000, 2900, 2400, 2500, 3500, 3400, 2000, 1900);
        assertThat(paymentList).extracting(x -> sdf.parse(x.getDate().toString())).contains(sdf.parse(d2), sdf.parse(d1), sdf.parse(d2), sdf.parse(d1), sdf.parse(d2), sdf.parse(d1), sdf.parse(d2), sdf.parse(d1), sdf.parse(d2), sdf.parse(d1), sdf.parse(d2), sdf.parse(d1), sdf.parse(d2));
        assertThat(paymentList).extracting(x -> x.getPurpose()).contains("Oplata za elektroenergiu za yanvar 2021", "Oplata za elektroenergiu za fevral 2021", "Oplata za elektroenergiu za yanvar 2021", "Oplata za elektroenergiu za fevral 2021", "Oplata za elektroenergiu za yanvar 2021", "Oplata za elektroenergiu za fevral 2021", "Oplata za elektroenergiu za yanvar 2021", "Oplata za elektroenergiu za fevral 2021", "Oplata za elektroenergiu za yanvar 2021", "Oplata za elektroenergiu za fevral 2021", "Oplata za elektroenergiu za yanvar 2021", "Oplata za elektroenergiu za fevral 2021", "Oplata za elektroenergiu za yanvar 2021");
    }

    @Test
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/account_init.sql", "/db_scripts/payment_init.sql"})
    public void testDeletePaymentByAccountId() throws ParseException{

        int id = 1;

        transactionTemplate.execute(transactionStatus ->{

            paymentRepository.deletePaymentByAccountId(id);
            return null;
        });

        // проверка через request удаления строки
        Request requestOne = new Request(dataSource,
                "SELECT * FROM account_data WHERE account_id = 1");

        Assertions.assertThat(requestOne)
                .isEmpty();

        //проверка через getAllPayments
        List<Payment> paymentList = paymentRepository.findAll();

        assertThat(paymentList)
                .isNotEmpty()
                .hasSize(12);

        String d1 = "2021-03-01";
        String d2 = "2021-02-01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        assertThat(paymentList).extracting(x -> x.getId()).contains(3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
        assertThat(paymentList).extracting(x -> x.getAccount().getId()).contains(2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7);
        assertThat(paymentList).extracting(x -> x.getAmount()).contains(2200, 2100, 1500, 1700, 3000, 2900, 2400, 2500, 3500, 3400, 2000, 1900);
        assertThat(paymentList).extracting(x -> sdf.parse(x.getDate().toString())).contains(sdf.parse(d1), sdf.parse(d2), sdf.parse(d1), sdf.parse(d2), sdf.parse(d1), sdf.parse(d2), sdf.parse(d1), sdf.parse(d2), sdf.parse(d1), sdf.parse(d2), sdf.parse(d1), sdf.parse(d2));
        assertThat(paymentList).extracting(x -> x.getPurpose()).contains("Oplata za elektroenergiu za fevral 2021", "Oplata za elektroenergiu za yanvar 2021", "Oplata za elektroenergiu za fevral 2021", "Oplata za elektroenergiu za yanvar 2021", "Oplata za elektroenergiu za fevral 2021", "Oplata za elektroenergiu za yanvar 2021", "Oplata za elektroenergiu za fevral 2021", "Oplata za elektroenergiu za yanvar 2021", "Oplata za elektroenergiu za fevral 2021", "Oplata za elektroenergiu za yanvar 2021", "Oplata za elektroenergiu za fevral 2021", "Oplata za elektroenergiu za yanvar 2021");
    }

    @Test
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/account_init.sql", "/db_scripts/payment_init.sql"})
    public void testUpdate(){

        String purpose = "Test";
        int id = 1;

        transactionTemplate.execute(transactionStatus ->{

            Payment paymentExpected = paymentRepository.findById(id).get();

            paymentExpected.setPurpose(purpose);

            paymentRepository.save(paymentExpected);

            return null;
        });

        Request request = new Request(dataSource,
                "SELECT * FROM account_data WHERE id = 1");

        Assertions.assertThat(request)
                .hasNumberOfRows(1)
                .column("id").hasValues(id)
                .column("payment_purpose").hasValues(purpose);
    }
}
