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
public class TestMeterRepositoryIT {

    @Autowired
    private MeterRepository meterRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private ReadingRepository readingRepository;

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
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/meter_init.sql", "/db_scripts/reading_init.sql"})
    public void testGetAllMeters(){

        List<Meter> meterList = meterRepository.findAll();

        assertThat(meterList).extracting(x -> x.getId()).contains(1, 2, 3, 4, 5, 6, 7);
        assertThat(meterList).extracting(x -> x.getNumber()).contains("428510", "428511", "428512", "428513", "428514", "428515", "428516");
        assertThat(meterList).extracting(x -> x.getMeterPlace().getId()).contains(1, 2, 3, 4, 5, 6, 7);
    }

    @Test
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/meter_init.sql", "/db_scripts/reading_init.sql"})
    public void testSaveMeter(){

        int testPlaceId = 1;
        String testNumber = "TestNumber";

        transactionTemplate.execute(transactionStatus -> {

            Place place = placeRepository.findById(testPlaceId).get();

            Meter meter = new Meter();

            meter.setNumber(testNumber);
            meter.setMeterPlace(place);

            place.setMeter(meter);

            meterRepository.save(meter);

            return null;
        });

        Request request = new Request(dataSource,
                "SELECT * FROM meter WHERE id = 8");

        Assertions.assertThat(request)
                .hasNumberOfRows(1)
                .column("id").hasValues(8)
                .column("meter_number").hasValues(testNumber)
                .column("place_id").hasValues(testPlaceId);
    }

    @Test
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/meter_init.sql", "/db_scripts/reading_init.sql"})
    public void testGetMeter(){

        int id = 1;
        String meterNumber = "428510";
        int placeId = 1;

        // проверка через getMeter
        Meter meter = meterRepository.findById(id).get();

        assertThat(id).isEqualTo(meter.getId());
        assertThat(meterNumber).isEqualTo(meter.getNumber());
        assertThat(placeId).isEqualTo(meter.getMeterPlace().getId());

        // проверка без getMeter через request из БД
        Request request = new Request(dataSource,
                "SELECT * FROM meter WHERE id = 1");

        Assertions.assertThat(request)
                .hasNumberOfRows(1)
                .column("id").hasValues(id)
                .column("meter_number").hasValues(meterNumber)
                .column("place_id").hasValues(placeId);
    }

    @Test
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/meter_init.sql", "/db_scripts/reading_init.sql"})
    public void testGetMeterByPlaceId(){

        int id = 1;
        String meterNumber = "428510";
        int placeId = 1;

        // проверка через getMeter
        Meter meter = meterRepository.findMeterByMeterPlace_Id(placeId);

        assertThat(id).isEqualTo(meter.getId());
        assertThat(meterNumber).isEqualTo(meter.getNumber());
        assertThat(placeId).isEqualTo(meter.getMeterPlace().getId());

        // проверка без getMeter через request из БД
        Request request = new Request(dataSource,
                "SELECT * FROM meter WHERE place_id = 1");

        Assertions.assertThat(request)
                .hasNumberOfRows(1)
                .column("id").hasValues(id)
                .column("meter_number").hasValues(meterNumber)
                .column("place_id").hasValues(placeId);
    }

    @Test
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/meter_init.sql", "/db_scripts/reading_init.sql"})
    public void testGetMeterByNumber(){

        int id = 1;
        String meterNumber = "428510";
        int placeId = 1;

        // проверка через getMeter
        Meter meter = meterRepository.findMeterByNumber(meterNumber);

        assertThat(id).isEqualTo(meter.getId());
        assertThat(meterNumber).isEqualTo(meter.getNumber());
        assertThat(placeId).isEqualTo(meter.getMeterPlace().getId());

        // проверка без getMeter через request из БД
        Request request = new Request(dataSource,
                "SELECT * FROM meter WHERE meter_number = '428510'");

        Assertions.assertThat(request)
                .hasNumberOfRows(1)
                .column("id").hasValues(id)
                .column("meter_number").hasValues(meterNumber)
                .column("place_id").hasValues(placeId);
    }

    @Test
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/meter_init.sql"})
    public void testDeleteMeterById(){

        int id = 1;

        transactionTemplate.execute(transactionStatus ->{
            meterRepository.deleteBy(id);
            return null;
        });

        // проверка через request удаления строки
        Request requestOne = new Request(dataSource,
                "SELECT * FROM meter WHERE id = 1");

        Assertions.assertThat(requestOne)
                .isEmpty();

        // проверка через request всех оставшихся после удаления строк таблицы
        Request requestAll = new Request(dataSource,
                "SELECT * FROM meter");

        Assertions.assertThat(requestAll)
                .column("id").hasValues(2, 3, 4, 5, 6, 7)
                .column("meter_number").hasValues("428511", "428512", "428513", "428514", "428515", "428516")
                .column("place_id").hasValues(2, 3, 4, 5, 6, 7);

        //проверка через getAllMeters
        List<Meter> meterList = meterRepository.findAll();

        assertThat(meterList).extracting(x -> x.getId()).contains(2, 3, 4, 5, 6, 7);
        assertThat(meterList).extracting(x -> x.getNumber()).contains("428511", "428512", "428513", "428514", "428515", "428516");
        assertThat(meterList).extracting(x -> x.getMeterPlace().getId()).contains(2, 3, 4, 5, 6, 7);
    }

    @Test
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/meter_init.sql", "/db_scripts/reading_init.sql"})
    public void testUpdate(){

        String meterNumber = "123456";;
        int id = 1;

        transactionTemplate.execute(transactionStatus -> {
            Meter meterExpected = meterRepository.findById(id).get();

            meterExpected.setNumber(meterNumber);

            meterRepository.save(meterExpected);

            return null;
        });

        // тест без использования getMeter
        Request request = new Request(dataSource,
                "SELECT * FROM meter WHERE id = 1");

        Assertions.assertThat(request)
                .hasNumberOfRows(1)
                .column("id").hasValues(id)
                .column("meter_number").hasValues(meterNumber);
    }
}
