const token = localStorage.getItem("token");

const currentPage = window.location.pathname;

if (!token && currentPage.includes("user")) {
    window.location.href = "index.html";
}

const loginForm = document.getElementById("loginForm");
const message = document.getElementById("message");

if (loginForm) {
    loginForm.addEventListener("submit", async function (event) {
        event.preventDefault();
        const emailValue = document.getElementById("username").value;
        const passwordValue = document.getElementById("password").value;

        try {
            const response = await fetch("http://localhost:8080/auth/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    email: emailValue,
                    password: passwordValue
                })
            });

            const result = await response.json();
            console.log("LOGIN RESPONSE =", result);

            if (response.ok) {
                localStorage.setItem("token", result.data);

                message.style.color = "green";
                message.innerText = "Đăng nhập thành công!";

                setTimeout(function () {
                    window.location.href = "user.html";
                }, 500);

            } else {
                message.style.color = "red";
                message.innerText = result.message || "Sai tài khoản hoặc mật khẩu";
            }

        } catch (error) {
            console.error(error);
            message.style.color = "red";
            message.innerText = "Server Error";
        }
    });
}

async function loadStats() {
    try {
        const response = await fetch(
            "http://localhost:8080/users/stats",
            {
                method: "GET",

                headers: {
                    Authorization: "Bearer " + token
                }
            }
        );

        const result = await response.json();

        console.log("RESULT =", result);
        console.log("DATA =", result.data);

        if (!response.ok) {

            alert(result.message);
            return;

        }

        const data = result.data;

        if (!data) {
            return;
        }

        const total =
            document.getElementById("totalUsers");

        if (total) {
            total.innerText = data.totalUsers;
        }
        const userList =
            document.getElementById("userList");
        if (userList) {
            let html = "";

            data.users.forEach(function (user) {
                html +=
                    "<div class='user-card'>" +
                    "<h3>" + user.name + "</h3>" +
                    "<p>Age: " + user.age + "</p>" +
                    "<p>Email: " + user.email + "</p>" +
                    "<p>Phone: " + user.phone + "</p>" +
                    "<p>Gender: " + user.gender + "</p>" +
                    "<button class='delete-btn' onclick='deleteUser(" + user.id + ")'>Delete</button>" +
                    "</div>";
            });
            userList.innerHTML = html;
        }

    } catch (error) {
        console.error("LOAD ERROR =", error);
    }
}

async function deleteUser(id) {
    try {
        const response = await fetch(
            "http://localhost:8080/users/" + id,
            {
                method: "DELETE",
                headers: {
                    Authorization: "Bearer " + token
                }
            }
        );

        const result = await response.json();

        console.log("DELETE RESPONSE =", result);

        if (response.ok) {
            loadStats();
        } else {
            alert(result.message);
        }
    } catch (error) {
        console.error(error);
    }
}
function logout() {
    localStorage.removeItem("token");
    window.location.href = "index.html";
}

if (currentPage.includes("user")) {
    loadStats();
}