package org.zzr1000.guavaTest;

import com.google.common.base.Splitter;
import org.junit.Test;

import java.util.Iterator;

public class SplitterTest {

    @Test
    public void test(){
        Iterable<String> str =
        Splitter.on(',')
                .trimResults()
                .omitEmptyStrings()
                .split("foo,bar,,   qux");

        for(Iterator a = str.iterator();a.hasNext();){
            System.out.println(a.next());
        }

    }

}
