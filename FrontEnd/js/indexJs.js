const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');

const signup_btn =$("#signup_btn");
const nameText = $("#nameText");
const passwordText = $("#passwordText");
const confirmPasswordText = $("#confirmPasswordText");
const emailText = $("#emailText");
const otpText = $("#otpText");

const companySignUPBtn =$("#companySignUPBtn");
const companyNameText = $("#companyNameText");
const companyPasswordText = $("#companyPasswordText");
const companyConfirmPasswordText = $("#companyConfirmPasswordText");
const companyEmailText = $("#companyEmailText");
const companyOtpText = $("#companyOtpText");
const companySendOtpBtn = document.getElementById("companySendOtpBtn");


const signIn_btn = $("#signIn_Btn");
const signInEmail =  $("#signInEmail");
const signInPassword =  $("#signInPassword");


signUpButton.addEventListener('click', () => {
    container.classList.add("right-panel-active");
    otpText.prop("disabled",true)
    passwordText.prop("disabled",true)
    confirmPasswordText.prop("disabled",true)
    // signup_btn.disabled = true;

});

signInButton.addEventListener('click', () => {
    container.classList.remove("right-panel-active");
    // signInEmail.val().clear();
    // signInPassword.val().clear();
});




const sendOtpBtn = document.getElementById("sendOtpBtn");
let timer;

sendOtpBtn.addEventListener("click", function () {
    const email = emailText.val();

    if (!email) {
        alert("Please enter your email first!");
        return;
    }
    otpText.prop("disabled",false);

    // --- මේ තැන ඔයාගේ backend එකට OTP request යවන්න ---
    console.log("OTP sent to:", email);
    $.ajax({
        url: 'http://localhost:8080/auth/otp',
        type: 'POST',
        data: JSON.stringify({
            email: emailText.val(),
            }),
        contentType: 'application/json',
        success: function (response) {
            console.log(response)
        },
        error: function (xhr, status, error){
            console.error(error)
        }
    });

    // Disable button and start countdown
    let timeLeft = 60;
    sendOtpBtn.disabled = true;
    sendOtpBtn.textContent = `${timeLeft}s`;

    timer = setInterval(() => {
        timeLeft--;
        sendOtpBtn.textContent = `${timeLeft}s`;

        if (timeLeft <= 0) {
            clearInterval(timer);
            sendOtpBtn.disabled = false;
            sendOtpBtn.textContent = "Send OTP";
            otpText.prop("disabled",false);

        }
    }, 1000);
});


otpText.on("input", function () {
    if (otpText.val().length===4){
        console.log("aa");
        otpText.prop("disabled",true);

        $.ajax({
           url: 'http://localhost:8080/auth/otpValidate',
            type: 'POST',
            data: JSON.stringify({
                email: emailText.val(),
                otp: otpText.val(),
                time: ""
            }),
            contentType: 'application/json',
            success: function (req) {

                console.log("👉 Data value:", req);  // check boolean directly


                if (req === true){
                   nameText.prop("disabled",true);
                   emailText.prop("disabled",true);
                   otpText.css("color", "green");
                   otpText.prop("disabled",true);

                   passwordText.prop("disabled",false);
                   confirmPasswordText.prop("disabled",false);
                   console.log(req)
                    return
               }

                otpText.css("color", "red")
            },
            error: function (error) {
                otpText.css("color", "red");
                console.error(error)
            }
        });


    }
});

if (confirmPasswordText.val()){
    signup_btn.disabled = false;
}


signup_btn.on("click", function () {

    if (nameText.val() && emailText.val() && otpText.val() && passwordText.val() && confirmPasswordText.val()){
        if (passwordText.val() === confirmPasswordText.val()){
            $.ajax({
                url: 'http://localhost:8080/auth/register',
                type: 'POST',
                data: JSON.stringify({
                    username: emailText.val(),
                    password: passwordText.val(),
                    role: "USER"
                }),
                contentType:'application/json',
                success: function (res) {

                    nameText.val("");
                    emailText.val("");
                    otpText.val("");
                    passwordText.val("");
                    confirmPasswordText.val("");
                    alert("sign up successfully");

                    const token = res.data.token || res.token || res.data; // backend response structure check කරන්න

                    console.log(token);
                    localStorage.setItem("token", token);


                    const payload = parseJwt(token);
                    console.log("User role:", payload.role);


                    if (payload.role === "ROLE_COMPANY") {
                        window.location.assign("companyMainPage.html");
                    } else if (payload.role === "ROLE_USER") {
                        window.location.assign("UserHomePage.html");
                    } else {
                        alert("Unknown role!"); // fallback
                    }
                },
                error: function (error) {
                    console.error(error);
                }
            });
        }
    }else {
        alert("Please fill the form first!");
    }
});

signIn_btn.on("click", function () {

    console.log("clicked");
    if (!signInEmail.val() || !signInPassword.val()) {
        alert("Please enter your email & password first!");
        return;
    }
    console.log(signInEmail.val());
    console.log(signInPassword.val());


    $.ajax({
        url: 'http://localhost:8080/auth/login',
        type: 'POST',
        data: JSON.stringify({
            username: signInEmail.val(),
            password: signInPassword.val(),
        }),
        contentType:'application/json',
        success: function (res) {

            signInEmail.val("");
            signInPassword.val("");

            alert("sign up successfully");
            const token = res.data.token || res.token || res.data; // backend response structure check කරන්න

            console.log(token);
            localStorage.setItem("token", token);


            const payload = parseJwt(token);
            console.log("User role:", payload.role);
            // alert("sign up successfully");


            if (payload.role === "ROLE_COMPANY") {
                window.location.assign("companyMainPage.html");
            } else if (payload.role === "ROLE_USER") {
                window.location.assign("UserHomePage.html");
            } else {
                alert("Unknown role!"); // fallback
            }

        },
        error: function (error) {
            console.error(error);
        }
    });

});

// 🔹 JWT decode function එක
function parseJwt(token) {
    let base64Url = token.split('.')[1];
    let base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    let jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
    return JSON.parse(jsonPayload);
}

// COMPANY SIGN UP

companySendOtpBtn.addEventListener("click", function () {
    const email = companyEmailText.val();

    if (!email) {
        alert("Please enter your email first!");
        return;
    }
    companyOtpText.prop("disabled",false);

    // --- මේ තැන ඔයාගේ backend එකට OTP request යවන්න ---
    console.log("OTP sent to:", email);
    $.ajax({
        url: 'http://localhost:8080/auth/otp',
        type: 'POST',
        data: JSON.stringify({
            email: companyEmailText.val(),
        }),
        contentType: 'application/json',
        success: function (response) {
            console.log(response)
        },
        error: function (xhr, status, error){
            console.error(error)
        }
    });

    // Disable button and start countdown
    let timeLeft = 60;
    companySendOtpBtn.disabled = true;
    companySendOtpBtn.textContent = `${timeLeft}s`;

    timer = setInterval(() => {
        timeLeft--;
        companySendOtpBtn.textContent = `${timeLeft}s`;

        if (timeLeft <= 0) {
            clearInterval(timer);
            companySendOtpBtn.disabled = false;
            companySendOtpBtn.textContent = "Send OTP";
            companyOtpText.prop("disabled",false);

        }
    }, 1000);
});



companyOtpText.on("input", function () {
    if (companyOtpText.val().length===4){
        console.log("aa");
        companyOtpText.prop("disabled",true);

        $.ajax({
            url: 'http://localhost:8080/auth/otpValidate',
            type: 'POST',
            data: JSON.stringify({
                email: companyEmailText.val(),
                otp: companyOtpText.val(),
                time: ""
            }),
            contentType: 'application/json',
            success: function (req) {
                if (req === true){
                    console.log("valid otp")
                    companyNameText.prop("disabled",true);
                    companyEmailText.prop("disabled",true);

                    companyOtpText.css("color", "green");
                    companyOtpText.prop("disabled",true);

                    companyPasswordText.prop("disabled",false);
                    companyConfirmPasswordText.prop("disabled",false);
                    return
                }
                console.log("invalid otp")

                companyPasswordText.prop("disabled",true);
                companyConfirmPasswordText.prop("disabled",true);
                companyOtpText.css("color", "read");


                console.log(typeof req)
            },
            error: function (error) {
                console.error(error)

            }
        });


    }
});

if (companyConfirmPasswordText.val()){
    companySignUPBtn.disabled = false;
}


companySignUPBtn.on("click", function () {

    if (companyNameText.val() && companyEmailText.val() && companyOtpText.val() && companyPasswordText.val() && companyConfirmPasswordText.val()){
        if (companyPasswordText.val() === companyConfirmPasswordText.val()){
            $.ajax({
                url: 'http://localhost:8080/auth/register',
                type: 'POST',
                data: JSON.stringify({
                    username: companyEmailText.val(),
                    password: companyPasswordText.val(),
                    role: "COMPANY"
                }),
                contentType:'application/json',
                success: function (res) {

                    companyNameText.val("");
                    companyEmailText.val("");
                    companyOtpText.val("");
                    companyPasswordText.val("");
                    companyConfirmPasswordText.val("");

                    alert("sign up successfully");
                    const token = res.data.token || res.token || res.data; // backend response structure check කරන්න

                    console.log(token);
                    localStorage.setItem("token", token);


                    const payload = parseJwt(token);
                    console.log("User role:", payload.role);


                    if (payload.role === "ROLE_COMPANY") {
                        window.location.assign("companyMainPage.html");
                    } else if (payload.role === "ROLE_USER") {
                        window.location.assign("UserHomePage.html");
                    } else {
                        alert("Unknown role!"); // fallback
                    }

                },
                error: function (error) {
                    console.error(error);
                }
            });
        }
    }else {
        alert("Please fill the form first!");
    }
});
