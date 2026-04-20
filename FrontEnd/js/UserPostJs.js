document.addEventListener('DOMContentLoaded', () => {

    getAllPost();


});


let page = 0;
let size = 4;
let loading = false; // prevent multiple calls at once

function loadOnScroll() {
    if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight - 100 && !loading) {
        if (!$("#jobSearchBar").val()) {
            getAllPost();
        }
    }
}

window.addEventListener("scroll", loadOnScroll); // function pass කරනවා




let jobs = [];
let postCount =0;


    function getAllPost() {
        // if (loading) return; // prevent duplicate calls
        // loading = true;
        console.log("get all Post")

            const token = localStorage.getItem('token')

        fetch(`http://localhost:8080/user/allPost?page=${page}&size=${size}`, {
            method:'GET',
            headers:{
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            }
        }).then(res => res.json())
            .then(response => {
                const newJobs = response.data; // API එකේ page එකේ posts

                if (newJobs.length > 0) {
                    console.log(newJobs.length)
                    newJobs.forEach(job => {
                        if (!jobs.some(j => j.id === job.id)) { // check duplicate by ID
                            jobs.push(job);
                            console.log(job)
                            renderPosts([job]);
                           // render one by one

                            // postCount++;
                            // if (postCount === 4) {
                            //     page++;
                            //     postCount =0
                            //     console.log("page size" + page)
                            // }
                        }
                    });
                    page++
                        console.log("page size" + page)

                }

                else {
                    // window.removeEventListener("scroll", loadOnScroll);
                    window.removeEventListener("scroll", loadOnScroll); // ✅ remove properly

                    console.log("No more posts");
                }
            })
            .finally(() => {
                loading = false; // finish loading
            });
    }




    function formatDateTime(timestamp) {
    const date = new Date(timestamp);
    return date.toLocaleString("en-GB", {
    year: "numeric",
    month: "long",
    day: "2-digit",
    hour: "2-digit",
    minute: "2-digit",
    hour12: true
});
}

    function renderPosts([job]) {

        const container = document.getElementById("previewContainer");
        // if ($("#jobSearchBar").val()){
            container.innerHTML = ""; // clear before rendering
        // }



        jobs.forEach(job => {
            postHeader(job.username, job.id);

            const card = document.createElement("div");
            card.classList.add("post-card");

            card.innerHTML = `
                <div class="post-header" class="post-header feed-profile" style="cursor:pointer;">
                     <img id="profileImg${job.id}" class="company-logo">
                    <divc lass="name" class="post-meta">
                        <h3 id="profileName${job.id}"></h3>
                        <div class="post-date">
                            <i class="far fa-clock"></i> Posted on ${formatDateTime(job.createdAt)}
                        </div>
                    </div>
<!--                    <div class="post-menu">-->
<!--                        <i class="fas fa-ellipsis-h"></i>-->
<!--                    </div>-->
                </div>

                <div class="post-content">
                    <div class="job-caption">
                        <span class="caption-item"><i class="fas fa-map-marker-alt"></i> ${job.address}</span>
                        <span class="caption-item"><i class="fas fa-briefcase"></i> ${job.experienceRequired}</span>
                        <span class="caption-item"><i class="fas fa-dollar-sign"></i> ${job.salaryRange}</span>
                        <span class="caption-item"><i class="fas fa-tools"></i> ${job.skills}</span>
                        <span class="caption-item"><i class="fas fa-clock"></i> ${job.jobType}</span>
                        <span class="caption-item"><i class="fas fa-users"></i> ${job.jobTitle} </span>
                    </div>

                    <p class="post-text">${job.jobDescription}</p>

                    <img src="${job.jobImagePath}" alt="Job Image" class="post-image">
                </div>

                <div class="post-actions">
                    <div id="chat${job.id}" class="post-action">
                        <i class="far fa-comments"></i> Chat
                    </div>
                    <div class="post-action">
                        <textarea  hidden readonly id="jobLocations-${job.id}"> ${job.location}</textarea>
                        <button type="button" class="openMap" onclick="openModal(document.getElementById('jobLocations-${job.id}').value)" >
                         <i class="fas fa-map-marker-alt" id="location"></i> see location
                        </button>

                    </div>
                </div>
            `;

            container.appendChild(card);
        });

    }

    let image =""
    let imgId = 0

    function postHeader(userName,id) {
        const token = localStorage.getItem('token')
        console.log(userName)

        fetch(`http://localhost:8080/user/postProfile?userId=${userName}`, {
            method:'GET',
            headers:{
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            }
        }).then(res => res.json())
            .then(response => {

                // HTML already rendered → safe to set
                const imgEl = document.getElementById(`profileImg${id}`);
                const nameEl = document.getElementById(`profileName${id}`);
                if (nameEl) {
                    nameEl.textContent = response.data.companyName; // text දාන්න
                }

                if (imgEl) {
                    imgEl.setAttribute("src", response.data.profileImage);
                } else {
                    console.warn("Image element not found for id:", id);
                }
            })
    }

function parseJwt(token) {
    let base64Url = token.split('.')[1];
    let base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    let jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
    return JSON.parse(jsonPayload);
}

$(document).on("click", "[id^=chat]", function () {
    let jobId = $(this).attr("id").replace("chat", "");
    console.log("hi from job id:", jobId);

    const token = localStorage.getItem("token");
    let userName = parseJwt(token).sub;
    console.log(userName);
    console.log(jobId);

    // Encode special characters in URL
    window.location.assign(`chat.html?jobId=${jobId}&userName=${encodeURIComponent(userName)}`);
});



    // run render

