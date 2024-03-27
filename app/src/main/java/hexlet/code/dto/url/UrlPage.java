package hexlet.code.dto.url;

import hexlet.code.dto.BasePage;
import hexlet.code.model.Url;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class UrlPage extends BasePage {
    private Url url;
    private List<String> checkResult = new ArrayList<>();

    public UrlPage(Url url) {
        this.url = url;
    }
}
