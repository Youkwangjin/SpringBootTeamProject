document.addEventListener("DOMContentLoaded", function () {
    tinymceInit();

    function tinymceInit() {
        tinymce.init({
            selector: 'textarea',
            height: 500,
            width: 760,
            plugins: "image table textcolor",
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
                                    "X-CSRF-TOKEN": csrfToken,
                                },
                                body: formData
                            })
                                .then(async response => {
                                    const responseData = await response.json();

                                    if (response.status === 200) {
                                        let img = "<img src='" + responseData.data + "' alt='' />";
                                        editor.insertContent(img);
                                    } else {
                                        alert(responseData.errorMsg);
                                    }
                                })
                                .catch(() => {
                                    alert(MSG.ERROR);
                                });
                        };
                        input.click();
                    }
                });
            }
        });
    }
});