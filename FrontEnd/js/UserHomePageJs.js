document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem("token");

    if (!token) {
        // Token localStorage එකේ නොතිබේ නම්
        alert("Please login first!");
        window.location.assign("index.html"); // login page
        return;
    }

    // Token decode කරන්න (optional: role check)
    const payload = parseJwt(token);

    // Token expire check (optional)
    const currentTime = Math.floor(Date.now() / 1000); // seconds
    if (payload.exp && currentTime > payload.exp) {
        localStorage.removeItem("token");
        alert("Session expired. Please login again!");
        window.location.assign("index.html");
        return;
    }

    console.log("User logged in, role:", payload.role);
});

function parseJwt(token) {
    let base64Url = token.split('.')[1];
    let base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    let jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
    return JSON.parse(jsonPayload);
}


$("#logOut_Btn").on("click", function () {
   localStorage.removeItem("token");
    window.location.assign("index.html");
});