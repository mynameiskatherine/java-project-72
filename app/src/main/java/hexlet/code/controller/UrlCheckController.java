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

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlCheckController {

    public static void create(Context ctx) {
        Long urlId = ctx.pathParamAsClass("id", Long.class).get();
        Url url = UrlRepository.find(urlId).orElseThrow(() -> new NotFoundResponse("No such id found"));

        try {
            HttpResponse<String> response = Unirest.get(url.getName()).asString();
            int responseStatus = response.getStatus();
            String responseBody = response.getBody();
            String title = parseBody(responseBody, "(?<=<title>).*(?=</title>)");
            String description = parseBody(responseBody, "(?<=<meta name=\"description\" content=\").*(?=\">)");
            String h1 = parseBody(responseBody, "(?<=>)(?<=>).*(?=</h1>)");
            UrlCheck urlCheck = new UrlCheck(urlId, responseStatus, title, h1, description, LocalDateTime.now());
            UrlCheckRepository.save(urlCheck);
            ctx.sessionAttribute("flash-message", "Site successfully checked!");
            ctx.sessionAttribute("flash-type", "success");
            ctx.redirect(NamedRoutes.urlPath(urlId));
        } catch (Exception e) {
            ctx.sessionAttribute("flash-message", "URL is incorrect. Please try with another one.");
            ctx.sessionAttribute("flash-type", "danger");
            ctx.redirect(NamedRoutes.urlPath(urlId));
        }
    }

    private static String parseBody(String responseBody, String regexp) {
        Pattern patternTitle = Pattern.compile(regexp);
        Matcher matcher = patternTitle.matcher(responseBody);
        String result = "";
        if (matcher.find()) {
            result = matcher.group();
        }
        return result;
    }
}
