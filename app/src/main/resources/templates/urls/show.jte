@import hexlet.code.dto.url.UrlPage
@import hexlet.code.model.Url
@import hexlet.code.util.NamedRoutes
@param UrlPage pageData

@template.layout.page(
header = @`
    <div class="navbar-nav">
        <a class="nav-link" href="${NamedRoutes.rootPath()}">Home</a>
        <a class="nav-link" href="${NamedRoutes.urlsPath()}">Sites</a>
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
                <h1 class="pt-5">Site: ${pageData.getUrl().getName()}</h1>
                <table class="table table-responsive-sm table-striped table-hover">
                    <tbody>
                        <tr>
                            <th scope="row">id</th>
                            <td>${pageData.getUrl().getId()}</td>
                        </tr>
                        <tr>
                            <th scope="row">url</th>
                            <td>${pageData.getUrl().getName()}</td>
                        </tr>
                        <tr>
                            <th scope="row">created at</th>
                            <td>${String.valueOf(pageData.getUrl().getCreatedAt())}</td>
                        </tr>
                    </tbody>
                </table>
                <h2 class="pt-5">Check results</h2>
                <button type="submit" class="h-100 btn btn-primary btn-lg">Start check</button>
                <table class="table table-responsive-sm table-striped table-hover">
                    <thead>
                        <tr>
                            <th scope="col">id</th>
                            <th scope="col">response code</th>
                            <th scope="col">title</th>
                            <th scope="col">h1</th>
                            <th scope="col">description</th>
                            <th scope="col">last check</th>
                        </tr>
                    </thead>
                    <tbody>
                    @if (pageData.getCheckResult().isEmpty())
                        <tr>
                            <td colspan="6">No check records found</td>
                        </tr>
                    @else
                        @for (String check : pageData.getCheckResult())
                            <tr>
                                <td>${check}</td>
                                <td>${check}</td>
                                <td>${check}</td>
                                <td>${check}</td>
                                <td>${check}</td>
                                <td>${check}</td>
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