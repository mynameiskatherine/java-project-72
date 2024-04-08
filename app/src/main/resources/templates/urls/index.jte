@import hexlet.code.dto.url.UrlsPage
@import hexlet.code.model.Url
@import hexlet.code.model.UrlCheck
@import hexlet.code.util.NamedRoutes
@import java.time.format.DateTimeFormatter
@param UrlsPage pageData

@template.layout.page(
header = @`
    <div class="navbar-nav">
        <a class="nav-link" href="${NamedRoutes.rootPath()}">Home</a>
        <a class="nav-link active" aria-current="page" href="${NamedRoutes.urlsPath()}">Sites</a>
    </div>
`,
    main = @`
    <div class="container-fluid pb-5 pt-3 px-3">
        @if(pageData.getFlashMessage() != null && pageData.getFlashType() != null)
            @if(pageData.getFlashType().equals("success"))
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    ${pageData.getFlashMessage()}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            @elseif(pageData.getFlashType().equals("warning"))
                <div class="alert alert-warning alert-dismissible fade show" role="alert">
                    ${pageData.getFlashMessage()}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            @elseif(pageData.getFlashType().equals("danger"))
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    ${pageData.getFlashMessage()}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            @endif
        @endif
        <div class="row justify-content-center align-items-center overflow-x-scroll overflow-y-auto">
            <div class="col col-lg-8 col-md-10 col-sm-auto">
                <h1 class="pt-5">Sites</h1>
                <table class="table table-responsive-sm table-striped table-hover">
                    <thead>
                        <tr>
                            <th scope="col">id</th>
                            <th scope="col">url</th>
                            <th scope="col">last check</th>
                            <th scope="col">response code</th>
                        </tr>
                    </thead>
                    <tbody>
                    @if (pageData.getUrlList().isEmpty())
                        <tr>
                            <td colspan="4">No records found</td>
                        </tr>
                    @else
                        @for (Url url : pageData.getUrlList())
                        <tr>
                            <td>${url.getId()}</td>
                            <td><a href="${NamedRoutes.urlPath(url.getId())}">${url.getName()}</a></td>
                            @if (pageData.getUrlLastChecksMap().get(url.getId()) != null)
                                <td>${pageData.getUrlLastChecksMap().get(url.getId()).getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))}</td>
                                <td>${pageData.getUrlLastChecksMap().get(url.getId()).getStatusCode()}</td>
                            @else
                                <td></td>
                                <td></td>
                            @endif
                        </tr>
                        @endfor
                    @endif
                        </tbody>
                </table>
            </div>
        </div>
    </div>
`
)