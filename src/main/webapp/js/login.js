// this is dummy data for the login page
localStorage.setItem(
  "user",
  JSON.stringify({
    id: 4,
    firstName: "Default",
    lastName: "Default",
    email: "Default@mail.com",
    employeeRole: "Default",
    reimbursements: [{
      id: 4,
      status: "PENDING",
      type: "FOOD",
      submitDate: 1656824217197,
      amount: 2025,
      description: "Mcdonalds",
      lastModified: 1656824217197,
      resolver: null,
      receipt: null,
    },],
    username: "Default",
  })
);


const fetchPendingReimbursements = () => {
  return fetch("reimbursements?status=PENDING")
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      return data;
    });
};


const approve_reimbursement = async (reimbursement_id) => {
  fetch(`update-reimbursement?id=${reimbursement_id}&status=APPROVED`).then((response) => {
    console.log(response);
    initialize();
  });
}

const deny_reimbursement = async (reimbursement_id) => {
  fetch(`update-reimbursement?id=${reimbursement_id}&status=DENIED`).then((response) => {
    console.log(response);
    initialize();
  });
}


const approve_deny_button = (reimbursement) => {
  const cell = document.createElement('td');
  if (reimbursement.status === "PENDING") {
    cell.innerHTML = `
      <div class="btn-group" role="group">
        <button class="btn btn-outline-success btn-sm" onclick="approve_reimbursement(${reimbursement.id})">${String.fromCodePoint(0x2714, 0xFE0F)}</button>
        <button class="btn btn-outline-danger btn-sm" onclick="deny_reimbursement(${reimbursement.id})">${String.fromCodePoint(0x274C)}</button>
      </div>`;
    return cell;
  }
}

const drawPendingReimbursements = (reimbursements) => {
  const dashboardBody = document.getElementById("admin-dashboard-body");
  dashboardBody.innerHTML = "";

  if (reimbursements.length < 1) {
    const no_reimbursements = document.createElement("div");
    no_reimbursements.setAttribute("class", "no-reimbursements text-center");
    no_reimbursements.innerText = "No pending reimbursements";
    dashboardBody.appendChild(no_reimbursements);
    return;
  }

  const table = document.createElement("table");
  table.setAttribute("id", "reimbursements-table");
  table.setAttribute("class", "table table-striped table-borderless");

  const header = document.createElement("thead");
  header.appendChild(document.createElement("th")).innerText = "Id";
  header.appendChild(document.createElement("th")).innerText = "Author";
  header.appendChild(document.createElement("th")).innerText = "Type";
  header.appendChild(document.createElement("th")).innerText = "Amount";
  header.appendChild(document.createElement("th")).innerText = "Description";
  header.appendChild(document.createElement("th")).innerText = "Approve/Deny";
  table.appendChild(header);

  const body = document.createElement("tbody");

  reimbursements.forEach((reimbursement) => {
    const row = document.createElement("tr");
    const id = document.createElement("td");
    id.innerText = reimbursement.id;
    const author = document.createElement("td");
    author.innerText = reimbursement.submitter;
    const type = document.createElement("td");
    type.innerText = reimbursement.type;
    const amount = document.createElement("td");
    amount.innerText = `$${reimbursement.amount / 100}`;
    const description = document.createElement("td");
    description.innerText = reimbursement.description;

    row.appendChild(id);
    row.appendChild(author);
    row.appendChild(type);
    row.appendChild(amount);
    row.appendChild(description);
    row.appendChild(approve_deny_button(reimbursement));
    body.appendChild(row);
  });
  table.appendChild(body);
  document.getElementById("admin-dashboard-body").innerHTML = "";
  document.getElementById("admin-dashboard-body").appendChild(table);
};

const initialize = () => {
  return fetch("me")
    .then((response) => response.json())
    .then((user) => {
      if (user == null) {
        document.getElementById("login-form").setAttribute("class", "active");
        document.getElementById("dashboard").setAttribute("class", "");
      } else {
        localStorage.setItem("user", JSON.stringify(user));
        document.getElementById("login-form").setAttribute("class", "");
        document.getElementById("dashboard").setAttribute("class", "active");
        // render th admin dashboard if the user is an admin
        if (user.employeeRole === "ADMIN" || user.employeeRole === "MANAGER") {
          document.getElementById("admin-dashboard").classList.remove("d-none");
          fetchPendingReimbursements().then((reimbursements) => {
            drawPendingReimbursements(reimbursements);
          });
        }
      }
    }).then(() => {
      document.getElementById("welcome-message").innerHTML =
        localStorage.getItem("user") ?
          `Welcome, ${JSON.parse(
            localStorage.getItem("user")
          ).firstName[0].toUpperCase()}${JSON.parse(localStorage.getItem("user")).firstName.substring(1)
          }` :
          "Welcome, Employee";
    });
};

initialize();

document.getElementById("logout-button").addEventListener("click", () => {
  localStorage.removeItem("user");
  fetch("logout").then(() => {
    initialize();
  });
});
