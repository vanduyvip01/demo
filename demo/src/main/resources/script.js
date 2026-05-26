// ======================
// CHECK LOGIN
// ======================

const token =
localStorage.getItem("token");

if (
!token &&
!window.location.pathname.includes("login")){

window.location.href ="index.html";

}

// ======================
// LOGIN
// ======================

const loginForm = document.getElementById("loginForm");

const message = document.getElementById("message");


if(loginForm){

loginForm.addEventListener("submit",async function(event){

event.preventDefault();

const username =
document.getElementById("username").value;

const password =
document.getElementById("password").value;

try{

const response =
await fetch(

"http://localhost:8080/auth/login",

{

method:"POST",

headers:{

"Content-Type":
"application/x-www-form-urlencoded"

},

body:

`username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`

}

);

const data =await response.json();

if(response.ok){

localStorage.setItem("token",data.data);

message.style.color ="green";

message.innerText =data.message;

setTimeout(()=>{

window.location.href ="user.html";},500);

}

else{

message.style.color ="red";

message.innerText =data.message;
}
}
catch(error){

console.log(error);

message.innerText ="Server Error";
}
}
);

}

// ======================
// LOAD USERS + STATS
// ======================

async function loadStats(){

try{const response =await fetch("http://localhost:8080/users/stats",

{

method:"GET",headers:{

Authorization:`Bearer ${token}`

}

}
);
const result =await response.json();

console.log("FULL RESPONSE",result);


// TOTAL USER

const total =document.getElementById("totalUsers");

if(total){total.innerText =result.totalUsers;

}

// USER LIST

const userList =document.getElementById("userList");

if(userList){
let html ="";
result.users.forEach(user=>{html +=`

<div class="user-card">

<h3>

${user.name}

</h3>

<p>
Age:
${user.age}
</p>

<p>
Email:
${user.email}
</p>

<p>
Phone:
${user.phone}
</p>

<p>
Gender:
${user.gender}
</p>

<button

class="delete-btn"

onclick="deleteUser(${user.id})"

>

Delete

</button>

</div>
`;

}
);
userList.innerHTML =html;
}

}

catch(error){
console.log("LOAD ERROR",error);

}
}
// ======================
// DELETE USER
// ======================
async function deleteUser(id){try{
await fetch(`http://localhost:8080/users/${id}`,{

method:"DELETE",

headers:{

Authorization:`Bearer ${token}`
}
}
);
loadStats();
}
catch(error){console.log(error);
}
}



// ======================
// LOGOUT
// ======================

function logout(){

localStorage.removeItem("token");
window.location.href ="index.html";
}

// ======================
// AUTO LOAD
// ======================

if(window.location.pathname.includes("user")){
loadStats();

}