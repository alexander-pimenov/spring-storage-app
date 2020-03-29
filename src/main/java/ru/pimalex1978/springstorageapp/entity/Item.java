package ru.pimalex1978.springstorageapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Item")
public class Item {
    @Id
    private Integer id;

    @Column(name = "contained_in")
    private Integer parentId;

    @Column(name = "color")
    private String color;

    public Item() {
    }

    public Item(Integer id, Integer parentId, String color) {
        this.id = id;
        this.parentId = parentId;
        this.color = color;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return String.format("Item id=%d, parentId=%d, color=%s",
                id, parentId, color);
    }
}
