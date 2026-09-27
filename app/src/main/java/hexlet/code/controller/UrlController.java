package hexlet.code.controller;

import hexlet.code.dto.UrlPage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlChekRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URI;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class UrlController {

    public static void home(Context ctx) {
        ctx.render("articles/index.jte");
    }

    public static void create(Context ctx) throws SQLException{
        String urlName = ctx.formParam("url");

        try {
            URI uri = URI.create(urlName);
            URL url = uri.toURL();
            String preparedUrl = url.getProtocol() + "://" + url.getHost();
            if (url.getPort() != -1) {
                preparedUrl += ":" + url.getPort();
            }
            Optional<Url> urlFromDatabase = UrlRepository.findByUrl(preparedUrl);
            if (!urlFromDatabase.isEmpty()) {
                ctx.sessionAttribute("flash", "Страница уже существует");
                ctx.sessionAttribute("flashType","exists");
                ctx.redirect("/urls/" + urlFromDatabase.get().getId());
            } else {
                Timestamp createdAt = new Timestamp(System.currentTimeMillis());
                Url newUrl = new Url(preparedUrl, createdAt);
                UrlRepository.save(newUrl);
                ctx.sessionAttribute("flash", "Страница успешно добавлена");
                ctx.sessionAttribute("flashType","success");
                ctx.redirect("/urls/" + newUrl.getId());
            }

        } catch (IllegalArgumentException | MalformedURLException e) {

            UrlPage page = new UrlPage();
            page.setFlash("Некорректный URL");
            page.setFlashType("Incorrect");

            ctx.status(422);
            ctx.render("articles/index.jte", Map.of("page", page));

        }

    }

    public static void show(Context ctx) throws SQLException {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        Url url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
        List<UrlCheck> urlsCheck = UrlChekRepository.getEntities(id);
        String flash = ctx.consumeSessionAttribute("flash");
        String flashType = ctx.consumeSessionAttribute("flashType");

        UrlPage page = new UrlPage();
        page.setUrl(url);
        page.setFlash(flash);
        page.setFlashType(flashType);
        page.setUrlsCheck(urlsCheck);
        ctx.render("urls/show.jte", Map.of("page", page));
    }

    public static void index(Context ctx) throws SQLException {
        List<Url> urls = UrlRepository.getEntities();
        Map<Long, UrlCheck> lastChecks = new HashMap<>();
        for (Url url : urls) {
            UrlChekRepository.findLast(url.getId()).ifPresent(check -> lastChecks.put(url.getId(), check));
        }

        UrlPage page = new UrlPage();
        page.setUrls(urls);
        page.setLastChecks(lastChecks);
        ctx.render("urls/index.jte", Map.of("page", page));
    }

    public static void createCheck(Context ctx) throws SQLException {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        Url url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));

        HttpResponse<String> response = Unirest.get(url.getName()).asString();

        int statusCode = response.getStatus();

        if (statusCode >= 400) {
            ctx.sessionAttribute("flash", "Произошла ошибка при проверке");
            ctx.sessionAttribute("flashType","error");
        } else {
            Document doc = Jsoup.parse(response.getBody());
            String title = doc.title();
            Element h1Element = doc.selectFirst("h1");
            String h1 = h1Element != null ? h1Element.text() : null;
            Element descriptionElement = doc.selectFirst("meta[name=description]");
            String description = descriptionElement != null ? descriptionElement.attr("content") : null;

            Timestamp createdAt = new Timestamp(System.currentTimeMillis());

            UrlCheck urlCheck = new UrlCheck(id, statusCode, h1, title, description, createdAt);

            UrlChekRepository.save(urlCheck);

            ctx.sessionAttribute("flash", "Страница успешно проверена");
            ctx.sessionAttribute("flashType","successfully");
        }



        ctx.redirect(NamedRoutes.urlPath(id));
    }
}
