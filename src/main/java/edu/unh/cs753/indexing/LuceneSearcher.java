package edu.unh.cs753.indexing;

import edu.unh.cs.treccar_v2.Data;
import edu.unh.cs753.utils.IndexUtils;
import edu.unh.cs753.utils.SearchUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.BasicStats;
import org.apache.lucene.search.similarities.SimilarityBase;

import java.io.IOException;

public class LuceneSearcher {
    public final IndexSearcher searcher;
    public final Iterable<Data.Page> pages;

    public LuceneSearcher(String indexLoc, String queryCborLoc) {
        searcher = SearchUtils.createIndexSearcher(indexLoc);
        pages = IndexUtils.createPageIterator(queryCborLoc);
    }

    /**
     * Function: query
     * Desc: Queries Lucene paragraph corpus using a standard similarity function.
     *       Note that this uses the StandardAnalyzer.
     * @param queryString: The query string that will be turned into a boolean query.
     * @param nResults: How many search results should be returned
     * @return TopDocs (ranked results matching query)
     */
    public TopDocs query(String queryString, Integer nResults) {
        Query q = SearchUtils.createStandardBooleanQuery(queryString, "text");
        try {
            return searcher.search(q, nResults);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void run() throws IOException {
        // Quick sketch of things you might need
        for (Data.Page page : pages) {

            // Id of the page, which is needed when you print out the run file
            String pageId = page.getPageId();

            // This query is the name of the page
            String query = page.getPageName();
            doSearch(query);

        }

    }

    public void customRun() {
        // We don't need this right now
    }

    public void doSearch(String query) throws IOException {
        TopDocs topDocs = query(query, 100);

        // This is an example of iterating of search results
        for (ScoreDoc sd : topDocs.scoreDocs) {
            Document doc = searcher.doc(sd.doc);
            String paraId = doc.get("id");
            float score = sd.score;
        }

        // You should return something here after parsing out the paragraph ids and scores
    }

    public void custom () throws IOException {
        System.out.println("This is custom Scoring function");
        SimilarityBase mysimilarity= new SimilarityBase() {
            @Override
            protected float score(BasicStats basicStats, float v, float v1) {
                float sum1 = 0.0f;
                sum1 += v;
                return sum1;
            }

            @Override
            public String toString() {
                return null;
            }
        };
        searcher.setSimilarity(mysimilarity);

//        TopDocs topDocs= query(qinput,10);
//        for (ScoreDoc sd : topDocs.scoreDocs) {
//            Document doc = searcher.doc(sd.doc);
//            String id = doc.get("id");
//            String text = doc.get("text");
//            System.out.println("id: " + id + "\ntext: " + text);
//        }


    }
    /**
     * Function: queryWithCustomScore
     * Desc: Queries Lucene paragraph corpus using a custom similarity function.
     *       Note that this uses the StandardAnalyzer.
     * @param queryString: The query string that will be turned into a boolean query.
     * @param nResults: How many search results should be returned
     * @return TopDocs (ranked results matching query)
     */
    public TopDocs queryWithCustomScore(String queryString, Integer nResults) {
        Query q = SearchUtils.createStandardBooleanQuery(queryString, "text");
        IndexSearcher customSearcher = new IndexSearcher(searcher.getIndexReader());

        // Declares a custom similarity function for use with a new IndexSearcher
        SimilarityBase similarity = new SimilarityBase() {
            @Override
            protected float score(BasicStats basicStats, float freq, float docLen) {

                // Needs to be filled out
                return 0;
            }

            @Override
            public String toString() {
                return null;
            }
        };

        customSearcher.setSimilarity(similarity);

        try {
            return customSearcher.search(q, nResults);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
