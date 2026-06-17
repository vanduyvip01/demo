const token = localStorage.getItem("token");

// ======================
// CHECK LOGIN
// ======================

if (!token) {
window.location.href = "login.html";
}

// ======================
// LOAD USERS
// ======================

async function loadUsers() {

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

    if (!response.ok) {
        alert(result.message);
        return;
    }

    const data = result.data;

    if (!data) {
        console.log("Data null");
        return;
    }

    // TOTAL USERS

    const totalUsersElement =
        document.getElementById("totalUsers");

    if (totalUsersElement) {
        totalUsersElement.innerText =
            data.totalUsers || 0;
    }

    // USER LIST

    const userListElement =
        document.getElementById("userList");

    if (!userListElement) {
        console.log("userList not found");
        return;
    }

    let html = "";

    const users = data.users || [];

    users.forEach(user => {

        html += `
            <div class="user-card">

                <h3>${user.name}</h3>

                <p>Age: ${user.age}</p>

                <p>Email: ${user.email}</p>

                <p>Phone: ${user.phone}</p>

                <p>Gender: ${user.gender}</p>

                <button
                    class="delete-btn"
                    onclick="deleteUser(${user.id})"
                >
                    Delete
                </button>

            </div>
        `;
    });

    userListElement.innerHTML = html;

}
catch (error) {

    console.error(
        "LOAD USERS ERROR =",
        error
    );

}


}

// ======================
// ADD USER
// ======================

async function addUser() {

const user = {

    name:
        document.getElementById("name").value,

    age:
        parseInt(
            document.getElementById("age").value
        ),

    email:
        document.getElementById("email").value,

    phone:
        document.getElementById("phone").value,

    gender:
        "MALE"
};

try {

    const response = await fetch(
        "http://localhost:8080/users",
        {
            method: "POST",

            headers: {
                Authorization:
                    "Bearer " + token,
                "Content-Type":
                    "application/json"
            },

            body:
                JSON.stringify(user)
        }
    );

    const result =
        await response.json();

    alert(result.message);

    document.getElementById("name").value = "";
    document.getElementById("age").value = "";
    document.getElementById("email").value = "";
    document.getElementById("phone").value = "";

    loadUsers();

}
catch (error) {

    console.error(
        "ADD USER ERROR =",
        error
    );

}


}

// ======================
// DELETE USER
// ======================

async function deleteUser(id) {

if (!confirm("Delete this user?")) {
    return;
}

try {

    const response = await fetch(
        "http://localhost:8080/users/" + id,
        {
            method: "DELETE",

            headers: {
                Authorization:
                    "Bearer " + token
            }
        }
    );

    const result =
        await response.json();

    alert(result.message);

    loadUsers();

}
catch (error) {

    console.error(
        "DELETE USER ERROR =",
        error
    );

}


}

// ======================
// LOGOUT
// ======================

function logout() {


localStorage.removeItem("token");

window.location.href =
    "login.html";


}

// ======================
// START
// ======================

document.addEventListener(
"DOMContentLoaded",
function () {


    console.log(
        document.getElementById("totalUsers")
    );

    console.log(
        document.getElementById("userList")
    );

    loadUsers();

}


);
