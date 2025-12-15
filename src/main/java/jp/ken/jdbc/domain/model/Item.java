package jp.ken.jdbc.domain.model;

import lombok.Data;

@Data
public class Item {
    private int id;          // goods_id
    private String title;    // goods_name
    private String image;    // image_url
}