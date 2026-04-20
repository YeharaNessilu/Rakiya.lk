 const post = $('#post');
const cansel = $("#cancel")

let base64String = '';

 function previewAndUploadImage(event) {
     const file = event.target.files[0];
     if (!file) return;

     const reader = new FileReader();

     reader.onload = function(e) {
         // Base64 String (data:image/png;base64,....)
          base64String = e.target.result;

         console.log("Base64 Image:", base64String);


         // Hidden input එකකට set කරන්න (backendට යවන්න easy)
         document.getElementById("hiddenBase64").value = base64String;
     };

     reader.readAsDataURL(file);
 }

 function fillAll() {
     let isValid = true; // flag

     // fields array
     const fields = [
         '#jobTitle',
         '#address',
         '#experience',
         '#salary',
         '#jobType',
         '#workMode',
         '#skills'
     ];

     fields.forEach(selector => {
         const field = $(selector);
         if (!field.val()) {
             field.css("border-color", "red"); // empty → red
             isValid = false;                  // mark as invalid
         } else {
             field.css("border-color", "");    // filled → normal
         }
     });

     return isValid; // true if all filled, false if any empty
 }


 function parseJwt (token) {
     const base64Url = token.split('.')[1];
     const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
     const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
         return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
     }).join(''));
     return JSON.parse(jsonPayload);
 };
  const token = localStorage.getItem("token");
 const user = (parseJwt(token));

//
//  const playLoad = {
//      $('#jobTitle').val(),
//     $('#address').val(),
//     ('#jobLocations').val() ,
//     $('#experience').val(),
//     $('#salary').val(),
//     $('#jobType').val(),
//     $('#workMode').val(),
//     $('#skillInput').val(),
//     $('#jobContent').val(),
// }

post.on('click', function () {

    // if (!fillAll()) {
    //     return;
    //     // proceed with building jobData object / submit Ajax
    // }

    console.log($('#jobTitle').val())
    console.log($('#address').val())
    console.log($('#jobLocations').val())
    console.log($('#experience').val())
    console.log( $('#salary').val())
    console.log($('#jobType').val())
    console.log($('#workMode').val())
    console.log( $('#jobContent').val())
    console.log(base64String)

    console.log(user.sub)

    fetch( 'http://localhost:8080/company/postJob', {
        method: 'POST',
        headers:{
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token
        },
        body: JSON.stringify({
            "jobTitle": $('#jobTitle').val(),
            "address": $('#address').val(),
            "location":$('#jobLocations').val() ,
            "experienceRequired":  $('#experience').val(),
            "salaryRange":  $('#salary').val(),
            "jobType":  $('#jobType').val(),
            "workMode":  $('#workMode').val(),
            "skills":  $('#skillInput').val(),
            "jobDescription":  $('#jobContent').val(),
            "jobImagePath":  base64String,
            "createdAt": new Date(),
            "userName":  user.sub
        })
    }).then(response =>{
        if (response.status === 403) {
            Swal.fire({
                icon: "error",
                title: "Oops...",
                text: "Something went wrong!",
                footer: '<a href="#">Why do I have this issue?</a>'
            });
            throw new Error('403 Forbidden - Check if your role is correctly set to ROLE_COMPANY');

        }
        if (!response.ok) {
            Swal.fire({
                icon: "error",
                title: "Oops...",
                text: "Something went wrong!",
                footer: '<a href="#">Why do I have this issue?</a>'
            });
            throw new Error('HTTP error ' + response.status);


        }
        console.log(response.json())
        const Toast = Swal.mixin({
            toast: true,
            position: "top-end",
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: true,
            didOpen: (toast) => {
                toast.onmouseenter = Swal.stopTimer;
                toast.onmouseleave = Swal.resumeTimer;
            }
        });
        Toast.fire({
            icon: "success",
            title: "SAVE successfully"
        });
        window.location.href = "companyMainPage.html";
        // return response.json();
    });



})


cansel.on('click', function () {
    Swal.fire({
        title: "Are you sure?",
        text: "You won't be able to revert this!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, Go Home Page!"
    }).then((result) => {
        if (result.isConfirmed) {
            Swal.fire({
                title: "Ok!",
                text: "Home Page.",
                icon: "success"
            });

            window.location.href = "companyMainPage.html";

        }
    });
});
