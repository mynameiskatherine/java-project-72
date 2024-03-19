@template.layout.page(
    main = @`
    <div class="container-fluid bg-dark py-5 px-3">
            <div class="row justify-content-center align-items-center overflow-x-scroll overflow-y-auto">
                <div class="col col-lg-8 col-md-10 col-sm-auto text-white">
                    <h1>Page Analyzer</h1>
                    <p>Check your website for SEO for free</p>
                    <form class="row text-body">
                        <div class="col">
                            <div class="form-floating w-100">
                                <input type="text" class="form-control" id="floatingInputUrl" placeholder="For example: https://www.example.ru">
                                <label for="floatingInputUrl">URL</label>
                            </div>
                        </div>
                        <div class="col-auto">
                            <button type="submit" class="h-100 btn btn-primary btn-lg">Submit</button>
                        </div>

                    </form>
                    <p class="text-white-50">
                        <small>
                            For example: https://www.example.ru
                        </small>
                    </p>
</div>
            </div>
    </div>
`
)