
const userCredentialString = sessionStorage.getItem("userCredential")

if (userCredentialString !== null) {
    // use the credential to authenticate
    const userCredential = JSON.parse(userCredentialString);
    console.log(userCredential.id, userCredential.password);

    console.log('Credentials found');
    document.getElementById('createLink').hidden = false;
    document.getElementById('loginLink').hidden = true;
    document.getElementById('logoutLink').hidden = false;
} else {
    // no credential found
    console.log('No credential found');
    document.getElementById('createLink').hidden = true;
    document.getElementById('loginLink').hidden = false;
    document.getElementById('logoutLink').hidden = true;
}

function logout() {
    sessionStorage.clear();
    location.href = "index.html";
}
