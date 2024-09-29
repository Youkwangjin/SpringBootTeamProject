document.addEventListener("DOMContentLoaded", function () {
    tinymceInit();

    function tinymceInit() {
        tinymce.init({
            selector: 'textarea',
            height: 500,
            width: 760,
            plugins: [
                'lists',
                'image',
                'charmap',
                'preview',
                'searchreplace',
                'fullscreen',
                'table',
                'quickbars',
            ],
            toolbar:
                'undo redo blocks bold italic forecolor alignleft aligncenter' +
                'alignright alignjustify bullist numlist outdent indent lists' +
                'fullscreen preview',
            menubar: false,
        });
    }
});
