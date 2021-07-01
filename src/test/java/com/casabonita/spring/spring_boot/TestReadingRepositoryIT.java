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
public class TestReadingRepositoryIT {

    @Autowired
    private MeterRepository meterRepository;

    @Autowired
    private ReadingRepository readingRepository;

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
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/meter_init.sql", "/db_scripts/reading_init.sql"})
    public void testGetAllReadings() throws ParseException{

        List<Reading> readingList = readingRepository.findAll();

        String d1 = "2021-03-01";
        String d2 = "2021-02-01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        assertThat(readingList).extracting(x -> x.getId()).contains(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
        assertThat(readingList).extracting(x -> x.getMeter().getId()).contains(1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7);
        assertThat(readingList).extracting(x -> x.getTransferData()).contains(11100, 11000, 21900, 21800, 3700, 3800, 6800, 6900, 15900, 15800, 32700, 32600, 29000, 28900);
        assertThat(readingList).extracting(x -> sdf.parse(x.getTransferDate().toString())).contains(sdf.parse(d1), sdf.parse(d2), sdf.parse(d1), sdf.parse(d2), sdf.parse(d1), sdf.parse(d2), sdf.parse(d1), sdf.parse(d2), sdf.parse(d1), sdf.parse(d2), sdf.parse(d1), sdf.parse(d2), sdf.parse(d1), sdf.parse(d2));
    }

    @Test
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/meter_init.sql"})
    public void testSaveReading() throws ParseException {

        String d = "2021-01-01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        int id = 1;
        int testTransferData = 100;
        Date testTransferDate = sdf.parse(d);

        transactionTemplate.execute(transactionStatus ->{
            Meter meter = meterRepository.findById(id).get();

            Reading reading = new Reading();

            reading.setMeter(meter);
            reading.setTransferData(testTransferData);
            reading.setTransferDate(testTransferDate);

            readingRepository.save(reading);

            return null;
        });

        Request request = new Request(dataSource,
                "SELECT * FROM meter_data");

        Assertions.assertThat(request)
                .hasNumberOfRows(1)
                .column("id").hasValues(id)
                .column("data").hasValues(testTransferData)
                .column("data_date").hasValues(d);
    }

    @Test
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/meter_init.sql", "/db_scripts/reading_init.sql"})
    public void testGetReading() throws ParseException{

        String d = "2021-03-01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        int id = 1;
        int meterId = 1;
        int transferData = 11100;
        Date transferDate = sdf.parse(d);

        // проверка через getReading
        Reading reading = readingRepository.findById(id).get();

        assertThat(id).isEqualTo(reading.getId());
        assertThat(meterId).isEqualTo(reading.getMeter().getId());
        assertThat(transferData).isEqualTo(reading.getTransferData());
        assertThat(transferDate).isEqualTo(reading.getTransferDate());

        // тест без использования getPayment
        Request request = new Request(dataSource,
                "SELECT * FROM meter_data WHERE id = 1");

        Assertions.assertThat(request)
                .hasNumberOfRows(1)
                .column("id").hasValues(id)
                .column("meter_id").hasValues(meterId)
                .column("data").hasValues(transferData)
                .column("data_date").hasValues(d);
    }

    @Test
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/meter_init.sql", "/db_scripts/reading_init.sql"})
    public void testDeleteReadingById() throws ParseException{

        int id = 1;
        transactionTemplate.execute(transactionStatus -> {

            readingRepository.deleteBy(id);
            return null;
        });

        List<Reading> readingList = readingRepository.findAll();

        assertThat(readingList)
                .isNotEmpty()
                .hasSize(13);

        String d1 = "2021-03-01";
        String d2 = "2021-02-01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        assertThat(readingList).extracting(x -> x.getId()).contains(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
        assertThat(readingList).extracting(x -> x.getMeter().getId()).contains(1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7);
        assertThat(readingList).extracting(x -> x.getTransferData()).contains(11000, 21900, 21800, 3700, 3800, 6800, 6900, 15900, 15800, 32700, 32600, 29000, 28900);
        assertThat(readingList).extracting(x -> sdf.parse(x.getTransferDate().toString())).contains(sdf.parse(d2), sdf.parse(d1), sdf.parse(d2), sdf.parse(d1), sdf.parse(d2), sdf.parse(d1), sdf.parse(d2), sdf.parse(d1), sdf.parse(d2), sdf.parse(d1), sdf.parse(d2), sdf.parse(d1), sdf.parse(d2));
    }

    @Test
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/meter_init.sql", "/db_scripts/reading_init.sql"})
    public void testDeleteReadingByMeterId() throws ParseException{

        int id = 1;

        transactionTemplate.execute(transactionStatus ->{

            readingRepository.deleteReadingByMeterId(id);
            return null;
        });

        // проверка через request удаления строки
        Request requestOne = new Request(dataSource,
                "SELECT * FROM meter_data WHERE meter_id = 1");

        Assertions.assertThat(requestOne)
                .isEmpty();

        //проверка через getAllReadings
        List<Reading> readingList = readingRepository.findAll();

        assertThat(readingList)
                .isNotEmpty()
                .hasSize(12);

        String d1 = "2021-03-01";
        String d2 = "2021-02-01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        assertThat(readingList).extracting(x -> x.getId()).contains(3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
        assertThat(readingList).extracting(x -> x.getMeter().getId()).contains(2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7);
        assertThat(readingList).extracting(x -> x.getTransferData()).contains(21900, 21800, 3700, 3800, 6800, 6900, 15900, 15800, 32700, 32600, 29000, 28900);
        assertThat(readingList).extracting(x -> sdf.parse(x.getTransferDate().toString())).contains(sdf.parse(d1), sdf.parse(d2), sdf.parse(d1), sdf.parse(d2), sdf.parse(d1), sdf.parse(d2), sdf.parse(d1), sdf.parse(d2), sdf.parse(d1), sdf.parse(d2), sdf.parse(d1), sdf.parse(d2));
    }

    @Test
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/meter_init.sql", "/db_scripts/reading_init.sql"})
    public void testUpdate() throws ParseException{

        int data = 123456;
        int id = 1;

        transactionTemplate.execute(transactionStatus ->{

            Reading readingExpected = readingRepository.findById(id).get();

            readingExpected.setTransferData(data);

            readingRepository.save(readingExpected);

            return null;
        });

        Request request = new Request(dataSource,
                "SELECT * FROM meter_data WHERE id = 1");

        Assertions.assertThat(request)
                .hasNumberOfRows(1)
                .column("id").hasValues(id)
                .column("data").hasValues(data);
    }
}
