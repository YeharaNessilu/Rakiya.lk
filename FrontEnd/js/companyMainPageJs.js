document.addEventListener('DOMContentLoaded', function () {
    const token = localStorage.getItem("token")

    const role = parseJwt(token).role;
    let link = ''
    let userName=''

    if (role === "ROLE_COMPANY") {
        link = "company"; userName = parseJwt(token).sub
    } else{
        link = "user"
        userName = localStorage.getItem("cardUserName");
    }


    console.log(userName)

    fetch(`http://localhost:8080/${link}/getProfile?userName=${userName}`,{
        method:"GET",
        headers:{
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token
        }
    }).then(res => res.json())
        .then(response => {
            const profile = response.data; // data object එක
            // console.log(profile.companyName); // TechCorp International
            console.log(profile);    // Software Development
            setData(profile);
        });
});

function parseJwt(token) {
    let base64Url = token.split('.')[1];
    let base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    let jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
    return JSON.parse(jsonPayload);
}

function setData(profile) {
    // Suppose profile object එක server එකෙන් එනවා
// profile.bannerImageBase64 contains base64 string from getBase64FromFile()

    $("#bannerImage").attr("src", profile.bannerImage);

    // Assume profile.profileImageBase64 contains the base64 image string
    $(".profile-avatar").css({
        "background-image": `url(${profile.profileImage})`,
        "background-size": "cover",       // div එකට fit වෙනවා
        "background-position": "center",  // image center align
        "color": "transparent"            // "TC" letters hide කරන්න
    });

// profile object එකෙන් name එක set කිරීම
    $("#profileName").text(profile.companyName); // "TechCorp International" වැනි text set කරයි
    $("#profileTagline").text(profile.tagline);
    let count = 0;
    if (profile.followersCount === null){
        count = 0;
    }else count = profile.followersCount;
    $("#followers").text(count+' followers'); // "TechCorp International" වැනි text set කරයි
    $("#profileOverview").text(profile.overview);
    $("#profileMission").text(profile.mission);
    $("#profileVision").text(profile.vision);
    $("#location").text(profile.locations);
    // $("#profileTagline").text(profile.tagline);

}