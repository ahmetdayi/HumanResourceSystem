package com.obss.hrms.service.elasticsearch;

import com.obss.hrms.converter.elasticsearch.AdvertisementElasticConverter;
import com.obss.hrms.entity.AdvertisementStatue;
import com.obss.hrms.entity.HumanResourceEntity;

import com.obss.hrms.entity.elasticsearch.AdvertisementElastic;
import com.obss.hrms.repository.elasticsearch.AdvertisementElasticRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdvertisementElasticServiceTest {

    private AdvertisementElasticRepository advertisementElasticRepository;

    private AdvertisementElasticConverter advertisementElasticConverter;

    private AdvertisementElasticService advertisementElasticService;
    @BeforeEach
    void setUp() {
        advertisementElasticRepository = mock(AdvertisementElasticRepository.class);
        advertisementElasticConverter = mock(AdvertisementElasticConverter.class);
        advertisementElasticService = new AdvertisementElasticService(
                advertisementElasticRepository,
                advertisementElasticConverter
        );
    }

    @Test
    @DisplayName("search advertisment all fields")
    public void testSearchAdvertisement(){
        String query ="ahmet";
        HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                "cn=mike",
                "ahmet",
                "dayi",
                "ahmet dayi"
        );
        List<AdvertisementElastic> advertisementElastics = List.of(new AdvertisementElastic(
                "156545",
                "124324",
                "Obss",
                "java",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2025, 1, 1),
                AdvertisementStatue.PASSIVE,
                humanResourceEntity,
                null
        ));

        when(advertisementElasticRepository.findByAdvCodeLikeOrTitleLikeOrJobDescriptionLike(query,query,query)).thenReturn(advertisementElastics);


        List<AdvertisementElastic> advertisementElastics1 = advertisementElasticService.searchAdvertisement(query);

        assertEquals(advertisementElastics,advertisementElastics1);

        verify(advertisementElasticRepository).findByAdvCodeLikeOrTitleLikeOrJobDescriptionLike(query,query,query);


    }
    @Test
    @DisplayName("save all advertisement elastic")
    public void testSaveAll(){
        HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                "cn=mike",
                "ahmet",
                "dayi",
                "ahmet dayi"
        );
        List<AdvertisementElastic> advertisementElastics = List.of(new AdvertisementElastic(
                "156545",
                "124324",
                "Obss",
                "java",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2025, 1, 1),
                AdvertisementStatue.PASSIVE,
                humanResourceEntity,
                null
        ));
        advertisementElasticService.saveAll(advertisementElastics);

        verify(advertisementElasticRepository).saveAll(advertisementElastics);
    }
    @Test
    @DisplayName("find all advertisement elastic and return AdvertisementElastic list ")
    public void testFindAll(){
        HumanResourceEntity humanResourceEntity = new HumanResourceEntity(
                "cn=mike",
                "ahmet",
                "dayi",
                "ahmet dayi"
        );
        List<AdvertisementElastic> expected = List.of(new AdvertisementElastic(
                "156545",
                "124324",
                "Obss",
                "java",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2025, 1, 1),
                AdvertisementStatue.PASSIVE,
                humanResourceEntity,
                null
        ));
        when(advertisementElasticRepository.findAll()).thenReturn(expected);
        List<AdvertisementElastic> actual = advertisementElasticService.findAll();

        assertEquals(expected,actual);

        verify(advertisementElasticRepository).findAll();
    }
}