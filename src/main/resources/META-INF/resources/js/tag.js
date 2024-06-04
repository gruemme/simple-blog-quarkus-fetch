let queryParams = new URLSearchParams(window.location.search);
let tag = queryParams.get("tag");
if (tag === null || tag === 'undefined') {
    location.href = "index.html"
} else {
    fetch('/tags/' + tag)
    .then(resp => resp.json())
    .then(function (data) {
        showEntries(data);
    })
    .catch(function (error) {
        console.error(error);
    });
}
