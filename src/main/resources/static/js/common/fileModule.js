let fileModule = (function () {
    let Util = {
        isFileInput: function (elem) {
            return elem instanceof HTMLInputElement && elem.type === "file";
        }
    };

    let FileModule = function (elem) {
        if (!Util.isFileInput(elem)) throw new Error("not file element.");
        this.elem = elem;
        this.fileList = [];
        this.fileNames = {};

        this.elem.addEventListener('change', (event) => {
            this.handleFiles(this.elem.files);
            this.elem.value = '';
            event.preventDefault();
        });

        const fileChoiceButton = document.getElementById('fileChoiceBtn');
        if (fileChoiceButton) {
            fileChoiceButton.addEventListener('click', () => {
                this.elem.click();
            });
        }

        this.displayFiles = this.displayFiles.bind(this);
        this.removeFile = this.removeFile.bind(this);
        this.handleFiles = this.handleFiles.bind(this);
    }

    FileModule.prototype.handleFiles = function (files) {
        let duplicateFiles = [];
        Array.from(files).forEach(file => {
            if (this.fileNames[file.name]) {
                duplicateFiles.push(file.name);
            } else {
                this.fileList.push(file);
                this.fileNames[file.name] = true;
            }
        });
        if (duplicateFiles.length > 0) {
            alert("중복된 파일은 업로드 할 수 없습니다");
        }
        if (this.fileList.length > 5) {
            alert("파일은 최대 5개까지 업로드할 수 있습니다.");
            this.fileList = this.fileList.slice(0, 5);
        }
        this.displayFiles();
    };

    FileModule.prototype.displayFiles = function() {
        const fileListArea = document.getElementById('fileListArea');
        fileListArea.innerHTML = '';
        this.fileList.forEach(file => {
            const fileEntry = document.createElement('div');
            fileEntry.classList.add('file-entry');

            const fileNameSpan = document.createElement('span');
            fileNameSpan.textContent = file.name;
            fileEntry.appendChild(fileNameSpan);

            const deleteButton = document.createElement('button');
            deleteButton.textContent = '×';
            deleteButton.classList.add('delete-btn');
            deleteButton.addEventListener('click', (event) => {
                event.preventDefault();
                this.removeFile(file.name);
            });
            fileEntry.appendChild(deleteButton);
            fileListArea.appendChild(fileEntry);
        });
    };

    FileModule.prototype.removeFile = function (fileName) {
        this.fileList = this.fileList.filter(file => file.name !== fileName);
        delete this.fileNames[fileName];
        this.displayFiles();
    };

    FileModule.prototype.getFiles = function () {
        return this.fileList;
    };

    return {
        init: function (elem) {
            return new FileModule(elem);
        }
    };
})();
