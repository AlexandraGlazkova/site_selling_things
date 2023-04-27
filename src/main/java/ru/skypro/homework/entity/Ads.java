package ru.skypro.homework.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;
@Entity
public class Ads {
    /**
     * поле - id автора объявления
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer author;
    /**
     * поле - ссылка на картинку объявления
     */
    private String image;
    /**
     * поле - id объявления
     */
    private Integer pk;
    /**
     * поле - цена объявления
     */
    private Integer price;
    /**
     * поле - заголовок объявления
     */
    private String title;

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ads ads = (Ads) o;
        return Objects.equals(author, ads.author) && Objects.equals(image, ads.image) && Objects.equals(pk, ads.pk) && Objects.equals(price, ads.price) && Objects.equals(title, ads.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, image, pk, price, title);
    }

    @Override
    public String toString() {
        return "Ads{" +
                "author=" + author +
                ", image='" + image + '\'' +
                ", pk=" + pk +
                ", price=" + price +
                ", title='" + title + '\'' +
                '}';
    }
}
