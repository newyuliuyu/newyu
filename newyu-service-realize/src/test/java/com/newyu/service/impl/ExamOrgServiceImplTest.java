package com.newyu.service.impl;

import com.google.common.collect.Lists;
import com.newyu.domain.org.*;
import com.newyu.service.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * ClassName: ExamOrgImplTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-6-3 上午10:45 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class ExamOrgServiceImplTest {

    @Autowired
    private ExamOrgServiceImpl examOrgService;

    @Test
    public void saveProvince() throws Exception {
        List<Province> provinces = Lists.newArrayList();
        provinces.add(Province.builder()
                .code("1")
                .name("省1")
                .build());
        provinces.add(Province.builder()
                .code("2")
                .name("省2")
                .build());

        examOrgService.saveProvince(1L, provinces);

    }

    @Test
    public void saveCity() throws Exception {
        List<City> cities = Lists.newArrayList();
        cities.add(City.builder()
                .code("1")
                .name("地市1")
                .build());
        cities.add(City.builder()
                .code("2")
                .name("地市2")
                .build());
        examOrgService.saveCity(1L, cities);
    }

    @Test
    public void saveCounty() throws Exception {
        List<County> counties = Lists.newArrayList();
        counties.add(County.builder()
                .code("1")
                .name("区县1")
                .build());
        counties.add(County.builder()
                .code("2")
                .name("区县2")
                .build());
        examOrgService.saveCounty(1L, counties);
    }

    @Test
    public void saveSchool() throws Exception {
        List<School> schools = Lists.newArrayList();
        schools.add(School.builder()
                .code("1")
                .name("学校1")
                .build());
        schools.add(School.builder()
                .code("2")
                .name("学校2")
                .build());
        examOrgService.saveSchool(1L, schools);
    }

    @Test
    public void saveClazz() throws Exception {
        List<Clazz> clazzes = Lists.newArrayList();
        clazzes.add(Clazz.builder()
                .schoolCode("1")
                .schoolName("学校1")
                .code("1")
                .name("1班")
                .wl(0)
                .group("A")
                .build());
        clazzes.add(Clazz.builder()
                .schoolCode("1")
                .schoolName("学校1")
                .code("2")
                .name("2班")
                .wl(0)
                .group("B")
                .build());

        examOrgService.saveClazz(1L, clazzes);

    }

    @Test
    public void saveTeachClazz() throws Exception {
        List<TeachClazz> teachClazzes = Lists.newArrayList();
        teachClazzes.add(TeachClazz.teachClazzBuilder()
                .schoolCode("1")
                .schoolName("学校1")
                .code("1")
                .name("1班")
                .subjectName("语文")
                .group("A")
                .build());
        teachClazzes.add(TeachClazz.teachClazzBuilder()
                .schoolCode("1")
                .schoolName("学校1")
                .code("2")
                .name("2班")
                .subjectName("语文")
                .group("B")
                .build());

        examOrgService.saveTeachClazz(1L,teachClazzes);
    }

    @Test
    public void saveOrgXSchool()throws Exception{
        List<OrgXSchool> orgXSchools=Lists.newArrayList();
        orgXSchools.add(OrgXSchool.builder()
                .province(Province.builder().code("1").name("省1").build())
                .city(City.builder().code("1").name("地市1").build())
                .county(County.builder().code("1").name("区县1").build())
                .school(School.builder().code("1").name("学校1").build())
                .build());
        orgXSchools.add(OrgXSchool.builder()
                .province(Province.builder().code("1").name("省1").build())
                .city(City.builder().code("2").name("地市2").build())
                .county(County.builder().code("2").name("区县2").build())
                .school(School.builder().code("2").name("学校2").build())
                .build());
        orgXSchools.add(OrgXSchool.builder()
                .province(Province.builder().code("1").name("省1").build())
                .city(City.builder().code("3").name("地市3").build())
                .county(County.builder().code("3").name("区县3").build())
                .school(School.builder().code("3").name("学校3").build())
                .build());

        examOrgService.saveOrgXSchool(1L,orgXSchools);
    }
}