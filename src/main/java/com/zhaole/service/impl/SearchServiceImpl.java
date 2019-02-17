package com.zhaole.service.impl;

import com.zhaole.model.Question;
import com.zhaole.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * created by zl on 2019/2/17 16:08
 */
@Service
public class SearchServiceImpl implements SearchService
{
    private static final String SOLR_URL = "http://192.168.7.125:8983/solr/wenda";
    private HttpSolrClient client = new HttpSolrClient.Builder(SOLR_URL).build();
    private static final String QUESTION_TITLE_FIELD = "question_title";
    private static final String QUESTION_CONTENT_FIELD = "question_content";

    /**
     *
     * @param keyword 搜索的关键词
     * @param offset 翻页
     * @param count 翻页
     * @param hlPre  高亮的前缀
     * @param hlPos 高亮的后缀
     * @return
     * 搜索
     */
    public List<Question> searchQuestion(String keyword, int offset, int count,String hlPre, String hlPos) throws Exception
    {
        List<Question> questionList = new ArrayList<>();
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setRows(count);
        solrQuery.setStart(offset);
        solrQuery.setHighlight(true);
        solrQuery.setHighlightSimplePre(hlPre);
        solrQuery.setHighlightSimplePost(hlPos);
        solrQuery.set("hl.fl", QUESTION_CONTENT_FIELD + "," + QUESTION_TITLE_FIELD);
        QueryResponse queryResponse = client.query(solrQuery);
        //解析搜索界面
        for(Map.Entry<String, Map<String, List<String>>> entry : queryResponse.getHighlighting().entrySet())
        {
            Question question = new Question();
            question.setId(Integer.parseInt(entry.getKey()));//string
            if(entry.getValue().containsKey(QUESTION_CONTENT_FIELD))
            {
                List<String> contentList = entry.getValue().get(QUESTION_CONTENT_FIELD);//Map<String, List<String>>
                if(contentList.size() > 0)
                {
                    question.setContent(contentList.get(0));
                }
            }

            if(entry.getValue().containsKey(QUESTION_TITLE_FIELD))
            {
                List<String> titleList = entry.getValue().get(QUESTION_TITLE_FIELD);
                if(titleList.size() > 0)
                {
                    question.setTitle(titleList.get(0));
                }
            }
            questionList.add(question);
        }
        return questionList;
    }

    /**
     * 索引
     * @param qid
     * @param title
     * @param content
     * @return
     */
    public boolean indexQuestion(int qid, String title, String content) throws Exception
    {
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id",qid);
        document.setField(QUESTION_TITLE_FIELD,title);
        document.setField(QUESTION_CONTENT_FIELD,content);

        UpdateResponse response = client.add(document, 1000);
        return response != null && response.getStatus() == 0;
    }
}
