package hexlet.code.controller;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.time.LocalDateTime;

public class UrlCheckController {

    public static void create(Context ctx) {
        Long urlId = ctx.pathParamAsClass("id", Long.class).get();
        Url url = UrlRepository.find(urlId).orElseThrow(() -> new NotFoundResponse("No such id found"));

        try {
            HttpResponse<String> response = Unirest.get(url.getName()).asString();
            int responseStatus = response.getStatus();
            String responseBody = response.getBody();
            Document body = Jsoup.parse(responseBody);
            String title = body.title();
            Element h1Tag = body.selectFirst("h1");
            String h1 = (h1Tag != null) ? h1Tag.text() : "";
            Element descriptionTag = body.selectFirst("meta[name=\"description\"][content]");
            String description = (descriptionTag != null) ? descriptionTag.attr("content") : "";
            UrlCheck urlCheck = new UrlCheck(urlId, responseStatus, title, h1, description, LocalDateTime.now());
            UrlCheckRepository.save(urlCheck);
            ctx.sessionAttribute("flash-message", "Site successfully checked!");
            ctx.sessionAttribute("flash-type", "success");
            ctx.redirect(NamedRoutes.urlPath(urlId));
        } catch (Exception e) {
            ctx.sessionAttribute("flash-message", "URL is incorrect or unreachable. Please try with another one.");
            ctx.sessionAttribute("flash-type", "danger");
            ctx.redirect(NamedRoutes.urlPath(urlId));
        }
    }
}
