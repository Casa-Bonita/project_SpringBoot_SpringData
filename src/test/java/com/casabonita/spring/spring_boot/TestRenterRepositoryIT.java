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
public class TestRenterRepositoryIT {

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
    @Sql("/db_scripts/renter_init.sql")
    public void testGetAllRenters() throws ParseException{

        List<Renter> renterList = renterRepository.findAll();

        String d1 = "1995-01-11";
        String d2 = "2006-05-03";
        String d3 = "2014-03-18";
        String d4 = "2008-12-23";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        assertThat(renterList).extracting(x -> x.getId()).contains(1, 2, 3, 4);
        assertThat(renterList).extracting(x -> x.getName()).contains("Romashka", "Luytik", "Oduvanchik", "Margaritka");
        assertThat(renterList).extracting(x -> x.getOgrn()).contains("1076318010548", "1064027042991", "1145476032668", "1086168005550");
        assertThat(renterList).extracting(x -> x.getInn()).contains("6318308609", "4027073395", "5406775985", "6168024958");
        assertThat(renterList).extracting(x -> sdf.parse(x.getRegistrDate().toString())).contains(sdf.parse(d1), sdf.parse(d2), sdf.parse(d3), sdf.parse(d4));
        assertThat(renterList).extracting(x -> x.getAddress()).contains("443117, Samarskaya oblast, gorod Samara, Orshanskij pereulok, 9",
                "248002, Kaluzhskaya oblast, gorod Kaluga, ulica Saltykova-Shchedrina, 76",
                "656056, Altajskij kraj, gorod Barnaul, ploshchad im V.N.Bavarina, dom 2, ofis 910",
                "344015, Rostovskaya oblast, gorod Rostov-na-Donu, ulica Eremenko, 58/9");
        assertThat(renterList).extracting(x -> x.getDirectorName()).contains("Prohorov Vladimir Stepanovich",
                "Shumakov Grigorij Anatolevich", "Trufanov Anton Yurevich", "Pavlickaya Natalya Yakovlevna");
        assertThat(renterList).extracting(x -> x.getContactName()).contains("Yablochkin Vasilij Petrovich",
                "Goncharov Eduard Sergeevich", "Arhipova Nadezhda Viktorovna", "Boldyreva Svetlana Aleksandrovna");
        assertThat(renterList).extracting(x -> x.getPhoneNumber()).contains("+7(495)123-45-67", "+7(495)123-67-45",
                "+7(495)123-45-89", "+7(495)123-67-45");
    }

    @Test
    public void testSaveRenter() throws ParseException{

        String d = "2021-01-01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String testName = "TestName";
        String testOGRN = "TestOGRN";
        String testINN = "TestINN";
        Date testDate = sdf.parse(d);
        String testAddress = "TestAddress";
        String testDirectorName = "TestDirectorName";
        String testContactName = "testContactName";
        String testPhoneNumber = "testPhoneNumber";

        transactionTemplate.execute(transactionStatus -> {
            Renter renter = new Renter();

            renter.setName(testName);
            renter.setOgrn(testOGRN);
            renter.setInn(testINN);
            renter.setRegistrDate(testDate);
            renter.setAddress(testAddress);
            renter.setDirectorName(testDirectorName);
            renter.setContactName(testContactName);
            renter.setPhoneNumber(testPhoneNumber);

            renterRepository.save(renter);

            return null;
        });

        Request request = new Request(dataSource,
                "SELECT * FROM renter");

        Assertions.assertThat(request)
                .hasNumberOfRows(1)
                .column("id").hasValues(1)
                .column("name").hasValues(testName)
                .column("name").hasValues(testName)
                .column("ogrn").hasValues(testOGRN)
                .column("inn").hasValues(testINN)
                .column("registr_date").hasValues(d)
                .column("address").hasValues(testAddress)
                .column("director_name").hasValues(testDirectorName)
                .column("contact_name").hasValues(testContactName)
                .column("phone").hasValues(testPhoneNumber);
    }

    @Test
    @Sql("/db_scripts/renter_init.sql")
    public void testGetRenter() throws ParseException{

        int id = 1;
        String name = "Romashka";
        String ogrn = "1076318010548";
        String inn = "6318308609";
        String d = "1995-01-11";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(d);
        String address = "443117, Samarskaya oblast, gorod Samara, Orshanskij pereulok, 9";
        String directorName = "Prohorov Vladimir Stepanovich";
        String contactName = "Yablochkin Vasilij Petrovich";
        String phone = "+7(495)123-45-67";

        // тест с использованием getRenter
        Renter renter = renterRepository.findById(id).get();

        assertThat(id).isEqualTo(renter.getId());
        assertThat(name).isEqualTo(renter.getName());
        assertThat(ogrn).isEqualTo(renter.getOgrn());
        assertThat(inn).isEqualTo(renter.getInn());
        assertThat(date).isEqualTo(renter.getRegistrDate());
        assertThat(address).isEqualTo(renter.getAddress());
        assertThat(directorName).isEqualTo(renter.getDirectorName());
        assertThat(contactName).isEqualTo(renter.getContactName());
        assertThat(phone).isEqualTo(renter.getPhoneNumber());

        // тест без использования getRenter
        Request request = new Request(dataSource,
                "SELECT * FROM renter WHERE id = 1");

        Assertions.assertThat(request)
                .hasNumberOfRows(1)
                .column("id").hasValues(1)
                .column("name").hasValues(name)
                .column("ogrn").hasValues(ogrn)
                .column("inn").hasValues(inn)
                .column("registr_date").hasValues(d) // почему тут String, а не Date
                .column("address").hasValues(address)
                .column("director_name").hasValues(directorName)
                .column("contact_name").hasValues(contactName)
                .column("phone").hasValues(phone);
    }

    @Test
    @Sql("/db_scripts/renter_init.sql")
    public void testGetRenterByName() throws ParseException{

        int id = 1;
        String name = "Romashka";
        String ogrn = "1076318010548";
        String inn = "6318308609";
        String d = "1995-01-11";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(d);
        String address = "443117, Samarskaya oblast, gorod Samara, Orshanskij pereulok, 9";
        String directorName = "Prohorov Vladimir Stepanovich";
        String contactName = "Yablochkin Vasilij Petrovich";
        String phone = "+7(495)123-45-67";

        // тест с использованием getRenter
        Renter renter = renterRepository.findByName(name);

        assertThat(id).isEqualTo(renter.getId());
        assertThat(name).isEqualTo(renter.getName());
        assertThat(ogrn).isEqualTo(renter.getOgrn());
        assertThat(inn).isEqualTo(renter.getInn());
        assertThat(date).isEqualTo(renter.getRegistrDate());
        assertThat(address).isEqualTo(renter.getAddress());
        assertThat(directorName).isEqualTo(renter.getDirectorName());
        assertThat(contactName).isEqualTo(renter.getContactName());
        assertThat(phone).isEqualTo(renter.getPhoneNumber());

        // тест без использования getRenter
        Request request = new Request(dataSource,
                "SELECT * FROM renter WHERE id = 1");

        Assertions.assertThat(request)
                .hasNumberOfRows(1)
                .column("id").hasValues(1)
                .column("name").hasValues(name)
                .column("ogrn").hasValues(ogrn)
                .column("inn").hasValues(inn)
                .column("registr_date").hasValues(d) // почему тут String, а не Date
                .column("address").hasValues(address)
                .column("director_name").hasValues(directorName)
                .column("contact_name").hasValues(contactName)
                .column("phone").hasValues(phone);
    }

    @Test
    @Sql("/db_scripts/renter_init.sql")
    public void testDeleteRenterById() throws ParseException{

        int id = 1;

        transactionTemplate.execute(transactionStatus -> {
            renterRepository.deleteBy(id);
            return null;
        });


        List<Renter> renterList = renterRepository.findAll();

        assertThat(renterList)
                .isNotEmpty()
                .hasSize(3);

        String d2 = "2006-05-03";
        String d3 = "2014-03-18";
        String d4 = "2008-12-23";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        assertThat(renterList).extracting(x -> x.getId()).contains(2, 3, 4);
        assertThat(renterList).extracting(x -> x.getName()).contains("Luytik", "Oduvanchik", "Margaritka");
        assertThat(renterList).extracting(x -> x.getOgrn()).contains("1064027042991", "1145476032668", "1086168005550");
        assertThat(renterList).extracting(x -> x.getInn()).contains("4027073395", "5406775985", "6168024958");
        assertThat(renterList).extracting(x -> sdf.parse(x.getRegistrDate().toString())).contains(sdf.parse(d2), sdf.parse(d3), sdf.parse(d4));
        assertThat(renterList).extracting(x -> x.getAddress()).contains("248002, Kaluzhskaya oblast, gorod Kaluga, ulica Saltykova-Shchedrina, 76",
                "656056, Altajskij kraj, gorod Barnaul, ploshchad im V.N.Bavarina, dom 2, ofis 910",
                "344015, Rostovskaya oblast, gorod Rostov-na-Donu, ulica Eremenko, 58/9");
        assertThat(renterList).extracting(x -> x.getDirectorName()).contains("Shumakov Grigorij Anatolevich",
                "Trufanov Anton Yurevich", "Pavlickaya Natalya Yakovlevna");
        assertThat(renterList).extracting(x -> x.getContactName()).contains("Goncharov Eduard Sergeevich",
                "Arhipova Nadezhda Viktorovna", "Boldyreva Svetlana Aleksandrovna");
        assertThat(renterList).extracting(x -> x.getPhoneNumber()).contains("+7(495)123-67-45", "+7(495)123-45-89", "+7(495)123-67-45");
    }

    @Test
    @Sql("/db_scripts/renter_init.sql")
    public void testUpdate() throws ParseException{

        String d = "1995-01-11";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        int id = 1;
        String name = "Test";
        String ogrn = "1076318010548";
        String inn = "6318308609";
        Date testDate = sdf.parse(d);
        String address = "443117, Samarskaya oblast, gorod Samara, Orshanskij pereulok, 9";
        String directorName = "Prohorov Vladimir Stepanovich";
        String contactName = "Yablochkin Vasilij Petrovich";
        String phoneNumber = "+7(495)123-45-67";

        transactionTemplate.execute(transactionStatus -> {
            Renter renterExpected = renterRepository.findById(id).get();

            renterExpected.setName(name);

            renterRepository.save(renterExpected);

            return null;
        });

        // тест без использования getRenter
        Request request = new Request(dataSource,
                "SELECT * FROM renter WHERE id = 1");

        Assertions.assertThat(request)
                .hasNumberOfRows(1)
                .column("id").hasValues(id)
                .column("name").hasValues(name)
                .column("ogrn").hasValues(ogrn)
                .column("inn").hasValues(inn)
                .column("registr_date").hasValues(sdf.format(testDate))
                .column("address").hasValues(address)
                .column("director_name").hasValues(directorName)
                .column("contact_name").hasValues(contactName)
                .column("phone").hasValues(phoneNumber);
    }
}