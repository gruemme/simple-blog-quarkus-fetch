fetch('/entries?sort=created%2Cdesc')
.then(resp => resp.json())
.then(function (data) {
    showEntries(data.content);
})
.catch(function (error) {
    console.error(error);
});
