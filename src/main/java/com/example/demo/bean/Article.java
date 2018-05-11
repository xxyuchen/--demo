package com.example.demo.bean;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by Administrator on 2018/5/11 0011.
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
public class Article {

    @Id
    @GeneratedValue
    private Integer id;

    private String tatil;

    private String url;

    private String parm;

    private String author;

    private String authorUrl;

    private Date createTime;
}
