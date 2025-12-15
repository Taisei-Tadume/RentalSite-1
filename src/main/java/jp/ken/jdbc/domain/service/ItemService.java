package jp.ken.jdbc.domain.service;

import java.util.List;

import jp.ken.jdbc.domain.model.Item;

public interface ItemService {

	List<Item> findNewItems();

}
