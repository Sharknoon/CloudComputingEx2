var filelist = [];  // Ein Array, das alle hochzuladenden Files enthält
var totalSize = 0; // Enthält die Gesamtgröße aller hochzuladenden Dateien

function main() {

    const dragzone = document.getElementById("dragzone");
    dragzone.addEventListener('drop', handleDropEvent, false);
    dragzone.addEventListener('dragover', function (ev) {
        ev.preventDefault();
    }, false);
}

function uploadNewImage() {

    if (filelist.length === 0) {
        return;
    }

    getBase64(filelist[0]).then(function (data) {
        console.log(data);
        const imageCaption = document.getElementById("caption").value;
        const imageDesc = document.getElementById("description").value;

        let requestParameters = {
            "imageCaption": imageCaption,
            "imageDesc": imageDesc,
            "imageData": data
        };

        let requestBody = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(requestParameters)
        };

        fetch('saveNewImage', requestBody)
            .then(function (response) {
                return response.json();
            })
            .then(function (myJson) {
                console.log(JSON.stringify(myJson));
            });
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
}

