package com.target.cs;

import com.target.cs.services.time.calc.NextAvailiableBusServiceTimeCalcImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TarBusTimeArrivalCalcApplicationTests {


    private static final Logger LOGGER = LoggerFactory.getLogger(TarBusTimeArrivalCalcApplicationTests.class);

    @Autowired
    private WebApplicationContext context;


    private MockMvc mockMvc;


    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }


    @Test
    public void contextLoads() throws Exception {

        RequestBuilder requestBuilder;
        MvcResult result;

        LOGGER.info("\n<======= Case-1 =====================================================================================================>");
        requestBuilder = MockMvcRequestBuilders.get("/app/bus/METRO Blue Line/SOUTH/Mall of America Station");
        result = mockMvc.perform(requestBuilder).andReturn();
        LOGGER.info(result.getResponse().getContentAsString());
        Assert.assertTrue("Checking for 200 ",
                result.getResponse().getStatus() >= 200 && result.getResponse().getStatus() < 300);
        Assert.assertTrue("Checking on non empty data ", !StringUtils.isEmpty(result.getResponse().getContentAsString()));


        LOGGER.info("\n<======= Case-2 =====================================================================================================>");
        requestBuilder = MockMvcRequestBuilders.get("/app/bus/Express - Target - Hwy 252 and 73rd Av P&R - Mpls/SOUTH/Target North Campus Building F");
        result = mockMvc.perform(requestBuilder).andReturn();
        LOGGER.info(result.getResponse().getContentAsString());
        Assert.assertTrue("Checking for 200 ",
                result.getResponse().getStatus() >= 200 && result.getResponse().getStatus() < 300);
        Assert.assertTrue("Checking on non empty data ", !StringUtils.isEmpty(result.getResponse().getContentAsString()));


        LOGGER.info("\n<======= Case-3 =====================================================================================================>");
        requestBuilder = MockMvcRequestBuilders.get("/app/bus/Express - Target - Hwy 252 and 73rd Av P&R - Mpls/NORTH/Express - Target - Hwy 252 and 73rd Av P&R - Mpls");
        result = mockMvc.perform(requestBuilder).andReturn();
        LOGGER.info(result.getResponse().getContentAsString());
        Assert.assertTrue("Checking for 200 ",
                result.getResponse().getStatus() >= 200 && result.getResponse().getStatus() < 300);
        Assert.assertTrue("Checking on non empty data ", result.getResponse().getContentAsString()
                .contains(NextAvailiableBusServiceTimeCalcImpl.stopsNotFoundMessage));


        LOGGER.info("\n<======= Case-4 =====================================================================================================>");
        requestBuilder = MockMvcRequestBuilders.get("/app/bus/Express - Target/NORTH/Express - Target - Hwy 252 and 73rd Av P&R - Mpls");
        result = mockMvc.perform(requestBuilder).andReturn();
        LOGGER.info(result.getResponse().getContentAsString());
        Assert.assertTrue("Checking for 200 ",
                result.getResponse().getStatus() >= 200 && result.getResponse().getStatus() < 300);
        Assert.assertTrue("Checking on non empty data ", result.getResponse().getContentAsString()
                .contains(NextAvailiableBusServiceTimeCalcImpl.stopsNotFoundMessage));


        LOGGER.info("\n<======= Case-5 =====================================================================================================>");
        requestBuilder = MockMvcRequestBuilders.get("/app/bus//NORTH/Express - Target - Hwy 252 and 73rd Av P&R - Mpls");
        result = mockMvc.perform(requestBuilder).andReturn();
        LOGGER.info(result.getResponse().getContentAsString());
        Assert.assertTrue("Checking for 400 type ",
                result.getResponse().getStatus() >= 400 && result.getResponse().getStatus() < 499);


        LOGGER.info("\n<======= Case-6 =====================================================================================================>");
        requestBuilder = MockMvcRequestBuilders.get("/app/bus/23/NORTHS/Express - Target - Hwy 252 and 73rd Av P&R - Mpls");
        result = mockMvc.perform(requestBuilder).andReturn();
        LOGGER.info(result.getResponse().getContentAsString());
        Assert.assertTrue("Checking for 200 ",
                result.getResponse().getStatus() >= 200 && result.getResponse().getStatus() < 300);
        Assert.assertTrue("Checking on non empty data ", result.getResponse().getContentAsString()
                .contains(NextAvailiableBusServiceTimeCalcImpl.invalidRouteMessage));

        LOGGER.info("\n<======= Case-7 =====================================================================================================>");
        requestBuilder = MockMvcRequestBuilders.get("/app/bus/23kjkj kj/NORTHS/Express - Target - Hwy 252 and 73rd Av P&R - Mpls");
        result = mockMvc.perform(requestBuilder).andReturn();
        LOGGER.info(result.getResponse().getContentAsString());
        Assert.assertTrue("Checking for 200 ",
                result.getResponse().getStatus() >= 200 && result.getResponse().getStatus() < 300);
        Assert.assertTrue("Checking on non empty data ", result.getResponse().getContentAsString()
                .contains(NextAvailiableBusServiceTimeCalcImpl.invalidDirectionMessage));


        LOGGER.info("\n<======= Case-8 =====================================================================================================>");
        requestBuilder = MockMvcRequestBuilders.get("/app/bus/23kjkj kj/NORTH/88");
        result = mockMvc.perform(requestBuilder).andReturn();
        LOGGER.info(result.getResponse().getContentAsString());
        Assert.assertTrue("Checking for 200 ",
                result.getResponse().getStatus() >= 200 && result.getResponse().getStatus() < 300);
        Assert.assertTrue("Checking on non empty data ", result.getResponse().getContentAsString()
                .contains(NextAvailiableBusServiceTimeCalcImpl.invalidStopMessage));


    }

}
