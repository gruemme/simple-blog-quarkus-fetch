// pass in an HTML5 ArrayBuffer, returns a base64 encoded string
function arrayBufferToBase64(arrayBuffer) {
    const bytes = new Uint8Array(arrayBuffer);
    const len = bytes.byteLength;
    let binary = '';
    for (let i = 0; i < len; ++i) {
        binary += String.fromCharCode(bytes[i]);
    }
    return btoa(binary);
}

function sendData(data) {
    const userCredentialString = sessionStorage.getItem("userCredential")
    const userCredential = JSON.parse(userCredentialString);
    const auth = btoa(userCredential.username + ':' + userCredential.password);
    fetch('/entries', {
        method: 'POST', headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': `Basic ${auth}`
        }, body: JSON.stringify(data)
    }).then(e => location.href = "index.html");
}

function createEntry() {
    let newEntry = {};
    newEntry.title = document.getElementById('inputTitle').value;
    newEntry.text = document.getElementById('inputText').value;

    const inputTags = document.getElementById('inputTags').value.split(
        ',');
    newEntry.tags = inputTags.map(t => t.trim());

    const fileInput = document.getElementById('inputFile').files[0];

    if (fileInput !== null && typeof fileInput !== 'undefined') {
        newEntry.imageContentType = fileInput.type;

        let reader = new FileReader();
        reader.onloadend = function () {
            newEntry.imageData = arrayBufferToBase64(reader.result);
            sendData(newEntry);
        };
        reader.readAsArrayBuffer(fileInput);
    } else {
        sendData(newEntry);
    }
}

if (userCredentialString === null || typeof userCredentialString === undefined) {
    location.href = "index.html";
}
