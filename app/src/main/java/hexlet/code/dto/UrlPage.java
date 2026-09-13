package hexlet.code.dto;

import hexlet.code.model.Url;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class UrlPage extends BasePage{

    private Url url;

    private List<Url> urls = new ArrayList<>();

    public UrlPage() {
    }
    public void setUrl(Url url) {
        this.url = url;
    }

    public void addUrl(Url url) {
        urls.add(url);
    }

    public void setUrls(List<Url> urls) {
        this.urls = urls;
    }
}
