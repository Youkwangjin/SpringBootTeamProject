document.addEventListener("DOMContentLoaded", function () {
    tinymceInit();

    function tinymceInit() {
        tinymce.init({
            selector: 'textarea',
            height: 500,
            width: 760,
            plugins: "image imagetools table textcolor",
            toolbar: [
                "undo redo | code | styleselect | bold italic | fontsizeselect forecolor backcolor",
                "alignleft aligncenter alignright alignjustify | outdent indent | table | custom_image"
            ],
            convert_urls: false,
            menubar: false,
            setup: function (editor) {
                editor.ui.registry.addButton("custom_image", {
                    icon: "image",
                    tooltip: "이미지 업로드",
                    onAction: function () {
                        let input = document.createElement("input");
                        input.setAttribute("type", "file");
                        input.onchange = function () {
                            let file = input.files[0];
                            let formData = new FormData();
                            formData.append("file", file)

                            const csrfTokenElement = document.querySelector('input[name="_csrf"]');
                            const csrfToken = csrfTokenElement ? csrfTokenElement.value : null;

                            fetch("/api/editor/image/upload", {
                                method: "POST",
                                headers: {
                                    'X-CSRF-TOKEN': csrfToken,
                                },
                                body: formData
                            })
                                .then(response => response.json())
                                .then(data => {
                                    let img = "<img src='" + data.data + "' alt='' />";
                                    editor.insertContent(img);
                                })
                                .catch(error => console.error('Error:', error));
                        };
                        input.click();
                    }
                });
            }
        });
    }
});
