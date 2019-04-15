var filelist = [];  // Ein Array, das alle hochzuladenden Files enthält
var totalSize = 0; // Enthält die Gesamtgröße aller hochzuladenden Dateien

function main() {

    const dragzone = document.getElementById("dragzone");
    dragzone.addEventListener('drop', handleDropEvent, false);
    dragzone.addEventListener('dragover', function (ev) {
        ev.preventDefault();
    }, false);

    loadSaveImages()
}

function loadSaveImages() {

    fetch('getSavedImages')
        .then(async function (response) {
            const responseJson = await response.json();
            showSavedImages(responseJson.images);
        })
        .then(function (myJson) {
            console.log(JSON.stringify(myJson));
        });

    showSavedImages([]);
}

function showSavedImages(images) {

    const overviewContainer = document.getElementById("overviewContainer");

    for (let i = 0; i < images.length; i++) {

        const imageContainer = document.createElement("div");
        imageContainer.classList.add("imageContainer");
        overviewContainer.appendChild(imageContainer);

        const imageCaption = document.createElement("h2");
        imageCaption.textContent = images[i].caption;
        imageContainer.appendChild(imageCaption);

        const imageTag = new Image();
        imageTag.src = images[i].link;
        imageContainer.appendChild(imageTag);

        const imageDesc = document.createElement("span");
        imageDesc.textContent = images[i].description;
        imageContainer.appendChild(imageDesc);
    }
}

function uploadNewImage() {

    if (filelist.length === 0) {
        return;
    }

    const imageCaption = document.getElementById("caption").value;
    const imageDesc = document.getElementById("description").value;

    const formData = new FormData();
    formData.append('imageData', filelist[0]);
    formData.append('imageCaption', imageCaption);
    formData.append('imageDesc', imageDesc);

    let requestBody = {
        method: "POST",
        body: formData
    };

    fetch('saveNewImage', requestBody)
        .then(function (response) {
            location.reload();
        })
        .then(function (myJson) {
            console.log(JSON.stringify(myJson));
        });
}

function getBase64(file) {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => resolve(reader.result);
        reader.onerror = error => reject(error);
    });
}

function handleDropEvent(event) {
    event.stopPropagation();
    event.preventDefault();

    // event.dataTransfer.files enthält eine Liste aller gedroppten Dateien
    for (var i = 0; i < event.dataTransfer.files.length; i++) {
        filelist.push(event.dataTransfer.files[i]);  // Hinzufügen der Datei zur Uploadqueue
        totalSize += event.dataTransfer.files[i].size;  // Hinzufügen der Dateigröße zur Gesamtgröße
    }

    getBase64(filelist[0]).then(function (data) {
        let img = new Image();
        img.src = data;
        img.classList.add("toUploadImage");
        document.getElementById("dragzone").textContent = "";
        document.getElementById("dragzone").appendChild(img);
    });
}

