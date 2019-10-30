package com.example.beanielibrary.model;

public class NewsModel {
    public String title;
    public String description;
    public String url;

    public NewsModel()
    {

    }

    public NewsModel(String title, String description, String url)
    {
        this.title = title;
        this.description = description;
        this.url = url;
    }

    public String getTitle()
    {
        return title;
    }

    public String getDescription()
    {
        return description;
    }

    public String getUrl()
    {
        return url;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    @Override
    public String toString()
    {
        return "Article{" +
            "title='" + title +'\'' +
            "description='" + description +'\'' +
            "url='" + url +
        '}';

    }
}
