package com.zhaole.service.impl;


import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Service
public class SensitiveService implements InitializingBean
{
    private static final Logger logger = LoggerFactory.getLogger(SensitiveService.class);
    private static final String DEFUALT_REPLACEMENT = "**";

    private class TrieNode
    {
        //true：关键词终结
        private boolean end = false;

        //key:下一个字符，value:对应的节点
        private Map<Character,TrieNode> subNodes = new HashMap<>();

        //向指定位置添加节点树
        void addSubNode(Character key, TrieNode node)
        {
            subNodes.put(key,node);
        }

        //获取下一个节点
        TrieNode getSubNode(Character key)
        {
            return subNodes.get(key);
        }

        boolean isKeywordEnd()
        {
            return end;
        }

        void setKeywordEnd(boolean end)
        {
            this.end = end;
        }

        public int getSubNodeCount()
        {
            return subNodes.size();
        }
    }

    private TrieNode root = new TrieNode();

    private boolean isSymbol(char c)
    {
        int intc = (int)c;
        //东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (intc<0x2E80||intc>0x9FFF);
    }

    public String filter(String text)
    {
        if(StringUtils.isBlank(text))
        {
            return text;
        }
        String replacement = DEFUALT_REPLACEMENT;
        StringBuilder result = new StringBuilder();
        TrieNode temp = root;
        int begin = 0;//回滚数
        int position = 0;//当前比较位置

            /*    ↓position
                  哈哈赌就博啊啊;
                  ↑
                     root     (tmp)
                    / |  \
                  a   b   w
                 /\  /\   /\
                b d a  f  o s
             */
        while(position < text.length())
        {
            char c = text.charAt(position);
            if(isSymbol(c))//符号直接跳过
            {
                if(temp == root)
                {
                    result.append(c);
                    begin++;
                }
                position++;
                continue;
            }
            temp = temp.getSubNode(c);//获取root的以赌为key的value--即node1
            if(temp == null)
            {
                //以begin开头的字符串不存在敏感词
                result.append(text.charAt(begin));
                position = begin+1;
                begin = position;
                temp = root;
            }
            else if(temp.isKeywordEnd())
            {
                result.append(replacement);
                position = position+1;
                begin = position;
                temp = root;
            }
            else
            {
                position++;
            }
        }
        result.append(text.substring(begin));//从这里开始到末尾的子串
        return result.toString();
    }

    private void addWord(String lineTxt)//某一行的文字
    {
        TrieNode temp = root;
        for(int i = 0;i < lineTxt.length();++i)
        {
            Character c = lineTxt.charAt(i);
            if(isSymbol(c)){
                continue;
            }
            TrieNode node = temp.getSubNode(c);//得到以赌为key的一个trienode；；；以博为key
            if(node == null)
            {
                node = new TrieNode();
                temp.addSubNode(c,node);//root拥有了一个map《赌，node1》；；；上一个node1拥有了一个map《博，node2》
            }
            temp = node;//tmp=这个新的子节点node1
            if(i == lineTxt.length()-1)
            {
                temp.setKeywordEnd(true);
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception
    {
        root = new TrieNode();
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
            InputStreamReader read = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt;
            while((lineTxt = bufferedReader.readLine())!=null)
            {
                lineTxt = lineTxt.trim();//去掉两端的空格
                addWord(lineTxt);
            }
            read.close();
        }catch (Exception e){
            logger.error("读取敏感词文件失败"+e.getMessage());
        }
    }
}
