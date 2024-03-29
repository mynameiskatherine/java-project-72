package hexlet.code.controller;

import hexlet.code.dto.url.UrlPage;
import hexlet.code.dto.url.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class UrlController {
    public static void index(Context ctx) {
        var urlsList = UrlRepository.getEntities();
        UrlsPage page = new UrlsPage(urlsList);
        page.setFlashMessage(ctx.consumeSessionAttribute("flash-message"));
        page.setFlashType(ctx.consumeSessionAttribute("flash-type"));
        ctx.render("urls/index.jte", Collections.singletonMap("pageData", page));
    }

    public static void create(Context ctx) {
        try {
            String url = ctx.formParam("url");
            assert url != null;
            URL formatedUrl = new URI(url).toURL();
            String protocol = formatedUrl.getProtocol();
            String hostPort = formatedUrl.getAuthority();
            assert protocol != null && hostPort != null;
            String urlToSave = protocol + "://" + hostPort;
            if (!UrlRepository.search(urlToSave).isPresent()) {
                Url urlRecord = new Url(urlToSave, LocalDateTime.now());
                UrlRepository.save(urlRecord);
                ctx.sessionAttribute("flash-message", "New record successfully added!");
                ctx.sessionAttribute("flash-type", "success");
                ctx.redirect(NamedRoutes.urlsPath());
            } else {
                ctx.sessionAttribute("flash-message", "Record already exists");
                ctx.sessionAttribute("flash-type", "warning");
                ctx.redirect(NamedRoutes.urlsPath());
            }
        } catch (Exception e) {
            ctx.sessionAttribute("flash-message", "URL is incorrect. Please follow the example.");
            ctx.sessionAttribute("flash-type", "danger");
            ctx.redirect(NamedRoutes.rootPath());
        }
    }

    public static void show(Context ctx) {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        Url url = UrlRepository.find(id).orElseThrow(() -> new NotFoundResponse("No such id found"));
        List<UrlCheck> checksList = UrlCheckRepository.getEntitiesByUrlId(id);
        UrlPage page = new UrlPage(url, checksList);
        page.setFlashMessage(ctx.consumeSessionAttribute("flash-message"));
        page.setFlashType(ctx.consumeSessionAttribute("flash-type"));
        ctx.render("urls/show.jte", Collections.singletonMap("pageData", page));
    }
}
