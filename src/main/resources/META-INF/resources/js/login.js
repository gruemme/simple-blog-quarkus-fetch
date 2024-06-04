
function login() {
    let credentials = {};
    credentials.username = document.getElementById('inputUsername').value;
    credentials.password = document.getElementById('inputPassword').value;

    const auth = btoa(credentials.username + ':' + credentials.password);
    fetch('/users/me', {
        method: 'GET',
        headers: {
            Authorization: `Basic ${auth}`
        },
    }).then(response => {
        if(!response.ok) {
            // TODO: Prevent Chrome asking for credentials
            throw new Error(response.statusText);
        }
        const userCredential = {
            username: credentials.username,
            password: credentials.password
        };
        sessionStorage.setItem("userCredential", JSON.stringify(userCredential));
        console.log('Credential stored successfully');

        return response.json();
    })
    .then(e => location.href = "index.html")
    .catch(function (error) {
        console.error(error);
    });
}
