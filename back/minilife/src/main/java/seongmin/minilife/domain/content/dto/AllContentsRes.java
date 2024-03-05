package seongmin.minilife.domain.content.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import seongmin.minilife.domain.content.entity.Content;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class AllContentsRes {
    private List<GetContentRes> contents;

    public static AllContentsRes from(List<Content> contents) {
        AllContentsRes response = AllContentsRes.builder()
                .contents(new ArrayList<>())
                .build();

        for (Content content : contents) {
            Document doc = Jsoup.parse(content.getBody()); // html 태그 제거
            response.contents.add(GetContentRes.from(content, doc.text()));
        }

        return response;
    }
}
