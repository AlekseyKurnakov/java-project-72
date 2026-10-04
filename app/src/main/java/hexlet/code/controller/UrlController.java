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

import java.net.URL;
import java.net.URI;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import kong.unirest.core.UnirestException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class UrlController {

    public static void home(Context ctx) {
        ctx.render("articles/index.jte");
    }

    public static void create(Context ctx) throws Exception {
        String inputUrl = ctx.formParam("url");

        URL url;
        try {
            URI parsedUrl = new URI(inputUrl);
            url = parsedUrl.toURL();
        } catch (Exception e) {

            UrlPage page = new UrlPage();
            page.setFlash("Некорректный URL");
            page.setFlashType("failure");

            ctx.status(422);
            ctx.render("articles/index.jte", Map.of("page", page));
            return;
        }

        String preparedUrl = url.getProtocol() + "://" + url.getHost();
        if (url.getPort() != -1) {
            preparedUrl += ":" + url.getPort();
        }
        Optional<Url> urlFromDatabase = UrlRepository.findByUrl(preparedUrl);
        if (!urlFromDatabase.isEmpty()) {
            ctx.sessionAttribute("flash", "Страница уже существует");
            ctx.sessionAttribute("flashType", "success");
            ctx.redirect("/urls/" + urlFromDatabase.get().getId());
        } else {
            Url newUrl = new Url(preparedUrl);
            UrlRepository.save(newUrl);
            ctx.sessionAttribute("flash", "Страница успешно добавлена");
            ctx.sessionAttribute("flashType", "success");
            ctx.redirect("/urls/" + newUrl.getId());
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
        Map<Long, UrlCheck> lastChecks = UrlChekRepository.getAllLastChecks();

        UrlPage page = new UrlPage();
        page.setUrls(urls);
        page.setLastChecks(lastChecks);
        ctx.render("urls/index.jte", Map.of("page", page));
    }

    public static void createCheck(Context ctx) throws SQLException {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        Url url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));

        HttpResponse<String> response;
        Document doc;

        try {
            response = Unirest.get(url.getName()).asString();
            doc = Jsoup.parse(response.getBody());
        } catch (UnirestException e) {
            ctx.sessionAttribute("flash", "Не удалось подключиться к сайту");
            ctx.sessionAttribute("flashType","failure");
            ctx.redirect(NamedRoutes.urlPath(id));
            return;
        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Не удалось обработать содержимое страницы");
            ctx.sessionAttribute("flashType","failure");
            ctx.redirect(NamedRoutes.urlPath(id));
            return;
        }


        int statusCode = response.getStatus();

        if (statusCode >= 400) {
            ctx.sessionAttribute("flash", "Произошла ошибка при проверке");
            ctx.sessionAttribute("flashType","failure");
        } else {
            String title = doc.title();
            Element h1Element = doc.selectFirst("h1");
            String h1 = h1Element != null ? h1Element.text() : null;
            Element descriptionElement = doc.selectFirst("meta[name=description]");
            String description = descriptionElement != null ? descriptionElement.attr("content") : null;

            UrlCheck urlCheck = new UrlCheck(id, statusCode, h1, title, description);

            UrlChekRepository.save(urlCheck);

            ctx.sessionAttribute("flash", "Страница успешно проверена");
            ctx.sessionAttribute("flashType","success");
        }




        ctx.redirect(NamedRoutes.urlPath(id));
    }
}
