

const check_logged_in = () => {
    return fetch('/employee-reimbursement-system/me')
        .then(response => response.json())
        .then(user => {
            if(user == null){
                document.getElementById('login-form').setAttribute('class', 'active')
                document.getElementById('dashboard').setAttribute('class', '')
                document.getElementById('loading').setAttribute('class', '')
            } else{
                localStorage.setItem('user', JSON.stringify(user))
                document.getElementById('login-form').setAttribute('class', '')
                document.getElementById('dashboard').setAttribute('class', 'active')
                document.getElementById('loading').setAttribute('class', '')
            }
        });
}


check_logged_in();

document.getElementById('welcome-message').innerHTML = localStorage.getItem('user') 
    ? `Welcome, ${JSON.parse(localStorage.getItem('user')).firstName}`
    : 'Welcome, Employee';

document.getElementById('logout-button').addEventListener('click', () => {
    localStorage.removeItem('user');
    fetch('/employee-reimbursement-system/logout');
});

localStorage.setItem('user', JSON.stringify(
    {
        "id": 4,
        "firstName": "Manager",
        "lastName": "Manager",
        "email": "Manager@mail.com",
        "employeeRole": "MANAGER",
        "reimbursements": [
          {
            "id": 4,
            "status": "PENDING",
            "type": "FOOD",
            "submitDate": 1656824217197,
            "amount": 2025,
            "description": "Mcdonalds",
            "lastModified": 1656824217197,
            "resolver": null,
            "receipt": null
          }
        ],
        "username": "manager"
      }
))
