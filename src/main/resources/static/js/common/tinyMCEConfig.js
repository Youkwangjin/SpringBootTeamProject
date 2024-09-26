document.addEventListener("DOMContentLoaded", function () {
    tinymceInit();

    function tinymceInit() {
        tinymce.init({
            selector: 'textarea',
            height: 500,
            width: 760,
            plugins: 'image',
            menubar: 'format',
            toolbar: 'undo redo | link image',
            image_title: true,
            automatic_uploads: true,
            file_picker_types: 'image',
            file_picker_callback: function (cb, value, meta) {
                createFileInput(cb);
            },
            content_style: 'body { font-family:Helvetica,Arial,sans-serif; font-size:16px }'
        });
    }

    // 이미지 파일 선택 input 요소 생성
    function createFileInput(cb) {
        const input = document.createElement('input');
        input.setAttribute('accept', 'image/*');
        input.setAttribute('type', 'file');

        input.onchange = function (e) {
            const file = e.target.files[0];
            handleFileSelection(file, cb);
        };

        input.click();
    }

    function handleFileSelection(file, cb) {
        const reader = new FileReader();

        reader.onload = function () {
            const blobCache = tinymce.activeEditor.editorUpload.blobCache;
            const id = 'blobid' + (new Date()).getTime();
            const blobInfo = blobCache.create(id, file, reader.result);
            blobCache.add(blobInfo);

            // 서버에 이미지 파일 업로드
            uploadFileToServer(file, function (url) {
                const img = new Image();
                img.src = url;
                img.onload = function () {
                    cb(url, {
                        title: file.name,
                        width: img.width,
                        height: img.height
                    });
                };
            }, function (error) {
                cb(null, {error: error});
            });
        };

        reader.readAsDataURL(file);
    }

    // 이미지 파일 업로드 처리
    function uploadFileToServer(file, successCallback, errorCallback) {
        const xhr = new XMLHttpRequest();
        const uploadUrl = '/api/images/upload';

        xhr.open('POST', uploadUrl);
        xhr.onload = function () {
            if (xhr.status === 200) {
                const json = JSON.parse(xhr.responseText);
                if (json.url) {
                    successCallback(json.url);
                } else {
                    errorCallback(json.error);
                }
            } else {
                errorCallback('HTTP Error: ' + xhr.status);
            }
        };

        xhr.onerror = function () {
            errorCallback('XHR Error: Could not upload file.');
        };

        const formData = new FormData();
        formData.append('file', file, file.name);
        xhr.send(formData);
    }
});
