package com.jetwinner.webfast.freemarker;

import org.junit.Before;

import static org.junit.Assert.assertEquals;

public class FastLibraryObjectTest {

    private FastLibraryObject fastLib;

    @Before
    public void setup() {
        fastLib = new FastLibraryObject();
    }

    private String fillQuestionStemText(String stem) {
        if (stem != null && stem.indexOf("[[") >= 0 && stem.indexOf("]]") >= 0) {
            return stem.replaceAll("\\[\\[.+?\\]\\]", "____");
        }
        return stem;
    }

    public void fillQuestionStemText() {
        String str = fillQuestionStemText("今年是[[2014|马]]年");
        assertEquals("今年是____年", str);
        str = "《鲁滨逊漂流记》的作者是英国作家[[丹尼尔迪福]]。他在一座无人荒岛上生活多年后，收得一土人为奴，取名[[星期五]]。";
        String expect = "《鲁滨逊漂流记》的作者是英国作家____。他在一座无人荒岛上生活多年后，收得一土人为奴，取名____。";
        assertEquals(expect, fillQuestionStemText(str));
    }
}