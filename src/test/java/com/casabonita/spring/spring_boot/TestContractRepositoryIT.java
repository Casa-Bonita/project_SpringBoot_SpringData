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
public class TestContractRepositoryIT {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private RenterRepository renterRepository;

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
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/meter_init.sql",
            "/db_scripts/reading_init.sql", "/db_scripts/account_init.sql", "/db_scripts/payment_init.sql"})
    public void testGetAllContracts() throws ParseException{

        List<Contract> contractList = contractRepository.findAll();

        String d1 = "2019-01-01";
        String d2 = "2019-01-01";
        String d3 = "2021-12-31";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        assertThat(contractList).extracting(x -> x.getId()).contains(1, 2, 3, 4, 5, 6, 7);
        assertThat(contractList).extracting(x -> x.getNumber()).contains("100R", "101R", "102R", "103L", "104O", "105O", "106M");
        assertThat(contractList).extracting(x -> sdf.parse(x.getDate().toString())).contains(sdf.parse(d1), sdf.parse(d1), sdf.parse(d1), sdf.parse(d1), sdf.parse(d1), sdf.parse(d1), sdf.parse(d1));
        assertThat(contractList).extracting(x -> x.getFare()).contains(1000, 2000, 3000, 500, 1500, 2500, 3500);
        assertThat(contractList).extracting(x -> sdf.parse(x.getStartDate().toString())).contains(sdf.parse(d2), sdf.parse(d2), sdf.parse(d2), sdf.parse(d2), sdf.parse(d2), sdf.parse(d2), sdf.parse(d2));
        assertThat(contractList).extracting(x -> sdf.parse(x.getFinishDate().toString())).contains(sdf.parse(d3), sdf.parse(d3), sdf.parse(d3), sdf.parse(d3), sdf.parse(d3), sdf.parse(d3), sdf.parse(d3));
        assertThat(contractList).extracting(x -> x.getPaymentDay()).contains(5, 5, 5, 4, 3, 3, 2);
        assertThat(contractList).extracting(x -> x.getContractPlace().getId()).contains(1, 2, 3, 4, 5, 6, 7);
        assertThat(contractList).extracting(x -> x.getRenter().getId()).contains(1, 1, 1, 2, 3, 3, 4);
    }

    @Test
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/meter_init.sql",
            "/db_scripts/reading_init.sql", "/db_scripts/account_init.sql", "/db_scripts/payment_init.sql"})
    public void testSaveContract() throws ParseException{

        int id = 1;

        String d1 = "2021-01-01";
        String d2 = "2021-01-15";
        String d3 = "2021-12-31";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String testNumber = "TestNumber";
        Date testContractDate = sdf.parse(d1);
        int testFare = 100;
        Date testStartDate = sdf.parse(d2);
        Date testFinishDate = sdf.parse(d3);
        int testPaymentDay = 25;

        int placeId = 1;
        int renterId = 1;

        transactionTemplate.execute(transactionStatus -> {

            Place place = placeRepository.findById(placeId).get();
            Renter renter = renterRepository.findById(renterId).get();
            Account account = accountRepository.findById(id).get();

            Contract contract = new Contract();

            contract.setNumber(testNumber);
            contract.setDate(testContractDate);
            contract.setFare(testFare);
            contract.setStartDate(testStartDate);
            contract.setFinishDate(testFinishDate);
            contract.setPaymentDay(testPaymentDay);
            contract.setContractPlace(place);
            contract.setRenter(renter);
            contract.setAccount(account);

            contractRepository.save(contract);
            return null;
        });

        Request request = new Request(dataSource,
                "SELECT * FROM contract WHERE id = 8");

        Assertions.assertThat(request)
                .hasNumberOfRows(1)
                .column("id").hasValues(8)
                .column("number").hasValues(testNumber)
                .column("contract_date").hasValues(d1)
                .column("fare").hasValues(testFare)
                .column("start_date").hasValues(d2)
                .column("finish_date").hasValues(d3)
                .column("payment_day").hasValues(testPaymentDay)
                .column("place_id").hasValues(placeId)
                .column("renter_id").hasValues(renterId);
    }

    @Test
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/meter_init.sql",
            "/db_scripts/reading_init.sql", "/db_scripts/account_init.sql", "/db_scripts/payment_init.sql"})
    public void testGetContract() throws ParseException{

        String d1 = "2019-01-01";
        String d2 = "2019-01-01";
        String d3 = "2021-12-31";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        int id = 1;
        String number = "100R";
        Date contractDate = sdf.parse(d1);
        int fare = 1000;
        Date startDate = sdf.parse(d2);
        Date finishDate = sdf.parse(d3);
        int paymentDay = 5;
        int contractPlace = 1;
        int renter = 1;

        // проверка через getContract
        Contract contract = contractRepository.findById(id).get();

        assertThat(id).isEqualTo(contract.getId());
        assertThat(number).isEqualTo(contract.getNumber());
        assertThat(contractDate).isEqualTo(contract.getDate());
        assertThat(fare).isEqualTo(contract.getFare());
        assertThat(startDate).isEqualTo(contract.getStartDate());
        assertThat(finishDate).isEqualTo(contract.getFinishDate());
        assertThat(paymentDay).isEqualTo(contract.getPaymentDay());
        assertThat(contractPlace).isEqualTo(contract.getContractPlace().getId());
        assertThat(renter).isEqualTo(contract.getRenter().getId());

        // проверка без getContract через request из БД
        Request request = new Request(dataSource,
                "SELECT * FROM contract WHERE id = 1");

        Assertions.assertThat(request)
                .hasNumberOfRows(1)
                .column("id").hasValues(id)
                .column("number").hasValues(number)
                .column("contract_date").hasValues(d1)
                .column("fare").hasValues(fare)
                .column("start_date").hasValues(d2)
                .column("finish_date").hasValues(d3)
                .column("payment_day").hasValues(paymentDay)
                .column("place_id").hasValues(contractPlace)
                .column("renter_id").hasValues(renter);
    }

    @Test
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/meter_init.sql",
            "/db_scripts/reading_init.sql", "/db_scripts/account_init.sql", "/db_scripts/payment_init.sql"})
    public void testGetContractByPlaceId() throws ParseException{

        String d1 = "2019-01-01";
        String d2 = "2019-01-01";
        String d3 = "2021-12-31";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        int id = 1;
        String number = "100R";
        Date contractDate = sdf.parse(d1);
        int fare = 1000;
        Date startDate = sdf.parse(d2);
        Date finishDate = sdf.parse(d3);
        int paymentDay = 5;
        int contractPlace = 1;
        int renter = 1;

        // проверка через getContract
        Contract contract = contractRepository.findContractByContractPlaceId(contractPlace);

        assertThat(id).isEqualTo(contract.getId());
        assertThat(number).isEqualTo(contract.getNumber());
        assertThat(contractDate).isEqualTo(contract.getDate());
        assertThat(fare).isEqualTo(contract.getFare());
        assertThat(startDate).isEqualTo(contract.getStartDate());
        assertThat(finishDate).isEqualTo(contract.getFinishDate());
        assertThat(paymentDay).isEqualTo(contract.getPaymentDay());
        assertThat(contractPlace).isEqualTo(contract.getContractPlace().getId());
        assertThat(renter).isEqualTo(contract.getRenter().getId());

        // проверка без getContract через request из БД
        Request request = new Request(dataSource,
                "SELECT * FROM contract WHERE place_id = 1");

        Assertions.assertThat(request)
                .hasNumberOfRows(1)
                .column("id").hasValues(id)
                .column("number").hasValues(number)
                .column("contract_date").hasValues(d1)
                .column("fare").hasValues(fare)
                .column("start_date").hasValues(d2)
                .column("finish_date").hasValues(d3)
                .column("payment_day").hasValues(paymentDay)
                .column("place_id").hasValues(contractPlace)
                .column("renter_id").hasValues(renter);
    }

    @Test
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/meter_init.sql",
            "/db_scripts/reading_init.sql", "/db_scripts/account_init.sql", "/db_scripts/payment_init.sql"})
    public void testGetContractByRenterId() throws ParseException{

        String d1 = "2019-01-01";
        String d2 = "2019-01-01";
        String d3 = "2021-12-31";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        int id = 7;
        String number = "106M";
        Date contractDate = sdf.parse(d1);
        int fare = 3500;
        Date startDate = sdf.parse(d2);
        Date finishDate = sdf.parse(d3);
        int paymentDay = 2;
        int contractPlace = 7;
        int renter = 4;

        // проверка через getContract
        List<Contract> contractList = contractRepository.findContractByRenterId(renter);

        assertThat(id).isEqualTo(contractList.get(0).getId());
        assertThat(number).isEqualTo(contractList.get(0).getNumber());
        assertThat(contractDate).isEqualTo(contractList.get(0).getDate());
        assertThat(fare).isEqualTo(contractList.get(0).getFare());
        assertThat(startDate).isEqualTo(contractList.get(0).getStartDate());
        assertThat(finishDate).isEqualTo(contractList.get(0).getFinishDate());
        assertThat(paymentDay).isEqualTo(contractList.get(0).getPaymentDay());
        assertThat(contractPlace).isEqualTo(contractList.get(0).getContractPlace().getId());
        assertThat(renter).isEqualTo(contractList.get(0).getRenter().getId());

        // проверка без getContract через request из БД
        Request request = new Request(dataSource,
                "SELECT * FROM contract WHERE renter_id = 4");

        Assertions.assertThat(request)
                .hasNumberOfRows(1)
                .column("id").hasValues(id)
                .column("number").hasValues(number)
                .column("contract_date").hasValues(d1)
                .column("fare").hasValues(fare)
                .column("start_date").hasValues(d2)
                .column("finish_date").hasValues(d3)
                .column("payment_day").hasValues(paymentDay)
                .column("place_id").hasValues(contractPlace)
                .column("renter_id").hasValues(renter);
    }

    @Test
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/meter_init.sql",
            "/db_scripts/reading_init.sql", "/db_scripts/account_init.sql", "/db_scripts/payment_init.sql"})
    public void testGetContractByNumber() throws ParseException{

        String d1 = "2019-01-01";
        String d2 = "2019-01-01";
        String d3 = "2021-12-31";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        int id = 1;
        String number = "100R";
        Date contractDate = sdf.parse(d1);
        int fare = 1000;
        Date startDate = sdf.parse(d2);
        Date finishDate = sdf.parse(d3);
        int paymentDay = 5;
        int contractPlace = 1;
        int renter = 1;

        // проверка через getContract
        Contract contract = contractRepository.findContractByNumber(number);

        assertThat(id).isEqualTo(contract.getId());
        assertThat(number).isEqualTo(contract.getNumber());
        assertThat(contractDate).isEqualTo(contract.getDate());
        assertThat(fare).isEqualTo(contract.getFare());
        assertThat(startDate).isEqualTo(contract.getStartDate());
        assertThat(finishDate).isEqualTo(contract.getFinishDate());
        assertThat(paymentDay).isEqualTo(contract.getPaymentDay());
        assertThat(contractPlace).isEqualTo(contract.getContractPlace().getId());
        assertThat(renter).isEqualTo(contract.getRenter().getId());

        // проверка без getContract через request из БД
        Request request = new Request(dataSource,
                "SELECT * FROM contract WHERE number = '100R'");

        Assertions.assertThat(request)
                .hasNumberOfRows(1)
                .column("id").hasValues(id)
                .column("number").hasValues(number)
                .column("contract_date").hasValues(d1)
                .column("fare").hasValues(fare)
                .column("start_date").hasValues(d2)
                .column("finish_date").hasValues(d3)
                .column("payment_day").hasValues(paymentDay)
                .column("place_id").hasValues(contractPlace)
                .column("renter_id").hasValues(renter);
    }

    @Test
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/meter_init.sql",
            "/db_scripts/reading_init.sql"})
    public void testDeleteContractById() throws ParseException{

        String d1 = "2019-01-01";
        String d2 = "2019-01-01";
        String d3 = "2021-12-31";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        int id = 1;

        transactionTemplate.execute(transactionStatus -> {
            contractRepository.deleteBy(id);
            return null;
        });

        // проверка через request удаления строки
        Request requestOne = new Request(dataSource,
                "SELECT * FROM contract WHERE id = 1");

        Assertions.assertThat(requestOne)
                .isEmpty();

        // проверка через request всех оставшихся после удаления строк таблицы
        Request requestAll = new Request(dataSource,
                "SELECT * FROM contract");

        Assertions.assertThat(requestAll)
                .column("id").hasValues(2, 3, 4, 5, 6, 7)
                .column("number").hasValues("101R", "102R", "103L", "104O", "105O", "106M")
                .column("contract_date").hasValues(d1, d1, d1, d1, d1, d1)
                .column("fare").hasValues(2000, 3000, 500, 1500, 2500, 3500)
                .column("start_date").hasValues(d2, d2, d2, d2, d2, d2)
                .column("finish_date").hasValues(d3, d3, d3, d3, d3, d3)
                .column("payment_day").hasValues(5, 5, 4, 3, 3, 2)
                .column("place_id").hasValues(2, 3, 4, 5, 6, 7)
                .column("renter_id").hasValues(1, 1, 2, 3, 3, 4);

        //проверка через getAllContracts
        List<Contract> contractList = contractRepository.findAll();

        assertThat(contractList).extracting(x -> x.getId()).contains(2, 3, 4, 5, 6, 7);
        assertThat(contractList).extracting(x -> x.getNumber()).contains("101R", "102R", "103L", "104O", "105O", "106M");
        assertThat(contractList).extracting(x -> sdf.parse(x.getDate().toString())).contains(sdf.parse(d1), sdf.parse(d1), sdf.parse(d1), sdf.parse(d1), sdf.parse(d1), sdf.parse(d1));
        assertThat(contractList).extracting(x -> x.getFare()).contains(2000, 3000, 500, 1500, 2500, 3500);
        assertThat(contractList).extracting(x -> sdf.parse(x.getStartDate().toString())).contains(sdf.parse(d2), sdf.parse(d2), sdf.parse(d2), sdf.parse(d2), sdf.parse(d2), sdf.parse(d2));
        assertThat(contractList).extracting(x -> sdf.parse(x.getFinishDate().toString())).contains(sdf.parse(d3), sdf.parse(d3), sdf.parse(d3), sdf.parse(d3), sdf.parse(d3), sdf.parse(d3));
        assertThat(contractList).extracting(x -> x.getPaymentDay()).contains(5, 5, 4, 3, 3, 2);
        assertThat(contractList).extracting(x -> x.getContractPlace().getId()).contains(2, 3, 4, 5, 6, 7);
        assertThat(contractList).extracting(x -> x.getRenter().getId()).contains(1, 1, 2, 3, 3, 4);
    }

    @Test
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/meter_init.sql",
            "/db_scripts/reading_init.sql", "/db_scripts/account_init.sql", "/db_scripts/payment_init.sql"})
    public void testUpdate() throws ParseException{

        String d1 = "2019-01-01";
        String d2 = "2021-12-31";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        int id = 1;
        String number = "TestNumber";
        Date testDate1 = sdf.parse(d1);
        int fare = 1000;
        Date testDate2 = sdf.parse(d2);
        int paymentDay = 5;
        int placeId = 1;
        int renterId = 1;

        transactionTemplate.execute(transactionStatus -> {
            Contract contractExpected = contractRepository.findById(id).get();

            contractExpected.setNumber(number);

            contractRepository.save(contractExpected);

            return null;
        });

        Request request = new Request(dataSource,
                "SELECT * FROM contract WHERE id = 1");

        Assertions.assertThat(request)
                .hasNumberOfRows(1)
                .column("id").hasValues(id)
                .column("number").hasValues(number)
                .column("contract_date").hasValues(sdf.format(testDate1))
                .column("fare").hasValues(fare)
                .column("start_date").hasValues(sdf.format(testDate1))
                .column("finish_date").hasValues(sdf.format(testDate2))
                .column("payment_day").hasValues(paymentDay)
                .column("place_id").hasValues(placeId)
                .column("renter_id").hasValues(renterId);
    }
}