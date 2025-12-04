package jp.ken.jdbc.presentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.ken.jdbc.application.service.GoodsService;
import jp.ken.jdbc.domain.entity.GenreEntity;
import jp.ken.jdbc.domain.entity.GoodsEntity;
import jp.ken.jdbc.presentation.form.SearchForm;

@Controller
@SessionAttributes("searchForm")
public class SearchController {

	@Autowired
	private GoodsService goodsService;
	
	@ModelAttribute("searchForm")
	public SearchForm setUpForm() {
		return new SearchForm();
	}
	
	@ModelAttribute("genres")
	public List<GenreEntity> getAllGenres(){
		return goodsService.getAllGenres();
	}
	
    @GetMapping("/search")
    public String search(@ModelAttribute("searchForm") SearchForm searchForm,
    		@RequestParam(value = "page", defaultValue = "0") int page, Model model) {
    	
    	int pageSize = 8;
    	
    	int genreId = searchForm.getGenreId();
    	
    	List<GoodsEntity> resultList = goodsService.searchGoods(genreId, page, pageSize);
    	
    	long totalItems = goodsService.countGoodsByGenre(genreId);
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);
        
        model.addAttribute("resultList", resultList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("selectedGenre", genreId);
        
        return "searchresult";
    }
}
