package com.company;

import edu.stanford.nlp.ie.machinereading.MachineReading;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;

public class TrainRelations {

    public static void main(String[] args) throws Exception {
    /*    ClassLoader cl = ClassLoader.getSystemClassLoader();

        URL[] urls = ((URLClassLoader)cl).getURLs();

        for(URL url: urls){
            System.out.println(url.getFile());
        }
        StanfordCoreNLP pipeline =
                new StanfordCoreNLP("roth.properties");*/
        //Properties props = new Properties("roth.properties");
        MachineReading mr = MachineReading.makeMachineReading(args);
        mr.run();
    }
}
