function showEntries(data) {
    if (data.length === 0) {
        return;
    }
    const dataDiv = document.getElementById('content');
    let entries = '';
    data.forEach(entry => {
        const entryDate = new Date(entry.created);
        const entryDateFormatted = formatDate(entryDate);
        entries += '<div class="row w-90 gap-3"><div class="col"><article>'
            + `<div><h2>${entry.id} ${entry.title}</h2></div>`
            + `<div><p class="text-end fw-lighter"><small>${entryDateFormatted} by ${entry.author.username}</small></p></div>`
            + `<div>${getImage(entry.titleImage)}</div>`
            + `<div><p class="text-justify">${entry.text}</p></div>`
            + `<div><p><br /></p></div>`
            + `<div><p class="text-end fw-lighter">Tags: ${formatTags(
                entry.tags) ?? '-'}</p></div>` + '<br /></article></div></div>';
    });
    dataDiv.innerHTML = entries;
}

function formatDate(entryDate) {
    return `${entryDate.getFullYear()}-${entryDate.getMonth()
    + 1}-${entryDate.getDate()} ${entryDate.getHours()}:${entryDate.getMinutes()}`;
}

function formatTags(tags) {
    return tags.map(function (tag) {
        return `<a href="/tag.html?tag=${tag.name}">${tag.name}</a>`
    }).toString();
}

function getImage(titleImage) {
    if (titleImage !== null && typeof titleImage !== 'undefined') {
        return `<img alt="blog-image" class="mx-auto d-block" src="data:${titleImage.contentType};base64,${titleImage.imageContent}" />`
    }

    return '';
}
