package com.studio.cloudstone;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GenCertTest {

    @Test
    public void getNo() {
        DateTime applyDate = DateTime.parse("2019-10-23", DateTimeFormat.forPattern("yyyy-MM-dd"));
        System.out.println(GenCert.getNo(applyDate));
        assertEquals(1,1);
    }
}