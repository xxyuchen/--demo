package com.example.demo.scheduled;

import com.example.demo.bean.Article;
import com.example.demo.dao.ArticleDao;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * Created by Administrator on 2018/3/14 0014.
 */
@Component
@Slf4j
public class ATask {
    @Autowired
    private ArticleDao articleDao;

    Long timeL;

    @Scheduled(cron = "0/10 * * * * ?")
    public void scheduleTask() {
        String url = "https://www.csdn.net/nav/newarticles";
        long lastTime = 0;
        try {
            log.info("时间：【{}】",new Date());
            Document doc = Jsoup.parse(new java.net.URL(url),3000);
            log.info("页面调用成功：{}",doc);
            Elements elements = doc.select("li.clearfix");
            //Elements elements = doc.getElementsByClass("clearfix");
            int i = 1;
            String attrTime = elements.get(0).attr("shown-time");
            lastTime = Long.parseLong(attrTime);
            Article article;
            for(Element element : elements){
                String attr = element.attr("shown-time");
                long time = Long.parseLong(attr);
                log.info("li{}:{}",i,element);
                i++;
                //show-time
                if(time>timeL){
                    try {
                        Element a = element.getElementsByClass("title").get(0).getElementsByTag("a").get(0);
                        article = new Article();
                        article.setTatil(a.text());
                        article.setUrl(a.attr("href"));
                        article.setParm(element.getElementsByClass("summary").get(0).text());
                        Element author = element.getElementsByClass("name").get(0).getElementsByTag("a").get(0);
                        article.setAuthor(author.text());
                        article.setAuthorUrl(author.attr("href"));
                        article.setCreateTime(new Date());
                        articleDao.save(article);
                    }catch (Exception e){
                        log.info("解析报错：{}",e);
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            log.info("页面调用失败：{}",e);
            e.printStackTrace();
        }finally {
            timeL = lastTime;
        }
    }
}
