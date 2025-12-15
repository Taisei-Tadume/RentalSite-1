package jp.ken.jdbc.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jp.ken.jdbc.domain.model.Item;
import jp.ken.jdbc.domain.repository.ItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemServicelmpl implements ItemService {

	private final ItemRepository itemRepository;

	@Override
	public List<Item> findNewItems() {
		// ★ 新作入荷商品を取得（登録日の新しい順など）
		return itemRepository.findNewItems();
	}

}