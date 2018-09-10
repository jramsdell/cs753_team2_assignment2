package edu.unh.cs753.utils;

import java.io.*;
import java.util.*;

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

    // Make a map of lists to get the list of PIDs
    public static HashMap<String, ArrayList<String>> ParseResults(String path) throws IOException {
        // Initialie hashmap
        HashMap<String, ArrayList<String>> m = new HashMap<>();

        FileReader fstream = new FileReader(path);
        BufferedReader in = new BufferedReader(fstream);
        String curLine;

        // If this query is in our first hashmap, then it is relevant, so add the id to the list
        while (null != in.readLine()) {
            curLine = in.readLine();
            String[] elements = curLine.split(" ");
            String query = elements[0];
            String ID = elements[2];

            if (!m.containsKey(query)) {
                ArrayList<String> a = new ArrayList<>();
                m.put(query, a);
            }
            ArrayList<String> al = m.get(query);
            al.add(ID);
            m.put(query, al);
        }
        in.close();
        return m;
    }

    public static void main(String [] args) throws IOException {

        String qrels = "/home/rachel/ir/test200/test200-train/train.pages.cbor-article.qrels";
        HashMap<String, HashSet<String>> relevant = EvaluationUtils.GetRelevantQueries(qrels);

        String res1 = "/home/rachel/ir/P2/cs753_team2_assignment2/results1.txt";
        String res2 = "/home/rachel/ir/P2/cs753_team2_assignment2/results2.txt";

        HashMap<String, ArrayList<String>> metrics1 = EvaluationUtils.ParseResults(res1);
        HashMap<String, ArrayList<String>> metrics2 = EvaluationUtils.ParseResults(res2);

    }


}
