package hexlet.code.dto;

import hexlet.code.model.Url;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hexlet.code.model.UrlCheck;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UrlPage extends BasePage{

    private Url url;

    private List<Url> urls = new ArrayList<>();

    private List<UrlCheck> urlsCheck = new ArrayList<>();

    private Map<Long, UrlCheck> lastChecks = new HashMap<>();

    public UrlPage() {
    }

    public void addUrl(Url url) {
        urls.add(url);
    }

    public void addUrlCheck(UrlCheck urlCheck) {
        this.urlsCheck.add(urlCheck);
    }

}
