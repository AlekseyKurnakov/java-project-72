package hexlet.code.controller;

import hexlet.code.dto.UrlPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URI;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
            Optional<Url> UrlFromDatabase = UrlRepository.findByUrl(preparedUrl);
            if (!UrlFromDatabase.isEmpty()) {
                ctx.sessionAttribute("flash", "Страница уже существует");
                ctx.sessionAttribute("flashType","exists");
                ctx.redirect("/urls/" + UrlFromDatabase.get().getId());
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
            page.setFlashType("danger");

            ctx.status(422);
            ctx.render("articles/index.jte", Map.of("page", page));

        }

    }

    public static void show(Context ctx) throws SQLException {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        Url url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
        String flash = ctx.consumeSessionAttribute("flash");
        String flashType = ctx.consumeSessionAttribute("flashType");

        UrlPage page = new UrlPage();
        page.setUrl(url);
        page.setFlash(flash);
        page.setFlashType(flashType);
        ctx.render("urls/show.jte", Map.of("page", page));
    }

    public static void index(Context ctx) throws SQLException {
        List<Url> urls = UrlRepository.getEntities();
        UrlPage page = new UrlPage();
        page.setUrls(urls);
        ctx.render("urls/index.jte", Map.of("page", page));
    }
}
