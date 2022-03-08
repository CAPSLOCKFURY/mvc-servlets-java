let checkInDateVar = null;
let checkOutDateVar = null;
const pricePerDayVar = document.getElementById("pricePerDay").value;

function dateChange(){
    let checkInDate = document.getElementById("checkInDate");
    let checkOutDate = document.getElementById("checkOutDate");
    if(checkInDate.value !== ""){
        checkInDateVar = checkInDate.value;
    }
    if(checkOutDate.value !== ""){
        checkOutDateVar = checkOutDate.value;
    }
    if(checkInDateVar != null && checkOutDateVar != null){
        calculateDaysBetweenDates(checkInDateVar, checkOutDateVar);
    }
}

function calculateDaysBetweenDates(startDate, endDate) {
    let diffInMs = new Date(endDate) - new Date(startDate);
    let diffInDays = diffInMs / (1000 * 60 * 60 * 24);
    if(diffInDays > 0) {
        document.getElementById("calculatedPrice").innerHTML = pricePerDayVar * diffInDays;
    }
}