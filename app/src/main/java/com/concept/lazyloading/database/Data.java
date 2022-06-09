package com.concept.lazyloading.database;

public class Data {
    int id;
    String username;
    String age;
    String movie_name;
    String genre;
    String like_anime;

    public Data(int id, String username, String age, String movie_name, String genre, String like_anime) {
        this.id = id;
        this.username = username;
        this.age = age;
        this.movie_name = movie_name;
        this.genre = genre;
        this.like_anime = like_anime;
    }

    public Data(String username, String age, String movie_name, String genre, String like_anime) {
        this.username = username;
        this.age = age;
        this.movie_name = movie_name;
        this.genre = genre;
        this.like_anime = like_anime;
    }

    public Data() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLike_anime() {
        return like_anime;
    }

    public void setLike_anime(String like_anime) {
        this.like_anime = like_anime;
    }
}
