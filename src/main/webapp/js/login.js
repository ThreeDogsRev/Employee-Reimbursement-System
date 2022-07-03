// this is dummy data for the login page
localStorage.setItem(
  "user",
  JSON.stringify({
    id: 4,
    firstName: "Default",
    lastName: "Default",
    email: "Default@mail.com",
    employeeRole: "Default",
    reimbursements: [
      {
        id: 4,
        status: "PENDING",
        type: "FOOD",
        submitDate: 1656824217197,
        amount: 2025,
        description: "Mcdonalds",
        lastModified: 1656824217197,
        resolver: null,
        receipt: null,
      },
    ],
    username: "Default",
  })
);

const check_logged_in = () => {
  return fetch("me")
    .then((response) => response.json())
    .then((user) => {
      if (user == null) {
        document.getElementById("login-form").setAttribute("class", "active");
        document.getElementById("dashboard").setAttribute("class", "");
        //document.getElementById("loading").setAttribute("class", "");
      } else {
        localStorage.setItem("user", JSON.stringify(user));
        document.getElementById("login-form").setAttribute("class", "");
        document.getElementById("dashboard").setAttribute("class", "active");
        //document.getElementById("loading").setAttribute("class", "");
      }
    })
    .then(() => {
      document.getElementById("welcome-message").innerHTML =
        localStorage.getItem("user")
          ? `Welcome, ${JSON.parse(
              localStorage.getItem("user")
            ).firstName[0].toUpperCase()}${
              JSON.parse(localStorage.getItem("user")).firstName.substring(1)
            }`
          : "Welcome, Employee";
    });
};

check_logged_in();

document.getElementById("logout-button").addEventListener("click", () => {
  localStorage.removeItem("user");
  fetch("logout").then(() => {
    check_logged_in();
  });
});
