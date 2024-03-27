package hexlet.code.controller;

import hexlet.code.dto.RootPage;
import io.javalin.http.Context;

import java.util.Collections;

public class RootController {
    public static void index(Context ctx) {
        RootPage page = new RootPage();
        page.setFlashMessage(ctx.consumeSessionAttribute("flash-message"));
        page.setFlashType(ctx.consumeSessionAttribute("flash-type"));
        ctx.render("index.jte", Collections.singletonMap("pageData", page));
    }
}
