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
public class TestPlaceRepositoryIT {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private PlaceRepository placeRepository;

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
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/meter_init.sql"})
    public void testGetAllPlaces() {

        List<Place> placeList = placeRepository.findAll();

        assertThat(placeList).extracting(x -> x.getId()).contains(1, 2, 3, 4, 5, 6, 7);
        assertThat(placeList).extracting(x -> x.getNumber()).contains(42, 43, 44, 10, 37, 40, 26);
        assertThat(placeList).extracting(x -> x.getName()).contains("Place-01", "Place-02", "Place-03", "H&M", "Post office", "Cafe", "Union cards");
        assertThat(placeList).extracting(x -> x.getSquare()).contains(45.8, 35.7, 50.1, 39.6, 6.0, 62.0, 2.0);
        assertThat(placeList).extracting(x -> x.getFloor()).contains(2, 2, 2, 3, 1, 0, 0);
        assertThat(placeList).extracting(x -> x.getType()).contains("office", "office", "office", "shop", "island", "other", "atm");
    }

    @Test
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/meter_init.sql"})
    public void testSavePlace(){

        int testContractId = 1;
        int testNumber = 123456;
        String testName = "TestName";
        Double testSquare = 1111.99;
        int testFloor = 99;
        String testType = "TestType";

        transactionTemplate.execute(transactionStatus -> {

            Contract contract = contractRepository.findById(testContractId).get();

            Place place = new Place();

            place.setNumber(testNumber);
            place.setName(testName);
            place.setSquare(testSquare);
            place.setFloor(testFloor);
            place.setType(testType);

            contract.setContractPlace(place);

            placeRepository.save(place);

            return null;
        });

        Request request = new Request(dataSource,
                "SELECT * FROM place WHERE id = 8");

        Assertions.assertThat(request)
                .hasNumberOfRows(1)
                .column("id").hasValues(8)
                .column("number").hasValues(testNumber)
                .column("name").hasValues(testName)
                .column("square").hasValues(testSquare)
                .column("floor").hasValues(testFloor)
                .column("type").hasValues(testType);
    }

    @Test
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/meter_init.sql"})
    public void testGetPlaceById() {

        int id = 1;
        int number = 42;
        String name = "Place-01";
        Double square = 45.8;
        int floor = 2;
        String type = "office";

        // проверка через getPlace
        Place place = placeRepository.findById(id).get();

        assertThat(id).isEqualTo(place.getId());
        assertThat(number).isEqualTo(place.getNumber());
        assertThat(name).isEqualTo(place.getName());
        assertThat(square).isEqualTo(place.getSquare());
        assertThat(floor).isEqualTo(place.getFloor());
        assertThat(type).isEqualTo(place.getType());

        // проверка без getPlace через request из БД
        Request request = new Request(dataSource,
                "SELECT * FROM place WHERE id = 1");

        Assertions.assertThat(request)
                .hasNumberOfRows(1)
                .column("id").hasValues(id)
                .column("number").hasValues(number)
                .column("name").hasValues(name)
                .column("square").hasValues(square)
                .column("floor").hasValues(floor)
                .column("type").hasValues(type);
    }

    @Test
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/meter_init.sql"})
    public void testGetPlaceByNumber() {

        int id = 1;
        int number = 42;
        String name = "Place-01";
        Double square = 45.8;
        int floor = 2;
        String type = "office";

        // проверка через getPlace
        Place place = placeRepository.findPlaceByNumber(number);

        assertThat(id).isEqualTo(place.getId());
        assertThat(number).isEqualTo(place.getNumber());
        assertThat(name).isEqualTo(place.getName());
        assertThat(square).isEqualTo(place.getSquare());
        assertThat(floor).isEqualTo(place.getFloor());
        assertThat(type).isEqualTo(place.getType());

        // проверка без getPlace через request из БД
        Request request = new Request(dataSource,
                "SELECT * FROM place WHERE number = 42");

        Assertions.assertThat(request)
                .hasNumberOfRows(1)
                .column("id").hasValues(id)
                .column("number").hasValues(number)
                .column("name").hasValues(name)
                .column("square").hasValues(square)
                .column("floor").hasValues(floor)
                .column("type").hasValues(type);
    }

    @Test
    @Sql({"/db_scripts/place_init.sql"})
    public void testDeletePlaceById() {

        int id = 1;

        transactionTemplate.execute(transactionStatus -> {
            placeRepository.deleteBy(id);
            return null;
        });

        // проверка через request удаления строки
        Request requestOne = new Request(dataSource,
                "SELECT * FROM place WHERE id = 1");

        Assertions.assertThat(requestOne)
                .isEmpty();

        // проверка через request всех оставшихся после удаления строк таблицы
        Request requestAll = new Request(dataSource,
                "SELECT * FROM place");

        Assertions.assertThat(requestAll)
                .column("id").hasValues(2, 3, 4, 5, 6, 7)
                .column("number").hasValues(43, 44, 10, 37, 40, 26)
                .column("name").hasValues("Place-02", "Place-03", "H&M", "Post office", "Cafe", "Union cards")
                .column("square").hasValues(35.7, 50.1, 39.6, 6.0, 62.0, 2.0)
                .column("floor").hasValues(2, 2, 3, 1, 0, 0)
                .column("type").hasValues("office", "office", "shop", "island", "other", "atm");

        //проверка через getAllPlaces
        List<Place> placeList = placeRepository.findAll();

        assertThat(placeList).extracting(x -> x.getId()).contains(2, 3, 4, 5, 6, 7);
        assertThat(placeList).extracting(x -> x.getNumber()).contains(43, 44, 10, 37, 40, 26);
        assertThat(placeList).extracting(x -> x.getName()).contains("Place-02", "Place-03", "H&M", "Post office", "Cafe", "Union cards");
        assertThat(placeList).extracting(x -> x.getSquare()).contains(35.7, 50.1, 39.6, 6.0, 62.0, 2.0);
        assertThat(placeList).extracting(x -> x.getFloor()).contains(2, 2, 3, 1, 0, 0);
        assertThat(placeList).extracting(x -> x.getType()).contains("office", "office", "shop", "island", "other", "atm");
    }

    @Test
    @Sql({"/db_scripts/renter_init.sql", "/db_scripts/place_init.sql", "/db_scripts/contract_init.sql", "/db_scripts/meter_init.sql"})
    public void testUpdate() {

        int id = 1;
        int testNumber = 123456;
        String name = "Place-01";
        Double square = 45.8;
        int floor = 2;
        String type = "office";


        transactionTemplate.execute(transactionStatus -> {

            Place placeExpected = placeRepository.findById(id).get();

            placeExpected.setNumber(testNumber);

            placeRepository.save(placeExpected);

            return null;
        });

        // тест без использования getPlace
        Request request = new Request(dataSource,
                "SELECT * FROM place WHERE id = 1");

        Assertions.assertThat(request)
                .hasNumberOfRows(1)
                .column("id").hasValues(id)
                .column("number").hasValues(testNumber)
                .column("name").hasValues(name)
                .column("square").hasValues(square)
                .column("floor").hasValues(floor)
                .column("type").hasValues(type);
    }
}