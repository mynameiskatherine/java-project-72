@import hexlet.code.util.NamedRoutes
@import hexlet.code.dto.RootPage
@param RootPage pageData

@template.layout.page(
    header = @`
    <div class="navbar-nav">
        <a class="nav-link active" aria-current="page" href="/">Home</a>
        <a class="nav-link" href="${NamedRoutes.urlsPath()}">Sites</a>
    </div>
    `,
    main = @`
    <div class="container-fluid bg-dark pb-5 pt-3 px-3">
        @if(pageData.getFlashMessage() != null && pageData.getFlashType() != null)
            @if(pageData.getFlashType().equals("success"))
                <div id="alertOnAddingUrl" class="alert alert-success alert-dismissible fade show" role="alert">
                    ${pageData.getFlashMessage()}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            @elseif(pageData.getFlashType().equals("warning"))
                <div id="alertOnAddingUrl" class="alert alert-warning alert-dismissible fade show" role="alert">
                    ${pageData.getFlashMessage()}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            @elseif(pageData.getFlashType().equals("danger"))
                <div id="alertOnAddingUrl" class="alert alert-danger alert-dismissible fade show" role="alert">
                    ${pageData.getFlashMessage()}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            @endif
        @endif
        <div class="row justify-content-center align-items-center overflow-x-scroll overflow-y-auto">
            <div class="col col-lg-8 col-md-10 col-sm-auto text-white">
                <h1 class="pt-5">Page Analyzer</h1>
                <p>Check your website for SEO for free</p>
                <form class="row text-body" action="${NamedRoutes.urlsPath()}" method="post">
                    <div class="col">
                        <div class="form-floating w-100">
                            <input type="text" class="form-control" id="floatingInputUrl"
                                   placeholder="For example: https://www.example.ru" name="url" required>
                            <label for="floatingInputUrl">URL</label>
                        </div>
                    </div>
                    <div class="col-auto">
                        <button type="submit" class="h-100 btn btn-primary btn-lg">Submit</button>
                    </div>
                </form>
                <p class="text-white-50"><small>For example: https://www.example.ru</small></p>
            </div>
        </div>
    </div>
`
)