package jp.ken.jdbc.presentation.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InformationController {

    @GetMapping("/employee/info")
    public String info(Model model) {

        // 商品レンタルランキング（ダミー）
        List<Map<String, Object>> rental = new ArrayList<>();
        rental.add(Map.of("rank", 1, "name", "商品A", "count", 120));
        rental.add(Map.of("rank", 2, "name", "商品B", "count", 110));

        // アーティスト人気ランキング（ダミー）
        List<Map<String, Object>> artist = new ArrayList<>();
        artist.add(Map.of("rank", 1, "name", "Artist X", "count", 98));

        // 映画監督人気ランキング（ダミー）
        List<Map<String, Object>> director = new ArrayList<>();
        director.add(Map.of("rank", 1, "name", "Director Y", "count", 76));

        // トレンド情報（ダミー）
        List<Map<String, Object>> trend = new ArrayList<>();
        trend.add(Map.of("title", "作品A", "views", 1200, "rentals", 300, "growth", "+15%"));

        model.addAttribute("rentalRanking", rental);
        model.addAttribute("artistRanking", artist);
        model.addAttribute("directorRanking", director);
        model.addAttribute("trendList", trend);

        return "infomanagement";
    }
}
