package com.company;

import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.stream.Collectors;

public class CreateRelationData {

    public static void main(String[] args) {
        // set up pipeline properties
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma, ner, entitymentions, regexner");
        props.put("regexner.mapping", "basic_ner.rules");
        // set up Stanford CoreNLP pipeline
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        // build annotation for a review
        String fileContents;
        try {
            fileContents = IOUtils.slurpFile(args[0]);
        } catch (IOException e) {
            fileContents = "We expect that the Earnings Per Share - EPS of Google will be up by 4% in 4th quarter of 2018.";
            //e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (Exception ex) {
            fileContents = "We expect that the Earnings Per Share - EPS of Google will be up by 4% in 4th quarter of 2018.";
        }
        Annotation annotation = new Annotation(fileContents);
        pipeline.annotate(annotation);
        int sentNum = 0;
        //create StringBuffer object
        StringBuffer sbf = new StringBuffer();
        for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            int tokenNum = 1;
            int elementNum = 0;
            int entityNum = 0;
            if (sentence.get(CoreAnnotations.MentionsAnnotation.class).size() == 0)
                continue;
            CoreMap currEntityMention = sentence.get(CoreAnnotations.MentionsAnnotation.class).get(entityNum);
            String currEntityMentionWords = currEntityMention.get(CoreAnnotations.TokensAnnotation.class).stream().map(token -> token.word()).
                    collect(Collectors.joining("/"));
            String currEntityMentionTags =
                    currEntityMention.get(CoreAnnotations.TokensAnnotation.class).stream().map(token -> token.tag()).
                            collect(Collectors.joining("/"));
            String currEntityMentionNER = currEntityMention.get(CoreAnnotations.EntityTypeAnnotation.class);
            while (tokenNum <= sentence.get(CoreAnnotations.TokensAnnotation.class).size()) {
                if (currEntityMention.get(CoreAnnotations.TokensAnnotation.class).get(0).index() == tokenNum) {
                    String entityText = currEntityMention.toString();
                    System.out.println(sentNum + "\t" + currEntityMentionNER + "\t" + elementNum + "\t" + "O\t" + currEntityMentionTags + "\t" +
                            currEntityMentionWords + "\t" + "O\tO\tO");
                    sbf.append(sentNum + "\t" + currEntityMentionNER + "\t" + elementNum + "\t" + "O\t" + currEntityMentionTags + "\t" +
                            currEntityMentionWords + "\t" + "O\tO\tO");
                    sbf.append(System.lineSeparator());
                    // update tokenNum
                    tokenNum += (currEntityMention.get(CoreAnnotations.TokensAnnotation.class).size());
                    // update entity if there are remaining entities
                    entityNum++;
                    if (entityNum < sentence.get(CoreAnnotations.MentionsAnnotation.class).size()) {
                        currEntityMention = sentence.get(CoreAnnotations.MentionsAnnotation.class).get(entityNum);
                        currEntityMentionWords = currEntityMention.get(CoreAnnotations.TokensAnnotation.class).stream().map(token -> token.word()).
                                collect(Collectors.joining("/"));
                        currEntityMentionTags =
                                currEntityMention.get(CoreAnnotations.TokensAnnotation.class).stream().map(token -> token.tag()).
                                        collect(Collectors.joining("/"));
                        currEntityMentionNER = currEntityMention.get(CoreAnnotations.EntityTypeAnnotation.class);
                    }
                } else {
                    CoreLabel token = sentence.get(CoreAnnotations.TokensAnnotation.class).get(tokenNum - 1);
                    System.out.println(sentNum + "\t" + token.ner() + "\t" + elementNum + "\tO\t" + token.tag() + "\t" + token.word() + "\t" + "O\tO\tO");
                    sbf.append(sentNum + "\t" + token.ner() + "\t" + elementNum + "\tO\t" + token.tag() + "\t" + token.word() + "\t" + "O\tO\tO");
                    sbf.append(System.lineSeparator());
                    tokenNum += 1;
                }
                elementNum += 1;
            }
            sentNum++;
            System.out.println();
            sbf.append(System.lineSeparator());
            System.out.println("0\t3\tLive_In");
            sbf.append("0\t3\tLive_In");
            System.out.println();
            sbf.append(System.lineSeparator());
            sbf.append(System.lineSeparator());
        }
        // Write sbf to file
        BufferedWriter bwr = null;
        try {
            bwr = new BufferedWriter(new FileWriter(new File("SampleOutput.txt")));
            //write contents of StringBuffer to a file
            bwr.write(sbf.toString());

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {

            if (bwr != null) {
                try {
                    //flush the stream
                    bwr.flush();
                    //close the stream
                    bwr.close();
                } catch (IOException e) {
                    // e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

            }


        }


        System.out.println("Content of StringBuffer written to File.");

    }
}