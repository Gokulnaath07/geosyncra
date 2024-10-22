package com.teamSeven.GeoSyncra.Configs;

import jakarta.persistence.*;


@Entity
@Table(name = "images") // Specify your desired table name here
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;
    private String name;
    private String description;
    private String location;

    // Constructor
    public ImageEntity() {}

    public ImageEntity(String imageUrl, String name, String description, String location) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.description = description;
        this.location = location;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
