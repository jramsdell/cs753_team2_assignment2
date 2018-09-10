package edu.unh.cs753.utils;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EvaluationUtils {

    // Make a static function that takes this path as a function.
    // Open the file, split based on space, make a map of sets,
    // key = query, set = the results that are relevant to the query.
    public static HashMap<String, HashSet<String>> GetRelevantQueries(String path) throws IOException {
        // Initialie hashmap
        HashMap<String, HashSet<String>> m = new HashMap<>();

        FileReader fstream = new FileReader(path);
        BufferedReader in = new BufferedReader(fstream);
        String curLine;

        while (in.readLine() != null) {
            curLine = in.readLine();
            String[] elements = curLine.split(" ");
            String query = elements[0];
            String ID = elements[2];

            if (!m.containsKey(query)) {
                HashSet<String> s = new HashSet<>();
                m.put(query, s);
            }
            HashSet<String> hs = m.get(query);
            hs.add(ID);
            m.put(query, hs);
        }
        in.close();
        return m;
    }

    // Make a map of lists to parse the results file.


    public static void main(String [] args) throws IOException {

        String qrels = "/home/rachel/ir/test200/test200-train/train.pages.cbor-article.qrels";
        HashMap<String, HashSet<String>> relevant = EvaluationUtils.GetRelevantQueries(qrels);

    }


}
