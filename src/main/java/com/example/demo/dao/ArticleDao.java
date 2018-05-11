package com.example.demo.dao;

import com.example.demo.bean.Article;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2018/5/11 0011.
 */
public interface ArticleDao extends JpaRepository<Article,Integer> {

}
