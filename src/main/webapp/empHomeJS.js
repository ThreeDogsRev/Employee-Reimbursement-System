const reimbursmentCard = document.getElementById("submitReimbursement");
const userInputs = reimbursmentCard.querySelectorall("input");
const userInputsType = document.getElementById("type-select");

const table = document.querySelector("table");

const submitReimbursement = () => {
  const username = userInputs[0].value;
  const amount = userInputs[1].value;
  const description = userInputs[2].value;
  const type = userInputsType.value;
};

//render tables

const renderTable = (data) => {
  let header = document.createElement("thead");
  let headerRow = document.createElement("tr");

  header.appendChild(headerRow);
  table.appendChild(header);

  let th1 = document.createElement("th");
  th1.innerHTML = "Id";
  let th2 = document.createElement("th");
  th2.innerHTML = "Employee Name";
  let th3 = document.createElement("th");
  th3.innerHTML = "Type";
  let th4 = document.createElement("th");
  th4.innerHTML = "Date";
  let th5 = document.createElement("th");
  th5.innerHTML = "amount";
  let th6 = document.createElement("th");
  th6.innerHTML = "reslover";
  let th7 = document.createElement("th");
  th7.innerHTML = "status";

  data.array.forEach((element) => {
    let row = document.createElement("tr");
    let td1 = document.createElement("td");
    let td2 = document.createElement("td");
    let td3 = document.createElement("td");
    let td4 = document.createElement("td");
    let td5 = document.createElement("td");
    let td6 = document.createElement("td");
    let td7 = document.createElement("td");

    td1.innerHTML = element.id;
    td2.innerHTML = element.firstName;
    td3.innerHTML = element.type;
    td4.innerHTML = element.submitDate;
    td5.innerHTML = element.amount;
    td6.innerHTML = element.resolver;
    td7.innerHTML = element.status;

    row.appendChild(td1);
    row.appendChild(td2);
    row.appendChild(td3);
    row.appendChild(td4);
    row.appendChild(td5);
    row.appendChild(td6);
    row.appendChild(td7);

    table.appendChild(row);
  });
};

function fetchOpen() {
  let hostname = window.location.hostname;

  fetch(
    `http://${hostname}:8080/Employee-Reimbursment-System/employees?id=<id>`
  ).then((response) => response.json());
}
